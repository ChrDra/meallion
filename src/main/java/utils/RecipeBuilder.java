package utils;

import java.util.List;
import javax.json.*;
import orm.Recipe;
import utils.RecipeStep;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class RecipeBuilder {

    public static List<RecipeStep> createRecipeList(String dom) throws Exception{    
        Document d = RecipeBuilder.convertStringToDocument(dom);
        NodeList l = d.getElementsByTagName("step");

        ArrayList<RecipeStep> result = new ArrayList<RecipeStep>();;
        
        Log.wln("NodeList size= "+l.getLength());
        
        for(int i=0;i<l.getLength();i++){
            Log.wln("i: "+i);
            Node n = l.item(i); // 1 step
            Element e = (Element) n; //cast step to element
            NodeList img_node = e.getElementsByTagName("img"); //List of img in one step. Size = 1
            Log.wln("SAVE IN RECIPE LIST: "+e.getElementsByTagName("img"));
            Element img_element = (Element) img_node.item(0);
            NodeList txt_node = e.getElementsByTagName("txt"); //List of txt in one step. Size = 1
            Log.wln("SAVE IN RECIPE LIST: "+e.getElementsByTagName("txt"));
            Element txt_element = (Element) txt_node.item(0);
            Log.wln("--> "+img_element.getTextContent()+" ; "+txt_element.getTextContent());
            RecipeStep step = new RecipeStep(img_element.getTextContent(),txt_element.getTextContent());
            Log.wln("before add to result");
            result.add(step);
            Log.wln("end of loop i");
        }
        return result;

    }
    
    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
}
