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
        var index1=0;
        var index2=0;
        var index3=0;
        var index4=0;
        var index5=0;
        var index6=0;
        var index7=0;
        var index8=0;
        var index9=0;
        var index10=0;
        var index11=0;
        var index12=0;
        var index13=0;
        var index14=0;
        var index15=0;
        var index16=0;
        var index17=0;
        var index18=0;
        var index19=0;
        var index20=0;
        var index21=0;

        function addTwentyoneEvaluate(){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/twentyoneEvaluate/createTwentyoneEvaluate.json',
                data:{
                    "twentyoneEvaluate.index1":index1,
                    "twentyoneEvaluate.index2":index2,
                    "twentyoneEvaluate.index3":index3,
                    "twentyoneEvaluate.index4":index4,
                    "twentyoneEvaluate.index5":index5,
                    "twentyoneEvaluate.index6":index6,
                    "twentyoneEvaluate.index7":index7,
                    "twentyoneEvaluate.index8":index8,
                    "twentyoneEvaluate.index9":index9,
                    "twentyoneEvaluate.index10":index10,
                    "twentyoneEvaluate.index11":index11,
                    "twentyoneEvaluate.index12":index12,
                    "twentyoneEvaluate.index13":index13,
                    "twentyoneEvaluate.index14":index14,
                    "twentyoneEvaluate.index15":index15,
                    "twentyoneEvaluate.index16":index16,
                    "twentyoneEvaluate.index17":index17,
                    "twentyoneEvaluate.index18":index18,
                    "twentyoneEvaluate.index19":index19,
                    "twentyoneEvaluate.index20":index20,
                    "twentyoneEvaluate.index21":index21,
                    "twentyoneEvaluate.gain":jQuery("#gain").val(),
                    "twentyoneEvaluate.advice":jQuery("#advice").val(),
                },
                type:'post',
                dataType:'json',
                success:function (result){
                    jAlert(result.message,'提示',function() {});
                } ,
                error:function(e){
                    jAlert('添加失败','提示',function() {});
                }
            });
        }
    </script>
    <style type="text/css">
        small{font-size: 18px;}
    </style>
</head>
<body>

