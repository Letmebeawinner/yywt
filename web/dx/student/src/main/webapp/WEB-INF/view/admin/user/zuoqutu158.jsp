<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>二楼会议室座区图</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>

    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        <%--var nameArray="${names}".split(",");--%>
        var nameArray="${names}".split(",");
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
            /*alert(nameArray.toString());
             return false;*/

            jQuery("#classTypeIdExcel").val(jQuery("#classType").val());
            jQuery("#names").val(nameArray.toString());
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
                /* alert(nameArray[firstsort]);
                 alert(nameArray[secondsort]);*/
                nameArray[firstsort]=secondchooseusername;
                nameArray[secondsort]=firstchooseusername;
                /*alert(nameArray[firstsort]);
                 alert(nameArray[secondsort]);*/

                jQuery("#"+firstchooseuserid).attr("style","border:2px solid red;");
                jQuery("#"+secondchooseuserid).attr("style","border:2px solid red;");

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
    <div class="pageheader  pageStyle">
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

        </p>
        <h1 class="page-list">
            ${classes.name}
        </h1>
        <div class="testtle-tables">
            <p class="mt20">主席台</p>
            <table border="1" style="width:736px;">
                <tbody>
                <tr>
                    <c:forEach items="${all.get(0)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" colspan="2" style="border-bottom:none;" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(1)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" colspan="2" style="border-bottom:none;" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(2)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" colspan="2" style="border-bottom:none;" class="vertical"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(3)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第一排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(4)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(5)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(6)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(7)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第二排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(8)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(9)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(10)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(11)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第三排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(12)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(13)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(14)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(15)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第四排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(16)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="5" colspan="2" style="border-bottom:1px dotted #ddd;border-top:none;" class="vertical"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(17)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="5" colspan="2" style="border-bottom:1px dotted #ddd;border-top:none;" class="vertical"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(18)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="5" colspan="2" style="border-bottom:1px dotted #ddd;border-top:none;" class="vertical"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(19)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第五排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(20)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(21)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(22)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(23)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第六排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(24)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(25)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(26)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(27)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第七排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(28)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(29)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(30)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(31)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第八排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(32)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(33)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(34)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(35)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td>第九排</td>
                </tr>
                <tr>
                    <c:forEach items="${all.get(36)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td colspan="2" style="border-top:1px dotted #ddd;"></td>
                    <c:forEach items="${all.get(37)}" var="user">
                    <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td colspan="2" style="border-top:1px dotted #ddd;"></td>
                    <c:forEach items="${all.get(38)}" var="user">
                    <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                </c:forEach>
                    <td colspan="2" style="border-top:1px dotted #ddd;"></td>
                    <td colspan="2" ></td>
                    <td>第十排</td>
                </tr>
                </tbody></table>
            <p class="stdformbutton">
                <button class="radius2" onclick="zuoqutuExcel()" id="submitButton" style="margin-top:20px;background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;">
                    导出
                </button>
            </p>
        </div>

    </div>

</div>
</body>
</html>
