/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import orm.Recipe;
import sql.SQL;
import utils.Log;
import utils.RecipeBuilder;
import utils.RecipeStep;

/**
 *
 * @author chris
 */
public class handler_Embed {
    
    private SQL sql;
    
    public handler_Embed(SQL sql) {
        this.sql = sql;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            
            if(request.getParameter("recipe")!=null){
                Log.wdln("To execute Embed request: parameter=\"recipe\"");
                
                String recipe_keyword = request.getParameter("recipe");

                Log.debug_w("Query meal from database: "+recipe_keyword);
                
                List<Recipe> recipe_results = sql.getEM().createNamedQuery("Recipe.findByKeyword",Recipe.class).setParameter("keyword", recipe_keyword).getResultList();
                
                Log.debug_wln("found "+recipe_results.size()+" results");
                
                Recipe r = recipe_results.get(0);

                if(r!=null){
                    request.setAttribute("recipe", r);
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_interface.jsp");
                    rd.include(request, response);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_notfound.jsp");
                    rd.include(request, response);
                }
            }
            
            if(request.getParameter("menu")!=null){
                
                Log.debug_w("Preparing mealplan..");
                
                MealPlan current_mealplan = (MealPlan) request.getSession().getAttribute("custom_mealplan");
                Log.debug_w("Mealplan prepared");
                
                if(current_mealplan!=null){
                    if(!current_mealplan.IsEmpty()){
                        current_mealplan.UpdateIngredientAmounts();
                        request.setAttribute("mealplan", current_mealplan);
                        request.setAttribute("mealplan_status", "temporary");
                        RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_mealplan.jsp");
                        rd.include(request, response);
                    }else{
                        Log.debug_wdln("custom_mealplan is empty");
                        RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_no_mealplan.jsp");
                        rd.include(request, response);
                    }
                }else{
                    Log.debug_wdln("custom_mealplan is empty");
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_no_mealplan.jsp");
                    rd.include(request, response);
                }
            }
            
            
            }catch(IOException e){
            Log.eln("in Embed: IOException: "+e);
            Log.eln("in Embed: IOException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_ooops.jsp");
            rd.include(request, response);
        }catch(NullPointerException e){
            Log.eln("in Embed: NPEException "+e);
            Log.eln("in Embed: NPEException "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_ooops.jsp");
            rd.include(request, response);
        }catch(ServletException e){
            Log.eln("in Embed: ServletException: "+e);
            Log.eln("in Embed: ServletException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_ooops.jsp");
            rd.include(request, response);
        }catch(PersistenceException e){
            Log.eln("in Embed: PersistenceException: "+e);
            Log.eln("in Embed: PersistenceException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_ooops.jsp");
            rd.include(request, response);
        }catch(Exception e){
            Log.eln("in Embed: unknown Exception: "+e);
            Log.eln("in Embed: unknown Exception: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_embed_ooops.jsp");
            rd.include(request, response);
        }
    }
}
