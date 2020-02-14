<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/jquery-1.11.3.min.js"></script>
    <script src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">
        function save(){
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
</head>
<body>
<div id="content"
     style="height: 830px; width: 100%; z-index: 100; overflow: hidden;">
    <%=
    request.getAttribute("pageoffice")
    %>
</div>
</body>
</html>
