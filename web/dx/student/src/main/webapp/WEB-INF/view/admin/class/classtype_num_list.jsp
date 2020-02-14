<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>班型列表</title>
        <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script> 
        <script type="text/javascript">
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">班型人数列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班型的总人数.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/classType/queryClassTypeNum.json" method="post">
                            <div class="tableoptions disIb mb10">
                                <span class="vam">名称 &nbsp;</span>
                                <label class="vam">
                                <input style="width: auto;" name="classType.name" type="text" class="hasDatepicker" value="${classType.name}" placeholder="请输入名称">
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
                                <th class="head0 center">ID</th>
                                <th class="head0 center">名称</th>
                                <th class="head0 center">总人数</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${classTypeList}" var="classType" varStatus="index">
                            <tr>
                                <td>${classType.id}</td>
                                <td>${classType.name}</td>
                                <td>${classType.num}</td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/queryClassPersonNum.json?id=${classType.id}" class="stdbtn btn_orange">各班次的报名人数</a>
                                </td>
                            </tr>
                            </c:forEach>
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