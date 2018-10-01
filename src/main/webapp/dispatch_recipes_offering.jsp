<%@page import="features.SearchResults"%>
<%@page import="utils.Log"%>
<%@page import="orm.MealPlan"%>
<!-- dispatch_recipes_offering.jsp

Is loaded on the landing page to showcase the (top, featured) meals

CSS:
dispatch_recipes_offering.css loaded in index head

JS:
dispatch_recipes_offering
script in file as needed in the loop

-->

<%@page import="java.util.List"%>
<%@page import="orm.Recipe"%>
<%@page import="sql.SQL"%>
<%@page import="orm.MealPlan"%>

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>

    <%
       
        SearchResults searchresults = (SearchResults) request.getAttribute("search_results");
        
        if(searchresults.isOk()){
            List<Recipe> recipes = searchresults.getRecipe_results();
            MealPlan current_mealplan = (MealPlan) request.getAttribute("mealplan");

    %>
        <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <h1>Ausgewählte Rezepte</h1><br>
                </div>
        </div>
    
        <div id="offering_waterfall" class="row selection-row">
        <%
            for(Recipe r : recipes){
        %>

                <div class="col-lg-4 col-md-4 col-sm-6 portfolio-element-placeholder">
                    <div id="portfolio-element-<% out.print(r.getId()); %>" class="portfolio-element" >
                        <a href="/CookBook?recipe=<% out.print(r.getKeyword()); %>">

                            <img class="portfolio-element-img" src="<% out.print(r.getImgUrl_small()); %>">

                            <div id="portfolio-element-feature-<% out.print(r.getId()); %>" class="portfolio-element-feature" data-selected="false">
                                <img class="portfolio-element-feature-img" src="/images/elements/chef-hat.svg">
                            </div>

                            <div class="portfolio-element-price-tag">
                                <b><% out.print(r.GetPriceString()); %></b>
                            </div>

                            <div class="portfolio-element-text">
                                <div class="portfolio-element-text-header">
                                    <b><font size="3"> <% out.print(r.getName()); %></font></b>
                                </div>
                                <div class="portfolio-element-text-descr">
                                    <i><font size="2"><% out.print(r.getShort_descr()); %></font></i>
                                </div>
                            </div>
                        </a> 
                    </div>
                </div>

                <script type="text/javascript">
                    <% 
                        try{
                            if(current_mealplan!=null){
                                boolean selected = current_mealplan.Contains(r);
                                if(selected){
                        %>
                                    $("#portfolio-element-feature-<% out.print(r.getId()); %>").find('.portfolio-element-feature-img').attr('src','/images/elements/chef-hat_selected.svg');
                                    $("#portfolio-element-feature-<% out.print(r.getId()); %>").data("selected",true);;
                        <%
                                }else{
                        %>
                                $("#portfolio-element-feature-<% out.print(r.getId()); %>").data("selected",false);
                        <%
                                }
                            }
                        }catch(Exception e){
                            Log.wln("Error in dispatch_recipes_offering: "+e);
                        }
                    %>

                    $("#portfolio-element-feature-<% out.print(r.getId()); %>").click(function(e) {
                        e.stopPropagation();
                        e.preventDefault();

                        var this_element = this;

                        if($(this).data("selected")){
                            
                            var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="/images/elements/Spinner-5.9s-200px.gif"> Rezept wird entfernt..',
                            closeButton: false
                            });
                            var timeout = 1000; 
                            setTimeout(function () {
                                dialog1.modal('hide');
                            }, timeout);
            
                            $(this).find('.portfolio-element-feature-img').attr('src','/images/elements/Spinner-5.9s-42px.gif');

                            $(this).data("selected",false);
                            trigger_menuchange(<% out.print(r.getId()); %>,"0","false","custom_mealplan",function(){

                                $(this_element).find('.portfolio-element-feature-img').attr('src','/images/elements/chef-hat.svg');
                            });
                        }else{
                            
                            var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="/images/elements/Spinner-5.9s-200px.gif"> Rezept wird hinzugefügt..',
                            closeButton: false
                            });
                            var timeout = 1000; 
                            setTimeout(function () {
                                dialog1.modal('hide');
                            }, timeout);   

                            $(this).find('.portfolio-element-feature-img').attr('src','/images/elements/Spinner-5.9s-42px.gif');

                            $(this).data("selected",true);
                            trigger_menuchange(<% out.print(r.getId()); %>,"1","false","custom_mealplan",function(){

                                $(this_element).find('.portfolio-element-feature-img').attr('src','/images/elements/chef-hat_selected.svg');
                            });
                        }
                    });

                    /*$("#portfolio-element-<% out.print(r.getId()); %>").click(function(e) {
                        window.location.href = "/CookBook?recipe=<% out.print(r.getKeyword()); %>";
                    });*/

                     $("#portfolio-element-feature-<% out.print(r.getId()); %>").hover(function(e){
                         $(this).animate({
                            width: 40,
                            height: 40
                            }, 60, function() {
                                // Animation complete.
                            });
                     },function(e){
                         $(this).animate({
                            width: 30,
                            height: 30
                            }, 60, function() {
                                // Animation complete.
                            });
                     });

                </script>

            <% 
                }
            //if zero or err results:
            }else{
                //if zero
                if(searchresults.getMeta()==0){
                    %>
                    
                    <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12">
                                <h1>Leider keine Gerichte gefunden...</h1><br>
                            </div>
                    </div>
                    
                    <img id="no_results_image" src="/images/elements/noun_food_waste_922012_63cc87.svg">
                    <%
                //if error    
                }else{

                }
            }
            %>
            
    </div>
