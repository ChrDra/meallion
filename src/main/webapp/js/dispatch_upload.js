function showPreview(url){
    $("#preview_img").attr("src",url);
    $("#preview_img_section").slideDown(200,function(){
        
    });
}

/*alert("hi1");
$("#tags_input").tagify();
alert("hi2");*/


// vanilla component
var input = document.querySelector('input[name=tags]'),


// with settings passed
tagify = new Tagify( input, {
    duplicates: true,
    whitelist: ['foo', 'bar'],
    callbacks: {
        
    }
});


$(".tags_update_button").click(function(){
    alert($("#tags_input").serialize());
    $.ajax({url:"Upload",method: "POST", data: {"tags_update" : $("#tags_input").serialize()}}).done(function(data){
        alert("updated!");		
	});
});