<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>报名信息确认</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
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

            var classTypeId = "${user.classTypeId}";
            jQuery("#classTypeId option[value='" + classTypeId + "']").attr("selected", true);
            var classId = "${user.classId}";
            if (classId != null && classId != 0) {
                var className = "${user.className}";
                jQuery("#classId").html("<option value='" + classId + "'>" + className + "</option>");
            }
            var studentId = "${user.studentId}";
            jQuery("#studentId").val(studentId);

            var baodao = "${user.baodao}";
            jQuery("#baodao option[value='" + baodao + "']").attr("selected", true);


        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function pass(id) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/passJoinClass.json?id=' + id,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery("#operationtd" + id).html("<a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已确认参加'>已确认参加</a>");

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

        function deny(id) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/denyJoinClass.json?id=' + id,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery("#operationtd" + id).html("<a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已拒绝参加'>已拒绝参加</a>");
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
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">报名信息确认</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示当前登录学员进行报名信息确认.<br/>
                    2.可点击"确认参加"来参加该班次,也可点击"取消参加"拒绝参加该班次.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

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
                    <th class="head0 center">班型</th>
                    <th class="head0 center" width="25%">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">年龄</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">学员单位及职务职称（全称）</th>
                    <th class="head0 center">级别</th>
                    <th class="head0 center">政治面貌</th>
                    <th class="head0 center">联系电话</th>
                    <th class="head0 center">报名时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${user.classTypeName}</td>
                    <td>${user.className}</td>
                    <td>${user.name}</td>
                    <td>${user.sex}</td>
                    <td>${user.age}</td>
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
                    <td>${mobile}</td>
                    <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td class="center" id="operationtd${user.id}">
                        <a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}" class="stdbtn btn_orange"
                           title="详细信息">详细信息</a>
                        <c:if test="${user.status==4}">
                            <a href="javascript:void(0)" onclick="pass(${user.id})" class="stdbtn btn_orange"
                               title="通过">确认参加</a>
                            <a href="javascript:void(0)" onclick="deny(${user.id})" class="stdbtn btn_orange"
                               title="驳回">拒绝参加</a>
                        </c:if>
                        <c:if test="${user.status==1}">
                            <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="确认参加">已确认参加</a>
                        </c:if>
                        <c:if test="${user.status==6}">
                            <a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="已拒绝参加">已拒绝参加</a>
                        </c:if>

                    </td>
                </tr>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <%--<jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>--%>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>