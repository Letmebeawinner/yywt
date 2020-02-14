<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改学习考试成绩</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">
        function updateStudyTest(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/studyTest/updateStudyTest.json',
                data:params,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        window.location.href="${ctx}/admin/jiaowu/studyTest/studyTestList.json";
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('添加失败','提示',function() {});
                }
            });
        }


    </script>


</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改学习考试成绩</h1>
            <span>
            <span style="color:red">说明</span><br>
            1.本页面用于修改学习考试成绩<br />
    2.按要求填写相关信息,点击"提交"按钮,修改学习考试成绩.<br />
    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
    </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" name="studyTest.id" id="studentId" value="${studyTest.id}"/>
                    <p>
                        <label><em style="color: red;">*</em>学员</label>
                        <span class="field"><input type="text" name="" class="longinput" value="${studyTest.name}" readonly="readonly"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>毕（结）业证号</label>
                        <span class="field"><input type="text" name="studyTest.graduateNumber" class="longinput" value="${studyTest.graduateNumber}"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>在线学习</label>
                        <span class="field"><input type="text" name="studyTest.onlineStudy" id="personMaterial" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}" value="${studyTest.onlineStudy}"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>调研报告</label>
                        <span class="field"><input type="text" name="studyTest.searchReport" value="${studyTest.searchReport}" id="organisation" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>毕业考试</label>
                        <span class="field"><input type="text" name="studyTest.graduateTest" value="${studyTest.graduateTest}" id="allPerformance" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}"/></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="studyTest.note" class="mediuminput" id="note">${studyTest.note}</textarea></span>
                    </p>
                    <br />


                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="updateStudyTest()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>