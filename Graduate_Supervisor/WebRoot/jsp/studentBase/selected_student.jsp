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
	 	 {field : 's_id',title : '学号',width : getWidth(0.25),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.25),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.2),align : 'center'},
	 	 {field:  'detail',title:'详细信息',width:getWidth(0.25),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none' onclick='studentScore("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		] ],
		
		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '删除学生',plain : true,iconCls : 'icon-trash'});
			$('.detailcls').linkbutton({text : '智育成绩',plain : true,iconCls : 'icon-search'});
			

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

/**
 * 详细信息
 */
function studentScore(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var s_id = row.s_id;
	var menu_href = "${pageContext.request.contextPath}/studentBase/studentScore?s_id="
		+ s_id;
	parent.addTabs("详细信息",menu_href);
}

</script>
</head>
<body class="easyui-layout">

	<div id="basic_div" data-options="region:'center',title:'学生信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
