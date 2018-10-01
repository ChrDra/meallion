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
import java.text.DecimalFormat;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import orm.Ingredient;
import orm.MealPlanRecipePK;
import orm.Recipe;
import orm.Recipe;

/**
 *
 * @author Christoph
 */
@Entity(name = "MealPlanRecipe")
@Table(name = "mealplan_recipe")
@XmlRootElement
public class MealPlanRecipe implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    //@MapsId("recipe_id")
    //@JoinColumn(name="recipeId")
    private Recipe recipe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mealplanId")
    //@MapsId("mealplan_id")
    //@JoinColumn(name="mealplanId")
    private MealPlan mealplan;
    
    @EmbeddedId
    protected MealPlanRecipePK mealplanrecipePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "portions")
    private int portions;
    
    public MealPlanRecipe() {
        this.setDefaultPKObject();
    }
    
    public void setDefaultPKObject(){
        this.mealplanrecipePK = new MealPlanRecipePK();
    }
 
    public MealPlanRecipe(Recipe recipe, int portions) {
        this.recipe = recipe;
        this.portions = portions;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getPortions() {
        return portions;
    }

    public MealPlan getMealplan() {
        return mealplan;
    }

    public void setMealplan(MealPlan mealplan) {
        this.mealplan = mealplan;
    }

    public MealPlanRecipePK getMealplanrecipePK() {
        return mealplanrecipePK;
    }

    public void setMealplanrecipePK(MealPlanRecipePK mealplanrecipePK) {
        this.mealplanrecipePK = mealplanrecipePK;
    }
    
    

    public void setPortions(int portions) {
        this.portions = portions;
    }

    @Override
    public String toString() {
        return "MealPlanRecipe{" + "recipe=" + recipe + ", portions=" + portions + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.mealplanrecipePK);
        hash = 11 * hash + this.portions;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        //Two mealplanrecipe objects are identical if the Recipe ID is identical
        MealPlanRecipe mpr_compare = (MealPlanRecipe) obj;
        if(this.getRecipe().getId()==mpr_compare.getRecipe().getId()){
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MealPlanRecipe other = (MealPlanRecipe) obj;
        if (this.portions != other.portions) {
            return false;
        }
        if (!Objects.equals(this.mealplanrecipePK, other.mealplanrecipePK)) {
            return false;
        }
        return false;
    }
 
}