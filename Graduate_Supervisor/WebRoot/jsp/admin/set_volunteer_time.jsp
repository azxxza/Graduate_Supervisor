<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>志愿时间</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<script type="text/javascript">
		
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
		url : '${ctx}/admin/getVolunteerTimeList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 'id',hidden:true},
	 	 {field : 'v_volunteer',title : '第几志愿',width : getWidth(0.2),align : 'center',
	 		 formatter : function(value, row, index) {
	 			
	 			return "第 "+value+" 志愿";
		 		
			 }
	 	 },
	 	 {field : 'v_t_start_time',title : '开始时间(点击单元格修改时间)',width : getWidth(0.36),align : 'center',
	 	 	editor:{ type:'datetimebox',options:{editable:false}}
	 	 },
	 	 {field : 'v_t_end_time',title : '结束时间(点击单元格修改时间)',width : getWidth(0.36),align : 'center',
	 		 editor:{ type:'datetimebox',options:{editable:false}}
	 	 }
	 	 
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		},
		
		onLoadSuccess : function(data) {
			$(this).datagrid('doCellTip',{});

		}
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

	var para = undefined;

	var array = new Array(); 

	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = data.rows;
	
	for(var i = 0;i<rows.length;i++){
		var id =  rows[i].id;
		var v_t_start_time = rows[i].v_t_start_time;
		var v_t_end_time = rows[i].v_t_end_time;
		
		if (v_t_start_time != undefined && v_t_end_time != undefined){
			if(v_t_start_time >= v_t_end_time){
				var count = i + 1;
				$.messager.alert('提示信息','第'+count+'志愿开始时间不能大于结束时间','warning');
				return;
			}
			
			if(i != 0){
				var last_end_time = rows[i-1].v_t_end_time;
				if(last_end_time > v_t_start_time){
					var count = i+1;
					$.messager.alert('提示信息','第'+count+'志愿开始时间不能小于'+i+'志愿结束时间','warning');
					return;
				}
			}
			var par =  id + "," + v_t_start_time + "," + v_t_end_time;
			array.push(par);
		}
		
	}
	
	para = array.join(";");
	
	var jsonPara = {"para" : para};
	
	var saveURL = "${ctx}/admin/updateVolunteersTime?date="
				+ new Date() + "";
	
	jQuery.post(saveURL,jsonPara,function(jsonData) {
		var flag = jsonData.flag;
		var message = jsonData.message;
		if (flag == true) {
			$.messager.alert('提示信息',message,'info',function(){
				jQuery("#basicGrid_div").datagrid("reload");
			});
			

		} else {
			
			$.messager.alert('提示信息', message,'error');

		}
	}, "json");
}

function saveData(){
	jQuery("#basicGrid_div").datagrid('acceptChanges');
}

function undo(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
}



	
</script>
	</head>

<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
	
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true"
				onclick="saveData();">保存编辑</a> 
				
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
				onclick="undo();">撤销编辑</a> 
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
				onclick="submitData();">提交编辑</a> 
				
		
	</div>

	<div id="basic_div" data-options="region:'center'">
		<div id="basicGrid_div"></div>
	</div>
	
	
</body>
</html>
