<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加出库</title>
    <script type="text/javascript">
        function doBatchOutStore() {

            var receiver=jQuery("#receiver").val();
            if(receiver==null||receiver==''){
                alert("领用人不能为空");
                return;
            } 
            var outStockType=jQuery("#outStockType").val();
            if(outStockType==null||outStockType==''){
                alert("出库类型不能为空");
                return;
            } 
            var source=jQuery("#source").val();
            if(source==null||source==''){
                alert("用途不能为空");
                return;
            }
            var flag = false;
            var idStr = "";
            var numStr = ""
            jQuery(".out-stock-num").each(function () {
                var num = jQuery(this).val();
                var max = jQuery(this).attr('max')
                var no = jQuery(this).attr('count')
                var dataId = jQuery(this).attr('data-id');
                if (num == null || num == '') {
                    alert("第" + no+" 行出库数量为空!");
                    flag=true;
                    return;
                }
                if (parseInt(num) > parseInt(max)) {
                    alert("第" + no +"行出库数量大于总库存量!");
                    flag=true;
                    return;
                }
                idStr += dataId + "#";
                numStr += num + "#";
            });
            if(flag){
                return;
            }
            jQuery("#storeHouseIdStr").val(idStr.substring(0,idStr.length-1));
            jQuery("#storeHouseNumStr").val(numStr.substring(0,numStr.length-1));
            
            
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addOutStockBatch.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryStorehouse.json";
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
        <h1 class="pagetitle">物品出库</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于物品出库信息；<br>
            2.根据要求填写出库信息；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>修改库存信息；<br>
        </div>
    </div>


    <div id="contentwrapper" class="contentwrapper">
        
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="addFormSubmit"  method="post">
                    <input type="hidden" name="storeHouseIdStr" id="storeHouseIdStr" value="">
                    <input type="hidden" name="storeHouseNumStr" id="storeHouseNumStr" value="">
                    <div class="disIb ml20 mb10">
                        <span class="vam"><em style="color: red;">*</em>出库类型 &nbsp;</span>
                        <label class="vam">
                           <select name="outStock.outStockType" id="outStockType">
                               <option value="0">--请选择--</option>
                               <c:forEach items="${storageTypeList}" var="type">
                                   <option value="${type.id}">--${type.name}--</option>
                               </c:forEach>
                           </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <label><em style="color: red;">*</em>领用人</label>
                        <span class="field">
                        <input type="text" name="outStock.receiver" class="longinput" id="receiver"/>
                    </span>
                    </div>

                    <div class="disIb ml20 mb10">
                        <label><em style="color: red;">*</em>用途</label>
                        <span class="field">
                        <input type="text" name="outStock.source" class="longinput" id="source"/>
                    </span>
                    </div> 
                    <div class="disIb ml20 mb10">
                        <label>备注</label>
                        <span class="field">
                      <input type="text"  name="outStock.context" id="context" class="longinput"/>
                    </span>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="doBatchOutStore()" class="stdbtn ml10 btn_orange">确定出库</a>
                </div>
            </div>
        </div>
       
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
                    <th class="head0 center">库房名称</th>
                    <th class="head0 center">商品编号</th>
                    <th class="head0 center">商品名称</th>
                    <th class="head0 center">当前库存</th>
                    <th class="head0 center">出库数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="storeHouse" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${storeHouse.storageName}</td>
                        <td>${storeHouse.code}</td>
                        <td>${storeHouse.name}</td>
                        <td>${storeHouse.num}</td>
                        <td>
                            <input type="number" class="out-stock-num" count="${status.count}" data-id="${storeHouse.id}" name="outStock.num" 
                                   min="1" step="1" max = "${storeHouse.num}"  onkeyup='this.value=this.value.replace(/\D/gi,"")'>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
</div>
</body>
</html>