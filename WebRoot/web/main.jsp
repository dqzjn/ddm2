<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tjtt.tdm.sysbase.action.ShowTree" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <base href="<%=basePath%>">

    <title>试验数据管理系统</title>
	
	<script src="<%=path %>/web/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="<%=path %>/web/js/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=path%>/js_css_image/lhgdialog/lhgcore.lhgdialog.min.js?skin=mac"></script>
	<script src="<%=path %>/web/js/function.js" type="text/javascript"></script>
	<link href="<%=path %>/web/style/layout.css" rel="stylesheet" type="text/css" />
	<style>
           body{background-repeat:repeat-x;background-color:#eaeaea; }
    </style>
    <script type="text/javascript">
    	$(document).ready( function(){
    		getInitCount();
    		
 //   		changeRight("<%=path %>/tip.jsp");
			
    	});
    	function refreshCount(para){
    		changeRight(para);
    		getInitCount();
    	}
		function getInitCount(){
			<%--$.ajax({
				type: "POST",
				async: true,//取消异步提交
				url: 'getUnreadMessageCount.action',
				dataType: "json",
				success : function(data, textStatus) {  
					if(data.ajaxResult=='success'){
						$("#unreadMessage").html("["+data.count+"]");
					}else{
						$("#unreadMessage").html("[0]");
					}
				}
			});--%>
			$.ajax({
				type: "POST",
				async: true,//取消异步提交
				url: 'getdaiqianCount.action',
				dataType: "json",
				success : function(data, textStatus) {  
					if(data.ajaxResult=='success'){
						$("#undoDaiqian").html("["+data.count+"]");
					}else{
						$("#undoDaiqian").html("[0]");
					}
				}
			});
			$.ajax({
				type: "POST",
				async: true,//取消异步提交
				url: 'getdaibanCount.action',
				dataType: "json",
				success : function(data, textStatus) {  
					if(data.ajaxResult=='success'){
						$("#undoWork").html("["+data.count+"]");
					}else{
						$("#undoWork").html("[0]");
					}
				}
			});
			<%--$.ajax({
				type: "POST",
				async: true,//取消异步提交
				url: 'getUndoWorkCount.action',
				dataType: "json",
				success : function(data, textStatus) {  
					if(data.ajaxResult=='success'){
						$("#undoWork").html("["+data.count+"]");
					}else{
						$("#undoWork").html("[0]");
					}
				}
			});--%>
			$.ajax({
				type: "POST",
				async: true,//取消异步提交
				url: 'getxiezuoCount.action',
				dataType: "json",
				success : function(data, textStatus) {  
					if(data.ajaxResult=='success'){
						$("#assistWork").html("["+data.count+"]");
					}else{
						$("#assistWork").html("[0]");
					}
				}
			});
		}
	</script>
	<script>
/**---------------------------------------------------------------------------
*/
{
var ua = navigator.userAgent;
var $IE = (navigator.appName == "Microsoft Internet Explorer");
var $IE5 = $IE && (ua.indexOf('MSIE 5') != -1);
var $IE5_0 = $IE && (ua.indexOf('MSIE 5.0') != -1);
var $Gecko = ua.indexOf('Gecko') != -1;
var $Safari = ua.indexOf('Safari') != -1;
var $Opera = ua.indexOf('Opera') != -1;
var $Mac = ua.indexOf('Mac') != -1;
var $NS7 = ua.indexOf('Netscape/7') != -1;
var $NS71 = ua.indexOf('Netscape/7.1') != -1;

if ($Opera) {
   $IE = true;
   $Gecko = false;
   $Safari = false;
}
if ($IE5) {
         $IE = true; 



         $Gecko = false;
         $Safari = false;
}
}
function $_t(root,tag,id){
var ar=root.getElementsByTagName(tag);
for (var i=0;i<ar.length;i++){
   if (ar[i].id==id) return ar[i];
}
return null;
}
function _(root){

var ids=arguments;
var i0=0;

if (typeof(root) == 'string') root = document;
else i0=1;

for (var i=i0;i<ids.length;i++){
   var s=root.getElementsByTagName("*");

   var has=false;
   for (var j=0;j<s.length;j++){
    if (s[j].id==ids[i]){
     root=s[j];
     has=true;
     break;
    }
   }
   if (!has) return null;
}
return root;
}
//util 


function $dele(o,fn,rv){

var r = function (){
  
   var s=arguments.callee;
  
   var args = [];
   for (var i=0;i<s.length;i++) args[i]=s[i];
   var argStr = args.join(",");
   if (argStr.length > 0) argStr=","+argStr;
  
   var callStr="s.thiz[s.fn]("+argStr+")";
   var v=eval(callStr);
  
  
   if (s.rv!=null) {
    return s.rv;
   } else {
    return v;
   }
}

r.thiz=o;
r.fn=fn;
r.rv=rv;

return r;
} 

function $ge(e){
if (e!=null) return e;
if ($IE) {
   return window.event;
} else return e;
} 


/**
* get event for a element;
*/
function $gte(e,ev){
if (!e.getElementById) e=e.ownerDocument;
if ($IE) {
   return ev!=null ? ev : e.parentWindow.event;
} else {
   return ev;
   throw new Error("this method can only execute in IE");
}
}
function $addEL(n,e,l,b){

if ($IE){
   if (n["$__listener_"+e]==null){
    var lst=function (e){
    
     var f=arguments.callee;
     var ar=f.fList;
    
     e=$ge(e);
     for (var i=0;i<ar.length;i++){     
      ar[i](e);
     }
    }
    lst.fList=[];   
    n["$__listener_"+e]=lst;
    n["on"+e]=n["$__listener_"+e]; 


   
   }
   var fList=n["$__listener_"+e].fList;
   fList[fList.length]=l;
  
} else {
   n.addEventListener(e,l,b);
}
}
function $cancelEvent (e) {
if ($IE) {
   e.returnValue = false;
   e.cancelBubble = true;
} else
   e.preventDefault();
};
function $cpAttr(o,p){
for (var i in p){
   var s=p[i];
   o[i]=s;
}
return o;
}
function $getValue(v,d){
return v==null ? d : v;
}
var $gv=$getValue; 


var $dom={
parseInt : function(s) {
   if (s == null || s == '' || typeof(s)=='undefined')
    return 0; 

   return parseInt(s);
},
getClientSize : function(n){
   if ($IE){
    //ts("this is ie");
    var s= {x:n.clientLeft,y:n.clientTop};
    s.l=s.x;
    s.t=s.y;
    s.r=n.clientRight;
    s.b=n.clientBottom;
   
    s.w=n.clientWidth;
    s.h=n.clientHeight;
   
    //tr("calculated client size");
   
    return s;
   } else {
    var t=n.style;
    if (t.borderLeftWidth.length==0 || t.borderTopWidth.length==0 || t.borderRightWidth.length==0 || t.borderBottomWidth.length==0){
    
     var l=n.offsetWidth;
     t.borderLeftWidth="0px";
     l-=n.offsetWidth; 


    
     var r=n.offsetWidth;
     t.borderRightWidth="0px";
     r-=n.offsetWidth;
    
     var o=n.offsetHeight;
     t.borderTopWidth="0px";
     o-=n.offsetHeight;
    
     var b=n.offsetHeight;
     t.borderBottomWidth="0px";
     b-=n.offsetHeight;
    
     t.borderLeftWidth=l+"px";
     t.borderTopWidth=o+"px";
     t.borderRightWidth=r+"px";
     t.borderBottomWidth=b+"px";
    
     var s={l:l,r:r,t:o,b:b,x:l,y:o};
    
     
     return s;
    } else {
     var s= {
       x: this.parseInt(n.style.borderLeftWidth),
       y: this.parseInt(n.style.borderTopWidth),
       r: this.parseInt(n.style.borderRightWidth),
       b: this.parseInt(n.style.borderBottomWidth)
      };
     s.l=s.x;
     s.t=s.y;
     return s;
    }
   }
},



getSize : function (n,withMargin){
   var c={
    x : n.offsetWidth != null ? n.offsetWidth : 0,
    y : n.offsetHeight != null ? n.offsetHeight : 0
   };
  
   //c.x=this.parseInt(c.x);
   //c.y=this.parseInt(c.y);
   

   //tr("get size for : "+n.id);
   //tra(c);
   if (withMargin) {
    var m=this.getMargin(n);
    c.x+=m.l+m.r;
    c.y+=m.t+m.b;
   }
   //tra(m);
   //tr("get size for : "+n.id);
   //tra(c);
   return c; 
},

setSize : function(elmt,x,y,withMargin){
   //tf("$dom::setSize");
   //if (elmt==undefined || elmt.style.display=="none") return;
   if ($IE){
    if (withMargin){    
     var m=this.getMargin(elmt);
     x-=m.l+m.r;
     y-=m.t+m.b;    
    }   
    elmt.style.width=x;   
    elmt.style.height=y;    
   } else {
    var clientSize=this.getClientSize(elmt);
    var dx=clientSize.l+clientSize.r;
   
    var dy=clientSize.t+clientSize.b;
   
    elmt.style.width=x-dx+"px";
    elmt.style.height=y-dy+"px";
   }
},

/**
* get the context position relative to its parent.
*/
getPosition : function (elmt,withMargin){
   var c;
  
   c={
    x:elmt.offsetLeft,
    y:elmt.offsetTop
   };
   //c.x=this.parseInt(c.x);
   //c.y=this.parseInt(c.y);
   if (withMargin){
    var m=this.getMargin(elmt);
    c.x-=m.l;
    c.y-=m.t;
   }
  
   return c;
},
setPosition : function (elmt,x,y,withMargin){ 


   //tf("$dom::setPosition");
   if (withMargin){
    //var m=this.getMargin(elmt);
    //x-=m.l;
    //y-=m.t;
   } 
   elmt.style.left=x+"px";
   elmt.style.top=y+"px";
},
  

setAlpha : function (n,a){
   return;
   n.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity="+a*100+");";
   n.style.opacity = a;
   n.style.MozOpacity = a;
}

}
var $motion={
smooth : function (s, e, t){
   if (t>1) t=1;
   return (e - s) * t + s;
}
} 

