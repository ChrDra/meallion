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
            
            <meta property="og:title" content="<% out.print(r.getName()); %>" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="<% out.print("/meals-v1/CookBook?recipe="+r.getKeyword()); %>" />
            <meta property="og:image" content="<% out.print(r.getImgUrl_small()); %>" />	
	</head>
	
        <body>
            <%@include file="header.jsp"%>
            
            <div class="container main-page-container">

                <div id="headline_section" class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 recipe-headline-left">
                        <div id="recipe_image" class="ingredient-element-img" style="background-image: url(<% out.print(r.getImgUrl_small()); %>)"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 recipe-headline-right">
                        <h1><% out.print(r.getName()); %></h1><br>
                        <div class="recipe-short-descr">
                            <% out.print(r.getShort_descr()); %>
                        </div>
                        <div class="recipe-headline-features col-12">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="recipe-headline-features-inmenu" data-selected="false">
                                        Hinzufuegen
                                        <div id="recipe-feature-<% out.print(r.getId()); %>" class="recipe-headline-features-inmenu-img-placeholder center">
                                            <img id="recipe-headline-features-inmenu-img" class="recipe-headline-features-inmenu-img" src="https://www.meallion.de/images/elements/chef-hat.svg">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <button id="portions_select_button" class="recipe-headline-features-portionselector btn btn-secondary dropdown-toggle selection-style" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            W&auml;hle Portionen<br><span id="portions_select_button_number">1<span class="caret"></span>
                                    </button></span>
                                    <ul id="portion_select_dropdown_div" class="dropdown-list dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <li id="portions_select_1"><span class="drop_button">1</li>
                                        <li id="portions_select_2"><span class="drop_button">2</span></li>
                                        <li id="portions_select_3"><span class="drop_button">3</span></li>
                                        <li id="portions_select_4"><span class="drop_button">4</span></li>
                                        <li id="portions_select_5"><span class="drop_button">5</span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        
                <div id="ingredient_section">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                <h2>Zutaten (<% out.print(price_string);%> pro Portion) </h2><br>
                            </div>
                        </div>

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

                <div id="recipe_section">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12">
                            <h2>Zubereitung</h2><br>
                        </div>
                    </div>

                    <%  for(RecipeStep s : steps) { 
                        
                        //If there is a step image, show image and the text
                        if(!s.img.equals("")){
                    %>
                            <div class="row recipe-row">
                                <div class="col-lg-4 col-md-4 col-sm-6 recipe-step-img-placeholder">
                                    <img class="recipe-step-img-container" src="<% out.print(s.img);%>">
                                </div>
                                <div class="col-md-8" class="col-lg-8">
                                    <div class="label label-default step-numbering"> <font size="4"><% out.print(i);%></font></div>
                                    <% out.print(s.txt);%>
                                </div>
                            </div>
                    <% 
                        //if there is no image, only show text
                        }else{ %>
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4"><% out.print(i);%></font></div>
                                    <% out.print(s.txt);%>
                                </div>
                            </div>
                        
                        <% } i++; }  %>
                </div>
                
            </div>
                
            <%@include file="footer.jsp" %>
            
            <!--<script type="text/javascript" src="js/dispatch_recipe.js">-->
            
            <script>
                
                trigger_personchange(<% out.print(r.getProposedPortions()); %>);
                
                $("#portions_select_1").click(function(){
                    trigger_personchange(1);
                });

                $("#portions_select_2").click(function(){
                    trigger_personchange(2);
                });

                $("#portions_select_3").click(function(){
                    trigger_personchange(3);
                });

                $("#portions_select_4").click(function(){
                    trigger_personchange(4);
                });

                $("#portions_select_5").click(function(){
                    trigger_personchange(5);
                });
                
                $("#portions_select_6").click(function(){
                    trigger_personchange(6);
                });
                
                $("#portions_select_7").click(function(){
                    trigger_personchange(7);
                });
                
                $("#portions_select_8").click(function(){
                    trigger_personchange(8);
                });
                
                $('#recipe_image').css('cursor', 'pointer');
                $("#recipe_image").click(function(){
                    
                    bootbox.alert({
                        message: '<img style="width: 100%; heigh: 100%;" src="<% out.print(r.getImgUrl_large()); %>">',
                        className : "recipe_img_popup",
                        size: 'large',
                        backdrop: true,
                        buttons: {
                            ok: {
                                label: "Zur&uuml;ck",
                                callback: function(){
                                }
                            }
                        }
                    });
                });
                
                <% 
                    try{
                        Log.wdln("Check if r#"+r.getId()+" is in mealplan");
                        boolean selected = current_mealplan.Contains(r);
                        if(selected){
                        Log.wdln("Recipe found in current mealplan!");
                %>      
                        $("#recipe-headline-features-inmenu-img").attr('src','https://www.meallion.de/images/elements/chef-hat_selected.svg');
                        $("#recipe-feature-<% out.print(r.getId()); %>").data("selected",true);
                        
                        $("#recipe-feature-<% out.print(r.getId()); %>").find('.recipe-feature-img').attr('src','https://www.meallion.de/images/elements/chef-hat_selected.svg');
                        $("#recipe-feature-<% out.print(r.getId()); %>").data("selected",true);
                <%
                        }else{
                        Log.wdln("Recipe not found in current mealplan!");
                %>
                        $("#recipe-feature-<% out.print(r.getId()); %>").data("selected",false);
                <%
                        }
                    }catch(Exception e){
                        Log.edln("During dispatch_recipe, setting in menu attribute: "+e);
                    }
                %>

                $(".recipe-headline-features-inmenu").click(function(e) {
                    e.stopPropagation();
                    
                    if($(this).data("selected")){
                        
                        //load dialog box
                        var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="https://www.meallion.de/images/elements/Spinner-5.9s-200px.gif"> Wird entfernt..',
                            closeButton: false
                        });
                        var timeout = 1000; 
                        setTimeout(function () {
                            dialog1.modal('hide');
                        }, timeout);    
                        // end load dialog box
                            
                        $('#recipe-headline-features-inmenu-img').attr('src','https://www.meallion.de/images/elements/chef-hat.svg');
                        $(this).data("selected",false);
                        trigger_menuchange(<% out.print(r.getId()); %>,"0","false","custom_mealplan",function(){});
                    }else{
                        
                        //load dialog box
                        var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="https://www.meallion.de/images/elements/Spinner-5.9s-200px.gif"> Wird hinzugef&uuml;gt..',
                            closeButton: false
                        });
                        var timeout = 1000; 
                        setTimeout(function () {
                            dialog1.modal('hide');
                        }, timeout);    
                        // end load dialog box
                        
                        $('#recipe-headline-features-inmenu-img').attr('src','https://www.meallion.de/images/elements/chef-hat_selected.svg');
                        $(this).data("selected",true);
                        trigger_menuchange(<% out.print(r.getId()); %>,"1","false","custom_mealplan",function(){});
                    }
                });
                
            </script>

    </body>
    <% }catch(Exception e){ %>
    
        <%@include file="dispatch_notfound.jsp" %>
    
    <% } %>
</html>

