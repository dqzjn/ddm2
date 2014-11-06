var firstTitle = "";
$(document).ready(function () {
    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });
    $('.navigation a[_url],#systemmenu a[_url]').click(function () {

        var ajaxloader = $("#ajax-loader");

        ajaxloader.show();
        $("#contentdetail").hide();
        $("#position .index").html(firstTitle);
        $("#position .current").html($(this).text());
        var customSrc = $(this).attr('_url');
        var appName = $(this).attr('href').replace('#', '');
        var _h = $(this).attr('_h');

        var appDir = customSrc || '/' + appName + '/';
        if ($(this).attr('mode') == 1) {
            var template = "<div class='iframe_loader' id='app_loader_" + appName + "'><iframe frameborder='0' src='" + appDir + "' width='100%' height='" + (_h || "100%") + "' scrolling='no' allowtransparency='true'></iframe></div>";
            $('#contentside').html("").append(template).show();
        }
        else {
            if ($(this).attr("_channelid")) {
                $("#contentside").load(appDir, { ChannelID: $(this).attr("_channelid") }, function () {
                    $("#leftside").css("height", "100%");
                    ajaxloader.fadeOut(2000);
                }).show();
            } else {
                $("#contentside").load(appDir,  function () {
                    $("#leftside").css("height", "100%");
                    ajaxloader.fadeOut(2000);
                }).show();
            }
        }
        var tag = $(this).parent();
        $(".navigation li").removeClass("heading selected");
        tag.addClass("heading selected").siblings();
    });
    //初始化fancybox
    $(".fancybox").fancybox({ 'modal': true });
    //导航控制
  	$(".collapsed + ul").slideToggle('medium');  
	var temp;
    $("#menu > li > a").click(function () {
		if (temp==undefined)
		{
			temp = $(this)[0].innerText;
			$(".expanded + ul").slideUp('medium');
			$(this).parent().find('> ul').slideDown('medium')
			$(".expanded").addClass('collapsed');
			$(".expanded").removeClass('expanded');
			$(this).toggleClass('expanded').toggleClass('collapsed')
		}
		else
		{
			if (temp==$(this)[0].innerText)
			{
				$(this).parent().find('> ul').slideToggle('medium');
				$(this).toggleClass('expanded').toggleClass('collapsed')
			}
			else
			{
				$(".navigation").slideUp('medium');
				$(".expanded").addClass('collapsed');
				$(".expanded").removeClass('expanded');
				$(this).toggleClass('expanded').toggleClass('collapsed').parent().find('> ul').slideDown('medium');

			}
			temp = $(this)[0].innerText;
		}
		//$(".expanded + ul").slideUp('medium');
	    //$(this).parent().find('> ul').slideDown('medium')
		//$(".expanded").addClass('collapsed');
		//$(".expanded").removeClass('expanded');
        //$(this).toggleClass('expanded').toggleClass('collapsed').parent().find('> ul').slideToggle('medium');
    });
    //默认点击
    $(".navigation a[index=true]").trigger("click");
});
//退出系统
function logout() {
    $.ajax({
        dataType: 'text',
        type: "POST",
        url: "/Login/Logout/",
        beforeSend: function () { loadingMask("加载中"); },
        error: function (XMLHttpRequest, textStatus) { if (XMLHttpRequest.status == 500) { alert(XMLHttpRequest.responseText); } },
        success: function (data) {
            $().toastmessage('showToast', {
                sticky: true,
                text: "已成功退出系统！",
                type: 'success',
                closeButton: false
            });
            setTimeout(function () {
                window.location.replace("/");
            }, 1500);
        }
    });
}
//确认删除信息
function confirmMessage(str,fun,chk) {
    if ($("input[name=SelectID]:checked").length <= 0 && chk) {
        $().toastmessage('showToast', {
            text: "你没有选中任何信息",
            type: 'error',
            mask: false
        });
        $("#page-mask").remove();
        return false;
    }
    var HTML = "<div>" + str + "</div><div style='padding-left:75px; padding-top:15px;'><input type='button' value='确认' class='btn' onclick='" + fun + ";' /></div>";
    $().toastmessage('showToast', {
        sticky: true,
        text: HTML,
        type: 'warning'
    });
}
function firstWay(title){
	firstTitle = title;
}
function errorMessage() {
    $().toastmessage('showToast', {
        text: '未知错误，请稍后再试！',
        type: 'error'
    });
}
function noticeMessage(str) {
    $().toastmessage('showToast', {
        text: str,
        type: 'notice',
        closeButton: false
    });
}
function loadingMask(str) {
    $().toastmessage('showToast', {
        sticky: true,
        text: "<div style='text-align:center;'>" + str + "<img src='../../Content/BackEnd/1.gif'></div>",
        type: 'notice',
        closeButton: false
    });
}
function loadingPageMask() {
    $('<div id="page-mask"></div>').css({ 'filter': 'alpha(opacity=50)', '-moz-opacity': '0.5', '-khtml-opacity': '0.5', 'opacity': '0.5', 'position': 'absolute', 'z-index': '9998', 'top': 0, 'left': 0, 'width': '100%', 'height': '1px', 'display': '', 'background': '#fff' }).appendTo('.contentbox').animate({ opacity: 0.5,height:'100%' }, 500);
    $("#checkboxall").attr("checked", false);
}
//导航显示
function setPosition(list, detail) {
    if (!detail) {
        $("#position .current").html(list);
    }
    else {
        $("#position .current").html(detail);
    }
}
//全选
function checkAll() {
    $("#checkboxall").click(function () {
        var checked_status = this.checked;
        $("input[name=SelectID]").each(function () {
            if (!$(this).attr("disabled"))
                this.checked = checked_status;
        });
    });
    $("#dataTable tr:gt(0):odd").attr("class", "odd");
    $("#dataTable tr:gt(0):even").attr("class", "alt");
    $("#dataTable tr:gt(0)").hover(
    function () {
        $(this).addClass('mouseover');
    },
    function () {
        $(this).removeClass('mouseover');
    });
    $("#ajax-loader").fadeOut(2000);
    $(".runbutton").button();
}
//自动选中下拉框内的值
function checkSelect(Voption, Value) {
    var obj = document.getElementById(Voption);
    if (obj) {
        for (var i = 0; i < obj.length; i++) {
            if (obj.options[i].value == Value) {
                obj.options[i].selected = true;
                break;
            }
        }
    }
}
//切换列表与详细页
function switchPage(p) {
    var cd = $("#contentdetail"); //详细页DIV
    var cs = $("#contentside"); //列表页DIV
    if (p.length == 0) {//没有详细页，表示返回到列表页
        cd.toggle(500);
        cs.toggle(500);
    }
    else {//列表页隐藏
        cd.toggle(500);
        cs.toggle(500);
    }
}
//获取<option>格式的角色
function getSelectRole(obj, val, adm) {
    $.ajax({
        dataType: 'text',
        type: "POST",
        url: "/AdminRole/GetSelectRole",
        data: { Admin: adm },
        error: function (XMLHttpRequest, textStatus) { if (XMLHttpRequest.status == 500) { alert(XMLHttpRequest.responseText); } },
        timeout: 10000,
        success: function (data) {
            $("#" + obj).html(data);
            checkSelect(obj, val);
        }
    });
}

