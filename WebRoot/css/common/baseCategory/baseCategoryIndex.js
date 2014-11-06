Ext.BLANK_IMAGE_URL = '../../images/s.gif';
function refreshCategoryById(id){
	treePanel.getNodeById(id).reload();
}
Ext.onReady(function(){
	Ext.QuickTips.init();
    addCategoryHandler = function(){
 		var treeNode = treePanel.getSelectionModel().getSelectedNode();
 		if(treeNode != null){
	 		categoryAdd.showDialog('category_add');
	 		categoryFormPanel.getForm().reset();
	 		Ext.get("parentId_add").dom.value = treeNode.id;
 		}else{
 			Ext.MessageBox.alert('提示', '请选择一个分类节点！');
 		}
 	}
 	editCategoryHandler = function(){
 		var treeNode = treePanel.getSelectionModel().getSelectedNode();
 		if(treeNode != null){
	 		categoryEdit.showDialog('category_edit');
	 		Ext.get("categoryid_edit").dom.value = treeNode.id;
	 		Ext.get("categoryname_edit").dom.value = treeNode.text;
	 		Ext.get("categorydesc_edit").dom.value = treeNode.attributes.baseCategoryDesc;
	 		Ext.get("categoryChildNum_edit").dom.value = treeNode.attributes.childrenNum;
	 		Ext.get("categoryPid_edit").dom.value = treeNode.attributes.parentId;
 		}else{
 			Ext.MessageBox.alert('提示', '请选择一个分类节点！');
 		}
 	}
 	
 	delCategoryHandler = function(){
 		Ext.MessageBox.confirm('提示','确定删除吗？',function(btn){
 			if(btn=='yes'){
		 		var treeNode = treePanel.getSelectionModel().getSelectedNode();
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
			 				var parentid = treeNode.attributes.parentId;
			 				var menuid = treeNode.id;
			 				var parent = treePanel.getNodeById(parentid);
			 				parent.attributes.childrenNum = parent.attributes.childrenNum - 1;
							if (parentid != "0" && parent.attributes.childrenNum == 0) {
								parentid = parent.parentNode.id;
							}
							refreshCategoryById(parentid);
				 			Ext.MessageBox.alert("提示","删除成功");
			 			}else{
			 				Ext.MessageBox.alert("错误","删除失败，请与管理员联系！");
			 			}
			 		}
			 	}
		 		
		 		if(treeNode != null){
		 			if(treeNode.isLeaf() && treeNode.id != 0){
						var id = treeNode.id;
						Ext.Ajax.request({
						   url: 'delBaseCategory.action',
						   method:'post',
						   success: result.success,
						   failure: result.failure,
						   params: {baseCategoryId: id}
						});
			 		}else{
			 			Ext.MessageBox.alert('提示', '请选择叶子节点进行删除!');	
			 		}	
		 		}else{
		 			Ext.MessageBox.alert('提示', '请选择一个分类节点！');
		 		}
	 	}
	 	});//////
 	}
 	
 	var tree = Ext.tree;
    var tb = new Ext.Toolbar({region:'north',height:30});
    tb.add({
        id:'category_add',
        text:'添加模板分类',
        handler:addCategoryHandler,
        disabled:false
    },'-',{
    	id:'category_edit',
    	text:'修改模板分类',
    	handler:editCategoryHandler,
    	disabled:false
    },'-',{
    	id:'category_del',
    	text:'删除模板分类',
    	handler:delCategoryHandler,
    	disabled:false
    });
    treePanel = new tree.TreePanel({
     		id:'categoryTreeId',
     		region:'center',
     		width : 250,
    		border  : false,
     		frame   : false,
     		autoScroll:true,
            loader: new tree.TreeLoader({
            		dataUrl:'searchBaseCategory.action',
            		listeners:{
            			"beforeload": function(treeLoader, node){
      						selectedNodeId = node.id;
							treeLoader.baseParams = {parentId:selectedNodeId};
     					}
     				}
     		}),
           border: false
    });
    
    var root = new tree.AsyncTreeNode({
               	text: '模板分类根节点',
               	id:'0'
    });
    treePanel.setRootNode(root);
    root.expand(false,false);
    treePanel.on("click",function(node){
   		var btn  = tb.items.map;
   		btn.category_add.enable();
   		btn.category_del.enable();
   		btn.category_edit.enable();
   		if(node.id==0){ btn.category_del.disable();btn.category_edit.disable();}
    	if(node.id != 0){
    		if(node.isLeaf()){
//    			btn.category_add.disable();
    		}else{
    			btn.category_del.disable();
    		}
    	}
    });
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [tb,treePanel]
	});
	 checkbutton();
});

