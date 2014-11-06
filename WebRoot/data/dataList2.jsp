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
<link href="style/css.css" rel="stylesheet" type="text/css" />
<title>数据管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js_css_image/lhgdialog/lhgcore.lhgdialog.min.js?skin=mac"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/js/mypage.js" />
<c:if test="${requestScope.message!=null}">
	<script type="text/javascript">
	  	 		     alert('<c:out value="${requestScope.message}"></c:out>');
	  	 		     window.close();
	  			</script>
</c:if>

<script type="text/javascript">
	$(document).ready(function(){
													
	}); 
	
	//添加
	function addProinfo(){
		$.dialog({
	        title: '工程信息添加',
	        content: 'url:<%=request.getContextPath()%>/project/addproject.jsp',
	        okVal: false,//确定按钮文字
	        cancelVal: false,//取消按钮文字
	        min: true, //是否显示最小化按钮
	        max: false,//是否显示最大化按钮
	        fixed: false,//开启静止定位
	        lock: true,//开启锁屏
	        focus: true,//弹出窗口后是否自动获取焦点（4.2.0新增）
	        time: null,//设置对话框显示时间
	        resize: true,//是否允许用户调节尺寸
	        drag: true,//是否允许用户拖动位置
	        cache: false,//是否缓存iframe方式加载的窗口内容页
	        width: '600px',
	        height: 350
		});
	}
	
	//修改
	function updateProinfo(){
		var ids= $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
		if (!ids) {
		    alert("请先选择记录!");  
		    return false;  
		}
		if(ids.indexOf(",")!=-1){
			  alert("只能选择一条记录!");  
		        return false; 
		}
		var row = jQuery("#gridTable").jqGrid('getRowData',ids);//获取选中行.
		var id = row.ID;//获取选中行的id属性
		$.dialog({
	        title: '工程信息修改',
	        content: 'url:proinfoAction!initProinfo.action?id='+id+'&temp='+Math.round(Math.random()*10000),
	        okVal: false,//确定按钮文字
	        cancelVal: false,//取消按钮文字
	        min: true, //是否显示最小化按钮
	        max: false,//是否显示最大化按钮
	        fixed: false,//开启静止定位
	        lock: true,//开启锁屏
	        focus: true,//弹出窗口后是否自动获取焦点（4.2.0新增）
	        time: null,//设置对话框显示时间
	        resize: true,//是否允许用户调节尺寸
	        drag: true,//是否允许用户拖动位置
	        cache: false,//是否缓存iframe方式加载的窗口内容页
	        width: '600px',
	        height: 350
		});
	}
	
	//delete
	function deleteProinfo(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
       
		if(!confirm("是否确认删除 ？")){
			return false;
		}
		var params = {"ids": ids};  
		var actionUrl = "proinfoAction!deleteProjinfoByIds.action";  
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
		      	if(data.result=='success'){
		      		alert("删除成功！");       
		      		refreshIt();    
		      	}else{
		      		alert("删除失败！");
		      	}
		    }  
		});
	}
	
	
	
	
	function winopen(targeturl){
		newwin=window.open("","","scrollbars,status=yes");
		if(document.all){
		newwin.moveTo(0,0)//新窗口的坐标
		newwin.resizeTo(screen.width,screen.height)
		}
		newwin.location=targeturl;
		}
		
	
 
    //查询
		function gridSearch(){
			var projectname = jQuery("#projectname").val();
			var params = {  
	            "projectname" : encodeURIComponent($.trim(projectname))
			};							 
			 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
			 $.extend(postData, params);
			jQuery("#gridTable").jqGrid('setGridParam',
			{
				url:'proinfoAction!queryProinfolist.action'
			}).trigger("reloadGrid", [{page:1}]); 
        } 
	//刷新
	function refreshIt(){
		document.location.href='<%=request.getContextPath()%>
	/project/projectlist.jsp';
	}
	//清空
	function reset() {
		jQuery("#data_title").val("");
		jQuery("#start_date").val("");
		jQuery("#end_date").val("");
		jQuery("#data_Type").val("");
	}
</script>
</head>
<body>

	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;标题222：<input type="text" id="data_title"
					name="data_title" value="" class="input" style="width:150px;" />&nbsp;&nbsp;
					<td>&nbsp;&nbsp;日期222：<input type="text" id="start_date"
						name="start_date" value="" class="input" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" style="width:150px;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input
						type="text" id="end_date" name="end_date" value="" onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" class="input"
						style="width:150px;" />&nbsp;&nbsp;
						<td>&nbsp;&nbsp;类型222：<select id="data_Type" name="data_Type"  style="width:150px;">
													<option value="" selected="selected">--请选择--</option>
													<option value="joke">搞笑</option>
													<option value="joke">新闻</option>
													<option value="joke">电影</option>
													<option value="joke">游戏</option>
												</select>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" /></td>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td><input id="add" type='button' value='添 加'
					onclick="addProinfo();" class='button_b' /> <input id="update"
					type='button' value='修 改' onclick='updateProinfo()'
					class='button_b' /> <input id="delete" type='button' value='删 除'
					onclick='deleteProinfo();' class='button_b' /> <input id="refresh"
					type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table id="gridTable"></table>
					<div id="gridPager"></div></td>
			</tr>
		</table>
	</form>
</body>
</html>