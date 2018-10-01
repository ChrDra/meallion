package features;

import java.text.DecimalFormat;
import orm.Ingredient;


public class MealPlanIngredient {
    private Ingredient ingredient;
    private double amount;

    public MealPlanIngredient(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getAmountString() {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(this.getAmount()); 
    }
    
    public String getFriendlyAmountString() {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(this.getAmount()/this.ingredient.getFriendly_unit_conv()); 
    }
    
}
