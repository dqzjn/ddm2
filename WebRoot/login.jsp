<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理登陆</title>
<link rel="stylesheet" href="css/reset.css" type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" />
<c:if test="${requestScope.message!=null}">
	<script type="text/javascript">
		alert('<c:out value="${requestScope.message}"></c:out>');
	</script>
</c:if>
<style>
.log_bt {
	background-image: url(images/loginBtn.jpg);
	height: 35px;
	border: 1px solid #20255d;
	font-size: 17px;
	font-family: microsoft yahei;
	color: #FFFFFF;
	width: 280px;
}
</style>
</head>

<body class="loginPage" onload="checkParent()" leftmargin="0"
	topmargin="0" marginwidth="0" marginheight="0">
	<form method="post" action="login.action" id="loginForm">
		<div class="loginLogoContainer">
			<div class="logo"></div>
		</div>
		<div class="loginContainer">
			<div class="content">
				<div class="loginBox">
					<table width="70%" border="0" cellspacing="0" cellpadding="0"
						class="loginTable">
						<tr>
							<td>用户名：</td>
						</tr>
						<tr>
							<td><input type="text" name="j_username" id="username"
								class="input_text" />
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
						<tr>
							<td>密码：</td>
						</tr>
						<tr>
							<td><input type="text" class="input_text" id="password1"
								onkeypress="javascript:hiddenPass(event)"
								onkeyup="this.value=this.value.replace(/./g,'*');" /> <input
								type="hidden" name="j_password" id="password" class="input_text" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="center"><input type="button" name="button"
								id="button" onClick="javascript:login()" class="log_bt" /></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="loginBottom">北京智美点心科技有限公司 &copy; Copyright 2014</div>
		</from>
</body>
</html>
<script type="text/javascript">
	String.prototype.trim = function() {
		return this.replace(/^\s+/, '').replace(/\s+$/, '');
	}
	function checkParent() {
		document.getElementById('loginForm').j_username.focus();
		if (window.parent.length > 0) {
			window.parent.location = "login.jsp";
		}
	}
	function login() {
		var usernameObj = document.getElementById("username");
		var passwordObj = document.getElementById("password1");
		//onFocus(usernameObj);
		//onFocus(passwordObj);
		if (usernameObj.value.trim() == "" || usernameObj.value.trim() == null) {
			alert("用户名不能为空！");
			usernameObj.focus();
			return;
		} else if (passwordObj.value.trim() == ""
				|| passwordObj.value.trim() == null) {
			alert("密码不能为空！");
			passwordObj.focus();
			return;
		} else {
			document.getElementById("loginForm").submit();
		}
	}
	function nologin() {
		var usernameObj = document.getElementById("username");
		var passwordObj = document.getElementById("password1");
		usernameObj.value = "";
		passwordObj.value = "";
	}
	function onFocus(obj) {
		if (obj.value.trim() == "" || obj.value.trim() == null) {
			obj.className = 'requiredInput';
			obj.title = "请输入密码";
		} else {
			obj.className = '';
			obj.title = "";
		}
	}
	function hiddenPass(e) {
		e = e ? e : window.event;
		var kcode = e.which ? e.which : e.keyCode;
		var pass = document.getElementById("password1");
		var j_pass = document.getElementById("password");
		if (kcode != 8 && kcode != 13) {
			var keychar = String.fromCharCode(kcode);
			j_pass.value = j_pass.value + keychar;
			j_pass.value = j_pass.value.substring(0, pass.length);
		} else if (kcode == 13) {
			login();
		}
	}
</script>
</html>
