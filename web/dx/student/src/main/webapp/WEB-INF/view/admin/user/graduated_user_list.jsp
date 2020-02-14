<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>历届学员列表</title>
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
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/graduatedUserList.json");
        }

        function useExcelByCondition(){
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userListExcelByGraCondition.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/graduatedUserList.json");
        }



    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">历届学员列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示历届学员列表.<br/>
					2.可通过班型、班次和学号查询对应的学员.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/graduatedUserList.json" method="post">
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
                            </td>

                            <td
                                    <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.nationality}</td>
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