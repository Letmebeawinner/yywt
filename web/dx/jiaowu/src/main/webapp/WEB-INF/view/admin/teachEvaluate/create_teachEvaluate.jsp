<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>调查表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>

    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        var writeall=true;
        function validate(){
            writeall=true;
            var index1=jQuery("#index1").val();
            if(index1==""){
//                index1=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index2=jQuery("#index2").val();
            if(index2==""){
//                index2=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index3=jQuery("#index3").val();
            if(index3==""){
//                index3=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index4=jQuery("#index4").val();
            if(index4==""){
//                index4=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index5=jQuery("#index5").val();
            if(index5==""){
//                index5=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index6=jQuery("#index6").val();
            if(index6==""){
//                index6=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index7=jQuery("#index7").val();
            if(index7==""){
//                index7=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
            var index8=jQuery("#index8").val();
            if(index8==""){
//                index8=0;
                jAlert("请填写各项分数",'提示',function() {});
                writeall=false;
            }
        }

        function calculateTotal(){
            validate();
            if(!writeall){
                return false;
            }
            var total=parseInt(jQuery("#index1").val())+parseInt(jQuery("#index2").val())+parseInt(jQuery("#index3").val())+parseInt(jQuery("#index4").val())+parseInt(jQuery("#index5").val())+parseInt(jQuery("#index6").val())+parseInt(jQuery("#index7").val())+parseInt(jQuery("#index8").val());
            jQuery("#total").val(total);
        }

        function addTeachEvaluate(){
            /*validate();
            if(!writeall){
                return false;
            }*/
            calculateTotal();
            if(!writeall){
                return false;
            }
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/teachEvaluate/addTeachEvaluate.json',
                data:jQuery("#form1").serialize(),
                type:'post',
                dataType:'json',
                success:function (result){
                    jAlert(result.message,'提示',function() {});
                },
                error:function(e){
                    jAlert('删除失败','提示',function() {});
                }
            });
        }
    </script>
</head>
<body>

<div class="centercontent tables page-all-invite">
    <div class="pageheader notab pageStyle">
        <h1 class="page-list">
            中共贵阳市委党校主体班教学质量评估表
        </h1>
        <h2><a href="javascript:;" title="青干班" style="text-decoration: none;">(青干班)</a></h2>
        <%--<span>--%>
        <%--<span style="color:red">说明</span><br>--%>
        <%--1.本页面用于填写学习需求调查表.<br />--%>
        <%--2.以下4项内容均为选填,每人只可填写一次,填写完相关内容后请点击"提交"按钮提交.<br />--%>
        <%--</span>--%>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper page-invitor"><!--contentwrapper-->
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <form id="form1" class="stdform" method="post" action="">
                <input type="hidden" name="teachEvaluate.teacherId" value="${teacherId}"/>
                <input type="hidden" name="teachEvaluate.courseId" value="${course.id}"/>
                <input type="hidden" name="teachEvaluate.courseName" value="${course.name}"/>
                <input type="hidden" name="teachEvaluate.courseArrangeId" value="${courseArrangeId}"/>
            <div id="validation" class="subcontent">
                <div class="tables-all">
                    <table>
                        <tr>
                            <td colspan="2">
                                <tt>任课讲师</tt>
                            </td>
                            <td colspan="3">
                                <tt>${teacherName}</tt>
                            </td>
                            <td colspan="2">
                                <tt>日期</tt>
                            </td>
                            <td colspan="2">
                                <tt><fmt:formatDate type="both" value="${createTime}" pattern="yyyy-MM-dd"/></tt>
                            </td>

                        </tr>
                        <tr>
                            <td colspan="2">
                                <tt>教学课程内容</tt>
                            </td>
                            <td colspan="7">
                                <tt>${course.name}</tt>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="9">
                                <tt style="font-weight: bolder">评价指标及分值</tt>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <tt style="font-weight: bolder">教学态度20%</tt>
                            </td>
                            <td colspan="2">
                                <tt style="font-weight: bolder">教学内容30%</tt>
                            </td>
                            <td colspan="2">
                                <tt style="font-weight: bolder">教学方法20%</tt>
                            </td>
                            <td colspan="2">
                                <tt style="font-weight: bolder">教学效果30%</tt>
                            </td>
                            <td rowspan="2" >
                                <tt>合计总分</tt>
                            </td>
                        </tr>
                        <tr>
                            <td >
                                备课认真治学严谨(10分)
                            </td>
                            <td >
                                教态端庄为人师表(10分)
                            </td>
                            <td >
                                层次分明突出重点(15分)
                            </td>
                            <td >
                                联系实际范例恰当(15分)
                            </td>
                            <td >
                                语言清晰善用多媒(10分)
                            </td>
                            <td >
                                启发教学培养思维(10分)
                            </td>
                            <td >
                                教学内容印象深刻(15分)
                            </td>
                            <td >
                                个人成长帮助较大(15分)
                            </td>

                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index1" id="index1" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index2" id="index2" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index3" id="index3" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index4" id="index4" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index5" id="index5" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index6" id="index6" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index7" id="index7" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.index8" id="index8" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/>
                            </td>
                            <td style="padding: 10px;">
                                <input type="text" name="teachEvaluate.total" id="total" onfocus="calculateTotal()"/>
                            </td>
                        </tr>

                    </table>

                </div>
            </div>
            </form>
            <p class="stdformbutton tac">
                <button class="radius2" onclick="addTeachEvaluate()" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-top: 30px;">
                    提交
                </button>
                <%--<button class="radius2" onclick="clear()" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 20px;">
                    清空
                </button>--%>
            </p>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div><!--contentwrapper-->
</div>

</body>
</html>