<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食堂人员</title>
    <script type="text/javascript">
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#attendtime',
                format:'YYYY-MM-DD'
            });
        });


        function addFormSubmit() {
            var messId=jQuery("#messId").val();
            var manage=jQuery("#manage").val();
            if(manage==null || manage==''){
                alert("管理员不能为空!");
                return;
            }
            var duty=jQuery("#duty").val();
            if(duty==null || duty==''){
                alert("值班人员不能为空!");
                return;
            }
            var attendtime=jQuery("#attendtime").val();
            if(attendtime==null || attendtime==''){
                alert("值班时间不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateMessManage.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMessManage.json?id="+messId;
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
        <h1 class="pagetitle">修改食堂人员</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改食堂人员信息；<br>
            2.修改食堂人员信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改食堂人员信息<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>管理员</label>
                    <span class="field">
                    <input type="hidden" name="messManage.id" value="${messManage.id}" >
                    <input type="hidden" name="messManage.messId" value="${messManage.messId}" id="messId">
                        <input type="text" name="messManage.manage"  class="longinput" value="${messManage.manage}" id="manage"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>位置</label>
                    <span class="field">
                        <input type="text" name="messManage.duty"  class="longinput" value="${messManage.duty}" id="duty" />
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>值班时间</label>
                    <span class="field">
                        <input type="text" name="messManage.attendtime"  class="longinput"  id="attendtime" readonly="readonly" value="<fmt:formatDate  type='both' value='${messManage.attendtime}' pattern='yyyy-MM-dd'/>"/>
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