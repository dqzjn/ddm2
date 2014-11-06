<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html style="overflow-y:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">

<title>数据编辑系统</title>

<script src="<%=path%>/web/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/web/js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="<%=path%>/web/js/function.js" type="text/javascript"></script>
<link href="<%=path%>/web/style/layout.css" rel="stylesheet" type="text/css" />
<style>
body {
	background-repeat: repeat-x;
	background-color: #eaeaea;
}

.li {
	background-image: url(web/images/bg_navigation.png);
	background-repeat: repeat-x;
	height: 40px;
}

.innerHTML {
	color: #000;
	font-size: 18px;
	font-weight: 700;
	letter-spacing: -1px;
	padding-bottom: 5px;
	margin-left: 70px;
	padding-top: 10px
}

.footer {
	font-size: 12px;
	height: 47px;
	clear: both;
	border-top: 1px solid #d4d4d4;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	-moz-border-radius-bottomright: 10px;
	-moz-border-radius-bottomleft: 10px;
	text-shadow: 1px 1px 1px white;
	margin-top: 20px;
}

.footer .copyright {
	float: left;
	display: inline;
	margin: 10px 0 0 15px;
}
</style>
</head>
<script type="text/javascript">
/* 	$(document).ready(function(){
						
	});  */
	function iFrameHeight() { 
			var ifm= document.getElementById("mainframe"); 
			var subWeb = document.frames ? document.frames["mainframe"].document : ifm.contentDocument; 
			if(ifm != null && subWeb != null) { 
				ifm.height = subWeb.body.scrollHeight; 
			} 
		} 
	function li(id){
		if(id=='id1')
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/dataList.jsp'
		else if(id=='id2')
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/dataList1.jsp'
		else if(id=='id3'){
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/dataList2.jsp'
		}
	}
	function logout(){
		if(!confirm("是否确认退出 ？")){
			return false;
		}
		window.location='<%=request.getContextPath()%>/login_userLoginOut.action?temp='+Math.round(Math.random()*10000);
	}
	function changeRight(url){
        jQuery("#mainframe").attr("src",url);
        window.scrollTo(50,50); 
    }
</script>
<body id="homepage">
	<div class="header">
		<div class="header_img">
			<div>
				<div class="logo"></div>
			</div>
			<div id="systemmenu">
				<p class="left smltxt">
				<DIV style="margin-top:15px;">
					<a href="WEB-INF/main.jsp"><img src="web/images/top_icon1.png"
						border="0" title="首页" /> </a> <img src="web/images/top_icon5.png"
						onclick="logout()" style="cursor: pointer" border="0" title="退出" />
				</DIV>
				</p>
			</div>
		</div>
	</div>
	<div id="rightside">
		<div id="contentside">
			<iframe scrolling="no" src="" width="100%" frameborder="0"
				id="mainframe" onLoad="iFrameHeight()"> </iframe>
		</div>
		<div id="contentdetail" style="display: none;"></div>
		<div style="clear: both;"></div>
		<div class="footer">
			<span class="copyright">Copyright&copy; 北京智美点心科技有限公司</span>
		</div>
	</div>
	<div id="leftside">
		<div class="user">
			<p>
				欢迎登录：<br />
			</p>
			<p class="username"><%=session.getAttribute("username")%></p>
			<div style="float:left">
			<p class="userbtn"><a class="fancybox fancybox.ajax" title=""><span onclick="changeRight('user/updatePwd.jsp')" style="cursor: pointer">修改密码</span></a></p>
			</div>
		</div>
		<ul id="menu">
			<li class="li" id="id1" onclick="li(id)"><p class="innerHTML">数据管理(线下)</p>
			</li>
			<li class="li" id="id2" onclick="li(id)"><p class="innerHTML">备用</p>
			</li>
			<li class="li" id="id3" onclick="li(id)"><p class="innerHTML">备用</p>
			</li>
		</ul>
	</div>
	<div id="ajax-loader"
		style="display:none;position:fixed;right:0;bottom:0;">
		<img src="web/images/loading.gif" alt="loading" />
	</div>
	<div id="dialog"></div>
</body>
</html>