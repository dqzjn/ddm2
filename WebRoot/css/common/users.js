/** 使用此选择用户功能的模块，需要实现deal_userinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选
 *  传递参数的方式为json格式
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.seluser = Ext.extend(Ext.Window,{
		constructor: function(config) {
			var tab = this.initTabData(config);
			var title = "用户";
			if(config.title!=null && config.title!=''){
				title = config.title;
			}
			customParams = config.customParams;
			if(null == customParams || customParams=='undefined'){
				customParams = "";
			}
			var win = this;
			var con = {
				items:[tab],
				title:title,
				customParams:customParams,
				height:400,
				buttonAlign : 'center',
				width:600,
				modal:true,
				frame:true,
				layout : 'fit',
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				closeAction : 'hide',
				buttons : [{
					text : '确定',
					handler : function (){
                           var objData;	
					       var userRole;
					       if(win.tab.activeTab.id==1){
                               objData=win.orggrid.selModel.getSelections();
			               }else{
			                	objData=win.rolegrid.selModel.getSelections(); 
			               }
			               if(objData==null || objData.length==0){
			                	Ext.MessageBox.alert("提示", "请选择一条记录");
			                	return;
			               }
			                //用到用户选择功能的模块需要自己实现
			               if(null != config.handler && config.handler!=''){
			                	config.handler(objData);
			               }else{
			                	deal_userinfo_common(objData);
			               }
					}
				}, {
					text : '取消',
					handler : function() {
						win.hide();
					}
				}]
			}	
	        TDMPlatform.common.seluser.superclass.constructor.call(this, con);
    	},
    	initTabData:function(config){
    		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
	           url:  common_contextPath+'/common/searchUsersCommon.action'
	       }),
	        reader: new Ext.data.JsonReader({
	            root: 'users'
	        },
		    [
	         	{name: 'userid', mapping: 'userid'},
		        {name: 'roleIds', mapping: 'roles'},
	         	{name: 'loginname', mapping: 'loginname'},
	        	{name: 'role.rolename', mapping: 'roleName'},
		        {name: 'orgIdLabel', mapping: 'orgIdLabel'},
	        	{name: 'orgNameLabel', mapping: 'orgNameLabel'},
	        	{name: 'organization.orgName', mapping: 'orgNameLabel'},
	        	{name: 'active', mapping: 'activeLabel'},
	        	{name: 'username', mapping: 'username'},
	        	{name: 'fixedPhone', mapping: 'fixedPhone'},
	         	{name: 'mobile', mapping: 'mobile'},
	        	{name: 'email', mapping: 'email'},
	            {name: 'userdesc', mapping: 'userdesc'},
	            {name: 'securityClass', mapping: 'securityClass'},
	            {name: 'securityClassLabel', mapping: 'securityClassLabel'}	      
		    ])
			,remoteSort : true
		   });
			store.setDefaultSort('userid', 'desc');
			var orgsm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});
			var rolesm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});			
			//根据组织查询的用户grid			
			var orggrid = new Ext.grid.GridPanel({
				cm : new Ext.grid.ColumnModel({
					columns : [ orgsm,{
								header : "用户登录名",
								dataIndex : 'loginname',
								autoWidth:true,
								sortable : true
							},{
								header : "所属组织",
								dataIndex : 'organization.orgName',
								hidden: true,
								width:100,
								sortable : true
							}, {
								header : "用户姓名",
								width:100,
								dataIndex : 'username',
								sortable : true
							}]
						}),
				 viewConfig: {
	                    forceFit:true
                },		
				columnLines : true,
				autoWidth : true,
				sm:orgsm,
				region: 'center',
				store : store,
				frame : true
			});
			//根据角色查询的用户grid			
			 var rolegrid = new Ext.grid.GridPanel({
					cm : new Ext.grid.ColumnModel({
						columns : [ rolesm,{
									header : "用户登录名",
									dataIndex : 'loginname',
									autoWidth:true,
									sortable : true
								}, {
									header : "所属角色",
									dataIndex : 'role.rolename',
									hidden : true,// TODO
									width:100,
									sortable : true
								}, {
									header : "用户姓名",
									dataIndex : 'username',
									width:100,
									sortable : true
								}]
					}),
					viewConfig: {
		                    forceFit:true
	                },		
					columnLines : true,
					sm:rolesm,
					autoWidth : true,
					region: 'center',
					store : store,
					frame : true
			});	
			//组织树
			var orgTree = new Ext.tree.TreePanel({
				title:'组织',
				split : true,
				width:150,
				border : true,
				autoScroll:true,
				collapsible : true,
				region:'west',
				listeners:{
    				"click": function(node, e){
						searchUsers(node.id,"org");
 					}
 				},
				root:new Ext.tree.AsyncTreeNode({
					text : '组织结构',
					id:-1
				}),
				loader : new Ext.tree.TreeLoader({
					dataUrl : common_contextPath+'/common/searchOrgCommon.action',
					listeners:{
           				"beforeload": function(treeLoader, node){
							treeLoader.baseParams = {orgId:node.id};
     					}
     				}
				})
			});
			//角色树
		   var roleTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
					dataUrl :common_contextPath+'/common/findAllRoleCommon.action'
				}),
				split : true,
				title:'角色',
				width:150,
				border : true,
				autoScroll:true,
				collapsible : true,
				region:'west',
				listeners:{
    				"click": function(node, e){
						searchUsers(node.id,"role");
 					}
 				},
				root:new Ext.tree.AsyncTreeNode({
					text : '角色',
					id:'-1'
				})
			});
			var orgFormPanel = new Ext.Panel({   
			    labelWidth: 100,   
			    border:false,   
			    height:300,
			    layout:'border',   
			    items: [orgTree,{
		    			xtype : 'hidden',
		    			name : 'users.userid',
		    			id : 'userid',
		    			value : ''
		    		},orggrid]   
			  }); 	

			var roleFormPanel = new Ext.Panel({   
			    labelWidth: 100,   
			    border:false,        
			    height:300,
			    layout:'border',
			    items: [roleTree,{
		    			xtype : 'hidden',
		    			name : 'users.userid',
		    			id : 'userid',
		    			value : '' 
		    		},rolegrid]   
			  });  
			function searchUsers(id,type){
	    		if(type=="org"){
			            var url = common_contextPath+"/common/searchUsersCommon.action";  
			       		orggrid.getStore().proxy.setUrl(url);
			       		orggrid.getStore().load({params:{orgId:id}});
			    }else if(type=="role"){
			    		url = common_contextPath+"/common/searchUsersByRoleIdCommon.action";
			    		rolegrid.getStore().proxy.setUrl(url);
			    		rolegrid.getStore().load({params:{roleId:id}});
			    } 
	    	}
		   var selecttab = new Ext.TabPanel({
		        width:450,
		        activeTab:0,
		        defaults:{autoHeight: true},
		        listeners:{
		        	tabchange:function(q,p){
		        		if(p.id==1){
		        			orgTree.getRootNode().expand(false,false);
		        		}else{
		        			roleTree.getRootNode().expand(false,false);
		        		}
		        	}
		        },
		        items:[
		            {items: orgFormPanel,id:'1', title: '按组织机构'},
		            {items: roleFormPanel,id:'2', title: '按角色'}
		        ]
		        
		    });
		    
		    this.orggrid = orggrid;
		    this.tab = selecttab;
		    this.rolegrid = rolegrid;
		    
		    return selecttab;
		    
    	},
    	clearSelect:function(){
    		if(null != this.orggrid.getSelectionModel().grid){
	    		this.orggrid.getSelectionModel().clearSelections();
	    	}
    		if(null != this.rolegrid.getSelectionModel().grid){
	    		this.rolegrid.getSelectionModel().clearSelections();
	    	}
    	}
    	
	});
