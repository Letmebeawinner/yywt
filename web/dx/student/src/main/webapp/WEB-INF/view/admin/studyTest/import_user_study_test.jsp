<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>批量报名</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
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
            jQuery("#importForm").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批量导入考试成绩</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于批量报名<br/>
            2.可下载excel模板,参照模板说明,填写用户信息，批量导入系统用户。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">
                <form id="importForm" class="stdform" method="post"
                      action="${ctx}/admin/jiaowu/studyTest/batchStudyTest.json" enctype="multipart/form-data">
                    <p>
                        <label style="margin-top: 30px">excel模板说明</label>
                        <span class="field" style="margin-top: 30px">
                            1.姓名,必填<br>
                            2.毕（结）业证号,必填<br>
                            3.在线学习,必填<br>
                            4.调研报告,必填<br>
                            5.毕业考试,必填<br>
                            6.备注<br>
                            <a href="${ctx }/static/common/import_user_study_test.xls"
                               style="color: red;">点击下载模板</a><br>
                        </span>
                    </p>
                    <p>
                        <label>上传</label>
                        <span class="field">
                        <input id="myFile" type="file" value="" name="myFile"/>
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