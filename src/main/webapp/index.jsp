<html>
    <head>
        <%@include file="standard_head.jsp" %>
        
        <link href="css/dispatch_recipes_offering.css" rel="stylesheet">
        <link href="css/tagify.css" rel="stylesheet">
        <link href="css/index.css" rel="stylesheet">
        <link href="css/control_elements.css" rel="stylesheet">
        <link href="css/slick/slick.css" rel="stylesheet" type="text/css">
        <link href="css/slick/slick-theme.css" rel="stylesheet" type="text/css">
    </head>
    
    <body>
        <%@include file="header.jsp" %>
	
        <!-- BEGIN CAROUSEL DESKTOP -->

            <div id="desktop_carousel" class="carousel_container">
                <div class="slick_carousel">
                    <div>
                        <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                            <source src="/videos/main_carousel-1.mp4">
                            <img src="/images/elements/mobile_carousel-small.jpg">
                        </video>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Dein tägliches Kochbuch.</span></h2>
                        </div>
                    </div>
                    <div>
                        <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                            <source src="/videos/main_carousel-1.mp4">
                            <img src="/images/elements/mobile_carousel-small.jpg">
                        </video>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Stelle Dein individuelles Menü zusammen.</span></h2>
                        </div>
                    </div>
                    <div>
                        <video class="show_dektop l_o_h autoplay_video" preload="auto" playsinline muted loop="loop" autoplay="autoplay" controlslist="nodownload" data-alt="Video-Clip Filmmaterial - Video">
                            <source src="/videos/main_carousel-1.mp4">
                            <img src="/images/elements/mobile_carousel-small.jpg">
                        </video>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Finde Deine perfekten Wochenmenüs.</span></h2>
                        </div>
                    </div>
                </div>
            </div>


        <!-- END CAROUSEL -->
        
        <!-- BEGIN CAROUSEL MOBILE -->
        
            <div id="mobile_carousel" class="carousel_container" hidden>
                <div class="slick_carousel">
                    <div>
                        <div class="carousel_image" style="background-image:url('/images/slide/slide1.jpg');"></div>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Dein tägliches Kochbuch.</span></h2>
                        </div>
                    </div>
                    <div>
                        <div class="carousel_image" style="background-image:url('/images/slide/slide1.jpg');"></div>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Stelle Dein individuelles Menü zusammen.</span></h2>
                        </div>
                    </div>
                    <div>
                        <div class="carousel_image" style="background-image:url('/images/slide/slide1.jpg');"></div>
                        <div class="carousel-caption">
                            <h2><span class="carousel-batch">Finde Deine perfekten Wochenmenüs.</span></h2>
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
                    <button type="button" id="search_menus_button" class="recipes_menu_switch_button btn btn-primary">Menüs</button>
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

        <!-- BEGIN OFFERING -->
        <div class="container offering-container">
            
            <!-- Upload
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <form action="Upload" id="fileuploadform" enctype="multipart/form-data" method="post">
                        <input type="file" name="file" id="selectfile" class="btn btn-primary" />
                        <input type="submit" id="submitfile" class="btn btn-primary" />
                    </form>
            
                    <button id="uploadbutton" class="btn btn-primary"/>Upload!</button>
                </div>
            </div>-->
            
            <div class="section offering-section" style="position: relative;">
                <div class="container">
                    <div class="row" id="offering_placeholder"> 
                        <div id="offering_loading_gadget">
                            <img id="offering_loading_gif" src="/images/elements/Spinner-5.9s-200px.gif">
                        </div>
                    </div><!-- /.row -->
                </div><!-- /.container -->
            </div><!-- /.section -->

            <!-- END OFFERING -->
        </div>
	
        <%@include file="footer.jsp" %>
        <script type="text/javascript" src="js/tagify.js"></script>
        <script type="text/javascript" src="js/tagify.min.js"></script>
        <script type="text/javascript" src="js/mainsearch.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <script type="text/javascript" src="js/masonry.pkgd.min.js"></script>
        <script type="text/javascript" src="js/imagesloaded.pkgd.min.js"></script>
        <script type="text/javascript" src="js/slick/slick.min.js"></script>
        <script>
            
            if(jQuery.browser.mobile){
                alert("mobile");
                $("#mobile_carousel").show();
                $("#desktop_carousel").hide();
            }
            
            $( window ).resize(function() {
                console.log($( window ).width());
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
                /*$('#offering_waterfall').masonry({
                    itemSelector : '.portfolio-element-placeholder'
                });*/
        
                var container = document.querySelector('#offering_waterfall');
                
                imagesLoaded( container, function( instance ) {
                    var msnry = new Masonry( container, {
                        itemSelector: '.portfolio-element-placeholder',
                        isAnimated: true,
                        resize: true,
                        originLeft: true,
                        transitionDuration: '1.2s',
                        animationOptions: {
                            duration: 5000,
                            easing: 'linear',
                            queue: false
                        }
                        
                    });
                    
                    msnry.layout();
                });
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
                        message: 'Du verwendest Internet Explorer. Wir können nicht garantieren, dass der Seiteninhalt korrekt dargestellt wird. Wir empfehlen daher Chrome oder Firefox.',
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
