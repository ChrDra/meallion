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
@Embeddable
public class IngredientRecipePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "recipe_id")
    private int recipeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingredient_id")
    private int ingredientId;

    public IngredientRecipePK() {
    }

    public IngredientRecipePK(int recipeId, int ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) recipeId;
        hash += (int) ingredientId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngredientRecipePK)) {
            return false;
        }
        IngredientRecipePK other = (IngredientRecipePK) object;
        if (this.recipeId != other.recipeId) {
            return false;
        }
        if (this.ingredientId != other.ingredientId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "orm.IngredientRecipePK[ recipeId=" + recipeId + ", ingredientId=" + ingredientId + " ]";
    }
    
}
