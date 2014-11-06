<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jqGrid/themes/smoothness/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jqGrid/css/jqgrid.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/grid.locale-cn.js"></script>
<script type="text/javascript" language="javaScript">
	var t = document.documentElement.clientWidth;
	window.onresize = function() {
		if (t != document.documentElement.clientWidth) {
			t = document.documentElement.clientWidth;
		}
	}

	//取得页面大小
	function getPageSize() {
		var winW, winH;
		if (window.innerHeight) {// all except IE 
			winW = window.innerWidth;
			winH = window.innerHeight;
		} else if (document.documentElement
				&& document.documentElement.clientHeight) {// IE 6 Strict Mode 
			winW = document.documentElement.clientWidth;
			winH = document.documentElement.clientHeight;
		} else if (document.body) { // other 
			winW = document.body.clientWidth;
			winH = document.body.clientHeight;
		} // for small pages with total size less then the viewport  
		return {
			WinW : winW,
			WinH : winH
		};
	}

	function countlen(textarea) {
		if (textarea.value.length > 250) {
			alert("最多为250个字符,字符输入超出限制!")
			textarea.value = textarea.value.substring(0, 250)
		}
		return true
	}
</script>