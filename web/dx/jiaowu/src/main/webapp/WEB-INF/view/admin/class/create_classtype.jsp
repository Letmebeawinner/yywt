<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建班型</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function addClassType() {
            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/classType/doCreateClassType.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/classType/classTypeList.json";
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">新建班型</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于新建班型<br/>
                    2.按要求填写相关信息,点击"提交"按钮,新建班型.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                        <label><em style="color: red;">*</em>名称</label>
                        <span class="field"><input type="text" name="classType.name" id="name"
                                                   class="longinput"/></span>
                    </p>
                    <p>
                        <label>描述</label>
                        <span class="field"><textarea cols="80" rows="5" name="classType.description"
                                                      class="mediuminput" id="description"></textarea></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="classType.note" class="mediuminput"
                                                      id="note"></textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addClassType();return false;" id="submitButton"> 提交</button>
                    </p>
                </form>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>