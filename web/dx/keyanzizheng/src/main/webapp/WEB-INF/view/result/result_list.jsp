<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果列表</title>
    <script type="text/javascript">
        jQuery(function () {

            // 作者
            var resFormVal = jQuery("#resultForm").val();
            var $authorName = jQuery("#authorName");
            if (resFormVal === "1") {
                $authorName.show();
            } else {
                $authorName.hide();
                $authorName.val("")
            }


            if (jQuery("#resultForm").val() == 1) {
                jQuery("#resultForm1").show();
                jQuery("#resultForm2").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("论文名称");
                jQuery("#publicationsThesisName").html("论文名称")
                jQuery("#secondType").html("刊物性质&nbsp;")
                jQuery("#publication").show()
            } else if (jQuery("#resultForm").val() == 2) {
                jQuery("#resultForm2").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("著作名称");
                jQuery("#secondType").html("类别&nbsp;")
                jQuery("#publication").hide()
            } else if (jQuery("#resultForm").val() == 4) {
                jQuery("#resultForm1").show();
                jQuery("#resultForm2").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("内刊名称");
                jQuery("#publicationsThesisName").html("内刊名称")
                jQuery("#secondType").html("刊物性质&nbsp;")
                jQuery("#publication").show()
            } else if (jQuery("#resultForm").val() == 6) {
                jQuery("#resultForm1").show();
                jQuery("#resultForm2").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("其他名称");
                jQuery("#publicationsThesisName").html("其他名称")
                jQuery("#secondType").html("刊物性质&nbsp;")
                jQuery("#publication").show()
            } else {
                jQuery("#resultForm3").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm2").hide();
                jQuery("#resultName").html("课题名称");
                jQuery("#publication").hide()
                jQuery("#secondTypeDiv").hide()
            }

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

            // 回显下拉列表
            jQuery("select[name='queryResult.teacherResearch']").val(jQuery("input[name='tr']").val())

            // 绑定时间插件
            laydate.skin('molv');
            laydate({
                elem: '#searchAddTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#searchEndTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

            secondaryLinkage(jQuery("#resultForm").get(0))

            jQuery("#storageYear").val('${storageYear}')
        });

        /**
         * 执行查询
         */
        function searchForm() {
            // 开始时间不能晚于结束时间
            // 开始时间不能大于结束时间
            // 开始时间不能>结束时间
            var searchAddTime = jQuery("#searchAddTime").val()
            var searchEndTime = jQuery("#searchEndTime").val()
            if (searchAddTime.length > 0 &&
                searchEndTime.length > 0) {

                var _addTime = new Date(searchAddTime.replace(/-/g, '/'));
                var _endTime = new Date(searchEndTime.replace(/-/g, '/'));
                if (_addTime > _endTime) {
                    alert('开始时间不能晚于结束时间, 请重新选择');
                    return false;
                }
            }

            jQuery("#getResultList").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        /**
         * 成果汇总表导出
         */
        function resultExport() {
            jQuery("#getResultList").prop("action", "${ctx}/admin/ky/resultExport.json");
            jQuery("#getResultList").submit();
            jQuery("#getResultList").prop("action", "${ctx}/admin/ky/getResultList.json");
        }


        // 二级联动
        function secondaryLinkage(obj) {
            var selectedId = jQuery(obj).find("option:selected").val()
            jQuery.getJSON("/admin/ky/secondaryLinkage.json", {"selectedId": selectedId},
                function (result) {
                    var $secondaryType = jQuery("#selectJournalNature")
                    $secondaryType.empty()

                    if (result.data.length > 0) {
                        $secondaryType.append("<option value=''>请选择</option>")
                        jQuery.each(result.data, function () {
                            $secondaryType.append("<option value='" + this.id + "'>" + this.name + "</option>")
                        })

                        $secondaryType.val("${queryResult.journalNature}")
                    } else {
                        $secondaryType.append("<option value=''>暂无类型</option>")
                    }
                })
        }

        // 导出
        function xlsExcelExport() {
            var $getResultList = jQuery("#getResultList")
            var totalCount = "${pagination.totalCount}"

            if (totalCount === "0") {
                alert("暂无数据")
                return false
            }

            $getResultList.prop("action", "${ctx}/admin/ky/xlsExcelExport.json");
            $getResultList.submit();
            $getResultList.prop("action", "${ctx}/admin/ky/getResultList.json")
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">成果列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来编辑、删除成果信息，打印成果报表，成果入库，查看人员信息；<br>
                2. 打印成果报表：点击搜索框中最后的<span style="color:red">导出汇总表</span>按钮导出相应成果汇总信息；<br>
                3. 编辑成果信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑成果信息；<br>
                4. 删除成果信息：点击操作列中的<span style="color:red">删除</span>按钮删除成果信息；<br>
                5. 成果入库：未入库的成果点击操作列中的<span style="color:red">入库</span>按钮可将成果入库；<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/getResultList.json" method="post">
                    <input type="hidden" name="queryResult.resultType" id="resultType"
                           value="${queryResult.resultType}">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">论文名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果名称" name="queryResult.name"
                                   value="${queryResult.name}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10" id="publication">
                        <span class="vam">刊物 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入发表刊物" name="queryResult.publish"
                                   value="${queryResult.publish}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">入库年份 &nbsp;</span>
                        <label class="vam">
                            <select name="storageYear" id="storageYear">
                                <option value="">未选择</option>
                                <c:forEach begin="${beginYear}" end="${endYear}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">开始时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入登记时间" name="addTime"
                                   id="searchAddTime" readonly
                                   value="<fmt:formatDate value="${addDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">结束时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入登记时间" name="endTime"
                                   id="searchEndTime" readonly
                                   value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10" id="authorName">
                        <span class="vam">作者 &nbsp;</span>
                        <label class="vam">
                            <input type="text" placeholder="输入作者姓名" name="queryResult.workName" value="${queryResult.workName}">
                        </label>
                    </div>

                    <div class="tableoptions disIb mb10">
                        <span class="vam">成果形式&nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.resultForm" id="resultForm"
                                    onchange="searchForm();">
                                <c:forEach items="${resultFormList}" var="resultForm">
                                    <option <c:if test="${queryResult.resultForm==resultForm.id}"> selected</c:if>
                                            value="${resultForm.id}">${resultForm.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="tableoptions disIb mb10" id="secondTypeDiv">
                        <span class="vam" id="secondType">成果类型&nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.journalNature" id="selectJournalNature"></select>
                        </label>
                    </div>

                    <div class="tableoptions disIb mb10">
                        <span class="vam">处室 &nbsp;</span>
                        <label class="vam">
                            <input type="hidden" name="tr" value="${queryResult.teacherResearch}">
                            <select name="queryResult.teacherResearch">
                                <option value="">--请选择--</option>
                                <c:forEach items="${subSectionList}" var="s">
                                    <option value="${s.id}">${s.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="xlsExcelExport()">导出汇总表</a>
                    <a href="${ctx}/admin/ky/resultXlsExcelImport.json" class="stdbtn ml10">导入成果</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm1">
                <thead>
                <tr>
                    <th class="head0 center" width="2%">序号</th>
                    <th class="head0 center" width="5%">申报人姓名</th>
                    <th class="head1" id="publicationsThesisName" width="15%">论文名称</th>
                    <th class="head1" width="10%">发表刊物</th>
                    <th class="head1" width="10%">刊物性质</th>
                    <th class="head1" width="5%">刊号</th>
                    <th class="head1" width="5%">作者</th>
                    <th class="head1" width="10%">处室</th>
                    <th class="head1" width="5%">登记时间</th>
                    <th class="head1" width="5%">发表时间</th>
                    <th class="head1" width="5%">入库时间</th>
                    <td class="head0 center" width="15%">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty result.workName}">
                                    ${result.workName}
                                </c:when>
                                <c:otherwise>
                                    ${result.employeeName}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty categoryMap[result.journalNature]}">
                                    未选择
                                </c:when>
                                <c:otherwise>${categoryMap[result.journalNature]}</c:otherwise>
                            </c:choose>

                        </td>
                        <td>${result.publishNumber}</td>
                        <td>${result.workName}</td>
                        <td>
                            <input type="hidden" value="${result.teacherResearch}" name="TRD">
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
                        <td><fmt:formatDate value="${result.regTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <c:if test="${result.intoStorage==1}">
                                <a href="javascript:void(0)"
                                   onclick="intoStorageResult(${result.id},${result.resultForm})" class="stdbtn"
                                   title="入库">入库</a>
                                <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除"
                                   onclick="delResult(${result.id})">删除</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm2">
                <thead>
                <tr>
                    <th class="head0 center" width="5%">序号</th>
                    <th class="head0 center" width="5%">申报人姓名</th>
                    <th class="head1" width="10%">著作名称</th>
                    <th class="head1" width="5%">类别</th>
                    <th class="head1" width="10%">出版社</th>
                    <th class="head1" width="5%">出版时间</th>
                    <th class="head1" width="10%">主编（字数）</th>
                    <th class="head1" width="10%">副主编（字数）</th>
                    <th class="head1" width="10%">处室</th>
                    <th class="head1" width="5%">登记时间</th>
                    <th class="head1" width="5%">入库时间</th>
                    <td class="head0 center" width="25%">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.employeeName}</td>
                        <td>${result.name}</td>
                        <td>${result.journalNatureName}</td>
                        <td>${result.publish}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${result.workName}（${result.wordsNumber}字）
                        </td>
                        <td>${result.associateEditor}（${result.associateNumber}字）
                        </td>
                        <td>
                            <input type="hidden" value="${result.teacherResearch}" name="TRD">
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
                        <td><fmt:formatDate value="${result.regTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <c:if test="${result.intoStorage==1}">
                                <a href="javascript:void(0)"
                                   onclick="intoStorageResult(${result.id},${result.resultForm})" class="stdbtn"
                                   title="入库">入库</a>
                                <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除"
                                   onclick="delResult(${result.id})">删除</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <thead>
                <tr>
                    <th class="head0 center" width="5%">序号</th>
                    <th class="head1" width="5%">申报人姓名</th>
                    <th class="head1" width="20%">课题名称</th>
                    <th class="head1" width="10%">课题负责人</th>
                    <th class="head1" width="5%">字数</th>
                    <th class="head1" width="10%">处室</th>
                    <th class="head1" width="5%">开始时间</th>
                    <th class="head1" width="5%">结束时间</th>
                    <th class="head1" width="5%">登记时间</th>
                    <th class="head1" width="5%">入库时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.employeeName}</td>
                        <td>${result.name}</td>
                        <td>${result.workName}</td>
                        <td>${result.wordsNumber}</td>
                        <td>
                            <input type="hidden" value="${result.teacherResearch}" name="TRD">
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
                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.regTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <c:if test="${result.passStatus == 8 && result.intoStorage==1}">
                                <a href="javascript:void(0)"
                                   onclick="intoStorageResult(${result.id},${result.resultForm})" class="stdbtn"
                                   title="入库">入库</a>
                                <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除"
                                   onclick="delResult(${result.id})">删除</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                            <a href="${ctx}/admin/ky/getTaskChangeList.json?id=${result.id}" class="stdbtn"
                               title="变更记录">变更记录</a>
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
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
</body>
</html>