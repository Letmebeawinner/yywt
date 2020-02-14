<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>党性锻炼成绩列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        function emptyForm(){
            jQuery("#name").val('');
        }
        function deletePartySpirit(id){
            jConfirm('您确定要删除吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/partySpirit/delPartySpirit.json?id='+id,
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

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">党性锻炼成绩列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示党性锻炼成绩列表.<br />
					2.可通过名称查询对应的党性锻炼成绩.<br />
					3.可点击"修改"按钮,修改党性锻炼成绩.<br />
					4.可点击"删除"按钮,删除党性锻炼成绩.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/partySpirit/partySpiritList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="${partySpirit.name}" placeholder="请输入名称">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
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
                    <th class="head0 center">学员名称</th>
                    <th class="head0 center">个人党性分析材料</th>
                    <th class="head0 center">组织纪律</th>
                    <th class="head1 center">综合表现</th>
                    <th class="head1 center">总分</th>
                    <th class="head0 center">备注</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${partySpiritList!=null&&partySpiritList.size()>0 }">
                    <c:forEach items="${partySpiritList}" var="partySpirit" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${partySpirit.name}</td>
                            <td>${partySpirit.personMaterial}</td>
                            <td>${partySpirit.organisation}</td>
                            <td>${partySpirit.allPerformance}</td>
                            <td>${partySpirit.total}</td>
                            <td>${partySpirit.note}</td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/partySpirit/toUpdatePartySpirit.json?id=${partySpirit.id}" class="stdbtn" title="修改">修改</a>
                                <a href="javascript:void(0)" onclick="deletePartySpirit(${partySpirit.id})" class="stdbtn" title="删除">删除</a>
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