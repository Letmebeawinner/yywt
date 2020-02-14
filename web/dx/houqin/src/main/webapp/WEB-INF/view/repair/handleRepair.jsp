<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>处理报修</title>
    <script type="text/javascript">

        function addFormSubmit() {

            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateRepairStatus.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/cancelRepairList.json";
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
        <h1 class="pagetitle">处理报修</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于处理报修；<br>
            2.可根据实际情况修改报修物品的处理状态，处理结果，处理时间；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>，保存报修物品的处理结果；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>报修编号</label>
                    <span class="field">
                        ${repairs.number}
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>报修物品</label>
                    <span class="field">
                        ${repairs.name}
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>故障说明</label>
                    <span class="field">
                        ${repairs.context}
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>报修人</label>
                    <span class="field">
                        ${repairs.userName}
                    </span>
                </p>
                <p>
                    <label>报修人电话</label>
                    <span class="field">
                      ${repairs.telephone}&nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>报修时间</label>
                    <span class="field">
                      <fmt:formatDate value="${repairs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </span>
                </p>
                <p>
                    <label>损耗品</label>
                    <span class="field">
                        <textarea rows="5" cols="80" name="repair.lossGoods" class="longinput">

                        </textarea>
                    </span>
                </p>
                <p>
                    <label>处理结果
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="repair.result" class="longinput"
                                                  id="result">${repairs.result}</textarea></span>
                </p>

                <p>
                    <input type="hidden" value="${repairs.id}" name="repair.id" id="repairId">
                    <label>处理状态</label>
                    <span class="field">
                        <select name="repair.status" id="status">
                                <option value="0"
                                        <c:if test="${repairs.status==0}">selected="selected"</c:if>>未处理</option>
                                <option value="1"
                                        <c:if test="${repairs.status==1}">selected="selected"</c:if>>正维修</option>
                                <option value="2"
                                        <c:if test="${repairs.status==2}">selected="selected"</c:if>>已维修</option>
                                <option value="3"
                                        <c:if test="${repairs.status==3}">selected="selected"</c:if>>已取消</option>
                        </select>
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