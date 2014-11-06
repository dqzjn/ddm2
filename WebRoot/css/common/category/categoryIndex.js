/**
 * 功能描述：重载ext自身的方法initValue，目的：解决文本框输入最大字符数限制，修改后超过设置的maxlength值之后，不允许再输入字符。
 * add by songxiaoyan
 */
Ext.form.TextField.prototype.initValue = function(){
	if(!isNaN(this.maxLength)&&(this.maxLength*1)>0&&(this.maxLength!=Number.MAX_VALUE)){
		this.el.dom.maxLength = this.maxLength * 1;
	}
}

Ext.BLANK_IMAGE_URL = '../../images/s.gif';
function refreshCategoryById(id){
	treePanel.getNodeById(id).reload();
}
Ext.onReady(function(){
	Ext.QuickTips.init();
	var height = document.body.clientHeight - 10;  //屏幕高度
	var width = document.body.clientWidth - 10;  //屏幕宽度
	
    addCategoryHandler = function(){
 		var treeNode = treePanel.getSelectionModel().getSelectedNode();
 		if(treeNode != null){
	 		categoryAdd.showDialog('category_add');
	 		categoryFormPanel.getForm().reset();
	 		Ext.get("parentId_add").dom.value = treeNode.id;//1;
 		}else{
 			Ext.MessageBox.alert('提示', '请选择一个父菜单！');
 		}
 	}
 	editCategoryHandler = function(){
 		var treeNode = treePanel.getSelectionModel().getSelectedNode();
 		if(treeNode != null){
	 		categoryEdit.showDialog('category_edit');
	 		Ext.get("categoryid_edit").dom.value = treeNode.id;
	 		Ext.get("categoryname_edit").dom.value = treeNode.text;
	 		Ext.get("categorydesc_edit").dom.value = treeNode.attributes.categoryDesc;
	 		Ext.get("categoryChildNum_edit").dom.value = treeNode.attributes.childrennum;
	 		Ext.get("categoryPid_edit").dom.value = treeNode.attributes.parentid;
 		}else{
 			Ext.MessageBox.alert('提示', '请选择一个父菜单！');
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
			 				var parentid = treeNode.attributes.parentid;
			 				var menuid = treeNode.id;
			 				var parent = treePanel.getNodeById(parentid);
			 				parent.attributes.childrennum = parent.attributes.childrennum - 1;
							if (parentid != "0" && parent.attributes.childrennum == 0) {
								parentid = parent.parentNode.id;
							}
							refreshCategoryById(parentid);
				 			Ext.MessageBox.alert("提示","删除成功");
			 			}else{
			 				Ext.MessageBox.alert("提示","删除失败，该记录还有其它绑定不允许删除!");
			 			}
			 		}
			 	}
		 		
		 		if(treeNode != null){
		 			if(treeNode.isLeaf() && treeNode.id != 0){
						var id = treeNode.id;
						Ext.Ajax.request({
						   url: 'delCategory.action',
						   method:'post',
						   success: result.success,
						   failure: result.failure,
						   params: {categoryId: id}
						});
			 		}else{
			 			Ext.MessageBox.alert('提示', '请选择叶子节点进行删除!');	
			 		}	
		 		}else{
		 			Ext.MessageBox.alert('提示', '请选择一个父菜单！');
		 		}
	 	}
	 	});//////
 	}
 	
 	var tree = Ext.tree;
	var cview = Ext.DomHelper.append('main-ct',
        {cn:[{id:'main-tb'},{id:'cbody'}]}
    );
    var tb = new Ext.Toolbar('main-tb');
    tb.add({
        id:'category_add',
        text:'添加分类',
        handler:addCategoryHandler,
        disabled:false
    },'-',{
    	id:'category_edit',
    	text:'修改分类',
    	handler:editCategoryHandler,
    	disabled:false
    },'-',{
    	id:'category_del',
    	text:'删除分类',
    	handler:delCategoryHandler,
    	disabled:false
    });
    treePanel = new tree.TreePanel({
		id:'categoryTreeId',
		width : 250,
		border  : false,
 		frame   : false,
 		autoScroll:true,
		loader: new tree.TreeLoader({
			dataUrl:'searchCategory.action',
			listeners:{
				"beforeload": function(treeLoader, node){
					selectedNodeId = node.id;
					treeLoader.baseParams = {parentId:selectedNodeId};
				}
			}
		}),
		autoScroll:true,
		height: height-80,
		width:width-16,
		border: false
    });
    
    var root = new tree.AsyncTreeNode({
               	text: '下拉框数据管理',
               	id:'0'
    });
    treePanel.setRootNode(root);
    treePanel.render('cbody');
    root.expand(false,false);
    treePanel.on("click",function(node){
   		var btn  = tb.items.map;
   		btn.category_add.enable();
   		btn.category_del.enable();
   		btn.category_edit.enable();
   		if(node.id==0){ btn.category_del.disable();btn.category_edit.disable();}
    	if(node.id != 0){
    		if(node.isLeaf()){
    		}else{
    			btn.category_del.disable();
    		}
    	}
    	
    });
	var myBorderPanel = new Ext.Panel({
	    renderTo: 'main-ct',
	    height: height,
	    width:width,
	    title: '下拉框数据管理',
	    tbar:tb,
	    el : cview,
	    items:[treePanel]
	});
	checkbutton();
});

