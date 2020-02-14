<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>正式学员列表</title>
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
            jQuery("#userId").val("${user.id}");
            jQuery("#name").val("${user.name}");
            jQuery("#idNumber").val("${user.idNumber}");
            jQuery("#email").val("${user.email}");
            jQuery("#mobile").val("${user.mobile}");
            jQuery("#time").val("${time}");
            jQuery("#groupId").val("${groupId}");
            jQuery("#unitName").val("${user.unit}");
            jQuery("#business").val("${user.business}");
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function deleteUser(id,mobile) {
            jConfirm('您确定要删除吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/user/delUser.json?id=' + id,
                        data:{"mobile":mobile},
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                alert("删除成功!");
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
            var classId = jQuery("#classId").val();
            if (classId == null || classId == "" || classId == "0") {
                jAlert("请选择班次!", '提示', function () {
                });
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/newUserListExcel/"+classId+".json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userList.json");
        }

        function useExcelByCondition(){
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userListExcelByCondition.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">正式学员列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示正式学员列表.<br/>
					2.可通过班型、班次和学号查询对应的学员.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userList.json" method="post">
                    <input type="hidden" name="unitId" id="unitId" value="${user.unitId}"/>
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
                        <span class="vam">组别 &nbsp;</span>
                        <label class="vam">
                            <select name="groupId" class="vam" id="groupId">
                                <option value="">请选择</option>
                                <option value="1">第一组</option>
                                <option value="2">第二组</option>
                                <option value="3">第三组</option>
                                <option value="4">第四组</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">级别 &nbsp;</span>
                        <label class="vam">
                            <select name="business" id="business">
                                <option value="">请选择</option>
                                <option value="1">正厅</option>
                                <option value="2">巡视员</option>
                                <option value="3">副厅</option>
                                <option value="4">副巡视员</option>
                                <option value="5">正县</option>
                                <option value="6">副县</option>
                                <option value="7">调研员</option>
                                <option value="8">副调研员</option>
                                <option value="9">正科</option>
                                <option value="10">副科</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">类别 &nbsp;</span>
                        <label class="vam">
                            <select name="category" id="category">
                                <option value="0" >请选择</option>
                                <option value="1" <c:if test="${category == 1}">selected=selected</c:if>>未毕业</option>
                                <option value="2" <c:if test="${category == 2}">selected=selected</c:if>>已毕业</option>
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
                            <input id="idNumber" style="width: auto;" name="idNumber" type="text" class="hasDatepicker"
                                   value="" placeholder="请输入身份证号">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">单位 &nbsp;</span>
                        <label class="vam"><input style="width: auto;" name="unit" type="text" id="unitName"
                                                  class="hasDatepicker" value="" placeholder="请输入单位名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">根据组别导出Excel</a>
                    <a href="javascript: void(0)" onclick="useExcelByCondition()" class="stdbtn btn_orange">根据条件导出Excel</a>
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
                    <th class="head0 center" width="5%">姓名</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">政治面貌</th>
                    <th class="head0 center">民族</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center" width="7%">级别</th>
                    <th class="head0 center">学员单位及职务职称（全称）</th>
                    <th class="head0 center">联系电话</th>
                    <th class="head1 center">报名时间</th>
                    <th class="head1 center" width="5%">状态</th>
                    <th class="head1 center" width="26%">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user" varStatus="index">
                        <tr>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${index.index+1}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.name}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.sex}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
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
                                <c:if test="${user.politicalStatus==5}">
                                    中共预备党员
                                </c:if>
                                <c:if test="${user.politicalStatus==6}">
                                    共青团员
                                </c:if>
                                <c:if test="${user.politicalStatus==7}">
                                    民革党员
                                </c:if>
                                <c:if test="${user.politicalStatus==8}">
                                    民盟盟员
                                </c:if>
                                <c:if test="${user.politicalStatus==9}">
                                    民建会员
                                </c:if>
                                <c:if test="${user.politicalStatus==10}">
                                    民进会员
                                </c:if>
                                <c:if test="${user.politicalStatus==11}">
                                    农工党党员
                                </c:if>
                                <c:if test="${user.politicalStatus==12}">
                                    致公党党员
                                </c:if>
                                <c:if test="${user.politicalStatus==13}">
                                    九三学社社员
                                </c:if>
                                <c:if test="${user.politicalStatus==14}">
                                    台盟盟员
                                </c:if>
                            </td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                <c:if test="${fn:contains(user.nationality,'族')}">
                                    ${user.nationality}
                                </c:if>
                                <c:if test="${!fn:contains(user.nationality,'族')}">
                                    ${user.nationality}族
                                </c:if>
                            </td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.unit}</td>
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
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.job}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.mobile}</td>
                            <td width="12%" <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                <fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>

                            <td>
                                <c:if test="${user.status==1}">
                                    正常
                                </c:if>
                                <c:if test="${user.status==8}">
                                    在校
                                </c:if>
                                <c:if test="${user.status==7}">
                                    已结业
                                </c:if>
                            </td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}" class="stdbtn"
                                   title="详细信息">详细信息</a>
                                <!--如果是组织部，则不显示-->
                                <c:if test="${flag==true}">
                                    <a href="${ctx}/admin/jiaowu/user/toUpdateUserPerId.json?userId=${user.id}" class="stdbtn" title="修改一卡通编号">修改一卡通编号</a>
                                </c:if>
                                    <a href="${ctx}/admin/jiaowu//user/toUpdateUser.json?id=${user.id}" class="stdbtn btn_orange" title="修改学员信息">修改学员信息</a>
                                <a href="javascript:void(0)" class="stdbtn" onclick="deleteUser(${user.id},'${user.mobile}')">删除</a>
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