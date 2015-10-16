<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<script language="javascript">
	
/*
 * 表格初始化
 */
function initBasicGrid() {
	jQuery('#basicGrid_div').datagrid({
		view : myview,
		fit : true,
		fitColumns : false,
		singleSelect : true,
		loadMsg : '正在加载数据',
		toolbar:'#toolbar',
		pagination : true,
		emptyMsg : '没有相关记录',
		striped:true,
		sortName: 's_t_volunteer',
		remoteSort:true,
		url : '${pageContext.request.contextPath}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.1),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.09),align : 'center'},
		 {field : 't_occupation',title : '职称/职务',width : getWidth(0.1),align : 'center'},
		 {field : 't_hightest_background',title : '最高学历',width : getWidth(0.1),align : 'center'},
		 {field : 't_gradute_school',title : '毕业院校',width : getWidth(0.1),align : 'center'},
		 {field : 't_tel',title : '联系电话',width : getWidth(0.1),align : 'center'},
		 {field : 't_email',title : 'Email',width : getWidth(0.1),align : 'center'},
		 {field : 't_number',title : '名额总数(个)',width : getWidth(0.1),align : 'center',
		 	styler: function(value,row,index){
				
					return 'color:red;';
				
			}
		 	
		 },
		 {field : 'rest_number',title : '名额剩余(个)',width : getWidth(0.1),align : 'center',
		 	styler: function(value,row,index){
				
				return 'color:red;';
				
			}
		 },
		 {field:  'detail',title:'详细信息',width:getWidth(0.1),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' style='color:blue' onclick='detail("+index+")'>学生列表</a>";  
				 return detail; 
       		 } 
		 }

		] ],

		onLoadSuccess : function(data) {
			$(this).datagrid('doCellTip',{});

		},

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		}
		
	});
	

}

function initWin(){
	$('#win').window({
		width : getWidth(0.6),
		height : getHeight(0.8),
		modal : true,
		title : "用户操作",
		loadMsg : '正在加载数据',
		closed : true,
		inline:false,
		onBeforeClose : function() {
			jQuery('#basicGrid_div').datagrid("reload");
		}

	});
}

/**
 * 页面加载初始化
 */
jQuery(function() {

	initBasicGrid();
	
	initWin();
	
});


/**
 * 详细信息
 */
function detail(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;
	$('#win').window({
	
		content : "<iframe frameborder='0' scrolling='no' style='width:100%;height:100%;' src='${pageContext.request.contextPath}/studentBase/selected_student?t_work_id="
		+ t_work_id+"'></iframe>",
	});
	$('#win').window('open');
	
}

function exportAll(){

	var saveURL = "${pageContext.request.contextPath}/admin/exportAll";
				
	window.location.href =saveURL;		
	
	
}

function exportRest(){
	alert("导出剩余");
}


</script>
</head>
<body class="easyui-layout">

	<div id="toolbar"  style="padding-right: 5%;">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportAll();">导出所有教师</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportRest();">导出有剩余名额教师</a>
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>
	
	<div id="win">
		
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
