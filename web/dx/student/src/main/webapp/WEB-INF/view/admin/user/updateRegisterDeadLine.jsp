<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改报名截止时间</title>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });

        function updateRegisterDeadline(){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user//updateRegisterDeadLine.json',
                data:{"deadline":jQuery("#startTime").val()},
                type:'post',
                dataType:'json',
                success:function (result){
                    jAlert(result.message,'提示',function() {});
                } ,
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }

    </script>

    </head>
    <body>
    <div class="centercontent tables">
            <div class="pageheader notab" style="margin-left: 30px">
            <h1 class="pagetitle">修改报名截止时间</h1>
            <span>
            <span style="color:red">说明</span><br>
            1.本页面用于修改报名截止时间<br />
            </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
            <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

            <form id="form1" class="stdform" method="post" action="">

            <p>
            <label><em style="color: red;">*</em>报名截止时间</label>
            <span class="field">
            <input id="startTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="classes.startTime" value="<fmt:formatDate value='${registerDeadline.deadline}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"/>
            </span>
            </p>
            <br />

            </form>
            <p class="stdformbutton">
            <button class="radius2" onclick="updateRegisterDeadline()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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