<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建课件</title>
    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function(){
            laydate.skin('molv');
//             uploadFile("uploadFile",false,"myFile",'http://192.168.1.110:6694',callbackFile);
//            uploadFile("uploadFile",false,"myFile",'http://58.42.243.163:6694',callbackFile);
            /*             uploadFile("uploadFile",false,"myFile",'http://127.0.0.1:8083',callbackFile);*/
            uploadFile("uploadFile",false,"myFile",'http://10.100.101.1:6694',callbackFile);
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


        function addFormSubmit() {
            jQuery.ajax({
                url: "${ctx}/admin/jiaowu/courseware/createCourseware.json",
                data:jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/courseware/coursewareList.json";
                    } else {
                        jAlert(result.message,'提示',function() {});
                    }
                }
            });
        }

        function selectTeacher(){
//             window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
            window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=yes,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addToPeopleId(teacherArray){
            jQuery("#teacherspan").html(teacherArray[1]);
            jQuery("#teacherId").val(teacherArray[0]);
            jQuery("#teacherName").val(teacherArray[1]);
        }
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">新建课件</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建课件<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建课件.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
		</span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <!--  <div class="contenttitle2">
                 <h3>新建教学材料</h3>
             </div> -->
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>讲师专题库</label>
                    <span class="field">
                        <select name="courseware.teacherLibraryId">
                            <c:if test="${teacherLibraryList!=null&&teacherLibraryList.size()>0}">
                                <c:forEach items="${teacherLibraryList}" var="teacherLibrary">
                                    <option value="${teacherLibrary.id}">${teacherLibrary.projectName}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field"><input type="text" name="courseware.title" id="title" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文件地址:</label>
					 <span class="field">
						 <input type="text" name="courseware.url"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <center><h4  id="file"></h4></center>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>讲师</label>
                    <span class="field">
                        <a href="javascript:void(0)" onclick="selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                        <span id="teacherspan"></span>
                         <input type="hidden" name="courseware.teacherId" id="teacherId"/>
                    	<input type="hidden" name="courseware.teacherName" id="teacherName"/>
                    </span>
                </p>
            </form>
            <%-- <p class="stdformbutton" style="text-align: center">
                 <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
             </p>--%>
            <p class="stdformbutton">
                <button class="radius2" onclick="addFormSubmit()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                    提交
                </button>
                <br/>
        </div>
    </div>
</div>

</body>
</html>