<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'list.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/easyui_1.4.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/easyui_1.4.3/themes/icon.css">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/easyui_1.4.3/locale/easyui-lang-zh_CN.js"></script>
		
		
	</head>

<body>
	
	<div data-options="title:'基本信息'" class="easyui-panel"
		style="width:100%; overflow-x: hidden;overflow-y:hidden; ">
		<iframe style="width:100%;height:100%;" 
			src="${pageContext.request.contextPath}/teacherBase/base?t_work_id=${t_work_id}"></iframe>
	</div>
		<div data-options="title:'拟指导毕业生的方向'" class="easyui-panel"
			style="width:100%;overflow-x: hidden;overflow-y:hidden;">
			<iframe style="width:100%;height:60%;"
				src="${pageContext.request.contextPath}/teacherBase/planAspect?t_work_id=${t_work_id}"></iframe>
		</div>
	
		<div data-options="title:'近三年承担的主要课程'" class="easyui-panel"
			style="width:100%; overflow-x: hidden; overflow-y: scroll;">
			<iframe  style="width:100%; height: 70%;" 
				src="${pageContext.request.contextPath}/teacherBase/recentlyCourse?t_work_id=${t_work_id}"></iframe>
		</div>
		
		<div data-options="title:'近三年指导毕业论文\设计情况'" class="easyui-panel"
			style="width:100%; overflow-x: hidden; overflow-y: scroll;">
			<iframe  style="width:100%; height: 70%;" 
				src="${pageContext.request.contextPath}/teacherBase/recentlyAspect?t_work_id=${t_work_id}"></iframe>
		</div>
		
		<div data-options="title:'近三年指导大学生创新项目和各类专业竞赛情况'" class="easyui-panel"
			style="width:100%; overflow-x: hidden; overflow-y: scroll;">
			<iframe  style="width:100%; height: 70%;" 
				src="${pageContext.request.contextPath}/teacherBase/recentlyCompletition?t_work_id=${t_work_id}"></iframe>
		</div>
		
			<div data-options="title:'近三年教学、科研项目、成果及论著'" class="easyui-panel"
			style="width:100%; overflow-x: hidden; overflow-y: scroll;">
			<iframe  style="width:100%; height: 70%;" 
				src="${pageContext.request.contextPath}/teacherBase/recentlyResearch?t_work_id=${t_work_id}"></iframe>
		</div>
</body>
</html>
