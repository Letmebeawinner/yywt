<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>岗位详情</title>
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
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">岗位详情</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <form id="addFormSubmit" class="stdform stdform2" method="post">
            <p>
                <input type="hidden" name="job.id" id="id" class="longinput" value="${job.id}"/>
                <label>岗位系列:</label>
                <span class="field">
						<select name="job.jobOrderId" id="jobOrderId" disabled>
							<option value="">--请选择--</option>
							<c:forEach items="${jobOrderList}" var="jobOrder">
                            <option value="${jobOrder.id}" <c:if test="${jobOrder.id==job.jobOrderId}">selected </c:if> >${jobOrder.name}</option>
                            </c:forEach>
						</select>
                </span>
            </p>
            <p>
                <label>岗位名称:</label>
                <span class="field">
						<input type="text" name="job.name" id="name" class="longinput" value="${job.name}" disabled/>
					</span>
            </p>
            <p>
                <label>岗位编号:</label>
                <span class="field">
						<input type="text" name="job.number" id="number" class="longinput" value="${job.number}" disabled/>
					</span>
            </p>
            <p>
                <label>所属部门:</label>
                <span class="field">
						<input type="text" name="job.departmentName" id="departmentName" class="longinput" value="${job.departmentName}" disabled/>
					</span>
            </p>
            <p>
                <label>所辖人数:</label>
                <span class="field">
						<input type="text" name="job.manageNumber" id="manageNumber" class="longinput" value="${job.manageNumber}" disabled/>
					</span>
            </p>
            <p>
                <label>直接上级:</label>
                <span class="field">
						<input type="text" name="job.leader" id="leader" class="longinput" value="${job.leader}" disabled/>
					</span>
            </p>
            <p>
                <label>本岗位人数:</label>
                <span class="field">
						<input type="text" name="job.thisJobNumber" id="thisJobNumber" class="longinput" value="${job.thisJobNumber}" disabled/>
					</span>
            </p>
            <p>
                <label>直接下级:</label>
                <span class="field">
						<input type="text" name="job.junior" id="junior" class="longinput" value="${job.junior}" disabled/>
					</span>
            </p>
            <p>
                <label>岗位分析日期:</label>
                <span class="field">
						<input type="text" name="job.analyzeTime" id="analyzeTime" disabled class="longinput" readonly value="<fmt:formatDate value="${job.analyzeTime}" pattern="yyyy-MM-dd"/> "/>
					</span>
            </p>

            <p>
                <label><em style="color: red;">*</em>岗位描述:</label>
                <span class="field">
						<textarea name="job.description" id="content" class="longinput" disabled>${job.description}</textarea>
					</span>
            </p>
        </form>
    </div>
</div>
</body>
</html>