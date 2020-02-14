<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产报废</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var propertyId=jQuery("#propertyId")
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addPropertyClean.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/propertyCleanList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }
    </script>

    <script type="text/javascript">
        var data = data;
        var functionsIds;

        function addproperty() {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/selectPropertyList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择资产列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/searchProperty.json",
                                    data: {'id': functionsIds},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            var name = result.data.name;
                                            var id = result.data.id;
                                            var typeId=result.data.propertyId;
                                            jQuery("#propertyName").html(name);
                                            jQuery("#propertyId").val(id);
                                            jQuery("#propertyTypeId").val(typeId);
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }

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
                    jQuery('#popup_message').css("max-height", "450px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }
    </script>

</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产报废</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加资产报废；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" value="${userId}" name="propertyClean.userId"/>
                    <label>管理员</label>
                    <span class="field">
                        <input type="text" value="${userName}" class="longinput" id="name"/>
                    </span>
                </p>


                <p>
                    <label><em style="color: red;">*</em>选择资产</label>
                <td>
                    <input type="hidden" value="" id="propertyId" name="propertyClean.propertyId">
                    <input type="hidden" value="" id="propertyTypeId" name="propertyClean.propertyTypeId">
                    <span class="field">
                        <a href="javascript:addproperty()" class="stdbtn btn_orange">选择资产</a><font id="propertyName"></font></span>
                </td>
                </p>
                <p>
                    <label><em style="color: red;">*</em>说明</label>
                    <span class="field">
                       <textarea rows="10" cols="5" class="longinput" name="propertyClean.description">

                       </textarea>
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