<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>通过学员列表</title>
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
                url:'${ctx}/admin/jiaowu/user/passLunXun.json?id='+id,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){

                    jQuery("#operationtd"+id).html("<a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已通过'>已通过</a>");
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
            jConfirm('您确定要驳回该学员的培训资格吗?','确认',function(r) {
                if (r) {
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/user/denyLunXun.json?id='+id,
                        type:'post',
                        dataType:'json',
                        success:function (result){
                            if(result.code=="0"){

                                jQuery("#operationtd"+id).html(" <a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已驳回'>已驳回</a>");
                            }else{
                                jAlert(result.message,'提示',function() {});
                            }
                        },
                        error:function(e){
                            jAlert('更新失败','提示',function() {});
                        }
                    });
                }
            });


        }

        function useExcel(){
            var totalCount="${pagination.totalCount}";
            if(totalCount==""||totalCount=="0"){
                jAlert("暂无数据,不可导出!",'提示',function() {});
                return;
            }
            var classId=jQuery("#classId").val();
            if(classId==null||classId==""||classId=="0"){
                jAlert("请选择班次!",'提示',function() {});
                return;
            }
            <%--jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/exportBeforeSignUserList.json");--%>
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/newUserListExcel.json?classId="+classId);
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user//beforeSignUserList.json");
        }

        function oneButtonPass(userId){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/oneButtonPass.json?userId='+userId,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jQuery("#passspan"+userId).html("<a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已设置为正式学员'>已设置为正式学员</a>");
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }

        function oneButtonPassOfOneClass(){
            var classId=jQuery("#classId").val();
            if(classId==null||classId=="0"){
                jAlert('请选择班次','提示',function() {});
                return false;
            }
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/user/oneButtonPassOfOneClass.json?classId='+classId,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        jAlert(result.message,'提示',function() {window.location.reload()});
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
        <h1 class="pagetitle">通过学员列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示通过学员列表.<br />

                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user//beforeSignUserList.json" method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="classTypeId" class="vam" id="classTypeId">
                                <option value="0">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">班次 &nbsp;</span>
                        <label class="vam">
                            <select name="classId" class="vam" id="classId" >
                                <option value="0">请选择</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
                    <c:if test="${flag==true}">
                    <a href="javascript: void(0)" onclick="oneButtonPassOfOneClass()" class="stdbtn btn_orange">一键通过某班次所有学员</a>
                    </c:if>
                </div>
                <div class="disIb ml20 mb10">
                    <span class="vam">总人数: &nbsp;</span>
                    <label class="vam">
                        ${totalNum}人
                    </label>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

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
                        <th class="head0 center">序号</th>
                        <th class="head0 center">班型</th>
                         <th class="head0 center" width="10%">班次</th>
                        <th class="head0 center">姓名</th>
                        <th class="head0 center">性别</th>
                        <th class="head0 center">政治面貌</th>
                        <th class="head0 center">年龄</th>
                        <th class="head0 center">学历</th>
                        <th class="head0 center">民族</th>
                        <th class="head0 center">单位</th>
                        <th class="head0 center">学员单位及职务职称（全称）</th>
                        <th class="head0 center">联系电话</th>
                        <th class="head1 center">身份证号</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user" varStatus="index">
                        <tr>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${index.index+1}</td>
                                    <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.classTypeName}</td>
                                    <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.className}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.name}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.sex}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                    <c:if test="${user.politicalStatus==0}">中共党员</c:if>
                                    <c:if test="${user.politicalStatus==1}">民主党派</c:if>
                                    <c:if test="${user.politicalStatus==2}">无党派人士</c:if>
                                    <c:if test="${user.politicalStatus==3}">群众</c:if>
                                    <c:if test="${user.politicalStatus==4}">其它</c:if>
                                    <c:if test="${user.politicalStatus==5}">中共预备党员</c:if>
                                    <c:if test="${user.politicalStatus==6}">共青团员</c:if>
                                    <c:if test="${user.politicalStatus==7}">民革党员</c:if>
                                    <c:if test="${user.politicalStatus==8}">民盟盟员</c:if>
                                    <c:if test="${user.politicalStatus==9}">民建会员</c:if>
                                    <c:if test="${user.politicalStatus==10}">民进会员</c:if>
                                    <c:if test="${user.politicalStatus==11}">农工党党员</c:if>
                                    <c:if test="${user.politicalStatus==12}">致公党党员</c:if>
                                    <c:if test="${user.politicalStatus==13}">九三学社社员</c:if>
                                    <c:if test="${user.politicalStatus==14}">台盟盟员</c:if>
                                </td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.age}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.qualification}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.nationality}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.unit}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.job}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.mobile}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.idNumber}</td>
                            <td class="center" id="operationtd${user.id}">
                                <a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}" class="stdbtn btn_orange" title="详细信息">详细信息</a>
                                <span id="passspan${user.id}">
                                <c:if test="${user.status==4}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部已通过">组织部已通过</a>
                                </c:if>
                                <c:if test="${user.status==5}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部已驳回">组织部已驳回</a>
                                </c:if>
                                <c:if test="${user.status==6}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人已驳回">个人已驳回</a>
                                </c:if>
                                <c:if test="${user.status==1}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人已通过">个人已通过</a>
                                </c:if>
                                <c:if test="${user.status!=1}">
                                    <a href="javascript:void(0)" onclick="oneButtonPass(${user.id})" class="stdbtn btn_orange" title="一键通过">一键通过</a>
                                </c:if>
                                </span>
                                <%--<c:if test="${department.departmentId!=81}">--%>
                                <a href="${ctx}/admin/jiaowu//user/toUpdateUser.json?id=${user.id}" class="stdbtn btn_orange" title="修改学员信息">修改学员信息</a>
                                <%--</c:if>--%>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>