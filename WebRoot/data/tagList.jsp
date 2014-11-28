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
<title>标签管理</title>
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
.ui-jqgrid-sortable {font-size:16px;}  
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#gridTable").jqGrid({					
			url:'<%=request.getContextPath()%>/locker_queryTagTable.action?temp='+Math.round(Math.random()*10000),
			datatype: "json",
			height: 500,
			//width: 1005, 
			autowidth:true,
			colNames:['id','标签'],
			colModel:[
					{name:'id',index:'id', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'tag_name',index:'tag_name', width:500,align: 'center'}
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
	function addTag(){
		window.showModalDialog('<%=request.getContextPath()%>/data/editTag.jsp?temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:500px;');
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
		window.showModalDialog('<%=request.getContextPath()%>/locker_editTag.action?id='+id+'&temp='+new Date(),'', 'dialogWidth:700px;status:no;dialogHeight:500px;');
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
		var actionUrl = "<%=request.getContextPath()%>/locker_deleteTagById.action";  
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
			var tag_name = jQuery("#tag_name").val();
			var params = {  
	            "tag_name" : encodeURIComponent($.trim(tag_name))
			};							 
			 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
			 $.extend(postData, params);
			jQuery("#gridTable").jqGrid('setGridParam',
			{
				url:'<%=request.getContextPath()%>/locker_queryTagTable.action'
			}).trigger("reloadGrid", [{page:1}]); 
        } 
	//刷新
	function refreshIt(){
		gridSearch();
	}
	//清空
	function reset() {
		jQuery("#tag_name").val("");
	}
</script>
</head>
<body>
	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;标签：<input type="text" id="tag_name"
					name="tag_name" value="" class="input" style="width:150px;" />
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" />
					</td>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td><input id="add" type='button' value='添 加'
					onclick="addTag();" class='button_b' /> <input id="update"
					type='button' value='修 改' onclick='updateData()'
					class='button_b' /> <input id="delete" type='button' value='删 除'
					onclick='deleteData();' class='button_b' />
					 <input id="refresh" type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table id="gridTable" style="font-size: 24px;"></table>
					<div id="gridPager"></div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>