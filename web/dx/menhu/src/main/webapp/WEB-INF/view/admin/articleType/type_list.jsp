<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>资讯类型列表</title>
        <script type="text/javascript">
            function clearVal(){
                jQuery(".hasDatepicker").val("");
            }
            function submit(){
                jQuery("#from").submit();
            }
            function delSubmit(id){
              if(confirm("确定要删除吗？")){
                  jQuery.ajax({
                      type: "POST",
                      dataType:"json",
                      url:ctx+"/admin/menhu/articleType/delArticleType.json",
                      data:{"id":id},
                      cache: false,
                      async: false,
                      error: function(request) {
                          alert("网络异常，请稍后再试");
                      },
                      success: function(result) {
                          window.location.reload();
                      }
                  });
              }
            }
        </script>
	</head>
	<body>
    <div class="centercontent tables">
        <div class="pageheader notab" style="margin-left: 30px">
            <h1 class="pagetitle">资讯类型列表</h1>
        </div>
        <div id="contentwrapper" class="contentwrapper">

            <!-- 搜索条件，开始 -->
            <div class="overviewhead clearfix mb10">
                <div class="fl mt5">
                    <form class="disIb" id="from" method="post" action="${ctx}/admin/menhu/articleType/queryArticleType.json">
                        <div class="tableoptions disIb mb10">
                            <span class="vam">类型名称： &nbsp;</span>
                            <label class="vam" for="name"></label>
                            <input id="name" style="width: auto;" type="text" class="hasDatepicker" name="articleType.name"  value="${articleType.name}"placeholder="类型名称" >
                        </div>
                    </form>
                    <div class="disIb ml20 mb10">
                        <a href="javascript: submit()" class="stdbtn btn_orange">搜 索</a>
                        <a href="javascript: clearVal()" class="stdbtn ml10">重 置</a>
                    </div>
                </div>
            </div>
            <div class="pr">
                <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                    <thead>
                    <tr>
                        <th class="head0 center">类型编号</th>
                        <th class="head0 center">类型名</th>
                        <th class="head0 center">状态</th>
                        <th class="head1 center">
                            操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${typeList}" var="typeList">
                        <tr>
                            <td>${typeList.id}</td>
                            <td>${typeList.name}</td>
                            <td>
                                <c:if test="${typeList.status==0}">正常</c:if>
                                <c:if test="${typeList.status==1}">锁定</c:if>
                            </td>

                            <td class="center">
                                <a class="stdbtn" title="编辑" href="${ctx}/admin/menhu/articleType/toUpdateArticleType.json?id=${typeList.id}"><span>编辑</span></a>
                                <a href="javascript:delSubmit(${typeList.id})" class="stdbtn" title="删除">删除</a>
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
    </div>

    </body>
</html>