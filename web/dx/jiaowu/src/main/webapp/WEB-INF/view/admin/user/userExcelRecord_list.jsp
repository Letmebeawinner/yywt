<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>导入学员名单列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#createTime',
                format:'YYYY-MM-dd'
            });

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
                            jQuery("#classId").val("${user.classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${user.classTypeId}");
            jQuery("#classTypeId").trigger("change");


        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("select").val(0);
            jQuery("#className").val("");
            jQuery("#createTime").val("");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">导入学员名单列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示导入学员名单列表.<br />
					2.可通过班型、班次和导入时间查询对应的导入学员名单.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userExcelRecordList.json" method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="userExcelRecord.classTypeId" class="vam" id="classTypeId">
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
                            <select name="userExcelRecord.classId" class="vam" id="classId" >
                                <option value="0">请选择</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">班次名称 &nbsp;</span>
                        <label class="vam">
                            <input id="className" style="width: auto;" name="userExcelRecord.className" type="text" class="hasDatepicker" placeholder="请输入班次名称" value="${userExcelRecord.className}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">导入时间 &nbsp;</span>
                        <label class="vam">
                            <input id="createTime" type="text" class="width100 laydate-icon" name="userExcelRecord.createTime" value="<fmt:formatDate value='${userExcelRecord.createTime}' pattern='yyyy-MM-dd'/>"/>
                        </label>
                    </div>


                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <%--<a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>--%>
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
                    <th class="head0 center">班次</th>
                    <th class="head0 center">名称</th>
                    <th class="head0 center">附件地址</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userExcelRecordList!=null&&userExcelRecordList.size()>0 }">
                    <c:forEach items="${userExcelRecordList}" var="userExcelRecord" varStatus="index">
                        <tr>

                            <td>${index.index+1}</td>
                            <td>${userExcelRecord.classTypeName}</td>
                            <td>${userExcelRecord.className}</td>
                            <td>${userExcelRecord.title}</td>
                            <td>${userExcelRecord.url}</td>
                            <td><fmt:formatDate value='${userExcelRecord.createTime}' pattern='yyyy-MM-dd'/></td>
                            <td class="center">
                                <%--<a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}" class="stdbtn" title="详细信息">详细信息</a>--%>
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