<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加成果类型</title>
    <style>
        em {
            color: red;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/admin/js/category.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">添加成果类型</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行添加成果类型。<br>
                2. 带有红色 <span style="color: red">*</span> 标记的内容为必填部分。<br>
                3. 排序值越高, 显示越靠前。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label> <em>*</em> 成果形式</label>
                    <span class="field">
                        <select name="category.resultFormId" id="resultFormId">
                            <c:forEach items="${resultForms}" var="r">
                                <option value="${r.id}">${r.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 成果类型名称</label>
                    <span class="field"><input type="text" name="category.name" id="name" maxlength="225"
                                               class="longinput"/></span>
                </p>

                <p>
                    <label>排序</label>
                    <span class="field"><input type="text" name="category.sort" id="sort" maxlength="8"
                                               class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="saveForm();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>