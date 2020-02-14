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

        function clear(){
            jQuery("#hotQuestion").html("");
            jQuery("#solveQuestion").html("");
            jQuery("#advice").html("");
            jQuery("#other").html("");
        }
        function addSearch(){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/addSearch.json',
                data:{
                    "search.hotQuestion":jQuery("#hotQuestion").val(),
                    "search.solveQuestion":jQuery("#solveQuestion").val(),
                    "search.advice":jQuery("#advice").val(),
                    "search.other":jQuery("#other").val()},
                type:'post',
                dataType:'json',
                success:function (result) {
                    jAlert(result.message,'提示',function() {});
                    if (result.code == "0") {
                        clear();
                    }
                }
            });
        }
    </script>
</head>
<body>

<div class="centercontent tables page-all-invite">
    <div class="pageheader notab pageStyle">
        <h1 class="page-list">
            贵阳市委党校调训学员学习需求调查表
        </h1>
        <h2><a href="javascript:;" title="请参训学员本人填写并于入学前报市委党校">(请参训学员本人填写并于入学前报市委党校)</a></h2>
        <div class="clearfix produce_all">
            <span class="fl">单位:${user.unit}</span>
            <span class="fr">学员签名:${user.name}</span>
        </div>
                <%--<span>--%>
                    <%--<span style="color:red">说明</span><br>--%>
                    <%--1.本页面用于填写学习需求调查表.<br />--%>
                    <%--2.以下4项内容均为选填,每人只可填写一次,填写完相关内容后请点击"提交"按钮提交.<br />--%>
				<%--</span>--%>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper page-invitor"><!--contentwrapper-->
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <%--<form id="form1" class="stdform" method="post" action="">--%>
                <div id="validation" class="subcontent">
                    <div class="tables-all">
                        <table>
                            <tr>
                                <td>
                                    <tt>班次名称</tt>
                                </td>
                                <td colspan="2">
                                    ${user.className}
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="5">
                                    <tt>学习需求</tt>
                                </td>
                                <td colspan="2">
                                   <span>请您围绕培训专题,针对实际工作需要,提出您参加此次培训最迫切希望解决的具体问题</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="page-padding">
                                    带来一个干部群众最关心的一个热点问题
                                </td>
                                <td style="padding: 0;">
                                    <textarea name="" id="hotQuestion" ></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="page-padding">
                                    带来一个希望在党校学习解决的一个思想理论问题
                                </td>
                                <td style="padding: 0;">
                                    <textarea name="" id="solveQuestion" ></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="page-padding">
                                    带来一个推动创新型中心城市建设的建议
                                </td>
                                <td style="padding: 0;">
                                    <textarea name="" id="advice" ></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="page-padding">
                                    其它
                                </td>
                                <td style="padding: 0;">
                                    <textarea name="" id="other" ></textarea>
                                </td>
                            </tr>

                        </table>

                    </div>

                    <p class="page-bottom-all"><span>备注:《调训学员学习需求调查表》</span><i>报名方式:以"班级+姓名"邮件名发至123216423@qq.com</i></p>

                </div>
            <%--</form>--%>
            <p class="stdformbutton">
                <button class="radius2" onclick="addSearch()" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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