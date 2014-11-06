/** 使用此选择单位功能的模块，需要实现deal_deptinfo_common(datas)方法，
 *  其中datas为选择的用户
 * 	用户可以通过制定isSingle指定是否可以多选，false：可以多选，true:单选，通过title指定window窗口的名字
 * **/
Ext.ns('TDMPlatform.common');
TDMPlatform.common.bindconn = {
		initBindconnWin: function(config) {
			config.window = window;
			window.showModalDialog(common_contextPath+'/common/bindconn/bindconnShow.jsp?',config,'dialogWidth:600px;dialogHeight:512px;scroll:yes;status:yes; resizable:yes;');
	    }
};
