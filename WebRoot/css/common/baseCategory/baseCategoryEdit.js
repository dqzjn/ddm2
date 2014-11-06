var editCategoryFormPanel = new Ext.FormPanel({
		id:'editCategoryForm',
        labelWidth: 90,
        frame:true,
        waitMsgTarget: true,
        stateful:false,
        defaultType: 'textfield',
        defaults: {width: 230},
        items: [{
        		 
                fieldLabel: '模板分类名称',
                id:'categoryname_edit',
                name: 'baseCategory.baseCategoryName',
                allowBlank:false,
                sideText : '<font color=red>*</font>'//,
                //regex:/^[\w\u4E00-\u9FA5\uF900-\uFA2D]*$/,
				//regexText:'分类名称不能包含除下划线以外的特殊字符。'
                	
            },{
            	xtype:'textarea',
            	id:'categorydesc_edit',
                fieldLabel: '模板分类描述',
                name: 'baseCategory.baseCategoryDesc'
            },{
            	xtype:'hidden',
            	 id:'categoryid_edit',
                name: 'baseCategory.baseCategoryId'
            },{
            	xtype:'hidden',
            	 id:'categoryPid_edit',
                name: 'baseCategory.parentId'
            },{
            	xtype:'hidden',
            	 id:'categoryChildNum_edit',
                name: 'baseCategory.childrenNum'
            }]
    });
    
var editCategorySubmit = function(){
	var _form = editCategoryFormPanel.getForm();
	if(_form.isValid()){
		_form.submit({
			waitMsg:'正在保存...',
			url:'updateBaseCategory.action',
			failure:function(form,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				Ext.MessageBox.alert("错误",result.result);
			},
			success:function(form1,resp){
				Ext.MessageBox.alert("提示","保存成功!");
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				categoryEdit.closeDialog();
				var treeNode = treePanel.getSelectionModel().getSelectedNode();
				parentid = treeNode.parentNode.id;
				refreshCategoryById(parentid);
			}
		});
	}
}

var categoryEdit = function(){
    var win,showButton;
    return {
	    showDialog:function(btn_id){
	    	 if(!win){
	            win = new Ext.Window({
	                applyTo:'category-edit',
	                width:400,
	                height:200,
	                border : false,
					modal : true,
					title:'修改模板分类',
	                closeAction:'hide',
	                items:editCategoryFormPanel,
	                buttonAlign:'center',
	                buttons: [{
	                    text:'提交',
	                    handler:editCategorySubmit
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