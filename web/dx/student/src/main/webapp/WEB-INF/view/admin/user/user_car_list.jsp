<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学员车辆列表</title>
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
            jQuery("#cardNoId").val("${cardNoId}");
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">学员车辆列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示学员车辆列表.<br/>
					2.可通过班型、班次和学号查询对应的学员.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userCarList.json" method="get">
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
                        <span class="vam" style="width: 100px">是否有车牌</span>
                        <label class="vam">
                            <select name="cardNoId" class="vam" id="cardNoId">
                                <option value="0">请选择</option>
                                <option value="1">是</option>
                                <option value="2">否</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="" placeholder="请输入姓名">
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
                    <th class="head0 center">班级</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">联系电话</th>
                    <th class="head1 center">车牌号码</th>
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
                            <td>${user.sex}</td>
                            <td>${user.mobile}</td>
                            <td>${user.carNumber}</td>
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