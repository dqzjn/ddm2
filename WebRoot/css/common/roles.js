/** 使用此选择用户功能的模块，需要实现deal_roleinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.selrole = Ext.extend(Ext.Window,{
		constructor: function(config) {
			var grid = this.initGridData(config);
			this.grid = grid;
			var title = "角色";
			if(config.title!=null && config.title!=''){
				title = config.title;
			}
			var win = this;
			var con = {
				items:[grid],
				title:title,
				height:450,
				buttonAlign : 'center',
				closeAction : 'hide',
				width:316,
				buttons : [{
					text : '确定',
					handler : function (){
                            var objData=win.grid.selModel.getSelections();
			                if(objData==null || objData.length==0){
			                	Ext.MessageBox.alert("提示", "请选择一条记录");
			                	return;
			                }
			                //用到用户选择功能的模块需要自己实现
			                if(null != config.handler && config.handler!=''){
			                	config.handler(objData);
			                }else{
			                	deal_roleinfo_common(objData);	
			                }
			                
					}
				}, {
					text : '取消',
					handler : function() {
						win.hide();
					}
				}]
			}	
	        TDMPlatform.common.selrole.superclass.constructor.call(this, con);
    	},
    	initGridData:function(config){
    		var store = new Ext.data.Store({
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
			store.setDefaultSort('userid', 'desc');
			var sm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});
			//根据组织查询的用户grid			
			var grid = new Ext.grid.GridPanel({
					autoScroll:true,
					height:400,
					width:300,
					tbar:new Ext.Toolbar({
					    height: 30,
					    items: [
					        "角色名称",{
					            xtype: 'textfield'
					        },"-",{
				            	pressed:true,
				            	enableToggle:true,
				            	text:'查    询',
				            	toggleHandler: function(){
				            		var items = grid.getTopToolbar().items;
				            		var i = 0;
				            		var name = '';
				            		items.each(function(x){
				            			if(i==1){
				            				name = x.getValue();
				            				return false;
				            			}
				            			i++;
				            		});  
				            		
				            		store.baseParams = {roleName:name};
				            		store.load();
				            	}
					        }
					    ]
					}),
					cm : new Ext.grid.ColumnModel({
						columns : [ sm,{
									header : "角色名称",
									dataIndex : 'rolename',
									autoWidth:true,
									sortable : true
								},{
									header : "角色描述",
									dataIndex : 'roledesc',
									autoWidth:true,
									sortable : true
								}]
							}),
					 viewConfig: {
		                    forceFit:true
	                },		
					columnLines : true,
					autoWidth : true,
					sm:sm,
					region: 'center',
					store : store
				});
			return grid;
	    },
	    /**
	     * 清除组件的当前选中状态
	     */
	    clearSelect:function(){
	    	if(null != this.grid.getSelectionModel().grid){
	    		this.grid.getSelectionModel().clearSelections();
	    	}	
	    }
	    
	});
