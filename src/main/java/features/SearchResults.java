/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.ArrayList;
import java.util.List;
import orm.MealPlan;
import orm.Recipe;

/**
 *
 * @author Christoph
 */
public class SearchResults {
    private List<Recipe> recipe_results;
    private List<MealPlan> mealplan_results;
    private int meta;
    
    public static final int ERROR = -1;
    public static final int ZERO = 0;
    

    public void SetRecipesOnly(List<Recipe> results, int meta) {
        this.recipe_results = results;
        this.mealplan_results = new ArrayList<MealPlan>();
        this.meta = meta;
    }
    
    public void SetMealPlansOnly(List<MealPlan> results, int meta) {
        this.recipe_results = new ArrayList<Recipe>();
        this.mealplan_results = results;
        this.meta = meta;
    }
    
    public void SetZero() {
        this.recipe_results = new ArrayList<Recipe>();
        this.mealplan_results = new ArrayList<MealPlan>();
        this.meta = 0;
    }
    
    public void SetErr() {
        this.recipe_results = new ArrayList<Recipe>();
        this.mealplan_results = new ArrayList<MealPlan>();
        this.meta = -1;
    }
    
    public boolean isOk(){
        if(this.meta>0)
            return true;
        return false;
    }
    
    public boolean isErr(){
        if(this.meta<0)
            return true;
        return false;
    }
    
    public boolean isZero(){
        if(this.meta==0)
            return true;
        return false;
    }

    public List<Recipe> getRecipe_results() {
        return recipe_results;
    }

    public void setRecipe_results(List<Recipe> recipe_results) {
        this.recipe_results = recipe_results;
    }

    public List<MealPlan> getMealplan_results() {
        return mealplan_results;
    }

    public void setMealplan_results(List<MealPlan> mealplan_results) {
        this.mealplan_results = mealplan_results;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        int recipe_results_count = -1;
        if(this.recipe_results!=null)
            recipe_results_count = this.recipe_results.size();
        int mealplan_results_count = -1;
        if(this.mealplan_results!=null)
            mealplan_results_count = this.mealplan_results.size();
        return "SearchResults{" + "number of recipe_results=" + recipe_results_count + ", number of mealplan_results=" + mealplan_results_count + ", meta=" + this.meta + '}';
    }
         
}
