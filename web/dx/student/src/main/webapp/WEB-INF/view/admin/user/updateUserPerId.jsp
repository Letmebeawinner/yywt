<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改学员编号</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function updatePerId(){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/updateUserPerId.json',
                data:{"userId":jQuery("#userId").val(),"perId":jQuery("#perId").val()},
                type:'post',
                dataType:'json',
                success:function (result){
                    jAlert(result.message,'提示',function() {});
                } ,
                error:function(e){
                    jAlert('修改失败','提示',function() {});
                }
            });
        }

    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改学员编号</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于修改学员编号<br />
                    2.按要求填写相关信息,点击"提交"按钮,修改学员编号.<br />
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                    <input type="hidden" id="userId" name="userId" value="${user.id}"/>
                        <label>学员</label>
                        <span class="field"><input type="text" name="name" id="name" class="longinput" value="${user.name}" readonly="readonly"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>一卡通编号</label>
                        <span class="field"><input type="text" name="perId" id="perId" class="longinput" value="${user.timeCardNo}"/></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="updatePerId();return false;" id="submitButton">
                            提交
                        </button>
                    </p>
                </form>
            </div>
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>