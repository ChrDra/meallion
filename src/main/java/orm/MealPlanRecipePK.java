/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Christoph
 */
public class MealPlanRecipePK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "recipe_id")
    private int recipeId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "mealplan_id")
    private int mealplanId;

    public MealPlanRecipePK() {
    }

    public MealPlanRecipePK(int recipeId, int mealplanId) {
        this.recipeId = recipeId;
        this.mealplanId = mealplanId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getMealplanId() {
        return mealplanId;
    }

    public void setMealplanId(int mealplanId) {
        this.mealplanId = mealplanId;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.recipeId;
        hash = 67 * hash + this.mealplanId;
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
        final MealPlanRecipePK other = (MealPlanRecipePK) obj;
        if (this.recipeId != other.recipeId) {
            return false;
        }
        return this.mealplanId == other.mealplanId;
    }
    
    
    
    
    
}
