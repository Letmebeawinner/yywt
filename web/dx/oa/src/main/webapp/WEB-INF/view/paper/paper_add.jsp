<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加封条</title>

    <link rel="stylesheet" href="${ctx}/static/ztree/css/newTree.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle.css" type="text/css">
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>

    <script type="text/javascript">
        jQuery(function(){
            uploadFile("uploadFile",false,"myFile",imagePath,callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);

        }
        function upFile(){
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加封条</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于封条信息添加；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addPaper">
                <input type="hidden" id="subjectId" name="departmentId">
                <p>
                    <label><em style="color: red;">*</em>封条名称</label>
                    <span class="field"><input type="text" name="paper.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>封条类别</label>
                    <span class="field">
                        <select name="paper.paperTypeId" id="paperTypeId">
                            <option value="">请选择封条类别</option>
                            <c:forEach items="${paperTypes}" var="paperType">
                                <option value="${paperType.id}">${paperType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>封条用途</label>
                    <span class="field">
                        <select name="paper.paperFunctionId" id="paperFunctionId">
                            <option value="">请选择封条类别</option>
                            <c:forEach items="${paperFunctions}" var="paperFunction">
                                <option value="${paperFunction.id}">${paperFunction.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <div style="border: 1px solid #ddd;  background: #fcfcfc; border-top: 0;position: relative;">
                    <label><em style="color: red;">*</em>发行部门</label>
                     <span class="field">
                         <input id="subjectNameBtn" type="text" readonly="readonly" value="" style="width:120px;height: 40px;padding: 10px" onclick="showSubjectMenu()"/>
                        <div id="subjectmenuContent" class="menuContent" style="display:none">
                            <ul id="subject_ztreedemo" class="ztree" style="margin-top:0; width:160px;"></ul>
                        </div>
                     </span>

                    <div style="clear: both"></div>
                </div>
                <p>
                    <label>版本号</label>
                    <span class="field"><input type="text" name="paper.version" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文件地址:</label>
					 <span class="field">
						 <input type="hidden" name="paper.fileUrl"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <center><h4  id="file"></h4></center>
					</span>
                </p>
                <p>
                    <label>描述</label>
                    <span class="field"><textarea cols="80" rows="5" name="paper.description"
                                                  class="longinput"></textarea></span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
            <br/>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/js/paper/paper.js"></script>
<script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
<script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">

    var subject_treedata =${departments};
    jQuery(function () {
        jQuery.fn.zTree.init(jQuery("#subject_ztreedemo"), subject_setting, subject_treedata);
    });

</script>
</body>
</html>