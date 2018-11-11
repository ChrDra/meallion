<%-- 
    Document   : plain_embed_button
    Created on : Nov 10, 2018, 2:58:05 PM
    Author     : chris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <!--START EMBED-->
        <link href="https://fonts.googleapis.com/css?family=Signika" rel="stylesheet">
        <link href="https://www.meallion.de/css/bootstrap.css" rel="stylesheet">
        <link href="css/embed.css" rel="stylesheet">
        <button id="meallion_add_to_cart" class="btn btn-primary">
            <img id="meallion_button_ico" src="https://www.meallion.de//images/elements/m.svg">
            &nbsp;&nbsp;Zutaten zu meinem Men&uuml; hinzuf&uuml;gen
        </button>
        <div id="meallion_modal" class="meallion_modal">
        <div class="meallion_modal-content">
            <span id="meallion_close_modal" class="meallion_close">&times;</span>
            <iframe frameBorder='0' src='http://localhost:8085/Meallion/Embed?recipe=kartoffel-tomaten-gratin' style='width:100%; height:90%;'></iframe>
        </div>
        </div>
        <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script>
                $("#meallion_add_to_cart").hover(function(e) {
                    $("#meallion_button_ico").addClass("meallion_animation-target");
                }, function(){
                    $("#meallion_button_ico").removeClass("meallion_animation-target");
                });
                var modal = document.getElementById('meallion_modal');
                var btn = document.getElementById("myBtn");
                var span = document.getElementsByClassName("close")[0];
                $("#meallion_add_to_cart").click(function(e) {
                    modal.style.display = "block";			
                });
                $("#meallion_close_modal").click(function(e) {
                    modal.style.display = "none";
                });
                $(window).click(function(e) {
                    if (e.target == modal) {
                        modal.style.display = "none";
                    }
                });
                modal.style.display = "none";
        </script>
        <!--END EMBED-->
        
    </body>
</html>
