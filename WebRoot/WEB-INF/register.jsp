<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>

<s:property value="result" />
<s:form action="register" >
    <s:textfield name="worker_phone" label="电话" />
	<s:textfield name="worker_name" label="姓名" />
	<s:textfield name="identitycard_no" label="身份证" />
	<s:textfield name="area_no" label="地点" />
	<s:password name="password" label="密码" />
	<s:submit value="注册" />
</s:form>

</body>
</html>
