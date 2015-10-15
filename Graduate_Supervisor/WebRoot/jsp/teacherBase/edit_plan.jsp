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

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#basicGrid_div').datagrid('validateRow', editIndex)){
		$('#basicGrid_div').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCell(index, field){
	
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	
		if (endEditing()){
		$('#basicGrid_div').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	
	}
	
}

/*
 * 初始化窗体
 */
function initWin(){
	$('#win').window({
		width : getWidth(0.3),
		height : getHeight(0.7),
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
		onClickCell: onClickCell,
		url : '${pageContext.request.contextPath}/teacherBase/getPlanAspectListBySession?Date='
				+ new Date() + '',
		 columns : [ [
		  {field : 'p_a_id',hidden:true},
	 	 {field : 'p_a_name',title : '指导方向标题(点击单元格可修改)',width : getWidth(0.7),halign : 'center',
	 	 editor:{ type:'textbox'}
	 	 },
	 	 {field:  'option',title:'操作',width:getWidth(0.25),align:'center',
		 	formatter : function(value, row, index) {
	 		
	 			var del = "<a href='#' class='delcls' style='color:blue' onclick='comfirmDel("
				+ index + ")'></a>";
		 		return del; 
		 		
			 }
		 }
		] ],
		
		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '删除记录',plain : true,iconCls : 'icon-trash'});
			$(this).datagrid('doCellTip',{});

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
	
	initWin();
	
});


function addData(){
	$('#win').window('open');
}

function saveData(){
	submitData();
	
}

function undoData(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
}

function submitData(){
	jQuery("#basicGrid_div").datagrid('acceptChanges');
}

function refreshData(){
	jQuery("#basicGrid_div").datagrid('reload');
}


</script>
</head>
<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
		&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addData();">添加一条记录</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveData();">保存记录</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undoData();">撤销</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="submitData();">提交修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshData();">刷新</a>
		
	</div>

	<div id="basic_div" data-options="region:'center'">
		<div id="basicGrid_div"></div>
	</div>
	
	<div id="win">
		
	</div>

</body>
</html>
