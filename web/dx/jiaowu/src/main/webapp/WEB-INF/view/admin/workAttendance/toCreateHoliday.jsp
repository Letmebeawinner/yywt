<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>请假</title>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });

        /*function addHoliday(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/holiday/createHoliday.json',
                data:params,
                type:'post',
                dataType:'json',
                success:function (result){
                    jAlert(result.message,'提示',function() {});
                    if(result.code=="0"){
                        jQuery("#note").val("");
                        jQuery("#startTime").val("");
                        jQuery("#endTime").val("");
                    }

                } ,
                error:function(e){
                    jAlert('添加失败','提示',function() {});
                }
            });
        }*/
        function addHoliday(){
            var startTimeValue=jQuery("#startTime").val();
            if(startTimeValue==null){
                jAlert('请填写开始时间','提示',function() {});
            }
            var endTimeValue=jQuery("#endTime").val();
            if(endTimeValue==null){
                jAlert('请填写结束时间','提示',function() {});
            }
            var startTime=new Date(startTimeValue);
            var endTime=new Date(endTimeValue);
            if((parseInt(endTime-startTime)/(1000*60*60*24))>=2){
                jConfirm('该学员请假时间超过了2天，请确认是否提交该申请？','确认',function(r){
                    if(r){
                        var params=jQuery("#form1").serialize();
                        jQuery.ajax({
                            url:'${ctx}/admin/jiaowu/holiday/createHoliday.json',
                            data:params,
                            type:'post',
                            dataType:'json',
                            success:function (result){
                                jAlert(result.message,'提示',function() {});
                                if(result.code=="0"){
                                    jQuery("#note").val("");
                                    jQuery("#startTime").val("");
                                    jQuery("#endTime").val("");
                                }

                            } ,
                            error:function(e){
                                jAlert('添加失败','提示',function() {});
                            }
                        });
                    }
                });
            }else{
                var params=jQuery("#form1").serialize();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/holiday/createHoliday.json',
                    data:params,
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        jAlert(result.message,'提示',function() {});
                        if(result.code=="0"){
                            jQuery("#note").val("");
                            jQuery("#startTime").val("");
                            jQuery("#endTime").val("");
                        }

                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            }

        }
    </script>

    </head>
    <body>
    <div class="centercontent tables">
            <div class="pageheader notab" style="margin-left: 30px">
            <h1 class="pagetitle">请假</h1>
            <span>
            <span style="color:red">说明</span><br>
            1.本页面用于请假<br />
            2.按要求填写相关信息,点击"提交"按钮,请假.<br />
            3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
    </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
            <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

            <form id="form1" class="stdform" method="post" action="">

            <p>
            <label><em style="color: red;">*</em>开始时间</label>
            <span class="field">
            <input id="startTime" type="text" class="width100 laydate-icon" style="width: 160px;height: 40px;" name="holiday.beginTime"/>
            </span>
            </p>
            <p>
            <label><em style="color: red;">*</em>结束时间</label>
            <span class="field">
            <input id="endTime" type="text" class="width100 laydate-icon" style="width: 160px;height: 40px;" name="holiday.endTime"/>
            </span>
            </p>
            <p>
            <label>原因</label>
            <span class="field"><textarea cols="80" rows="5" name="holiday.reason" class="mediuminput" id="note"></textarea></span>
            </p>
            <br />

            </form>
            <p class="stdformbutton">
            <button class="radius2" onclick="addHoliday()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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