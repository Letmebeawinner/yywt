<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改下乡信息</title>
    <script type="text/javascript" src="${ctx}/static/js/countryside.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#beginTime',
                format:'YYYY-MM-DD'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD'
            });
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">下乡信息修改</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改下乡帮扶信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updatecountryside">
                <p>
                    <label><em style="color: red;">*</em>下乡人员姓名</label>
                    <span class="field"><input type="text" name="countryside.name" id="name" class="longinput" value="${countryside.name}"/></span>
                </p>
                <p>
                    <input type="hidden" name="countryside.id" id="id" value="${countryside.id}"/>
                    <label><em style="color: red;">*</em>下乡地点</label>
                    <span class="field"><input type="text" value="${countryside.place}" name="countryside.place" id="place" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>下乡内容</label>
                    <span class="field">
                        <textarea name="countryside.content" id="content" cols="80" rows="5" class="longinput">${countryside.content}4</textarea>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" readonly name="countryside.beginTime" id="beginTime" class="longinput"value="<fmt:formatDate value="${countryside.beginTime}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>结束时间</label>
                    <span class="field"><input type="text" readonly name="countryside.endTime" id="endTime" class="longinput"value="<fmt:formatDate value="${countryside.endTime}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updatecountryside();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
</body>
</html>