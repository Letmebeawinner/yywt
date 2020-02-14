<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>评价维修结果</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var responseTime = jQuery("input[name='repair.responseTime']:checked").val();
            var quality = jQuery("input[name='repair.quality']:checked").val();
            var attitude = jQuery("input[name='repair.attitude']:checked").val();
            var commentsBelow = jQuery("input[name='repair.commentsBelow']").val();
            if (!responseTime) {
                alert("请选择响应时间");
                return;
            }
            if (!quality) {
                alert("请选择维修质量");
                return;
            }
            if (!attitude) {
                alert("请选择人员态度");
                return;
            }
            if (!commentsBelow) {
                alert("请填写意见栏");
                return
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/saveAppraise.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("添加成功");
                        window.location.href = "${ctx}/admin/houqin/queryAllRepair.json";
                    } else {
                        alert(result.message)
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">评价维修结果</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于评价维修结果；<br>
            2.评价维修结果：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" value="${repairId}" name="repair.id">
                    <label><em style="color: red;">*</em>响应时间</label>
                    <span class="field">
                        <input type="radio" name="repair.responseTime" value="1" checked="checked"> 满意
                        <input type="radio" name="repair.responseTime" value="0"> 不满意
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>维修质量</label>
                    <span class="field">
                          <input type="radio" name="repair.quality" value="1" checked="checked"> 满意
                         <input type="radio" name="repair.quality" value="0"> 不满意
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>人员态度</label>
                    <span class="field">
                         <input type="radio" name="repair.attitude" value="1" checked="checked"> 满意
                         <input type="radio" name="repair.attitude" value="0"> 不满意
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>意见栏</label>
                    <span class="field">
                         <input type="text" name="repair.commentsBelow" class="longinput"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交评价</button>
                </p>
            </form>

        </div>
    </div>
</div>
</body>
</html>