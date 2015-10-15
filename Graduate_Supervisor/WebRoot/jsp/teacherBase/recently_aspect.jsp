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
		fit : true,
		fitColumns : false,
		toolbar : '#toobar',
		singleSelect : true,
		loadMsg : '正在加载数据',
		emptyMsg : '没有相关记录',
		striped:true,
		rownumbers:true,
		url : '${pageContext.request.contextPath}/teacherBase/getRecentlyAspectListBySession?Date='
				+ new Date() + '',
		 columns : [ [
	 	 {field : 'r_a_grade',title : '年级',width : getWidth(0.2),halign : 'center'},
	 	 {field : 'r_a_project_name',title : '论文(设计)题目',width : getWidth(0.55),halign : 'center'},
	 	 {field : 'r_a_project_type',title : '论文(设计)类型',width : getWidth(0.2),halign : 'center'}
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		}
		
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
