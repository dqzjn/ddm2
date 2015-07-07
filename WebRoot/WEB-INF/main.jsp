<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	background-color: #ffffff;
}

.li {
	background-image: url(web/images/bg_navigation.png);
	background-repeat: repeat-x;
	height: 50px;
	width: 148px;
	margin-left:8px;
	text-align: left;
}

.innerHTML {
	color: #696969;
	font-size: 23px;
	font-weight: 700;
	letter-spacing: -1px;
	padding-bottom: 5px;
	padding-top: 13px;
	margin-left: 23px;
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
				//ifm.height = subWeb.body.scrollHeight; 
			} 
		} 
	function li(id){
		if(id=='id1'){
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/dataList.jsp';
		}
		else if(id=='id2'){
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/tagList.jsp';
		}
		else if(id=='id3'){
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/wallPaperList.jsp';
		}
		else if(id=='id4'){
			document.getElementById("mainframe").src = '<%=request.getContextPath()%>/data/notifyList.jsp';
			//document.getElementById("mainframe").scrolling='no';
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
				<div class="logo">Hi~<%=session.getAttribute("username")%>!&nbsp;&nbsp;&nbsp;&nbsp;欢迎登录潘多拉</div>
			</div>
			<div id="systemmenu">
				<p class="left smltxt">
				<DIV style="margin-top:15px;margin-right: 15px;"><br/>
					<img src="<%=request.getContextPath()%>/web/images/changePwd.png"
						onclick="changeRight('user/updatePwd.jsp')" style="cursor: pointer;" border="0" title="修改密码" />
					<img src="<%=request.getContextPath()%>/web/images/quit.png"
						onclick="logout()" style="cursor: pointer;margin-left: 20px;margin-right: 15px;" border="0" title="退出" />
					<br/>
				</DIV>
				</p>
			</div>
		</div>
	</div>
	<div id="rightside">
		<div id="contentside">
			<iframe scrolling="no" src="<%=request.getContextPath()%>/data/dataList.jsp" width="100%" height="90%" frameborder="0"
				id="mainframe" onLoad="iFrameHeight()"> </iframe>
		</div>
		<div id="contentdetail" style="display: none;"></div>
		<div style="text-align: center;padding-bottom: 10px;padding-top: 10px;">
			<span class="copyright">Copyright&copy; 北京智美点心科技有限公司</span>
		</div>
	</div>
	<div id="leftside">
<!-- 		<div class="user"> -->
<!-- 			<p> -->
<!-- 				欢迎登录：<br /> -->
<!-- 			</p> -->
<!-- 			<p class="username"><%=session.getAttribute("username")%></p> -->
<!-- 			<div > -->
<!-- 			<p class="userbtn"> -->
<!-- 				<a title=""  onclick="changeRight('user/updatePwd.jsp')" style="cursor: pointer">修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				<a title=""  onclick="logout()" style="cursor: pointer">退出</a> -->
<!-- 			</p> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<ul id="menu">
			<li class="li" style="cursor: pointer" id="id1" onclick="li(id)"><p class="innerHTML">数据管理</p>
			</li>
			<c:if test="${sessionScope.USER_ORG=='0'}"><li class="li" style="cursor: pointer" id="id2" onclick="li(id)"><p class="innerHTML">标签管理</p>
			</li></c:if>
			<c:if test="${sessionScope.USER_ORG=='0'}"><li class="li" style="cursor: pointer" id="id3" onclick="li(id)"><p class="innerHTML">壁纸管理</p>
			</li></c:if>
			<c:if test="${sessionScope.USER_ORG=='0'}"><li class="li" style="cursor: pointer" id="id4" onclick="li(id)"><p class="innerHTML">通知管理</p>
			</li></c:if>
		</ul>
	</div>
	<div id="ajax-loader"
		style="display:none;position:fixed;right:0;bottom:0;">
		<img src="web/images/loading.gif" alt="loading" />
	</div>
	<div id="dialog"></div>
</body>
</html>