<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>一键分房</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">一键分房</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.分房规则：按照男女比例，2人住一间为标准，进行随机分房，持有一卡通遇到客房部进行授权<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>入住时间</label>
                    <span class="field">
                        <input type="text" name="electricity.degrees"  class="longinput"   id="degrees"/>
                    </span>
                </p>
                <p>
                    <label>班级人数</label>
                    <span class="field">
                        50人
                    </span>
                </p>
                <p>
                    <label>空间房间20间</label>
                    <span class="field">
                        20人
                    </span>
                </p>
                <p>
                    <label>退房时间</label>
                    <span class="field">
                         <input type="text" class="longinput" >
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">确认</button>
                    <button class="submit" onclick="addFormSubmit();return false;">取消</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>