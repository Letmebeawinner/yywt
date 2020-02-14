<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>批量导入教职工</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
              
        jQuery(function(){
            var errorInfo="${errorInfo}";
            if(errorInfo!=null&&errorInfo!=""){
            	jAlert(errorInfo,'提示',function() {});
            }
        });
        
        function importExcel() {

            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
            	jAlert('请选择导入内容','提示',function() {});
                return false;
            }
            jQuery("#form1").submit();
        }
 <%--   </script>--%>

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">批量导入教职工</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于批量导入教职工.<br />
                    2.可下载excel模块,按照excel模板说明,填写教职工信息,进行批量导入教职工.
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="${ctx }/admin/jiaowu/employee/batchImportEmployee" enctype="multipart/form-data">
                        <p>                        
                        	<label>excel模板说明</label>
                            <span class="field">
                            	1.姓名<font color="red">(必填)</font><br>
                            	2.出生日期<font color="red">(必填),格式为"1994-06-22".</font><br>
                            	3.身份证<font color="red">(必填)</font><br>
                        		4.电话<font color="red">(必填)</font><br>
                        		5.邮箱<font color="red">(必填)</font><br>
                        		6.密码<font color="red">(必填)</font><br>
                        		7.年龄<font color="red">(必填)</font><br>
                        		8.性别 (1代表男,0代表女)<font color="red">(必填)</font><br>
                        		<%--9.是否残疾 (1代表是,0代表否)<font color="red">(必填)</font><br>--%>
                        		9.民族<font color="red">(必填)</font><br>
                        		10.学历<font color="red">(必填)</font><br>
                        		11.专业<font color="red">(必填)</font><br>
                        		12.职务<font color="red">(必填)</font><br>
                        		<%--14.基本工资<font color="red">(必填)</font><br>--%>
                        		13.履历信息<font color="red">(必填)</font><br>
                        		<a href="${ctx}/static/common/import_employee.xls" style="color: red;">点击下载模板</a><br>
                        	
                            </span>
                        </p>
                        <p>
                        	<label>上传</label>
                            <span class="field">
                            <input id="myFile" type="file" value="" name="myFile"/>
                            </span>
                        </p>
                        
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="importExcel()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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