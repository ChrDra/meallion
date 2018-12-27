<%@page import="orm.MealPlan"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="orm.IngredientRecipe"%>
<%@page import="orm.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="utils.RecipeStep"%>
<%@page import="utils.RecipeBuilder"%>
<%@page import="orm.Recipe"%>


    <% try {
        MealPlan current_mealplan = (MealPlan) request.getAttribute("mealplan");
        Log.wdln("In Menu JSP: displaying menu with name="+current_mealplan.getName()+" ; keyword="+current_mealplan.getKeyword());
        if(current_mealplan==null) System.out.println("dispatch mealpan: current mealplan object is NULL!");
        List<MealPlanRecipe> recipes = current_mealplan.GetMealPlanRecipes();  
    %>

    
    <html lang="en">
        <head>
            
            <!--standard head tags like title and meta data: -->
            <%@include file="standard_head.jsp"%>
            
            <!--additional head tags -->
            <link href="css/dispatch_recipe.css" rel="stylesheet">
            <link href="css/dispatch_mealplan.css" rel="stylesheet">
            <link href="css/dispatch_recipes_offering.css" rel="stylesheet">
            <link href="css/dispatch_embed_interface.css" rel="stylesheet">
            <link href="css/embed.css" rel="stylesheet">

            <meta property="og:title" content="" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="" />
            <meta property="og:image" content="" />
            
            <!--additional resources -->
            <link href="//cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
            <link href="//cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet">
            
        </head>

        <body>
            
            <nav class="navbar navbar-fixed-top" role="navigation">
                <div class="container">
                    <div class="row">  
                        <div class="col col-lg-3 col-m-3 col-sm-3 col-xs-12">
                            <img class="meallion_logo" src="https://www.meallion.de//images/elements/Meallion_logo.PNG">
                        </div>
                    </div>
                </div>
            </nav>
            
            <div class="container embed-container">
            
                    <%
                        if(!"saved".equals((String)request.getAttribute("mealplan_status"))){
                    %>
                        <!-- SAVE MEALPLAN STATUS -->
                        <div id="mealplan_status" data-mealplan_keyword="custom_mealplan"></div>
                        <!-- -->
                        <h1>Mein Men&uuml;</h1><br>
                    <%
                        }else{
                    %>
                        <!-- SAVE MEALPLAN STATUS -->
                        <div id="mealplan_status" data-mealplan_keyword=<%out.print(current_mealplan.getKeyword()); %>></div>
                        <!-- -->
                        <h1><% out.print(current_mealplan.getName()); %></h1><br>

                            <%
                            if(!current_mealplan.getDescr().equals("")){
                        %>
                            <div id="descr_section" class="row">
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                <% out.print(current_mealplan.getDescr()); %>
                            </div></div>
                        <%
                            }
                        }
                    %>
                    <h2>Rezepte</h2><br>
                
                <div id="recipe_section">
                    
                    <%
                        int i = 1;
                        for(MealPlanRecipe r : recipes){
                    %>     
                    
                        <div id="portfolio-element-placeholder-<% out.print(r.getRecipe().getId()); %>" class="col-lg-4 col-md-4 col-sm-6 portfolio-element-placeholder">
                            <div class="portfolio-element" id="portfolio-element-<% out.print(r.getRecipe().getId()); %>" >
                                <img class="portfolio-element-img" src="<% out.print(r.getRecipe().getImgUrl_small()); %>">

                                <div class="portfolio-element-feature" data-selected="false">
                                    <div class="dropdown">
                                        <button class="mealplan_portions_selection_switch_button btn btn-secondary dropdown-toggle selection-style" id="mealplan_portions_selection_switch_button-<% out.print(r.getRecipe().getId()); %>" type="button" data-i="<% out.print(i); %>">
                                                <% out.print(r.getPortions()); %><span class="caret"></span>
                                        </button>
                                    </div>
                                </div>
                                <ul class="portion-select-list" id="portion-select-list-<% out.print(i); %>">
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="0"><span class="drop_button">0</span></li>
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="1"><span class="drop_button">1</span></li>
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="2"><span class="drop_button">2</span></li>
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="3"><span class="drop_button">3</span></li>
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="4"><span class="drop_button">4</span></li>
                                    <li class="mealplan_portions_selection" data-recipe="<% out.print(r.getRecipe().getId()); %>" data-portions="5"><span class="drop_button">5</span></li>
                                </ul>
                                <div class="portfolio-element-text">
                                    <div class="portfolio-element-text-header">
                                        <b><font size="3"> <% out.print(r.getRecipe().getName()); %></font></b>
                                    </div>
                                    <div class="portfolio-element-text-descr">
                                        <i><font size="2"><% out.print(r.getRecipe().getShort_descr()); %></font></i>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
                        <script>
                            $(document).ready(function () {
                                $("#portfolio-element-<% out.print(r.getRecipe().getId()); %>").click(function(e) {
                                    window.location.href = "/CookBook?recipe=<% out.print(r.getRecipe().getKeyword()); %>";
                                });

                                $("#portion-select-list-<% out.print(i); %>").click(function(e){
                                   e.stopPropagation();
                                });
                                
                                $("#mealplan_portions_selection_switch_button-<% out.print(r.getRecipe().getId()); %>").click(function(e){
                                    e.stopPropagation();
                                });

                           });
                        </script>

                    <% 
                        i++;
                        }
                    %>

                </div>
                    
                <div id="ingredient_section">
                    <%@include file="dispatch_ingredients.jsp" %>
                </div>    
                    
            </div>    
            

            <script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
            <script src="//cdn.quilljs.com/1.3.6/quill.min.js"></script>
            <script type="text/javascript" src="js/dispatch_mealplan.js"></script>
            <script type="text/javascript" src="js/masonry.pkgd.min.js"></script>
            <script type="text/javascript" src="js/imagesloaded.pkgd.min.js"></script>
            
        </body>
        
        <% }catch(Exception e){
            System.out.println(e);
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        %>

        <%@include file="dispatch_notfound.jsp" %>

        <% } %>
    </html>

