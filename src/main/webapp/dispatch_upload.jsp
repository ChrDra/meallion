<%@page import="java.util.ArrayList"%>
<%@page import="orm.MealPlan"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="orm.IngredientRecipe"%>
<%@page import="orm.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="utils.RecipeStep"%>
<%@page import="utils.RecipeBuilder"%>
<%@page import="orm.Recipe"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <html>
        <head>
            
            <!--standard head tags like title and meta data: -->
            <%@include file="standard_head.jsp"%>
            
            <!--additional head tags -->
            <link href="css/dispatch_upload.css" rel="stylesheet">
            <link href="css/tagify.css" rel="stylesheet">

            <meta property="og:title" content="" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="" />
            <meta property="og:image" content="" />
            
        </head>
        <body>
             <%@include file="header.jsp"%>
            
            <div class="container main-page-container">

                <div class="row search_section">
                <h2>Tags</h2>
                    <div class="col-lg-12 col-md-12 col-sm-12">
                        <div class="main_search_input_group">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <input name='tags' id="tags_input" class="tags_update_input"><br>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-12">
                                <button class="btn btn-primary tags_update_button" type="button">Tags speichern</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row search_section">
                    <h2>Bild</h2>

                    <div class="col-lg-8 col-md-12 col-sm-12 col-lg-offset-2 preview_img_section">
                        <div class="image_element" >
                                <img class="upload-element-img" src="<%out.print(request.getAttribute("fileurl"));%>">
                        </div>
                    </div>
                </div>
                    
                <br>powered by: <img id="clarify_credential_img" src="https://clarifai.com/cms-assets/20180311184054/Clarifai_Pos.svg">
                        
            </div>

            <%@include file="footer.jsp" %>
            <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
            <script type="text/javascript" src="js/jquery-3.1.0.min.js"></script>
            <script type="text/javascript" src="js/tagify.js"></script>
            <script type="text/javascript" src="js/jQuery.tagify.js"></script>
            <script type="text/javascript" src="js/jQuery.tagify.min.js"></script>
            <script type="text/javascript" src="js/tagify.min.js"></script>
            <script src="js/dispatch_upload.js"></script>
            
            <script>
                tagify.addTags([
                <% 
                    ArrayList<String> tags = (ArrayList<String>) request.getAttribute("tags");
                    for(int i=0;i<tags.size();i++){
                        out.print("{\"value\":\""+tags.get(i)+"\"}");
                        if(i!=tags.size()-1){
                            out.print(",");
                        }
                    }
                %>                    
                ])
                
            </script>
        </body>
</html>