<div class="centercontent tables page-all-invite">
    <div class="pageheader notab pageStyle">
        <h3 class="page-list pb20 pt30">
            贵阳市干部教育培训办班<c:if test="${type==2}">（异地培训）</c:if>综合质量评估表
        </h3>
        <div class="clearfix produce_alls">
            <span class="fl" style="width: 706px;">培训班名称:
                <c:if test="${classes!=null}">
                    <b>${classes.name}</b>
                </c:if>
            </span>
            <span class="fl" style="width: 496px;">培训起止日期:
                <c:if test="${classes!=null}">
                    <fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd"/>-
                    <fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd"/>
                </c:if>
            </span>
        </div>
        <div class="clearfix produce_alls">
            <span class="fl" style="width: 496px;">办班单位:贵阳市委组织部</span>
            <span class="fl">培训机构:贵阳市委党校</span>
        </div>
        <div class="clearfix produce_alls">
            <p class="fl ">说明:请您根据对培训班的总体感受,在相应的选项栏内化√。</p>
        </div>
        <%--<span>--%>
        <%--<span style="color:red">说明</span><br>--%>
        <%--1.本页面用于填写学习需求调查表.<br />--%>
        <%--2.以下4项内容均为选填,每人只可填写一次,填写完相关内容后请点击"提交"按钮提交.<br />--%>
        <%--</span>--%>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper page-invitor page-moment">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <form id="form1" class="stdform" method="post" action="">
            <div id="validation" class="subcontent">
                <div class="tables-all">
                    <table>
                        <tr>
                            <td colspan="2" rowspan="2">
                                <tt style="width:40px;display:inline-block;font-weight: bolder;">评估内容</tt>
                            </td>
                            <td colspan="1" rowspan="2" >
                                <tt>评估指标</tt>
                            </td>
                            <td colspan="4" >
                                <tt>评估等次</tt>
                            </td>
                        </tr>
                        <tr>

                            <td><tt>满意</tt></td>
                            <td><tt>较满意</tt></td>
                            <td><tt>一般</tt></td>
                            <td><tt>不满意</tt></td>
                        </tr>
                        <tr>
                            <td rowspan="6" style="width: 70px">
                                <em>培训方案</em>
                            </td>
                            <td>
                                <tt>1</tt>
                            </td>
                            <td>需求调查的评价</td>
                            <td class="index1" onclick="jQuery('.index1').html('');index1=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index1" onclick="jQuery('.index1').html('');index1=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index1" onclick="jQuery('.index1').html('');index1=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index1" onclick="jQuery('.index1').html('');index1=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>2</tt>
                            </td>
                            <td>课程设置的评价</td>
                            <td class="index2" onclick="jQuery('.index2').html('');index2=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index2" onclick="jQuery('.index2').html('');index2=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index2" onclick="jQuery('.index2').html('');index2=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index2" onclick="jQuery('.index2').html('');index2=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>3</tt>
                            </td>
                            <td>教学活动安排的评价</td>
                            <td class="index3" onclick="jQuery('.index3').html('');index3=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index3" onclick="jQuery('.index3').html('');index3=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index3" onclick="jQuery('.index3').html('');index3=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index3" onclick="jQuery('.index3').html('');index3=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>4</tt>
                            </td>
                            <td>时间长短和进度的评价</td>
                            <td class="index4" onclick="jQuery('.index4').html('');index4=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index4" onclick="jQuery('.index4').html('');index4=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index4" onclick="jQuery('.index4').html('');index4=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index4" onclick="jQuery('.index4').html('');index4=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>5</tt>
                            </td>
                            <td>师资安排的评价</td>
                            <td class="index5" onclick="jQuery('.index5').html('');index5=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index5" onclick="jQuery('.index5').html('');index5=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index5" onclick="jQuery('.index5').html('');index5=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index5" onclick="jQuery('.index5').html('');index5=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>6</tt>
                            </td>
                            <td>方案创造性的评价</td>
                            <td class="index6" onclick="jQuery('.index6').html('');index6=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index6" onclick="jQuery('.index6').html('');index6=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index6" onclick="jQuery('.index6').html('');index6=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index6" onclick="jQuery('.index6').html('');index6=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td rowspan="6">
                                <em>培训实施</em>
                            </td>
                            <td>
                                <tt>7</tt>
                            </td>
                            <td>内容针对性的评价</td>
                            <td class="index7" onclick="jQuery('.index7').html('');index7=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index7" onclick="jQuery('.index7').html('');index7=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index7" onclick="jQuery('.index7').html('');index7=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index7" onclick="jQuery('.index7').html('');index7=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>8</tt>
                            </td>
                            <td>学习资料质量的评价</td>
                            <td class="index8" onclick="jQuery('.index8').html('');index8=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index8" onclick="jQuery('.index8').html('');index8=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index8" onclick="jQuery('.index8').html('');index8=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index8" onclick="jQuery('.index8').html('');index8=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>9</tt>
                            </td>
                            <td>讲师教学水平和效果的评价</td>
                            <td class="index9" onclick="jQuery('.index9').html('');index9=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index9" onclick="jQuery('.index9').html('');index9=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index9" onclick="jQuery('.index9').html('');index9=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index9" onclick="jQuery('.index9').html('');index9=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>10</tt>
                            </td>
                            <td>方式方法的评价</td>
                            <td class="index10" onclick="jQuery('.index10').html('');index10=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index10" onclick="jQuery('.index10').html('');index10=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index10" onclick="jQuery('.index10').html('');index10=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index10" onclick="jQuery('.index10').html('');index10=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>11</tt>
                            </td>
                            <td>教学组织、管理的评价</td>
                            <td class="index11" onclick="jQuery('.index11').html('');index11=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index11" onclick="jQuery('.index11').html('');index11=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index11" onclick="jQuery('.index11').html('');index11=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index11" onclick="jQuery('.index11').html('');index11=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>12</tt>
                            </td>
                            <td>运用现代教学手段的评价</td>
                            <td class="index12" onclick="jQuery('.index12').html('');index12=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index12" onclick="jQuery('.index12').html('');index12=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index12" onclick="jQuery('.index12').html('');index12=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index12" onclick="jQuery('.index12').html('');index12=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                            <tr>
                            <td rowspan="4">
                                <em>培训保障</em>
                            </td>
                            <td>
                                <tt>13</tt>
                            </td>
                            <td>教学设施设备的评价</td>
                            <td class="index13" onclick="jQuery('.index13').html('');index13=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index13" onclick="jQuery('.index13').html('');index13=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index13" onclick="jQuery('.index13').html('');index13=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index13" onclick="jQuery('.index13').html('');index13=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>14</tt>
                            </td>
                            <td>食宿等卫生情况的评价</td>
                            <td class="index14" onclick="jQuery('.index14').html('');index14=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index14" onclick="jQuery('.index14').html('');index14=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index14" onclick="jQuery('.index14').html('');index14=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index14" onclick="jQuery('.index14').html('');index14=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>15</tt>
                            </td>
                            <td>学习环境、卫生状况的评价</td>
                            <td class="index15" onclick="jQuery('.index15').html('');index15=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index15" onclick="jQuery('.index15').html('');index15=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index15" onclick="jQuery('.index15').html('');index15=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index15" onclick="jQuery('.index15').html('');index15=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>16</tt>
                            </td>
                            <td>培训学风、纪律的评价</td>
                            <td class="index16" onclick="jQuery('.index16').html('');index16=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index16" onclick="jQuery('.index16').html('');index16=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index16" onclick="jQuery('.index16').html('');index16=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index16" onclick="jQuery('.index16').html('');index16=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td rowspan="5">
                                <em>培训效果</em>
                            </td>
                            <td>
                                <tt>17</tt>
                            </td>
                            <td>实现培训目标程度的评价</td>
                            <td class="index17" onclick="jQuery('.index17').html('');index17=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index17" onclick="jQuery('.index17').html('');index17=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index17" onclick="jQuery('.index17').html('');index17=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index17" onclick="jQuery('.index17').html('');index17=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>18</tt>
                            </td>
                            <td>提高政策理论水平的评价</td>
                            <td class="index18" onclick="jQuery('.index18').html('');index18=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index18" onclick="jQuery('.index18').html('');index18=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index18" onclick="jQuery('.index18').html('');index18=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index18" onclick="jQuery('.index18').html('');index18=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>19</tt>
                            </td>
                            <td>提高工作能力和创新能力的评价</td>
                            <td class="index19" onclick="jQuery('.index19').html('');index19=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index19" onclick="jQuery('.index19').html('');index19=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index19" onclick="jQuery('.index19').html('');index19=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index19" onclick="jQuery('.index19').html('');index19=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>20</tt>
                            </td>
                            <td>培训重要性的评价</td>
                            <td class="index20" onclick="jQuery('.index20').html('');index20=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index20" onclick="jQuery('.index20').html('');index20=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index20" onclick="jQuery('.index20').html('');index20=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index20" onclick="jQuery('.index20').html('');index20=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>
                        <tr>
                            <td>
                                <tt>21</tt>
                            </td>
                            <td>总体满意度的评价</td>
                            <td class="index21" onclick="jQuery('.index21').html('');index21=1;jQuery(this).html('<small>√</small>');">
                                <%--<small class="c-red fsize18">√</small>--%>
                            </td>
                            <td class="index21" onclick="jQuery('.index21').html('');index21=2;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index21" onclick="jQuery('.index21').html('');index21=3;jQuery(this).html('<small>√</small>');"></td>
                            <td class="index21" onclick="jQuery('.index21').html('');index21=4;jQuery(this).html('<small>√</small>');"></td>
                        </tr>


                        <tr>
                            <td >
                               <em>请简要</em>
                            </td>
                            <td colspan="6">
                                <i>在这次培训中您最大的收获是什么?</i>
                                  <textarea name="gain"   placeholder="在这次培训中您最大的收获是什么"  id="gain">

                                    </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td >
                               <em>表述</em>
                            </td>
                            <td colspan="6">
                                <i>您对这次培训哪些方面比较满意,哪些方面还不满意?您认为应如何改进?</i>
                                    <textarea name="advice" placeholder="请介绍自己..." id="advice">

                                    </textarea>
                            </td>
                        </tr>
                    </table>

                </div>
            </div>
            </form>
            <p class="stdformbutton tac">
                <button class="radius2" onclick="addTwentyoneEvaluate()" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-top: 30px;">
                    提交
                </button>
                <%--<button class="radius2" onclick="clear()" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 20px;">
                    清空
                </button>--%>
            </p>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>

</body>
</html>