function PopUp(id, config){
this.id=id;

var c = this.config = config;
c.width = $gv(c.width,300);
c.height = $gv(c.height,200);
c.bottom = $gv(c.bottom,0);
c.right = $gv(c.right,20);
c.display = $gv(c.display,true);
c.contentUrl= $gv(c.contentUrl,"");
c.motionFunc= $gv(c.motionFunc,$motion.smooth);
c.position = {x:0,y:0};

var t=c.time;
t.slideIn = $gv(t.slideIn,10);
t.hold   = $gv(t.hold,10);
t.slideOut = $gv(t.slideOut,10);

t.slideIn *= 1000;
t.hold   *= 1000;
t.slideOut *= 1000;

this.container = document.body;
this.popup = null;
this.content = null;
this.switchButton = null;

this.moveTargetPosition = 0;
this.startMoveTime = null;
this.startPosition = null; 



this.status = PopUp.STOP;
this.intervalHandle = null;

this.mm = "max";

this.imgMin = "images/min.gif";
this.imgMax = "images/max.gif";
} 

//static members
PopUp.STOP = 0;
PopUp.MOVE_DOWN = 1;
PopUp.MOVE_UP = 2;
PopUp.SWITCH_TO_MIN = PopUp.MOVE_DOWN | 4;
PopUp.SWITCH_TO_MAX = PopUp.MOVE_UP | 8; 


