<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>单位人员名单</title>
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

            jQuery("#time").val("${time}");
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function deleteUser(id) {
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
        }

        function baodao(id) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/baodao.json?id=' + id,
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

        function baodao(id) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/baodao.json?id=' + id,
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

        function setLunXun(id) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/setNewUserToLunXun.json?id=' + id,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery("#operationtd" + id).html("");
                        jQuery("#statustd" + id).html("已设置为培训人员");
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

        function useExcel() {
            var totalCount = "${pagination.totalCount}";
            if (totalCount == "" || totalCount == "0") {
                jAlert("暂无数据,不可导出!", '提示', function () {
                });
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/exportNewUserList.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/newUserList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">单位人员名单</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示单位人员名单.<br/>
					2.可通过点击"设置为培训人员"将单位人员设置为培训人员.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/newUserList.json" method="get">
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
                        <span class="vam">学号 &nbsp;</span>
                        <label class="vam">
                            <input id="studentId" style="width: auto;" name="studentId" type="text"
                                   class="hasDatepicker" value="" placeholder="请输入学号">
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
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head1 center">身份证号</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">出生日期</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">学员单位及职务职称（全称）</th>
                    <th class="head0 center">级别</th>
                    <th class="head0 center">政治面貌</th>
                    <th class="head0 center">学历</th>
                    <th class="head0 center">联系电话</th>
                    <th class="head0 center">报名时间</th>
                    <th class="head1 center">操作</th>
                    <th class="head1 center">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${user.classTypeName}</td>
                            <td>${user.className}</td>
                            <td>${user.name}</td>
                            <td>${user.idNumber}</td>
                            <td>${user.sex}</td>
                            <td>${user.birthday}</td>
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
                            <td>${user.qualification}</td>
                            <td>${user.mobile}</td>
                            <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center" id="operationtd${user.id}">
                                <c:if test="${user.status==2}">
                                    <a href="javascript:void(0)" onclick="setLunXun(${user.id})" class="stdbtn"
                                       title="设置为培训人员">设置为培训人员</a>
                                </c:if>
                            </td>
                            <td id="statustd${user.id}">
                                <c:if test="${user.status==3}">
                                    已设置为培训人员
                                </c:if>
                                <c:if test="${user.status==5}">
                                    组织部驳回
                                </c:if>
                                <c:if test="${user.status==6}">
                                    个人驳回
                                </c:if>
                                <c:if test="${user.status==1}">
                                    个人通过
                                </c:if>
                                <c:if test="${user.status==4}">
                                    组织部通过
                                </c:if>
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
</body>
</html>