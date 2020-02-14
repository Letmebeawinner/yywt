<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改用电量</title>
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
        <h1 class="pagetitle">修改用电量</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于更新用电量；<br>
            2.更新用电量信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；更新用电量信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <input type="hidden" name="electricity.id" id="electricityId" value="${electricity.id}">
                    <input type="hidden" name="electricity.userId" value="${userId}">
                    <input type="hidden"  id="secTypeId" value="${secType.id}">
                    <label><em style="color: red;">*</em>录入人</label>
                    <span class="field">
                        <input type="text" class="longinput" readonly="readonly" value="${userName}"/>
                    </span>
                </p>
                <p>
                    <label>月份</label>
                    <span class="field">
                        <input type="text" name="electricity.monthTime" value="${electricity.monthTime}" class="longinput" id="monthTime"/>
                    </span>
                </p>
                <p>
                    <label>用电区域</label>
                    <span class="field" >
                         <select disabled="disabled" name="electricity.typeId" style="width: 150px" onchange="findClass(this)">
                                 <c:forEach items="${typeList}" var="eleType">
                                    <option value="${eleType.id}"
                                            <c:if test="${electricity.typeId eq eleType.id}">selected="selected"</c:if>
                                    >${eleType.type}</option>
                                 </c:forEach>
                         </select>
                    </span>
                </p>
                <p>
                    <label>二级用电区域</label>
                    <span class="field">
                         <select disabled="disabled" name="electricity.secTypeId" class="longinput"  onchange="findClassTwo(this)">
                             <option value="${secType.id}">${secType.typeName}</option>
                         </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>上期读数</label>
                    <span class="field">
                        <input type="text" name="electricity.previousDegrees" onkeyup="clearNoNum(this);calculation();" value="${electricity.previousDegrees}" class="longinput"   id="previousDegrees"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>本期读数</label>
                    <span class="field">
                        <input type="text" name="electricity.currentDegrees" onkeyup="clearNoNum(this);calculation();"  value="${electricity.currentDegrees}" class="longinput"   id="currentDegrees"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单价</label>
                    <span class="field">
                        <input type="text" name="electricity.price" onkeyup="clearNoNum(this);calculation();" value="${electricity.price}"  class="longinput"   id="price"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>倍率</label>
                    <span class="field">
                        <input type="text" name="electricity.rate" onkeyup="value=value.replace(/[^\d]/g,'');calculation();" value="${electricity.rate}"  class="longinput"   id="rate"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>用电量</label>
                    <span class="field">
                        <span id="degrees">${electricity.degrees}</span>&nbsp;
                        <input type="hidden" name="electricity.degrees" class="longinput" value="${electricity.degrees}" id="degreesInp"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>电费</label>
                    <span class="field">
                        <span id="eleFee">${electricity.eleFee}</span>&nbsp;
                        <input type="hidden"  name="electricity.eleFee" value="${electricity.eleFee}"  class="longinput" id="eleFeeInp"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>备注</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="electricity.context" id="context"
                                  class="longinput">${electricity.context}</textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>