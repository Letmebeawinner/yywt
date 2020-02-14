<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加用电量</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/admin/js/electricity.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加用电量</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加电表；<br>
            2.添加电表：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加电表<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>月份</label>
                    <span class="field">
                        <input type="text" name="electricity.monthTime" value="" class="longinput" id="monthTime"/>
                    </span>
                </p>
                <p>
                    <label>用电区域</label>
                    <span class="field">
                         <select name="electricity.typeId" class="longinput" onchange="findClass(this)">
                             <option value="0">请选择</option>
                                 <c:forEach items="${typeList}" var="eleType">
                                     <option value="${eleType.id}"
                                             <c:if test="${'其他区域' eq eleType.type}">selected="selected"</c:if>>${eleType.type}</option>
                                 </c:forEach>
                         </select>
                    </span>
                </p>
                <p>
                    <label>二级用电区域</label>
                    <span class="field">
                         <select name="electricity.secTypeId" class="longinput" id="secTypeId"
                                 onchange="findClassTwo(this)">
                         </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>上期读数</label>
                    <span class="field">
                        <input type="text" name="electricity.previousDegrees" onkeyup="clearNoNum(this);calculation();" class="longinput" id="previousDegrees"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>本期读数</label>
                    <span class="field">
                        <input type="text" name="electricity.currentDegrees" onkeyup="clearNoNum(this);calculation();" class="longinput" id="currentDegrees"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>倍率</label>
                    <span class="field">
                        <input type="text" name="electricity.rate" onkeyup="value=value.replace(/[^\d]/g,'');calculation();" class="longinput" id="rate"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单价</label>
                    <span class="field">
                        <input type="text" name="electricity.price" onkeyup="clearNoNum(this);calculation();" class="longinput" id="price"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>用电量</label>
                    <span class="field">
                        <span id="degrees"></span>&nbsp;
                        <input type="hidden" name="electricity.degrees" class="longinput " id="degreesInp"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>电费</label>
                    <span class="field">
                        <span id="eleFee"></span>&nbsp;
                        <input type="hidden" name="electricity.eleFee" class="longinput" id="eleFeeInp"/>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="electricity.context" id="context"
                                   class="longinput"></textarea>
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