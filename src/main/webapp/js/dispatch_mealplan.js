var open_selection_list = null;
var msnry;
var container;


$(document).ready(function() {

    var editor = new Quill('.editor-container');  // First matching element will be used

    var container = $('.editor-container').get(0);

    var quill = new Quill('#editor-container', {
      modules: {
        toolbar: [
          [{ header: [1, 2, false] }],
          ['bold', 'italic', 'underline'],
          []
        ]
      },
      placeholder: 'Gib hier eine Beschreibung Deines Men&uuml;s ein',
      theme: 'snow'  // or 'bubble'
    });
    
    container = document.querySelector('#recipe_section');

    imagesLoaded( container, function( instance ) {
        msnry = new Masonry( container, {
            itemSelector: '.portfolio-element-placeholder'
        });  
    });
});



$(".mealplan_portions_selection").click(function(){
   var recipe=  $(this).data("recipe");
   var portions=  $(this).data("portions");
   
   var curPos = $(document).scrollTop();
   
   //check what mealplan shall be updated:
   // if mealplan status = saved, then update current_mealplan
   // if mealplan status = custom, the update custom mealplan
   
   var mealplan_keyword = $("#mealplan_status").data("mealplan_keyword");
   
   //if 0 portions selected, reload the page in callback function
   if(portions===0)
       trigger_menuchange(recipe,portions,"true",mealplan_keyword,function() {
             $(document).scrollTop(curPos);   
             $('#portfolio-element-placeholder-'+recipe).animate({
                opacity: 0,
              }, 700,function(){
                    $('#portfolio-element-placeholder-'+recipe).remove();
                    container = document.querySelector('#recipe_section');
                    msnry = new Masonry( container, {
                        itemSelector: '.portfolio-element-placeholder'
                    });
              });
         
           
            
        });
   else
       trigger_menuchange(recipe,portions,"true",mealplan_keyword,null);
   
   var button_id = "#mealplan_portions_selection_switch_button-"+recipe;
   $(button_id).html(portions);
   open_selection_list.hide();
   open_selection_list = null;
});

$(".mealplan_portions_selection_switch_button").click(function(){
    var list_id = "#portion-select-list-"+$(this).data("i");
    if($(open_selection_list).is(":visible")){
       $(list_id).hide();
       open_selection_list = null;
   }else{
       $(list_id).show();
       open_selection_list = $(list_id);
   }
});

$(window).click(function(){
    if($(open_selection_list).is(":visible")){
        open_selection_list.hide();
    }
});

var save_section_open = false;
$("#mealplan_save_section_open_button").click(function(){
    $("#mealplan_save_section").slideToggle(200,function(){
        save_section_open = !save_section_open;
        if(save_section_open){
            $('#save_button_glyph').addClass('glyphicon-chevron-up').removeClass('glyphicon-chevron-down');
        }else{
            $('#mealplan_save_section_name_exists_already').hide();
            $('#save_button_glyph').addClass('glyphicon-chevron-down').removeClass('glyphicon-chevron-up');
        }
    });
});

$("#mealplan_save_section_submit_button").click(function(){
    trigger_save_current_mealplan($("#mealplan_save_section_input").val(),$(".ql-editor").html());
    $("#mealplan_save_section_input").val("");
});

$('#mealplan_save_section_input').keypress(function (e) {
  if (e.which == 13) {
    $('#mealplan_save_section_submit_button').click();
  }
});