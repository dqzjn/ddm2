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
<title>数据管理</title>
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
	$(document).ready(function(){
		$("#gridTable").jqGrid({					
			url:'<%=request.getContextPath()%>/locker_queryDataImgTable.action?temp='+Math.round(Math.random()*10000),
			datatype: "json",
			height: 500,
			//width: 1005, 
			autowidth:true,
			colNames:['ID','标题','类型','内容','发布日期','url','图片','发布'],
			colModel:[
					{name:'ID',index:'ID', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'title',index:'title', width:150}, 
					{name:'type',index:'type', width:80,align: 'center'}, 
					{name:'imgUrl',index:'imgUrl', width:200,align: 'center'}, 
					{name:'collect_time',index:'collect_time', width:80,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},
					{name:'url',index:'url', width:270,align: 'center'}, 
					{name:'url',index:'url', width:335, align:'center',
							formatter: function(cellvalue, options, rowObject) {
								if(rowObject.url.indexOf(".")!=-1){
						  			return "<img src='"+rowObject.url+"' style='width:170px;' />" ;
								}else{
						  			return "<img src='' style='width:170px;' />" ;
								}
			  				}
			  			},
			  		{name:'data_sub',index:'data_sub', width:270,align: 'center',hidden:true}
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
			rowList:[5,10,20,100,500],
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
		//AutoSizeWindow();
		window.showModalDialog('<%=request.getContextPath()%>/locker_addDataImg.action?temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:570px;');
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
		var id = row.ID;//获取选中行的id属性
		if(row.data_sub==1){
			alert("数据已发布，不能修改!");
			return false;
		}
		var params = {"id": id};  
		var actionUrl = "<%=request.getContextPath()%>/locker_CheckedManyImgsByDataImgTableId.action";  
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
		    		window.showModalDialog('<%=request.getContextPath()%>/locker_editDataImg.action?id='+id+'&temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:570px;');
		    		refreshIt();
		      	}else{
		      		alert("多图文数据暂不支持修改，敬请等待。。。");
		      	}
		    }  
		});
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
		var actionUrl = "<%=request.getContextPath()%>/locker_deleteDataImgById.action";  
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
			var data_title = jQuery("#data_title").val();
			var start_date = jQuery("#start_date").val();
			var end_date = jQuery("#end_date").val();
			var type = jQuery("#type").val();
			var edit_date = jQuery("#edit_date").val();
			var data_sub = jQuery("#data_sub").val();
			var collect_website = jQuery("#collect_website").val();
			var params = {  
	            "data_title" : encodeURIComponent($.trim(data_title)),
	            "start_date" : encodeURIComponent($.trim(start_date)),
	            "end_date" : encodeURIComponent($.trim(end_date)),
	            "type" : encodeURIComponent($.trim(type)),
	            "edit_date" : encodeURIComponent($.trim(edit_date)),
	            "data_sub" : encodeURIComponent($.trim(data_sub)),
	            "collect_website" : encodeURIComponent($.trim(collect_website))
			};							 
			 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
			 $.extend(postData, params);
			jQuery("#gridTable").jqGrid('setGridParam',
			{
				url:'<%=request.getContextPath()%>/locker_queryDataImgTable.action'
			}).trigger("reloadGrid", [{page:1}]); 
        } 
	//刷新
	function refreshIt(){
		gridSearch();
	}
	//清空
	function reset() {
		jQuery("#data_title").val("");
		jQuery("#start_date").val("");
		jQuery("#end_date").val("");
		jQuery("#data_type").val("");
		jQuery("#edit_date").val("");
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
		var actionUrl = "<%=request.getContextPath()%>/locker_insertDataImg.action";  
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
	//批量修改时间
	function updateTime(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
		window.showModalDialog('<%=request.getContextPath()%>/data/editTimePage.jsp?ids='+ids+'&temp='+new Date(),'', 'dialogWidth:300px;status:no;dialogHeight:200px;');
		gridSearch();
	}
</script>
</head>
<body>
	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;标题：<input type="text" id="data_title"
					name="data_title" value="" class="input" style="width:150px;" />&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;日期：<input type="text" id="edit_date"
						name="edit_date" value="" class="input"
						onClick="WdatePicker()"
						readonly="readonly" style="width:150px;" /></td>
						<td>&nbsp;&nbsp;<input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" />
					</td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;类型：<select id="type" name="type" style="width:150px;">
								<option value="" selected="selected">--请选择--</option>
								<option value="joke">搞笑</option>
								<option value="news">新闻</option>
								<option value="film">影视</option>
								<option value="entertainment">娱乐</option>
								<option value="game">游戏</option>
						</select>
						<td>&nbsp;&nbsp;同步：<select id="data_sub" name="data_sub" style="width:150px;">
								<option value="">全部</option>
								<option value="0">未发布</option>
								<option value="1">已发布</option>
						</select>
						<c:if test="${sessionScope.USER_ORG=='0'}"><td>&nbsp;&nbsp;来源：<select id="collect_website" name="collect_website" style="width:150px;">
								<option value="">全部</option>
								<option value="百度">百度</option>
								<option value="网易">网易</option>
						</select></td></c:if>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td><input id="add" type='button' value='添 加'
					onclick="addData();" class='button_b' /> <input id="update"
					type='button' value='修 改' onclick='updateData()'
					class='button_b' /> <input id="delete" type='button' value='删 除'
					onclick='deleteData();' class='button_b' />
					<c:if test="${sessionScope.USER_ORG=='0'}"><input id="delete" type='button' value='插入数据库' onclick='insertData();' class='button_b' /></c:if>
					<c:if test="${sessionScope.USER_ORG=='0'}"><input id="delete" type='button' value='批量修改时间' onclick='updateTime();' class='button_b' /></c:if>
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