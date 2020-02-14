<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>批量导入教学课程</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">

        jQuery(function(){
            var errorInfo="${errorInfo}";
            if(errorInfo!=null&&errorInfo!=""){
                jAlert(errorInfo,'提示',function() {});
            }
        });

        function importExcel() {

            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
                jAlert('请选择导入内容','提示',function() {});
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批量导入教学课程</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于批量导入教学课程<br />
                    2.可下载excel模板,参照模板说明,填写教学课程信息,批量导入教学课程.
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post" action="${ctx}/admin/jiaowu/teach/batchImportTeachingProgramCourse.json" enctype="multipart/form-data">
                    <p>
                        <label>excel模板说明</label>
                            <span class="field">
                        		1.课程ID,请到<a href="${ctx}/admin/jiaowu/course/courseList.json" target="_blank" style="color: blue">课程管理-课程列表</a>查询,必填.<br>
                        		2.班次ID,请到<a href="${ctx}/admin/jiaowu/class/classList.json" target="_blank" style="color: blue">班级管理-班次列表</a>查询,必填.<br>
                        		3.教室ID,请到<a href="${ctx}/admin/jiaowu/classroom/classroomList.json" target="_blank" style="color: blue">教室管理-教室列表</a>查询,必填.<br>
								15.备注<br>
                        		<a href="${ctx }/static/common/import_teachingProgramCourse.xls" style="color: red;">点击下载模板</a><br>

                            </span>
                    </p>
                    <p>
                        <label>上传</label>
                            <span class="field">
                            <input id="myFile" type="file" value="" name="myFile"/>
                            </span>
                    </p>

                    <br />

                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="importExcel()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        提交
                    </button>
                </p>


            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>