    <%@page import="utils.Log"%>
<!-- dispatch_recipe

CookBook Servlet dispatches this jsp sothat URL is nice

CSS:
dispatch_recipe.css

JS:
meallion.js
script is needed in here

-->

<%@page import="orm.MealPlan"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="orm.IngredientRecipe"%>
<%@page import="orm.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="utils.RecipeStep"%>
<%@page import="utils.RecipeBuilder"%>
<%@page import="orm.Recipe"%>

<% try {

    Recipe r = (Recipe) request.getAttribute("recipe");
    List<RecipeStep> steps = (List<RecipeStep>) request.getAttribute("steps");
    List<IngredientRecipe> ir = r.getIngredientRecipe();
    int i=1;

    String price_string= r.GetPriceString();

    MealPlan current_mealplan = (MealPlan) request.getAttribute("mealplan");

%>

<!DOCTYPE html>
<html lang="en">
	<head>
            <!--standard head tags like title and meta data: -->
            <%@include file="standard_head.jsp"%>
            
            <!--additional head tags -->
            <link href="css/dispatch_recipe.css" rel="stylesheet">
            <link href="css/dispatch_recipe.css" rel="stylesheet">
            <link href="css/dispatch_embed_interface.css" rel="stylesheet">
                        
            <meta property="og:title" content="<% out.print(r.getName()); %>" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="<% out.print("/meals-v1/CookBook?recipe="+r.getKeyword()); %>" />
            <meta property="og:image" content="<% out.print(r.getImgUrl_small()); %>" />	
	</head>
	
        <body>
            <nav class="navbar navbar-fixed-top" role="navigation">
            <div class="row embed-row">  
                <div class="col col-lg-2 col-md-2 col-sm-2 col-xs-12 embed-logo">
                    <img class="meallion_logo" src="https://www.meallion.de//images/elements/Meallion_logo.PNG">
                </div>
                <div class="col col-lg-4 col-md-4 col-sm-4 col-xs-12 embed-feature">
                    <div class="row">
                        <div class="col-md-8 col-sm-12 col-xs-6">
                            <span class="embed-label">W&auml;hle die Anzahl der Portionen:</span>
                        </div>
                        <div class="col-md-3 col-sm-12 col-xs-6 col-md-offset-1 col-sm-offset-3">
                            <div id="amount_picker" class="input-group orange-border no-picker">
                                <span class="input-group-btn">
                                    <button type="button" class="quantity-left-minus btn btn-number"  data-type="minus" data-field="">
                                        <span class="glyphicon glyphicon-minus"></span>
                                    </button>
                                </span>
                                <input type="text" id="quantity" name="quantity" class="form-control input-number" value="10" min="1" max="100">
                                <span class="input-group-btn">
                                    <button type="button" class="quantity-right-plus btn btn-number" data-type="plus" data-field="">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-3 col-sm-3 col-xs-6 embed-btn txt-right">
                    <button id="add_to_cart" class="btn btn-primary">
                        Men&uuml aktualisieren
                    </button>
                </div>
                <div class="col col-lg-3 col-md-3 col-sm-3 col-xs-6 embed-btn txt-left">
                    <button id="to_menu" class="btn btn-primary">
                        Zumdd Men&uuml!
                    </button>
                </div>
            </div>
        </nav>
            
            <div class="container embed-container">
                <div class="row">
                    
                    <% for(IngredientRecipe current_ir : ir) { %>

                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-<% out.print(current_ir.getIngredient().getId()); %>" data-price="<% out.print(current_ir.getIngredient().getPrice()); %>" data-amount="<% out.print(current_ir.getAmount()); %>" data-friendly-amount="<% out.print(current_ir.getFriendlyAmount()); %>">
                                <div class="ingredient-element-name">
                                    <b><% out.print(current_ir.getIngredient().getName());%></b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" ><% out.print(current_ir.getAmountString()); %></span> <% out.print(current_ir.getIngredient().getUnit()); %>
                                </div>
                                
                                <% if(current_ir.getIngredient().hasFriendlyUnit()){ %>
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. <% out.print(current_ir.getFriendlyAmountString()); %></span> <% out.print(current_ir.getIngredient().getFriendly_unit()); %>
                                    </div>
                                <% }else{ %>
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                <% } %>
                                
                                
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
            </div>
                
            <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
        <script>
            
                $(window).resize(function() {
                    // apply dynamic padding at the top of the body according to the fixed navbar height
                    $(".embed-container").css("padding-top", $(".navbar-fixed-top").height());
                    console.log($(".navbar-fixed-top").height());
                });
            
                var quantity=1;

                $(document).ready(function(){
                    quantity=1;
                    $('#quantity').val(quantity);

                    $('.quantity-right-plus').click(function(e){
                        e.preventDefault();
                        quantity = parseInt($('#quantity').val());
                        quantity += 1;
                        $('#quantity').val(quantity);
                    });

                    $('.quantity-left-minus').click(function(e){
                        e.preventDefault();
                        quantity = parseInt($('#quantity').val());
                        if(quantity>0){
                            quantity -= 1;
                            $('#quantity').val(quantity);
                        }
                    });

                    $('#add_to_cart').focusout(function() {
                        $(this).html('Men&uuml aktualisieren');
                    });
                });

                $("#add_to_cart").click(function(){
                    $.ajax({url:"CookBook", data: {"command" : 2,"recipeid" : <% out.print(r.getId());%>, "portions": quantity, "request_ingredient_list": "false","mealplan_keyword": "custom_mealplan"}}).done(function(data){});
                });
                
                $("#to_menu").click(function(){
                    alert("gdfg");
                    window.location = "/Menu";
                });

            </script>   
        </body>
        
        
    <% }catch(Exception e){ %>
    
        <%@include file="dispatch_notfound.jsp" %>
    
    <% } %>
</html>