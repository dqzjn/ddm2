<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>

<s:property value="result" />
<s:form action="login" >
	<s:textfield name="worker_phone" label="联系电话" />
	<s:password name="password" label="设置密码" />
	<s:submit value="登录" />
</s:form>

</body>
</html>
