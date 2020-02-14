<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>导入学员名单</title>
    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function(){
            laydate.skin('molv');
            uploadFile("uploadFile",false,"myFile",'http://10.100.101.1:6694',callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);

        }
        function upFile(){
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }


        function addFormSubmit() {
            jQuery.ajax({
                url: "${ctx}/admin/jiaowu/user/createUserExcelRecord.json",
                data:jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                   /* if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/teachingMaterial/teachingMaterialList.json";
                    } else {
                        jAlert(result.message,'提示',function() {});
                    }*/
                    jAlert(result.message,'提示',function() {});
                }
            });
        }

        jQuery(function(){
            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":selectedClassTypeId
                    },
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        if(result.code=="0"){
                            var list=result.data;
                            var classstr="<option value=0>请选择</option>";
                            if(list!=null&&list.length>0){

                                for(var i=0;i<list.length;i++){
                                    classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
//                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
        });
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">导入学员名单</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于导入学员名单<br />
                    2.按要求填写相关信息,点击"提交"按钮,导入学员名单.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
		</span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field"><input type="text" name="userExcelRecord.title" id="title" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>班型</label>
                    <span class="field">
                        <select name="userExcelRecord.classTypeId" class="vam" id="classTypeId">
								<option value="0">请选择</option>
								<c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>班次</label>
                    <span class="field">
                        <select name="userExcelRecord.classId" class="vam" id="classId" >
								<option value="0">请选择</option>
							</select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文件地址:</label>
					 <span class="field">
						 <input type="text" name="userExcelRecord.url"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <center><h4  id="file"></h4></center>
					</span>
                </p>
            </form>
            <p class="stdformbutton">
                <button class="radius2" onclick="addFormSubmit()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                    提交
                </button>
                <br/>
        </div>
    </div>
</div>

</body>
</html>