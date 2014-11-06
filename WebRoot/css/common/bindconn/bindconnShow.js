/** 使用此选择单位功能的模块，需要实现deal_deptinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选，通过title指定window窗口的名字
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.bindconn = {
		init: function(config) {
			var pageSize = this.getPageSize();
			config.pageSize = pageSize;
			var bindConn = this.initGridData(config);
			var grid = this.initGrid(bindConn.data,config);
			var searchtable = this.initQueryItem(bindConn.data,config);
			var items = new Array();
			//将基础数据赋值到当前元素中;
			this.pageSize = pageSize;
			this.grid = grid;
			this.config = config;
			this.bindConn = bindConn;
			
	        var viewport = new Ext.Viewport({
	        	style:'background:#fff3e6',
				layout : 'border',
				items : [searchtable,grid]
			});
			this.load(bindConn.data,config);
    	},
    	initGridData:function(config){
    		var flag = true;
    		var bindConnId;
    		if(null != config.bindConnId && config.bindConnId!=''){
    			bindConnId = config.bindConnId;
    		}
    		var bindConn;
    		Ext.Ajax.request({
				url: common_contextPath+'/common/bindconn/getBindConnById.action',
				method:'post',
				async:false,
				success: function(resp){
					var resText = resp.responseText;
		 			bindConn = Ext.util.JSON.decode(resText);
				},
				failure: function(){
					Ext.MessageBox.alert("提示","获取海量数据信息出错！");
					return;
				},
				params: {bindConnId:bindConnId}
			});
		    return bindConn;
	    },
	    initGrid:function(bindConn,config){
	    	var fields = '[';
	    	var objs = Ext.util.JSON.decode(bindConn.entityColumn);
	    	for(var i = 0;i<objs.length;i++){
	    		var obj = objs[i];
				fields += '{name:"' + obj.dataIndex + '"},';  
	    	}
	    	
	    	if(fields.length>0){
	    		fields = fields.substring(0,fields.length-1);
	    	}
	    	fields += "]";
	    	fields = eval(fields);
	    	var datastore = new Ext.data.Store            
		 	({                    
				proxy:new Ext.data.HttpProxy({url:common_contextPath+"/common/bindconn/getDataByBindConnId.action"}),                   
				reader:new Ext.data.JsonReader({totalProperty:"count",root:"data",fields:fields})            
			});
			var columns = bindConn.entityColumn;
			columns = columns.replaceAll("\"header\"","header");
			columns = columns.replaceAll("\"dataIndex\"","dataIndex");
			columns = columns.replaceAll("\"hidden\"","hidden");
			columns = columns.replaceAll("\"true\"","true");
			
			columns = columns.replaceAll("\"false\"","false");
			
			if(null == config.isSingle){
				config.isSingle=true;
			}
			
			var sm = new Ext.grid.CheckboxSelectionModel({
							handleMouseDown : Ext.emptyFn,
							singleSelect : config.isSingle
						});
			var tempCols = eval(columns);
			var cols = new Array();
			cols[0] = sm;
			for(var i = 1;i<tempCols.length+1;i++){
				cols[i] = tempCols[i-1];
			}
			
	    	var cm = new Ext.grid.ColumnModel(cols);
	    	var pagingBar = new Ext.PagingToolbar            
			({                    
				displayInfo:true,                    
				emptyMsg:"没有数据显示",                    
				displayMsg:"显示试验件信息{0} - {1} 总计 {2}",                    
				store:datastore,                    
				pageSize:config.pageSize          
			});  
			
	    	var grid = new Ext.grid.GridPanel            
			({                    
				cm:cm,                
		 		store:datastore,                    
				frame:false,                    
				region:'center',
				border:true,                                        
				layout:"fit",
				buttonAlign:'center',
				sm:sm,
				width:'100%',                    
				width:430,
				buttons : [{
					text : '确定',
					handler : function (){
							var objData = grid.selModel.getSelections();
			                if(objData==null || objData.length==0){
			                	Ext.MessageBox.alert("提示", "请选择一条记录");
			                	return;
			                }
			                var customParams = config.customParams;
			                //用到用户选择功能的模块需要自己实现
			                if(null != config.handler && config.handler!=''){
			                	eval("window.dialogArguments.window."+config.handler+"(objData,window,customParams)");
			                }else{
			                	window.dialogArguments.window.deal_bindconn_common(objData,win,customParams);
			                }
					}
				}, {
					text : '取消',
					handler : function() {
						window.close();
					}
				}],
				border:false,
				viewConfig:{forceFit:true},                
				bbar:pagingBar            
			});
			return grid;
	    },
	    initQueryItem:function(bindConn,config){
	    		
	    	var table = new Ext.Panel({
		        layout:'table',
		        region:'north',
		        height:0,
		        defaults: {
		        	border:false,
		            bodyStyle:'padding:1px'
		        },
		        layoutConfig: {
		            columns: 2
		        }
		    });	
		    var column = bindConn.entityColumn;
		    var searchflag = false;
		    var count_ = 0;
		    if(null != column && column!=''){
		    	var cols = Ext.util.JSON.decode(column);
		    	for(var i = 0;i<cols.length;i++){
		    		var col = cols[i];
		    		if(col.isSearch!='0'){
		    			searchflag = true;
		    			table.add({
					          width:220,
					          layout: 'form',
					          labelWidth:55,
					          items: [{
					              xtype:'textfield',
					              fieldLabel:col.header,
					              name:col.dataIndex+"_"+i,
					              anchor:'95%'
					          }]
		    			});
		    			count_++;
		    		}
		    	}
		    	
		    	var w = this;
		    	if(searchflag){
			    	table.add({
				          layout: 'form',
				          labelWidth:55,
				          items: [{
				              xtype:'button',
				              text:'查询',
				              handler:function(){
				    		 	w.load(bindConn,config);
				    		  },
				              anchor:'35%'
				          }]
			    	});
			    	var height = 30*(count_/2+1)
		    		table.setHeight(height);
		    	}
		    }
		    return table;
	    },
	    getPageSize:function(){
	    	return 15;
	    },
	    /**
	     * 清表的数据
	     */
	    clearSelects:function(){
	    	this.grid.getSelectionModel().clearSelections();
	    },
	    /**
	     * 加载数据
	     */
	    load:function(bindConn,config){
	    	var ss = "{bindConnId:'"+config.bindConnId+"'";
	    	var temp='';
	    	var column = bindConn.entityColumn;
		    if(null != column && column!=''){
		    	var cols = Ext.util.JSON.decode(column);
		    	for(var i = 0;i<cols.length;i++){
		    		var col = cols[i];
		    		if(col.isSearch!='0'){
		    			var varobj = Ext.query("*[name="+col.dataIndex+"_"+i +"]");
		    			if(null != varobj && varobj!='undefined' && varobj.length>0){
			    			var val = Ext.query("*[name="+col.dataIndex+"_"+i +"]")[0].value; 
			    			if(val!=null && val!='' &&val!='undefined'){
				    			if(temp.length>0){
				    				temp += ' and '
				    			}
				    			temp += col.dataIndex;
				    			if(col.isSearch=='1'){
				    				temp += " like \'" +"%"+val+"%\'";
				    			}else{
				    				temp += " = \'"+val+"\'";
				    			}
			    			}
		    			}	
		    		}
		    	}
		    }
		    ss += ",searchss:\""+temp+"\"}";
	    	this.grid.getStore().baseParams = Ext.util.JSON.decode(ss);
	    	this.grid.getStore().load({params:{start:0,limit:config.pageSize}});
	    }
	};
Ext.onReady(function(){
	var width = document.body.clientWidth;  //屏幕宽度
	var height = document.body.clientHeight - 10;  //屏幕高度
	TDMPlatform.common.bindconn.init(config);
});