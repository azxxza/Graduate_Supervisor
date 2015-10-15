<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>基本信息列表</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/easyui_extension.js"></script>
		<script language="javascript">
	
/*
 * 表格初始化
 */
function initBasicGrid() {
	jQuery('#basicGrid_div').datagrid({
		view : myview,
		fit : false,
		fitColumns : false,
		toolbar : '#toobar',
		singleSelect : true,
		loadMsg : '正在加载数据',
		emptyMsg : '没有相关记录',
		striped:true,
		rownumbers:true,
		 columns : [ [
	 	 {field : 'r_c_year',title : '年份',width : getWidth(0.1),align : 'center'},
	 	 {field : 'r_c_project_name',title : '项目名称',width : getWidth(0.55),align : 'center'},
	 	 {field : 'r_c_student_header',title : '学生负责人',width : getWidth(0.1),align : 'center'},
	 	 {field : 'r_c_project_grade',title : '项目级别',width : getWidth(0.1),align : 'center'},
	 	 {field : 'r_c_project_state',title : '项目状态',width : getWidth(0.1),align : 'center'},
	 	  
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		}
		
	});
	
	var t_work_id = ${requestScope.t_work_id};
	
	$('#basicGrid_div')
		.datagrid(
				{
					url : "${pageContext.request.contextPath}/teacherBase/getRecentlyCompletitionListByWorkId?t_work_id="+t_work_id+"&Date="
							+ new Date() + ""
				});
	

}






/**
 * 页面加载初始化
 */
jQuery(function() {

	initBasicGrid();
});

</script>
</head>
<body class="easyui-layout">

	
	
	<div id="basic_div" data-options="region:'center'">
		<div id="basicGrid_div"></div>
	</div>

</body>
</html>
