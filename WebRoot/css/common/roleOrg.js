/** 使用此选择用户功能的模块，需要实现deal_userinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选
 *  传递参数的方式为json格式
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.selRoleOrg = Ext.extend(Ext.Window,{
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
					       var userRole;
                               objData=win.orggrid.selModel.getSelections();
			                	objDataRole=win.rolegrid.selModel.getSelections(); 
			               if((objData==null || objData.length==0)&&(objDataRole==null || objDataRole.length==0)){
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
	        TDMPlatform.common.selRoleOrg.superclass.constructor.call(this, con);
    	},
    	initTabData:function(config){
    	//////////
//    	var recordIds=new Array();// 选中的Record主键列id列表    
//		var recordsChecked=new Array();// 选中的Record列表  
		////////
			var orgStore = new Ext.data.Store({
		       proxy: new Ext.data.HttpProxy({
		           url: common_contextPath+'/organization/findOraByPage.action'
		       }),
		       reader: new Ext.data.JsonReader({
		            root: 'organizations',
		            totalProperty: 'count'
		        },[{
		        	name: 'orgName', mapping: 'orgName'
		        },{
		        	name: 'orgId', mapping: 'orgId'
		        },{
		        	name: 'orgDesc', mapping: 'orgDesc'
		        }
		        	
		        ]),
		        remoteSort: true
		    });
		    orgStore.load();
    		var roleStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		           url:  common_contextPath+'/common/searchRolesCommon.action'
		       }),
		       autoLoad:true,
		        reader: new Ext.data.JsonReader({
		            root: 'roles'
		        },
			    [
		         	{name: 'roleid', mapping: 'roleid'},
			        {name: 'rolename', mapping: 'rolename'},
			        {name: 'rolelabel', mapping: 'rolelabel'},
		         	{name: 'roledesc', mapping: 'roledesc'}	      
			    ])
				,remoteSort : true
			 });

			var orgsm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});
			var rolesm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});			
			//查询的部门的grid			
			var orggrid = new Ext.grid.GridPanel({
				cm : new Ext.grid.ColumnModel({

					columns : [ orgsm,{
								header : "部门名称",
								dataIndex : 'orgName',
								autoWidth:true,
								sortable : true
							},{
								dataIndex : 'orgId',
								hidden: true,
								width:100,
								sortable : true
							}
							]
						}),
				 viewConfig: {
	                    forceFit:true
                },		
				columnLines : true,
				autoWidth : true,
				sm:orgsm,
				region: 'center',
				store : orgStore,
				frame : true,
				tbar: (['部门名称：', {
	                id:'org_name',
	                pressed: true,
	                xtype:'textfield',
	                enableToggle:true
	            	},'-', {
	                id:'dept_selectBtn',
	                pressed: true,
	                enableToggle:true,
	                text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
	               	toggleHandler: function(){
					            		orgStore.baseParams = {orgName:Ext.getCmp("org_name").getValue()};
					            		orgStore.load();
					            	}
	            	}]),
	            bbar: new Ext.PagingToolbar({
		            pageSize: GlobalVar.PAGE_LIMIT,
		            store: orgStore,
		            displayInfo: true,
		            displayMsg: '显示数据信息{0} - {1} 总计 {2}',
		            emptyMsg: "没有数据信息"
        		})
			});
			//查询的用户grid			
			 var rolegrid = new Ext.grid.GridPanel({
					cm : new Ext.grid.ColumnModel({
						columns : [ rolesm,{
									header : "用户名称",
									dataIndex : 'rolename',
									autoWidth:true,
									sortable : true
								}, {
									dataIndex : 'roleid',
									hidden : true,// TODO
									width:100,
									sortable : true
								}
								]
					}),
					viewConfig: {
		                    forceFit:true
	                },		
					columnLines : true,
					sm:rolesm,
					autoWidth : true,
					region: 'center',
					store : roleStore,
					frame : true,
					tbar:new Ext.Toolbar({
					    height: 30,
					    items: [
					        "用户名称",{
					            xtype: 'textfield'
					        },"-",{
				            	pressed:true,
				            	enableToggle:true,
				            	text:'查    询',
				            	toggleHandler: function(){
				            		var items = rolegrid.getTopToolbar().items;
				            		var i = 0;
				            		var name = '';
				            		items.each(function(x){
				            			if(i==1){
				            				name = x.getValue();
				            				return false;
				            			}
				            			i++;
				            		});  
				            		
				            		roleStore.baseParams = {roleName:name};
				            		roleStore.load();
				            	}
					        }
					    ]
					})
			});	
			var orgFormPanel = new Ext.Panel({   
			    labelWidth: 100,   
			    border:false,   
			    height:300,
			    layout:'border',   
			    items: orggrid 
			  }); 	

			var roleFormPanel = new Ext.Panel({   
			    labelWidth: 100,   
			    border:false,        
			    height:300,
			    layout:'border',
			    items: rolegrid  
			  });  
		   var selecttab = new Ext.TabPanel({
		        width:450,
		        activeTab:0,
		        defaults:{autoHeight: true},
		        items:[
		            {items: orgFormPanel,id:'1', title: '组织机构'},
		            {items: roleFormPanel,id:'2', title: '角色'}
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
	
	//////
//	function RemoveArray(array,attachId){   
//		for(var i=0,n=0;i<array.length;i++){   
//		if(array[i]!=attachId){   
//		array[n++]=array[i]   
//		}   
//	}   
//	array.length -= 1;   
//	}   
//	function containsArray(array,attachId){   
//		for(var i=0;i<array.length;i++){   
//		if(array[i]==attachId){   
//		return true;   
//		break;   
//		}   
//	}   
//	return false;   
//	}    
//	Array.prototype.remove = function (obj) {   
//		return RemoveArray(this,obj);   
//	};    
//	Array.prototype.contains = function (obj) {   
//		return containsArray(this,obj);   
//	};
