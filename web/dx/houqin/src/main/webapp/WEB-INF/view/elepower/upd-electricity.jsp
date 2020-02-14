<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改供电局抄表数</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/admin/js/elepower.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改供电局抄表数</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改供电局抄表数；<br>
            2.修改供电局抄表数：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="id" id="elePowerId" value="${elePower.id}">
                <input type="hidden" name="secTypeId" id="secTypeId" value="${secType.id}">
                <p>
                    <label>月份</label>
                    <span class="field">
                        <input type="text" name="monthTime" value="${elePower.monthTime}" class="longinput"
                               id="monthTime"/>
                    </span>
                </p>
                <p>
                    <label>用电区域</label>
                    <span class="field">
                         <select name="typeId" class="longinput">
                             <c:forEach items="${typeList}" var="eleType">
                                 <c:if test="${elePower.typeId == eleType.id}">
                                     <option value="${eleType.id}"
                                             <c:if test="${elePower.typeId == eleType.id}">selected="selected"</c:if>>${eleType.type}</option>
                                 </c:if>
                             </c:forEach>
                         </select>
                    </span>
                </p>
                <p>
                    <label>二级用电区域</label>
                    <span class="field">
                         <select disabled="disabled" name="secTypeId" class="longinput">
                             <option value="${secType.id}">${secType.typeName}</option>
                         </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 上期读数</label>
                    <span class="field">
                        <input type="text" name="previousDegrees" value="${elePower.previousDegrees}" class="longinput"
                               id="previousDegrees" onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 本期读数</label>
                    <span class="field">
                        <input type="text" name="currentDegrees" value="${elePower.currentDegrees}" class="longinput"
                               id="currentDegrees" onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 单价</label>
                    <span class="field">
                        <input type="text" name="price" value="${elePower.price}" class="longinput" id="price"
                               onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 倍率</label>
                    <span class="field">
                        <input type="text" name="rate" value="${elePower.rate}" class="longinput"
                               onkeyup="value=value.replace(/[^\d]/g,'');calculation();" id="rate" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;用电量</label>
                    <span class="field">
                        <span id="degrees">${elePower.degrees}</span>&nbsp;
                        <input type="hidden" value="${elePower.degrees}" name="degrees" id="degreesInp"/>
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;电费</label>
                    <span class="field">
                        <span id="eleFee">${elePower.eleFee}</span>&nbsp;
                        <input type="hidden" value="${elePower.eleFee}" name="eleFee" id="eleFeeInp">
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="context" id="context"
                                   class="longinput">${elePower.context}</textarea>
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