var __o={
create : function (){
  
   var doc=document;
   var c=this.config; 
  
   //create popup holder & config it.
   var p = this.popup = doc.createElement("div");
   this.container.appendChild(p);
  
   p.id=this.id;
   p.style.cssText="position:absolute;\
       z-index:9000;\
       overflow:hidden;\
       border:0px solid #f00;\
       ";
   $dom.setSize(p, c.width, c.height);
  
  
  
   //create popup content holder & config it.
   var t = this.content = doc.createElement("div");
   p.appendChild(t);
  
   t.id = this.id+"_content"; 
   t.style.cssText="position:absolute;\
       z-index:1;\
       overflow:hidden;";
   $dom.setSize(t, c.width, c.height);
   $dom.setPosition(t,0,0);//add
  
   c.position.y = c.height;//add
   this.onresize();//add
   //$dom.setPosition(t, 0, c.height);//hide it at first
  
  
  
   // create content holder's content.
   // a close button & an iframe for loading external content.
   t.innerHTML = "<a id='closeButton' href='#'></a>"+
          "<a id='switchButton' href='#'></a>"+
           "<iframe id='"+this.id+"_content_iframe' src="+c.contentUrl+" frameborder=0 scrolling=no width='100%' height='100%' style='height:100%'></iframe>"; 


  
  
   var sBtn = this.switchButton = $_t(t,'a',"switchButton");
   sBtn.style.cssText='position:absolute;\
        z-index:2;\
        \
        font-size:0px;\
        line-height:0px;\
        \
        left:220px;\
        top:6px;\
        width:15px;\
        height:15px;\
        \
        background-image:url("images/min.gif");';

   $addEL(sBtn,"click",$dele(this,"switchMode"),true);
   $addEL(sBtn,"click",$cancelEvent,true); 
  
  
   var btn = $_t(t,'a',"closeButton");
   btn.style.cssText='position:absolute;\
        z-index:2;\
        \
        font-size:0px;\
        line-height:0px;\
        \
        left:240px;\
        top:6px;\
        width:15px;\
        height:15px;\
        \
        background-image:url("images/close.gif");';
  
  
  
   $addEL(btn,"mouseover",function (e){ 

           $dom.setAlpha(this,0.4);
           },true);
  
   $addEL(btn,"mouseout",function (e){
           $dom.setAlpha(this,1);
           },true);
           
  
  
   $addEL(btn,"click",$dele(this,"hide"),true);
   $addEL(btn,"click",$cancelEvent,true);
  
   var container=$IE ? document.body : document.documentElement;
  
   $addEL(document.body,"resize",$dele(this,"onresize"),true);
  
    this.__hackTimer=window.setInterval("__popup.onresize()",50);
  
   
   $addEL(container,"scroll",$dele(this,"onresize"),true);
  
   //initialize position at once.
   this.onresize();
  
},

show : function (){
  
   if (!this.config.display) return;
  
   this.moveTargetPosition = 0;
   this.status = PopUp.MOVE_UP;
   this.startMove();
},

hide : function (){
  
   this.moveTargetPosition = this.config.height;
   this.status = PopUp.MOVE_DOWN;
   this.startMove();
},

minimize : function (){
   //alert("minimize");
   this.mm = "min";
   this.moveTargetPosition = this.config.height - 28;
   this.status = PopUp.SWITCH_TO_MIN;
   this.startMove();
  
   var s = this.switchButton.style; 


   var bg = s.backgroundImage;
  
   if (bg.indexOf(this.imgMin) > -1) {
    bg = bg.replace(this.imgMin,this.imgMax);
    s.backgroundImage = bg;   
   }
},

maximize : function (){
   //alert("maximize");
   if (!this.config.display) return;
  
   this.mm = "max";
   this.moveTargetPosition = 0;
   this.status = PopUp.SWITCH_TO_MAX;
   this.startMove();
  
  
   var s = this.switchButton.style;
   var bg = s.backgroundImage;
  
   if (bg.indexOf(this.imgMax) > -1) {
    bg = bg.replace(this.imgMax,this.imgMin);
    s.backgroundImage = bg;   
   }
},

delayHide : function (){   


   window.setTimeout("__popup.hide()",this.config.time.hold);
},

delayMin : function (){
   window.setTimeout("__popup.minimize()",this.config.time.hold);
},

switchMode : function (){
   //alert("switch");
   if (this.mm == "min"){
    this.maximize();
   } else {
    this.minimize();
   }
},

startMove : function (){
   this.stopMove();
  
   this.intervalHandle = window.setInterval("__popup.move()",100);
  
   this.startMoveTime = new Date().getTime();
   //this.startPosition = $dom.getPosition(this.content).y;//parseInt(this.content.style.top);
   this.startPosition = this.config.position.y;
},

stopMove : function (){
   if (this.intervalHandle != null) window.clearInterval(this.intervalHandle); 

   this.intervalHandle = null;
},


move : function (){
  
  
   var t = new Date().getTime();
   t = t - this.startMoveTime;
  
   var total = this.status & PopUp.MOVE_UP ? 
      this.config.time.slideIn : 
      this.config.time.slideOut;
  
   var y = this.config.motionFunc(this.startPosition, this.moveTargetPosition, t/total);
   //this.content.style.top = y + "px";
   this.config.position.y = y;
   this.onresize();
    
   if (t >= total){
    this.onFinishMove();
   }
},

onFinishMove : function (){
   this.stopMove();
   //this.content.style.top = this.moveTargetPosition + "px";
  
   if (this.status == PopUp.MOVE_UP && this.config.time.hold > 0 ){ 

    this.delayMin();
   } else {
    if (this.__hackTimer!=null) window.clearInterval(this.__hackTimer);
   }
   this.status = PopUp.STOP;
},

onresize : function (){
   var c=this.config;
   //var t=document.documentElement;
   var t=document.body;
  
   var dx=t.clientWidth + t.scrollLeft;
   var dy=t.clientHeight + t.scrollTop;
  
   var x = dx - c.right - c.width ;
   var y = dy - c.bottom - c.height + c.position.y;
  
  
   $dom.setPosition(this.popup, x, y); 
   $dom.setSize(this.popup, c.width, c.height-c.position.y);
}
} 

