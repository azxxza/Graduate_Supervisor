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
	
function unitFormatter(value, rowData, rowIndex) { 
	 if(value == "1"){
	 	return "第一志愿";
	 } else 
	 
	  if(value == "2"){
	 	return "第二志愿";
	 } else 
	 
	  if(value == "3"){
	 	return "第三志愿";
	 } else 
	 
	  if(value == "4"){
	 	return "第四志愿";
	 } else 
	 
	  if(value == "5"){
	 	return "第五志愿";
	 } else {
	 	return "";
	 }
	 
   
}  

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
		pagination : true,
		emptyMsg : '没有相关记录',
		striped:true,
		rownumbers:true,
		url : '${pageContext.request.contextPath}/studentBase/getStudentBaseListBySelected?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 's_t_id',hidden:true},
		 {field : 't_work_id',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.3),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.3),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.3),align : 'center'}
		] ],
		
		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '删除学生',plain : true,iconCls : 'icon-trash'});
			

		},

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

	

	<div id="toobar"  style="padding-right: 5%;">
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
