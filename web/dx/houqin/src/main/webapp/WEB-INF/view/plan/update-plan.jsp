<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改就餐计划</title>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#createTime',
                format: 'YYYY-MM-DD'
            });

            var levelOne = jQuery("input[name='levelOne']").val();
            var levelTwo = jQuery("input[name='levelTwo']").val();
            if (levelOne.length > 0) {
                jQuery("#classTypeId").val(levelOne);
                // 加载二级菜单
                level()
                jQuery("#classTypeId").change()
                jQuery("#classId").val(levelTwo)
            }

            var messOne = jQuery("input[name='messOne']").val();
            var messTwo = jQuery("input[name='messTwo']").val();
            if (messOne.length > 0) {
                jQuery("#messId").val(messOne)
                // 加载二级菜单
                messLevle()
                jQuery("#messId").change()
                console.log(jQuery("#areaIdSelect").val())
                jQuery("#areaIdSelect").val(messTwo)
                console.log(jQuery("#areaIdSelect").val())
            }
        });


        var level = function () {
            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery("#classId").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/houqin/getClassListByClassType.json',
                    data: {
                        "classTypeId": selectedClassTypeId
                    },
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            if (list != null && list.length > 0) {
                                var classstr = "";
                                for (var i = 0; i < list.length; i++) {
                                    classstr += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }
                                jQuery("#classId").html(classstr);
                            } else {
                                jQuery("#classId").html("<option value='0'>请选择</option>");
                            }
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
        }


        var messLevle = function () {
            jQuery("#messId").change(function () {
                var selectedId = jQuery(this).children('option:selected').val();
                jQuery("#areaIdSelect").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/houqin/listAreaJson.json',
                    data: {
                        "messId": selectedId
                    },
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            if (list != null && list.length > 0) {
                                var str = "";
                                for (var i = 0; i < list.length; i++) {
                                    str += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }
                                jQuery("#areaIdSelect").html(str);
                                console.log(str)
                            } else {
                                jQuery("#areaIdSelect").html("<option value='0'>请选择</option>");
                            }
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
        }

        function addFormSubmit() {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updatePlan.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href="${ctx}/admin/houqin/selectAllPlan.json"
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改就餐计划</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改就餐计划；<br>
            2.修改就餐计划：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；修改就餐计划<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>就餐时间</label>
                    <span class="field">
                         <input type="text" name="plan.planTime" readonly class="longinput" id="createTime" value="<fmt:formatDate value="${plan.planTime}" pattern="yyyy-MM-dd"/>"/>
                     </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>就餐要求</label>
                    <span class="field">
                        <input type="text" name="plan.requ" value="${plan.requ}" class="longinput" id="requ"/>
                        <input type="hidden" name="plan.id" value="${plan.id}" class="longinput" id="id"/>
                    </span>
                </p>
                <input type="hidden" name="levelOne" value="${plan.classType}">
                <input type="hidden" name="levelTwo" value="${plan.classId}">
                <%--接收食堂区域回显--%>
                <input type="hidden" name="messOne" value="${plan.messId}">
                <input type="hidden" name="messTwo" value="${plan.areaId}">
                <%-- <p>
                     <label><em style="color: red;">*</em>班次</label>
                     <span class="field">
                         <input type="text" name="plan.classes" value="${plan.classId}" class="longinput" id="classes"/>
                     </span>
                 </p>--%>
                <p>
                    <label><em style="color: red;">*</em>班型</label>
                    <span class="field">
                        <select id="classTypeId" name="plan.classType">
                            <option value="0">请选择</option>
                             <c:forEach items="${classTypeMap}" var="classType">
                                 <option value="${classType.key}">${classType.value}</option>
                             </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color:red;">*</em>班次</label>
                    <span class="field">
                       <select name="plan.classId" class="vam" id="classId">
								<option value="0">请选择</option>
							</select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>人数</label>
                    <span class="field">
                        <input type="text" name="plan.people" value="${plan.people}" class="longinput" id="people"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>标准</label>
                    <span class="field">
                        <input type="text" name="plan.standard" value="${plan.standard}" class="longinput"
                               id="standard"/>
                    </span>
                </p>
                <%-- <p>
                     <label><em style="color: red;">*</em>早中晚人数</label>
                     <span class="field">
                         <input type="text" name="plan.numberPeople" value="${plan.numberPeople}" class="longinput"
                                id="numberPeople"/>
                     </span>
                 </p>--%>

                <p>
                    <label><em style="color: red;">*</em>食堂</label>
                    <span class="field">
                        <select id="messId" name="plan.messId">
                            <option value="0">请选择</option>
                            <c:forEach items="${messes}" var="mess">
                                <option value="${mess.id}"
                                        <c:if test="${plan.messId==mess.id}">selected="selected"</c:if>
                                >${mess.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>食堂中的区域</label>
                    <span class="field">
                        <select id="areaIdSelect" name="plan.areaId">
                            <option value='0'>请选择</option>
                        </select>
                    </span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>