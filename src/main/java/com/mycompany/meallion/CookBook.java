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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import orm.Ingredient;
import orm.Recipe; 
import sql.SQL; 
import utils.Configs;
import utils.HttpResponder;
import utils.Log;
import utils.RecipeBuilder;
import utils.RecipeStep;

/**
 * Main servlet handling all requests. Either CookBook is answering itself or forwards to /Menu Servlet, if the request url ends with /Menu.
 * /Menu has no own Servlet because it would have to create another SQL connection object. More than one SQL Connection object could mess-up the persistance entity manager.
 * @author chris
 */

@WebServlet(name = "CookBook", urlPatterns = {"/CookBook","/Menu","/embed/*","/Examples"})
public class CookBook extends HttpServlet{  

    //the SQL connection object
    private SQL sql;
        
    private handler_Menu handler_menu;
    private handler_embed handler_embed;
    private handler_Examples handler_examples;
    
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
            this.handler_embed = new handler_embed(this.sql);
            this.handler_examples = new handler_Examples(this.sql); 
            
            // Set config parameters:
            
            Configs configs = new Configs(config_fullPath);
            String solr_url = configs.FetchString("solr_url");
            int solr_max_results = configs.FetchInt("solr_max_results");
            
            Log.wdln("Meallion config: solr_url="+solr_url);
            Log.wdln("Meallion config: solr_max_results="+solr_max_results);
            
            // End setting config parameters 
            
            this.searchengine = new SearchEngine(this.sql,solr_url,solr_max_results);
            
