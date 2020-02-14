<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案详情</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            jQuery("#typeId").val('${archive.typeId}')
        });
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">档案详情</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于归入档案；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>档案分类</label>
                    <span class="field">
                        <select name="archive.typeId" id="typeId" class="longinput" disabled>
                            <option value="0">--请选择--</option>
                            <c:forEach items="${linkedHashMapList}" var="arch">
                                <option value="${arch.id}">${arch.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档号</label>
                    <span class="field">${archive.danghao}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档案件号</label>
                    <span class="field">${archive.jianhao}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>责任者</label>
                    <span class="field">${archive.author}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>题名</label>
                    <span class="field">${archive.autograph}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文号</label>
                    <span class="field">${archive.wenhao}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        ${archive.archivedate}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>页数</label>
                    <span class="field">${archive.pages}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>机构或问题</label>
                    <span class="field">${archive.orginzation}&nbsp;</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>下载附件:</label>
                    <span class="field">
                        <a href="${archive.file}" class="stdbtn" title="下载附件" download="">下载附件</a>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        ${archive.description} &nbsp;
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <input class="reset radius2" type="reset" onclick="history.back();" value="返回"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>