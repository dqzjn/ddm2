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
<title>通知管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jqGrid/themes/cupertino/jquery-ui-1.7.2.custom.css" />
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
text-overflow : ellipsis;
}
.button_b{height: 18px;width: 36px;background-image: url(<%=request.getContextPath()%>/images/inputBg.png) ;background-size:cover;background-color: transparent;border: none ;}
.button_b1{height: 18px;width: 89px;background-image: url(<%=request.getContextPath()%>/images/inputBg2.png) ;background-size:cover;background-color: transparent;border: none ;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#gridTable").jqGrid({					
			url:'<%=request.getContextPath()%>/locker_queryNotifyTable.action?temp='+Math.round(Math.random()*10000),
			datatype: "json",
			height: 500,
			//width: 1005, 
			autowidth:true,
			colNames:['id','标题','内容','类型','URL','应用','开始时间','结束时间','优先级','图标','次数','修改时间','状态','状态'],
			colModel:[
					{name:'id',index:'id', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'title',index:'title', width:100,align: 'center'}, 
					{name:'content',index:'content', width:80,align: 'center'}, 
					{name:'type',index:'type', width:300,align: 'center'}, 
					{name:'url',index:'url', width:270,align: 'center'}, 
					{name:'application',index:'application', width:270,align: 'center'}, 
			  		{name:'start_time',index:'start_time', width:100,align: 'center'},
			  		{name:'end_time',index:'end_time', width:80,align: 'center'},
					{name:'level',index:'level', width:80,align: 'center'},
					{name:'icon',index:'icon', width:80,align: 'center'},
					{name:'times',index:'times', width:80,align: 'center'},
					{name:'lastModified',index:'lastModified', width:80,align: 'center'},
					{name:'data_sub',index:'data_sub', width:80,align: 'center',hidden:true},
					{name:'data_sub1',index:'data_sub1', width:80,align: 'center',
						formatter: function(cellvalue, options, rowObject) {
							if(rowObject.data_sub=='审核通过'){
					  			return "<p style=\"color: #FFC125;font-size: 16px;\">审核通过</p>" ;
							}else if(rowObject.data_sub=='审核中'){
					  			return "<p style=\"color: green;font-size: 16px;\">审核中</p>" ;
							}else if(rowObject.data_sub=='审核未通过'){
					  			return "<p style=\"color: red;font-size: 16px;\">审核未通过</p>" ;
							}
		  				}}
			],
			shrinkToFit:false,
			sortname:'id',
			sortorder:'desc',
			viewrecords:true,
			multiselect: true, // 是否显示复选框
			multiboxonly : true, 
			//gridview: true,  //提升速度
			rownumbers: false,//显示行号
			rownumWidth: 30, //行号的宽度
			rowNum:10,
			rowList:[10,20,50,100,200,500],
			toolbar: [false,"top"],
			jsonReader: {
				root:"rows",		// 数据行（默认为：rows）
				page: "page",  	// 当前页
				total: "total",  // 总页数
				records: "records",  // 总记录数
				repeatitems : false		// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			prmNames:{rows:"rows",page:"page",sort:"sidx",order:"sord",search:"search"},
			pager:"#gridPager",
			caption: "数据列表"									
	});
				
	 jQuery("#gridTable").jqGrid('navGrid','#gridPager',{add:false,edit:false,del:false,search:false,refresh:false});
		jQuery("#gridTable").jqGrid('navButtonAdd','#gridPager',
					{ 	
					caption: "列状态",                          
					title: "Reorder Columns",                           
					onClickButton : function (){                               
					jQuery("#gridTable").jqGrid('columnChooser');                           
					}
		}); 
											
	}); 
	
	//添加
	function addData(){
		window.showModalDialog('<%=request.getContextPath()%>/data/editNotify.jsp?temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:570px;');
		gridSearch();
	}
	
	//修改
	function updateData(){
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
		var id = row.id;//获取选中行的id属性
		if(row.data_sub=='审核通过'){
			alert("数据已发布，不能修改!");
			return false;
		}
		window.showModalDialog('<%=request.getContextPath()%>/locker_editNotify.action?id='+id+'&temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:570px;');
		refreshIt();
	}
	
	//delete
	function deleteData(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
       
		if(!confirm("是否确认删除 ？")){
			return false;
		}
		var params = {"ids": ids};  
		var actionUrl = "<%=request.getContextPath()%>/locker_deleteNotifyById.action";  
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

	//查询
		function gridSearch(){
			var title = jQuery("#title").val();
			var start_time = jQuery("#start_time").val();
			var end_time = jQuery("#end_time").val();
			var data_sub = jQuery("#data_sub").val();
			var params = {  
	            "title" : encodeURIComponent($.trim(title)),
	            "start_time" : encodeURIComponent($.trim(start_time)),
	            "end_time" : encodeURIComponent($.trim(end_time)),
	            "data_sub" : encodeURIComponent($.trim(data_sub))
			};							 
			 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
			 $.extend(postData, params);
			jQuery("#gridTable").jqGrid('setGridParam',
			{
				url:'<%=request.getContextPath()%>/locker_queryNotifyTable.action'
			}).trigger("reloadGrid", [{page:1}]); 
        } 
	//刷新
	function refreshIt(){
		gridSearch();
	}
	//清空
	function reset() {
		jQuery("#p_name").val("");
		jQuery("#start_date").val("");
		jQuery("#end_date").val("");
		jQuery("#imageEXT").val("");
		jQuery("#data_sub").val("");
	}
	//插入云数据库
	function insertData(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
       
		if(!confirm("是否确认插入云数据 ？")){
			return false;
		}
		var params = {"ids": ids};  
		var actionUrl = "<%=request.getContextPath()%>/locker_insertNotify.action";  
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
		      		alert("插入成功！");       
		      		refreshIt();    
		      	}else{
		      		alert("插入失败！");
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
				<td>&nbsp;&nbsp;标题：<input type="text" id="title"
					name="title" value="" class="input" style="width:150px;" />&nbsp;&nbsp;
					&nbsp;&nbsp;发布时间：<input type="text" id="start_time"
						name="start_time" value="" class="input"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end_time\')}'})"
						readonly="readonly" style="width:150px;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input
						type="text" id="end_time" name="end_time" value=""
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_time\')}'})"
						readonly="readonly" class="input" style="width:150px;" />
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同&nbsp;&nbsp;步：&nbsp;&nbsp;<select id="data_sub" name="data_sub" style="width:150px;">
							<option value="">全部</option>
							<option value="0">未发布</option>
							<option value="1">已发布</option>
					</select>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" /><br /><br />
					</td>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td><input id="add" type='button' value='添 加'
					onclick="addData();" class='button_b' /> <input id="update"
					type='button' value='修 改' onclick='updateData()'
					class='button_b' /> <input id="delete" type='button' value='删 除'
					onclick='deleteData();' class='button_b' />
					<c:if test="${sessionScope.USER_ORG=='0'}"><input id="delete" type='button' value='插入数据库' onclick='insertData();' class='button_b1' /></c:if>
					 <input id="refresh" type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
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