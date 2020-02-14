<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>科研信息</title>
    <script type="text/javascript">
        jQuery(function () {
            if(jQuery("#resultForm").val()==1){
                jQuery("#resultForm1").show();
                jQuery("#resultForm2").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("论文名称");
            }else if(jQuery("#resultForm").val()==2){
                jQuery("#resultForm2").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("著作名称");
            }else{
                jQuery("#resultForm3").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm2").hide();
                jQuery("#resultName").html("课题名称");
            }
        });
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getResultList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab"  style="margin-left: 10px">
        <h1 class="pagetitle">科研信息</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示教职工的科研信息
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/rs/selectEmployeeResults.json" method="post">
                    <input type="hidden" name="queryResult.employeeId" value="${!empty queryResult.employeeId?queryResult.employeeId:queryResult.sysUserId}">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入ID" name="queryResult.id" value="${queryResult.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">论文名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果名称" name="queryResult.name" value="${queryResult.name}">
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">成果形式&nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.resultForm" style="color: red" id="resultForm" onchange="searchForm()">
                                <option value="1" <c:if test="${queryResult.resultForm==1}"> selected</c:if>>论文</option>
                                <option value="2" <c:if test="${queryResult.resultForm==2}"> selected</c:if>>著作</option>
                                <option value="3" <c:if test="${queryResult.resultForm==3}"> selected</c:if>>课题</option>
                            </select>
                        </label>
                    </div>
                    &nbsp;
                    <div class="tableoptions disIb mb10">
                        <span class="vam">入库状态  &nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.intoStorage" class="vam">
                                <option value="">请选择</option>
                                <option value="1" <c:if test="${queryResult.intoStorage=='1'}"> selected </c:if>>未入库</option>
                                <option value="2" <c:if test="${queryResult.intoStorage=='2'}"> selected </c:if>>已入库</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>

        <div class="pr" >
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm1">
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head0 center">申报人姓名</th>
                    <th class="head1">作者</th>
                    <th class="head1">论文名称</th>
                    <th class="head1">发表刊物</th>
                    <th class="head1">刊号</th>
                    <th class="head1">发表时间</th>
                    <th class="head1">审批状态</th>
                    <th class="head1">入库状态</th>
                    <th class="head1">入库时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result">
                    <tr>
                        <td>${result.id}</td>
                        <td>${result.employeeName}</td>
                        <td>${result.workName}</td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td>${result.publishNumber}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${result.passStatus==1}"><span style="color: #ff0dde">未审批</span></c:if>
                            <c:if test="${result.passStatus==2}"><span style="color: #ff4977">通过部门审批</span></c:if>
                            <c:if test="${result.passStatus==3}"><span style="color: #0d5ac1">未通过部门审批</span></c:if>
                            <c:if test="${result.passStatus==4}"><span style="color: #ff0000">通过科研处审核</span></c:if>
                            <c:if test="${result.passStatus==5}"><span style="color: #2225dc">未通过科研处审核</span></c:if>
                        </td>
                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm2">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head0 center">申报人姓名</th>
                    <th class="head1">著作名称</th>
                    <th class="head1">出版社</th>
                    <th class="head1">出版时间</th>
                    <th class="head1">主编（字数）</th>
                    <th class="head1">副主编（字数）</th>
                    <th class="head1">审批状态</th>
                    <th class="head1">入库状态</th>
                    <th class="head1">入库时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result">
                    <tr>
                        <td>${result.id}</td>
                        <td>${result.employeeName}</td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${result.workName}（${result.wordsNumber}字）</td>
                        <td>${result.associateEditor}（${result.associateNumber}字）</td>
                        <td>
                            <c:if test="${result.passStatus==1}">未审批</c:if>
                            <c:if test="${result.passStatus==2}">通过部门审批</c:if>
                            <c:if test="${result.passStatus==3}">未通过部门审批</c:if>
                            <c:if test="${result.passStatus==4}">通过科研处审核</c:if>
                            <c:if test="${result.passStatus==5}">未通过科研处审核</c:if>
                        </td>
                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head1">课题名称</th>
                    <th class="head1">课题负责人</th>
                    <th class="head1">结项单位</th>
                    <th class="head1">字数</th>
                    <th class="head1">审批状态</th>
                    <th class="head1">入库状态</th>
                    <th class="head1">入库时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result">
                    <tr>
                        <td>${result.id}</td>
                        <td>${result.name}</td>
                        <td>${result.workName}</td>
                        <td>${result.resultDepartment}</td>
                        <td>${result.wordsNumber}</td>
                        <td>
                            <c:if test="${result.passStatus==1}">未审批</c:if>
                            <c:if test="${result.passStatus==2}">通过部门审批</c:if>
                            <c:if test="${result.passStatus==3}">未通过部门审批</c:if>
                            <c:if test="${result.passStatus==4}">通过科研处审核</c:if>
                            <c:if test="${result.passStatus==5}">未通过科研处审核</c:if>
                        </td>
                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>