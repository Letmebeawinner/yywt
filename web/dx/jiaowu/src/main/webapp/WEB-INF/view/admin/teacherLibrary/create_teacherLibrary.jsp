<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建专题</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function addTeacherLibrary() {
            if (jQuery("input[name='teacherLibrary.teacherId']").val() == '') {
                jAlert("请选择讲师", "提示", function () {
                });
                return false
            }

            if (jQuery("input[name='teacherLibrary.teacherName']").val() == '') {
                jAlert("请选择讲师", "提示", function () {
                });
                return false
            }

            if (jQuery("#politicalStatus").val()=='') {
                jAlert("专题类别不能为空", "提示", function () {
                });
                return false
            }
            if (jQuery("input[name='teacherLibrary.projectName']").val() == '') {
                jAlert("专题名称不能为空", "提示", function () {
                });
                return false
            }



            var params = jQuery("#addTeacher").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/teacher/saveProject.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "/admin/jiaowu/teacher/teacherLibraryList.json";
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

        function selectClass() {
            window.open('${ctx}/jiaowu/teacher/allTeacherListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
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
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建专题</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建专题<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addTeacher">
                <p>
                    <label><em style="color: red;">*</em>讲师</label>
                    <span class="field">
                        <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择讲师</a>
                        <span id="teacherspan"></span>
                         <input type="hidden" name="teacherLibrary.teacherId" id="teacherId"/>
                    	<input type="hidden" name="teacherLibrary.teacherName" id="teacherName"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>专题类别</label>
                    <span class="field">
                        <select name="teacherLibrary.classification" id="politicalStatus">
                            <option value="">--请选择--</option>
                            <option value="1">马克思主义原著</option>
                            <option value="2">中国特色社会主义理论体系</option>
                            <option value="3">学习贯彻习近平总书记重要讲话精神</option>
                            <option value="4">中国特色社会主义政治、经济、文化、社会、生态文明建设</option>
                            <option value="5">党性教育、党性锻炼与廉政建设</option>
                            <option value="6">贵州、贵阳发展重大问题</option>
                            <option value="7">干部综合素质、能力建设</option>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>专题名称</label>
                    <span class="field">
                        <input type="text" name="teacherLibrary.projectName" class="longinput">
                    </span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addTeacherLibrary();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>