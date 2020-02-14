<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>导入成果</title>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">

        jQuery(function () {
            var errorInfo = "${errorInfo}";
            if (errorInfo != null && errorInfo != "") {
                alert(errorInfo);
            }
        });

        function importExcel() {
            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
                alert('请选择导入内容');
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">导入成果</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于导入成果<br/>
                    2.可下载excel模板,参照模板说明,填写成果信息,批量导入成果.
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post"
                      action="${ctx}/admin/ky/importResults.json" enctype="multipart/form-data">
                    <p>
                        <label>excel模板说明</label>
                        <span class="field">
                        		1.论文/著作/课题名称,必填.<br>
                        		2.类型,"请填写: 1(论文) 2(著作) 3(课题)",必填.<br>
                        		3.提交部门,"请填写: 1(科研) 2(咨政)", 必填.<br>
                        		4.论文:发表刊物, 著作:出版社,必填, 课题:不必填<br>
                        		5.论文:刊号,必填, 著作/课题: 不必填<br>
                        		6.论文:作者名称, 著作:主编, 课题:课题负责人, 不必填<br>
                                7.论文:发表时间, 著作:出版时间, 不必填<br>
                        		<a href="${ctx}/static/import.xls" style="color: red;">点击下载模板</a><br>

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