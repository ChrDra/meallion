<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Meallion</title>

        <link rel="icon" href="https://meallion.de/images/elements/meallion.ico" type="image/x-icon">

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

        
        <link href="css/dispatch_recipes_offering.css" rel="stylesheet">
        <link href="css/tagify.css" rel="stylesheet">
        <link href="css/index.css" rel="stylesheet">
        <link href="css/control_elements.css" rel="stylesheet">
        <link href="css/slick/slick.css" rel="stylesheet" type="text/css">
        <link href="css/slick/slick-theme.css" rel="stylesheet" type="text/css">
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
                        <li><a href="/Menu">Mein Menu</a></li>
                        
                        <!-- PLACEHOLDER FOF NAVBAR ELEMENTS -->

                    </ul>
                </div>
            </div>
        </nav>

        
        <!-- END NAVBAR -->

        
        <!-- BEGIN CAROUSEL DESKTOP -->

        <div id="desktop_carousel" class="carousel_container">
            <div class="slick_carousel">
                <div>
                    <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                        <source src="https://meallion.de/videos/main_carousel-1.mp4">
                        <img src="https://meallion.de/images/elements/mobile_carousel-small.jpg">
                    </video>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Dein t&auml;gliches Kochbuch.</span></h2>
                    </div>
                </div>
                <div>
                    <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                        <source src="https://meallion.de/videos/main_carousel-1.mp4">
                        <img src="https://meallion.de/images/elements/mobile_carousel-small.jpg">
                    </video>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Stelle Dein individuelles Men&uuml; zusammen.</span></h2>
                    </div>
                </div>
                <div>
                    <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                        <source src="https://meallion.de/videos/main_carousel-1.mp4">
                        <img src="https://meallion.de/images/elements/mobile_carousel-small.jpg">
                    </video>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Finde Deine perfekten Wochenmen&uuml;s.</span></h2>
                    </div>
                </div>
            </div>
        </div>

        <!-- END CAROUSEL -->
        
        <!-- BEGIN CAROUSEL MOBILE -->

        <div id="mobile_carousel" class="carousel_container" hidden>
            <div class="slick_carousel">
                <div>
                    <div class="carousel_image" style="background-image:url('https://meallion.de/images/slide/slide1.jpg');"></div>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Dein t&auml;gliches Kochbuch.</span></h2>
                    </div>
                </div>
                <div>
                    <div class="carousel_image" style="background-image:url('https://meallion.de/images/slide/slide1.jpg');"></div>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Stelle Dein individuelles Men&uuml; zusammen.</span></h2>
                    </div>
                </div>
                <div>
                    <div class="carousel_image" style="background-image:url('https://meallion.de/images/slide/slide1.jpg');"></div>
                    <div class="carousel-caption">
                        <h2><span class="carousel-batch">Finde Deine perfekten Wochenmen&uuml;s.</span></h2>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- END CAROUSEL MOBILE-->
            
        <div class="container">
            
            <div class="row" id="recipe_menu_switch_element">
                <div class="col-lg-3 col-md-2"></div>
                <div class="col-lg-6 col-md-8 col-sm-12 green-border">
                    <button type="button" id="search_recipes_button" class="recipes_menu_switch_button btn btn-primary">Rezepte</button>
                    <button type="button" id="search_menus_button" class="recipes_menu_switch_button btn btn-primary">Men&uuml;s</button>
                </div>
            </div>
            
            <div class="row search_section">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <div class="main_search_input_group">
                        <div class="col-lg-10 col-md-10 col-sm-12">
                            <input name='tags' class="main_search_input_group_tags" id="tags_input" placeholder='Suche nach Zutaten - oder Beschreibungen'><br>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-12">
                            <button class="btn btn-primary main_search_input_button" id="main_search_input_button" type="button">Suchen</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row toggle_section">
                <div class="col-lg-3 col-md-6 col-sm-12 toggle_section_element_placeholder" id="budget_chooser_box">
                    <div class="green-border toggle_section_element">
                        <div class="toggle_section_element_text toggle_section_element_content">Budget</div>
                        <div class="slidecontainer toggle_section_range_slider toggle_section_element_content">
                            <input id="toggle_section_element_budget" type="range" min="0.10" max="5.10" value="5.10" step="0.1" class="range_slider">
                        </div>
                        <div id="control_output_budget" class="toggle_section_element_content">Keine Pr&auml;ferenz</div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 toggle_section_element_placeholder" id="time_chooser_box">
                    <div class="green-border toggle_section_element">
                        <div class="toggle_section_element_text toggle_section_element_content">Zeit</div>
                        <div class="slidecontainer toggle_section_range_slider toggle_section_element_content">
                            <input id="toggle_section_element_time" type="range" min="10" max="70" value="70" step="10" class="range_slider toggle_section_range_slider">
                        </div>
                        <div id="control_output_time" class="toggle_section_element_content">Keine Pr&auml;ferenz</div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 toggle_section_element_placeholder">
                    <div class="green-border toggle_section_element">
                        <div class="toggle_section_element_text toggle_section_element_content">Nur Veggie</div>
                        
                        <div class="toggle_section_element_content">
                            <label class="switch">
                                <input type="checkbox" id="toggle_section_element_veggie">
                                <span class="checkbox_slider round"></span>
                            </label>
                        </div>
                        
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 toggle_section_element_placeholder">
                    <div class="green-border toggle_section_element">
                        <div class="toggle_section_element_text toggle_section_element_content">Nur Vegan</div>
                        
                        <div class="toggle_section_element_content">
                            <label class="switch">
                                <input type="checkbox" id="toggle_section_element_vegan">
                                <span class="checkbox_slider round"></span>
                            </label>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="page-header">
                <h1>My Ingredients</h1>
            </div>

            

        </div>

        <!-- Extra OFFER -->
        <div class="container">
            <div class="page-header">
                <h1>Option</h1>
            </div>
            <div class="extraOption green-border">
                <span class="optTitle mt-10">Do you want extra cheeze?</span>
                <span class="optBtn mt-10">
                    <div class="toggle_section_element_content no-margin">
                        <label class="switch no-margin">
                            <input type="checkbox" id="toggle_section_element_veggie">
                            <span class="checkbox_slider round"></span>
                        </label>
                    </div>
                </span>

                <div id="amount_picker" class="mt-10 mr-10 input-group orange-border masry-featGroup">
                    <span class="input-group-btn">
                        <button type="button" class="quantity-left-minus btn btn-number">
                            <span class="glyphicon glyphicon-minus"></span>
                        </button>
                    </span>
                    <input type="text" id="quantity" name="quantity" class="form-control input-number" value="10" min="1" max="100">
                    <span class="input-group-btn">
                        <button type="button" class="quantity-right-plus btn btn-number">
                            <span class="glyphicon glyphicon-plus"></span>
                        </button>
                    </span>
                </div>

                <b class="masry-price pull-right mr-10 mt-12">EUR 1.66</b>

            </div>
        </div>

        <!-- BEGIN OFFERING -->
        <div class="container offering-container">
            <div id="offering_placeholder">
                
                
            </div>
        </div><!-- END OFFERING -->

        
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

        <script type="text/javascript" src="js/bootstrap.min.js"></script>
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

        <script type="text/javascript" src="https://meallion.de/js/tagify.js"></script>
        <script type="text/javascript" src="js/tagify.min.js"></script>
        <script type="text/javascript" src="js/mainsearch.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <script type="text/javascript" src="js/masonry.pkgd.min.js"></script>
        <script type="text/javascript" src="js/jquery.isotope.min.js"></script>
        <script type="text/javascript" src="js/imagesloaded.pkgd.min.js"></script>
        <script type="text/javascript" src="js/slick/slick.min.js"></script>

        <script>
            if(jQuery.browser.mobile){
                $("#mobile_carousel").show();
                $("#desktop_carousel").hide();
            }
            
            $( window ).resize(function() {
                if($( window ).width()<450){
                    $("#mobile_carousel").show();
                    $("#desktop_carousel").hide();
                }else{
                    $("#mobile_carousel").hide();
                    $("#desktop_carousel").show();
                }
            });
            
            window.onload = function () {
                //fight Chrome bug: add muted attribute manually and autoplay video:
                var autoplay_video = document.getElementsByClassName("autoplay_video");
                    
                var i=0;
                for(i=0;i<autoplay_video.length;i++){
                    autoplay_video[i].muted = true;
                    autoplay_video[i].play();
                }
            }
             
            function startWaterfall(){
                alert("hi");
                // masonry
                var e=$(window).width();
                var t=$(".container-gallery");
                t.imagesLoaded(function(){t.masonry()});
                alert("hi");
                var n=$(".wrapper-portfolio");
                t.imagesLoaded(function(){n.masonry()});
            }
            
            $(document).ready(function() {
                       
                $("#toggle_section_element_time").prop("value", "70");
                $("#toggle_section_element_budget").prop("value", "5.10");
                
                $("#search_recipes_button").focus();
                
                $('.slick_carousel').slick({
                    autoplay: true,
                    autoplaySpeed: 4500,
                    speed: 1800,
                    fade: true,
                    cssEase: 'linear'
                });
                
                set_default_request();
                trigger_selection(startWaterfall);
		
                if (navigator.appName == 'Microsoft Internet Explorer' ||  !!(navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/rv:11/)) || (typeof $.browser !== "undefined" && $.browser.msie == 1)){
                    bootbox.alert({
                        message: 'Du verwendest Internet Explorer. Wir k&ouml;nnen nicht garantieren, dass der Seiteninhalt korrekt dargestellt wird. Wir empfehlen daher Chrome oder Firefox.',
                        className : "recipe_img_popup",
                        size: 'large',
                        buttons: {
                            ok: {
                                label: "Alles klar, verstanden!",
                                callback: function(){
                                }
                            }
                        }
                    });
                }
                
            });
        </script>
    </body>
</html>
