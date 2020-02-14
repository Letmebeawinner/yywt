<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改菜品</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#usetime',
                format: 'YYYY-MM-DD'
            });
            uploadFile("uploadFile", false, "fileUrl", imagePath, callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);

        }
        function upFile() {
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

        function addFormSubmit() {
            var messId = jQuery("#messId").val();
            var breakfast = jQuery("#breakfast").val();
            if (breakfast == null || breakfast == '') {
                alert("早餐名称不能为空!");
                return;
            }
            var bprice = jQuery("#breakprice").val();
            if (bprice == null || bprice == '') {
                alert("早餐价格不能为空!");
                return;
            }
            var lunch = jQuery("#lunch").val();
            if (lunch == null || lunch == '') {
                alert("午餐名称不能为空!");
                return;
            }
            var lunchPrice = jQuery("#lunchprice").val();
            if (lunchPrice == null || lunchPrice == '') {
                alert("午餐价格不能为空!");
                return;
            }
            var dinner = jQuery("#dinner").val();
            if (dinner == null || dinner == '') {
                alert("晚餐名称不能为空!");
                return;
            }
            var dinnerprice = jQuery("#dinnerprice").val();
            if (dinnerprice == null || dinnerprice == '') {
                alert("晚餐价格不能为空!");
                return;
            }
            var usetime = jQuery("#usetime").val();
            if (usetime == null || usetime == '') {
                alert("使用时间不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateDishes.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllDishes.json?id=" + messId;
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
        <h1 class="pagetitle">修改菜品</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="dishes.id" value="${dishes.id}">
                    <input type="hidden" name="dishes.messId" value="${dishes.messId}" id="messId">
                    <label><em style="color: red;">*</em>早餐</label>
                    <span class="field">
                        <input type="text" name="dishes.breakfast" class="longinput" id="breakfast"
                               value="${dishes.breakfast}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>早餐价格</label>
                    <span class="field">
                        <input type="text" name="dishes.breakprice" class="longinput" id="breakprice"
                               value="${dishes.breakprice}"/>单位元
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>午餐</label>
                    <span class="field">
                        <input type="text" name="dishes.lunch" class="longinput" id="lunch" value="${dishes.lunch}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>午餐价格</label>
                    <span class="field">
                        <input type="text" name="dishes.lunchprice" class="longinput" id="lunchprice"
                               value="${dishes.lunchprice}"/>单位元
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>晚餐</label>
                    <span class="field">
                        <input type="text" name="dishes.dinner" class="longinput" id="dinner" value="${dishes.dinner}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>晚餐价格</label>
                    <span class="field">
                        <input type="text" name="dishes.dinnerprice" class="longinput" id="dinnerprice"
                               value="${dishes.dinnerprice}"/>单位元
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>使用时间</label>
                    <span class="field">
                        <input type="text" name="dishes.usetime" class="longinput" id="usetime"
                               value="<fmt:formatDate value="${dishes.usetime}" pattern="yyyy-MM-dd"/>"/>
                    </span>
                </p>
                <%--新加字段--%>
                 <p>
                    <label>图片:</label>
                    <span class="field">
						 <input type="hidden" name="dishes.imagePath" id="fileUrl" value="${dishes.imagePath}"/>
						 <input type="button" id="uploadFile" value="上传图片"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <img src="${dishes.imagePath}" style="width: 200px;height:150px;">
					</span>
                </p>
                <p>
                    <label>描述</label>
                    <span class="field">
                        <input type="text" value="${dishes.description}" name="dishes.description" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label>农残检测</label>
                    <span class="field">
                        <input type="text" value="${dishes.pesticideResidues}" name="dishes.pesticideResidues"
                               class="longinput"/>
                    </span>
                </p>
                <p>
                    <label>菜品检样</label>
                    <span class="field">
                        <input type="text" value="${dishes.retentionSamples}" name="dishes.retentionSamples"
                               class="longinput"/>
                    </span>
                </p>
                <%--新增字段结束--%>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>