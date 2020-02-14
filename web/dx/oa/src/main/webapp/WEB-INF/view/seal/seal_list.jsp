<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>印章列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#sealForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delSeal(sealId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteSeal.json",
                    data: {
                        "id" : sealId
                    },
                    type: "POST",
                    dataType: "JSON",
                    async : false,
                    cache : false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">印章列表</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于印章信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用印章信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看用印章信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除印章信息；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="sealForm" action="${ctx}/admin/oa//queryAllSeal.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入ID" style="width: auto" name="seal.id" value="${seal.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">印章名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入印章名称" style="width: auto" name="seal.name" value="${seal.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">印章类别 &nbsp;</span>
                        <label class="vam">
                            <select name = "seal.sealTypeId" style="width: 150px">
                                <option value = "">--请选择--</option>
                                <c:forEach items = "${sealTypes}" var = "sealType">
                                    <option value = "${sealType.id}" <c:if test = "${seal.sealTypeId == sealType.id}">selected = "selected"</c:if>>${sealType.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">印章用途 &nbsp;</span>
                        <label class="vam">
                            <select name = "seal.sealFunctionId" style="width: 150px">
                                <option value = "">--请选择--</option>
                                <c:forEach items = "${sealFunctions}" var = "sealFunction">
                                    <option value = "${sealFunction.id}" <c:if test = "${seal.sealFunctionId == sealFunction.id}">selected = "selected"</c:if>>${sealFunction.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">存放地址 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="填写存放地址" style="width: 300px;" name="seal.address" value="${seal.address}">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">印章名称</th>
                    <th class="head1">印章类别</th>
                    <th class="head1">印章用途</th>
                    <th class="head1">存放地址</th>
                    <th class="head1">备注</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sealDtos}" var="sealDto">
                    <tr>
                        <td>${sealDto.id}</td>
                        <td>${sealDto.name}</td>
                        <td>${sealDto.sealTypeName}</td>
                        <td>${sealDto.sealFunctionName}</td>
                        <td>${sealDto.address}</td>
                        <td>${sealDto.note}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateSeal.json?id=${sealDto.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/toUpdateSeal.json?id=${sealDto.id}&flag=1" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delSeal(${sealDto.id})">删除</a>
                        </td>
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