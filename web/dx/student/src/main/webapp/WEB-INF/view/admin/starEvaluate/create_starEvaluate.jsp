<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>星级评价</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        var score=0;
        jQuery(function(){

        });

        function createStarEvaluate(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/starEvaluate/createStarEvaluate.json',
                data:{"score":score},
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

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">星级评价</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于星级评价<br />
    </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post" action="">

                    <table cellpadding="0" cellspacing="0" border="0" class="stdtable mailinbox">
                        <tbody>
                        <tr>
                            <td class="star" width="30%">
                                <a class="msgstar" id="star1" onclick='score=1;jQuery(".msgstar").removeClass("starred");jQuery("#star1").addClass("starred");'></a>
                                <a class="msgstar" id="star2" onclick='score=2;jQuery(".msgstar").removeClass("starred");jQuery("#star1").addClass("starred");jQuery("#star2").addClass("starred");'></a>
                                <a class="msgstar" id="star3" onclick='score=3;jQuery(".msgstar").removeClass("starred");jQuery("#star1").addClass("starred");jQuery("#star2").addClass("starred");jQuery("#star3").addClass("starred");'></a>
                                <a class="msgstar" id="star4" onclick='score=4;jQuery(".msgstar").removeClass("starred");jQuery("#star1").addClass("starred");jQuery("#star2").addClass("starred");jQuery("#star3").addClass("starred");jQuery("#star4").addClass("starred");'></a>
                                <a class="msgstar" id="star5" onclick='score=5;jQuery(".msgstar").removeClass("starred");jQuery("#star1").addClass("starred");jQuery("#star2").addClass("starred");jQuery("#star3").addClass("starred");jQuery("#star4").addClass("starred");jQuery("#star5").addClass("starred");'></a>
                            </td>
                            <td width="70%">

                            </td>
                        </tr>
                        </tbody>
                    </table>

                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="createStarEvaluate()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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