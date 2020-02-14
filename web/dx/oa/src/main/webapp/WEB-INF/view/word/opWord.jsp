<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/jquery.min.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>

    <style>
        #main{
            width:1040px;
            height:890px;
            border:#83b3d9 2px solid;
            background:#f2f7fb;

        }
        #shut{
            width:45px;
            height:30px;
            float:right;
            margin-right:-1px;
        }
        #shut:hover{
        }
    </style>
    <script type="text/javascript">
        var timer1 = "";
        //初始加载模板列表
        function load() {
            if (getQueryString("mb") != null)
                document.getElementById("letterModeId").value = getQueryString("mb");
        }
        function onProgressComplete(){
            hideRevision();
        }
        //获取url参数 
        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            else
                return null;
        }

        //套红
        function taoHong() {
            $("#form1").submit();
        }

        //保存并关闭
        function saveAndClose() {
            document.getElementById('PageOfficeCtrl1').WebSave();
            hideRevision();
            POBrowser.closeWindow();
        }
        //显示
        function showRevision() {
            document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
        }
        //隐藏
        function hideRevision() {
            document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
        }
        function ShowPrintDlg() {
            document.getElementById("PageOfficeCtrl1").ShowDialog(4); //打印对话框
        }
        //加盖印章
        function InsertSeal() {
            try {
                document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSeal();
            } catch(e) {}
        }
       
        
    </script>

</head>
<body onload="load();" >
<div id="content">
    <div id="textcontent" style="width: 1000px; height: 800px;">
        <div class="flow4">
            <form  action="${ctx}/open/oaWord.json?processDefinitionId=${processDefinitionId}" method="post" id="form1" >
                <strong>模板列表：</strong>
						<span style="color: Red;"> 
                            <select name="letterModeId" id="letterModeId" style='width: 240px;'>
								<c:forEach items="${letterModelList}" var="letterMode">
                                <option value='${letterMode.id}' <c:if test="${letterModeId == letterMode.id}">selected="selected"</c:if> >
									${letterMode.modelName}
								</option>
                                </c:forEach>
							</select>
                        </span>
						<span style="color: Red;"><input type="button" value="一键套红"
                                                         onclick="taoHong()"/> </span>
						<span style="color: Red;"><input type="button" value="保存关闭"
                                                         onclick="saveAndClose()"/> </span>
            </form>
        </div>
        <!--**************   卓正 PageOffice组件 ************************-->
        <div  style="height: 900px; width: 100%; overflow: hidden;">
            <%=
            request.getAttribute("pageoffice")
            %>
        </div>
    </div>
</div>
</body>
</html>
