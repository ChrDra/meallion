package orm;

import features.MealPlanIngredient;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.validation.ConstraintViolationException;
import sql.SQL;
import utils.Log;

/**
 * Stores a full mealplan either taken from the database or created from scratch. The object contains some transient attributes, i.e. these won't be loaded from the database and have to be set within the code:
 * The transient attributes are:
 * - SQL object
 * - ingredient_amount: a hashset with all ingredients needed
 * - vegetarian boolean value
 * - vegan boolean value
 * - price
 * 
 * When recipes are added or deleted, the ingredients list is not updated. This has to be done separately because it consumes a lot of CPU.
 * 
 * @author chris
 */

@Entity(name = "MealPlan")
@Table(name = "mealplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MealPlan.findAll", query = "SELECT m FROM MealPlan m"),
    @NamedQuery(name = "MealPlan.findById", query = "SELECT m FROM MealPlan m WHERE m.id = :id"),
    @NamedQuery(name = "MealPlan.findByKeyword", query = "SELECT m FROM MealPlan m WHERE m.keyword = :keyword"),
    @NamedQuery(name = "MealPlan.findByName", query = "SELECT m FROM MealPlan m WHERE m.name = :name")})

public class MealPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descr")
    String descr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "keyword")
    String keyword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tags")
    String tags;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "img_urls")
    String img_urls;
    @OneToMany(
        mappedBy = "mealplan", 
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<MealPlanRecipe> mealplanrecipe = new ArrayList<>();
    
    @Transient
    HashMap<Integer, Double> transient_ingredient_amount;
    
    @Transient
    private boolean transient_vegetarian;
    
    @Transient
    private boolean transient_vegan;
    
    @Transient
    private double transient_price;
    
    @Transient
    SQL transient_sql;
    
    /**
     * Default constructor necessary for the JPA entity manager
     */
    
    public MealPlan() {
        this.transient_ingredient_amount = new HashMap<>();
        this.name = "mealplan";
        this.keyword = "keyword";
        this.descr = ""; 
    }
    
    /**
     * Constructor for creating a new mealplan
     */
    
    public MealPlan(SQL sql) {
        this.transient_sql = sql;
        this.transient_ingredient_amount = new HashMap<>();
        this.name = "mealplan";
        this.keyword = "keyword";
        this.descr = "";
    }
    
    /**
     * 
     * @return 
     */

    public Integer getId() {
        return id;
    }
    
    /**
     * 
     * @param id 
     */

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * 
     * @return 
     */

    public String getDescr() {
        return descr;
    }
    
    /**
     * 
     * @param descr 
     */

    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    /**
     * 
     * @return 
     */

    public String getTags() {
        return tags;
    }
    
    /**
     * 
     * @param tags 
     */

    public void setTags(String tags) {
        this.tags = tags;
    }
    
    /**
     * Checks if the mealplan is empty, i.e. does not contain recipes
     * @return 
     */
       
    public boolean IsEmpty(){
        Log.wdln(this.mealplanrecipe.isEmpty());
        Log.wdln("Checking if mealplan is empty: Mealplan has "+this.mealplanrecipe.size()+" recipe entries");
        return this.mealplanrecipe.isEmpty();
    }
    
    /**
     * Adds a recipe to the Mealplan
     * @param id the id of the recipes
     * @param portions the amount of portions to be added
     * @return amount of portions added or -1 for an error (e.g. the sql attribute of the object is null)
     */
    
    public int QuickAdd(int id,int portions){
        
        for(MealPlanRecipe mpr : this.mealplanrecipe){
            if(mpr.getRecipe().getId()==id){
                mpr.setPortions(portions);
                Log.debug_wdln("Recipe exists already Mealplan. Servings portions updated.\nCurrent Mealplan: "+this);
                return portions;
            }
        }
        
        if(this.transient_sql==null){
            Log.edln("mealplan quickadd: sql object in mealplan object is null.");
            return -1;
        }

        Log.debug_wd("mealplan quickadd: trying to get recipe id from DB..");
        Recipe temp = this.transient_sql.getEM().getReference(Recipe.class,id);
        Log.debug_wdln("received recipe id from DB.");
        this.mealplanrecipe.add(new MealPlanRecipe(temp,portions));
        Log.debug_wln("Recipe added.\nCurrent Mealplan: "+this);
        return portions;
    }
    
    /**
     * Removes a recipe from the Mealplan
     * @param id
     * @return the object's MealPlanRecipe object, in which the recipe was stored, or null if the recipe was not found
     */

    public MealPlanRecipe QuickRemove(int id){
        Log.debug_wln("try to remove recipe from current mealplan..");
        
        for(MealPlanRecipe mpr : this.mealplanrecipe){
            Log.debug_w("quickremove: search id..");
            
            if(mpr.getRecipe().getId()==id){
                Log.debug_w("quickremove: found, remove #"+mpr.getRecipe().getId()+"...");
                this.mealplanrecipe.remove(mpr);
                Log.debug_wln("quickremove: removed");
                return mpr;
            }
            
        }
        return null;
    }
    
    /**
     * Loops trough all servings in the mealplan and calculates the total mealplan price
     * @param irs
     * @return the total price of the mealplan as double in the x.xx format in Euros
     */
    
    public static double getTotalPrice(List<MealPlanIngredient> irs){
        double price = 0;
        for(MealPlanIngredient current_ir : irs){
                price += current_ir.getIngredient().getPrice()*current_ir.getAmount();
        }
        return price;
    }
    
    /**
     * Loops trough all servings in the mealplan and calculates the average mealplan price
     * @param irs
     * @param m
     * @return 
     */
    
    public static double getAveragePrice(List<MealPlanIngredient> irs, MealPlan m){
        double price = 0;
        for(MealPlanIngredient current_ir : irs){
            price += current_ir.getIngredient().getPrice()*current_ir.getAmount();
        }
        return price/m.GetPortionsCount();
    }
    
    /**
     * get the transient SQL attribute
     * @return 
     */
    
    public SQL getTransient_sql() {
        return this.transient_sql;
    }
    
    /**
     * Sets the transient SQL attribute. This should be done after taking a mealplan object from the database to process it further
     * @param sql 
     */
    
    public void setTransient_sql(SQL sql) {
        this.transient_sql = sql;
    }
    
    /**
     * 
     * @return 
     */

    public String getKeyword() {
        return keyword;
    }
    
    /**
     * 
     * @param keyword 
     */
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * 
     * @return 
     */

    public List<MealPlanRecipe> getMealplanrecipe() {
        return mealplanrecipe;
    }
    
    /**
     * 
     * @param mealplanrecipe 
     */

    public void setMealplanrecipe(List<MealPlanRecipe> mealplanrecipe) {
        this.mealplanrecipe = mealplanrecipe;
    }
    
    /**
     * uploads the mealplan to the database.
     * @return 0 if success. -1 if the object already exits and no upload is possible
     * @throws PersistenceException 
     */
       
    public int SavetoDB() throws PersistenceException{
        try{
            for(MealPlanRecipe mpr : this.GetMealPlanRecipes()){
                mpr.setMealplan(this);
                mpr.setDefaultPKObject();
            }
            this.transient_sql.getEM().getTransaction().begin();
            this.transient_sql.getEM().merge(this);
            this.transient_sql.getEM().getTransaction().commit();

        }catch(ConstraintViolationException cve){
            Log.eln("While saving mealplan: "+cve);
            Log.eln("While saving mealplan: "+cve.getLocalizedMessage());
            return -1;
        }
        return 0;
    }
    
    /**
     * 
     * @return 
     */
      
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name 
     */

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */

    public String getImg_urls() {
        return img_urls;
    }
    
    /**
     * 
     * @param img_urls 
     */

    public void setImg_urls(String img_urls) {
        this.img_urls = img_urls;
    }
    
    /**
     * 
     * @return 
     */
    
    public List<MealPlanRecipe> GetMealPlanRecipes() {
        return mealplanrecipe;
    }
    
    /**
     * 
     * @return 
     */
    
    private List<Recipe> GetAllRecipes(){
        List<Recipe> temp = new ArrayList<>();
        Log.w("Looping tru all MealPlanRecipes and collect Recipes...");
        for(MealPlanRecipe mpr : this.mealplanrecipe){
            Log.wln("get recipe: "+mpr.getRecipe());
            temp.add(mpr.getRecipe());
        }
        Log.wln("done.");
        return temp;
    }
    
    /**
     * 
     * @param rlist
     * @param id
     * @return 
     */
    
    private Recipe GetRecipeByID(List<Recipe> rlist, int id){
        for(Recipe r : rlist){
            if(r.getId()==id)
                return r;
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    
    public boolean GetVegetarian(){
        for(Recipe r : this.GetAllRecipes()){
            if(!r.getVegetarian()) return false;
        }
        return true;
    }
    
    /**
     * 
     * @return 
     */
    
    public boolean GetVegan(){
        for(Recipe r : this.GetAllRecipes()){
            if(!r.getVegan()) return false;
        }
        return true;
    }
    
    /**
     * Gets the list of all ingredients in the mealplan and the corresponding amount. The MealPlanIngredient object contains one Ingredient object and an amount number
     * @return 
     */

    public List<MealPlanIngredient> GetMealPlanIngredients(){
        List<MealPlanIngredient> result = new ArrayList<>();
        
        for (Map.Entry<Integer, Double> entry : this.transient_ingredient_amount.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            
            result.add(new MealPlanIngredient(this.transient_sql.getEM().getReference(Ingredient.class, key),value));
            
        }
        return result;
    }
    
    /**
     * Returns one MealPlanRecipe by ID
     * @param mprl
     * @param id
     * @return 
     */
    
    private MealPlanRecipe GetMealPlanRecipeByRecipeID(List<MealPlanRecipe> mprl, int id){
        for(MealPlanRecipe mpr : mprl){
            if(mpr.getRecipe().getId()==id)
                return mpr;
        }
        return null;
    }
    
    /**
     * 
     * @return true if the mealplan is vegetarian (attention, this field is not created when downloading from database!)
     */

    public boolean isTransient_vegetarian() {
        return transient_vegetarian;
    }
    
    /**
     * 
     * @param transient_vegetarian 
     */

    public void setTransient_vegetarian(boolean transient_vegetarian) {
        this.transient_vegetarian = transient_vegetarian;
    }
    
    /**
     * 
     * @return true if the mealplan is vegan (attention, this field is not created when downloading from database!)
     */

    public boolean isTransient_vegan() {
        return transient_vegan;
    }
    
    /**
     * 
     * @param transient_vegan 
     */

    public void setTransient_vegan(boolean transient_vegan) {
        this.transient_vegan = transient_vegan;
    }
    
    /**
     * 
     * @return price of the mealplan (attention, this field is not created when downloading from database!)
     */

    public double getTransient_price() {
        return transient_price;
    }
    
    /**
     * 
     * @return price string in the format of "€ 0.00" of the mealplan (attention, this field is not created when downloading from database!)
     */

    public String GetTransientPriceString(){
        DecimalFormat df = new DecimalFormat("0.00");
        return "&#x20AC "+df.format(this.getTransient_price()); 
    }
    
    /**
     * 
     * @param transient_price 
     */
    
    public void setTransient_price(double transient_price) {
        this.transient_price = transient_price;
    }
    
    /**
     * Updates the IngredientAmount list of the mealplan. This function is fairly CPU and SQL intensive and should therefore only be done if needed, i.e. when uses requests ingredient list
     */
    
    public void UpdateIngredientAmounts(){
        try{
            Log.debug_w("Updating ingredient amounts in mealplan:");
            
            Log.debug_w("clearing ingredient_amount hashset..");
            this.transient_ingredient_amount.clear();
            Log.debug_wln("done.");

            Log.debug_w("Get IngredientAmount table from database..");
            List<Object[]> ingredient_amounts = this.GetIngredientAmounts();
            Log.debug_wln("done.");

            Log.debug_wln("Save IngredientAmounts in ingredient_amount hashset..");
            ingredient_amounts.stream().forEach((line) -> {
                
                int ingredient_id = (int) line[1];
                double amount = (double) line[2] * this.GetMealPlanRecipeByRecipeID(this.mealplanrecipe,(int)line[0]).getPortions();

                Log.debug_wln("self-merge IngredientAmount table: ingredient id: "+line[0]+", recipe id: "+line[1]+", amount: "+line[2]+", portions: "+this.GetMealPlanRecipeByRecipeID(this.mealplanrecipe,(int)line[0]).getPortions());

                this.transient_ingredient_amount.merge(ingredient_id, amount, (v1, v2) -> v1 + v2);
            });
            Log.debug_wln("done.");
            
        }catch(Exception e){
            Log.eln(e);
            Log.eln(e.getMessage());
        }
    }
     
    
    /**
     * Internal function to get the IngredientsAmount table from database:
     * First extracts a list of all Recipes. Then, passing them to the SELECT QUERY to get the IngredientRecipe Table from DB
     *
     *  Returns a List of 3 objects:
     *  0. object = Recipe Id
     *  1. object = Ingredient Id
     *  2. object = Corresponding Amount
     * @return 
     */
    
    private List<Object[]> GetIngredientAmounts(){
        try{
            Log.debug_w("Select all IngredientRecipes..");
            
            Query q = this.transient_sql.getEM().createQuery("SELECT i.recipe.id, i.ingredient.id, i.amount FROM IngredientRecipe i WHERE i.recipe IN :recipes");
            q.setParameter("recipes",  this.GetAllRecipes());
            
            Log.debug_wln("Raw ingredient amount list:");
            List results_list = q.getResultList();
            Iterator it = results_list.iterator( );
            while(it.hasNext()){ 
                Object[] result = (Object[])it.next();
                Log.debug_wln("recipeID: "+result[0]+"; ingredientID: "+result[1]+"; amount: "+result[2]);
            }
            return results_list;
        }catch(Exception e){
            Log.eln("Cannot receive IngredientAmounts. "+e);
            Log.eln(e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @return Returns total number of portions in the mealplan
     */
    
    public double GetPortionsCount(){
        int result = 0;
        for (MealPlanRecipe mpr : this.mealplanrecipe){
                result +=mpr.getPortions();
        }
        return result;
    }
    
    /**
     * Checks if a recipe if part of the mealplan
     * @param r
     * @return true if yes, false if no
     */
    
    public boolean Contains(Recipe r){
        Log.debug_wd("In mealplan.Contains, loop trough all mealplan recipes to find recipe #"+r.getId()+": ");
        for(MealPlanRecipe mpr : this.mealplanrecipe){
            if(mpr.getRecipe().equals(r)){
                Log.wln("found recipe!");
                return true;
            }
        }
    return false;
    }
    
    /**
     * if a recipe if part of the mealplan
     * @param id
     * @return true if yes, false if no
     */
    
    public boolean Contains(int id){
            for(MealPlanRecipe mpr : this.mealplanrecipe){
                if(mpr.getRecipe().getId()==id){
                    return true;
                }
            }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MealPlan other = (MealPlan) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.transient_ingredient_amount, other.transient_ingredient_amount)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("MealPlan{ingredient_amount=" + transient_ingredient_amount + ", id=" + id + ", name=" + name + "}\n");
        for(MealPlanRecipe mpr : this.mealplanrecipe){
            s.append("mpr#"+mpr.getRecipe().getId()+", portions: "+mpr.getPortions()+"\n");
        }
        return s.toString();
    }

    
     
}