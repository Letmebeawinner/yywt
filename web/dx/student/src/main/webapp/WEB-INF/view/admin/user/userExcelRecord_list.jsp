<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学员名单列表</title>
    <script type="text/javascript" src="/jquery.min.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            jQuery(".pobmodal-overlay").hide();

            laydate.skin('molv');
            laydate({
                elem: '#createTime',
                format:'YYYY-MM-DD'
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
                            jQuery("#classId").val("${userExcelRecord.classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    },
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${userExcelRecord.classTypeId}");
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

        function deleteStudent(id) {
            jConfirm('您确定要删除吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/user/userDelete.json?id='+id,
                        type:'post',
                        dataType:'json',
                        success:function (result){
                            if(result.code=="0"){
                                window.location.reload();
                            }else{
                                jAlert(result.message,'提示',function() {});
                            }
                        },
                        error:function(e){
                            jAlert('删除失败','提示',function() {});
                        }
                    });
                }
            });
        }
        function openDocuments(userExcelRecordId) {
            var url = "${ctx}/open/openDocuments.json?userExcelRecordId=" + userExcelRecordId
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";

        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">学员名单列表</h1>
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
                            <input id="createTime" type="text" class="width100 laydate-icon" name="recordCreateTime"  readonly value="${recordCreateTime}" style="height: 30px;width: 150px;"/>
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
                            <td><a href="javascript:void(0)" onclick="openDocuments(${userExcelRecord.id})">打开文档</a></td>
                            <td><fmt:formatDate value='${userExcelRecord.createTime}' pattern='yyyy-MM-dd'/></td>
                            <td class="center">
                                <a href="${userExcelRecord.url}" class="stdbtn" title="导出">导出</a>
                                <a href="javascript:void (0);" class="stdbtn" onclick="deleteStudent(${userExcelRecord.id})" title="删除">删除</a>
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
    </div>
</body>

</html>