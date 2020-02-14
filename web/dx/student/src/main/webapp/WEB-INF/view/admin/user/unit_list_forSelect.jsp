<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>选择单位</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:radio").attr("checked",false);
            jQuery("input:text").val("");
        }


        var myArrayMoveStock = new Array();
        function tijiao(){
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择单位','提示',function() {});
                return;
            }
            qstChecked.each(function() {
                var unitIdAndName=jQuery(this).val();
                myArrayMoveStock.push(unitIdAndName.split("-")[0]);
                myArrayMoveStock.push(unitIdAndName.split("-")[1]);
            });
            opener.addUnit(myArrayMoveStock);
            window.close();
        }
    </script>
</head>
<body>
<div class="tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">单位列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/user/unitListForSelect.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="unit.name" type="text" class="hasDatepicker" value="${unit.name}" placeholder="请输入单位名称">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10">确定</a>
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
                    <th class="head0 center">单位名称</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${unitList!=null&&unitList.size()>0 }">
                    <c:forEach items="${unitList}" var="unit">
                        <tr>
                            <td style="width: 50%"><input type="radio" value="${unit.id}-${unit.name}" class="questionIds" name="teacherId"/>${unit.id }</td>
                            <td style="width: 50%">${unit.name}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <c:if test="${pagination!=null && pagination.totalCount>0}">
            <div class="dataTables_info" id="dyntable_info">第 ${pagination.currentPage} 页，共 ${pagination.totalPages} 页，共${pagination.totalCount}个，每页最多显示 ${pagination.pageSize} 条记录</div>
            <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
                <c:choose>
                    <c:when test="${pagination.currentPage<=1}">
                        <span class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</span>
                        <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</span>
                    </c:when>
                    <c:when test="${pagination.currentPage>1}">
                        <span class="first paginate_button paginate_button_disabled" id="dyntable_first" onclick="goPage(1)">首页</span>
                        <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"  onclick="goPage(${pagination.currentPage-1})">上一页</span>
                    </c:when>
                </c:choose>

            <span>
                <c:forEach begin="${pagination.begin}" end="${pagination.end}" var="pageNo">
                    <c:if test="${pagination.currentPage==pageNo}">
                        <span class="paginate_active">${pageNo}</span>
                    </c:if>
                    <c:if test="${pagination.currentPage!=pageNo}">
                        <span class="paginate_button" onclick="goPage(${pageNo})">${pageNo}</span>
                    </c:if>
                </c:forEach>
            </span>
                <c:choose>
                    <c:when test="${pagination.currentPage>0 && pagination.currentPage < pagination.totalPages}">
                        <span class="paginate_button" id="dyntable_next" onclick="goPage(${pagination.currentPage+1})">下一页</span>
                        <span class="last paginate_button" id="dyntable_last" onclick="goPage(${pagination.totalPages})">尾页</span>
                    </c:when>
                    <c:otherwise>
                        <span class="paginate_button" id="dyntable_next">下一页</span>
                        <span class="last paginate_button" id="dyntable_last">尾页</span>
                    </c:otherwise>
                </c:choose>

            <span class="disIb ml20 left_box">
               <%--<label class="vam" style="padding: 4px 5px;">
                <input type="text" placeholder="" id="go_to_page_no" class="hasDatepicker" style="width: 40px;">
               </label>--%>
               <%--<span class="last paginate_button" onclick="goNum()" id="dyntable_last">确定</span>--%>
                <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10 btn_orange">确定</a>
           </span>
            </div>
        </div>
        </c:if>
        <script type="text/javascript">
            var currentPage = parseInt('${pagination.currentPage}');
            var totalPageSize = parseInt('${pagination.totalPages}');
            var currentUrl='${pagination.currentUrl}';
            function goPage(pageNo){
                if(pageNo>totalPageSize){
                    pageNo = totalPageSize;
                }
                if(pageNo<=0){
                    pageNo = 1;
                }
                if(currentUrl.indexOf('pagination.currentPage=')!=-1){
                    currentUrl = currentUrl.replace(/pagination.currentPage=[0-9]*/g,"pagination.currentPage="+pageNo);
                }else{
                    currentUrl = currentUrl+"pagination.currentPage="+pageNo;
                }
                window.location.href=currentUrl;
            }

            function goNum(){
                var _pageNo = document.getElementById("go_to_page_no").value;
                var reg = /^[0-9]+$/;
                if(!reg.test(_pageNo)){
                    document.getElementById("go_to_page_no").value='';
                    return;
                }
                _pageNo = parseInt(_pageNo);
                if(_pageNo<=0){
                    _pageNo=1;
                }
                if(_pageNo>totalPageSize){
                    _pageNo = totalPageSize;
                }
                goPage(_pageNo);
            }
        </script>
            <%--<jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>--%>
            <!--<a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10">确定</a>-->
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>