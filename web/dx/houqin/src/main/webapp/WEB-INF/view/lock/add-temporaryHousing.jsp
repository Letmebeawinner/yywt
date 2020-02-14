<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>临时住房</title>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#beginTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });


        function addFormSubmit() {

            var userName = jQuery("#userName").val();
            if (userName == null || userName == "") {
                alert("请填写姓名");
                return;
            }

            var IdNumber = jQuery("#IdNumber").val();
            // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
            // var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            // if (reg.test(IdNumber) === false) {
            //     alert("身份证输入不合法");
            //     return;
            // }

            var beginTime = jQuery("#beginTime").val();
            if (beginTime == null || beginTime == "") {
                alert("请填写开始时间");
                return;
            }

            var endTime = jQuery("#endTime").val();
            if (endTime == null || endTime == "") {
                alert("请填写结束时间");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/addTempOpenCard.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href="${ctx}/admin/houqin/tempOpenCardList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function saveRoomRepair(status) {
            var msg;
            if (status === 1) {
                msg = "是否将此房间标记维修";
            }
            if (status === 0) {
                msg = "是否取消此房间维修状态";
            }
            if (!confirm(msg)) {
                return
            }

            var roomId = jQuery("input[name='tempCard.roomId']").val()
            jQuery.getJSON("${ctx}/admin/houqin/markRepair.json",{"roomId": roomId, "serviceStatus": status},
                function (data) {
                    alert(data.message)
                    if (data.code === "0") {
                        window.location.reload()
                    }
            })
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">临时住房（${cridentialId}）</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="tempCard.roomId" value="${id}">
                    <input type="hidden" name="tempCard.cridentialId" value="${cridentialId}">
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field">
                        <input type="text" name="tempCard.userName" class="longinput" id="userName"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>性别</label>
                    <span class="field">
                        <input type="radio" name="tempCard.sex" value="男" checked/>男&nbsp;
                        <input type="radio" name="tempCard.sex" value="女"/>女

                    </span>
                </p>
                <p>
                    <label>身份证号</label>
                    <span class="field">
                        <input type="text" name="tempCard.IdNumber" class="longinput" id="IdNumber"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field">
                        <input type="text" name="tempCard.beginTime" class="longinput" id="beginTime"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>结束时间</label>
                    <span class="field">
                        <input type="text" name="tempCard.endTime" class="longinput" id="endTime"/>
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="tempCard.context" class="longinput"></textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <c:if test="${isRepairing == true}">
                        <button class="submit radius2" onclick="saveRoomRepair(0);return false;">取消维修</button>
                    </c:if>
                    <c:if test="${isRepairing == false}">
                        <button class="submit radius2" onclick="saveRoomRepair(1);return false;">设为维修</button>
                    </c:if>
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>