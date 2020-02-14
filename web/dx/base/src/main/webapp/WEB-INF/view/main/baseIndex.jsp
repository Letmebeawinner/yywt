<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>基础系统中心</title>
    <style>
        html,body,.bodywrapper{height: 100%;}
    </style>
</head>
<body>
    <div class="centercontent tables rightFrame">
        <span class="dis tac" style="padding-top: 160px;"><img src="/static/images/badge.png"></span>
        <p style="text-align: center;margin-top: 60px;font-size: 36px;color: #D20009;line-height: 40px;letter-spacing: 12px;">欢迎来到基础管理系统!</p>
    </div>
<%--<script>
    var GCD = function (w, h) {
        if (w % h) {
            return GCD(h, w % h)
        } else {
            return h
        }
    }
    var scale = function(w, h) {
        var gcd = GCD(w, h)
        return {
            w: w / gcd,
            h: h / gcd
        }
    }

    function height() {
        var that = this,
            w = 1738,
            h = 858,
            gcd = GCD(w, h),
            width = $('.centercontent').width() > 1738 ? 1738 : $('.centercontent').width();
        return width / (w / gcd) * h / gcd
    }
    var rh = height();
    $('.centercontent').css('height',rh+'px');
</script>--%>
</body>
</html>