            Log.wdln("Meallion config: success.");
            
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
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
            response.addHeader("Access-Control-Max-Age", "1728000");
            
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
            //PARAMETER: "embed_button" - Returns a button, which adds 1x the ingredients of recipe "keyword" to the Menu -----
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("embed_button")!=null){
                Log.wdln("To execute CookBook request: parameter=\"embed_button\"");
                
                int recipeid = Integer.valueOf(request.getParameter("recipeid"));
                int portions = Integer.valueOf(request.getParameter("portions"));
                
                int[] id_portions_set = new int[2];
                id_portions_set[0] = recipeid;
                id_portions_set[1] = portions;

                Log.debug_w("Query meal from database: "+recipeid);
                
                request.setAttribute("id_portions_set", id_portions_set);

                RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_button.jsp");
                rd.include(request, response);
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "clear" - Clears the entity manager cache to force re-do all queries (useful when database updated). The code actually kills the mySQL connection and sets up a new one.
            //           There must be more efficient ways to clear the Entity Manager but .clear does not really work (?)
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("clear")!=null){
                Log.wdln("To execute CookBook request: parameter=\"clear\"");
                Log.wd(("Clearing the cache/entity manager..."));
                
                /**
                * This does not seem to work:
                * 
                * this.sql.getEM().clear();
                * this.sql.getEM().getEntityManagerFactory().getCache().evictAll();
                * this.sql.ClearCache();
                * 
                */
                
                //very lame but only stable solution by now:
                this.sql.getEM().close();
                this.sql = new SQL();
                Log.wln("cache/entity manager cleared.");
                HttpResponder.print(response, "succcess");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "echo" - Gives back an survive echo
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("echo")!=null){
                Log.wdln("To execute CookBook request: parameter=\"echo\"");
                Log.w(("Echoing..."));
                HttpResponder.print(response, "echo echo...");
                Log.wln("done.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "getingredient" - Gives back an ingredient by id 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("getingredient")!=null){
                Log.wdln("To execute CookBook request: parameter=\"getingredient\"");
                
                String ingredient_id_str = request.getParameter("getingredient");
                int ingredient_id = Integer.parseInt(ingredient_id_str);
                
                List<Ingredient> ingredient_results = sql.getEM().createNamedQuery("Ingredient.findById",Ingredient.class).setParameter("id", ingredient_id).getResultList();
                
                response.setContentType("text/html");
                HttpResponder.print(response, ingredient_results.get(0));
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
                
                HttpResponder.print(response, "reindexed!");
                Log.wdln("Reindexed.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "getallsessionmealplans" - Gets all mealplan session attributes: custom and all saved ones 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("getallsessionmealplans")!=null){
                Log.wdln("To execute CookBook request: parameter=\"getallsessionmealplans\"");
                
                StringBuilder response_string = new StringBuilder();
                
                MealPlan custom_mealplan = (MealPlan) request.getSession().getAttribute("custom_mealplan"); 
                
                response_string.append("CUSTOM_MEALPAN (isEmtpy: ").append(custom_mealplan.IsEmpty()).append(")<br>");
                response_string.append("---------------").append("<br>");
                response_string.append(custom_mealplan);
                response_string.append("---------------").append("<br>");
                
                Enumeration attributeNames = request.getSession().getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    MealPlan saved_mealplan = (MealPlan) request.getSession().getAttribute((String)attributeNames.nextElement());
                    
                    response_string.append("MEALPAN ")
                            .append(saved_mealplan.getKeyword())
                            .append("").append("(isEmtpy: ")
                            .append(saved_mealplan.IsEmpty()).append(")<br>");
                    response_string.append("---------------").append("<br>");
                    response_string.append(saved_mealplan);
                }
                response_string.append("---------------").append("<br>");
                HttpResponder.print(response, response_string);
                Log.wln("All session mealplans showed.");
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //PARAMETER: "heap" - Return current heap used and max heap
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(request.getParameter("heap")!=null){
                Log.wdln("To execute CookBook request: parameter=\"heap\"");
                Log.wd("Getting Heap information...");
                
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
                
                StringBuilder response_string = new StringBuilder(); 
                response_string.append("Current heap:").append("<br>");
                response_string.append("Max: "+max+";").append("<br>");
                response_string.append("Used: "+total+";").append("<br>");
                response_string.append("Free: "+free+";").append("<br>");
                
                HttpResponder.print(response, response_string);
                
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
                HttpResponder.dispatch(request, response, "dispatch_recipes_offering.jsp");
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 2: portions changes in a menu
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
                        HttpResponder.print(response,"name_already_exists");
                    }
                }catch(NullPointerException e){
                    Log.edln("in Cookbook: NullPointerException while trying to save mealplan: "+e);
                    Log.edln("in Cookbook: NullPointerException while trying to save mealplan: "+e.getLocalizedMessage());
                    HttpResponder.print(response,"error");
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
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Command 6: Recipe ingredients list request 
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if(command==6){ 
                Log.wdln("To execute CookBook command 6: Recipe ingredients list request.");
                
                String recipe_keyword = request.getParameter("keyword"); 
                Log.debug_w("Query meal from database: "+recipe_keyword);
                
                List<Recipe> recipe_results = sql.getEM().createNamedQuery("Recipe.findByKeyword",Recipe.class).setParameter("keyword", recipe_keyword).getResultList();
                Log.debug_wln("found "+recipe_results.size()+" results");
                
                Recipe r = recipe_results.get(0);
                
                request.setAttribute("recipe", r);

                if(r!=null){
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_ingredients_cart.jsp");
                    rd.include(request, response);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_notfound.jsp");
                    rd.include(request, response);
                }
            }
            
        }catch(IOException e){
            Log.edln("in Cookbook: IOException: "+e);
            Log.edln("in Cookbook: IOException: "+e.getMessage());
            HttpResponder.print(response,"error");
        }catch(NullPointerException e){
            Log.edln("in Cookbook: NullPointerException: "+e);
            Log.edln("in Cookbook: NullPointerException: "+e.getMessage());
            HttpResponder.print(response,"error");
        }catch(SQLException e){
            Log.edln("in Cookbook: SQLException "+e);
            Log.edln("in Cookbook: SQLException "+e.getMessage()); 
            HttpResponder.print(response,"error");
            Log.wd("Re-connecting to database due to error..");
            sql = new SQL();
            Log.wln("done.");
        }catch(Exception e){
            Log.edln("in Cookbook: unknown Exception: "+e);
            Log.edln("in Cookbook: unknown Exception: "+e.getMessage());
            HttpResponder.print(response,"error");
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
        }else if(request.getRequestURI().endsWith("/embed")){
            Log.wln("Handing over request to Embed handler");
            this.handler_embed.processRequest(request, response);
        }else if(request.getRequestURI().endsWith("/Examples")){
            Log.wln("Handing over request to Examples handler");
            this.handler_examples.processRequest(request, response);    
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
        }else if(request.getRequestURI().endsWith("/embed")){
            Log.wln("Handing over request to Embed handler");
            this.handler_embed.processRequest(request, response);
        }else if(request.getRequestURI().endsWith("/Examples")){
            Log.wln("Handing over request to Examples handler");
            this.handler_examples.processRequest(request, response);
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
