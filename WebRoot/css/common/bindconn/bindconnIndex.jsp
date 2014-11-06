<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
	<head>
		<title>海量数据管理</title>
		<%@ include file="/include/jscsslib.jsp"%>
		<%@ include file="/include/authbutton.jsp"%>
		<script type="text/javascript" src="<%=common_contextPath%>/js/extjs/RowEditor.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=common_contextPath%>/resources/css/RowEditor.css" />
		<link type="text/css" href="<%=request.getContextPath() %>/css/table.css" rel="stylesheet">
		<script type="text/javascript" src="bindconnAdd.js"></script>
		<script type="text/javascript" src="bindconn.js"></script>
		<script type="text/javascript" src="bindconnIndex.js"></script>
	</head>
	<body>
		<div id="detailbody">
			<table id="mytable" cellspacing="0"   width="100%"> 
				<tr> 
					<td class='mytd'  width="15%" align="center" style="border-right:1px solid #fff;">数据表名:</td> 
				    <td class='mytd'  width="25%" style="border-right:1px solid #fff;"><div id="div_entityTableName"></div></td>
				    <td class='mytd'  width="10%" align="right"><div id="subbutton"></div></td> 
				    <td class='mytd'  width="40%" >&nbsp;</td>
				    <td class='mytd'  width="20%" >&nbsp;</td>				    
				</tr>
			</table>
			<div id="bindConn-grid"></div>
		</div> 
	</body>
</html>
