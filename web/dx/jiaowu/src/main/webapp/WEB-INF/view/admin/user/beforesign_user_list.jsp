<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>培训预报名名单</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){
            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
//          		jQuery("#classId").html("");
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
// 			jQuery("#classId option[value='"+classId+"']").attr("selected",true);
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
                url:'${ctx}/admin/jiaowu/user/denyLunXun.json?id='+id,
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

        function useExcel(){
            var totalCount="${pagination.totalCount}";
            if(totalCount==""||totalCount=="0"){
                jAlert("暂无数据,不可导出!",'提示',function() {});
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/exportBeforeSignUserList.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user//beforeSignUserList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">培训预报名名单</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示培训预报名名单.<br />
					2.可点击"通过",该学员会转到培训已报名名单中.也可点击"取消",该学员不会成为正式学员.<br />
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

                    <div class="disIb ml20 mb10">
                        <span class="vam">学号 &nbsp;</span>
                        <label class="vam">
                            <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学号">
                        </label>
                    </div>


                    <!-- <div class="disIb ml20 mb10">
                      <span class="vam">邮箱 &nbsp;</span>
                      <label class="vam">
                      <input id="email" style="width: auto;" name="email" type="text" class="hasDatepicker" value="" placeholder="请输入邮箱">
                      </label>
                  </div>
                   <div class="disIb ml20 mb10">
                      <span class="vam">手机号 &nbsp;</span>
                      <label class="vam">
                      <input id="mobile" style="width: auto;" name="mobile" type="text" class="hasDatepicker" value="" placeholder="请输入手机号">
                      </label>
                  </div> -->
                    <!--  <div class="disIb ml20 mb10">
                         <span class="vam">是否报到 &nbsp;</span>
                         <label class="vam">
                         <select name="baodao" class="vam" id="baodao">
                              <option value="-1">所有</option>
                              <option value="0">未报到</option>
                              <option value="1">已报到</option>
                          </select>
                         </label>
                     </div> -->
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <%--<a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>--%>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">班型ID</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次ID</th>
                    <th class="head0 center">班次</th>
                    <th class="head1 center">学号</th>
                    <th class="head0 center">名称</th>
                    <th class="head1 center">身份证号</th>
                    <th class="head0 center">手机号</th>
                    <th class="head0 center">邮箱</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">年龄</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.classTypeId}</td>
                            <td>${user.classTypeName}</td>
                            <td>${user.classId}</td>
                            <td>${user.className}</td>
                            <td>${user.studentId}</td>
                            <td>${user.name}</td>
                            <td>${user.idNumber}</td>
                            <td>${user.mobile}</td>
                            <td>${user.email}</td>
                            <td>${user.sex}</td>
                            <td>${user.age}</td>
                            <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center" id="operationtd${user.id}">
                                <%--<c:if test="${user.status==3}">
                                    <a href="javascript:void(0)" onclick="pass(${user.id})" class="stdbtn" title="通过">通过</a>
                                    <a href="javascript:void(0)" onclick="deny(${user.id})" class="stdbtn" title="驳回">驳回</a>
                                </c:if>--%>
                                <c:if test="${user.status==4}">
                                    组织部通过
                                </c:if>
                                <c:if test="${user.status==5}">
                                    组织部驳回
                                </c:if>
                                <c:if test="${user.status==6}">
                                    个人驳回
                                </c:if>
                                <c:if test="${user.status==1}">
                                    个人通过
                                </c:if>
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