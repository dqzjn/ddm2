Ext.BLANK_IMAGE_URL = '../../images/s.gif';
Ext.onReady(function(){
	var bindconnobj;
	Ext.QuickTips.init();
	addBindConnHandler = function(flag){
		clearValue();
		if(flag=='edit'){
			var objs = grid.selModel.getSelections();
			if(null == objs || objs.length==0){
				Ext.Msg.alert("提示","请选择一条记录！");
				return;
			}
			var obj = objs[0];
			setValue(obj);
		}
 		userEdit.showDialog('add_bindConn');
 	};
 	
 	delBindConnHandler = function(){
 		Ext.MessageBox.confirm('提示','确定删除吗？',function(btn){
 			if(btn=='yes'){
			 	var result = {
			 		failure:function(resp){
			 			var resText = resp.responseText;
						var result = Ext.util.JSON.decode(resText);
						Ext.MessageBox.alert("错误",result.result);
			 		},
			 		success:function(resp){
			 			var resText = resp.responseText;
			 			var result = Ext.util.JSON.decode(resText);
			 			if(result.success){
				 			Ext.MessageBox.alert("提示","删除成功");
				 			grid.getStore().load({params:{start:GlobalVar.PAGE_START, limit:GlobalVar.PAGE_LIMIT}});
			 			}else{
			 				Ext.MessageBox.alert("失败","该记录还有其它数据绑定。");
			 			}
			 		}
			 	}
		 		
		 		if(grid.selModel.getSelected() != null){
		 			var objData=grid.selModel.getSelected();
					var bindConnId = objData.data.bindConnId;
		 			Ext.Ajax.request({
						   url: 'delBindConn.action',
						   method:'post',
						   success: result.success,
						   failure: result.failure,
						   params: {bindConnId: bindConnId}
						});
		 		}else{
		 			Ext.MessageBox.alert('提示', '请选择一条记录！');
		 		}
	 	}
	 	});//////
	 	
	 }
	var store = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
           url: 'searchBindConn.action'
       }),
       reader: new Ext.data.JsonReader({
            root: 'bc',
            totalProperty: 'count'
        },[
        	{name: 'bindConnId', mapping: 'bindConnId'},
        	{name: 'entityTableName', mapping: 'entityTableName'},
        	{name: 'entityPkName', mapping: 'entityPkName'},
        	{name: 'entityColumn', mapping: 'entityColumn'},
        	{name: 'searchWhere', mapping: 'searchWhere'},
        	{name: 'searchSql', mapping: 'searchSql'},
        	{name: 'entityName', mapping: 'entityName'},
        	{name: 'entityIsDelFlagLable', mapping: 'entityIsDelFlagLable'},
        	{name: 'entityIsDelFlag', mapping: 'entityIsDelFlag'}
        ]),
        remoteSort: true
    });
    store.setDefaultSort('bindConnId', 'desc');
    grid = new Ext.grid.GridPanel({
        height:400,
        autoWidth:true,
        title:'海量数据管理',
        store: store,
        trackMouseOver:true,
        disableSelection:false,
        enableDragDrop:true,
        loadMask: true,
        enableHdMenu:false,
        sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
        columns:[{
            header: "编号",
            dataIndex: 'bindConnId',
            sortable: true
        },{
            header: "海量数据名称",
            dataIndex: 'entityName'
            //hidden: true,
           // sortable: true
        },{
            header: "数据表名称",
            dataIndex: 'entityTableName'
            //hidden: true,
           // sortable: true
        },{
            header: "数据表主键名",
            dataIndex: 'entityPkName'
            // sortable: true
        }],
        viewConfig: {
        	forceFit:true
        },
        tbar : ([
                '-', {
                id:'add_bindConn',
                pressed: true,
                enableToggle:true,
                text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;增    加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                toggleHandler:function(){
	            addBindConnHandler("add");
	                }
            },'-',{
            	id:'edit_bindConn',
            	pressed:true,
            	enableToggle:true,
            	text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编     辑&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
            	toggleHandler:function(){
	            addBindConnHandler("edit");
	                }
            },'-',{
            	id:'del_bindConn',
            	pressed:true,
            	enableToggle:true,
            	text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;删     除&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
            	toggleHandler:delBindConnHandler
            },'-',{
            	id:'view_bindConn',
            	pressed:true,
            	enableToggle:true,
            	text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预     览&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
            	toggleHandler:function(){
            		var objs = grid.selModel.getSelections();
            		if(objs.length>0){
            			if(null != bindconnobj){
            				Ext.destroy(bindconnobj);
            			}
            			TDMPlatform.common.bindconn.initBindconnWin({bindConnId:objs[0].data.bindConnId});
            		}else{
            			alert("请选择记录后再预览！");
            		}	
            	}
            }]),
        // paging bar on the bottom
        bbar: new Ext.PagingToolbar({
            pageSize: GlobalVar.PAGE_LIMIT,
            store: store,
            displayInfo: true,
            displayMsg: '显示信息{0} - {1} 总计 {2}',
            emptyMsg: "没有信息"
        })
    });
    var basenameField = new Ext.form.TextField({
				id:'entityTableName',
				name:'entityTableName',
				renderTo:'div_entityTableName'
	});
		var buttonName = new Ext.Button({
		id:"buttonName",
	    text:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查 询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
	    type:"button",
		hidden:false, 
		handleMouseEvents:true,
		handler:function(){search();},
		renderTo:"subbutton" 
	});
	function search(){
		var basenameField = document.getElementById("entityTableName").value;
        grid.getStore().baseParams={'entityTableName':basenameField};
        grid.getStore().load({params:{start:GlobalVar.PAGE_START, limit:GlobalVar.PAGE_LIMIT}});
	}
	
    grid.render('bindConn-grid');
    checkbutton();
    store.load({params:{start:GlobalVar.PAGE_START, limit:GlobalVar.PAGE_LIMIT}});
});

