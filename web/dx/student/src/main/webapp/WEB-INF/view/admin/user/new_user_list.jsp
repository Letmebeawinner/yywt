<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>预审学员列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
//          		jQuery("#classId").html("");
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
// 			jQuery("#classId option[value='"+classId+"']").attr("selected",true);
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

        function deleteUser(id, status) {
            if (status != 1) {
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
            } else {
                jAlert('请联系管理员', '提示', function () {
                });
            }
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

        function setLunXun(id, obj) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/setNewUserToLunXun.json?id=' + id,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery(obj).remove();
                        jQuery("#not_pass_" + id).html("单位已通过");
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
            var classId = jQuery("#classId").val();
            if (classId == null || classId == "" || classId == "0") {
                jAlert("请选择班次!", '提示', function () {
                });
                return;
            }
            <!--jQuery("#searchForm").prop("action", " ${ctx}/admin/jiaowu/user/exportNewUserList.json");-->
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/newUserListExcelTwo.json?classId=" + classId+"&classTypeId="+classTypeId);
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/newUserList.json");
        }

        function oneButtonPass(userId) {
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/oneButtonPass.json?userId=' + userId,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery("#passspan" + userId).html("<a href='javascript:void(0)' onclick='return false;' class='stdbtn' title='已设置为正式学员'>已设置为正式学员</a>");
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

        function updateUser(userId, status) {
            if (status != 1) {
                window.location.href = "${ctx}/admin/jiaowu/user/toUpdateUser.json?id=" + userId;
            } else {
                jAlert('请联系管理员', '提示', function () {
                });
            }
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">预审学员列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示预审学员列表.<br/>
					2.可通过点击"通过"将预审学员转到学员处审批.<br/>
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

                    <%--<div class="disIb ml20 mb10">
                       <span class="vam">学号 &nbsp;</span>
                       <label class="vam">
                       <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学号">
                       </label>
                   </div>--%>
                    <%-- <div class="disIb ml20 mb10">
                         <span class="vam">时间 &nbsp;</span>
                         <label class="vam">
                             <select name="time" class="vam" id="time" >
                                 <option value="0">请选择</option>
                                 <option value="1">上半年轮巡人员统计</option>
                                 <option value="2">一年内轮巡人员统计</option>
                                 <option value="3">五年内轮巡人员统计</option>
                             </select>
                         </label>
                     </div>--%>

                    <!-- <div class="disIb ml20 mb10">
                      <span class="vam">邮箱 &nbsp;</span>
                      <label class="vam">
                      <input id="email" style="width: auto;" name="email" type="text" class="hasDatepicker" value="" placeholder="请输入邮箱">
                      </label>
                  </div>
                   <div class="disIb ml20 mb10">
                      <span class="vam">手机号 &nbsp;</span>
                      <label class="vam">
                      <input id="mobile" style="width: auto;" name="mobile" type="text" class="hasDatepicker" value="" placeholder="请输入手机号">
                      </label>
                  </div> -->
                    <!--  <div class="disIb ml20 mb10">
                         <span class="vam">是否报到 &nbsp;</span>
                         <label class="vam">
                         <select name="baodao" class="vam" id="baodao">
                              <option value="-1">所有</option>
                              <option value="0">未报到</option>
                              <option value="1">已报到</option>
                          </select>
                         </label>
                     </div> -->
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
                </div>
                <div class="disIb ml20 mb10">
                    <span class="vam">总人数: &nbsp;</span>
                    <label class="vam">
                        ${totalNum}人
                    </label>
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

                    <%-- <th class="head0 center">序号</th>
                     <th class="head0 center">班型</th>
                     <th class="head0 center">班次</th>
                     <th class="head0 center">姓名</th>

                     <th class="head0 center">性别</th>
                     <th class="head0 center">出生日期</th>
                     <th class="head0 center">年龄</th>
                     <th class="head0 center">单位</th>
                     <th class="head0 center">职务和职称</th>
                     <th class="head0 center">级别</th>
                     <th class="head0 center">政治面貌</th>
                     <th class="head0 center">学历</th>
                     <th class="head0 center">联系电话</th>
                     <th class="head0 center">报名时间</th>
                     <th class="head1 center">身份证号</th>--%>
                    <th class="head0 center">序号</th>
                    <%-- <th class="head0 center">班型</th>
                     <th class="head0 center">班次</th>--%>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">政治面貌</th>
                    <%--<th class="head0 center">年龄</th>--%>
                    <%--<th class="head0 center">学历</th>--%>
                    <th class="head0 center">民族</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center" width="15%">学员单位及职务职称（全称）</th>
                    <th class="head0 center">联系电话</th>
                    <th class="head0 center">审核状态</th>
                    <th class="head0 center">操作</th>
                    <%--<th class="head1 center">身份证号</th>--%>
                    <%--<th class="head1 center">学号</th>--%>

                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user" varStatus="index">
                        <tr>


                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${index.index+1}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.classTypeName}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.className}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.name}</td>

                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.sex}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.birthday}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.age}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.unit}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.job}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
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
                                </td>

                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.mobile}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.idNumber}</td>--%>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${index.index+1}</td>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.classTypeName}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.className}</td>--%>
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
                            </td>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.age}</td>--%>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.qualification}</td>--%>
                            <td
                                    <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.nationality}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.unit}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.job}</td>
                            <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.mobile}</td>
                            <c:if test="${user.status == 2}">
                                <td
                                        <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>
                                        id="not_pass_${user.id}">未通过
                                </td>
                            </c:if>
                            <c:if test="${user.status == 3}">
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>单位已通过</td>
                            </c:if>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.idNumber}</td>--%>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.studentId}</td>--%>
                            <td class="center" id="operationtd${user.id}">
                                <a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}"
                                   class="stdbtn btn_orange" title="详细信息">详细信息</a>
                                <span id="passspan${user.id}">
                                        <c:if test="${user.status==2}">
                                            <a href="javascript:void(0)" onclick="setLunXun(${user.id}, this)"
                                               class="stdbtn btn_orange" title="通过">通过</a>
                                        </c:if>
                                        <c:if test="${usermanager==true}">
                                            <a href="javascript:void(0)" onclick="oneButtonPass(${user.id})"
                                               class="stdbtn btn_orange" title="一键通过">一键通过</a>
                                        </c:if>
                                        </span>
                                <a href="javascript:void(0)" onclick="deleteUser(${user.id},${user.status})"
                                   class="stdbtn" title="删除">删除</a>
                                <a href="javascript:updateUser('${user.id}','${user.status}')" class="stdbtn btn_orange"
                                   title="修改学员信息">修改学员信息</a>
                            </td>
                                <%--<td id="statustd${user.id}">--%>
                                <%--<c:if test="${user.status==3}">--%>
                                <%--<a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="单位已通过">单位已通过</a>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${user.status==5}">--%>
                                <%--<a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部驳回">组织部已驳回</a>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${user.status==6}">--%>
                                <%--<a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人驳回">个人已驳回</a>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${user.status==1}">--%>
                                <%--<a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="个人通过">个人已通过</a>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${user.status==4}">--%>
                                <%--<a href="javascript:void(0)" onclick="return false;" class="stdbtn" title="组织部通过">组织部已通过</a>--%>
                                <%--</c:if>--%>
                                <%--</td>--%>
                                <%--</c:if>--%>

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