$cpAttr(PopUp.prototype,__o); 


/*---------------------------------------*/ 

function readCookie(name)
{
var cookieValue = "";
var search = name + "=";
if(document.cookie.length > 0)
{ 
    offset = document.cookie.indexOf(search);
    if (offset != -1)
    { 
      offset += search.length;
      end = document.cookie.indexOf(";", offset);
      if (end == -1) end = document.cookie.length;
      cookieValue = unescape(document.cookie.substring(offset, end))
    }
}
return cookieValue;
} 



function writeCookie(name, value, hours)
{
var expire = "";
if(hours != null)
{
    expire = new Date((new Date()).getTime() + hours * 3600000);
    expire = "; expires=" + expire.toGMTString();
}
document.cookie = name + "=" + escape(value) + expire + ";path=/";
} 

/**
* main function to config the pop-up window & run it.
* web deployer change codes here to manipulte popups performance.
* & should not change codes out of this function.
*/
function job(){ 

/**
* config object
*/
var cfg={
   //width & height of the popup window ,these values should be determined debpended on inner contents.
   width     : 260,
   height     : 28,
  
   //distance to the bottom & the right edge.
   bottom    : 2,
   right    : 2,
  
   //switch of displaying the popup
   display    : true,
   
   //content url
   contentUrl   : "tip.jsp",
  
   //time configuration,in seconds
   time : {
    slideIn    : 1,
    hold     : 1,
    slideOut   : 1
   }      
}

//at what time the popup should display,in hours : 0~23,
//the number after add symbol means after how many the hours to display popup for the next time. 
var displayTimeList = ["7+7"];

// the popup displays each time thie page reload or only once at the first time page loaded.
// once / eachTime
//var displayMode = "once";
var displayMode = "eachTime";

//cookie name storing the next time to display popup
var cookieName="sina_blog_popup_next_display_time";



/**
* --------------------- from here below, the codes should NOT be modified.
*/
var hours={};
var delays=[];
for (var i=0;i<displayTimeList.length;i++) {
   var o = displayTimeList[i];
   var ar = o.split("+");
   var t = parseInt(ar[0]);
   for (var m=0;m<ar.length-1;m++){
      ar[m]=ar[m+1];
   }   
   hours[t]=true;
   for (var j=0;j<ar.length;j++){
    hours[t + parseInt(ar[j])]=true;
   }  
}
displayTimeList=[];
for (var i in hours){
   var s = parseInt(i);
   if (isNaN(s)) continue;
   displayTimeList[displayTimeList.length]=s;
}
displayTimeList = displayTimeList.sort();
//alert(displayTimeList);


var pp = new PopUp("xp", cfg);
window.__popup=pp;
pp.create();



//display:

var n=readCookie(cookieName); 

if (displayMode=="eachTime") 
   pp.show();
else {
   var tm=new Date().getTime();
   if (n==null || tm>n) {
    pp.show();
   
    //get next display time
    var hr=new Date().getHours();
    var f = 60*60*1000;
    var l = displayTimeList.concat(), len = l.length; 
    for (var i = 0; i < len; i++) l[len + i] = l[i] + 24;
    for (var i = 0; i < l.length && hr >= l[i]; i++);
   
    var dt = new Date();
    dt.setHours(l[i] > 23 ? l[i] - 24 : l[i]);
    var nextTime = dt.getTime();
    if (l[i] > 23) nextTime += f * 24 ;
   
    writeCookie(cookieName, nextTime, 24);
   }
}
} 



