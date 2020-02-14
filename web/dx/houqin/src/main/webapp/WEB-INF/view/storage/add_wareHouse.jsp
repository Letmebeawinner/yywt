<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加库房信息</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            var address = jQuery("#address").val();
            var managerName = jQuery("#managerName").val();
            var managerPhone = jQuery("#managerPhone").val();
            if (!name) {
                alert("库房名称不能为空，请填写");
                return;
            }
            if (!address) {
                alert("库房地址不可以为空，请填写");
                return;
            }
            if (managerName!=null&&managerName!="") {
                alert("管理员名字不能为空，请填写");
                return;
            }
            if (!managerPhone) {
                alert("手机不能为空，请重填");
                return
            }
            if (!(/^1[34578]\d{9}$/.test(managerPhone))) {
                alert("手机号码有误，请重填");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addWareHouse.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == 0) {
                        alert("添加成功");
                        window.location.href = "${ctx}/admin/houqin/wareHouseList.json";
                    }
                }
            });
        }

    </script>

    <script type="text/javascript">
        var data = data;
        var functionsIds;
        function addSystemUser() {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/storageGetSystemUserList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择人员列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            jQuery("#managerName").html(data);
                            jQuery("#manageNames").val(data);
                            jQuery("#sysId").val(functionsIds);
                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '20%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "600px");
                    jQuery('#popup_message').css("max-height", "400px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加库房信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加库房信息；<br>
            2.添加库房信息：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加库房信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>库房名称</label>
                    <span class="field">
                        <input type="text" name="wareHouse.name" class="longinput" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>库房地址</label>
                    <span class="field">
                         <input type="text" name="wareHouse.address" class="longinput" id="address"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>管理员姓名</label>
                    <input type="hidden" name="wareHouse.sysId" class="longinput" id="sysId"/>
                    <input type="hidden" name="wareHouse.managerName"  id="manageNames"/>
                    <span class="field">
                         <a href="javascript:void(0)"  onclick="addSystemUser();return false;"  class="stdbtn btn_orange">选择管理员</a>
                        <font id="managerName"></font>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>管理员电话</label>
                    <span class="field">
                         <input type="text" name="wareHouse.managerPhone" class="longinput" id="managerPhone"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>