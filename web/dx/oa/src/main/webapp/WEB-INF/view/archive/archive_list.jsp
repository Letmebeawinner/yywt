<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>档案列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delArchive(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/delArchive.json?id=" + id,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }


        function archiveExcel() {
            var totalCount = "${pagination.totalCount}";
            if (totalCount == "" || totalCount == "0") {
                alert("暂无数据,不可导出!");
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/oa/exportArchiveList.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/oa/queryArchiveList.json");
        }

        /**
         * 入库
         * @param id
         */
        function stock(id, stockFlag) {
            if (confirm("确认要入库？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/updateStockFlag.json",
                    data: {
                        "id": id,
                         "stockFlag": stockFlag
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
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
        <h1 class="pagetitle">档案列表</h1>

        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于档案信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>，修改用档案信息；<br>
            4.查看：点击<span style="color:red">查看</span>，查看用档案信息；<br>
            5.删除：点击<span style="color:red">删除</span>，删除档案信息；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">


        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryArchiveList.json?flag=${flag}" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">档号 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto" placeholder="输入档号"
                                   name="archive.danghao" value = "${archive.danghao}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">档案类型 &nbsp;</span>
                        <label class="vam">
                            <select name="archive.typeId">
                                <option value="">--请选择--</option>
                                <c:forEach items="${archiveTypeList}" var="arch">
                                    <option value="${arch.id}" <c:if test = "${archive.typeId == arch.id}">selected</c:if>>${arch.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="archiveExcel()">导出Excel</a>
                    <a href="${ctx}/admin/oa/toAddArchive.json" class="stdbtn ml10">添 加</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">部门名称</th>
                    <th class="head0 center">档案分类</th>
                    <th class="head0 center">档号</th>
                    <th class="head0 center">件号</th>
                    <th class="head0 center">责任者</th>
                    <th class="head0 center">文号</th>
                    <th class="head0 center">题名</th>
                    <th class="head0 center">日期</th>
                    <th class="head0 center">页数</th>
                    <th class="head0 center">机构或问题</th>
                    <th class="head0 center">是否入库</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${archiveList}" var="archive" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${archive.departmentName}</td>
                        <td>${archive.typeName}</td>
                        <td>${archive.danghao}</td>
                        <td>${archive.jianhao}</td>
                        <td>${archive.author}</td>
                        <td>${archive.wenhao}</td>
                        <td>${archive.autograph}</td>
                        <td><fmt:formatDate value="${archive.archivedate}" pattern="yyyyMMdd"/></td>
                        <td>${archive.pages}</td>
                        <td>${archive.orginzation}</td>
                        <td>
                            <c:if test = "${archive.stockFlag == 0}">
                                未入库
                            </c:if>
                            <c:if test = "${archive.stockFlag == 1}">
                                已入库
                            </c:if>
                        </td>
                        <td class="center">
                            <c:if test = "${flag == 1}">
                                <a href="javascript:void(0)" class="stdbtn" title="入库" onclick = "stock(${archive.id}, 1)">入库</a>
                                <a href="javascript:void(0)" class="stdbtn" title="取消" onclick = "stock(${archive.id}, 0)">取消入库</a>
                            </c:if>
                            <c:if test = "${flag == 0}">
                                <a href="${ctx}/admin/oa/toUpdateArchive.json?id=${archive.id}" class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delArchive(${archive.id})">删除</a>
                            </c:if>

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