<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  
    int[] id_portions_set = (int[]) request.getAttribute("id_portions_set");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <button id="add_to_cart" class="btn btn-primary">
            <img class="button_ico" src="https://www.meallion.de//images/elements/meallion.ico"> Hinzufügen#<% out.print(id_portions_set[0]); %>
        </button>
        
        <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/bootstrap.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/bootbox.min.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/plugins.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/banzhow.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/meallion.js"></script>
        <script type="text/javascript" src="https://meallion.de/js/detectmobile.js"></script>
        <script>
            $("add_to_cart").click(function(){
            
            
                $.ajax({url:"www.meallion.de/CookBook/", data: {"command" : 2,"recipeid" : <% out.print(id_portions_set[0]); %>, "portions": <% out.print(id_portions_set[1]); %>, "request_ingredient_list": "false","mealplan_keyword": "custom_mealplan"}}).done(function(data){});
                    $("add_to_cart").html("hs");
                    console.log("h");
                });
            
        </script>
        
    </body>
</html>
