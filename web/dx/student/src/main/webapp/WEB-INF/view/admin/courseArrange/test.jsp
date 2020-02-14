
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jQuery鼠标经过淡入显示提示框 演示</title>
<!-- <script type="text/JavaScript" src="../js/jquery-1.7.2.min.js"></script> -->
<style type="text/css">
.div {
	border: 1px solid #0000FF;
	float: left;
	width: 200px;
	height: 200px;
	margin: 10px;
}

div#tip {
	position: absolute;
	width: 100px;
	height: auto;
	border: 1px solid #00CC66;
}

.div1 {
	border: 0;
	cursor: pointer;
	width: auto;
	height: auto;
}
</style>
</head>

<body>

	<div id="tip" style="display: none">提示内容</div>
	<div class="div"></div>
	<div class="div"></div>
	<div class="div div1">enenba</div>
	<script type="text/javascript">
		jQuery(function(){
			jQuery('.div').hover(function() {
				jQuery('#tip').fadeIn('slow');
			});

			jQuery('.div').mousemove(function(e) {
				var top = e.pageY + 5;
				var left = e.pageX + 5;
				jQuery('#tip').css({
					'top' : top + 'px',
					'left' : left + 'px'
				});
			});

			jQuery('.div').mouseout(function() {
				jQuery('#tip').hide();
			});

		});
	</script>
</body>
</html>