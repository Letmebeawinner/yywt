<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <title>${activity.title}</title>
    <link rel="stylesheet" href="${ctx}/static/fonts/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/fonts/css/font-awesome-ie7.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.default.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.contrast.css" type="text/css">
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <style type="text/css">
        /* 			.noter-zan{background: url(${ctx}/static/images/icon-3.png) no-repeat} */
        /* 			.noter-zan em {background-position: -50px -117px;} */
    </style>
    <%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
    <script type="text/javascript">

        jQuery(function(){

        });
        function replyToActivity(){
            var activityId="${activity.id}";
            var replyContent=jQuery("#replyText").val();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/activityreply/addReplyToActivity.json',
                data:{"activityId":activityId,"content":replyContent},
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#replyText").val("");
// 	    				alert("回复成功!");
// 						window.location.reload();
                        var reply=result.data;
                        var createTime=formatDate(new Date(reply.createTime.replace(/-/g,"/")), "yyyy-MM-dd hh:mm");
                        var str='<li><div class="updatecontent"><div class="top"><font color="green">'+reply.userName+'</font><span style="margin-left: 50px;">'+createTime+'</span></div><div class="text">'+reply.content+'</div><div class="viewinfo"><a onclick="addPraise('+reply.id+')" href="javascript:void(0)"><span class="numviews" id="praiseSpan'+reply.id+'"> <img src="${ctx}/static/images/zan.jpg" width="20px;" height="20px;"/>'+reply.praiseNum+'</span></a><a id="'+reply.id+'" onclick="openOrCloseReplyDiv('+reply.id+')" href="javascript:void(0)"><span id="replyNumSpan'+reply.id+'" class="numcomments" style="margin-left: 30px;"> <img src="${ctx}/static/images/huifu.jpg" width="20px;" height="20px;"/>'+reply.replyNum+'</span></a></div></div><div id="replyDiv'+reply.id+'" style="display: none;"><div class="statusbox" style="width: 40%;margin-left: 80px;"><div class="status_content"><textarea name="" cols="" rows="3" id="replyText'+reply.id+'" placeholder="请输入您要回复的文字"></textarea></div><div class="submit"><button class="stdbtn btn_yellow" onclick="replyToReply('+reply.id+')">回复</button></div></div><ul class="updatelist" id="childReplyUl'+reply.id+'" style="width: 70%;margin-left: 100px;"></ul></div></li>';
                        jQuery("#replyListUl").append(str);
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                } ,
                error:function(e){
                    jAlert('回复失败,请稍后再试!','提示',function() {});
                }
            });
        }


        var praiseArray=new Array();
        function addPraise(replyId){
            for(var i=0;i<praiseArray.length;i++){
                if(praiseArray[i]==replyId){
                    jAlert('您已赞过','提示',function() {});
                    return;
                }
            }
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/activityreply/addPraise.json',
                data:{"replyId":replyId},
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#praiseSpan"+replyId).html('<img src="${ctx}/static/images/zan.jpg" width="20px;" height="20px;"/>'+result.data);
                        praiseArray.push(replyId);
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
// 	    			alert("点赞失败,请稍后再试!");
                    jAlert('点赞失败,请稍后再试!','提示',function() {});
                }
            });

        }

        function openOrCloseReplyDiv(replyId){
            var replyDiv=jQuery("#replyDiv"+replyId);
            if (replyDiv.is(":hidden")) {
                getChildReply(replyId);
                replyDiv.slideDown(200);
            }else{
                replyDiv.slideUp(200);
            }
        }

        function getChildReply(replyId){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/activityreply/getChildReply.json',
                data:{"replyId":replyId},
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        var childReplyList=result.data;
                        if(childReplyList!=null&&childReplyList.length>0){
                            var str="";
                            for(var i=0;i<childReplyList.length;i++){
                                var createTime=formatDate(new Date(childReplyList[i].createTime.replace(/-/g,"/")), "yyyy-MM-dd hh:mm");
                                str+='<li><div class="updatecontent"><div class="top"><font color="green">'+childReplyList[i].userName+'</font><span style="margin-left: 50px;">'+createTime+'</span></div><div class="text">'+childReplyList[i].content+'</div><div class="viewinfo"><a onclick="addPraise('+childReplyList[i].id+')" href="javascript:void(0)"><span class="numviews" id="praiseSpan'+childReplyList[i].id+'"> <img src="${ctx}/static/images/zan.jpg" width="20px;" height="20px;"/>'+childReplyList[i].praiseNum+'</span></a></div></div></li>';
                            }
                            console.info(str);
                            jQuery("#childReplyUl"+replyId).html(str);
                        }
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('点赞失败,请稍后再试!','提示',function() {});
                }
            });
        }

        function replyToReply(replyId){
            var replyContent=jQuery("#replyText"+replyId).val();
            var activityId="${activity.id}";
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/activityreply/addChildReply.json',
                data:{"activityId":activityId,"replyId":replyId,"content":replyContent},
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#replyText"+replyId).val("");
                        var childReply=result.data;
                        var createTime=formatDate(new Date(childReply.createTime.replace(/-/g,"/")), "yyyy-MM-dd hh:mm");
                        var str='<li><div class="updatecontent"><div class="top"><font color="green">'+childReply.userName+'</font><span style="margin-left: 50px;">'+createTime+'</span></div><div class="text">'+childReply.content+'</div><div class="viewinfo"><a onclick="addPraise('+childReply.id+')" href="javascript:void(0)"><span class="numviews" id="praiseSpan'+childReply.id+'"> <img src="${ctx}/static/images/zan.jpg" width="20px;" height="20px;"/>'+childReply.praiseNum+'</span></a></div></div></li>';
                        jQuery("#childReplyUl"+replyId).append(str);
                        jQuery("#replyNumSpan"+replyId).html('<img src="${ctx}/static/images/huifu.jpg" width="20px;" height="20px;"/>'+childReply.replyNum);
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                } ,
                error:function(e){
                    jAlert('回复失败,请稍后再试!','提示',function() {});
                }
            });
        }

        //格式化时间
        function formatDate(date,format){
            var o = {
                "M+" : date.getMonth()+1, //month
                "d+" : date.getDate(), //day
                "h+" : date.getHours(), //hour
                "m+" : date.getMinutes(), //minute
                "s+" : date.getSeconds(), //second
                "q+" : Math.floor((date.getMonth()+3)/3), //quarter
                "S" : date.getMilliseconds() //millisecond
            }

            if(/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            }

            for(var k in o) {
                if(new RegExp("("+ k +")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
                }
            }
            return format;
        }
    </script>


    </head>
    <body>
    <div class="centercontent tables">
            <div class="pageheader notab" style="margin-left: 30px">
            <span>
            <span style="color:red">说明</span><br>
            1.本页面展示一个活动和所有用户评论的内容.<br>
    2.用户登录后,可在下方的输入框对活动进行评论.<br>
    3.用户登录后,可点击某评论下方的点赞图片,对评论点赞,只可点赞一次.<br>
    4.用户登录后,可点击某评论下方的评论图片,对某评论回复,可回复多次.<br>
    </span>
    <h1 class="pagetitle">${activity.title}</h1>
            <!--                 <h6 ></h6> -->
            <div class="text" style="margin-left: 30px;">
            ${activity.content }
            </div>
            </div><!--pageheader-->
            <div id="contentwrapper" class="contentwrapper">
            <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->

            <ul class="updatelist" id="replyListUl">
            <c:if test="${not empty activityReplyList}">
            <c:forEach items="${activityReplyList}" var="reply">
            <li>
            <div class="updatecontent">
            <div class="top">
            <font color="green">${reply.userName}</font><span style="margin-left: 50px;"><fmt:formatDate type="both" value="${reply.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
            </div><!--top-->
            <div class="text">
            ${reply.content}
            </div>
            <div class="viewinfo">
            <a onclick="addPraise(${reply.id})" href="javascript:void(0)"><span class="numviews noter-zan" id="praiseSpan${reply.id}"><img src="${ctx}/static/images/zan.jpg" width="20px;" height="20px;"/>${reply.praiseNum }</span></a>
            <a onclick="openOrCloseReplyDiv(${reply.id})" href="javascript:void(0)"><span class="numcomments" style="margin-left: 30px;" id="replyNumSpan${reply.id}"><img src="${ctx}/static/images/huifu.jpg" width="20px;" height="20px;"/> ${reply.replyNum }</span></a>
            </div>
            </div>
            <div id="replyDiv${reply.id}" style="display: none;">
            <div class="statusbox" style="width: 40%;margin-left: 80px;">
            <div class="status_content">
            <textarea name="" cols="" rows="3" id="replyText${reply.id}" placeholder="请输入您要回复的文字"></textarea>
            </div>
            <div class="submit"><button class="stdbtn btn_yellow" onclick="replyToReply(${reply.id})">回复</button></div>
            </div>
            <ul class="updatelist" id="childReplyUl${reply.id}" style="width: 70%;margin-left: 100px;">

            </ul>

            </div>
            </li>
            </c:forEach>
            </c:if>



            </ul>
            <div class="statusbox">
            <div class="status_content">
            <textarea name="" cols="" rows="5" id="replyText" placeholder="请输入您要回复的文字"></textarea>
            </div>
            <div class="submit"><button class="stdbtn btn_yellow" onclick="replyToActivity()">回复</button></div>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
            </div><!-- #updates -->
            </div>
            </div>
            </body>
            </html>