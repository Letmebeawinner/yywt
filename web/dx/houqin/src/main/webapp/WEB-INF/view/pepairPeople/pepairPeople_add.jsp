<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加维修员用户</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/sysuser/sysuser.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/function/function.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">添加维修员用户</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于添加用户；<br>
                    2. 添加信息：添加用户信息，点击<span style="color:red">提交保存</span>按钮添加；点击<span style="color:red">重置表单</span>按钮，将重新显示用户添加前的信息；<br>
                    3. 添加密码：添加新密码并确认，点击<span style="color:red">提交保存</span>按钮，点击<span style="color:red">重置表单</span>按钮，将清空新密码以及确认密码
		</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn m80" onclick="addSystemUser()">添加维修人员</a>
                </div>
            </div>
        </div>
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/addPeople.json"
                  onsubmit="return false;" id="updateUser">
                <p>
                    <label><em style="color: red;">*</em>用户名</label>
                    <span class="field"><input type="text" name="userName" id="userName2" class="longinput"
                                               value="${sysUser.userName}"/><em style="color: red;"
                                                                                 id="usna"> </em></span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="update()">提交添加</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var data=data;
    var functionsIds;
    function addSystemUser() {
        jQuery.ajax({
            type:"post",
            url:"/admin/houqin/getSystemUserList.json",
            data:data,
            dataType:"text",
            async: false,
            success:function (result) {
                jQuery.alerts._show('选择功能列表',null, null,'addCont',function (confirm) {
                    if(confirm){
                        if(functionsIds){
                            jQuery.ajax({
                                type:"post",
                                url:"/admin/oa/updateDesk.json",
                                data:{'functionsIds': functionsIds},
                                dataType:"json",
                                success:function (result) {
                                    if(result.code=="0"){
                                        alert("操作成功!");
                                        window.location.reload();
                                    }else {
                                        alert(result.message);
                                    }
                                }
                            });
                        }else{
                            alert("没有选择！");
                        }

                    }
                });
                jQuery('#popup_message').html(result);
                // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                jQuery('#popup_container').css({
                    top: 50,
                    left: '30%',
                    overflow:'hidden'
                });
                jQuery('#popup_container').css("max-height","800px");
                jQuery('#popup_message').css("max-height","600px");
                jQuery('#popup_message').css('overflow-y','scroll');
            }
        });
    }
</script>
</body>
</html>