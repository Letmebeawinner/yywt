<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>获奖申报列表</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#resultFormId").val("${award.resultForm}")
            chgAwardSit()
            jQuery("#awardSituationId").val("${award.awardSituation}")
        })

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function audit(id) {
            if (confirm("是否通过审批?")) {
                jQuery.ajax({
                    url: '/admin/ky/award/audit.json?id=' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        alert(result.message);
                        window.location.reload();
                    },
                    error: function () {
                        alert('操作失败, 请稍后再试');
                    }
                });
            }
        }

        function del(id) {
            if (confirm("是否确认删除?")) {
                jQuery.ajax({
                    url: '/admin/ky/award/del.json?id=' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        alert(result.message);
                        window.location.reload();
                    },
                    error: function () {
                        alert('操作失败, 请稍后再试');
                    }
                })
            }

        }

        function chgAwardSit() {
            var $rf = jQuery("#resultFormId")
            var $as = jQuery("#awardSituationId")
            if ($rf.val() === "3") {
                $as.empty()
                $as.append("<option value=''>未选择</option>")
                $as.append("<option value='1'>党校系统级</option>")
                $as.append("<option value='2'>哲学社会成果奖（国家级、省级、市级）</option>")
                $as.append("<option value='3'>其他</option>")
            } else {
                $as.empty()
                $as.append("<option value=''>未选择</option>")
                $as.append("<option value='1'>国家级</option>")
                $as.append("<option value='2'>省级</option>")
                $as.append("<option value='3'>市级</option>")
                $as.append("<option value='4'>其他</option>")
            }
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">获奖申报列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示获奖申报列表.<br/>
					2.可通过获奖申报标题查询对应的获奖申报列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/ky/award/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">获奖申报标题 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="topicName" type="text"
                                   value="${award.title}" placeholder="获奖申报标题">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">成果形式 &nbsp;</span>
                        <label class="vam">
                            <select name="resultForm" id="resultFormId" onchange="chgAwardSit()">
                                <option value="">未选择</option>
                                <option value="1">论文</option>
                                <option value="2">著作</option>
                                <option value="3">课题</option>
                                <option value="4">内刊</option>
                                <option value="5">调研报告</option>
                                <option value="6">其他</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">获奖情况 &nbsp;</span>
                        <label class="vam">
                            <select name="awardSituation" id="awardSituationId">
                                <option value="">未选择</option>
                                <option value="1">国家级奖</option>
                                <option value="2">省部级奖</option>
                                <option value="3">地市级奖</option>
                                <option value="4">其他  </option>
                            </select>
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">标题</th>
                    <th class="head0 center">申请人</th>
                    <th class="head0 center">成果形式</th>
                    <th class="head0 center">获奖情况</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty awards}">
                    <c:forEach items="${awards}" var="award" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${award.title}</td>
                            <td>${award.userName}</td>
                            <td>${award.resultFormName}</td>
                            <td>${award.sitName}</td>
                            <td>
                                <c:if test="${award.status == 0}">
                                    未审批
                                </c:if>
                                <c:if test="${award.status == 1}">
                                    已通过审批
                                </c:if>
                                <c:if test="${award.status == 2}">
                                    审批被拒绝
                                </c:if>
                            </td>
                            <td class="center">
                                <c:if test="${award.status == 0}">
                                    <a href="${ctx}/admin/ky/award/audit.json?id=${award.id}"
                                       class="stdbtn" title="审批">审批</a>
                                </c:if>
                                <a href="${ctx}/admin/ky/award/awardInfo.json?id=${award.id}"
                                   class="stdbtn" title="查看详情">查看详情</a>
                                <a href="javascript:void(0)" onclick="del('${award.id}')"
                                   class="stdbtn" title="删除">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>