package com.mycompany.meallion;

import features.SearchEngine;
import features.SearchResults;
import java.io.FileNotFoundException;
import orm.MealPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import orm.Recipe;
import sql.SQL;
import utils.Configs;
import utils.Log;
import utils.RecipeBuilder;
import utils.RecipeStep;

/**
 * Main servlet handling all requests. Either CookBook is answering itself or forwards to /Menu Servlet, if the request url ends with /Menu.
 * /Menu has no own Servlet because it would have to create another SQL connection object. More than one SQL Connection object could mess-up the persistance entity manager.
 * @author chris
 */

@WebServlet(name = "CookBook", urlPatterns = {"/CookBook","/Menu"})
public class CookBook extends HttpServlet {

    //the SQL connection object
    private SQL sql;
        
    private handler_Menu handler_menu;
    private SearchEngine searchengine;

    public CookBook() throws IOException {
    }
    
    /**
     * Inits the Cookbook: creating the SQL and Solr connections, setting up the Search Engine and Menu Servlet. Therefore taking the file "meallion.conf" from WEB-INF folder.
     * 
     * @param config Information about the configurations of the servlet, such as path names 
     * @throws ServletException 
     */
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        try{
            Log.wdln("Meallion Congi: start configuring...");
            
            String config_fullPath = config.getServletContext().getRealPath("/WEB-INF")+"/meallion.conf";
            
            Log.wdln("meallion onfig file path: "+config_fullPath);
            
            this.sql = new SQL();
            this.handler_menu = new handler_Menu(this.sql);
            
            // Set config parameters:
            
            Configs configs = new Configs(config_fullPath);
            String solr_url = configs.FetchString("solr_url");
            int solr_max_results = configs.FetchInt("solr_max_results");
            
            Log.wdln("Meallion config: solr_url="+solr_url);
            Log.wdln("Meallion config: solr_max_results="+solr_max_results);
            
            Log.wdln("Meallion config: success.");
            
            // End setting config parameters 
            
            this.searchengine = new SearchEngine(this.sql,solr_url,solr_max_results);
            
        }catch(FileNotFoundException fnfe){ 
            Log.edln("Meallion Config file could not be found: "+fnfe);
        }catch(IOException ioe){ 
            Log.edln("while CookBook initializing. Probably SearchEngine could not be started by CookBook: "+ioe);
        }
    }
    
    /**
     * The function catches all requests made to Servlet CookBook and performes switch-ifs of the various parameters (URL text parameters and commands)
     * @param request Current HTTP request
     * @param response Response HTTP to be sent to client
     * @throws ServletException
     * @throws IOException 
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            Log.debug_wdln("Executing CookBook Servlet");
            
            response.setContentType("text/html;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            
            //Check if user session already has a "custom_mealplan" object. If not, create one:
            Log.wd("Check existence of custom_mealplan object..");
            if(request.getSession().getAttribute("custom_mealplan")==null){
                
                Log.w("no custom_mealplan available so far. Create new custom_mealplan object and store in HttpSession.");
                
                MealPlan menu = new MealPlan(this.sql);
                request.getSession().setAttribute("custom_mealplan", menu);
                
                Log.wln(" Done creating custom_mealplan object.");
            }else{
                MealPlan custom_mealplan = (MealPlan) request.getSession().getAttribute("custom_mealplan");
                Log.wln("custom_mealplan available. isEmpty: "+custom_mealplan.IsEmpty());
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "recipe" - Get recipe keyword and open recipe -----
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("recipe")!=null){
                Log.wdln("To execute CookBook request: parameter=\"recipe\"");
                
                String recipe_keyword = request.getParameter("recipe");

                Log.debug_w("Query meal from database: "+recipe_keyword);
                
                List<Recipe> recipe_results = sql.getEM().createNamedQuery("Recipe.findByKeyword",Recipe.class).setParameter("keyword", recipe_keyword).getResultList();
                
                Log.debug_wln("found "+recipe_results.size()+" results");
                
                Recipe r = recipe_results.get(0);

                if(r!=null){
                    
                    Log.debug_wln("Building RecipeStep List..");
                    
                    String recipe_json_manual = r.getBody();

                        List<RecipeStep> steps = RecipeBuilder.createRecipeList(recipe_json_manual);
                        request.setAttribute("recipe", r);
                        request.setAttribute("steps", steps);
                        request.setAttribute("mealplan", request.getSession().getAttribute("custom_mealplan"));

                    Log.debug_wln("done building recipe list. dispatching recipe..");
                    
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_recipe.jsp");
                    rd.include(request, response);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_notfound.jsp");
                    rd.include(request, response);
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "clear" - Clears the entity manager cache to force re-do all queries (useful when database updated). The code actually kills the mySQL connection and sets up a new one.
            //           There must be more efficient ways to clear the Entity Manager but .clear does not really work (?)
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("clear")!=null){
                Log.wdln("To execute CookBook request: parameter=\"clear\"");
                Log.wd(("Clearing the cache/entity manager..."));
                ///this.sql.getEM().clear();
                //this.sql.getEM().getEntityManagerFactory().getCache().evictAll();
                //this.sql.ClearCache();
                //very lame but only stable solution by now:
                this.sql.getEM().close();
                this.sql = new SQL();
                
                Log.wln("cache/entity manager cleared.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "echo" - Gives back an survive echo
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("echo")!=null){
                Log.wdln("To execute CookBook request: parameter=\"echo\"");
                Log.w(("Echoing..."));
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("Echo Echo...");
                out.close();
                Log.wln("done.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "reindex" - Deletes the current Solr index and re-indexes from current database
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("reindex")!=null){
                Log.wdln("To execute CookBook request: parameter=\"reindex\"");
                Log.wdln(("Reindexing started..."));
                
                //this function at first clears the entire Solr document base. Then creates it from scratch
                this.searchengine.IndexAll();
                
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("Reindexed!");
                out.close();
                
                Log.wdln("Reindexed.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "getallsessionmealplans" - Gets all mealplan session attributes: custom and all saved ones 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("getallsessionmealplans")!=null){
                Log.wdln("To execute CookBook request: parameter=\"getallsessionmealplans\"");
                
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                
                MealPlan custom_mealplan = (MealPlan) request.getSession().getAttribute("custom_mealplan"); 
                
                out.println("<br>-----------------<br>");
                out.println("CUSTOM MEALPLAN (isEmtpy:"+custom_mealplan.IsEmpty()+"):<br>");
                out.println(custom_mealplan);
                out.println("<br>-----------------<br>");
                
                Enumeration attributeNames = request.getSession().getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    MealPlan saved_mealplan = (MealPlan) request.getSession().getAttribute((String)attributeNames.nextElement());
                    out.println("<br>-----------------<br>");
                    out.println("SAVED MEALPLAN ["+saved_mealplan.getKeyword()+"]<br>");
                    out.println(saved_mealplan);
                }
                out.println("<br>-----------------<br>");
                out.close();
                Log.wln("All session mealplans showed.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "heap" - Return current heap used and max heap
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("heap")!=null){
                Log.wdln("To execute CookBook request: parameter=\"heap\"");
                Log.wd("Getting Heap information...");
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                
                //All values returned in byte
                
                /*
                Returns the maximum amount of memory that the Java virtual machine will attempt to use.
                If there is no inherent limit then the value Long.MAX_VALUE will be returned.
                */
                long max = Runtime.getRuntime().maxMemory();
                /*
                Returns the total amount of memory in the Java virtual machine.
                The value returned by this method may vary over time, depending on the host environment.
                */
                long total = Runtime.getRuntime().totalMemory();
                /*
                Returns the amount of free memory in the Java Virtual Machine.
                Calling the gc method may result in increasing the value returned by freeMemory.
                */
                long free = Runtime.getRuntime().freeMemory();
                out.println("Current heap:");
                out.println("Max: "+max+";");
                out.println("Used: "+total+";");
                out.println("Free: "+free+";");
                out.close();
                Log.wln("done.");
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //COMMANDS:
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Get command number
            int command = 0;
            if(request.getParameter("command")!=null){
                command = Integer.parseInt(request.getParameter("command"));
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 1: Recipe selection request 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(command==1){
                Log.wdln("To execute CookBook command 1: Recipe selection request.");
                
                SearchResults searchresult = searchengine.runRequest(request);
                
                Log.debug_wdln("Cookbook: received search results: "+searchresult);
                
                //set the attributes which will be cought by the JSP:
                request.setAttribute("search_results", searchresult);
                request.setAttribute("mealplan", request.getSession().getAttribute("custom_mealplan"));
                
                //Dispatch recipe offerings
                request.getRequestDispatcher("dispatch_recipes_offering.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("dispatch_recipes_offering.jsp");
                rd.include(request, response);
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 2: Menu selection and portions changes 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if(command==2){ 
                Log.wdln("To execute CookBook command 2: Menu selection and portions changes.");
                
                //choose which mealplan shall be updated. Default is custom_mealplan: 
                
                // get unique mealplan keyword:
                String mealplan_keyword = request.getParameter("mealplan_keyword");
                
                Log.debug_wdln("requested mealplan: "+mealplan_keyword);
                MealPlan mealplan_to_change = (MealPlan) request.getSession().getAttribute(mealplan_keyword);
                
                //if portions is not 0, add or update the number of servings
                if(!request.getParameter("portions").equals("0")){
                    
                    Log.debug_wdln("Adding recipe with id: "+request.getParameter("recipeid")+", portions: "+request.getParameter("portions"));
                    
                    int recipeid = Integer.parseInt(request.getParameter("recipeid"));
                                        
                    mealplan_to_change.QuickAdd(recipeid,Integer.parseInt(request.getParameter("portions")));
                    
                    Log.debug_wdln("Recipe added.");

                //if portions is 0, remove the serving from the menu
                }else{
                    Log.debug_wdln("Removing Recipe with id: "+request.getParameter("recipeid"));
                    int recipeid = Integer.parseInt(request.getParameter("recipeid"));
                    mealplan_to_change.QuickRemove(recipeid);
                    Log.debug_wln("Done removing recipe with id: "+request.getParameter("recipeid"));
                }

                //Check if the request needs an ingredients list sent back. This is the case when the user does changes while beeing on the menu site. Then, the ingredients list must be updated with every change:
                if(request.getParameter("request_ingredient_list")!=null){
                    if(!request.getParameter("request_ingredient_list").equals("false")){
                        
                        Log.debug_wdln("Update the MealPlan's ingredient list..");//Update the MealPlan
                        mealplan_to_change.UpdateIngredientAmounts();
                        Log.debug_wdln("Mealplan updated.");
                        
                        Log.debug_wdln("Dispatching ingredient section.");
                        request.setAttribute("mealplan", mealplan_to_change);
                        RequestDispatcher rd = request.getRequestDispatcher("dispatch_ingredients.jsp");
                        rd.include(request, response);
                        
                    }
                }
                
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 3: Receive contact mail address
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if(command==3){
                //Every time a user enters his email address in the page footer, it is logged here. You have to search the server's log file to find people that want to contact you:
                Log.wdln("Contact mail "+request.getParameter("email_address"));
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 4: Save current mealplan
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(command==4){
                Log.wdln("To execute CookBook command 4: Save current mealplan in database.");
                
                try{
                    MealPlan tosave = (MealPlan) request.getSession().getAttribute("custom_mealplan");
                    Log.debug_wdln("Mealplan name to be saved: "+request.getParameter("name"));
                    String temp_keyword = request.getParameter("name").replace(" ","");
                    Log.debug_wdln("Mealplan keyword to be saved: "+request.getParameter("name"));
                    
                    tosave.setName(request.getParameter("name"));
                    tosave.setDescr(request.getParameter("descr"));
                    tosave.setKeyword(temp_keyword);
                    tosave.setImg_urls("na");
                    tosave.setTags("na");
                    
                    tosave.setTransient_sql(this.sql);
                    
                    if(tosave.SavetoDB()==0){
                        Log.wdln("Mealplan saved: "+tosave.getName());
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        out.print("/Menu?m="+tosave.getKeyword());
                        out.close();
                    }else{
                        this.sql.getEM().getTransaction().rollback();
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        out.print("name_already_exists");
                        out.close();
                    }
                }catch(NullPointerException e){
                    Log.edln("in Cookbook: NullPointerException while trying to save mealplan: "+e);
                    Log.edln("in Cookbook: NullPointerException while trying to save mealplan: "+e.getLocalizedMessage());
                }   
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 5: Menu selection request 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(command==5){
                Log.wdln("To execute CookBook comand 5: Menu selection request.");
                
                SearchResults searchresult = searchengine.runRequest(request);
                
                Log.debug_wdln("Cookbook: received search results: "+searchresult);
                request.setAttribute("search_results", searchresult);
                
                Log.debug_wdln("To dispatch offering JSP.");
                RequestDispatcher rd = request.getRequestDispatcher("dispatch_mealplans_offering.jsp");
                rd.include(request, response);
            }
            
        }catch(IOException e){
            Log.edln("in Cookbook: IOException: "+e);
            Log.edln("in Cookbook: IOException: "+e.getMessage());
        }catch(NullPointerException e){
            Log.edln("in Cookbook: NullPointerException: "+e);
            Log.edln("in Cookbook: NullPointerException: "+e.getMessage());
        }catch(SQLException e){
            Log.edln("in Cookbook: SQLException "+e);
            Log.edln("in Cookbook: SQLException "+e.getMessage());
            Log.wd("Re-connecting to database due to error..");
            sql = new SQL();
            Log.wln("done.");
        }catch(Exception e){
            Log.edln("in Cookbook: unknown Exception: "+e);
            Log.edln("in Cookbook: unknown Exception: "+e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Log.wd("/CookBook: received GET...");
        request.setAttribute("sql", this.sql);
        
        //Check if requesting Meallion/Menu or Meallion/CookBook. Check for POST and for GET.
        if(request.getRequestURI().endsWith("/Menu")){
            Log.wln("handing over request to Menu handler");
            this.handler_menu.processRequest(request, response);
        }else{
            Log.wln("Handing over request to CookBook handler");
            processRequest(request, response);
        }
        
    }

    /**
     * 
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Log.wd("/CookBook: received POST...");
        request.setAttribute("sql", this.sql);
        
        //Check if requesting Meallion/Menu or Meallion/CookBook. Check for POST and for GET.
        if(request.getRequestURI().endsWith("/Menu")){
            Log.wln("Handing over request to Menu handler");
            this.handler_menu.processRequest(request, response);
        }else{
            Log.wln("Handing over request to CookBook handler");
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
