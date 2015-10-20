<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/privilege" prefix="privilege"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理系统</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/js/date.js"></script>
<script type="text/javascript">
//调用
var D = new Date();
var yy = D.getFullYear();
var mm = D.getMonth() + 1;
var dd = D.getDate();
var ww = D.getDay();

if (ww == 1) {
	ww = "一";
} else if (ww == 2) {
	ww = "二";
} else if (ww == 3) {
	ww = "三";
} else if (ww == 4) {
	ww = "四";
} else if (ww == 5) {
	ww = "五";
} else if (ww == 6) {
	ww = "六";
} else if (ww == 0) {
	ww = "日";
}

var ss = parseInt(D.getTime() / 1000);
if (yy < 100)
	yy = "19" + yy;

function getDate() {
	return yy + "年" + mm + "月" + dd + "日    " + "星期" + ww;
}
function getCNDate() {
	return GetLunarDay(yy, mm, dd);
}

/**
	 * 页面加载初始化
	 */
	jQuery(function() {
	
		var sys_date = "公元:" + getDate() + "&nbsp;&nbsp;&nbsp;农历:" + getCNDate();
		document.getElementById("sys_date").innerHTML = sys_date;
	
	});
</script>

</head>

<body class="easyui-layout">   
    
    <div data-options="region:'east',split:true" style="width:300px;">
    	 <div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north',iconCls:'icon-tip',title:'登录信息'" style="height:300px">
            	<div style="height: 7%;"></div>
    			 
	            	 <ul>
					    <li style="height: 30px;color: #d39c1f;">登录账号：${loginUser.s_user_name}</li>
					    <li style="height: 30px;color: #d39c1f;">登录次数：${loginUser.loginCount}</li>
					    <li style="height: 30px;color: #d39c1f;">登录时间：${loginUser.loginTime}</li>
	    			</ul> 
    			
    			    
            </div>   
            <div data-options="region:'center',iconCls:'icon-tip',title:'通知公告'">
            	<div style="height: 7%;"></div>
            	<privilege:show powerName="menu_teacher">
            	 <ul>
				   <li style="height: 30px;color: #d39c1f;">学生名额总数：${loginUser.t_number}</li>
				   <li style="height: 30px;color: #d39c1f;">已经录取名额：${loginUser.selected_number}</li>
				    
    			</ul> 
    			</privilege:show>
            	<privilege:show powerName="menu_admin">
            		<span style="padding-left: 30px; color: red;">暂无相关信息</span>
    			</privilege:show>
            	<privilege:show powerName="menu_student">
            	 <ul>
				   <span style="padding-left: 30px; color: red;">暂无相关信息</span>
				    
    			</ul> 
    			</privilege:show>
            </div>   
        </div> 
    	
    </div>   
    <div data-options="region:'center',iconCls:'icon-tip',title:'站内信息'">
    	<table style="padding-left: 100px;">
		
		<tr style="height: 300px;">
			<td></td>
			
			<td>
				<table height="100px;" cellSpacing=0 cellPadding=0 width="100%" border=0>
				
					<tr height="40px;"><img alt="" src="${ctx}/images/loginlogo.png"></tr>

					<tr>
						<td><span style="font-size: 20px; color: #d39c1f;">温馨提示:现在系统时间是:</span></td>
					</tr>
					
					<tr>
						<td><span style="font-size: 20px; color: #d39c1f;" id="sys_date"></span></td>
					</tr>
				</table>
			</td>
			
	
		</tr>
		<tr>
			<td colSpan=3 height=10></td>
		</tr>
	</table>
    </div>   
</body> 
</html>
