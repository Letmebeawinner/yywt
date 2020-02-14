<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加员工</title>
    <script type="text/javascript">
        function updateEmployeeCardNo() {
            var date = jQuery("#updateEmployee").serialize();
            jQuery.ajax({
                url: "/admin/rs/updateEmployee.json",
                data: date,
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/rs/getEmployeeList.json";
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
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">修改一卡通编号</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateEmployee">
                <p>
                    <input type="hidden" name="queryEmployee.id" value="${queryEmployee.id}" >
                    <label><em style="color: red;">*</em>一卡通编号</label>
                    <span class="field"><input type="text" name="queryEmployee.cardNo" id="mobile" class="longinput" value="${queryEmployee.cardNo}"/></span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateEmployeeCardNo();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>