    
<!-- dispatch_recipe

CookBook Servlet dispatches this jsp sothat URL is nice

CSS:
dispatch_recipe.css

JS:
meallion.js
script is needed in here

-->












<!DOCTYPE html>
<html lang="en">
	<head>
            <!--standard head tags like title and meta data: -->
            

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Meallion</title>

<link rel="icon" href="/images/elements/meallion.ico" type="image/x-icon">

<!-- font-awesome -->
<link href="css/font-awesome.min.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!--[if IE 7]>
<link href="css/font-awesome-ie7.css" rel="stylesheet">
<![endif]-->
<!-- font- signika medium -->
<link href="https://fonts.googleapis.com/css?family=Signika" rel="stylesheet">

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<!-- custom CSS -->
<link href="css/style.css" rel="stylesheet">
<!--[if lte IE 8]>
<link href="css/ie8.css" rel="stylesheet">
<![endif]-->
<!--[if lte IE 7]>
<link href="css/ie7.css" rel="stylesheet">
<![endif]-->

<link href="css/header.css" rel="stylesheet">

            
            <!--additional head tags -->
            <link href="css/dispatch_recipe.css" rel="stylesheet">
            <link href="css/dispatch_recipe.css" rel="stylesheet">
            
            <meta property="og:title" content="Krautwickel mit Reis und Quark" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="/meals-v1/CookBook?recipe=krautwickel-mit-reis-und-quark" />
            <meta property="og:image" content="https://www.meallion.de/images/recipes/krautwickel-mit-reis-und-quark-small.jpg" />	
	</head>
	
        <body>
            <!-- header

First tag in body of every page.
Loads navbar and other general data

-->

    <!-- BEGIN NAVBAR -->
    
    <nav class="navbar navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    
                    <span class="sr-only">Toggle navigation</span>
                    <span style="background-color: #ff9400;" class="icon-bar"></span>
                    <span style="background-color: #ff9400;" class="icon-bar"></span>
                    <span style="background-color: #ff9400;" class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Meallion</a>
                <font size="5"><b><i>BETA</i></b></font>
            </div>
            
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav navbar-right navigation-text-1">
                    <li><a href="">Mein Menu</a></li>
                    
                    <!-- PLACEHOLDER FOF NAVBAR ELEMENTS -->

                </ul>
            </div>
        </div>
    </nav>

    
