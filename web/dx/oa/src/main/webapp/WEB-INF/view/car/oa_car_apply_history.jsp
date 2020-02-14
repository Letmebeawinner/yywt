<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用车申请详情</title>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script>
        var id = "${carApply.driver}";
        jQuery(function() {
            queryDateNameById(id, "driver", 5);
        });

        function printDocument() {
            jQuery("#print").hide();
            jQuery(".printHide").hide();
            jQuery(".header").hide();
            jQuery(".iconmenu").hide();
            jQuery(".centercontent").css("marginLeft","0");
            jQuery("textarea").css("min-height","102px");
            jQuery("td").css("border","1px solid #999");


            // return;
            window.print();
            jQuery("#print").show();
            jQuery(".printHide").show();
            jQuery(".header").show();
            jQuery(".iconmenu").show();
            jQuery(".centercontent").css("marginLeft","181px");
            jQuery("td").css("border","1px solid #ddd");
            jQuery("textarea").css("height", "100%");

        }
    </script>

</head>
<body>
<div class="centercontent user-car">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list " style="text-align: center;">
            用车申请详情
        </h1>
    </div>
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "carApplyDeptAudit" method="post">
                    <table border="1">
                        <caption align="bottom">备注：如需使用公务用车，原则上提前一天填写该表，经同意后方可使用。该审批单由出车驾驶员、办公室各留存一份，办公室每月底对驾驶员出车情况进行汇总统计</caption>
                        <tr>
                            <td class="pt" style="width:20%;">申请人</td>
                            <td class="pt" style="width:15%;"><span>${applyName}</span></td>
                            <td class="pt" style="width:15%;">用车时间</td>
                            <td style="width:50%;">
                                <span><fmt:formatDate value="${carApply.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> 到 <fmt:formatDate value="${carApply.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt" >用车事由</td>
                            <td colspan="3"><span>${carApply.reason}</span></td>
                        </tr>
                        <tr>
                            <td class="pt" >线路、公里数</td>
                            <td colspan="3"><span>${carApply.distance}</span></td>
                        </tr>
                        <tr>
                            <td class="pt" >处室领导意见</td>
                            <td colspan="3"><span>${carApply.departmentOption}</span></td>
                        </tr>
                        <tr>
                            <td class="pt" >办公室管理员意见</td>
                            <td colspan="3"><span>${carApply.officeLeaderOption}</span></td>
                        </tr>
                        <tr>
                            <td class="pt" >所用车辆驾驶员</td>
                            <td class="pt" colspan="3">
                                <span id = "driver">
                                    ${carApply.driver}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt">所用车辆车牌号</td>
                            <td colspan="3"><span>${carApply.carNo}</span></td>
                        </tr>
                        <tr>
                            <td class="pt">实际用车时间</td>
                            <td colspan="3"><span><fmt:formatDate value="${carApply.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> 到 <fmt:formatDate value="${carApply.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></td>
                        </tr>
                        <tr class = "printHide">
                            <td class="pt">审核状态</td>
                            <td colspan="3">
                                    <span>
                                        <c:if test = "${carApply.audit == 1}">
                                            审批已通过
                                        </c:if>
                                        <c:if test = "${carApply.audit == 0}">
                                            审核中
                                        </c:if>
                                        <c:if test = "${carApply.audit == 2}">
                                            已拒绝
                                        </c:if>
                                    </span>
                            </td>
                        </tr>
                    </table>
            </div>
            </form>
            <div class="buttons printHide" style="text-align: center;margin-top:40px;">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
            </div>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>

        </div><!--subcontent-->
    </div>
</div>
<script type = "text/javascript">
    function printDocument() {
        jQuery(".printHide").hide();
        jQuery("#print").hide();
        jQuery(".header").hide();
        jQuery(".iconmenu").hide();
        jQuery(".centercontent").css("marginLeft","0");
        jQuery("textarea").css("min-height","85px");
//        jQuery("#pos0").css("marginLeft","-181px");
        jQuery(".kg-img-div").css({
            "left": "calc(64% - 0px)",
            transform: "translateY(-50%)"
        });
        //jQuery("td").css("border","1px solid #999");
        // return;
        window.print();
        jQuery("#print").show();
        jQuery(".printHide").show();
        jQuery(".header").show();
        jQuery(".iconmenu").show();
        jQuery(".centercontent").css("marginLeft","181px");
        //jQuery("#pos0").css("left","64%");
        //jQuery("td").css("border","1px solid #ddd");
        jQuery("textarea").css("height", "100%");
    }
</script>
</body>
</html>