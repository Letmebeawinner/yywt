<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建专题</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function addCourse() {
            if(jQuery("#year").val() == null || jQuery("#year").val() == ""){
                jAlert("请选择年份", '提示', function () {
                });
                return false;
            }
            if(jQuery("#season").val() == null || jQuery("#season").val() == ""){
                jAlert("请选择季节", '提示', function () {
                });
                return false;
            }
            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/course/createCourse.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/course/courseList.json";
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

        function selectTeacher() {
            window.open('${ctx}/jiaowu/teacher/teacherListForMultiSelect.json', 'newwindow', 'toolbar=yes,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addToPeopleId(teacherIds,teacherNames) {
            jQuery("#teacherspan").html(teacherNames);
            jQuery("#teacherId").val(teacherIds);
        }

    </script>


</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">新建专题</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建专题<br/>
                    2.按要求填写相关信息,点击"提交"按钮,新建专题.<br/>
                    3. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">

                    <p>
                        <input type="hidden" name="course.teacherId" id="teacherId"/>
                        <%--<input type="hidden" name="course.teacherName" id="teacherName"/>--%>
                        <label><em style="color: red;">*</em>名称</label>
                        <span class="field"><input type="text" name="course.name" id="name" class="longinput"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>专题类别</label>
                        <span class="field">
                            <select id="courseTypeId" name="course.courseTypeId">
                            	<option value="0">请选择</option>
                            	<c:if test="${courseTypeList!=null&&courseTypeList.size()>0}">
                                    <c:forEach items="${courseTypeList}" var="courseType">
                                        <option value="${courseType.id}">${courseType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>讲师</label>
                        <span class="field">
                            <span id="teacherspan"></span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>年份</label>
                        <span class="field">
                            <select id="year" name="course.year">
                                <option value="">请选择年份</option>
                            	<c:forEach begin="2018" end="2030" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>季节</label>
                        <span class="field">
                            <select id="season" name="course.season">
                                <option value="">请选择季节</option>
                            	<option value="1">春季</option>
                            	<option value="2">秋季</option>
                            </select>
                            </span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="course.note" class="mediuminput"
                                                      id="note"></textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addCourse();return false;" id="submitButton">提交</button>
                    </p>


                </form>

            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>