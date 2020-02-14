<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>物资信息列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getMaterialsList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMaterials(materialsId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteMaterials.json?id="+materialsId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
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
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#createTime',
                format:'YYYY-MM-DD'
            });
        });
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">物资信息列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除工会物资<br>
                2. 工会物资入库：点击搜索框中最后的<span style="color:red">物资入库</span>按钮进行入库；<br>
                3. 编辑工会物资：点击操作列中的<span style="color:red">编辑</span>按钮编辑工会物资；<br>
                4. 删除工会物资：点击操作列中的<span style="color:red">删除</span>按钮删除工会物资。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getMaterialsList" action="${ctx}/admin/rs/getMaterialsList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">入库时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: 150px;" class="hasDatepicker" readonly id="createTime" placeholder="选择入库时间" name="materials.material" value="${materials.material}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddMaterials.json" class="stdbtn ml10" >物资入库</a>
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
                    <th class="head0 center">id</th>
                    <th class="head0 center">物资名称</th>
                    <th class="head1">入库数量</th>
                    <th class="head1">入库时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${materialsList}" var="materials" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${materials.material}</td>
                        <td>${materials.number}</td>
                        <td><fmt:formatDate value="${materials.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateMaterials.json?id=${materials.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMaterials(${materials.id})">删除</a>
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