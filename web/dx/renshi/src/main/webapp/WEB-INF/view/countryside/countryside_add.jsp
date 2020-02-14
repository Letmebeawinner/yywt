<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>下乡管理</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/admin/js/countryside.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新增帮扶人员</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的帮扶人员<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addcountryside">
                <p>
                    <label><em style="color: red;">*</em>下乡人员姓名</label>
                    <span class="field"><input type="text" name="countryside.name" id="name"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>下乡地点</label>
                    <span class="field"><input type="text" name="countryside.place" id="place"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>下乡内容</label>
                    <span class="field">
                        <textarea name="countryside.content" id="content" class="longinput" cols="80" rows="5"
                                  style="text-align: left"></textarea>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" readonly name="countryside.beginTime" id="beginTime"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>结束时间</label>
                    <span class="field"><input type="text" readonly name="countryside.endTime" id="endTime"
                                               class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addcountrysideFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    /**
     * 清空表单
     */
    function resetData() {
        jQuery(".longinput").val("");
    }

    /**
     * 时间控件
     */
    jQuery(function () {
        laydate.skin('molv');
        laydate({
            elem: '#beginTime',
            istoday: false,
            format: 'YYYY年MM月'
        });
        laydate({
            elem: '#endTime',
            istoday: false,
            format: 'YYYY年MM月'
        });
    });
</script>
</body>
</html>