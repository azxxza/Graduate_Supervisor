<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/gray/easyui.css">
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
		toolbar : '#toobar',
		singleSelect : true,
		loadMsg : '正在加载数据',
		emptyMsg : '没有相关记录',
		url : '${pageContext.request.contextPath}/teacherStudent/getMyTeacher?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.11),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.11),align : 'center'},
		 {field : 't_occupation',title : '职称/职务',width : getWidth(0.11),align : 'center'},
		 {field : 't_hightest_background',title : '最高学历',width : getWidth(0.11),align : 'center'},
		 {field : 't_gradute_school',title : '毕业院校',width : getWidth(0.11),align : 'center'},
		 {field : 't_tel',title : '联系电话',width : getWidth(0.11),align : 'center'},
		 {field : 't_email',title : 'Email',width : getWidth(0.11),align : 'center'},
		 {field:  'detail',title:'详细信息',width:getWidth(0.11),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' style='color:blue' onclick='detail("+index+")'>更多</a>";  
				 return detail; 
       		 } 
		 },
		 
		 {field : 's_t_status',title : '状态',width : getWidth(0.11),align : 'center',
		 	 formatter: function(value,row,index){
				 var detail = "<a href='#' style='color:red' class='delcls'></a>";  
				 return detail;  
       		 } 
		 },
		
		] ],

		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '通过',plain : true,iconCls : 'icon-ok'});
			$(this).datagrid('doCellTip',{});

		},

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		}
		
		
		
		
	});
	

}


/*
 * 模态对话框初始化
 */
function initWin(){
	
	$('#win').window({
		width : 670,
		height : 450,
		modal : true,
		title : "用户操作",
		loadMsg : '正在加载数据',
		closed : true,

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
	var menu_href = "${pageContext.request.contextPath}/teacherBase/detail?t_work_id="
		+ t_work_id;
	parent.addTabs("详细信息",menu_href);
}




</script>
</head>
<body class="easyui-layout">

	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
