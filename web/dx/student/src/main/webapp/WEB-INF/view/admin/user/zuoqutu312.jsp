<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新大楼报告厅</title>
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
        <%--<h1 class="page-list">
            2017年市委党校秋季主体班开学典礼座区图
        </h1>--%>
        <div class="testtle-tables">
            <p class="border-all-wrap">主席台</p>
            <table border="1" style="width:900px;">
                <tbody>
                <tr>
                    <th></th>
                    <th>1</th>
                    <th>2</th>
                    <th>3</th>
                    <th>4</th>
                    <th>5</th>
                    <th></th>
                    <th>6</th>
                    <th>7</th>
                    <th>8</th>
                    <th>9</th>
                    <th>10</th>
                    <th>11</th>
                    <th>12</th>
                    <th>13</th>
                    <th></th>
                    <th>14</th>
                    <th>15</th>
                    <th>16</th>
                    <th>17</th>
                    <th>18</th>
                    <th>19</th>
                    <th>20</th>
                    <th>21</th>
                    <th></th>
                    <th>22</th>
                    <th>23</th>
                    <th>24</th>
                    <th>25</th>
                    <th>26</th>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>1排</tt></td>
                    <c:forEach items="${all.get(0)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(1)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(2)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(3)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>1排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>2排</tt></td>
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
                    <td class="vertical-list"><tt>2排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>3排</tt></td>
                    <c:forEach items="${all.get(8)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(9)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(10)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="2" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(11)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>3排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>4排</tt></td>
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
                    <td class="vertical-list"><tt>4排</tt></td>
                </tr>
                <tr>
                <td  colspan="15" class="vertical border-none"><tt>过</tt></td>
                <td  colspan="14" class="vertical border-none"><tt>道</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>5排</tt></td>
                    <c:forEach items="${all.get(16)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(17)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(18)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>过</tt>
                    </td>
                    <c:forEach items="${all.get(19)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>5排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>6排</tt></td>
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
                    <td class="vertical-list"><tt>6排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>7排</tt></td>
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
                    <td class="vertical-list"><tt>7排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>8排</tt></td>
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
                    <td class="vertical-list"><tt>8排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>9排</tt></td>
                    <c:forEach items="${all.get(32)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(33)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(34)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td rowspan="4" class="vertical border-none"><tt>道</tt>
                    </td>
                    <c:forEach items="${all.get(35)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>9排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>10排</tt></td>
                    <c:forEach items="${all.get(36)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(37)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(38)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(39)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>10排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>11排</tt></td>
                    <c:forEach items="${all.get(40)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(41)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(42)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(43)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>11排</tt></td>
                </tr>
                <tr>
                    <td class="vertical-list"><tt>12排</tt></td>
                    <c:forEach items="${all.get(44)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(45)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(46)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <c:forEach items="${all.get(47)}" var="user">
                        <td  id="user${user.id}" onclick="choose('user${user.id}','${user.name}',${user.sort})" style="">${user.name}</td>
                    </c:forEach>
                    <td class="vertical-list"><tt>12排</tt></td>
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
