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

    </script>
    <style type="text/css">
        small{font-size: 18px;}
    </style>
</head>
<body>

<div class="centercontent tables page-all-invite">
    <div class="pageheader notab pageStyle">
        <h3 class="page-list pb20 pt30">
            贵阳市干部教育培训办班综合质量评估表
        </h3>
        <div class="clearfix produce_alls">
            <span class="fl" style="width: 496px;">培训班名称:
                <c:if test="${classes!=null}">
                    ${classes.name}
                </c:if>
            </span>
            <span class="fl">培训起止日期:
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
                                <td rowspan="6">
                                    <em>培训方案</em>
                                </td>
                                <td>
                                    <tt>1</tt>
                                </td>
                                <td>需求调查的评价</td>
                                <td class="index1">
                                    <c:if test="${twentyoneEvaluate.index1==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index1">
                                    <c:if test="${twentyoneEvaluate.index1==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index1">
                                    <c:if test="${twentyoneEvaluate.index1==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index1">
                                    <c:if test="${twentyoneEvaluate.index1==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>2</tt>
                                </td>
                                <td>课程设置的评价</td>
                                <td class="index2">
                                        <c:if test="${twentyoneEvaluate.index2==1}">
                                            <small>√</small>
                                        </c:if>
                                </td>
                                <td class="index2">
                                    <c:if test="${twentyoneEvaluate.index2==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index2">
                                    <c:if test="${twentyoneEvaluate.index2==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index2">
                                    <c:if test="${twentyoneEvaluate.index2==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>3</tt>
                                </td>
                                <td>教学活动安排的评价</td>
                                <td class="index3">
                                    <c:if test="${twentyoneEvaluate.index3==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index3">
                                    <c:if test="${twentyoneEvaluate.index3==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index3">
                                    <c:if test="${twentyoneEvaluate.index3==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index3">
                                    <c:if test="${twentyoneEvaluate.index3==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>4</tt>
                                </td>
                                <td>时间长短和进度的评价</td>
                                <td class="index4">
                                    <c:if test="${twentyoneEvaluate.index4==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index4">
                                    <c:if test="${twentyoneEvaluate.index4==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index4">
                                    <c:if test="${twentyoneEvaluate.index4==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index4">
                                    <c:if test="${twentyoneEvaluate.index4==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>5</tt>
                                </td>
                                <td>师资安排的评价</td>
                                <td class="index5">
                                    <c:if test="${twentyoneEvaluate.index5==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index5">
                                    <c:if test="${twentyoneEvaluate.index5==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index5">
                                    <c:if test="${twentyoneEvaluate.index5==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index5">
                                    <c:if test="${twentyoneEvaluate.index5==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>6</tt>
                                </td>
                                <td>方案创造性的评价</td>
                                <td class="index6">
                                    <c:if test="${twentyoneEvaluate.index6==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index6">
                                    <c:if test="${twentyoneEvaluate.index6==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index6">
                                    <c:if test="${twentyoneEvaluate.index6==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index6">
                                    <c:if test="${twentyoneEvaluate.index6==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="6">
                                    <em>培训实施</em>
                                </td>
                                <td>
                                    <tt>7</tt>
                                </td>
                                <td>内容针对性的评价</td>
                                <td class="index7">
                                    <c:if test="${twentyoneEvaluate.index7==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index7">
                                    <c:if test="${twentyoneEvaluate.index7==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index7">
                                    <c:if test="${twentyoneEvaluate.index7==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index7">
                                    <c:if test="${twentyoneEvaluate.index7==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>8</tt>
                                </td>
                                <td>学习资料质量的评价</td>
                                <td class="index8">
                                    <c:if test="${twentyoneEvaluate.index8==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index8">
                                    <c:if test="${twentyoneEvaluate.index8==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index8">
                                    <c:if test="${twentyoneEvaluate.index8==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index8">
                                    <c:if test="${twentyoneEvaluate.index8==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>9</tt>
                                </td>
                                <td>讲师教学水平和效果的评价</td>
                                <td class="index9">
                                    <c:if test="${twentyoneEvaluate.index9==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index9">
                                    <c:if test="${twentyoneEvaluate.index9==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index9">
                                    <c:if test="${twentyoneEvaluate.index9==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index9">
                                    <c:if test="${twentyoneEvaluate.index9==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>10</tt>
                                </td>
                                <td>方式方法的评价</td>
                                <td class="index10">
                                    <c:if test="${twentyoneEvaluate.index10==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index10">
                                    <c:if test="${twentyoneEvaluate.index10==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index10">
                                    <c:if test="${twentyoneEvaluate.index10==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index10">
                                    <c:if test="${twentyoneEvaluate.index10==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>11</tt>
                                </td>
                                <td>教学组织、管理的评价</td>
                                <td class="index11">
                                    <c:if test="${twentyoneEvaluate.index11==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index11">
                                    <c:if test="${twentyoneEvaluate.index11==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index11">
                                    <c:if test="${twentyoneEvaluate.index11==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index11">
                                    <c:if test="${twentyoneEvaluate.index11==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>12</tt>
                                </td>
                                <td>运用现代教学手段的评价</td>
                                <td class="index12">
                                    <c:if test="${twentyoneEvaluate.index12==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index12">
                                    <c:if test="${twentyoneEvaluate.index12==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index12">
                                    <c:if test="${twentyoneEvaluate.index12==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index12">
                                    <c:if test="${twentyoneEvaluate.index12==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="4">
                                    <em>培训保障</em>
                                </td>
                                <td>
                                    <tt>13</tt>
                                </td>
                                <td>教学设施设备的评价</td>
                                <td class="index13">
                                    <c:if test="${twentyoneEvaluate.index13==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index13">
                                    <c:if test="${twentyoneEvaluate.index13==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index13">
                                    <c:if test="${twentyoneEvaluate.index13==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index13">
                                    <c:if test="${twentyoneEvaluate.index13==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>14</tt>
                                </td>
                                <td>食宿等卫生情况的评价</td>
                                <td class="index14">
                                    <c:if test="${twentyoneEvaluate.index14==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index14">
                                    <c:if test="${twentyoneEvaluate.index14==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index14">
                                    <c:if test="${twentyoneEvaluate.index14==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index14">
                                    <c:if test="${twentyoneEvaluate.index14==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>15</tt>
                                </td>
                                <td>学习环境、卫生状况的评价</td>
                                <td class="index15">
                                    <c:if test="${twentyoneEvaluate.index15==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index15">
                                    <c:if test="${twentyoneEvaluate.index15==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index15">
                                    <c:if test="${twentyoneEvaluate.index15==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index15">
                                    <c:if test="${twentyoneEvaluate.index15==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>16</tt>
                                </td>
                                <td>培训学风、纪律的评价</td>
                                <td class="index16">
                                    <c:if test="${twentyoneEvaluate.index16==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index16">
                                    <c:if test="${twentyoneEvaluate.index16==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index16">
                                    <c:if test="${twentyoneEvaluate.index16==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index16">
                                    <c:if test="${twentyoneEvaluate.index16==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="5">
                                    <em>培训效果</em>
                                </td>
                                <td>
                                    <tt>17</tt>
                                </td>
                                <td>实现培训目标程度的评价</td>
                                <td class="index17">
                                    <c:if test="${twentyoneEvaluate.index17==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index17">
                                    <c:if test="${twentyoneEvaluate.index17==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index17">
                                    <c:if test="${twentyoneEvaluate.index17==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index17">
                                    <c:if test="${twentyoneEvaluate.index17==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>18</tt>
                                </td>
                                <td>提高政策理论水平的评价</td>
                                <td class="index18">
                                    <c:if test="${twentyoneEvaluate.index18==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index18">
                                    <c:if test="${twentyoneEvaluate.index18==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index18">
                                    <c:if test="${twentyoneEvaluate.index18==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index18">
                                    <c:if test="${twentyoneEvaluate.index18==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>19</tt>
                                </td>
                                <td>提高工作能力和创新能力的评价</td>
                                <td class="index19">
                                    <c:if test="${twentyoneEvaluate.index19==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index19">
                                    <c:if test="${twentyoneEvaluate.index19==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index19">
                                    <c:if test="${twentyoneEvaluate.index19==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index19">
                                    <c:if test="${twentyoneEvaluate.index19==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>20</tt>
                                </td>
                                <td>培训重要性的评价</td>
                                <td class="index20">
                                    <c:if test="${twentyoneEvaluate.index20==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index20">
                                    <c:if test="${twentyoneEvaluate.index20==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index20">
                                    <c:if test="${twentyoneEvaluate.index20==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index20">
                                    <c:if test="${twentyoneEvaluate.index20==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>21</tt>
                                </td>
                                <td>总体满意度的评价</td>
                                <td class="index21">
                                    <c:if test="${twentyoneEvaluate.index21==1}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index21">
                                    <c:if test="${twentyoneEvaluate.index21==2}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index21">
                                    <c:if test="${twentyoneEvaluate.index21==3}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                                <td class="index21">
                                    <c:if test="${twentyoneEvaluate.index21==4}">
                                        <small>√</small>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td >
                                    <em>请简要</em>
                                </td>
                                <td colspan="6">
                                    <i>在这次培训中您最大的收获是什么?</i>
                                    <textarea name="gain"   placeholder="在这次培训中您最大的收获是什么"  id="gain" readonly="readonly">
                                        ${twentyoneEvaluate.gain}
                                    </textarea>
                                </td>
                            </tr>
                            <tr>
                                <td >
                                    <em>表述</em>
                                </td>
                                <td colspan="6">
                                    <i>您对这次培训哪些方面比较满意,哪些方面还不满意?您认为应如何改进?</i>
                                    <textarea name="advice" placeholder="请介绍自己..." id="advice" readonly="readonly">
                                        ${twentyoneEvaluate.advice}
                                    </textarea>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </form>
            <p class="stdformbutton tac">
                <button class="radius2" onclick="javascript:history.go(-1);" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-top: 30px;">
                    返回
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