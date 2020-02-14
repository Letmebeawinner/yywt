<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改规章制度</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);

        }
        function upFile() {
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

        function addFormSubmit() {
            var title = jQuery("#title").val();
            if (title == "" || title == null) {
                alert("请添加规章制度名称");
                return;
            }

            var sourceVal = jQuery("input[name='rule.source']").val();
            var author = jQuery("input[name='rule.author']").val();
            var views = jQuery("input[name='rule.views']").val();
            if (jQuery.trim(sourceVal).length == 0) {
                jAlert("请输入来源", "提示", function () {
                })
                return
            }

            if (jQuery.trim(author).length == 0) {
                jAlert("请输入作者", "提示", function () {
                })
                return
            }

            jQuery.ajax({
                url: "${ctx}/admin/oa/updateRule.json",
                data:jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllRule.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改规章制度</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于规章制度信息修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="rule.id" value="${rule.id}">
                    <label><em style="color: red;">*</em>规章制度名称</label>
                    <span class="field">
                        <input type="text" name="rule.name" id="title" class="longinput" value="${rule.name}"/></span>
                </p>
                <p>
                    <label>类型名称</label>
                    <span class="field">
                        <select name="rule.typeId">
                            <c:forEach items="${ruleTypeList}" var="ruleType">
                                <option value="${ruleType.id}" <c:if test="${rule.typeId==ruleType.id}">selected="selected"</c:if>>${ruleType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>来源</label>
                    <span class="field"><input type="text" name="rule.source" value="${rule.source}" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>作者</label>
                    <span class="field"><input type="text" name="rule.author" value="${rule.source}" class="longinput"/></span>
                </p>
                <p>
                    <label>浏览量</label>
                    <span class="field"><input type="text" name="rule.views" value="${rule.source}" class="longinput"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>规章制度地址</label>
               	 <span class="field">
						 <input type="hidden" name="file.fileUrl" value="${rule.fileUrl}" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传规章制度"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <%--<center><h4 id="file">${rule.fileUrl}</h4></center>--%>
					</span>
                </p>
                <p>
                    <label>内容</label>
                    <span class="field">
                        <textarea rows="8" cols="5" name="rule.context" class="longinput">${rule.context}</textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>


</body>
</html>