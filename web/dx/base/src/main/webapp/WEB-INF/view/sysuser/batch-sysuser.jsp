<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>添加管理员用户</title>
	<script type="text/javascript">
        /**
         * 获取学员和老师列表列表
         */
        function save() {
            var num=jQuery("#num").val();
            if(num==null||num==""){
                alert("请输入开通的数量");
                return;
			}
            jQuery.ajax({
                url: "/admin/base/sysuser/batchAddSysUser.json",
                data: {num: num},
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        jQuery("#num").val("");
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
	<div class="pageheader notab" style="margin-left: 30px">
		<h1 class="pagetitle">批量开通单位账号</h1>
	</div><!--pageheader-->
	<div id="contentwrapper" class="contentwrapper">

		<div id="basicform" class="subcontent">
			<form class="stdform stdform2" method="post" onsubmit="return false;" id="addUser">
				<p>
					<label><em style="color: red;">*</em>开通数量</label>
					<span class="field"><input type="text" name="num" id="num" class="longinput"  placeholder="开通数量"/></span>
				</p>

				<p class="stdformbutton">
					<button class="submit radius2" onclick="save()">提交保存</button>
					<input type="reset" class="reset radius2" value="重置表单" onclick="formResetAddSysuser()">
				</p>
			</form>
		</div>
	</div>
</div>
</body>
</html>