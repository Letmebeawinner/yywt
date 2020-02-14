<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">
        function onProgressComplete(){
            document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
        }
        function ShowPrintDlg() {
            document.getElementById("PageOfficeCtrl1").ShowDialog(4); //打印对话框
        }
        if(${1==type}) {
            //显示
            function showRevision() {
                document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
            }
            //隐藏
            function hideRevision() {
                document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
            }
        }
    </script>
</head>
<body>
<h1 style="margin: 0 auto;"></h1>
<div id="content"
     style="height: 900px; width: 100%; overflow: hidden;">
    <%=request.getAttribute("pageoffice")
    %>
</div>
</body>
</html>
