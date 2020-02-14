<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学习调查列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        function deleteSearch(id){
        jConfirm('您确定要删除吗?','确认',function(r){
            if(r){
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/user/delSearch.json?id='+id,
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
        <h1 class="pagetitle">学习调查列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示学习调查列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <%--<div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/searchList.json" method="get">

                </form>

            </div>
        </div>--%>
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
                    <th class="head0 center">学员姓名</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">带来一个干部群众最关心的一个热点问题</th>
                    <th class="head1 center">带来一个希望在党校学习解决的一个思想理论问题</th>
                    <th class="head1 center">带来一个推动创新型中心城市建设的建议</th>
                    <th class="head0 center">其它</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${searchList!=null&&searchList.size()>0 }">
                    <c:forEach items="${searchList}" var="search">
                        <tr>
                            <td>${search.userName}</td>
                            <td>${search.className}</td>
                            <td>${search.unit}</td>
                            <td>${search.hotQuestion}</td>
                            <td>${search.solveQuestion}</td>
                            <td>${search.advice}</td>
                            <td>${search.other}</td>
                            <td class="center">
                                <a href="javascript:void(0)" onclick="deleteSearch(${search.id})" class="stdbtn" title="删除">删除</a>

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