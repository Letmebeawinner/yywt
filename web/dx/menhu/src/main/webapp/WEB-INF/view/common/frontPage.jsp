<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<c:if test="${pagination!=null && pagination.totalCount>0}">
    <div class="dataTables_info" id="dyntable_info"></div>
        <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
            <c:choose>
                <c:when test="${pagination.currentPage<=1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first"> &nbsp;&nbsp;&nbsp;首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"> &nbsp;&nbsp;&nbsp;上一页</span>
                </c:when>
                <c:when test="${pagination.currentPage>1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first" onclick="goPage(1)"> &nbsp;&nbsp;&nbsp;首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"  onclick="goPage(${pagination.currentPage-1})"> &nbsp;&nbsp;&nbsp;上一页</span>
                </c:when>
            </c:choose>

            <span>
                <c:forEach begin="${pagination.begin}" end="${pagination.end}" var="pageNo">
                    &nbsp;&nbsp;&nbsp;
                    <c:if test="${pagination.currentPage==pageNo}">
                        <span style="color: red">${pageNo}</span>
                    </c:if>
                    <c:if test="${pagination.currentPage!=pageNo}">
                        <span onclick="goPage(${pageNo})">${pageNo}</span>
                    </c:if>
                </c:forEach>
                &nbsp;&nbsp;&nbsp;
            </span>
            <c:choose>
                <c:when test="${pagination.currentPage >= pagination.totalPages}">
                    <span class="next paginate_button" id="dyntable_next"> &nbsp;&nbsp;&nbsp;下一页</span>
                    <span class="last paginate_button" id="dyntable_last"> &nbsp;&nbsp;&nbsp;尾页</span>
                </c:when>
                <c:when test="${pagination.currentPage < pagination.totalPages}">
                    <span class="next paginate_button" id="dyntable_next" onclick="goPage(${pagination.currentPage+1})"> &nbsp;&nbsp;&nbsp;下一页</span>
                    <span class="last paginate_button" id="dyntable_last" onclick="goPage(${pagination.totalPages})"> &nbsp;&nbsp;&nbsp;尾页</span>
                </c:when>
            </c:choose>
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


</script>