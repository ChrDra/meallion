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
            List<MealPlan> mealplans = searchresults.getMealplan_results();
    %>
        <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <h1>Ausgew&auml;hlte Men&uuml;s</h1><br>
                </div>
        </div>
    
        <div class="row selection-row">
        <%
            for(MealPlan m : mealplans){
                
                String[] img_ulr_str = m.getImg_urls().split(";");

        %>

                <div class="col-lg-6 col-md-6 col-sm-12 mealplan-portfolio-element-placeholder">
                    <div id="portfolio-element-<% out.print(m.getId()); %>" class="mealplan-portfolio-element" >
                        <a href="/Menu?m=<% out.print(m.getKeyword()); %>">

                            <div class="portfolio-element-img viewport_container">
                                
                                <div class="mealplan_viewport" id="mealplan_viewport_1">    
                                    <div class="mealplan-element-img">
                                        <div class="mealplan-element-img-canvas" style="background-image: url('<%
                                            try{ 
                                                out.print(img_ulr_str[0]);
                                            }catch(Exception e){
                                                out.print("");
                                            }
                                        %>')"></div>
                                    </div>
                                </div>
                                <div class="mealplan_viewport" id="mealplan_viewport_2">
                                    <div class="mealplan-element-img">    
                                        <div class="mealplan-element-img-canvas" style="background-image: url('<%
                                            try{ 
                                                out.print(img_ulr_str[1]);
                                            }catch(Exception e){
                                                out.print("");
                                            }
                                        %>')"></div>
                                    </div>
                                </div>
                                <div class="mealplan_viewport" id="mealplan_viewport_3">
                                    <div class="mealplan-element-img">
                                        <div class="mealplan-element-img-canvas" style="background-image: url('<%
                                            try{ 
                                                out.print(img_ulr_str[2]);
                                            }catch(Exception e){
                                                out.print("");
                                            }
                                        %>')"></div>
                                    </div>
                                </div>
                                <div class="mealplan_viewport" id="mealplan_viewport_4">
                                    <div class="mealplan-element-img">
                                        <div class="mealplan-element-img-canvas" style="background-image: url('<%
                                            try{ 
                                                out.print(img_ulr_str[3]);
                                            }catch(Exception e){
                                                out.print("");
                                            }
                                        %>')"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="portfolio-element-price-tag">
                                <b><% out.print(m.GetTransientPriceString()); %></b>
                            </div>

                            <div class="mealplan-portfolio-element-text">
                                <b><font size="3"> <% out.print(m.getName()); %></font></b>
                            </div>
                        </a> 
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
                                <h1>Leider keine Men&uuml;s gefunden...</h1><br>
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