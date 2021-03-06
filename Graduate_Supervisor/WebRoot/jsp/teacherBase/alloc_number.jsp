<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
var para = undefined;

var array = new Array();   

var editIndex = undefined;
function endEditing(){

	var rows = $('#basicGrid_div').datagrid('getRows');
	for(var i = 0; i < rows.length; i++){
		$('#basicGrid_div').datagrid('endEdit',i);
	}
	
	$('.detailcls').linkbutton({text : '更多',plain : true,iconCls : 'icon-search'});
	
	return true;
	
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
		pagination : true,
		emptyMsg : '没有相关记录',
		striped:true,
		onClickCell: onClickCell,
		rownumbers:true,
		url : '${ctx}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.15),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.15),align : 'center'},
		 {field : 't_birthday',title : '出生年月',width : getWidth(0.15),align : 'center'},
		 {field : 't_degree',title : '学历',width : getWidth(0.15),align : 'center'},
		 {field:  'detail',title:'详细信息',width:getWidth(0.15),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none' onclick='teacherDetail("+index+")'>更多</a>";  
				 return detail; 
       		 } 
		 },
		 
		 {field : 't_number',title : '名额(单击单元格可分配名额)',width : getWidth(0.2),align : 'center',editor:{type:'numberbox'}},
		 {field : 't_number_copy',hidden:true},

		] ],

		onLoadSuccess : function(data) {
			$('.detailcls').linkbutton({text : '更多',plain : true,iconCls : 'icon-search'});

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
function teacherDetail(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;
	var menu_href = "${ctx}/teacherBase/teacherDetail?t_work_id="
		+ t_work_id;
	parent.addTabs("详细信息",menu_href);
}

function submitData(){

	saveData();

	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = data.rows;
	
	for(var i = 0;i<rows.length;i++){
		
		var t_number = rows[i].t_number;
		var t_number_copy = rows[i].t_number_copy;
		if (t_number != t_number_copy){
			var par = rows[i].t_work_id + "," + rows[i].t_number;
			array.push(par);
		}
	
	}
	
	if(array.length == 0){
		$.messager.alert('提示信息','没有可以提交的数据','warning');
		return;
	}
	
	para = array.join(";");
	
	var jsonPara = {"para" : para};
	
	if(para != undefined){
		var  saveURL = "${ctx}/teacherBase/saveTeacherNumber?date="
				+ new Date() + "";
	
		jQuery.post(saveURL,jsonPara,function(jsonData) {
			var flag = jsonData.flag;
			var message = jsonData.message;
			if(flag == true){
				$.messager.alert('提示信息', message,'info',function(){
					
					jQuery("#basicGrid_div").datagrid("reload");
					
				});
				
			
			}else {
				
				$.messager.alert('提示信息', '提交失败','error',function(){
					jQuery("#basicGrid_div").datagrid("reload");
				});
				
			}
			
			para = undefined;
			array = new Array();
			
		
		}, "json");
	}else {
		$.messager.alert('提示信息', '没有可提交的志愿','warning');
		
	}
	
}



function undo(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
	
}

function saveData(){
	jQuery("#basicGrid_div").datagrid('acceptChanges');
}


</script>
</head>
<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveData();">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo();">撤销</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="submitData();">提交</a>
		
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
