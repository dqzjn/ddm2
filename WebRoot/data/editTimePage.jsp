<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<title>修改时间</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jqGrid/themes/smoothness/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jqGrid/css/jqgrid.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<c:if test="${requestScope.message!=null}">
	<script type="text/javascript">
	  	 		     alert('<c:out value="${requestScope.message}"></c:out>');
	  	 		     window.close();
	  			</script>
</c:if>
<style>
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
</style>
<script type="text/javascript">
function saveTime(){
	var ids ='<%=request.getParameter("ids") %>';
	var edit_date = document.getElementById("edit_date").value;
	var params = {"edit_date" : edit_date };  
	var actionUrl = "<%=request.getContextPath()%>/locker_saveParams.action?ids="+ids+"";
		$.ajax({
			url : actionUrl,
			type : "post",
			data : params,
			dataType : "json",
			cache : false,
			error : function(textStatus, errorThrown) {
				alert("系统ajax交互错误: " + textStatus.value);
			},
			success : function(data, textStatus) {
				if (data.result == 'success') {
					alert("时间更改成功！");
					window.close();
				} else if(data.result == 'empty'){
					alert("更新数据null 请重新操作！");
				}else {
					alert("时间更改失败！");
				}
			}
		});
	}
</script>
</head>
<body>

	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;时间：<input type="text" id="edit_date"
					name="edit_date" value="" class="input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					readonly="readonly" style="width:150px;" /></td>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
			<br/><br/><br/><br/><br/><br/>
				<td colspan="4" align="center"><input type="button"
					id="submitBtn" onclick="saveTime()" value="保 存" class="form_bt_orange" /> <input
					type="button" value="取 消" class="form_bt_orange"
					onclick="window.close();" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table id="gridTable"></table>
					<div id="gridPager"></div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>