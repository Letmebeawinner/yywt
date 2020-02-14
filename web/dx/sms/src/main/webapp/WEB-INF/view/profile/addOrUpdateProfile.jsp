<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>配置管理</title>
    <script type="text/javascript" src="/static/js/profile.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 20px">
        <h1 class="pagetitle">配置管理</h1>
        <span>
            <span style="color:red">说明</span><br>
            1：此页面用于添加或修改配置信息；<br>
            2：添加配置：输入配置信息点击<span style="color: red">提交保存</span>按钮，保存配置信息；<br>
            3：修改配置：修改配置信息点击<span style="color: red">提交保存</span>按钮，保存配置信息；<br>
            4：重置表单：清空配置信息；<br>
            5：增加配置属性：点击<span style="color: red;">增加配置属性</span>按钮，添加更多配置属性；<br>
		</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post"  onsubmit="return false;" id="addorupdate">
                <p>
                    <input type="hidden" value="${profile.id}" name="profile.id">
                    <label><em style="color: red;">*</em>配置名</label>
                    <span class="field"><input type="text" name="profile.configName" class="longinput" value="${profile.configName}"  placeholder="配置名不能为空" /></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>配置关键字</label>
                    <span class="field"><input type="text"  name="profile.configKey" class="longinput" value="${profile.configKey}"  placeholder="配置key不能为空"/></span>
                </p>

                <p style="padding: 8px;">
                    <a class="btn btn2" href="javascript:void(0);" onclick="addElement()">
                        <i class="fa fa-plus c-999 fa-fw fsize16 ml5"></i>
                        <span>增加配置属性</span></a>
                </p>
                <c:if test="${configContext!=null && configContext.size()>0}">
                    <c:forEach items="${configContext}" var="context">
                        <p class="new-config">
                            <label>属性name:
                                <input type="text" value="${context.key}" title="属性name" style="width:70px;" onchange="changeName(this)" class="center configName"/>
                            </label>
                            <span class="field">属性value:
                                <input type="text" name="configName_${context.key}" value="${context.value}" class="configValue" title="属性value" style="width:62%;"/>
                                <a onclick="removeConfigProperty(this)" href="javascript:void(0);" class="btn btn2">
                                    <i class="fa fa-trash-o c-999 fa-fw fsize16 ml5"></i><span>删除配置属性</span>
                                </a>
                            </span>
                        </p>
                    </c:forEach>
                </c:if>
                <p class="stdformbutton" id="but-p-from">
                    <button class="submit radius2" onclick="saveProfile()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单" />
                </p>
            </form>
        </div>
    </div>
</div>
</body>

</html>