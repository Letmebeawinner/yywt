<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<c:if test="${pagination!=null && pagination.totalCount>0}">
    <div class="dataTables_info" id="dyntable_info">第 ${pagination.currentPage} 页，共 ${pagination.totalPages} 页，共 ${pagination.totalCount} 个,每页最多显示 ${pagination.pageSize} 条记录</div>
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
               <label class="vam" style="padding: 4px 5px;">
                <input type="text" placeholder="" id="go_to_page_no" class="hasDatepicker" style="width: 40px;">
               </label>
               <span class="last paginate_button" onclick="goNum()" id="dyntable_last">确定</span>
           </span>
        </div>
    </div>
</c:if>
<script type="text/javascript">
    var tableWidth=jQuery('.stdtable').width();
    jQuery(function () {
        jQuery('#dyntable_paginate').width(tableWidth-18);
        jQuery('.overviewhead').width(tableWidth-22);
    })

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