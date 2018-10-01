$(".portfolio-element").hover(
        function() {
                $(this).find('.portfolio-element-img').css('filter','saturate(110%)');
                $(this).find('.portfolio-element-text').css('background-color','#fff1dd');
        },
        function() {
                $(this).find('.portfolio-element-img').css('filter','saturate(80%)');
                $(this).find('.portfolio-element-text').css('background-color','#ffffff');
        }
);