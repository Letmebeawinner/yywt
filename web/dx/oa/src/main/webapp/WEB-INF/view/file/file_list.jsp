<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>文件列表</title>
    <script type="text/javascript" src="${ctx}/static/common/file-download.js"></script>
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

        function deleteFile(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteFile.json",
                    data: {"id": id},
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

        function updateShare(id, share) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateFile.json",
                data: {
                    "file.id": id,
                    "file.share": share
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


    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">文件列表</h1>

        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于文件信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>，修改文件信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除文件；<br>
            5.下载：点击<span style="color:red">下载</span>，下载文件；<br>
            5.分享：点击<span style="color:red">未分享</span>，分享文件；点击<span style="color:red">已分享</span>，取消分享文件<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryAllFile.json" method="post">

                    <%-- <div class="disIb ml20 mb10">
                         <span class="vam">ID &nbsp;</span>
                         <label class="vam">
                             <input style="width: auto;" name="file.id" type="text" class="hasDatepicker"
                                    value="${file.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                         </label>
                     </div>--%>

                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="file.title" type="text" class="hasDatepicker"
                                   value="${file.title}" placeholder="输入名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/oa/toAddFile.json" class="stdbtn ml10" onclick="emptyForm()">添 加</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">

                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">文件名称</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${fileList}" var="file" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${file.title}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateFile.json?id=${file.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteFile(${file.id})">删除</a>
                            <a href="${file.fileUrl}" class="stdbtn" title="下载">下载</a>
                            <c:if test="${file.share==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="未分享" onclick="updateShare(${file.id},'1')">未分享</a>
                            </c:if>
                            <c:if test="${file.share==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="已分享" onclick="updateShare(${file.id},'0')">已分享</a>
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