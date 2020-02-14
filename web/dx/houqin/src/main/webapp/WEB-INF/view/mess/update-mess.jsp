<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食堂</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == null || name == "") {
                alert("请添加食堂名称");
                return;
            }
            var peopleNum = jQuery("#peopleNum").val();
            if (peopleNum == null || peopleNum == "") {
                alert("请添加食堂人数");
                return;
            }
            var mTime = jQuery("#mTime").val();
            if (mTime == null || mTime == "") {
                alert("请添加早饭时间");
                return;
            }
            var nTime = jQuery("#nTime").val();
            if (nTime == null || nTime == "") {
                alert("请添加午饭时间");
                return;
            }
            var dTime = jQuery("#dTime").val();
            if (dTime == null || dTime == "") {
                alert("请添加晚饭时间");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateMess.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMess.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>

</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改食堂</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改食堂信息；<br>
            2.修改食堂信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改食堂信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateMess.json" id="addFormSubmit">
                <p>
                    <input type="hidden" name="mess.id" value="${mess.id}">
                    <label><em style="color: red;">*</em>修改名称</label>
                    <span class="field">
                            <input type="text" name="mess.name" value="${mess.name}" class="longinput" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>人数</label>
                    <span class="field">
                        <input type="text" name="mess.peopleNum" class="longinput" id="peopleNum" value="${mess.peopleNum}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>早饭时间</label>
                    <span class="field">
                        <input type="text" name="mess.morning" class="longinput" id="mTime" value="${mess.morning}"
                               placeholder="请填写合理的时间段"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>午饭时间</label>
                    <span class="field">
                        <input type="text" name="mess.noon" class="longinput" id="nTime" value="${mess.noon}" placeholder="请填写合理的时间段"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>晚饭时间</label>
                    <span class="field">
                        <input type="text" name="mess.night" class="longinput" id="dTime" value="${mess.night}"
                               placeholder="请填写合理的时间段"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="mess.context" id="context" class="longinput">${mess.context}</textarea>
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