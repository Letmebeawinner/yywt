<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加物品记录</title>
    <script type="text/javascript">
        function importExcel() {
            var myFile = jQuery("#myFile").val();
            var name = myFile.substring(myFile.lastIndexOf(".") + 1);
            if ("xls" != name) {
                alert("请使用xls格式的文件");
                return false;
            }
            if (myFile.length <= 0) {
                alert("请选择导入内容");
                return false;
            }
            jQuery("#importP").submit();
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">批量入库</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">使用说明</span><br>
                第一列：库房ID （<a href="${ctx }/admin/houqin/storageList.json" style="color: red;">点击查看库房信息</a>）<br>
                第二列：分类ID  （<a href="${ctx }/admin/houqin/queryAllGoodstype.json" style="color: red;">点击查看分类信息</a>）<br/>
                第三列：商品名称<br>
                第四列：商品数量<br>
                第五列：商品单位<br>
                第六列：规格型号<br>
                第七列：入库价格<br>
                tips： 上传请使用 xls 格式的excel 文件<br>
                      所有字段不能为空<br>
                      库房id,分类id,请先查看系统相应列表,填写对应id,否则无法匹配入库<br>
                      强烈建议,使用下方模板,且仔细阅读模板内第二工作表的使用说明<br>
                      
                （<a href="${ctx }/static/common/import_goods.xls" style="color: red;">点击下载模版</a>）<br>
        </div>
        <div style="margin-left: 20px;">
                <span class="ml10">
                    <form action="/admin/houqin/importGoodsBatch.json" method="post" id="importP" enctype="multipart/form-data">
                    <input id="myFile" type="file" value="" name="myFile"/>
                    </form>
                    <a href="javascript:void(0);" title="返回" class="stdbtn ml10 btn_orange"
                       onclick="importExcel()">提交</a>
                    <a href="javascript:history.go(-1);" title="返回" class="stdbtn ml10 btn_orange">返回</a>
                </span>
           
        </div>
    </div>
</div>
</body>
</html>