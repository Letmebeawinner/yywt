<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案处审批详情</title>
</head>

<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list " style="text-align: center;">
            档案处审批详情
        </h1>
    </div>
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>调阅人姓名</label>
                    <span class="field">
                        <input type="text"  value = "${applyName}" class="longinput" readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主要内容和目的</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" id="content" readonly>${oaArchive.content}</textarea>
                    </span>
                </p>

                <p class = "printHide">
                    <label>审核状态</label>
                    <span class="field">
                    <c:if test = "${oaArchive.audit == 1}">
                        审批已通过
                    </c:if>
                    <c:if test = "${oaArchive.audit == 0}">
                        审核中
                    </c:if>
                    <c:if test = "${oaArchive.audit == 2}">
                        已拒绝
                    </c:if>
                </span>
                </p>
                <p class="stdformbutton printHide" style="text-align: center">
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>
<script>
    function printDocument() {
        jQuery("#print").hide();
        jQuery(".printHide").hide();
        jQuery(".header").hide();
        jQuery(".iconmenu").hide();
        jQuery(".centercontent").css("marginLeft","0");
        jQuery("textarea").css("min-height","102px");
        jQuery("td").css("border","1px solid #999");


        // return;
        window.print();
        jQuery("#print").show();
        jQuery(".printHide").show();
        jQuery(".header").show();
        jQuery(".iconmenu").show();
        jQuery(".centercontent").css("marginLeft","181px");
        jQuery("td").css("border","1px solid #ddd");
        jQuery("textarea").css("height", "100%");

    }
</script>
</body>
</html>