
/*
 * user filter variables for selections:
 */

var budget = 0;
var time = 0;
var veggie = "false";
var vegan = "false";
var tags = "";

var command = 1;

/*
 * Addressable html divs:
 * 
 * #offering_placeholder
 *  holds the offering content. Before content is loaded, it holds a loading gif
 * 
 * #search_menus_button
 * #search_recipes_button
 * #main_search_input_button
 * #tags_input
 * 
 * #budget_chooser_box
 * #control_output_budget
 * #toggle_section_element_budget
 * 
 * #time_chooser_box
 * #control_output_time
 * #toggle_section_element_time
 * 
 * #toggle_section_element_veggie
 * #toggle_section_element_vegan
 * 
 * #mealplan_save_section_name_exists_already
 * 
 * #contact_mail_input
 * 
 * #ingredient_section
 * 
 * #portions_select_button_number
 * 
 */


function set_default_request(){
    budget = 10000;
    time = 10000;
    veggie = "false";
    vegan = "false";
    tags = "";
}

function set_offering_wait_status(){
    $('#offering_placeholder').html('<img id="offering_loading_gif" src="/images/elements/Spinner-5.9s-200px.gif">');
}

function trigger_selection(callback){
    set_offering_wait_status();
    $.ajax({url:"https://www.meallion.de/CookBook", data: {"command" : command,"budget" : budget,"time" : time,"veggie" : veggie,"vegan" : vegan,"tags" : tags}}).done(function(data){
            $('#offering_placeholder').html(data);
            if (!(callback === undefined)) callback();
    });
}

///////////////////////////////////////////////////////////////////////////////////////////
//SELECTION TRIGGER EVENTS:
///////////////////////////////////////////////////////////////////////////////////////////

////////////
//Decide between Recipe or Menu
////////////

$("#search_recipes_button").click(function(){
    command = 1;
    $("#time_chooser_box").show();
    $("#search_menus_button").css("background-color","#cccccc");
    $("#search_recipes_button").css("background-color","#63cc87");
    trigger_selection(startWaterfall);
});

$("#search_menus_button").click(function(){
    command = 5;
    $("#time_chooser_box").hide();
    $("#search_menus_button").css("background-color","#63cc87");
    $("#search_recipes_button").css("background-color","#cccccc");
    trigger_selection(startWaterfall);
});

////////////
//Budget Slider
////////////

//on input (show new value)

$("#toggle_section_element_budget").on("input change", function(){
       
       var number = parseFloat($("#toggle_section_element_budget").val());
       if(number<1){
           $("#control_output_budget").html((number*100).toLocaleString()+" Cent pro Portion");
       }else if(number == 5.1){
           $("#control_output_budget").html("Keine Pr&auml;ferenz");
       }
       else{
            //var formattedNumber = ($("#toggle_section_element_budget").val().toLocaleString('de-DE', { style: 'currency', currency: 'EUR' }));
            $("#control_output_budget").html("&#x20AC; "+number.toLocaleString(undefined, {minimumFractionDigits: 2})+" pro Portion");
       }
});

//on change (triggering ajax)

$("#toggle_section_element_budget").change(function(){
       budget = $("#toggle_section_element_budget").val();
       if(budget>=5.1){
           budget = 10000;
       }
       trigger_selection(startWaterfall);
});

////////////
//Time Slider
////////////

//on input (show new value)

$("#toggle_section_element_time").on("input change", function(){
    var time = parseFloat($("#toggle_section_element_time").val());
    if(time!=70){
        $("#control_output_time").html("max. "+$("#toggle_section_element_time").val()+" Minuten"); 
    }else{
        $("#control_output_time").html("Keine Pr&auml;ferenz");
    }
});

//on change (triggering ajax)

$("#toggle_section_element_time").change(function(){
       time = $("#toggle_section_element_time").val();
       if(time>=70){
           time = 10000;
       }
       trigger_selection(startWaterfall);
});

////////////
//Veggie Radio Button
////////////

$("#toggle_section_element_veggie").change(function(){
    if($("#toggle_section_element_veggie").is(':checked')){
        veggie = "true";
    }else{
        veggie = "false";
    }
    trigger_selection(startWaterfall);
});

////////////
//Vegan Radio Button
////////////

$("#toggle_section_element_vegan").change(function(){
    if($("#toggle_section_element_vegan").is(':checked')){
        vegan = "true";
    }else{
        vegan = "false";
    }
    trigger_selection(startWaterfall);
});

////////////
//Main Search Button
////////////

$("#main_search_input_button").click(function(){
    tags = $("#tags_input").serialize();
    trigger_selection(startWaterfall);
});

//Tagify Search Bar



//END SELECTION TRIGGER EVENTS
//////////////////////////////////////////////


function trigger_save_current_mealplan(name,descr){
        $.ajax({url:"CookBook", data: {"command" : 4,"name": name, "descr": descr}}).done(function(data){
            if(data=="name_already_exists"){
                $("#mealplan_save_section_name_exists_already").slideToggle(200,function(){
                });
            }else{
                window.location.href = data;
            }
            
	});
}

function send_email_address(email_address){
	$.ajax({url:"CookBook", data: {"command" : 3,"email_address" : email_address}}).done(function(data){
            $('#contact_mail_input').val("Danke!");
        });
}

function trigger_menuchange(recipe_id,portions,requestingredientlist, mealplan_keyword, callback){
	if(requestingredientlist!=="false"){
            $("#ingredient_section").html("<img id=\"ingredient_loading_gif\" src=\"/img/elements/Spinner-5.9s-200px.gif\">");
        }
        $.ajax({url:"CookBook", data: {"command" : 2,"recipeid" : recipe_id, "portions": portions, "request_ingredient_list": requestingredientlist,"mealplan_keyword": mealplan_keyword}}).done(function(data){
            if(requestingredientlist!=="false"){
                $("#ingredient_section").html(data);
                
            }
            callback();
	});
}

function ux_rounding(x){
    if(x<20){
        return x;
    }
    if(x>=20 && x<50){
        return Math.round(x);
    }
    if(x>=50 && x<500){
        return Math.round(x/5)*5;
    }
    if(x>=500){
        return Math.round(x/10)*10;
    }
}

function trigger_personchange(number){
    $(".ingredient-element").each(function(){
        var price = $(this).data("price");
        var amount = $(this).data("amount");
        var friendly_amount = $(this).data("friendly-amount");
        var new_amount = ux_rounding(amount*number);
        var new_friendly_amount = ux_rounding(friendly_amount*number);
        var new_price = amount*price*number;

        var friendly_amount_element = $(this).find(".ingredient-friendly-amount");
        var amount_element = $(this).find(".ingredient-amount");
        
        var price_element = $(this).find(".ingredient-price");

        $(amount_element).html(new_amount.toLocaleString(undefined, {
            minimumFractionDigits: 0,
            maximumFractionDigits: 1
          }));
        $(friendly_amount_element).html(new_friendly_amount.toLocaleString(undefined, {
          minimumFractionDigits: 0,
          maximumFractionDigits: 1
        }));
        $(price_element).html(new_price.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          }));
        
        $("#portions_select_button_number").html(number);
          
      });    
}


