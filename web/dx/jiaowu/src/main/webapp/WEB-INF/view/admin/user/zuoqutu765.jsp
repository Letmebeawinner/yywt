<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>

    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        <%--var nameArray="${names}".split(",");--%>
        $(function(){
            jQuery("#classType").val("${classTypeId}");
            jQuery("#classes").val("${classId}");
            jQuery("#classroom").val("${classroomId}");
            jQuery("#classType").change(function(){
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":jQuery(this).val()},
                    type:'post',
                    dataType:'json',
                    success:function (result) {
                        var classhtml="";
                        var classList=result.data;
                        if(classList!=null&&classList.length>0) {
                            for (var i = 0; i<classList.length; i++) {
                                classhtml += "<option value='" + classList[i].id + "'>" + classList[i].name + "</option>";
                                jQuery("#classes").html(classhtml);
                            }
                        }else{
                            jQuery("#classes").html("");
                        }
                    }

                });
            });
//            alert(nameArray);
        });

        function switchClasses(){
            if((jQuery("#classType").val()==null)||(jQuery("#classType").val()=="")||(jQuery("#classType").val()=="0")){
                jAlert("请选择班型",'提示',function() {});
                return false;
            }
            if((jQuery("#classes").val()==null)||(jQuery("#classes").val()=="")||(jQuery("#classes").val()=="0")){
                jAlert("请选择班次",'提示',function() {});
                return false;
            }
            window.location.href="${ctx}/admin/jiaowu/user/zuoqutu.json?classTypeId="+jQuery("#classType").val()+"&classId="+jQuery("#classes").val()+"&classroomId="+jQuery("#classroom").val();
        }

        function zuoqutuExcel(){
            /*jQuery.ajax({
             url:'${ctx}/admin/jiaowu/user/zuoqutuExcel.json',
             data:{"names":nameArray.toString()},
             type:'post',
             dataType:'json',
             success:function (result) {

             }

             });*/
            jQuery("#classTypeIdExcel").val(jQuery("#classType").val());
//            jQuery("#names").val(nameArray.toString());
            jQuery("#searchform").submit();
        }

        var firstchooseuserid="";
        var firstchooseusername="";
        var firstsort=-1;
        var secondchooseuserid="";
        var secondchooseusername="";
        var secondsort=-1;
        var tmpid="user100000";
        function choose(userid,username,sort){
            if(firstchooseuserid==""){
                firstchooseuserid=userid;
                firstchooseusername=username;
                firstsort=sort;
            }else{
                secondchooseuserid=userid;
                secondchooseusername=username;
                secondsort=sort;
                /*nameArray[firstsort]=secondchooseusername;
                nameArray[secondsort]=firstchooseusername;*/

                jQuery("#"+firstchooseuserid).html(secondchooseusername);
                jQuery("#"+firstchooseuserid).attr("onclick","choose('"+secondchooseuserid+"','"+secondchooseusername+"',"+firstsort+")");

                jQuery("#"+firstchooseuserid).attr("id",tmpid);
                jQuery("#"+secondchooseuserid).html(firstchooseusername);
                jQuery("#"+secondchooseuserid).attr("onclick","choose('"+firstchooseuserid+"','"+firstchooseusername+"',"+secondsort+")");
                jQuery("#"+secondchooseuserid).attr("id",firstchooseuserid);
                jQuery("#"+tmpid).attr("id",secondchooseuserid);
                firstchooseuserid="";
                firstchooseusername="";
                firstsort=-1;
                secondchooseuserid="";
                secondchooseusername="";
                secondsort=-1;
            }
        }
    </script>
