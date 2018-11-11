<!-- dispatch_ingredients

Is loaded whenever the Menu pages is loaded.
Is reloaded into the ingredients section whenever the user makes an update regarding portions amounts

CSS: 
all resources are loaded in the dispatch_mealplan.jsp

JS:
none

-->

<%@page import="orm.MealPlanRecipe"%>
<%@page import="features.MealPlanIngredient"%>
<%@page import="orm.MealPlan"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="orm.IngredientRecipe"%>
<%@page import="orm.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="utils.RecipeStep"%>
<%@page import="utils.RecipeBuilder"%>
<%@page import="orm.Recipe"%>
<%@page import="utils.Log"%>

    <% try {
    
        MealPlan ingredient_dispatch_current_mealplan = (MealPlan) request.getAttribute("mealplan");
        if(ingredient_dispatch_current_mealplan==null) Log.wln("dispatch ingredients: current mealplan object in session is NULL!");
        
        Log.wln("number of ingredients in current mealplan: "+ingredient_dispatch_current_mealplan.GetMealPlanIngredients().size());
        
        List<MealPlanIngredient> irs = (List<MealPlanIngredient>) ingredient_dispatch_current_mealplan.GetMealPlanIngredients();

         //get total price
        double price = 0;
        for(MealPlanIngredient current_ir : irs){
                price += current_ir.getIngredient().getPrice()*current_ir.getAmount();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String price_string= "&#x20AC "+df.format(price);

        double avg_portion_price = price/ingredient_dispatch_current_mealplan.GetPortionsCount();
        String avg_portion_price_string = "&#x20AC "+df.format(avg_portion_price);
    %>

    
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <h2>Zutaten</h2>
                <h4><% out.print("Gesamt: "+price_string+", &#216; pro Portion: "+avg_portion_price_string);%></h4>
            </div>
        </div>

        <div class="row">
            <% for(MealPlanIngredient current_ir : irs) { %>
                <div class="ingredient-element-placeholder">
                    <div class="ingredient-element" id="ingredient-<% out.print(current_ir.getIngredient().getId()); %>" data-price="<% out.print(current_ir.getIngredient().getPrice()); %>" data-amount="<% out.print(current_ir.getAmount()); %>">
                        <div class="ingredient-element-name">
                            <b><% out.print(current_ir.getIngredient().getName());%></b>
                        </div>
                        
                            <div class="ingredient-element-amount">
                                <span class="ingredient-amount" ><% out.print(current_ir.getAmountString()); %></span> <% out.print(current_ir.getIngredient().getUnit()); %>
                            </div>
                            
                            <% if(!current_ir.getIngredient().getUnit().equals(current_ir.getIngredient().getFriendly_unit())){ %>
                            <div class="ingredient-element-amount">
                                <span class="ingredient-amount" >~<% out.print(current_ir.getFriendlyAmountString()); %></span> <% out.print(current_ir.getIngredient().getFriendly_unit()); %>
                            </div>
                            <% } else { out.print("<br>"); } %>

                        <div class="ingredient-element-img-placeholder">
                            <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('<% out.print(current_ir.getIngredient().getImgUrl()); %>')"></div>
                        </div>
                        <div class="ingredient-element-price">
                            &#8364; <span class="ingredient-price"><% out.print(current_ir.getIngredient().getPriceString(current_ir.getAmount()));%></span>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>

    <% }catch(Exception e){
        System.out.println(e);
        System.out.println(e.getMessage());
        System.out.println(e.getLocalizedMessage());
    %>

        <%@include file="dispatch_notfound.jsp" %>

    <% } %>