function doit(){ 


if(document.readyState == 'loaded' || document.readyState == 'complete'){ 
   job();
}else{
window.setTimeout(doit,500);
   return;
   }

} 



doit(); 
</script>
  </head>
  
  <body id="homepage" style="background:url(<%=path %>/web/images/bg_body_left.png); background-repeat:repeat-y; background-position:left;background-color:#eaeaea;">
<div class="header">
  <div class="header_img">
   <div >
	    <div class="logo"><img src="web/images/logo.png" /></div>
	</div>
  <div id="systemmenu">
    <p class="left smltxt">
    <DIV style="margin-top:15px;">
    <a href="web/main.jsp"><img src="web/images/top_icon1.png" border="0" title="首页" /></a> 
    <img src="web/images/top_icon2.png" border="0" title="系统公告" onclick="refreshCount('message/MessageListView.jsp?type=0')" style="cursor: pointer"/>
    <img src="web/images/top_icon3.png" border="0" title="站内短消息" onclick="refreshCount('message/MessageListView.jsp?type=1')" style="cursor: pointer"/>
    <a href="help.doc"><img src="web/images/top_icon4.png" border="0" title="帮助" /></a> 
    <img src="web/images/top_icon5.png"  onclick="logout()" style="cursor: pointer" border="0" title="退出" />
    </DIV>
    </p>
  </div>
  </div>
