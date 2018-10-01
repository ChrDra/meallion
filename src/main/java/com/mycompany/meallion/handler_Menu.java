/**
Mealplan in session:



 */
package com.mycompany.meallion;

import java.io.IOException;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import orm.MealPlan;
import sql.SQL;
import utils.Log;

/**
 * 
 * Menu Servlet - Understanding Menus in Sessions:
 * 
 * The servlet creates a dispatch_menu.jsp file including a request for the ingredients and a list of the menu recipes
 * 
 * 
 * Every user gets a session with an array of Mealplan objects. Whenever a user access CookBook, the servlet checks, if there is a custom_mealplan MealPlan Object. If there is none, it creates one.
 * Whenever the user enters a pre-created menue, this servlet creates additional MealPlan objects with their keyword as unique name.
 * 
 * ----------------------
 * Menu status:
 * 
 * The dispatch_menu.jsp document has a tag called "mealplan_status", where it saves the data "mealplan_keyword"
 * <div id="mealplan_status" data-mealplan_keyword="custom_mealplan"></div>
 * 
 * This "mealplan_keyword" is either "custom_mealplan", if the user clicked on his own menu. Or it is the keyword of a specific pre-created/saved mealplan. For example:
 * <div id="mealplan_status" data-mealplan_keyword="menu2"></div>
 * 
 * This mealplan_keyword data is important so that the javascript code in dispatch_mealplan.js knows to which menu changes should apply (i.e. a user could have multiple, parallel menues in his session).
 * 
 * 
 * @author Christoph
 */
public class handler_Menu {

    private SQL sql;
    
    public handler_Menu(SQL sql) {
        this.sql = sql;
    }
    
    /**
     * Processes the request to "/Menu?m=[mealplan name]"  or "/Menu"
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setContentType("text/html;charset=UTF-8");
            
            Log.debug_wln("Executing Menu Servlet:");
            Log.debug_wln("Request: "+request);
            Log.debug_wln("Menu Servlet: searching for parameter..");
            
            //Check if the URL paramter "m" exists. If yes, the user accesses a menu, which is already saved in the database:
            if(request.getParameter("m")!=null){
                
                Log.debug_wdln("URL parameter m does exist");
                
                String mealplan_keyword = request.getParameter("m");
                
                Log.debug_wdln("Menu: parameter: "+mealplan_keyword);
                
                List<MealPlan> mealplan_results = sql.getEM().createNamedQuery("MealPlan.findByKeyword",MealPlan.class).setParameter("keyword", mealplan_keyword).setHint(mealplan_keyword, sql).getResultList();
                
                Log.debug_wdln("Menu: found "+mealplan_results.size()+" results");
                
                if(mealplan_results.size()==1){
                
                    MealPlan m = mealplan_results.get(0);
 
                    m.setTransient_sql(this.sql);
                    m.UpdateIngredientAmounts();
                    
                    Log.debug_wln("/Menu: found meal: "+m);
                    
                    if(request.getSession().getAttribute(m.getKeyword())==null){
                        request.getSession().setAttribute(m.getKeyword(),m);
                    }
                    
                    request.getSession().setAttribute(m.getKeyword(), m);

                    request.setAttribute("mealplan", m);
                    request.setAttribute("mealplan_status", "saved");
                    
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_mealplan.jsp");
                    rd.include(request, response);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_no_mealplan.jsp");
                    rd.include(request, response);
                }
                
            // "m" does not exist, i.e. the user accesses /Menu to see his custom mealplan:    
            }else{
                Log.debug_wdln("Menu Servlet: no parameter found. Check custom Menu..");
                
                MealPlan current_mealplan = (MealPlan) request.getSession().getAttribute("custom_mealplan");
                
                if(current_mealplan==null) Log.w("CUSTOMR IS NULL!!");
                
                if((!current_mealplan.IsEmpty())){
                    current_mealplan.UpdateIngredientAmounts();
                    request.setAttribute("mealplan", current_mealplan);
                    request.setAttribute("mealplan_status", "temporary");
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_mealplan.jsp");
                    rd.include(request, response);
                }else{
                    Log.debug_wdln("custom_mealplan is empty");
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_no_mealplan.jsp");
                    rd.include(request, response);
                }
            }
            
            if(request.getParameter("save")!=null){
                MealPlan tosave = (MealPlan) request.getSession().getAttribute("current_mealplan");
                tosave.SavetoDB();
            }
            
            
        }catch(IOException e){
            Log.eln("in Menu: IOException: "+e);
            Log.eln("in Menu: IOException: "+e.getLocalizedMessage());
        }catch(NullPointerException e){
            Log.eln("in Menu: SQLException "+e);
            Log.eln("in Menu: SQLException "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(ServletException e){
            Log.eln("in Menu: ServletException: "+e);
            Log.eln("in Menu: ServletException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(PersistenceException e){
            Log.eln("in Menu: PersistenceException: "+e);
            Log.eln("in Menu: PersistenceException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(Exception e){
            Log.eln("in Menu: unknown Exception: "+e);
            Log.eln("in Menu: unknown Exception: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }
    }
}
