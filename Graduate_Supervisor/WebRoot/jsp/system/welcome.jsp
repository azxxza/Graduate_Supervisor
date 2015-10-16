<%@page import="com.model.SysUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理系统</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>

</head>

<body>
	
	<table  cellSpacing=0 cellPadding=0 width="90%" align=center border=0>
		<tr height="40px;"></tr>
		<tr height=100>
			<td style="width: 7%;"></td>
			
			
			<td>
				<table height="100px;" cellSpacing=0 cellPadding=0 width="100%" border=0>

					
					<tr>
					
						<%
												String username = "";
																SysUser user = (SysUser)session.getAttribute("loginUser");
																if(user != null){
																	username = user.get("u_username");
																}
											%>
					
						<td>你好,<%=username%></td>
					
						
					</tr>
					
					<tr>
						<td><span style="font-size: 20px;">欢迎使用本科生导师双向选择系统！</span></td>
					</tr>
					
					<tr height="50%;"></tr>
					
					<tr>
						<td><span style="font-size: 20px; color: red;">温馨提示:本轮选导师学生互选时间为:</span></td>
					</tr>
					
					<tr>
						<td><span style="font-size: 20px; color: red;">2015-9-12至 2015-10-1</span></td>
					</tr>
				</table>
			</td>
			
	
		</tr>
		<tr>
			<td colSpan=3 height=10></td>
		</tr>
	</table>
	
	
	
	
</body>
</html>