</div>
<div id="position">
  <ul>
    <li onclick="toggleMenu()">
      <div id="toggle_menu" class="toggle_open"  title="关闭左侧菜单"></div>
    </li>
    <li>位置：</li>
    <li><span class="index">首页</span></li>
    <li>/</li>
    <li class="current">欢迎页面</li>
  </ul>
</div>
<div id="rightside">
  <div id="contentside">
  	<iframe scrolling="no" src="portletAction!getPortlet.action" width="100%" frameborder="0" id="mainframe" onLoad="iFrameHeight()"> </iframe>
  </div>
  <div id="contentdetail" style="display: none;"></div>
  <div style="clear: both;"></div>
  <div class="footer"> <span class="copyright">Copyright &copy; 河南柴油机重工业有限责任公司.All Rights Reserved.&nbsp;&nbsp;技术支持：北京天健通泰科技有限公司</span> <span class="sy_logo"><img src="web/images/shenying_logo.gif" /></span> </div>
</div>
<div id="leftside">
  <div class="user"> <img id="avatar" src="web/images/avatar.png" onerror="this.onerror=null;this.src='web/images/avatar.png';" width="48" height="48" class="hoverimg" />
    <p>欢迎登录：<br />
    </p>
    <p class="username"><%=session.getAttribute("username") %></p>
    <div class="message">
      <ul>
        <li title="待办事项"><img src="web/images/left_log_icon2.gif" alt="待办事项" /><span id="undoWork" style="cursor:pointer;color:red;" onclick="refreshCount('workflow/haslaunched_worklist.jsp')"></span></li>
      </ul>
    </div>
    <div style="float:left">
      <p class="userbtn"><a class="fancybox fancybox.ajax" title=""><span onclick="changeRight('user/updatePwd.jsp')" style="cursor: pointer">修改密码</span></a></p>
      <p class="userbtn"><a class="fancybox fancybox.ajax" title=""><span onclick="refreshCount('message/MessageListView.jsp?type=0')" style="cursor: pointer">查看公告</span></a></p>
      <p class="userbtn"><a title=""><span  onclick="logout()" style="cursor: pointer" title="">退出</span></a></p>
    </div>
  </div>
  <ul id="menu">
    <%out.println(ShowTree.createXpMenu(request)); %>
  </ul>
