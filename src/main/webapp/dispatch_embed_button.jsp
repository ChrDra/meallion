<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  
    int[] id_portions_set = (int[]) request.getAttribute("id_portions_set");

%>

<html>
    <head>
        <%@include file="standard_head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/embed/embed.css" rel="stylesheet">
    </head>
    <body>
        <button id="add_to_cart" class="btn btn-primary">
            <img class="add_to_cart_button_ico" src="https://www.meallion.de//images/elements/meallion.ico">&nbsp;&nbsp;&nbsp;Zum Men&uuml; Hinzuf&uuml;gen
        </button>
        
        <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
        <script>
            
            $("#add_to_cart").click(function(){
                $.ajax({url:"CookBook", data: {"command" : 2,"recipeid" : <% out.print(id_portions_set[0]); %>, "portions": <% out.print(id_portions_set[1]); %>, "request_ingredient_list": "false","mealplan_keyword": "custom_mealplan"}}).done(function(data){});
                });
            
        </script>
    </body>
</html>
