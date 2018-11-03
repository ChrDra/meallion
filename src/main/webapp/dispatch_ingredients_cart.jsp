<%@page import="utils.Log"%>

<%@page import="orm.MealPlan"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="orm.IngredientRecipe"%>
<%@page import="orm.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="utils.RecipeStep"%>
<%@page import="utils.RecipeBuilder"%>
<%@page import="orm.Recipe"%>

<link href="https://fonts.googleapis.com/css?family=Signika" rel="stylesheet">
    
<style>

    html, body {
      height:100%;
      font-family: 'Signika';
      color: #494949;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
    }    

    .meallion_add_in{
            width: 400px;
            height: auto;
            padding: 10px;
            margin: 20px;
    }

    #instagram_embedded{
            width: auto;
            height: auto;
    }

    #add_to_cart{
            margin: 10px;
            background-color: #63cc87;
    }

    .button_ico{
            width: 20px;
            height: 20px;
    }

    .popup_body{
            height: 100px;
    }

    .inline_link{
            color: #63cc87;
    }

    .bodyingredients{
            height: auto;
    }

    .ingredient_section{
            margin: 1000px;
    }

    .scrollbar1::-webkit-scrollbar {
        width: 6px;
        background-color: #F5F5F5;
            border-radius: 2px;
    } 

    .scrollbar1::-webkit-scrollbar-thumb {
        background-color: #ff9400;
            border-radius: 2px;
    }

    .ingredient_row{
            margin-right: 0px;
            margin-left: 0px;

    }

    .ingredient-element-placeholder{
        padding: 5px;
        position: relative;
        overflow: inherit;
        min-width: 80px;
        float: left;
    }

    .ingredient-element{
        border: 1px solid #63cc87;
        border-radius: 3px;
        display: block;
        word-break: break-all;
            padding: 6px;
    }

    .ingredient-element-name{
            display: block;
            text-align: center;
            margin-left: 10px;
            margin-right: 10px;
    }

    .ingredient-element-amount{
            text-align: center;
    }

    .ingredient-element-friendly-amount{
            text-align: center;
    }

    .ingredient-element-price{
            text-align: center;
    }

    .ingredient-element-img-placeholder{
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 50px;
        height: 50px;
        text-align: center;
    }

    .ingredient-element-img{
        height: 100%;
        width: 100%;
        background-repeat:no-repeat;
        background-position:center;
        background-size: contain;
    }

    .dialogWide > .modal-dialog {
        width: 60% !important;
            height: 100px;
            min-heigh: 100px;
            max-heigh: 50%;
     }

    .added_icon{
            width: 30px;
            height: 30px;
    }


    .animation-target {
      -webkit-animation: animation 2000ms linear infinite both;
      animation: animation 2000ms linear infinite both;	
    }

    /* Generated with Bounce.js. Edit at https://goo.gl/V24yLR */

    @-webkit-keyframes animation { 
      0% { -webkit-transform: matrix3d(0.9, 0, 0, 0, 0, 0.9, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(0.9, 0, 0, 0, 0, 0.9, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      3.15% { -webkit-transform: matrix3d(0.934, 0, 0, 0, 0, 0.934, 0, 0, 0, 0, 1, 0, 0, 3.656, 0, 1); transform: matrix3d(0.934, 0, 0, 0, 0, 0.934, 0, 0, 0, 0, 1, 0, 0, 3.656, 0, 1); }
      4.5% { -webkit-transform: matrix3d(0.949, 0, 0, 0, 0, 0.949, 0, 0, 0, 0, 1, 0, 0, 4.225, 0, 1); transform: matrix3d(0.949, 0, 0, 0, 0, 0.949, 0, 0, 0, 0, 1, 0, 0, 4.225, 0, 1); }
      6.26% { -webkit-transform: matrix3d(0.966, 0, 0, 0, 0, 0.966, 0, 0, 0, 0, 1, 0, 0, 4.44, 0, 1); transform: matrix3d(0.966, 0, 0, 0, 0, 0.966, 0, 0, 0, 0, 1, 0, 0, 4.44, 0, 1); }
      9.01% { -webkit-transform: matrix3d(0.988, 0, 0, 0, 0, 0.988, 0, 0, 0, 0, 1, 0, 0, 4.091, 0, 1); transform: matrix3d(0.988, 0, 0, 0, 0, 0.988, 0, 0, 0, 0, 1, 0, 0, 4.091, 0, 1); }
      11.76% { -webkit-transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 3.381, 0, 1); transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 3.381, 0, 1); }
      13.51% { -webkit-transform: matrix3d(1.009, 0, 0, 0, 0, 1.009, 0, 0, 0, 0, 1, 0, 0, 2.887, 0, 1); transform: matrix3d(1.009, 0, 0, 0, 0, 1.009, 0, 0, 0, 0, 1, 0, 0, 2.887, 0, 1); }
      17.22% { -webkit-transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.934, 0, 1); transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.934, 0, 1); }
      17.92% { -webkit-transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.778, 0, 1); transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.778, 0, 1); }
      22.67% { -webkit-transform: matrix3d(1.011, 0, 0, 0, 0, 1.011, 0, 0, 0, 0, 1, 0, 0, 0.946, 0, 1); transform: matrix3d(1.011, 0, 0, 0, 0, 1.011, 0, 0, 0, 0, 1, 0, 0, 0.946, 0, 1); }
      28.13% { -webkit-transform: matrix3d(1.004, 0, 0, 0, 0, 1.004, 0, 0, 0, 0, 1, 0, 0, 0.409, 0, 1); transform: matrix3d(1.004, 0, 0, 0, 0, 1.004, 0, 0, 0, 0, 1, 0, 0, 0.409, 0, 1); }
      29.03% { -webkit-transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 0.352, 0, 1); transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 0.352, 0, 1); }
      34.63% { -webkit-transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0.128, 0, 1); transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0.128, 0, 1); }
      39.09% { -webkit-transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.05, 0, 1); transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.05, 0, 1); }
      40.14% { -webkit-transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.039, 0, 1); transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.039, 0, 1); }
      50% { -webkit-transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      62.36% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      84.68% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      100% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); } 
    }

    @keyframes animation { 
      0% { -webkit-transform: matrix3d(0.9, 0, 0, 0, 0, 0.9, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(0.9, 0, 0, 0, 0, 0.9, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      3.15% { -webkit-transform: matrix3d(0.934, 0, 0, 0, 0, 0.934, 0, 0, 0, 0, 1, 0, 0, 3.656, 0, 1); transform: matrix3d(0.934, 0, 0, 0, 0, 0.934, 0, 0, 0, 0, 1, 0, 0, 3.656, 0, 1); }
      4.5% { -webkit-transform: matrix3d(0.949, 0, 0, 0, 0, 0.949, 0, 0, 0, 0, 1, 0, 0, 4.225, 0, 1); transform: matrix3d(0.949, 0, 0, 0, 0, 0.949, 0, 0, 0, 0, 1, 0, 0, 4.225, 0, 1); }
      6.26% { -webkit-transform: matrix3d(0.966, 0, 0, 0, 0, 0.966, 0, 0, 0, 0, 1, 0, 0, 4.44, 0, 1); transform: matrix3d(0.966, 0, 0, 0, 0, 0.966, 0, 0, 0, 0, 1, 0, 0, 4.44, 0, 1); }
      9.01% { -webkit-transform: matrix3d(0.988, 0, 0, 0, 0, 0.988, 0, 0, 0, 0, 1, 0, 0, 4.091, 0, 1); transform: matrix3d(0.988, 0, 0, 0, 0, 0.988, 0, 0, 0, 0, 1, 0, 0, 4.091, 0, 1); }
      11.76% { -webkit-transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 3.381, 0, 1); transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 3.381, 0, 1); }
      13.51% { -webkit-transform: matrix3d(1.009, 0, 0, 0, 0, 1.009, 0, 0, 0, 0, 1, 0, 0, 2.887, 0, 1); transform: matrix3d(1.009, 0, 0, 0, 0, 1.009, 0, 0, 0, 0, 1, 0, 0, 2.887, 0, 1); }
      17.22% { -webkit-transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.934, 0, 1); transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.934, 0, 1); }
      17.92% { -webkit-transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.778, 0, 1); transform: matrix3d(1.014, 0, 0, 0, 0, 1.014, 0, 0, 0, 0, 1, 0, 0, 1.778, 0, 1); }
      22.67% { -webkit-transform: matrix3d(1.011, 0, 0, 0, 0, 1.011, 0, 0, 0, 0, 1, 0, 0, 0.946, 0, 1); transform: matrix3d(1.011, 0, 0, 0, 0, 1.011, 0, 0, 0, 0, 1, 0, 0, 0.946, 0, 1); }
      28.13% { -webkit-transform: matrix3d(1.004, 0, 0, 0, 0, 1.004, 0, 0, 0, 0, 1, 0, 0, 0.409, 0, 1); transform: matrix3d(1.004, 0, 0, 0, 0, 1.004, 0, 0, 0, 0, 1, 0, 0, 0.409, 0, 1); }
      29.03% { -webkit-transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 0.352, 0, 1); transform: matrix3d(1.003, 0, 0, 0, 0, 1.003, 0, 0, 0, 0, 1, 0, 0, 0.352, 0, 1); }
      34.63% { -webkit-transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0.128, 0, 1); transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0.128, 0, 1); }
      39.09% { -webkit-transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.05, 0, 1); transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.05, 0, 1); }
      40.14% { -webkit-transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.039, 0, 1); transform: matrix3d(0.998, 0, 0, 0, 0, 0.998, 0, 0, 0, 0, 1, 0, 0, 0.039, 0, 1); }
      50% { -webkit-transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(0.999, 0, 0, 0, 0, 0.999, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      62.36% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      84.68% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); }
      100% { -webkit-transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); } 
    }
    
</style>

<% try {

    Recipe r = (Recipe) request.getAttribute("recipe");
    List<IngredientRecipe> ir = r.getIngredientRecipe();

%>
    <div class="row">
        <% for(IngredientRecipe current_ir : ir) { %>

            <div class="ingredient-element-placeholder">
                <div class="ingredient-element">
                    <div class="ingredient-element-name">
                        <b><% out.print(current_ir.getIngredient().getName());%></b>
                    </div>
                    <div class="ingredient-element-img-placeholder">
                        <div class="ingredient-element-img" style="background-image: url('<% out.print(current_ir.getIngredient().getImgUrl()); %>')"></div>
                    </div>
                </div>
            </div>
        <% } %>
    </div>

<% }catch(Exception e){ %>
    <%@include file="dispatch_notfound.jsp" %>
<% } %>


