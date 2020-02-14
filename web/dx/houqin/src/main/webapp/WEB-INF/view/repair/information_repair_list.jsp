<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查询报修列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }


        function delRepair(id) {
            if (confirm("确定删除这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delRepair.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.href = "/admin/houqin/queryXxRepairList.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("#status").val(-1);
            jQuery("#typeId").val("");
        }

        function updateCheck(id, type) {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/checkRepair.json",
                data: {
                    "repair.id": id,
                    "repair.check": type
                },
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryXxRepairList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function toAppraise(id) {
            window.location.href = "/admin/houqin/toAppraise.json?repairId=" + id;
        }
    </script>
    <script type="text/javascript">
        var data = data;
        var functionsIds;
        function addSystemUsers(id) {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/repairGetSysuserList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择人员列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/toAddRepairPeairPeople.json",
                                    data: {'peairPeopleId': functionsIds, 'id': id},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            alert("操作成功!");
                                            window.location.reload();
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }

                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '30%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "600px");
                    jQuery('#popup_message').css("max-height", "450px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }

        function repairExport() {
            jQuery("#searchForm").prop("action", "/admin/houqin/repairExport.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "/admin/houqin/queryXxRepairList.json");
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">查询报修列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于报修列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.详情：点击<span style="color:red">详情</span>查看报修物品详细信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除报修物品信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryXxRepairList.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">维修分类 &nbsp;</span>
                        <label class="vam">
                            <select name="repair.typeId" style="width: 200px" id="typeId">
                                <option value="">--全部--</option>
                                <c:forEach items="${repairTypeList}" var="repairType">
                                    <option value="${repairType.id}"
                                            <c:if test="${repairType.id==repair.typeId}">selected="selected"</c:if>>${repairType.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select name="repair.status" style="width: 150px" id="status">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${repair.status==0}">selected="selected"</c:if>>未处理
                                </option>
                                <option value="1" <c:if test="${repair.status==1}">selected="selected"</c:if>>正维修
                                </option>
                                <option value="2" <c:if test="${repair.status==2}">selected="selected"</c:if>>已维修
                                </option>
                                <option value="3" <c:if test="${repair.status==3}">selected="selected"</c:if>>已取消
                                </option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="repairExport()" class="stdbtn ml10">导出Excel</a>
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
                    <th class="head0 center">报修编号</th>
                    <th class="head0 center">报修人</th>
                    <th class="head0 center">报修物品</th>
                    <th class="head0 center">报修分类</th>
                    <th class="head0 center">报修地点</th>
                    <th class="head0 center">报修时间</th>
                    <%--<th class="head0 center">维修时间</th>--%>
                    <th class="head0 center">状态</th>
                    <%--<th class="head0 center">维修人员编号</th>--%>
                    <th class="head0 center">维修人员</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${repairList}" var="repair" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${repair.number}</td>
                        <td>${repair.userName}</td>
                        <td>${repair.name}</td>
                        <td>${repair.typeName}</td>
                        <td>${repair.repairSite}</td>
                        <td><fmt:formatDate value="${repair.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <%--<td><fmt:formatDate value="${repair.repairTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
                        <td style="width: 5%">
                            <c:if test="${repair.status==0}"><font style="color: red">未处理</font></c:if>
                            <c:if test="${repair.status==1}"><font style="color:orange">正维修</font></c:if>
                            <c:if test="${repair.status==2}"><font style="color:green">已维修</font></c:if>
                            <c:if test="${repair.status==3}"><font style="color:#ccc">已取消</font></c:if>
                        </td>
                            <%--<td>--%>
                            <%--${repair.sysUser.userNo}--%>
                            <%--</td>--%>
                        <td>
                                ${repair.sysUser.userName}
                        </td>
                        <td class="center" style="width: 18%">
                            <c:if test="${repair.status==0&&repair.pepairPeopleId==null}">
                                <a href="JavaScript:void(0)" onclick="addSystemUsers(${repair.id})" class="stdbtn"
                                   title="指派维修人员">指派维修人员</a>
                            </c:if>
                            <c:if test="${repair.status==0&&repair.pepairPeopleId!=null}">
                                <a href="JavaScript:void(0)" class="stdbtn btn_orange" title="已指派">已指派</a>
                            </c:if>
                            <c:if test="${repair.status==2}">
                                <a href="JavaScript:void(0)" onclick="toAppraise(${repair.id})" class="stdbtn"
                                   title="评价报修结果">评价报修结果</a>
                            </c:if>
                            <a href="${ctx}/admin/houqin/descRepair.json?id=${repair.id}" class="stdbtn"
                               title="详情">详情</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delRepair('${repair.id}')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
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