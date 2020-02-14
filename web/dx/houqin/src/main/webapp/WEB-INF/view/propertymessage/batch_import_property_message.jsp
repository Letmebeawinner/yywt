<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>批量导入资产信息</title>
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
        <h1 class="pagetitle">批量导入资产信息</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于批量导入资产信息<br/>
            2.可下载excel模板,参照模板说明,填写资产信息,批量导入.
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">
                <form id="form1" class="stdform" method="post" action="${ctx }/admin/houqin/batchImportPropertyMessage.json" enctype="multipart/form-data">
                    <p>
                        <label>excel模板说明</label>
                        <span class="field">
                            1.资产信息名称,<span style="color: red">必填</span>.<br>
                            2.资产类型ID,<span style="color: red">必填</span>.此ID可在资产类型列表查找.<br>
                            3.规格.<br>
                            4.数量.<br>
                            5.单位.<br>
                            6.金额.<br>
                            7.购入时间, <span style="color: red">必填</span>, 格式为"2018-01-01".<br>
                            8.使用期限, 格式为"2018-01-01".<br>
                            9.来源,请输入数字, <span style="color: red">必填, 1代表购入, 2代表捐赠.</span><br>
                            10.库房名称ID，<span style="color: red">只填数字，比如（3、教学综合楼）excel中只填&nbsp;3&nbsp;即可</span>：<br>
                                <c:forEach var="wareHouse" items="${wareHouseList}">
                                    &nbsp;&nbsp;&nbsp;&nbsp;${wareHouse.id}、${wareHouse.name}<br>
                                </c:forEach>
                            11.备注.<br>
                            <a href="${ctx }/static/common/import_PropertyMessage.xls" style="color: red;">点击下载模板</a><br>
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
                    <button class="radius2" onclick="importExcel()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>