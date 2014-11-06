	var Employee = Ext.data.Record.create([{
        name: 'dataIndex',
        type: 'string'
    },{
        name: 'hidden',
        type: 'string'
    },{
        name: 'header',
        type: 'string'
    },{
        name: 'isSearch',
        type: 'string'
    }]);
    var columnEditor = new Ext.ux.grid.RowEditor({
        saveText: '确定',   
	    cancelText: '取消',   
	    commitChangesText: '提交或取消更改!',   
	    errorText: '错误',   
	    errorSummary: false,   
	    listeners: {   
	    	beforeedit:function(rowedit,index){
	    		thisstore = columngrid.getStore().getAt(index).get("dataIndex");
	    	},
	        // 注册取消编辑事件处理 
	        canceledit: function(RowEditor){   
	            if (RowEditor.record.phantom) {   
	            	// 这里表示数据在客户端被修改过
	            	if(thisstore=='')
	            		columngrid.getStore().removeAt(RowEditor.rowIndex);   
	            }   
	        },   
	        // 注册编辑提交事件处理   
	        afteredit: function(r, o, record){
	        	getSql();
	        },
	        validateedit: function(r, o, record){   
	           
	        }
	    }
    });
    function getSql(){
    	var table = Ext.getCmp("entityTableName_add").getValue();
    	var id = Ext.getCmp("entityPkName_add").getValue();
    	var searchWhere = Ext.getCmp("searchWhere_id").getValue();
    	
    	if(null == table || table.length==0){
    		return;
    	}
    	if(null == id || id.length==0){
    		return;
    	}
    	if(columnstore.getCount()==0){
    		return;
    	}
     	var searchsql = 'select ';
     	columnstore.each(function(rec){
     		searchsql += rec.data.dataIndex + ",";
     	});
     	searchsql = searchsql.substring(0,searchsql.length-1);
     	searchsql += ' from  '+ table;
     	if(searchWhere!='' && searchWhere.trim().length>0){
			searchsql += ' where '+ searchWhere;     		
     	}
     	Ext.getCmp("searchSql_id").setValue(searchsql);
    }
    //根据表名得到列明store
    var columninfostore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
           url: '../../common/bindconn/getColumnByTable.action'
       }),
       reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'count'
        },[
        	{name: 'columnName', mapping: 'COLUMN_NAME'},
        	{name: 'dataType', mapping: 'DATA_TYPE'},
        	{name: 'dataLength', mapping: 'DATA_LENGTH'}
        ]),
        listeners:{
        	"beforeload":function(){
        		columninfostore.baseParams = {
					entityTableName:Ext.getCmp("entityTableName_add").getValue()
				}
        	}
        },
        remoteSort: true
    });
    //海量数据列信息配置store
    var columnstore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
           url: 'searchExperimentSensor.action'
       }),
       reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'count'
        },[
        	{name: 'dataIndex', mapping: 'dataIndex'},
        	{name: 'hidden', mapping: 'hidden'},
        	{name: 'isSearch', mapping: 'isSearch'},
        	{name: 'header', mapping: 'header'}
        ]),
        remoteSort: true
    });
    columngrid = new Ext.grid.GridPanel({
        store: columnstore,
        title:'列信息',
        trackMouseOver:true,
        height:200,
        width:480,
        autoScroll:true,
        plugins: [columnEditor],
        disableSelection:false,
        enableDragDrop:true,
        loadMask: true,
        enableHdMenu:false,
        sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
        columns:[
        new　Ext.grid.RowNumberer({
			header　:　"序号",
			width　:　35,
			renderer:function(value,metadata,record,rowIndex){
				return　　1　+　rowIndex;
			}
		}),{
            header: "显示名称",
            dataIndex: 'header',
            sortable: true,
            editor: {
                xtype: 'textfield',
                id:'viewname'
           }   
        },{
            header: "列名称",
            dataIndex: 'dataIndex',
            sortable: true,
            editor: 
                new Ext.form.ComboBox({
                        triggerAction : 'all',  //显示所有下列数.必须指定为'all'
                        id:'comunmname',
                        emptyText:'请选择...',   //没有默认值时,显示的字符串
                        listWidth:230,
                        store : columninfostore,
                        valueField : 'columnName',  //传送的值
                        displayField : 'columnName'
                })         
        },{
            header: "是否显示",
            dataIndex: 'hidden',
            value:'false',
            renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
            	var store = this.editor.getStore();
            	var str = '';
            	store.each(function(rec){
            		if(rec.data.value==value){
            			str = rec.data.label;
            		}
            	});
            	return str;
            },
            sortable: true,
            editor: 
                new Ext.form.ComboBox({
					allowBlank:true,
					id:'isHidden',
					width: 230,
					store:new Ext.data.ArrayStore({
						      fields:['label', 'value'],
						      data:[['显示', "false"],
						        	['隐藏',"true"]]
						}),
					editable : false,
					valueField:'value',  
		            displayField:'label',
		            forceSelection: true,
		            typeAhead: true,
		            selectOnFocus:true,
		            triggerAction: 'all',
					mode:'local'
			})
        },{
            header: "是否查询",
            dataIndex: 'isSearch',
            renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
            	var store = this.editor.getStore();
            	var str = '';
            	store.each(function(rec){
            		if(rec.data.value==value){
            			str = rec.data.label;
            		}
            	});
            	return str;
            },
            sortable: true,
            editor: 
                new Ext.form.ComboBox({
					fieldLabel: '是否启用',
					allowBlank:true,
					id:'isUsed',
					width: 230,
					store:new Ext.data.ArrayStore({
						      fields:['label', 'value'],
						      data:[
						      		['不查询', "0"],
						      		['模糊查询', "1"],
						        	['精确查询',"2"]]
						}),
					editable : false,
					valueField:'value',  
		            displayField:'label',
		            forceSelection: true,
		            typeAhead: true,
		            selectOnFocus:true,
		            triggerAction: 'all',
					mode:'local'
			})
        }],
        viewConfig: {
        	forceFit:true
        },
        // paging bar on the bottom
        bbar: new Ext.Toolbar({
            items:[
                {
                id:'add_certificate',
                pressed: true,
                enableToggle:true,
                text: '添    加',
                toggleHandler:function(){
                	var e = new Employee({
	                    dataIndex: '',
	                    hidden: 'false',
	                    header: '',
	                    isSearch:'0'
	                });
	                
	                var n = columnstore.getCount();
	                columnEditor.stopEditing();
	                columnstore.insert(n, e);
	                columngrid.getView().refresh();
	                columngrid.getSelectionModel().selectRow(n);
	                columnEditor.startEditing(n);
                }
            },'-',{
            	id:'del_certificate',
            	pressed:true,
            	enableToggle:true,
            	text:'删    除',
            	toggleHandler:function(){
                	columnEditor.stopEditing();
	                var s = columngrid.getSelectionModel().getSelections();
	                for(var i = 0, r; r = s[i]; i++){
	                    columnstore.remove(r);
	                }
                }
            }]
        })
    });

	
	var bindConnPanel = new Ext.FormPanel({
		id:'bindConnAddForm',
        labelWidth: 90,
        frame:true,
        defaultType: 'textfield',
        items: [{
                fieldLabel: '海量数据名称',
                id:'entityName_add',
                name: 'bindConn.entityName',
                allowBlank:false,
                width: 230,
                sideText : '<font color=red>*</font>'
            },{
                fieldLabel: '数据表名称',
                id:'entityTableName_add',
                name: 'bindConn.entityTableName',
                width: 230,
                allowBlank:false,
                sideText : '<font color=red>*&nbsp;&nbsp;注：数据库表名称</font>'
//                regex:/^[a-zA-Z0-9_]{1,30}$/,
//				regexText:'数据表主键名不能包含除下划线以外的特殊字符。',
            },new Ext.form.ComboBox({
                 	   id:'entityPkName_add',
                 	    fieldLabel: '主键',
                        triggerAction : 'all',  //显示所有下列数.必须指定为'all'
                        emptyText:'请选择...',   //没有默认值时,显示的字符串
                        width:230,
                        hiddenName:'bindConn.entityPkName',
                        store : columninfostore,
                        valueField : 'columnName',  //传送的值
                        listeners:{
            				"select": function(comb,record,index){
            					var value = record.data.columnName;
	      						var r1 = columnstore.query("dataIndex",value,false,true);
	      						if(null != r1 && r1!='undefined'){
									var e = new Employee({
							            dataIndex: value,
							            hidden: "true",
							            isSearch:0,
							            header: value
							        });
							        columnEditor.stopEditing();
							        columnstore.insert(0, e);
	      						}	
		     				}
		     			},
                        displayField : 'columnName'  //UI列表显示的文本
            }),new Ext.form.ComboBox({
				id:'entityIsDelFlag_id',
				fieldLabel: '是否启用',
				allowBlank:true,
				width: 230,
				hidden:true,
				hiddenName:'bindConn.entityIsDelFlag',
				value:'0',
				store:new Ext.data.ArrayStore({
					      fields:['label', 'value'],
					      data:[['启用', '0'],
					        	['禁用','1']]
					}),
				editable : false,
				valueField:'value',  
	            displayField:'label',
	            forceSelection: true,
	            typeAhead: true,
	            selectOnFocus:true,
	            triggerAction: 'all',
				mode:'local'
			}),columngrid,
			{
				id:'searchWhere_id',
				fieldLabel: 'where条件',
                name: 'bindConn.searchWhere',
                listeners:{
                	"blur":function(){
                		getSql();
                	}
                },
                xtype:'textarea',
                width: 300
			},
			{//<font color="red">海量数据不负责验证sql的正确性，请自行验证</font>
				fieldLabel: 'sql',
				id:'searchSql_id',
                name: 'bindConn.searchSql',
                readOnly:true,
                xtype:'textarea',
                width: 300
			},{
				id:'entityColumn_id',
                name: 'bindConn.entityColumn',
                xtype:'hidden',
                width: 300
			},{
				id:'bindConnId_id',
                name: 'bindConn.bindConnId',
                xtype:'hidden',
                width: 300
			}			
		]
            
    });
    
    //设置海量数据的值
	function setValue(obj){
		obj = obj.data;
		Ext.getCmp("bindConnId_id").setValue(obj.bindConnId);
		Ext.getCmp("entityName_add").setValue(obj.entityName);
		Ext.getCmp("entityTableName_add").setValue(obj.entityTableName);
		Ext.getCmp("entityPkName_add").setValue(obj.entityPkName);
		Ext.getCmp("entityIsDelFlag_id").setValue(obj.entityIsDelFlag);
		Ext.getCmp("searchWhere_id").setValue(obj.searchWhere);
		Ext.getCmp("searchSql_id").setValue(obj.searchSql);
		
		var cols = obj.entityColumn;
		if(null != cols && cols.length>0){
			for(var i = 0;i<cols.length;i++){
				var col = cols[i];
				var e = new Employee({
		            dataIndex: col.dataIndex,
		            hidden: col.hidden,
		            isSearch:col.isSearch,
		            header: col.header
		        });
		        columnEditor.stopEditing();
		        columnstore.insert(i, e);
			}
		}
	}
    //设置海量数据的值
	function clearValue(){
		Ext.getCmp("bindConnId_id").setValue('');
		Ext.getCmp("entityName_add").setValue('');
		Ext.getCmp("entityTableName_add").setValue('');
		Ext.getCmp("entityPkName_add").setValue('');
		Ext.getCmp("entityIsDelFlag_id").setValue(0);
		Ext.getCmp("searchWhere_id").setValue('');
		Ext.getCmp("searchSql_id").setValue('');
		
		columnstore.removeAll();   
	}
    
