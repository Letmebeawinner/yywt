<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>批量导入清单</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">

        jQuery(function () {
            var errorInfo = "${errorInfo}";
            if (errorInfo != null && errorInfo != "") {
                jAlert(errorInfo, '提示', function () {
                });
            }
        });

        function importExcel() {
            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
                jAlert('请选择导入内容', '提示', function () {
                });
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批量导入清单</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于批量批量导入清单<br/>
                    2.可下载excel模板,参照模板说明,填写清单信息,批量导入.
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post"
                      action="${ctx }/admin/houqin/import.json" enctype="multipart/form-data">
                    <p>
                        <label>excel模板说明</label>
                        <span class="field">

                            1.设备设施名称,<span style="color: red">必填</span>.<br>
                            2.规格型号,<span style="color: red">必填</span>.<br>
                            3.单位，<span style="color: red">必填</span>.<br>
                             4.数量,数字格式。<span style="color: red">必填</span>.<br>
                             5.验收状态<span style="color: red">必填</span>.<br>
                        		<a href="${ctx}/static/common/import_lnventory.xls"
                                   style="color: red;">点击下载模板</a><br>
                        	
                            </span>
                    </p>
                    <p>
                        <label>上传</label>
                        <span class="field">
                            <input id="myFile" type="file" value="" name="myFile"/>
                            <input type="hidden" value="${meetingId}" id="meetingId" name="meetingId"/>
                            </span>
                    </p>

                    <br/>

                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="importExcel()" id="submitButton"
                            style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        提交
                    </button>
                </p>


            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>