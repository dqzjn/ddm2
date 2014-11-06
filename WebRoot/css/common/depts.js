/** 使用此选择单位功能的模块，需要实现deal_deptinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选，通过title指定window窗口的名字
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.deptrole = Ext.extend(Ext.Window,{
		constructor: function(config) {
			var tree = this.initTreeData(config);
			this.tree = tree;
			var title = "部门";
			if(config.title!=null && config.title!=''){
				title = config.title;
			}
			var win = this;
			var con = {
				items:[tree],
				title:title,
				height:450,
				buttonAlign : 'center',
				width:450,
				buttons : [{
					text : '确定',
					handler : function (){
							var objData;
							if(config.isSingle==false || config.isSingle=='false'){
								objData = win.tree.getChecked();
							}else{
								objData=win.tree.getSelectionModel().getSelectedNodes();
							}
			                if(objData==null || objData.length==0){
			                	Ext.MessageBox.alert("提示", "请选择一条记录");
			                	return;
			                }
			                //用到用户选择功能的模块需要自己实现
			                //用到用户选择功能的模块需要自己实现
			                if(null != config.handler && config.handler!=''){
			                	config.handler(objData);
			                }else{
			                	deal_deptinfo_common(objData);
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
    	initTreeData:function(config){
    		var flag = true;
    		if(null != config.isSingle && (config.isSingle==false || config.isSingle=='false')){
    			flag = false;
    		}
		    var treePanel = new Ext.tree.TreePanel({
		     		title:'流程分类',
		     		autoScroll:true,
		     		rootVisible:true,
		     		height:400,
		     		root:new Ext.tree.AsyncTreeNode({
			               	text: '单位根节点',
			               	id:-1
				    }),
		     		selModel:new Ext.tree.MultiSelectionModel(),
		            loader: new Ext.tree.TreeLoader({
		           		dataUrl:common_contextPath+'/common/searchOrgCommon.action?dptChecked='+flag,
	            		listeners:{
	            			"beforeload": function(treeLoader, node){
	      						selectedNodeId = node.id;
								treeLoader.baseParams = {orgId:selectedNodeId};
	     					}
	     				}
		     		}),
		           border: false
		    });
		    treePanel.getRootNode().expand(false,false);
		    return treePanel
	    },
	    /**
	     * 清表的数据
	     */
	    clearSelect:function(){
	    	this.tree.getSelectionModel().clearSelections();
	    }
	});
