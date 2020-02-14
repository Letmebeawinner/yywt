<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建党性锻炼成绩</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">
        function addPartySpirit(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/partySpirit/createPartySpirit.json',
                data:params,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        window.location.href="${ctx}/admin/jiaowu/partySpirit/partySpiritList.json";
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('添加失败','提示',function() {});
                }
            });
        }

        function selectUser(){
            window.open('${ctx}/jiaowu/user/userListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addUser(userIdAndName){
            jQuery("#studentId").val(userIdAndName[0]);
            jQuery("#studentName").val(userIdAndName[1]);
            jQuery("#userspan").html(userIdAndName[1]);
        }
    </script>


    </head>
    <body>
    <div class="centercontent tables">
            <div class="pageheader notab" style="margin-left: 30px">
            <h1 class="pagetitle">新建党性锻炼成绩</h1>
            <span>
            <span style="color:red">说明</span><br>
            1.本页面用于新建党性锻炼成绩<br />
    2.按要求填写相关信息,点击"提交"按钮,新建党性锻炼成绩.<br />
    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
    </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
            <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

            <form id="form1" class="stdform" method="post" action="">
                <input type="hidden" name="partySpirit.userId" id="studentId"/>
                <input type="hidden" name="partySpirit.name" id="studentName" />
            <%--<p>
            <label><em style="color: red;">*</em>班次</label>
            <span class="field">
            <span id="classspan"></span>
            <a href="javascript:selectClass()" class="stdbtn btn_orange">选择班次</a>
            </span>
            </p>--%>
                <p>
                    <label><em style="color: red;">*</em>学员</label>
                        	<span class="field">
                                <span id="userspan"></span>
                                <a href="javascript:selectUser()" class="stdbtn btn_orange">选择学员</a>
                            </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>个人党性分析材料</label>
                    <span class="field"><input type="text" name="partySpirit.personMaterial" id="personMaterial" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>组织纪律</label>
                    <span class="field"><input type="text" name="partySpirit.organisation" id="organisation" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>综合表现</label>
                    <span class="field"><input type="text" name="partySpirit.allPerformance" id="allPerformance" class="longinput" onchange="if(/^\d+$|^\d+\.\d+$/.test(this.value)==false){alert('只能输入数字');this.value='';}"/></span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="partySpirit.note" class="mediuminput" id="note"></textarea></span>
                </p>
            <br />


            </form>
            <p class="stdformbutton">
            <button class="radius2" onclick="addPartySpirit()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
            </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
            </div><!-- #updates -->
            </div>
            </div>
            </body>
            </html>