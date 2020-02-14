<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>属于班次</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":selectedClassTypeId
                    },
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        if(result.code=="0"){
                            var list=result.data;
                            var classstr="<option value=0>请选择</option>";
                            if(list!=null&&list.length>0){

                                for(var i=0;i<list.length;i++){
                                    classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });

            var classTypeId= "${user.classTypeId}";
            jQuery("#classTypeId option[value='"+classTypeId+"']").attr("selected",true);
            var classId="${user.classId}";
            if(classId!=null&&classId!=0){
                var className="${user.className}";
                jQuery("#classId").html("<option value='"+classId+"'>"+className+"</option>");
            }
            var studentId="${user.studentId}";
            jQuery("#studentId").val(studentId);

            var baodao="${user.baodao}";
            jQuery("#baodao option[value='"+baodao+"']").attr("selected",true);


        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function pass(id){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/passJoinClass.json?id='+id,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#operationtd"+id).html("已通过");
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }

        function deny(id){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/denyJoinClass.json?id='+id,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#operationtd"+id).html("已驳回");
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">属于班次</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示当前登录学员属于的班次.<br />
                    2.可点击"确认参加"来参加该班次,也可点击"取消参加"拒绝参加该班次.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">班型ID</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次ID</th>
                    <th class="head0 center">编号</th>
                    <th class="head1 center">名称</th>
                    <th class="head0 center">讲师</th>
                    <th class="head1 center">已报到人数</th>
                    <th class="head0 center">未报到人数</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${classes.classTypeId}</td>
                    <td>${classes.classType}</td>
                    <td>${classes.id}</td>
                    <td>${classes.classNumber}</td>
                    <td>${classes.name}</td>
                    <td>${classes.teacherName}</td>
                    <td>${classes.studentSignNum}</td>
                    <td>${classes.studentTotalNum-classes.studentSignNum}</td>
                    <td><fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td class="center" id="operationtd${user.id}">
                        <c:if test="${user.status==4}">
                            <a href="javascript:void(0)" onclick="pass(${user.id})" class="stdbtn" title="通过">确认参加</a>
                            <a href="javascript:void(0)" onclick="deny(${user.id})" class="stdbtn" title="驳回">拒绝参加</a>
                        </c:if>
                        <c:if test="${user.status==1}">
                            确认参加
                        </c:if>
                        <c:if test="${user.status==6}">
                            拒绝参加
                        </c:if>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>