package utils;

import org.threeten.bp.LocalDateTime;

/**
 * Logging object for general or debugging logs. In non-debugging mode, comment out the content of the debug fuction (most efficient way)
 * 
 * @author chris
 */

public final class Log {
       
    /**
     * Write with date
     * @param s 
     */
    
    public static void wd(Object s){
        System.out.print(LocalDateTime.now()+": "+s);
    }
    
    /**
     * Write with date, line break
     * @param s 
     */
    
    public static void wdln(Object s){
        System.out.println(LocalDateTime.now()+": "+s);
    }
    
    /**
     * Write error note with date
     * @param s 
     */
    
    public static void ed(Object s){
        System.out.print(LocalDateTime.now()+" (Err): "+s);
    }
    
    /**
     * Write error note with date, line break
     * @param s 
     */
    
    public static void edln(Object s){
        System.out.println(LocalDateTime.now()+" (Err): "+s);
    }
    
    /**
     * Write
     * @param s 
     */
    
    public static void w(Object s){
        System.out.print(s);
    }
    
    /**
     * Write, line break
     * @param s 
     */
    
    public static void wln(Object s){
        System.out.println(s);
    }
    
    /**
     * Write error note
     * @param s 
     */
    
    public static void e(Object s){
        System.out.print("(Err): "+s);
    }
    
    /**
     * Write error note, line break
     * @param s 
     */
    
    public static void eln(Object s){
        System.out.println("(Err): "+s);
    }
    
    /**
     * Write a line break
     */
    
    public static void ln(){
        System.out.println();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Debugging logs. To enable/disable --> uncomment/comment the content of the below 4 functions
    //
    // replace:
    // /*comment line*/
    // with:
    // //comment line
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Write with date. For debugging purposes
     * @param s 
     */
    
    public static void debug_wd(Object s){
        /*comment line*/ System.out.print(LocalDateTime.now()+": "+s);
    }
    
    /**
     * Write with date, line break. For debugging purposes
     * @param s 
     */
    
    public static void debug_wdln(Object s){
        /*comment line*/ System.out.println(LocalDateTime.now()+": "+s);
    }
    
    /**
     * Write. For debugging purposes
     * @param s 
     */
    
    public static void debug_w(Object s){
        /*comment line*/ System.out.print(s);
    }
    
    /**
     * Write, line break. For debugging purposes
     * @param s 
     */
    
    public static void debug_wln(Object s){
        /*comment line*/ System.out.println(s);
    }
    
    
}