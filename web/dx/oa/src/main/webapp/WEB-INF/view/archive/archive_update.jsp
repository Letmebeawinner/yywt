<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改档案</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#archivedate',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            uploadFile("uploadFile",false,"myFile",imagePath,callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);

        }
        function upFile(){
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
            jQuery("#fileName").val(jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

        function addFormSubmit() {
            var typeId = jQuery("#typeId").val();
            if (typeId == 0 || typeId == null) {
                alert("请选择分类");
                return;
            }
            var danghao = jQuery("#danghao").val();
            if (danghao == "" || danghao == null) {
                alert("请添加党号");
                return;
            }
            var jianhao = jQuery("#jianhao").val();
            if (jianhao == "" || jianhao == null) {
                alert("请添加件号");
                return;
            }
            var autograph = jQuery("#autograph").val();
            if (autograph == "" || autograph == null) {
                alert("请添加文号");
                return;
            }
            var archivedate = jQuery("#archivedate").val();
            if (archivedate == "" || archivedate == null) {
                alert("请添加日期");
                return;
            }
            var pages = jQuery("#pages").val();
            if (pages == "" || pages == null) {
                alert("请添加页数");
                return;
            }
            var orginzation = jQuery("#orginzation").val();
            if (orginzation == "" || orginzation == null) {
                alert("请添加机构或问题");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateArchive.json",
                data: jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryArchiveList.json?flag=0";
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
        <h1 class="pagetitle">修改档案</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于档案修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>档案分类</label>
                    <span class="field">
                        <select  name="archive.danghao" id="typeId" class="longinput">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${archiveTypeList}" var="arch">
                                <option value="${arch.id}" <c:if test="${arch.id==archive.typeId}">selected</c:if>>${arch.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <input type="hidden" name="archive.id" value="${archive.id}">
                    <label><em style="color: red;">*</em>档号</label>
                    <span class="field"><input type="text" name="archive.danghao" value="${archive.danghao}"  id="danghao" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档案件号</label>
                    <span class="field"><input type="text" name="archive.jianhao" value="${archive.jianhao}" id="jianhao" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>责任者</label>
                    <span class="field"><input type="text" name="archive.author" value="${archive.author}" id="author" value="贵阳市委党校" class="longinput" readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>题名</label>
                    <span class="field"><input type="text" name="archive.autograph" value="${archive.autograph}" id="autograph" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文号</label>
                    <span class="field"><input type="text" name="archive.wenhao" id="wenhao" value="${archive.wenhao}"  class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field"><input type="text" name="archive.archivedate" readonly value="<fmt:formatDate value="${archive.archivedate}" pattern="yyyy-MM-dd"/>"  id="archivedate" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>页数</label>
                    <span class="field"><input type="text" name="archive.pages" id="pages" class="longinput" value="${archive.pages}" maxlength="8" onkeyup="value=this.value.replace(/\D+/g,'')" placeholder="请输入数字"
                                               style="ime-mode:Disabled"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>机构或问题</label>
                    <span class="field"><input type="text" name="archive.orginzation" value="${archive.orginzation}"  id="orginzation" class="longinput"/></span>
                </p>
                <p>
                    <label>附件:</label>
                    <span class="field">
						 <input type="hidden" name="archive.file"   id="fileUrl"  value="${archive.file}"/>
						 <input type="hidden" name="archive.fileName"  id="fileName"  value="${archive.fileName}"/>
						 <input type="button" id="uploadFile" value="上传附件"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <center><h4  id="file">${archive.fileName}</h4></center>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" name="archive.description">${archive.description}</textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>


</body>
</html>