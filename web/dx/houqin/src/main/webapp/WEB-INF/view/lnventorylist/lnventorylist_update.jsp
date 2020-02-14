<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会场清单</title>
    <script type="text/javascript">
        function addFormSubmit() {
            if (!jQuery("#name").val()) {
                alert("设备设施名称");
                return;
            }
            if (!jQuery("#type").val()) {
                alert("规格型号");
                return;
            }
            if (!jQuery("#unit").val()) {
                alert("单位");
                return;
            }
            if (!jQuery("#count").val()) {
                alert("数量");
                return;
            }
            if (!jQuery("#ysStatus").val()) {
                alert("验收状态");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateLnventorylist.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == "0") {
                        let entity = result.data;
                        window.location.href = "/admin/houqin/inventoryList.json?meetingId="+entity.meetingId;
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">会场清单</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于会场清单修改<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="lnventorylist.id" class="longinput" value="${lnventorylist.id}" id="id"/>
                <input type="hidden" name="lnventorylist.meetingId" class="longinput" value="${lnventorylist.meetingId}" id="meetingId"/>
                <p>
                    <label><em style="color: red;">*</em>设备设施名称</label>
                    <span class="field">
                        <input type="text" name="lnventorylist.name" value="${lnventorylist.name}" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>规格型号</label>
                    <span class="field">
                        <input type="text" name="lnventorylist.type" value="${lnventorylist.type}" class="longinput" id="type"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单位</label>
                    <span class="field">
                        <input type="text" name="lnventorylist.unit" value="${lnventorylist.unit}" class="longinput" id="unit"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="lnventorylist.count" value="${lnventorylist.count}" class="longinput" id="count"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>验收状态</label>
                    <span class="field">
                        <input type="text" name="lnventorylist.ysStatus" value="${lnventorylist.ysStatus}" class="longinput" id="ysStatus"/>
                    </span>
                </p>
                <p class="stdformbutton center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>