//判断是否为日期格式
function IsDate(str) {
    if (str == "") return true;
    var ghnn
    ghnn = str.split("/");
    if (ghnn.length != 3) ghnn = str.split("-");
    if (ghnn.length == 3) {
        return checkDate(ghnn[0], ghnn[1], ghnn[2]);
    } else {
        return false;
    }
}
function checkDate(arg_intYear, arg_intMonth, arg_intDay) {
    if ((parseInt(arg_intMonth) - 3 != arg_intMonth - 3) || (parseInt(arg_intMonth) > 12) || (parseInt(arg_intMonth) < 1)) {
        return false;
    }
    else {
        if ((parseInt(arg_intDay) - 3 != arg_intDay - 3) || (parseInt(arg_intDay) > 31) || (parseInt(arg_intDay) < 1)) {
            return false;
        }
        else {
            if ((parseInt(arg_intYear) - 3 != arg_intYear - 3) || (parseInt(arg_intYear) < 1000) || (parseInt(arg_intYear) > 5000))
                return false;
        }
    }
    return true;
}
function logout(){
	$.dialog({
	    content: '<b>您确定要退出吗?</b>',
	    title:'系统提示', 
	    min:true, //是否显示最小化按钮
        max:false,//是否显示最大化按钮
        fixed:false,//开启静止定位
        lock:true,//开启锁屏
        focus:true,//弹出窗口后是否自动获取焦点（4.2.0新增）
        time:null,//设置对话框显示时间
        resize:true,//是否允许用户调节尺寸
        drag:true,//是否允许用户拖动位置
	    width: '250px',
	    height: 100,
	    ok: function(){
			window.location.href="userLoginOut.action";
	        return false;
	    },
	    cancelVal: '关闭',
	    cancel: true /*为true等价于function(){}*/
	});
}