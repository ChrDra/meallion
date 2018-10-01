<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
            
            <!--standard head tags like title and meta data: -->
            <%@include file="standard_head.jsp"%>
            
            <meta property="og:title" content="" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content="" />
            <meta property="og:image" content="" />
            
            <!--additional resources -->
            <link href="css/dispatch_createrecipe.css" rel="stylesheet">
            <link href="//cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
            <link href="//cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet">
            
    </head>
    
    <body>
        <%@include file="header.jsp"%>
        
        <div class="container main-page-container"> 
            <div id="headline_section" class="row">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <h1>Erstelle ein Rezept</h1>
                </div>
            </div>
            <div class="row">
                <div id="editor-container"></div>
            </div>
            
            <form action="/action_page.php">
            <input type="file" name="pic" accept="image/*">
            <input type="submit">
            </form>
            
        </div>
        
        <%@include file="footer.jsp" %>
        
        <script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
        <script src="//cdn.quilljs.com/1.3.6/quill.min.js"></script>
        <script src="js/dispatch_createrecipe.js"></script>
        
    </body>
</html>
