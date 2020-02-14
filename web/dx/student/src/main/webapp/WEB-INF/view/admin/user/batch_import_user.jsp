<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>批量报名</title>
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
    </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">批量报名<span id="endTime"></span></h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于批量报名<br />
                    2.可下载excel模板,参照模板说明,填写学员信息,批量报名.
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="${ctx }/admin/jiaowu/user/batchImportUser.json" enctype="multipart/form-data">
                        <p>
							<div style="font-size: 20px;margin: 0 auto;width: 300px;font-weight: bold">
						<a href="http://10.100.101.1:6698/static/advisory/学员报名指南.pdf" target="_blank">学员报名指南(从此处下载)</a> </div>
                        	<label>excel模板说明：</label>
                            <span class="field" style="margin-top: 30px">
<!--                             	1.年份(<font color="red">4位数</font>),如1980,必填.<br> -->
<!--                             	2.月份(<font color="red">2位数</font>),如05,必填.<br> -->
                        		1.班型ID,请到<a href="${ctx}/admin/jiaowu/classType/classTypeList.json" target="_blank" style="color: blue">字典管理-班型列表</a>查询，必填.<br>
                        		2.班次ID,请到<a href="${ctx}/admin/jiaowu/class/classList.json" target="_blank" style="color: blue">字典管理-班次列表</a>查询,必填.<br>
<!--                         		5.学员序号(<font color="red">3位数</font>),必填.<br> -->
                        		3.姓名,必填.<br>
                        		4.身份证号,必填.<br>
                        		5.手机号,必填.<br>
                        		6.邮箱,非必填.<br>
                        		7.密码,必填.<br>
                        		8.性别(男,女),必填.<br>
                        		9.政治面貌,请填写"中共党员","民主党派","无党派人士","群众","其它"这几项中的一种,必填.<br>
                        		10.学历,必填.<br>
								11.单位ID,请填写<a href="${ctx}/admin/jiaowu/user/unitList.json" target="_blank" style="color: blue">单位列表页面</a>中的单位ID，必填.<br>
								12.学员单位及职务职称（全称）,必填.<br>
								13.级别,请填写"正厅","巡视员","副厅","副巡视员","正县","副县","调研员","副调研员","正科","副科"中的一种,必填.<br>
								14.民族,必填.<br>
								15.报送人姓名,必填<br>
								16.报送人手机号,必填<br>
								17.备注<br>
                        		<a href="${ctx }/static/common/import_student.xls" style="color: red;">点击下载模板</a><br>
                        		<%--<a href="${ctx }/admin/jiaowu/user/getBatchImportUserModel.json" style="color: red;">点击下载模板</a><br>--%>

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