<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>批量导入教职工</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
              
        jQuery(function(){
            var errorInfo="${errorInfo}";
            if(errorInfo!=null&&errorInfo!=""){
            	alert(errorInfo);
            }
        });
        
        function importExcel() {

            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
            	alert("请选择导入内容");
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">批量导入教职工</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于批量导入教职工<br />
                    2.点击<a href="${ctx}/static/exceltemplate/import_employee1.xls"
                       style="color:red">下载excel模板</a>,
                    参照模板的写法,填写教职工信息,批量导入.
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="${ctx}/admin/rs/batchAddEmployee.json" enctype="multipart/form-data">
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