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
import orm.Recipe;
import sql.SQL;
import utils.Log;

/**
 *
 * @author chris
 */
public class handler_Examples {
    private SQL sql;
    
    public handler_Examples(SQL sql) {
        this.sql = sql;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            
            if(request.getParameter("id")!=null){
                Log.wdln("To execute Examples request: parameter=\"id\"");
                
                String example_id = request.getParameter("id");
                
                Log.wdln("dispatching jsp: "+example_id);
                
                if(example_id!=null){
                    RequestDispatcher rd = request.getRequestDispatcher("/examples/"+example_id+".jsp");
                    rd.include(request, response);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("dispatch_notfound.jsp");
                    rd.include(request, response);
                }
            }
            
            
            }catch(IOException e){
            Log.eln("in Examples_ IOException: "+e);
            Log.eln("in Examples_ IOException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(NullPointerException e){
            Log.eln("in Examples_ SQLException "+e);
            Log.eln("in Examples_ SQLException "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(ServletException e){
            Log.eln("in Examples_ ServletException: "+e);
            Log.eln("in Examples_ ServletException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(PersistenceException e){
            Log.eln("in Examples_ PersistenceException: "+e);
            Log.eln("in Examples_ PersistenceException: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }catch(Exception e){
            Log.eln("in Examples_ unknown Exception: "+e);
            Log.eln("in Examples_ unknown Exception: "+e.getLocalizedMessage());
            RequestDispatcher rd = request.getRequestDispatcher("dispatch_ooops.jsp");
            rd.include(request, response);
        }
    }
}