var saveBindConn = function(){
	Ext.getCmp("entityColumn_id").setValue(storeToJsonStr(columnstore));
	var _form = bindConnPanel.getForm();
	if(_form.isValid()){
		//判断至少有一个字段是显示的
		var size = columnstore.getCount();
		if(size==0){
			Ext.Msg.alert("提示","请选择列信息");
			return;
		}
		var flag = false;
		for (var i = 0; i < size; i++) {
			var data = columnstore.getAt(i).data;
			if(data.hidden=='false'){
				flag = true;
				break;
			}
		}
		if(!flag){
			Ext.Msg.alert("提示","至少有一列信息是显示的！");
			return;
		}
	
		_form.submit({
			waitMsg:'正在保存...',
			url:'saveBindConn.action',
			failure:function(form,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				Ext.MessageBox.alert("错误",result.result);
			},
			success:function(form1,resp){
				Ext.MessageBox.alert("提示","保存成功!");
				userEdit.closeDialog();
				grid.getStore().load({params:{start:0, limit:25}});
			}
		});
	}
}

var userEdit = function(){
    var win,showButton;
    return {
	    showDialog:function(btn_id){
	    	 if(!win){
	            win = new Ext.Window({
	                width:500,
	                height:390,
	                border : false,
					isTopContainer : true, 
					modal : true,
					title:'添加数据',
	                closeAction:'hide',
	                autoScroll : false, 
	                items:bindConnPanel,
	                buttonAlign:'center',
	                buttons: [{
	                    text:'提交',
	                    handler:saveBindConn
	                	},{
	                    text: '关闭',
	                    handler: function(){
	                        win.hide();
                    }}]
	            });
	        }
	        showButton = Ext.get(btn_id);
	        win.show(showButton.dom);
	    },
	    closeDialog:function(){
	    	win.hide();
	    }
	    
    }
}();