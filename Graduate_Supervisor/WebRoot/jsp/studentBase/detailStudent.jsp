<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/gray/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<style type="text/css">
	.datagrid-cell-rownumber{
		height: 26px;
	}
</style>
<script language="javascript">

/*
 * 表格初始化
 */
function initBasicGrid() {
	var t_work_id = '${t_work_id}';
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
		url : '${pageContext.request.contextPath}/admin/getStudentBaseListByTeacher?t_work_id='+t_work_id+'&Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 'id',hidden:true},
		 {field : 't_work_id',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.3),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.32),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.3),align : 'center'}
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

	<div id="basic_div" data-options="region:'center',title:'学生信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
