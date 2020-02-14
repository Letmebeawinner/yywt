<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加就餐计划</title>
    <script type="text/javascript">
        jQuery(function () {

            laydate.skin('molv');
            laydate({
                elem: '#createTime',
                format: 'YYYY-MM-DD'
            });

            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery("#classId").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/houqin/getClassListByClassType.json',
                    data: {
                        "classTypeId": selectedClassTypeId
                    },
                    type: 'post',
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
                            }else{
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


            jQuery("#messId").change(function () {
                var selectedId = jQuery(this).children('option:selected').val();
                jQuery("#messArea").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/houqin/listAreaJson.json',
                    data: {
                        "messId": selectedId
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            if (list != null && list.length > 0) {
                                var str = "";
                                for (var i = 0; i < list.length; i++) {
                                    str += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }
                                jQuery("#messArea").html(str);
                            } else {
                                jQuery("#messArea").html("<option value='0'>请选择</option>");
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


        });
            function addFormSubmit() {

                var requ = jQuery("#requ").val();
                if (requ == null || requ == '') {
                    alert("请填写就餐要求");
                    return;
                }
                var classTypeVal = jQuery("select[name='plan.classType'] option:selected").val()
                if (classTypeVal == 0) {
                    alert("请选择班型");
                    return;
                }
                var classes = jQuery("#classId").val();
                if (classes == null || classes == 0) {
                    alert("请选择班次");
                    return;
                }
                var people = jQuery("#people").val();
                if (people == null || people == '') {
                    alert("请填写人数");
                    return;
                } else {
                    var mat = /^([1-9]\d*|0)?$/;
                    if (!mat.test(people)) {
                        alert("请填写人数");
                        return;
                    }
                }
                var standard = jQuery("#standard").val();
                if (standard == null || standard == '') {
                    alert("请填写标准");
                    return;
                }
                var messId = jQuery("#messId").val();
                if (messId == null || messId == 0) {
                    alert("请选择食堂");
                    return;
                }

                var messArea = jQuery("#messArea").val();
                if (messArea == null || messArea == 0) {
                    alert("请选择食堂区域");
                    return;
                }
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/addPlan.json",
                    data: jQuery("#addFormSubmit").serialize(),
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
                            window.location.href = "${ctx}/admin/houqin/selectAllPlan.json"
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
        <h1 class="pagetitle">添加就餐计划</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加就餐计划；<br>
            2.添加就餐计划：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加就餐计划<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>就餐时间${plan.createTime}</label>
                    <span class="field">
                         <input type="text" name="plan.planTime" class="longinput" id="createTime" readonly/>
                     </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>就餐要求</label>
                    <span class="field">
                        <input type="text" name="plan.requ" class="longinput" id="requ"/>
                    </span>
                </p>
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
                       <select name="plan.classId" class="vam" id="classId" >
								<option value="0">请选择</option>
							</select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>人数</label>
                    <span class="field">
                        <input type="text" name="plan.people" class="longinput" id="people"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>标准</label>
                    <span class="field">
                        <input type="text" name="plan.standard" class="longinput" id="standard"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>食堂</label>
                    <span class="field">
                        <select id="messId" name="plan.messId">
                            <option value="0">请选择</option>
                            <c:forEach items="${messes}" var="mess">
                                <option value="${mess.id}">${mess.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>食堂中的区域</label>
                    <span class="field">
                        <select id="messArea" name="plan.areaId">
                            <option value='0'>请选择</option>
                        </select>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>