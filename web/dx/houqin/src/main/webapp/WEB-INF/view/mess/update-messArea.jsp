<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食堂区域</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var messId = jQuery("#messId").val();
            if (messId == 0) {
                alert("请选择食堂!");
                return;
            }

            var name=jQuery("#name").val();
            if(name==null || name==''){
                alert("名称不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateMessArea.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMessArea.json";
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
        <h1 class="pagetitle">修改食堂区域</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改食堂分类信息；<br>
            2.修改食堂分类信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改食堂区域<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>食堂</label>
                    <span class="field">
                        <select name="messArea.messId" id="messId">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${messList}" var="mess">
                                <option value="${mess.id}" <c:if test="${mess.id==messArea.messId}">selected</c:if>>${mess.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <input type="hidden" name="messArea.id" value="${messArea.id}">
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field">
                        <input type="text" name="messArea.name"  class="longinput" value="${messArea.name}" id="name"/>
                    </span>
                </p>

                <p>
                    <label>位置</label>
                    <span class="field">
                        <input type="text" name="messArea.location"  class="longinput" value="${messArea.location}" />
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="messArea.context" id="context" class="longinput">${messArea.context}</textarea>
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