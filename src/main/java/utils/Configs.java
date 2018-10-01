/*

Config file should be saved in:

Word until first space is attribute, the rest is value
File layout:
-----

// <-- Comment
attribute value
attribute value

-----

*/

package utils;

import java.io.*;
import java.util.ArrayList;

public class Configs {
    private ArrayList<String> param;
    private ArrayList<String> value;
    private BufferedReader br;
    
    
    public Configs(String filename) throws FileNotFoundException, IOException {
        
        param = new ArrayList<>();
        value = new ArrayList<>();
        
        br = new BufferedReader(new FileReader(filename));

        String cur;
        while ((cur = br.readLine()) != null) {
            if (!cur.startsWith("//")) {
                
                int index = cur.indexOf(" ");
                if(index!=-1){
                    param.add(cur.substring(0,index));
                    value.add(cur.substring(index+1));
                }else{
                    param.add(cur);
                    value.add("");
                }
            }
        }
    }
    
    public int FetchInt(String name) throws NumberFormatException, IOException {
        for(int i=0;i<param.size();i++){
            if(param.get(i).equals(name)){
                return Integer.parseInt(value.get(i));
            }
        }
        throw new IOException("Config fetcher: no value found");
    }
    
    public boolean FetchBoolean(String name) throws NumberFormatException, IOException {
        for(int i=0;i<param.size();i++){
            if(param.get(i).equals(name)){
                return Boolean.parseBoolean(value.get(i));
            }
        }
        throw new IOException("Config fetcher: no value found");
    }
    
    public String FetchString(String name) throws IOException {
        for(int i=0;i<param.size();i++){
            if(param.get(i).equals(name)){
                return value.get(i);
            }
        }
        throw new IOException("Config fetcher: no value found");
    }
    
    public Double FetchDouble(String name)throws NumberFormatException, IOException {
        for(int i=0;i<param.size();i++){
            if(param.get(i).equals(name)){
                return Double.parseDouble(value.get(i));
            }
        }
        throw new IOException("Config fetcher: no value found");
    }
    
    public void CloseFile() throws IOException {
        br.close();
    }
    
    
}
