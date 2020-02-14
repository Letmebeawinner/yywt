<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>本班学员报修列表</title>
    <script type="text/javascript">
        jQuery(function(){
            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":selectedClassTypeId
                    },
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        if(result.code=="0"){
                            var list=result.data;
                            var classstr="<option value=''>请选择</option>";
                            if(list!=null&&list.length>0){

                                for(var i=0;i<list.length;i++){
                                    classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                            jQuery("#classId").val("${classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${classTypeId}");
            jQuery("#classTypeId").trigger("change");
        });


        function searchForm() {
            jQuery("#searchForm").submit();
        }


        function delRepair(id) {
            if (confirm("确定删除这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delRepair.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.href = "/admin/houqin/queryAllRepair.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("#status").val(-1);
            jQuery("#typeId").val("");
        }

        function updateCheck(id, type) {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/checkRepair.json",
                data: {
                    "repair.id": id,
                    "repair.check": type
                },
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllRepair.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function toAppraise(id) {
            window.location.href = "/admin/houqin/toAppraise.json?repairId=" + id;
        }
    </script>
    <script type="text/javascript">
        var data = data;
        var functionsIds;

        function addSystemUsers(id) {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/repairGetSysuserList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择功能列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/toAddRepairPeairPeople.json",
                                    data: {'peairPeopleId': functionsIds, 'id': id},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            alert("操作成功!");
                                            window.location.reload();
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }

                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '30%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "800px");
                    jQuery('#popup_message').css("max-height", "600px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">本班学员报修列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看本班学员报修列表；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

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
                    <th class="head0 center">报修编号</th>
                    <th class="head0 center">报修物品</th>
                    <th class="head0 center">报修时间</th>
                    <th class="head0 center">维修时间</th>
                    <th class="head0 center">报修人</th>
                    <th class="head0 center">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${repairList}" var="repair" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${repair.number}</td>
                        <td>${repair.name}</td>
                        <td><fmt:formatDate value="${repair.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${repair.repairTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${repair.sysUser.userName}</td>

                        <td>
                            <c:if test="${repair.status==0}"><font style="color: red">未处理</font></c:if>
                            <c:if test="${repair.status==1}"><font style="color:orange">正维修</font></c:if>
                            <c:if test="${repair.status==2}"><font style="color:green">已维修</font></c:if>
                            <c:if test="${repair.status==3}"><font style="color:#ccc">已取消</font></c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>