<!-- END NAVBAR -->

            
            <div class="container main-page-container">

                <div id="headline_section" class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 recipe-headline-right">
                        <h1>Krautwickel mit Reis und Quark</h1><br>
                        <div class="recipe-short-descr">
                            [Beschreibung]
                        </div>
                        <div class="recipe-headline-features col-12">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="recipe-headline-features-inmenu" data-selected="false">
                                        Hinzufuegen
                                        <div id="recipe-feature-59" class="recipe-headline-features-inmenu-img-placeholder center">
                                            <img id="recipe-headline-features-inmenu-img" class="recipe-headline-features-inmenu-img" src="https://www.meallion.de/images/elements/chef-hat.svg">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <button id="portions_select_button" class="recipe-headline-features-portionselector btn btn-secondary dropdown-toggle selection-style" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            W&auml;hle Portionen<br><span id="portions_select_button_number">1<span class="caret"></span>
                                    </button></span>
                                    <ul id="portion_select_dropdown_div" class="dropdown-list dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <li id="portions_select_1"><span class="drop_button">1</li>
                                        <li id="portions_select_2"><span class="drop_button">2</span></li>
                                        <li id="portions_select_3"><span class="drop_button">3</span></li>
                                        <li id="portions_select_4"><span class="drop_button">4</span></li>
                                        <li id="portions_select_5"><span class="drop_button">5</span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        
                <div id="ingredient_section">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                <h2>Zutaten (&#x20AC 2.17 pro Portion) </h2><br>
                            </div>
                        </div>

                    <div class="row">
                    
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-122" data-price="0.005" data-amount="166.666666666667" data-friendly-amount="0.333333333333334">
                                <div class="ingredient-element-name">
                                    <b>Wei&szlig;kohl</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >166.7</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 0.3</span> St&uuml;ck
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/whitecabbage-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.83</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-3" data-price="0.001" data-amount="100.0" data-friendly-amount="100.0">
                                <div class="ingredient-element-name">
                                    <b>Reis</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >100.0</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/rice-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.10</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-7" data-price="0.0013" data-amount="60.0" data-friendly-amount="0.3333333333333333">
                                <div class="ingredient-element-name">
                                    <b>Zwiebel</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >60.0</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 0.3</span> St&uuml;ck
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/onion-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.08</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-10" data-price="0.16" data-amount="0.333333333333333" data-friendly-amount="0.333333333333333">
                                <div class="ingredient-element-name">
                                    <b>Ei</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >0.3</span> St&uuml;ck
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/egg-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.05</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-136" data-price="0.0466666666666667" data-amount="3.0" data-friendly-amount="1.0">
                                <div class="ingredient-element-name">
                                    <b>Basilikum</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >3.0</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 1.0</span> Blatt
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/basil-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.14</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-61" data-price="0.2" data-amount="0.666666666666667" data-friendly-amount="0.666666666666667">
                                <div class="ingredient-element-name">
                                    <b>Lorbeerblatt</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >0.7</span> St&uuml;ck
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/bayleaf-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.13</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-24" data-price="0.01" data-amount="6.66666666666667" data-friendly-amount="6.66666666666667">
                                <div class="ingredient-element-name">
                                    <b>Oliven&ouml;l</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >6.7</span> ml
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/oliveoil-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.07</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-4" data-price="0.00166666666666667" data-amount="110.0" data-friendly-amount="1.0">
                                <div class="ingredient-element-name">
                                    <b>Tomate</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >110.0</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 1.0</span> St&uuml;ck
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/tomato-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.18</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-11" data-price="0.01" data-amount="6.66666666666667" data-friendly-amount="0.666666666666667">
                                <div class="ingredient-element-name">
                                    <b>Salz</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >6.7</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 0.7</span> Teel&ouml;ffel
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/salt-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.07</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-46" data-price="0.04" data-amount="1.66666666666667" data-friendly-amount="0.33333333333333404">
                                <div class="ingredient-element-name">
                                    <b>Pfeffer</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >1.7</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 0.3</span> Teel&ouml;ffel
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/pepper-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.07</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-69" data-price="0.04" data-amount="10.0" data-friendly-amount="0.6666666666666666">
                                <div class="ingredient-element-name">
                                    <b>Petersilie</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >10.0</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <span class="ingredient-friendly-amount" >ca. 0.7</span> Stiel
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/parsley-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.40</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ingredient-element-placeholder">
                            <div class="ingredient-element" id="ingredient-54" data-price="0.0014" data-amount="33.3333333333333" data-friendly-amount="33.3333333333333">
                                <div class="ingredient-element-name">
                                    <b>Quark</b>
                                </div>
                                <div class="ingredient-element-amount">
                                    <span class="ingredient-amount" >33.3</span> Gramm
                                </div>
                                
                                
                                    <div class="ingredient-element-friendly-amount">
                                        <br>
                                    </div>
                                
                                
                                
                                <div class="ingredient-element-img-placeholder">
                                    <div class="ingredient-element-img ingredients_animation-target" style="background-image: url('https://www.meallion.de/images/ingredients/curd-small.jpg')"></div>
                                </div>
                                <div class="ingredient-element-price">
                                    &#8364; <span class="ingredient-price">0.05</span>
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
                                    
Kohlkopf etwa 5-8 Min. in Wasser kochen, Strunk anschneiden und grosse Bl&auml;tter l&ouml;sen.Reis bissfest kochen, Zwiebel sch&auml;len und klein schneiden. 
                                </div>
                            </div>
                        
                        
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4">2</font></div>
                                    Zwiebel, Ei und Reis gut vermischen, mit Salz, Pfeffer und etwas Basilikum abschmecken.Masse gleiche Portionen teilen in Kohlbl&auml;tter wickeln.
                                </div>
                            </div>
                        
                        
                        
                            <div class="row recipe-row">
                                <div class="col-md-12" class="col-lg-12">
                                    <div class="label label-default step-numbering"> <font size="4">3</font></div>
                                    Oliven&ouml;l in die Pfanne geben, Kohlrouladen anbraten. Quark und Lorbeerbl&auml;tter zuf&uuml;gen und ca. 30 Min. schmoren lassen. Mit Salz und Pfeffer abschmecken, mit Basilikum und Petersilie bestreuen..
                                </div>
                            </div>
                        
                        
                </div>
                
            </div>
                
            <footer>
  <div class="container">

	<div class="row">
	  <div class="col-lg-4 col-md-4">
		<a class="footer-brand" href="http://localhost:8085/Meallion">Meallion</a>
		<p></p>
		<ul class="list-inline list-unstyled social-networks">
		  <li>
			<a href="#">
			  <div class="icon-social icon-social-facebook normal">
				<i class="fa fa-facebook"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-facebook"></i>
			  </div>
			</a>
		  </li>

		  <li>
			<a href="#">
			  <div class="icon-social icon-social-twitter normal">
				<i class="fa fa-twitter"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-twitter"></i>
			  </div>
			</a>
		  </li>

		  <li>
			<a href="#">
			  <div class="icon-social icon-social-google-plus normal">
				<i class="fa fa-google-plus"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-google-plus"></i>
			  </div>
			</a>
		  </li>

		  <li>
			<a href="#">
			  <div class="icon-social icon-social-youtube normal">
				<i class="fa fa-youtube-play"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-youtube-play"></i>
			  </div>
			</a>
		  </li>

		  <li>
			<a href="#">
			  <div class="icon-social icon-social-linkedin normal">
				<i class="fa fa-linkedin"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-linkedin"></i>
			  </div>
			</a>
		  </li>

		  <li>
			<a href="#">
			  <div class="icon-social icon-social-pinterest normal">
				<i class="fa fa-pinterest"></i>
			  </div>
			  <div class="icon-social hover">
				<i class="fa fa-pinterest"></i>
			  </div>
			</a>
		  </li>
		</ul>
	  </div>
	  <div class="col-lg-4 col-md-4">
		<h3>KONTAKT</h3>
		<address>
		  <i class="fa fa-phone"></i> +49 (0) 173 6989366<br>
		  <i class="fa fa-envelope"></i> info@meallion.de
		</address>
	  </div>			  
	  

	  <div class="col-lg-4 col-md-4">
		<h3>REICHE REZEPTE MIT BILDERN EIN</h3>
		<p>rezepte@meallion.de</p>
	  </div>
	</div><!-- /.row -->

  </div><!-- /.container -->
</footer>

<div class="footer-after">
  <div class="container">
	<div class="row">
	  <p class="col-md-10">Copyright 2017 All rights reserved. Built by Christoph. Credits to: John (banzhow), glyphicons.com, tagify.js, bootstrap, quill.js.</p>
	</div><!-- /.row -->
  </div>
</div>

<!-- JavaScript -->
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootbox.min.js"></script>
<script type="text/javascript" src="js/plugins.js"></script>
<script type="text/javascript" src="js/banzhow.js"></script>
<script type="text/javascript" src="js/meallion.js"></script>
<script type="text/javascript" src="js/detectmobile.js"></script>

<script>
    $("#contact_mail_input_button").click(function(e) {
        send_email_address($("#contact_mail_input").val());
    });
    
</script>
<!--[if lte IE 7]>
<script type="text/javascript" src="js/boxsizing/jquery.boxsizing.js"></script> 
<![endif]-->

            
            <!--<script type="text/javascript" src="js/dispatch_recipe.js">-->
            
            <script>
                
                trigger_personchange(3);
                
                $("#portions_select_1").click(function(){
                    trigger_personchange(1);
                });

                $("#portions_select_2").click(function(){
                    trigger_personchange(2);
                });

                $("#portions_select_3").click(function(){
                    trigger_personchange(3);
                });

                $("#portions_select_4").click(function(){
                    trigger_personchange(4);
                });

                $("#portions_select_5").click(function(){
                    trigger_personchange(5);
                });
                
                $("#portions_select_6").click(function(){
                    trigger_personchange(6);
                });
                
                $("#portions_select_7").click(function(){
                    trigger_personchange(7);
                });
                
                $("#portions_select_8").click(function(){
                    trigger_personchange(8);
                });
                
                $('#recipe_image').css('cursor', 'pointer');
                $("#recipe_image").click(function(){
                    
                    bootbox.alert({
                        message: '<img style="width: 100%; heigh: 100%;" src="https://meallion.de/images/recipes/krautwickel-mit-reis-und-quark-large.jpg">',
                        className : "recipe_img_popup",
                        size: 'large',
                        backdrop: true,
                        buttons: {
                            ok: {
                                label: "Zur&uuml;ck",
                                callback: function(){
                                }
                            }
                        }
                    });
                });
                
                
                        $("#recipe-feature-59").data("selected",false);
                

                $(".recipe-headline-features-inmenu").click(function(e) {
                    e.stopPropagation();
                    
                    if($(this).data("selected")){
                        
                        //load dialog box
                        var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="https://www.meallion.de/images/elements/Spinner-5.9s-200px.gif"> Wird entfernt..',
                            closeButton: false
                        });
                        var timeout = 1000; 
                        setTimeout(function () {
                            dialog1.modal('hide');
                        }, timeout);    
                        // end load dialog box
                            
                        $('#recipe-headline-features-inmenu-img').attr('src','https://www.meallion.de/images/elements/chef-hat.svg');
                        $(this).data("selected",false);
                        trigger_menuchange(59,"0","false","custom_mealplan",function(){});
                    }else{
                        
                        //load dialog box
                        var dialog1 = bootbox.dialog({
                            message: '<img class="dialog_box_spinner" src="https://www.meallion.de/images/elements/Spinner-5.9s-200px.gif"> Wird hinzugef&uuml;gt..',
                            closeButton: false
                        });
                        var timeout = 1000; 
                        setTimeout(function () {
                            dialog1.modal('hide');
                        }, timeout);    
                        // end load dialog box
                        
                        $('#recipe-headline-features-inmenu-img').attr('src','https://www.meallion.de/images/elements/chef-hat_selected.svg');
                        $(this).data("selected",true);
                        trigger_menuchange(59,"1","false","custom_mealplan",function(){});
                    }
                });
                
            </script>

    </body>
    
</html>

