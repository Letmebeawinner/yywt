<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <% Date nowDate = new Date(); request.setAttribute("nowDate", nowDate); %>
    <title>添加供电局抄表数</title>
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
        <h1 class="pagetitle">添加供电局抄表数</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加供电局抄表数；<br>
            2.添加供电局抄表数：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；<br>
            3.<span style="color:red">用电量 = ( 本期度数 - 上期度数 ) x 倍率</span><br>
            4.<span style="color:red">电费 = ( 本期度数 - 上期度数 ) x 倍率 x 单价</span><br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>月份</label>
                    <span class="field">
                        <input type="text" name="monthTime" value="" class="longinput" id="monthTime"/>
                    </span>
                </p>
                <p>
                    <label>用电区域</label>
                    <span class="field">
                         <select name="typeId" class="longinput" onchange="queryPreviousDegrees(this);">
                               <option value="0">请选择</option>
                             <c:forEach items="${typeList}" var="eleType">
                                 <option value="${eleType.id}">${eleType.type}</option>
                             </c:forEach>
                         </select>
                    </span>
                </p>
                <p>
                    <label>二级用电区域</label>
                    <span class="field">
                         <select name="secTypeId" class="longinput" id="secTypeId"
                                 onchange="findClassTwo(this)">
                         </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 上期读数</label>
                    <span class="field">
                        <input type="text" name="previousDegrees" class="longinput" id="previousDegrees" onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 本期读数</label>
                    <span class="field">
                        <input type="text" name="currentDegrees" class="longinput" id="currentDegrees" onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 单价</label>
                    <span class="field">
                        <input type="text" name="price"  class="longinput" id="price" onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em> 倍率</label>
                    <span class="field">
                        <input type="text" name="rate"  class="longinput" onkeyup="value=value.replace(/[^\d]/g,'');calculation();" id="rate" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;用电量</label>
                    <span class="field">
                        <span id="degrees"></span>&nbsp;
                        <input type="hidden" value="" name="degrees" id="degreesInp"/>
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;电费</label>
                    <span class="field">
                        <span id="eleFee"></span>&nbsp;
                        <input type="hidden" value="" name="eleFee" id="eleFeeInp">
                    </span>
                </p>
                <p>
                    <label>&nbsp;&nbsp;备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="context" id="context" class="longinput"></textarea>
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