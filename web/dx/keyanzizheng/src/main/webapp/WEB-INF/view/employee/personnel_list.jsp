<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>员工列表</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery(".KYB").hide()
            // 当前页所有的隐藏域
            var TRDArray = jQuery("input[name='TRD']")
            TRDArray.each(function () {
                // 不为空时, 回显教研部
                if (this.value.length > 0) {
                    // 兄弟节点的索引     从0开始
                    // 但教研部隐藏域的值  从1开始
                    jQuery(this).nextAll().eq(this.value - 1).show()
                }
            })
        })
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getEmployeeList").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        /**
         * 去空格
         * @param obj 文本
         */
        function replaceSpace(obj) {
            obj.value = obj.value.replace(/\s/gi, '')
        }

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">人员列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看参与过成果的教职工信息及相关成果<br>
                2. 查看成果：点击操作列中的<span style="color:red">成果列表</span>按钮查看教职工相关成果。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/ky/getPersonnelList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入姓名" name="name"
                                   value="${string}" onBlur="replaceSpace(this)">
                            <input type="hidden" name="resultType" value="${resultType}"/>
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
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">处室</th>
                    <th class="head1">民族</th>
                    <th class="head1">职位职务</th>
                    <th class="head1">电话</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="per" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${per.name}</td>
                        <td>
                            <input type="hidden" value="${per.teachingResearchDepartment}" name="TRD">
                            <span class="KYB">党史</span>
                            <span class="KYB">公管</span>
                            <span class="KYB">经济学</span>
                            <span class="KYB">法学</span>
                            <span class="KYB">文化与社会发展</span>
                            <span class="KYB">马列</span>
                            <span class="KYB">统一战线</span>
                            <span class="KYB">校委委员</span>
                            <span class="KYB">办公室(区县党校工作处)</span>
                            <span class="KYB">机关党委</span>
                            <span class="KYB">组织人事处</span>
                            <span class="KYB">财务处</span>
                            <span class="KYB">后勤管理处</span>
                            <span class="KYB">纪检监察室</span>
                            <span class="KYB">教务处</span>
                            <span class="KYB">信息资源管理处</span>
                            <span class="KYB">科研管理处</span>
                            <span class="KYB">生态文明研究所</span>
                            <span class="KYB">图书馆</span>
                            <span class="KYB">学员管理处</span>
                        </td>
                        <td>${per.nationality}</td>
                        <td>${per.position}</td>
                        <td>${per.mobile}</td>
                        <td class="center">
                            <c:if test="${not empty per.id}">
                                <a href="${ctx}/admin/ky/getEmployeeResultList.json?queryResult.employeeId=${per.id}"
                                   class="stdbtn" title="成果列表">成果列表</a>
                                <a href="${ctx}/admin/ky/rr/listResearchReport.json?resultType=2&peopleId=${per.id}"
                                   class="stdbtn" title="调研报告列表">调研报告列表</a>
                                <a href="${ctx}/admin/ky/getEmployeeResultStatistic.json?id=${per.id}"
                                   class="stdbtn" title="成果列表">统计信息</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
</body>
</html>