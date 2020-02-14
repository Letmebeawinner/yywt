<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>璁块棶璺緞閿欒-404</title>
</head>
<body>
    <div class="contentwrapper padding10">
        <div class="errorwrapper error403">
            <div class="errorcontent">
                <h1>璁块棶璺緞涓嶆纭�</h1>

                <p>鎵句笉鍒伴〉闈紝鎮ㄨ闂殑閾炬帴涓嶅瓨鍦�</p>
                <br />
                <button class="stdbtn btn_black" onclick="history.back()">杩斿洖涔嬪墠鐨勯〉闈�</button> &nbsp;
                <button onclick="location.href='${basePath}/admin/index.json'" class="stdbtn btn_orange">绯荤粺棣栭〉</button>
            </div><!--errorcontent-->
        </div><!--errorwrapper-->
    </div>
</body>
</html>
