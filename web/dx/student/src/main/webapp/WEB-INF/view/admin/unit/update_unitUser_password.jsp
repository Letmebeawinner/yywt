<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改单位密码</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){

        });
        function updateUnitUserPassword(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/updateUnitUserPassword.json',
                data:params,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jAlert("重置密码成功",'提示',function() {});
                        $("#password,#confirmPassword").val("");
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改单位密码</h1>

    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">
                <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${unit.id}" id="unitId" name="unitId"/>
                    <p>
                        <label><em style="color: red;">*</em>密码</label>
                        <span class="field"><input type="password" name="password" class="longinput" id="password"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>确认密码</label>
                        <span class="field"><input type="password" name="confirmPassword"  class="longinput" id="confirmPassword"/></span>
                    </p>
                    <br />


                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="updateUnitUserPassword()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>