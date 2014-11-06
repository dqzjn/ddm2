<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
	<head>
		<title>页面模板管理</title>
		<%@ include file="../../include/jscsslib.jsp"%>
		<%@ include file="../../include/authbutton.jsp"%>
		<script type="text/javascript" src="roles.js"></script>
		<script type="text/javascript" src="users.js"></script>
		<script type="text/javascript" src="depts.js"></script>
		<script type="text/javascript">
			function getrole(){
				var role = new TDMPlatform.common.deptrole({
					isSingle:true,
					title:'单位'
				});
				role.show();
			}
			
			
			
			
			
			
			Ext.onReady(function(){
			//数据
			var store= new Ext.data.Store({  
            	reader:new Ext.data.JsonReader({
	            	root:'root',
	            	idProperty:'courseName',
	            	totalProperty:'totalCount',
	            	fields:[
	            		{name:'courseName'}
	            	]
	            }),
				proxy:new Ext.data.HttpProxy({
					url:"LoginServlet"
                }),
                sortInfo:{field: "courseName",direction:"ASC"},
                remoteSort:true
            }); 
            
			//定义行模式
			var sm = new Ext.grid.CheckboxSelectionModel();
			sm.handleMouseDown = Ext.emptyFn;
			
			//主面板grid
			var ztevalgrid=new Ext.grid.GridPanel({
            	title:'专题班评估',
                renderTo:Ext.getBody(),
            	width:450,
            	height:300,
            	store:store,
            	sm:sm,
            	loadMask: true,
		        columnLines: true,
		        singleSelect: true,
            	columns:[
                	new Ext.grid.RowNumberer(),
               	 	sm,
                	{id:'courseName',header:'专题班名称',width:100,dataIndex:'courseName',sortable:true}
                ],
             	tbar:[
                	{
                		id:'pingJia',
	                	text:'评价',
	                	handler:function(){
	                    	b_evalue.show();
	                	},
	                	iconCls : "icon-add"
                	}
                ],
                bbar:new Ext.PagingToolbar({
               		pageSize:25,
                	store:store,
                	displayInfo:true
               })
          	});
             checkbutton();                   
            store.load({
        		params:{start:0,limit:15}
    	    });
    	});
			
			
			
			
			
			
		</script>
	</head>
	<body>
			<input type="button" value="选择用户" onclick="getrole(false)">
	</body>
</html>