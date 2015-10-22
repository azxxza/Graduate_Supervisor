<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/privilege" prefix="privilege"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<style type="text/css">
	.datagrid-cell-rownumber{
		height: 26px;
	}
</style>
<script language="javascript">
function unitFormatter(value, rowData, rowIndex) { 
	 
    for (var i = 0; i < unitJSON.length; i++) {  
        if (unitJSON[i].value == value) {  
            return unitJSON[i].name;  
        }  
    }  
    return value;  
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
		singleSelect : false,
		loadMsg : '正在加载数据',
		pagination : true,
		emptyMsg : '没有相关记录',
		rownumbers:true,
		striped:true,
		url : '${ctx}/studentBase/getAllStudentBaseList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 'id',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.15),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.15),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.15),align : 'center'},
	 	 {field:  'detail',title:'详细信息',width:getWidth(0.15),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none' onclick='studentScore("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		 
	 	 {field:  's_t_volunteer',title:'第几志愿',width:getWidth(0.15),align:'center',
	 	 	formatter: function(value,row,index){
				return '第 '+ value + ' 志愿';
       		 } 
       	},
       	 {field : 's_t_remark',title : '指导方向',width : getWidth(0.22),align : 'center'},
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		},
		
		onLoadSuccess : function(data) {
			$('.detailcls').linkbutton({text : '智育成绩',plain : true,iconCls : 'icon-search'});
		},
		
	});
	

}


/**
 * 详细信息
 */
function studentScore(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var s_id = row.s_id;
	var s_name = row.s_name;
	var menu_href = "${pageContext.request.contextPath}/studentBase/studentScore?s_id="
		+ s_id;
	parent.addTabs(s_name + "详细信息",menu_href);
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

	
	<privilege:show powerName="menu_teacher">

	<div id="basic_div" data-options="region:'center',title:'所有学生信息列表'">
		
		<div id="basicGrid_div"></div>
		
	</div>
	
	</privilege:show>
	
	<privilege:show powerName="menu_admin">
	
	<div id="basic_div" data-options="region:'center'">
		
		<div id="basicGrid_div"></div>
		
	</div>
	
	</privilege:show>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
