<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>导入成果</title>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">

        jQuery(function () {
            var errorInfo = "${errorInfo}";
            if (errorInfo !== null && errorInfo !== "") {
                alert(errorInfo);
            }
        });

        function importExcel() {
            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
                alert('请选择导入内容');
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">导入成果</h1>
        <span> <span style="color:red">说明</span><br/>
            1.本页面用于导入成果<br/>
            2.可下载excel模板,参照模板说明,填写成果信息,批量导入成果.<br>
            3.批量导入的<span style="color:red">课题为结项状态</span>.</span>
    </div>
    <!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">
                <form id="form1" class="stdform" method="post" action="${ctx}/admin/ky/doImportResultXls.json"
                      enctype="multipart/form-data">
                    <p><label>论文Excel模板说明</label>
                        <span class="field">
                        <a href="${ctx}/admin/ky/projectTemplateManagement/list.json"
                           style="color: red;" target="_blank">点击下载模板</a><br/>

                        1.论文名称, 必填.<br>
                        2.是否公开, 请填写 "公开", "未公开" 这几项中的一种,必填.<br>

                        3.刊物性质,请到<a href="${ctx}/admin/ky/listCategory.json" target="_blank" style="color: blue">
                        成果形式-成果类型</a>查询对应的标识，必填.<br>

                        4.级别, 请填写 "国家级", "省部级", "市级", "校级" 这几项中的一种,必填.<br>

                        5.发表刊物, 非必填项.<br>

                        6.刊号, 非必填项.<br>

                        7.发表时间, 请输入固定格式, 例如: "2018/01/01", 必填项.<br>

                        8.作者名称, 非必填项.<br>

                        9.课题组成员, 非必填项.<br>

                        10.字数, 必填项, 只能为数字.<br>

                        11.备注, 非必填项.<br>

                    </span></p>

                    <p><label>著作Excel模板说明</label>
                        <span class="field">
                        <a href="${ctx}/admin/ky/projectTemplateManagement/list.json"
                           style="color: red;" target="_blank">点击下载模板</a><br/>

                        1.著作名称, 必填.<br>

                        2.类别,请到<a href="${ctx}/admin/ky/listCategory.json" target="_blank" style="color: blue">
                        成果形式-成果类型</a>查询对应的标识，必填.<br>

                        3.级别, 请填写 "国家级", "省部级", "市级", "校级" 这几项中的一种,必填.<br>

                        4.出版社, 必填项.<br>

                        5.出版时间, 请输入固定格式, 例如: "2018/01/01", 必填项.<br>

                        6.主编, 非必填项.<br>

                        7.参编组成员, 非必填项.<br>

                        8.主编字数, 只能为数字, 非必填项.<br>

                        9.副主编, 非必填项.<br>

                        10.副主编字数, 只能为数字, 非必填项.<br>

                        11.参编章节信息, 必填.<br>

                        12.备注, 非必填项.

                    </span></p>

                    <p><label>课题Excel模板说明</label>
                        <span class="field">
                        <a href="${ctx}/admin/ky/projectTemplateManagement/list.json"
                           style="color: red;" target="_blank">点击下载模板</a><br/>

                        1.课题名称, 必填.<br>

                        2.级别, 请填写 "国家级", "省部级", "市级", "校级" 这几项中的一种,必填.<br>

                        3.课题发布单位, 非必填项.<br>

                        4.开始时间, 请输入固定格式, 例如: "2018/01/01", 必填项.<br>

                        5.结束时间, 请输入固定格式, 例如: "2018/01/01", 且结束时间不能早于开始时间 必填项.<br>

                        6.课题负责人, 必填项.<br>

                        7.课题组成员, 非必填项.<br>

                        8.字数, 只能为数字, 必填项.<br>

                        9.备注, 非必填项.

                    </span></p>

                    <p><label>内刊Excel模板说明</label>
                        <span class="field">
                        <a href="${ctx}/admin/ky/projectTemplateManagement/list.json"
                           style="color: red;" target="_blank">点击下载模板</a><br/>

                        1.内刊名称, 必填.<br>
                        2.是否公开, 请填写 "公开", "未公开" 这几项中的一种,必填.<br>

                        3.刊物性质,请到<a href="${ctx}/admin/ky/listCategory.json" target="_blank" style="color: blue">
                        成果形式-成果类型</a>查询对应的标识，必填.<br>

                        4.级别, 请填写 "国家级", "省部级", "市级", "校级" 这几项中的一种,必填.<br>

                        5.发表刊物, 非必填项.<br>

                        6.刊号, 非必填项.<br>

                        7.作者名称, 非必填项.<br>

                        8.课题组成员, 非必填项.<br>

                        9.字数, 必填项, 只能为数字.<br>

                        10.备注, 非必填项.<br>

                    </span></p>

                    <p><label>其他Excel模板说明</label>
                        <span class="field">
                        <a href="${ctx}/admin/ky/projectTemplateManagement/list.json"
                           style="color: red;" target="_blank">点击下载模板</a><br/>

                        1.成果名称, 必填.<br>
                        2.是否公开, 请填写 "公开", "未公开" 这几项中的一种,必填.<br>

                        3.刊物性质,请到<a href="${ctx}/admin/ky/listCategory.json" target="_blank" style="color: blue">
                        成果形式-成果类型</a>查询对应的标识，必填.<br>

                        4.级别, 请填写 "国家级", "省部级", "市级", "校级" 这几项中的一种,必填.<br>

                        5.发表刊物, 非必填项.<br>

                        6.刊号, 非必填项.<br>

                        7.作者名称, 非必填项.<br>

                        8.课题组成员, 非必填项.<br>

                        9.字数, 必填项, 只能为数字.<br>

                        10.备注, 非必填项.<br>

                    </span></p>


                    <p><label>上传</label> <span class="field">
                        <input id="myFile" type="file" value="" name="myFile"/> </span></p>
                    <br/>
                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="importExcel()" id="submitButton"
                            style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        提交
                    </button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div>
        <!-- #updates -->
    </div>
</div>
</body>
</html>