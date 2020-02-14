<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>早中餐管理</title>
    <script type="text/javascript">
        jQuery(function () {

        });

        /**
         * 返回前一个页面
         */
        function comeback() {
            window.location.href = document.referrer;
        }

        function addFormSubmit() {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addLunchManagement.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/houqin/selectAllPlan.json"
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
        <h1 class="pagetitle">早中餐管理</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于早中餐管理；<br>
            2.添加早中餐管理：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加早中餐管理<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>早餐</label>
                    <span class="field">
                        人数：<input type="text"  style="width: 200px;"  name="plan.moringpeople" value="${plan.moringpeople}" placeholder="人数" class="longinput" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                        &nbsp;&nbsp;&nbsp;时间：<input type="text"  style="width: 200px;" name="plan.moringtime" value="${plan.moringtime}" placeholder="时间" class="longinput"/>
                    </span>
                </p>
                <input type="hidden" name="plan.id" value="${plan.id}">
                <p>
                    <label>午餐</label>
                    <span class="field">
                        人数：<input type="text" name="plan.noonpeople"   style="width: 200px;"value="${plan.noonpeople}" placeholder="人数"
                               class="longinput"
                               onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"
                        />
                        &nbsp;&nbsp;&nbsp;时间：<input type="text" name="plan.noontime"  style="width: 200px;" value="${plan.noontime}" placeholder="时间"
                               class="longinput"/>
                    </span>
                </p>

                <p>
                    <label>晚餐</label>
                    <span class="field">
                        人数：<input type="text" name="plan.dinnerpeople"  style="width: 200px;" value="${plan.dinnerpeople}" placeholder="人数"
                               class="longinput"
                               onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"
                        />
                       &nbsp;&nbsp;&nbsp;时间：<input type="text" name="plan.dinnertime" style="width: 200px;" value="${plan.dinnertime}" placeholder="时间"
                               class="longinput"/>
                    </span>
                </p>

                <p>
                    <label>特殊说明</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="plan.instructions" id="context"
                                class="longinput">${plan.instructions}</textarea>
                    </span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                    &nbsp;
                    <button class="submit radius2" onclick="comeback(); return false">返回</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>