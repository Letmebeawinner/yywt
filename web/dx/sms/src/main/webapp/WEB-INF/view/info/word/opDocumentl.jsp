<%@ taglib prefix="po" uri="http://java.pageoffice.cn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/jquery-1.8.3.min.js"></script>
    <script src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">
        function save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
        function AfterDocumentOpened() {
            //alert(document.getElementById("PageOfficeCtrl1").Caption);
        }
        function SetBookmarks() {
            document.getElementById("PageOfficeCtrl1").BookmarksVisible = !document.getElementById("PageOfficeCtrl1").BookmarksVisible;
        }

        function Print() {
            document.getElementById("PageOfficeCtrl1").ShowDialog(4);
        }
        function SwitchFullScreen() {
            document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
        }
        function SetPageReal() {
            document.getElementById("PageOfficeCtrl1").SetPageFit(1);
        }
        function SetPageFit() {
            document.getElementById("PageOfficeCtrl1").SetPageFit(2);
        }
        function SetPageWidth() {
            document.getElementById("PageOfficeCtrl1").SetPageFit(3);
        }
        function ZoomIn() {
            document.getElementById("PageOfficeCtrl1").ZoomIn();
        }
        function ZoomOut() {
            document.getElementById("PageOfficeCtrl1").ZoomOut();
        }
        function FirstPage() {
            document.getElementById("PageOfficeCtrl1").GoToFirstPage();
        }
        function PreviousPage() {
            document.getElementById("PageOfficeCtrl1").GoToPreviousPage();
        }
        function NextPage() {
            document.getElementById("PageOfficeCtrl1").GoToNextPage();
        }
        function LastPage() {
            document.getElementById("PageOfficeCtrl1").GoToLastPage();
        }
        function RotateRight() {
            document.getElementById("PageOfficeCtrl1").RotateRight();
        }
        function RotateLeft() {
            document.getElementById("PageOfficeCtrl1").RotateLeft();
        }

    </script>
</head>
<body>
<div id="content" style="height: 830px; width: 100%; z-index: 100; overflow: hidden;">
    <c:if test="${type==1}">
        <po:PDFCtrl id="PageOfficeCtrl1"/>
    </c:if>
    <c:if test="${type==2}">
        <%=
        request.getAttribute("PageOfficeCtrl1")
        %>
    </c:if>

</div>
</body>
</html>
