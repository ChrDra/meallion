/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christoph
 */
@Entity(name = "IngredientRecipe")
@Table(name = "ingredient_recipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngredientRecipe.findAll", query = "SELECT i FROM IngredientRecipe i"),
    @NamedQuery(name = "IngredientRecipe.findByRecipeId", query = "SELECT i FROM IngredientRecipe i WHERE i.ingredientRecipePK.recipeId = :recipeId"),
    @NamedQuery(name = "IngredientRecipe.findByIngredientId", query = "SELECT i FROM IngredientRecipe i WHERE i.ingredientRecipePK.ingredientId = :ingredientId"),
    @NamedQuery(name = "IngredientRecipe.findByAmount", query = "SELECT i FROM IngredientRecipe i WHERE i.amount = :amount"),
    @NamedQuery(name = "IngredientRecipe.sumAmount", query = "SELECT i FROM IngredientRecipe i WHERE i.amount = :amount")})
public class IngredientRecipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IngredientRecipePK ingredientRecipePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private double amount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private Ingredient ingredient;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    public IngredientRecipe() {
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public IngredientRecipe(IngredientRecipePK ingredientRecipePK) {
        this.ingredientRecipePK = ingredientRecipePK;
    }

    public IngredientRecipe(IngredientRecipePK ingredientRecipePK, double amount) {
        this.ingredientRecipePK = ingredientRecipePK;
        this.amount = amount;
    }

    public IngredientRecipe(int recipeId, int ingredientId) {
        this.ingredientRecipePK = new IngredientRecipePK(recipeId, ingredientId);
    }

    public IngredientRecipePK getIngredientRecipePK() {
        return ingredientRecipePK;
    }

    public void setIngredientRecipePK(IngredientRecipePK ingredientRecipePK) {
        this.ingredientRecipePK = ingredientRecipePK;
    }

    public double getAmount() {
        return amount;
    }
    
    public double getFriendlyAmount() {
        return this.amount/this.ingredient.getFriendly_unit_conv();
    }
    
    public String getAmountString() {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(this.getAmount()); 
    }
    
    public String getFriendlyAmountString() {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(this.getFriendlyAmount()); 
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingredientRecipePK != null ? ingredientRecipePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngredientRecipe)) {
            return false;
        }
        IngredientRecipe other = (IngredientRecipe) object;
        if ((this.ingredientRecipePK == null && other.ingredientRecipePK != null) || (this.ingredientRecipePK != null && !this.ingredientRecipePK.equals(other.ingredientRecipePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "orm.IngredientRecipe[ ingredientRecipePK=" + ingredientRecipePK + " ]";
    }
    
}