</div>
<div id="ajax-loader" style="display:none;position:fixed;right:0;bottom:0;"><img src="web/images/loading.gif" alt="loading" /></div>
<div id="dialog"></div>
<script><!--
		function iFrameHeight() { 
			var ifm= document.getElementById("mainframe"); 
			var subWeb = document.frames ? document.frames["mainframe"].document : ifm.contentDocument; 
			if(ifm != null && subWeb != null) { 
				ifm.height = subWeb.body.scrollHeight+30; 
			} 
		} 
        var deskStr = '<div style=" text-align:center;"><img src="web/images/subbg.png" width="650" /></div>';
        function setMyDesk() {
            jQuery("#contentside").html(deskStr);
            setPosition("欢迎页面");
        }
        function openDialog(URL) {
            window.open(URL, "Map", "location=0,width=" + jQuery(document).width() + ",height=" + jQuery(document).height() + ",left=0,top=0,menubar=0,center=1,help=0,scroll=0,toolbar=0,resizable=1,status=0");
        }
        function toggleMenu() {
            var speed = 500;
            if (jQuery("#leftside").is(':visible')) {
                jQuery("#rightside").animate({ "margin-left": "20px" }, speed);
                jQuery("#position").animate({ "margin-left": "0px" }, speed);
                jQuery("body").css("background", "#EAEAEA");
                jQuery("#leftside").animate({ width: "0px" }, speed, "", function () { jQuery(this).hide() });
                jQuery("#toggle_menu").removeClass().addClass("toggle_close").attr("title", "打开左侧菜单");
            } else {
                jQuery("#rightside").animate({ "margin-left": "250px" }, speed);
                jQuery("#position").animate({ "margin-left": "225px" }, speed);
                jQuery("#leftside").show().animate({ width: "225px" }, speed, "", function () { jQuery("body").css("background", "#EAEAEA url(web/images/bg_body_left.png) repeat-y left") });
                jQuery("#toggle_menu").removeClass().addClass("toggle_open").attr("title", "关闭左侧菜单");
            }
        }
        function changeRight(url){
            jQuery("#mainframe").attr("src",url);
            window.scrollTo(50,50); 
<%--			    jQuery.get(url, function(result){  --%>
<%--			        jQuery("#contentside").html(result);  --%>
<%--			    });  --%>
        }
        /**
         * 在页面上产生个gotop按钮。
         * 用纯粹的JS实现，无须额外的CSS和HTML支持。
         *
         * @param int width 网页的主体宽度，以下三种取值
         * - 0 按钮靠浏览器左
         * - -1 按钮靠流利器右
         * - 其它正数 按钮靠网页内容右侧
         * @return void
         * @link http://blog.830725.com/post/add-goto-top-button.html
         */
        function gotoTop(width)
        {
          document.write('<a id="goto-top"><img src="web/images/top.png" /></a>');
          var gotop = document.querySelector('#goto-top');
          /* 默认情况下CSS属性的设置 */
          gotop.style.visibility='hidden';
          gotop.style.cursor='pointer';
          gotop.style.position = 'fixed';
          gotop.style.fontSize='2.5em';
          gotop.style.fontWeight='900';
          gotop.style.textAlign='center';
          gotop.style.background = 'gray';
          gotop.style.borderRadius = '0.2em';
          gotop.style.width='1.4em';
          gotop.style.height='1em';
          gotop.style.top = (document.documentElement.clientHeight / 2) + 100 + 'px';
          gotop.style.opacity='0.3';
          gotop.style.visibility = (document.body.scrollTop + document.documentElement.scrollTop > 10) ? 'visible' : 'hidden';
          if(0 == width)
          { gotop.style.left = '0em';  }
          else if(-1 == width)
          { gotop.style.right = '0em';  }
          else
          {
          var resize = function()
          {
          var left = (document.documentElement.clientWidth - width) / 2 + width + 10;
          if((left - gotop.clientWidth) < width)
          {
          gotop.style.right='0em';
          gotop.style.left = null;  // 设定了right属性，则需要取消left属性。
          }
          else
          {
          gotop.style.left = left + 'px';
          gotop.style.right = null;
          }
          };
          resize();
          window.addEventListener('resize', function()
          {
          resize();
          }, false);
         
          }
          if (gotop!='')
          {
	          gotop.addEventListener('mouseover', function()
	          {
	          this.style.opacity='0.8';
	          this.style.textDecoration='none';
	          }, false);
	          gotop.addEventListener('mouseout', function()
	          {
	          this.style.opacity='0.3';
	          }, false);
	          gotop.addEventListener('click', function()
	          {
	          // IE9和opera下body.scrollTop为0，chrome下documentElement.scrollTop为0
	          // 两者始终有一个为0
	          var h = document.body.scrollTop + document.documentElement.scrollTop; // 当前位置
	          var t = window.setInterval(function()
	          {
	          window.scrollTo(0,h -= 100); // 每次上移100像素
	          if(h <= 0)
	          { window.clearInterval(t);  }
	          }, 5);
	          }, false);
	          /* 通过window.onscroll事件确定按钮是否需要显示 */
	          window.addEventListener('scroll', function()
	          {
	          var scrollTop = document.body.scrollTop + document.documentElement.scrollTop;
	          gotop.style.visibility = scrollTop > 10 ? 'visible':'hidden';
	          }, false);
          }
          
        };
        gotoTop(-1);
    --></script>
</body>
</html>
