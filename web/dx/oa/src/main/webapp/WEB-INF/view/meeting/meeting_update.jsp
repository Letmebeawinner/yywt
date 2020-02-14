<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改会议室</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var id=jQuery("#meetId").val();
            var name=jQuery("#name").val();
            if(name==""||name==null){
                alert("请添加会议室名称");
                return;
            }
            var amount=jQuery("#amount").val();
            if(amount==""||amount==null){
                alert("请添加会议室容纳量");
                return;
            }
            var place=jQuery("#place").val();
            if(place==""||place==null){
                alert("请添加会议室地点");
                return;
            }
            var configure=jQuery("#configure").val();
            if(configure==""||configure==null){
                alert("请添加会议室配置");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateMeeting.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllMeeting.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改会议室</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于会议室信息修改；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>会议室名称</label>
                    <span class="field"><input type="text" name="meeting.name" id="name" class="longinput" value="${meeting.name}"/></span>
                </p>
                <input type="hidden" name="meeting.id" value="${meeting.id}" id="meetId">
                <p>
                    <label><em style="color: red;">*</em>会议室容量</label>
                    <span class="field"><input type="text" name="meeting.amount" id="amount" class="longinput"
                                               value="${meeting.amount}"
                                               onkeyup="value=value.replace(/[^\d]/g,'')"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>会议室地点</label>
                    <span class="field"><input type="text"  name="meeting.place" id="place" class="longinput" value="${meeting.place}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>会议室配置</label>
                    <span class="field"><textarea cols="80" rows="5" name="meeting.configure" id="configure" class="longinput">${meeting.configure}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>


</body>
</html>