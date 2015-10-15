<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'list.jsp' starting page</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/easyui_extension.js"></script>
<script type="text/javascript">
		
var para = undefined;

var array = new Array(); 

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
		url : '${pageContext.request.contextPath}/admin/getOpenTimeList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 'r_t_id',hidden:true},
	 	 {field : 'r_t_round',title : '第几轮',width : getWidth(0.25),align : 'center',
	 		 formatter : function(value, row, index) {
	 			
	 			return "第 "+value+" 轮";
		 		
			 }
	 	 },
	 	 {field : 'r_t_start_time',title : '开始时间(点击单元格修改时间)',width : getWidth(0.25),align : 'center',
	 	 	editor:{ type:'datetimebox',options:{editable:false}}
	 	 },
	 	 {field : 'r_t_end_time',title : '结束时间(点击单元格修改时间)',width : getWidth(0.25),align : 'center',
	 		 editor:{ type:'datetimebox',options:{editable:false}}
	 	 },
	 	 
	 	  {field:  'option',title:'操作',width:getWidth(0.13),align:'center',
		 	formatter : function(value, row, index) {
	 			var del = "<a href='#' class='delcls' style='color:blue' onclick='add("
				+ row.r_t_round + ")'></a>";
		 		return del; 
			 }
		 }
	 	
	 	 
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		},
		
		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '添加志愿时间',plain : true,iconCls : 'icon-add'});
			$(this).datagrid('doCellTip',{});

		},
		
	});
	

}



/**
 * 页面加载初始化
 */
jQuery(function() {

	initBasicGrid();
	
});

/*
 * 提交
 */
function submitData() {

	saveData();
	
	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = data.rows;
	
	for(var i = 0;i<rows.length;i++){
		var r_t_id =  rows[i].r_t_id;
		var r_t_start_time = rows[i].r_t_start_time;
		var r_t_end_time = rows[i].r_t_end_time;
		var par =  r_t_id + "," + r_t_start_time + "," + r_t_end_time;
		array.push(par);
		
	}
	
	para = array.join(";");
	
	var jsonPara = {"para" : para};
	
	var saveURL = "${pageContext.request.contextPath}/admin/updateOpenTime?date="
				+ new Date() + "";
	
	jQuery.post(saveURL,jsonPara,function(jsonData) {
		var flag = jsonData.flag;
		
		if (flag == true) {
			$.messager.alert('提示信息','操作成功','info');
			jQuery("#basicGrid_div").datagrid("reload");

		} else {
			var message = jsonData.message;
			$.messager.alert('提示信息', '操作失败,原因为：' + message,'error');

		}
	}, "json");
}

function saveData(){
	jQuery("#basicGrid_div").datagrid('acceptChanges');
}

function undo(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
}

function add(r_t_round){
	
	
	$('#win').window({
			content : "<iframe frameborder='0' scrolling='no' style='width:100%;height:100%;' src='${pageContext.request.contextPath}/admin/addVolunteerTimeList?r_t_round="+r_t_round+"'></iframe>",
		});
	$('#win').window('open');
	
}

function addData(){
	jQuery('#round_add_win').window("open");
	
}

function confirmAdd(){
	var formData = jQuery("#basic").serializeArray();
	
	var  saveURL = "${pageContext.request.contextPath}/admin/addOpenTime?date="
				+ new Date() + "";
	jQuery.post(saveURL,formData,function(jsonData) {
		var flag = jsonData.flag;
		
		if (flag == true) {
			$.messager.alert('提示信息','操作成功','info',function() {
				cancelAdd();
				jQuery("#basicGrid_div").datagrid("reload");
			});

		} else {
			var message = jsonData.message;
			$.messager.alert('提示信息', '操作失败,原因为：' + message,'error');

		}
	}, "json");
}

function cancelAdd(){
	document.getElementById("basic").reset();
	$('#round_add_win').window('close');
}

function reloadData(){
	jQuery("#basicGrid_div").datagrid("reload");
}

	
</script>
	</head>

<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
	
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addData();">添加一轮记录</a> 
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true"
				onclick="saveData();">保存编辑</a> 
				
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
				onclick="undo();">撤销编辑</a> 
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
				onclick="submitData();">提交编辑</a> 
				
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
		onclick="reloadData();">刷新表格</a> 
		
		
	</div>

	<div id="basic_div" data-options="region:'center'">
		<div id="basicGrid_div"></div>
	</div>
	
	<div id="win" class="easyui-dialog" title="编辑志愿时间" style="width:600px;height:400px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true,">

	</div>
	
	<div id="round_add_win" class="easyui-dialog" title="添加新一轮" style="width:400px;height:300px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true,onBeforeClose:function(){$('.delcls').linkbutton({text : '添加志愿时间',plain : true,iconCls : 'icon-add'});}">
        
        <div style="height: 30%;"></div>
        
        <form id="basic" name="basic">
        
       		 开始时间：<input class="easyui-datetimebox" id="r_t_start_time" name="s_round_open_time.r_t_start_time" data-options="editable:false"><br><br>
       		截止时间: <input class="easyui-datetimebox" id="r_t_end_time" name="s_round_open_time.r_t_end_time" data-options="editable:false"><br><br>
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="false" onclick="confirmAdd();">确定</a>&nbsp;&nbsp;
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancelAdd();">取消</a>
     	</form>
     	
	</div>  
	
	
</body>
</html>
