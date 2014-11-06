var categoryFormPanel = new Ext.FormPanel({
		id:'categoryForm',
        labelWidth: 90,
        frame:true,
        waitMsgTarget: true,
        stateful:false,
        defaultType: 'textfield',
        defaults: {width: 230},
        items: [{
        		 
                fieldLabel: '分类名称',
                id:'categoryname_add',
                name: 'category.categoryName',
                allowBlank:false,
                sideText : '<font color=red>*</font>',
                maxLength:64,  //限制最大字符数为64个
                regex:/^[\w\u4E00-\u9FA5\uF900-\uFA2D]*$/,
				regexText:'分类名称不能包含除下划线以外的特殊字符。'
            },{
            	xtype:'textarea',
            	id:'categorydesc_add',
                fieldLabel: '分类描述',
                name: 'category.categoryDesc'
            },{
            	xtype:'hidden',
            	 id:'parentId_add',
                name: 'category.parentId'
               
            }]
            
    });
    
var saveCategory = function(){
	var _form = categoryFormPanel.getForm();
	if(_form.isValid()){
		_form.submit({
			waitMsg:'正在保存...',
			url:'saveCategory.action',
			failure:function(form,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
				Ext.MessageBox.alert("错误",result.result);
			},
			success:function(form1,resp){
				var resText = resp.response.responseText;
				var result = Ext.util.JSON.decode(resText);
	 			if(result.success){
					Ext.MessageBox.alert("提示","添加成功");
					categoryAdd.closeDialog();
					var treeNode = treePanel.getSelectionModel().getSelectedNode();
					var menuid = treeNode.id;
					
					var parentid = treeNode.id;
					if(menuid != "0") {
						parentid = treeNode.parentNode.id;
					}
					//alert(parentid);
					refreshCategoryById(parentid);
					setTimeout("extendsNode('"+menuid+"')",200);
				}else{
					Ext.MessageBox.alert("提示","添加失败");
				}
			}
		});
	}
}
function extendsNode(menuid){
	treePanel.getNodeById(menuid).expand();
	treePanel.fireEvent('click',treePanel.getNodeById(menuid));
}

var categoryAdd = function(){
    var win,showButton;
    return {
	    showDialog:function(btn_id){
	    	 if(!win){
	            win = new Ext.Window({
	                applyTo:'category-add',
	                width:400,
	                //autoScroll:true,
	                height:250,
	                border : false,
					modal : true,
					title:'添加分类',
	                closeAction:'hide',
	                autoScroll : false, 
	                items:categoryFormPanel,
	                buttonAlign:'center',
	               
	                buttons: [{
	                    text:'提交',
	                    handler:saveCategory
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