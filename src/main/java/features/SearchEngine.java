package features;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import sql.SQL;
import javax.servlet.http.HttpServletRequest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import orm.Recipe;
import utils.Log;

import orm.IngredientRecipe;
import orm.MealPlan;
import orm.MealPlanRecipe;


public class SearchEngine {
    
    //SQL connection object
    private SQL sql;
    
    //Search parameters:
    //time
    private int time;
    //budget
    private double budget;
    //veggie
    private String veggie;
    //vegan
    private String vegan;
    //tags array from free search bar
    private String[] tags;
    
    
    //Current object holding the search results
    private SearchResults result;
    
    //command number of request (1=search recipes, 5=search mealplans)
    private int command;
    
    //defining max number of results
    private int max_results;
    
    //url of Solr
    private String url;
    
    //Solr client object
    private SolrClient client;
    
    /**
     * Constructor
     * @param sql
     * @param url
     * @param max_results
     * @throws IOException 
     */

    public SearchEngine(SQL sql, String url, int max_results) throws IOException {
        this.sql = sql;
        this.url = url;
        this.max_results = max_results;
        this.client = new HttpSolrClient.Builder(url).build();
    }
    
    /**
     * Runs a Http request, i.e. pulls all needed parameters out and prepares these for the actual Solr Request
     * This function runs then MealPlans_Engine_Solr_1 or REcipes_Engine_Solr_1, depending on command number
     * @param request
     * @return 
     */
    
    public SearchResults runRequest(HttpServletRequest request){
        Log.wdln("Search engine: running request..");

        this.time = Integer.parseInt(request.getParameter("time"));
        this.command = Integer.parseInt(request.getParameter("command"));
        this.budget = Double.parseDouble(request.getParameter("budget"));
        this.veggie = request.getParameter("veggie");
        this.vegan = request.getParameter("vegan");
        
        //The tags string usually arrives in this form: "tags=Kartoffel%2CSalz%2CSchinken"
        
        String serialized_tags = request.getParameter("tags");
        
        //kill umlauts
        serialized_tags = serialized_tags.replaceAll("%C3%84","Ä");
        serialized_tags = serialized_tags.replaceAll("%C3%96","Ö");
        serialized_tags = serialized_tags.replaceAll("%C3%9C","Ü");
        serialized_tags = serialized_tags.replaceAll("%C3%A4","ä");
        serialized_tags = serialized_tags.replaceAll("%C3%B6","ö");
        serialized_tags = serialized_tags.replaceAll("%C3%BC","ü");
        serialized_tags = serialized_tags.replaceAll("%C3%9F","ß");
        serialized_tags = serialized_tags.replaceAll("%C3%89","É");
        serialized_tags = serialized_tags.replaceAll("%C3%A9","é");
        serialized_tags = serialized_tags.replaceAll("%C3%88","È");
        serialized_tags = serialized_tags.replaceAll("%C3%A8","è");
        serialized_tags = serialized_tags.replaceAll("%C3%8E","Î");
        serialized_tags = serialized_tags.replaceAll("%C3%AE","î");
                
        int equals_sign_index = serialized_tags.indexOf("=");
        if(equals_sign_index!=-1){
            serialized_tags = serialized_tags.substring(equals_sign_index+1);
        }
        
        if(!serialized_tags.equals("")){
            this.tags = serialized_tags.split("%2C");
        }else{
            this.tags = new String[0];
        }
        
        Log.wln("search total string: "+serialized_tags);
        
        Log.wd("Identified tags: ");
        if(tags.length==0){
            Log.wln("none");
        }else{
            for(String tag : this.tags){
                Log.w(tag+", ");
            }
            Log.ln();
        }
        
        Log.wd("SearchEngine parameters: ");
        Log.w("time: "+this.time+"; ");
        Log.w("budget: "+this.budget+"; ");
        Log.w("veggie: "+this.veggie+"; ");
        Log.wln("vegan: "+this.vegan);
        
        if(command==5){
            return this.MealPlans_Engine_Solr_1();
        }else{
            return this.Recipes_Engine_Solr_1();
        }
    }
    
    /**
     * Runs a recipe search with the currently saved search variables and returns a SearchResults object
     * @return 
     */
    
