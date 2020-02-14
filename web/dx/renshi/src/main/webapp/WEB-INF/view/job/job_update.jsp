<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改岗位</title>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#analyzeTime',
                format: 'YYYY-MM-DD'
            });
            initFrontMultiUM("content", "80%", "300");
        });


        function initFrontMultiUM(id, width, height) {
            UE.delEditor(id);
            //实例化编辑器
            var ue = UE.getEditor('' + id, {
                toolbars: [
                    [
                        'fullscreen', 'source', '|', 'undo', 'redo', '|',
                        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                        'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                        'directionalityltr', 'directionalityrtl', 'indent', '|',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                        'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                        'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                        'horizontal', 'date', 'time'
                    ]
                ],
                enableAutoSave: false,
                autoHeightEnabled: true,
                autoFloatEnabled: true,
                initialFrameWidth: width,
                initialFrameHeight: height,
                scaleEnabled: true//滚动条
            });
            return ue;
        }



        function addFormSubmit() {

            if (jQuery("#name").val().trim() == "") {
                alert("请填写岗位名称。");
                return;
            }
            if (jQuery("#analyzeTime").val().trim() == "") {
                alert("请填写岗位分析时间。");
                return;
            }
            var content = UE.getEditor( 'content').getContent();
            jQuery.ajax({
                type: "post",
                dataType: "json",
                url: "/admin/ganbu/job/updateJob.json",
                data: {
                    "job.id":jQuery("#id").val(),
                    "job.jobOrderId":jQuery("#jobOrderId").val(),
                    "job.name":jQuery("#name").val(),
                    "job.number":jQuery("#number").val(),
                    "job.departmentName":jQuery("#departmentName").val(),
                    "job.manageNumber":jQuery("#manageNumber").val(),
                    "job.leader":jQuery("#leader").val(),
                    "job.thisJobNumber":jQuery("#thisJobNumber").val(),
                    "job.junior":jQuery("#junior").val(),
                    "job.analyzeTime":jQuery("#analyzeTime").val(),
                    "job.description":content
                },
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/ganbu/job/queryJob.json";
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
    <div class="pageheader notab">
        <h1 class="pagetitle">修改岗位</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <form id="addFormSubmit" class="stdform stdform2" method="post">
            <p>
                <input type="hidden" name="job.id" id="id" class="longinput" value="${job.id}"/>
                <label><em style="color: red;">*</em>岗位系列:</label>
                <span class="field">
						<select name="job.jobOrderId" id="jobOrderId">
							<option value="">--请选择--</option>
							<c:forEach items="${jobOrderList}" var="jobOrder">
                            <option value="${jobOrder.id}" <c:if test="${jobOrder.id==job.jobOrderId}">selected </c:if> >${jobOrder.name}</option>
                            </c:forEach>
						</select>
                </span>
            </p>
            <p>
                <label><em style="color: red;">*</em>岗位名称:</label>
                <span class="field">
						<input type="text" name="job.name" id="name" class="longinput" value="${job.name}"/>
					</span>
            </p>
            <p>
                <label>岗位编号:</label>
                <span class="field">
						<input type="text" name="job.number" id="number" class="longinput" value="${job.number}"/>
					</span>
            </p>
            <p>
                <label>所属部门:</label>
                <span class="field">
						<input type="text" name="job.departmentName" id="departmentName" class="longinput" value="${job.departmentName}"/>
					</span>
            </p>
            <p>
                <label>所辖人数:</label>
                <span class="field">
						<input type="text" name="job.manageNumber" id="manageNumber" class="longinput" value="${job.manageNumber}"/>
					</span>
            </p>
            <p>
                <label>直接上级:</label>
                <span class="field">
						<input type="text" name="job.leader" id="leader" class="longinput" value="${job.leader}"/>
					</span>
            </p>
            <p>
                <label>本岗位人数:</label>
                <span class="field">
						<input type="text" name="job.thisJobNumber" id="thisJobNumber" class="longinput" value="${job.thisJobNumber}"/>
					</span>
            </p>
            <p>
                <label>直接下级:</label>
                <span class="field">
						<input type="text" name="job.junior" id="junior" class="longinput" value="${job.junior}"/>
					</span>
            </p>
            <p>
                <label><em style="color: red;">*</em>岗位分析日期:</label>
                <span class="field">
						<input type="text" name="job.analyzeTime" id="analyzeTime" class="longinput" readonly value="<fmt:formatDate value="${job.analyzeTime}" pattern="yyyy-MM-dd"/> "/>
					</span>
            </p>

            <p>
                <label>岗位描述:</label>
                <span class="field">
						<textarea name="job.description" id="content" class="longinput">${job.description}</textarea>
					</span>
            </p>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
            </p>
        </form>
    </div>
</div>
</body>
</html>