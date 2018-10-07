package utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * final class to easily send plain text and JSON responses
 * @author chris
 */
public final class HttpResponder {
    
    public static void print(HttpServletResponse response_object, Object response_text) throws IOException {
        response_object.setContentType("text/html");
        PrintWriter out = response_object.getWriter();
        out.print(response_text);
        out.close();
    }
    
    public static void dispatch(HttpServletRequest request_object,HttpServletResponse response_object, String page_file) throws  ServletException,IOException{
        RequestDispatcher rd = request_object.getRequestDispatcher(page_file);
        rd.include(request_object, response_object);
    }
    
    
    
    
}
