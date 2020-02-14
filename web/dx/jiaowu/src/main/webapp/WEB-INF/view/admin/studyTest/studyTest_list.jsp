<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学习考试成绩列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        function emptyForm(){
            jQuery("#name").val('');
        }
        function deleteStudyTest(id){
            jConfirm('您确定要删除吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/studyTest/delStudyTest.json?id='+id,
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
        <h1 class="pagetitle">学习考试成绩列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示学习考试成绩列表.<br />
					2.可通过名称查询对应的学习考试成绩.<br />
					3.可点击"修改"按钮,修改学习考试成绩.<br />
					4.可点击"删除"按钮,删除学习考试成绩.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/studyTest/studyTestList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="${studyTest.name}" placeholder="请输入名称">
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
                    <th class="head0 center"  rowspan="2">序号</th>
                    <th class="head0 center"  rowspan="2">学员名称</th>
                    <th class="head0 center"  rowspan="2">毕（结）业证号</th>
                    <th class="head0 center" colspan="2" style="text-align: center;">在线学习</th>
                    <th class="head0 center" colspan="2" style="text-align: center;">调研报告</th>
                    <th class="head1 center" colspan="2" style="text-align: center;">毕业考试</th>
                    <th class="head1 center"  rowspan="2">总分</th>
                    <th class="head0 center"  rowspan="2">备注</th>
                    <th class="head1 center"  rowspan="2">操作</th>
                </tr>
                <tr>
                    <th class="head0 center">学时：</th>
                    <th class="head0 center">20%</th>
                    <th class="head0 center">卷面分：</th>
                    <th class="head0 center">30%</th>
                    <th class="head0 center">卷面分：</th>
                    <th class="head0 center">50%</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${studyTestList!=null&&studyTestList.size()>0 }">
                    <c:forEach items="${studyTestList}" var="studyTest" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${studyTest.name}</td>
                            <td>${studyTest.graduateNumber}</td>
                            <td>${studyTest.onlineStudy}</td>
                            <td>${studyTest.onlineStudy*0.2}</td>
                            <td>${studyTest.searchReport}</td>
                            <td>${studyTest.searchReport*0.3}</td>
                            <td>${studyTest.graduateTest}</td>
                            <td>${studyTest.graduateTest*0.5}</td>
                            <%--<td>${studyTest.total}</td>--%>
                            <td>${studyTest.onlineStudy*0.2+studyTest.searchReport*0.3+studyTest.graduateTest*0.5}</td>
                            <td>${studyTest.note}</td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/studyTest/toUpdateStudyTest.json?id=${studyTest.id}" class="stdbtn" title="修改">修改</a>
                                <a href="javascript:void(0)" onclick="deleteStudyTest(${studyTest.id})" class="stdbtn" title="删除">删除</a>
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