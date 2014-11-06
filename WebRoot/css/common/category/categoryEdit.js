var editCategoryFormPanel = new Ext.FormPanel({
		id:'editCategoryForm',
        labelWidth: 90,
        frame:true,
        waitMsgTarget: true,
        stateful:false,
        defaultType: 'textfield',
        defaults: {width: 230},
        items: [{
                fieldLabel: '分类名称',
                id:'categoryname_edit',
                name: 'category.categoryName',
                allowBlank:false,
                sideText : '<font color=red>*</font>',
                maxLength:64,  //限制最大字符数为64个
                regex:/^[\w\u4E00-\u9FA5\uF900-\uFA2D]*$/,
				regexText:'分类名称不能包含除下划线以外的特殊字符。'
            },{
            	xtype:'textarea',
            	id:'categorydesc_edit',
                fieldLabel: '分类描述',
                name: 'category.categoryDesc'
            },{
            	xtype:'hidden',
            	 id:'categoryid_edit',
                name: 'category.categoryId'
            },{
            	xtype:'hidden',
            	 id:'categoryPid_edit',
                name: 'category.parentId'
            },{
            	xtype:'hidden',
            	 id:'categoryChildNum_edit',
                name: 'category.childrenNum'
            }]
    });
    
var editCategorySubmit = function(){
	var _form = editCategoryFormPanel.getForm();
	if(_form.isValid()){
		_form.submit({
			waitMsg:'正在保存...',
			url:'updateCategory.action',
			failure:function(form,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				Ext.MessageBox.alert("错误",result.result);
			},
			success:function(form1,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				if(result.success){
					Ext.MessageBox.alert("提示","修改成功");
					categoryEdit.closeDialog();
					var treeNode = treePanel.getSelectionModel().getSelectedNode();
					parentid = treeNode.parentNode.id;
					refreshCategoryById(parentid);
				}else{
					Ext.MessageBox.alert("提示","修改失败");
				}
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
	                height:250,
	                border : false,
					modal : true,
					title:'修改分类',
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