</head>
<body>
<div class="centercontent testtest" style="margin-left:181px">
    <div class="pageheader notab pageStyle">
        <form action="${ctx}/admin/jiaowu/user/zuoqutuExcel.json" id="searchform" method="post">
            <input type="hidden" name="names" id="names" value="${names}"/>
            <input type="hidden" name="classTypeId" id="classTypeIdExcel"/>
            <input type="hidden" name="classId" id="classIdExcel"/>
            <input type="hidden" name="classroomId" value="${classroomId}" id="classroomId"/>
            <input type="hidden" name="className" value="${classes.name}" id="className"/>
        </form>
        <p class="select-color">
            <select id="classType">
                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                    <option value="">请选择</option>
                    <c:forEach items="${classTypeList}" var="classType">
                        <option value="${classType.id}">${classType.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="classes">
                <c:if test="${classesList!=null&&classesList.size()>0}">
                    <option value="">请选择</option>
                    <c:forEach items="${classesList}" var="classes">
                        <option value="${classes.id}">${classes.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="classroom">
                <c:if test="${classroomList!=null&&classroomList.size()>0}">
                    <option value="">请选择</option>
                    <c:forEach items="${classroomList}" var="classroom">
                        <option value="${classroom.id}">${classroom.position}</option>
                    </c:forEach>
                </c:if>
            </select>
            <a href="javascript:;" class="stdbtn btn_orange "  onclick="switchClasses()">确定</a>

            <%--<button value="确定">确定</button>--%>
        </p>
        <h1 class="page-list">
            ${classes.name}
        </h1>
        <div class="testtle-tables">
            <ul class="oul-list clearfix">
                <li class="margin-all"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <p>主席台</p>
            <table border="1">
                <tbody>
                <tr>
                    <td class="vertical-list"><tt>1排</tt></td>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <c:forEach items="${all.get(0)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="10" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(1)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="10" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(2)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <td class="vertical-list"><tt>1排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>2排</tt></td>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <c:forEach items="${all.get(3)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(4)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(5)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <td class="vertical-list"><tt>2排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>3排</tt></td>
                    <td class="border-none"></td>
                    <c:forEach items="${all.get(6)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(7)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(8)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="border-none"></td>
                    <td class="vertical-list"><tt>3排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>4排</tt></td>
                    <c:forEach items="${all.get(9)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(10)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(11)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <td class="vertical-list"><tt>4排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>5排</tt></td>
                    <c:forEach items="${all.get(12)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(13)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(14)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>5排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>6排</tt></td>
                    <c:forEach items="${all.get(15)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(16)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(17)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>7排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>7排</tt></td>
                    <c:forEach items="${all.get(18)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(19)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(20)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>6排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>8排</tt></td>
                    <c:forEach items="${all.get(21)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(22)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(23)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>8排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>9排</tt></td>
                    <c:forEach items="${all.get(24)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(25)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>

                    <c:forEach items="${all.get(26)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td colspan="8" class="vertical">过道</td>
                    <td colspan="17" class="vertical">过道</td>
                    <td colspan="7" class="vertical">过道</td>
                    <td class="vertical-list"><tt></tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>10排</tt></td>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <c:forEach items="${all.get(27)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="7" class="vertical"><tt>道</tt></td>
                    <c:forEach items="${all.get(28)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="7" class="vertical"><tt>道</tt></td>

                    <c:forEach items="${all.get(29)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="border-none"></td>
                    <td class="border-none"></td>
                    <td class="vertical-list"><tt>10排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>11排</tt></td>
                    <c:forEach items="${all.get(30)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(31)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(32)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>11排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>12排</tt></td>
                    <c:forEach items="${all.get(33)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(34)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(35)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>12排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>13排</tt></td>
                    <c:forEach items="${all.get(36)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(37)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(38)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>13排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>14排</tt></td>
                    <c:forEach items="${all.get(39)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(40)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(41)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>14排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>15排</tt></td>
                    <c:forEach items="${all.get(42)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(43)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(44)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>15排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>16排</tt></td>
                    <c:forEach items="${all.get(45)}" var="user">
                        <td id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(46)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(47)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>16排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td colspan="2" class="vertical">二楼</td>
                    <td colspan="29" class="vertical">二楼看台</td>
                    <td colspan="2" class="vertical">二楼</td>
                    <td class="vertical-list"><tt></tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>1排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(48)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(49)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2"></td>
                    <c:forEach items="${all.get(50)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical"><tt>过</tt></td>
                    <c:forEach items="${all.get(51)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>1排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>2排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(52)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(53)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(54)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(55)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>2排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <td colspan="29" class="vertical">过道</td>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>3排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(56)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(57)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4"></td>
                    <c:forEach items="${all.get(58)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical"><tt>道</tt></td>
                    <c:forEach items="${all.get(59)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>3排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>4排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(60)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(61)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(62)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(63)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>4排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>5排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(64)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(65)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(66)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(67)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>5排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>6排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(68)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(69)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(70)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(71)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>6排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <td colspan="29" class="vertical">过道</td>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt></tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>7排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(72)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical"><tt></tt>
                    </td>
                    <c:forEach items="${all.get(73)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4"></td>
                    <c:forEach items="${all.get(74)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical"><tt></tt></td>
                    <c:forEach items="${all.get(75)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>7排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>8排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(76)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(77)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(78)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(79)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>8排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>9排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(80)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(81)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(82)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(83)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>9排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>10排</tt></td>
                    <td class="vertical-list"><tt></tt></td>
                    <c:forEach items="${all.get(84)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(85)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(86)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(87)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt></tt></td>
                    <td class="vertical-list"><tt>10排</tt></td>
                </tr>
                </tbody>
            </table>
            <p class="stdformbutton">
                <button class="radius2" onclick="zuoqutuExcel()" id="submitButton"
                        style="margin-top:20px;background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;">
                    导出
                </button>
            </p>
        </div>

    </div>

</div>
</body>
</html>
