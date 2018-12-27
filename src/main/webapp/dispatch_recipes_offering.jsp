<%@page import="features.SearchResults"%>
<%@page import="utils.Log"%>
<%@page import="orm.MealPlan"%>
<%@page import="java.util.List"%>
<%@page import="orm.Recipe"%>
<%@page import="sql.SQL"%>
<%@page import="orm.MealPlan"%>

    <%
        SearchResults searchresults = (SearchResults) request.getAttribute("search_results");
        
        if(searchresults.isOk()){
            List<Recipe> recipes = searchresults.getRecipe_results();
            MealPlan current_mealplan = (MealPlan) request.getAttribute("mealplan");
    %>
    
        <div class="page-header">
            <h1>Ausgewählte Rezepte</h1>
        </div>

        <div class="row container-gallery">
        <%
            for(Recipe r : recipes){
                
                String hat = "https://meallion.de/images/elements/chef-hat.svg";
                boolean selected = current_mealplan.Contains(r);
                if(selected){
                    hat = "/images/elements/chef-hat_selected.svg";
                }
        %>
        
            <div class="col-md-4 col-sm-6 col-xs-12 col">
                <div class="post-container bd-white bg-darkred tx-left">
                    <div class="post-image">
                      <a href="/CookBook?recipe=<% out.print(r.getKeyword()); %>" class="img-group-gallery" title="Lorem ipsum dolor sit amet">
                        <img src="<% out.print(r.getImgUrl_small()); %>" class="img-responsive" alt="fransisca gallery">
                        <!-- Element Feature -->
                        <div class="masry-feature">
                            <div class="masry-imgFbg portfolio-element-feature" data-selected=<% out.print(hat); %> data-id="<% out.print(r.getId()); %>">
                                <img class="masry-img" src="<% out.print(hat); %>">
                            </div>
                            <b class="masry-price"><% out.print(r.GetPriceString()); %></b>
                        </div>

                        <!-- Title Block -->
                        <h1 class="masry-title"> <% out.print(r.getName()); %></h1>
                        <span class="masry-subtitle"><% out.print(r.getShort_descr()); %></span>
                      </a>
                    </div>
                </div>
            </div>
                
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
                    
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>        
    <script type="text/javascript">

        $(".portfolio-element-feature").click(function(e) {
            e.stopPropagation();
            e.preventDefault();

            var this_element = this;
            var id = parseInt($(this_element).data("id"));
            
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
                    trigger_menuchange(id,"0","false","custom_mealplan",function(){
                        $(this_element).find('.masry-img').attr('src','/images/elements/chef-hat.svg');
                    });
                }else{

                    var dialog1 = bootbox.dialog({
                    message: '<img class="dialog_box_spinner" src="/images/elements/Spinner-5.9s-200px.gif"> Rezept wird hinzugef&uuml;gt..',
                    closeButton: false
                    });
                    var timeout = 1000; 
                    setTimeout(function () {
                        dialog1.modal('hide');
                    }, timeout);   

                    $(this).find('.portfolio-element-feature-img').attr('src','/images/elements/Spinner-5.9s-42px.gif');

                    $(this).data("selected",true);
                    trigger_menuchange(id,"1","false","custom_mealplan",function(){

                        $(this_element).find('.masry-img').attr('src','/images/elements/chef-hat_selected.svg');
                    });
                }
            });
    </script>