    private SearchResults Recipes_Engine_Solr_1(){
        try{
            
            //create query string:
            StringBuilder query_string_builder = new StringBuilder();
                        
            if(this.tags.length!=0){
                for(String tag : this.tags){
                    query_string_builder.append("name:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("name:");
                    //add to string and remove whitespaces because they were deleted at indexing 
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                    
                    query_string_builder.append("ingredient:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("ingredient:");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                    
                    query_string_builder.append("tags:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("tags:");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                }
            }else{
                query_string_builder.append("*:*");
            }
            Log.debug_wdln("Solr search string = "+query_string_builder);
            
            SolrQuery query = new SolrQuery();
            query.setQuery(query_string_builder.toString());
            
            //add recipe filter
            Log.debug_wdln("Set recipe filter");
            query.addFilterQuery("type:recipe");
            
            //add budget filter
            Log.debug_wdln("Set budget filter from 0 to "+this.budget);
            query.addFilterQuery("budget:[0.0 TO "+this.budget+"]");
            
            //add time filter
            Log.debug_wdln("Set prep_time filter from 0 to "+this.time);   
            query.addFilterQuery("prep_time:[0 TO "+this.time+"]");
            
            //add veggie filter
            if(this.veggie.equals("true")){
                Log.debug_wdln("Set vegetarian filter");
                query.addFilterQuery("veggie:"+this.veggie);
            }
            
            //add vegan filter
            if(this.veggie.equals("true")){
                Log.debug_wdln("Set vegan filter");
                query.addFilterQuery("vegan:"+this.vegan);
            }
            
            //start at result 0
            query.setStart(0);
            //number of max results
            query.setRows(this.max_results);
            
            query.set("fl", "recipe_id, prep_time, budget, veggie, vegan, short_descr, long_descr, keyword, img_url_small, tags, img_url_large, name, score");
            
            QueryResponse response = client.query(query);
            SolrDocumentList results = response.getResults();
            
            Log.debug_wdln("Number of Solr results: "+results.size());
            
            if(!results.isEmpty()){
            
                ArrayList<Recipe> results_recipes = new ArrayList<>();
                
                for (int i = 0; i < results.size(); i++) {
                    Recipe temp_recipe = new Recipe();
                    
                    temp_recipe.setId(Integer.parseInt(results.get(i).getFirstValue("recipe_id").toString()));
                    temp_recipe.setPrepTime(Integer.parseInt(results.get(i).getFirstValue("prep_time").toString()));
                    temp_recipe.setPrice(Double.parseDouble(results.get(i).getFirstValue("budget").toString()));
                    temp_recipe.setVegetarian(Boolean.parseBoolean(results.get(i).getFirstValue("veggie").toString()));
                    temp_recipe.setVegan(Boolean.parseBoolean(results.get(i).getFirstValue("vegan").toString()));
                    temp_recipe.setShort_descr(results.get(i).getFirstValue("short_descr").toString());
                    temp_recipe.setLong_descr(results.get(i).getFirstValue("long_descr").toString());
                    temp_recipe.setKeyword(results.get(i).getFirstValue("keyword").toString());
                    temp_recipe.setImgUrl_large(results.get(i).getFirstValue("img_url_large").toString());
                    temp_recipe.setImgUrl_small(results.get(i).getFirstValue("img_url_small").toString());
                    temp_recipe.setName(results.get(i).getFirstValue("name").toString());
                    
                    /* Uncomment to log creating a Resipe object
                    Log.wdln("id:"+results.get(i).getFieldValue("id").toString());
                    Log.wdln("prep_time:"+results.get(i).getFirstValue("prep_time"));
                    Log.wdln("budget:"+results.get(i).getFirstValue("budget"));
                    Log.wdln("veggie:"+results.get(i).getFirstValue("veggie"));
                    Log.wdln("vegan:"+results.get(i).getFirstValue("vegan"));
                    Log.wdln("short_descr:"+results.get(i).getFirstValue("short_descr"));
                    Log.wdln("long_descr:"+results.get(i).getFirstValue("long_descr"));
                    Log.wdln("keyword:"+results.get(i).getFirstValue("keyword"));
                    Log.wdln("img_url:"+results.get(i).getFirstValue("img_url"));
                    Log.wdln("name:"+results.get(i).getFirstValue("name"));*/
                    
                    results_recipes.add(temp_recipe);
                }
                //return ok
                SearchResults sr_ok = new SearchResults();
                sr_ok.SetRecipesOnly(results_recipes,results_recipes.size());
                return sr_ok;

            }else{
                Log.debug_wdln("Solr found zero search results.");
                //return zero
                SearchResults sr_zero = new SearchResults();
                sr_zero.SetZero();
                this.result = sr_zero;
                return sr_zero;
            }
                
        }catch(IOException ioe){
            Log.edln("Could not create Solr search query: "+ioe);
        }catch(SolrServerException sse){
            Log.edln("Solr server exception "+sse);
        }
        
        //return error
        SearchResults sr_err = new SearchResults();
        sr_err.SetErr();
        this.result = sr_err;
        Log.wln(this);
        return sr_err;
    }
    
    /**
     * Runs a mealplan search with the currently saved search variables and returns a SearchResults object
     * @return 
     */
    
    private SearchResults MealPlans_Engine_Solr_1(){
        try{
            
            //create query string:
            StringBuilder query_string_builder = new StringBuilder();
                        
            if(this.tags.length!=0){
                for(String tag : this.tags){
                    query_string_builder.append("name:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("name:");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                    
                    query_string_builder.append("ingredient:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("ingredient:");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                    
                    query_string_builder.append("tags:*");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("* OR ");
                    query_string_builder.append("tags:");
                    //add to string and remove whitespaces because they were deleted at indexing
                    query_string_builder.append(tag.replaceAll("\\+", ""));
                    query_string_builder.append("~2 ");
                }
            }else{
                query_string_builder.append("*:*");
            }
            Log.debug_wdln("Solr search string = "+query_string_builder);
            
            SolrQuery query = new SolrQuery();
            query.setQuery(query_string_builder.toString());
            
            //add mealplan filter
            Log.debug_wdln("Set mealplan filter");
            query.addFilterQuery("type:mealplan");
            
            //add budget filter
            Log.debug_wdln("Set budget filter from 0 to "+this.budget);
            query.addFilterQuery("budget:[0.0 TO "+this.budget+"]");
            
            //add veggie filter
            if(this.veggie.equals("true")){
                Log.debug_wdln("Set vegetarian filter");
                query.addFilterQuery("veggie:"+this.veggie);
            }
            
            //add vegan filter
            if(this.veggie.equals("true")){
                Log.debug_wdln("Set vegan filter");
                query.addFilterQuery("vegan:"+this.vegan);
            }
            
            //start at result 0
            query.setStart(0);
            //number of max results
            query.setRows(this.max_results);
            
            query.set("fl", "mealplan_id, budget, veggie, img_urls, vegan, tags, keyword, descr, name, score");
            
            QueryResponse response = client.query(query);
            SolrDocumentList results = response.getResults();
            
            Log.debug_wdln("Number of Solr results: "+results.size());
            
            if(!results.isEmpty()){
            
                ArrayList<MealPlan> results_mealplans = new ArrayList<>();
                
                for (int i = 0; i < results.size(); i++) {
                    Log.wdln("create new temp mealplan:");
                    MealPlan temp_mealplan = new MealPlan(this.sql);
                    
                    Log.debug_wdln("building mealplan from search results: ");
                    Log.debug_wdln("id:"+results.get(i).getFirstValue("mealplan_id").toString());
                    temp_mealplan.setId(Integer.parseInt(results.get(i).getFirstValue("mealplan_id").toString()));
                    Log.debug_wdln("budget: "+results.get(i).getFirstValue("budget"));
                    temp_mealplan.setTransient_price(Double.parseDouble(results.get(i).getFirstValue("budget").toString()));
                    Log.debug_wdln("veggie:"+results.get(i).getFirstValue("veggie"));
                    temp_mealplan.setTransient_vegetarian(Boolean.parseBoolean(results.get(i).getFirstValue("veggie").toString()));
                    Log.debug_wdln("vegan:"+results.get(i).getFirstValue("vegan"));
                    temp_mealplan.setTransient_vegan(Boolean.parseBoolean(results.get(i).getFirstValue("vegan").toString()));
                    Log.debug_wdln("descr: "+results.get(i).getFirstValue("descr"));
                    temp_mealplan.setDescr(results.get(i).getFirstValue("descr").toString());
                    Log.debug_wdln("keyword:"+results.get(i).getFirstValue("keyword"));
                    temp_mealplan.setKeyword(results.get(i).getFirstValue("keyword").toString());
                    Log.debug_wdln("name:"+results.get(i).getFirstValue("name"));
                    temp_mealplan.setName(results.get(i).getFirstValue("name").toString());
                    Log.debug_wdln("img_urls:"+results.get(i).getFirstValue("img_urls"));
                    temp_mealplan.setImg_urls(results.get(i).getFirstValue("img_urls").toString());
                    
                    results_mealplans.add(temp_mealplan);
                    Log.debug_wdln("result mealplan added to results list");
                }
                    Log.wdln("return results size: "+results_mealplans.size());
                //return ok
                SearchResults sr_ok = new SearchResults();
                sr_ok.SetMealPlansOnly(results_mealplans,results_mealplans.size());
                    Log.debug_wdln("return results list");
                return sr_ok;

            }else{
                Log.debug_wdln("Solr found zero search results.");
                //return zero
                SearchResults sr_zero = new SearchResults();
                sr_zero.SetZero();
                this.result = sr_zero;
                return sr_zero;
            }
                
        }catch(IOException ioe){
            Log.edln("Could not create Solr search query: "+ioe);
        }catch(SolrServerException sse){
            Log.edln("Solr server exception "+sse);
        }catch(NullPointerException npe){
            Log.edln("Null exception: "+npe);
        }
        //return error
        SearchResults sr_err = new SearchResults();
        sr_err.SetErr();
        this.result = sr_err;
        Log.wln(this);
        return sr_err;
    }
    
    /**
     * Get latest results, which was created by MealPlans_Engine_Solr_1 or Recipes_Engine_Solr_1
     * @return 
     */
    
    public SearchResults getResult() {
        return result;
    }
    
    /**
     * Takes the entire database (recipes and mealplans) and reindexes it in Solr documents (1 document = 1 recipe or 1 mealplan)
     * @return
     * @throws IOException
     * @throws SolrServerException 
     */
    
    public int IndexAll() throws IOException, SolrServerException{
        try{
            this.client.deleteByQuery("*:*");
            int a = this.IndexMealPlans();
            int b = this.IndexRecipes();
            return a+b;
        }catch(SolrServerException sse){
            Log.edln("Error while creating doc from recipe database: "+sse);
            return -1;
        }
    }
    
    private int IndexMealPlans() throws IOException, SolrServerException{
        
        List<MealPlan> mealplans = this.sql.GetAllMealPlans();
        
        Log.wdln("Solr Indexing: Gather all mealplans from database: "+mealplans.size());
        
        int i=0;
        for(MealPlan m : mealplans){
            SolrInputDocument doc = new SolrInputDocument();
            
            m.setTransient_sql(this.sql);
            m.UpdateIngredientAmounts();
                        
            List<MealPlanIngredient> irs = (List<MealPlanIngredient>) m.GetMealPlanIngredients();
            Log.wdln("count irs: "+irs.size());
            List<MealPlanRecipe> mrs = m.getMealplanrecipe();
            
            //add mealplan_id
            doc.addField("mealplan_id", m.getId());
            Log.wdln("Added mealplan_id to document: "+m.getId());
            //add type
            doc.addField("type", "mealplan");
            Log.wdln("Added type to document: mealplan");
            //add budget
            doc.addField("budget", MealPlan.getAveragePrice(irs,m));
            Log.wdln("Added budget to document: "+MealPlan.getAveragePrice(irs,m));
            //add veggie
            doc.addField("veggie", String.valueOf(m.GetVegetarian()));
            Log.wdln("Added veggie to document: "+m.GetVegetarian());
            //add vegan
            doc.addField("vegan", String.valueOf(m.GetVegan()));
            Log.wdln("Added vegan to document: "+m.GetVegan());
            //add name
            doc.addField("name", m.getName());
            Log.wdln("Added name to document: "+m.getName());
            //add tags
            doc.addField("tags", m.getTags());
            Log.wdln("Added tags to document: "+m.getTags());
            //add descr
            doc.addField("descr", m.getDescr());
            Log.wdln("Added descr to document: "+m.getDescr());
            //add descr
            doc.addField("keyword", m.getKeyword());
            Log.wdln("Added keyword to document: "+m.getKeyword());
            
            //add img_urls: if "na", add recipe images separated by ";"
            if(m.getImg_urls().equals("na")){
                StringBuilder builder = new StringBuilder();
                for(MealPlanRecipe mr : mrs){
                    builder.append(mr.getRecipe().getImgUrl_small()).append(";");
                }
                String result_string;
                if(builder.length()>1){
                    result_string = builder.toString().substring(0, builder.length() - 1);
                }else{
                    result_string = builder.toString();
                }
                doc.addField("img_urls", result_string);
                Log.wdln("Added img_urls to document: "+result_string);
            }else{
                doc.addField("img_urls", m.getImg_urls());
                Log.wdln("Added img_urls to document: "+m.getImg_urls());
            }
            
            //add recipe names
            for(MealPlanRecipe mr : mrs){
                doc.addField("recipe_name", mr.getRecipe().getName().replaceAll(" ", ""));
                Log.wdln("    -"+mr.getRecipe().getName().replaceAll(" ", ""));
                
                //add ingredients
                List<IngredientRecipe> ingredientrecipe = mr.getRecipe().getIngredientRecipe();
                for(IngredientRecipe ir : ingredientrecipe){
                    doc.addField("ingredient", ir.getIngredient().getName().replaceAll(" ", ""));
                    Log.wdln("    -"+ir.getIngredient().getName().replaceAll(" ", ""));
                }   
            }
            
            client.add(doc);
            client.commit();

            Log.wdln("Doc successfully added to Solr.");
            i++;
        }
        return i;
    }
    
    private int IndexRecipes() throws IOException, SolrServerException{

        List<Recipe> recipes = this.sql.GetAllRecipes();

        Log.wdln("Solr Indexing: Gather all recipes from database: "+recipes.size());

        int i=0;
        for(Recipe r : recipes){

            SolrInputDocument doc = new SolrInputDocument();

            //add recipe_id
            doc.addField("recipe_id", r.getId());
            Log.wdln("Added recipe_id to document: "+r.getId());
            //add type
            doc.addField("type", "recipe");
            Log.wdln("Added type to document: recipe");
            //add prep_time
            doc.addField("prep_time", r.getPrepTime());
            Log.wdln("Added prep_time to document: "+r.getPrepTime());
            //add budget
            doc.addField("budget", r.getPrice());
            Log.wdln("Added budget to document: "+r.getPrice());
            //add veggie
            doc.addField("veggie", String.valueOf(r.getVegetarian()));
            Log.wdln("Added veggie to document: "+r.getVegetarian());
            //add vegan
            doc.addField("vegan", String.valueOf(r.getVegan()));
            Log.wdln("Added vegan to document: "+r.getVegan());
            //add short_descr
            doc.addField("short_descr", r.getShort_descr());
            Log.wdln("Added short_descr to document: "+r.getShort_descr());
            //add long_descr
            doc.addField("long_descr", r.getLong_descr());
            Log.wdln("Added long_descr to document: "+r.getLong_descr());
            //add tags
            doc.addField("tags", r.getTags());
            Log.wdln("Added tags to document: "+r.getTags());
            //add img_url_large
            doc.addField("img_url_large", r.getImgUrl_large());
            Log.wdln("Added img_url_large to document: "+r.getImgUrl_large());
            //add img_url_small
            doc.addField("img_url_small", r.getImgUrl_small());
            Log.wdln("Added img_url_small to document: "+r.getImgUrl_small());
            //add name
            doc.addField("name", r.getName());
            Log.wdln("Added name to document: "+r.getName());
            //add keyword
            doc.addField("keyword", r.getKeyword());
            Log.wdln("Added keyword to document: "+r.getKeyword());

            List<IngredientRecipe> ingredientrecipe = r.getIngredientRecipe();
            Log.wdln("Number of Ingredients: "+ingredientrecipe.size());

            //add all ingredients and delete whitespaces
            for(IngredientRecipe ir : ingredientrecipe){
                doc.addField("ingredient", ir.getIngredient().getName().replaceAll(" ", ""));
                Log.wdln("    -"+ir.getIngredient().getName().replaceAll(" ", ""));
            }

            client.add(doc);
            client.commit();

            Log.wdln("Doc successfully added to Solr.");
            i++;
        }
        return i;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder("SearchEngine{" + "time=" + time + ", budget=" + budget + ", veggie=" + veggie + ", vegan=" + vegan + ", tags=" + tags + ", result= ");
        for(Recipe r : this.result.getRecipe_results()){
            strb.append(r.getId()+",");
        }
        strb.delete(strb.length(), strb.length());
        strb.append("}");
        return strb.toString(); 
    }
}