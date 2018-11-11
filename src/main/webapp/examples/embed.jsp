<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Meallion Embed Beispiel</title>
        
        <link rel="icon" href="https://www.meallion.de/images/elements/meallion.ico" type="image/x-icon">

        <!-- font-awesome -->
        <link href="https://www.meallion.de/css/font-awesome.min.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <!--[if IE 7]>
        <link href="css/font-awesome-ie7.css" rel="stylesheet">
        <![endif]-->
        <!-- font- signika medium -->
        <link href="https://fonts.googleapis.com/css?family=Signika" rel="stylesheet">

        <!-- Bootstrap core CSS -->
        <link href="https://www.meallion.de/css/bootstrap.css" rel="stylesheet">

        <!-- custom CSS -->
        <link href="https://www.meallion.de/css/style.css" rel="stylesheet">
        
        <link href="https://www.meallion.de/css/header.css" rel="stylesheet">

            
            <!--additional head tags -->
            <link href="https://www.meallion.de/css/dispatch_recipe.css" rel="stylesheet">
            <link href="https://www.meallion.de/css/dispatch_recipe.css" rel="stylesheet">
            
            <meta property="og:title" content="Krautwickel mit Reis und Quark" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="/meals-v1/CookBook?recipe=krautwickel-mit-reis-und-quark" />
            <meta property="og:image" content="https://www.meallion.de/images/recipes/krautwickel-mit-reis-und-quark-small.jpg" />	
        
    </head>
    <body>
        <%@include file="../standard_head.jsp"%>
        <div class="container main-page-container">

                <div id="headline_section" class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 recipe-headline-left">
                        <div id="recipe_image" class="ingredient-element-img" style="background-image: url(https://www.meallion.de/images/recipes/krautwickel-mit-reis-und-quark-small.jpg)"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 recipe-headline-right">
                        <h1>Krautwickel mit Reis und Quark</h1><br>
                        <div class="recipe-short-descr">
                            [Beschreibung]
                        </div>
                        <div class="recipe-headline-features col-12">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">

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

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        
                
                <div id="recipe_section">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12">
                            <h2>Zubereitung</h2><br>
                        </div>
                    </div>

                    
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4">1</font></div>
                                    
									Kohlkopf etwa 5-8 Min. in Wasser kochen, Strunk anschneiden und grosse Bl&auml;tter l&ouml;sen. Reis bissfest kochen, Zwiebel sch&auml;len und klein schneiden. 
                                </div>
                            </div>
                        
                        
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4">2</font></div>
                                    Zwiebel, Ei und Reis gut vermischen, mit Salz, Pfeffer und etwas Basilikum abschmecken. Masse in gleiche Portionen teilen und in Kohlbl&auml;tter wickeln.
                                </div>
                            </div>
                        
                        
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4">3</font></div>
                                    Olivenoel in die Pfanne geben, Kohlrouladen anbraten. Quark und Lorbeerbl&auml;tter zuf&uuml;gen und ca. 30 Min. schmoren lassen. Mit Salz und Pfeffer abschmecken, mit Basilikum und Petersilie bestreuen.
                                </div>
                            </div>
                        
                        
                </div>
                
            </div>

    </body>
</html>
