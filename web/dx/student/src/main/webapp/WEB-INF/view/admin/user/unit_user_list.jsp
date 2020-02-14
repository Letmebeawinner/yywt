<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>单位学员列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data: {
                        "classTypeId": selectedClassTypeId
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            var classstr = "<option value=0>请选择</option>";
                            if (list != null && list.length > 0) {

                                for (var i = 0; i < list.length; i++) {
                                    classstr += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                            jQuery("#classId").val("${user.classId}");
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('添加失败', '提示', function () {
                        });
                    }
                });
            });
            jQuery("#classTypeId").val("${user.classTypeId}");
            jQuery("#classTypeId").trigger("change");

            var studentId = "${user.studentId}";
            jQuery("#studentId").val(studentId);
            /*var baodao="
            ${user.baodao}";
             jQuery("#baodao option[value='"+baodao+"']").attr("selected",true);*/
            jQuery("#userId").val("${user.id}");
            jQuery("#name").val("${user.name}");
            jQuery("#idNumber").val("${user.idNumber}");
            jQuery("#email").val("${user.email}");
            jQuery("#mobile").val("${user.mobile}");
            jQuery("#time").val("${time}");
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function deleteUser(id,status) {
            if(status!=1&&status!=7){
            jConfirm('您确定要删除吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/user/delUser.json?id=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                window.location.reload();
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('删除失败', '提示', function () {
                            });
                        }
                    });
                }
            });
            }else{
                jAlert('请联系管理员','提示',function() {});
            }
        }

        function assignMonitor(userId) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/getMonitor.json',
                data: {
                    "userId": userId
                },
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    console.info("success");
                    if (result.code == "0") {
                        if (result.data.hasMonitor == 1) {
                            jConfirm('该班次原班长为' + result.data.monitorName + ',您确定要修改班长吗?', '确认', function (r) {
                                if (r) {
                                    jQuery.ajax({
                                        url: '${ctx}/admin/jiaowu/user/assignMonitor.json?userId=' + userId + "&monitorId=" + result.data.monitorId,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (result) {
                                            if (result.code == "0") {
                                                window.location.reload();
                                            } else {
                                                jAlert(result.message, '提示', function () {
                                                });
                                            }
                                        },
                                        error: function (e) {
                                            jAlert('修改失败', '提示', function () {
                                            });
                                        }
                                    });
                                }
                            });
                        } else {
                            jConfirm('您确定要将其设为班长吗?', '确认', function (r) {
                                if (r) {
                                    jQuery.ajax({
                                        url: '${ctx}/admin/jiaowu/user/assignMonitor.json?userId=' + userId,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (result) {
                                            if (result.code == "0") {
                                                window.location.reload();
                                            } else {
                                                jAlert(result.message, '提示', function () {
                                                });
                                            }
                                        },
                                        error: function (e) {
                                            jAlert('修改失败', '提示', function () {
                                            });
                                        }
                                    });
                                }
                            });
                        }

                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('查询失败', '提示', function () {
                    });
                }
            });
        }

        function cancelMonitor(userId) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/cancelMonitor.json?userId=' + userId,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.reload();
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('更新失败', '提示', function () {
                    });
                }
            });
        }

        function selectUnit() {
            window.open('${ctx}/jiaowu/user/unitListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addUnit(unitIdAndName) {
            jQuery("#unitId").val(unitIdAndName[0]);
            jQuery("#unit").val(unitIdAndName[1]);
            jQuery("#unitspan").html(unitIdAndName[1] + '<a href="javascript:delUnit()" class="stdbtn">删除</a>');
        }

        function delUnit() {
            jQuery("#unitId").val("");
            jQuery("#unit").val("");
            jQuery("#unitspan").html("");
        }

        function useExcel() {
            var totalCount = "${pagination.totalCount}";
            if (totalCount == "" || totalCount == "0") {
                jAlert("暂无数据,不可导出!", '提示', function () {
                });
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/exportUserList.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userList.json");
        }

        function updateUser(userId,status){
            if(status!=1&&status!=7){
                window.location.href="${ctx}/admin/jiaowu/user/toUpdateUser.json?id="+userId;
            }else{
                jAlert('请联系管理员','提示',function() {});
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">单位学员列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示单位学员列表.<br/>
					2.可通过班型、班次和学号查询对应的学员.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/unitUserList.json" method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="classTypeId" class="vam" id="classTypeId">
                                <option value="0">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">班次 &nbsp;</span>
                        <label class="vam">
                            <select name="classId" class="vam" id="classId">
                                <option value="0">请选择</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value=""
                                   placeholder="请输入名称">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">身份证号 &nbsp;</span>
                        <label class="vam">
                            <input id="idNumber" style="width: 200px;" name="idNumber" type="text" class="hasDatepicker"
                                   value="" placeholder="请输入身份证号">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">时间 &nbsp;</span>
                        <label class="vam">
                            <select name="time" class="vam" id="time">
                                <option value="0">请选择</option>
                                <option value="1">上半年轮巡人员统计</option>
                                <option value="2">一年内轮巡人员统计</option>
                                <option value="3">五年内轮巡人员统计</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
                </div>
                <%--  <div class="disIb ml20 mb10">
                        <span class="vam">总人数: &nbsp;</span>
                        <label class="vam">
                        ${totalNum}人
                        </label>
                    </div> --%>
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
                    <th class="head0 center">班型</th>
                    <th class="head0 center" style="width: 16%">班次</th>
                    <th class="head0 center">姓名</th>
                    <%--<th class="head1 center">学号</th>--%>
                    <%--<c:if test="${showIdnumber==true}">
                        <th class="head1 center">身份证号</th>
                    </c:if>--%>
                    <th class="head0 center">性别</th>
                    <%--<th class="head0 center">出生日期</th>--%>
                    <%--<th class="head0 center">年龄</th>--%>
                    <th class="head0 center">单位</th>
                    <th class="head0 center" width="10%">学员单位及职务职称（全称）</th>
                    <th class="head0 center">级别</th>
                    <th class="head0 center">政治面貌</th>
                    <%--<th class="head0 center">学历</th>--%>
                    <th class="head0 center">联系电话</th>
                    <th class="head0 center">报名时间</th>
                    <%--<th class="head1 center">操作</th>--%>
                    <th class="head1 center">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user" varStatus="index">
                        <tr>
                                <%--<td>${user.id}</td>--%>

                            <td>${index.index+1}</td>
                            <td>${user.classTypeName}</td>
                            <td>${user.className}</td>
                            <td>${user.name}</td>
                            <td>${user.sex}</td>
                            <td>${user.unit}</td>
                            <td>${user.job}</td>
                            <td>
                                <c:if test="${user.business==1}">
                                    正厅
                                </c:if>
                                <c:if test="${user.business==2}">
                                    巡视员
                                </c:if>
                                <c:if test="${user.business==3}">
                                    副厅
                                </c:if>
                                <c:if test="${user.business==4}">
                                    副巡视员
                                </c:if>
                                <c:if test="${user.business==5}">
                                    正县
                                </c:if>
                                <c:if test="${user.business==6}">
                                    副县
                                </c:if>
                                <c:if test="${user.business==7}">
                                    调研员
                                </c:if>
                                <c:if test="${user.business==8}">
                                    副调研员
                                </c:if>
                                <c:if test="${user.business==9}">
                                    正科
                                </c:if>
                                <c:if test="${user.business==10}">
                                    副科
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${user.politicalStatus==0}">
                                    中共党员
                                </c:if>
                                <c:if test="${user.politicalStatus==1}">
                                    民主党派
                                </c:if>
                                <c:if test="${user.politicalStatus==2}">
                                    无党派人士
                                </c:if>
                                <c:if test="${user.politicalStatus==3}">
                                    群众
                                </c:if>
                                <c:if test="${user.politicalStatus==4}">
                                    其它
                                </c:if>
                            </td>
                            <td>${user.mobile}</td>
                            <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td id="statustd${user.id}">
                                <c:if test="${user.status==3}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="单位已通过">单位已通过</a>
                                </c:if>
                                <c:if test="${user.status==5}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部驳回">组织部已驳回</a>
                                </c:if>
                                <c:if test="${user.status==6}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人驳回">个人已驳回</a>
                                </c:if>
                                <c:if test="${user.status==1}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人通过">个人已通过</a>
                                    <%--<a href="${ctx}/admin/jiaowu/user/toUpdateUser.json?id=${user.id}"
                                       class="stdbtn btn_orange" title="修改学员">修改学员信息</a>--%>
                                </c:if>
                                <c:if test="${user.status==4}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部通过">组织部已通过</a>
                                </c:if>
                                <c:if test="${user.status==8}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="在校">在校</a>
                                </c:if>
                                <c:if test="${user.status==7}">
                                    <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="已结业">已结业</a>
                                </c:if>

                                <a href="javascript:updateUser('${user.id}','${user.status}')" class="stdbtn btn_orange" title="修改学员信息">修改学员信息</a>
                                <a href="javascript:void(0)" onclick="deleteUser(${user.id},${user.status})" class="stdbtn" title="删除">删除</a>
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