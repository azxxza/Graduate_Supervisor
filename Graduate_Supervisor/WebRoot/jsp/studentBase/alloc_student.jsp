<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/easyui.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<style type="text/css">
	.datagrid-cell-rownumber{
		height: 26px;
	}
</style>
<script language="javascript">
var para = undefined;
var array = new Array();
var unitJSON;
function unitFormatter(value, rowData, rowIndex) {  
	
	if(unitJSON == undefined){
		return "";
	}else {
		for (var i = 0; i < unitJSON.length; i++) {
		
	        if (unitJSON[i].value == value) {  
	        	
	            return unitJSON[i].text;  
	        }  
	    }  
    	return value;  
	}
     
    
} 
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#basicGrid_div').datagrid('validateRow', editIndex)){
		$('#basicGrid_div').datagrid('endEdit', editIndex);
		editIndex = undefined;
		$('.detailcls').linkbutton({text : '智育成绩',plain : true,iconCls : 'icon-search'});
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
		pagination : true,
		emptyMsg : '没有相关记录',
		striped:true,
		rownumbers:true,
		onClickCell: onClickCell,
		url : '${ctx}/studentBase/getUnselectStudentList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 's_t_id',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.17),align : 'center'},
	 	 {field : 'detail',title : '详细信息',width : getWidth(0.2),align : 'center',
	 	  	 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration: none;' onclick='studentScore("+index+")'></a>";  
				 return detail; 
       		 } 
	 	  },
	 	 {field : 'can_select_teacher',title : '可供选择的老师',width : getWidth(0.2),align : 'center',
	 	  
	 	  	formatter : unitFormatter,
	 	    
	 	    editor : {  type : 'combobox',  options : { 
			  	 id : 'combo', 
			  	 editable : false,
			  	 url : '${ctx}/teacherBase/getTeacherJson',
          		 valueField : "value",/* value是unitJSON对象的属性名 */  
           		 textField : "text",/* name是unitJSON对象的属性名 */ 
          		icons: [{
					iconCls:'icon-clear',
						handler: function(e){
							$(e.data.target).textbox('clear');
						}
					}],
 				onLoadSuccess : function(data) {
 					
 					unitJSON = data;
      					
				}
           	}
         	},
	 	  }
		] ],
		
		onLoadSuccess : function(data) {
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


function saveData(){
	jQuery("#basicGrid_div").datagrid('acceptChanges');
	
}

function undoData(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
	para = undefined;
			array = new Array();
}

function sumbitData(){
	
	saveData();
	
	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = jQuery("#basicGrid_div").datagrid("getRows");
	
	for(var i =0 ; i < rows.length; i++){
		var can_select_teacher = rows[i].can_select_teacher;
		if(can_select_teacher != undefined && can_select_teacher != ''){
			var s_id  = rows[i].s_id;
			var par = s_id + "," + can_select_teacher;
		 	array.push(par);
		}
	}
	
	para = array.join(";");
	
	if(array.length == 0){
		
		$.messager.alert('提示信息', '没有可提交的志愿','warning');
		return;
	}
	
	
	var jsonPara = {"para" : para};
	
	if(para != undefined){
		var  saveURL = "${ctx}/admin/doAdminVolunteer?date="
				+ new Date() + "";
	
		jQuery.post(saveURL,jsonPara,function(jsonData) {
			var flag = jsonData.flag;
			
			if(flag == true){
				$.messager.alert('提示信息', '提交成功','info');
				jQuery("#basicGrid_div").datagrid("reload");
			
			}else {
				var message = jsonData.message;
				jQuery("#basicGrid_div").datagrid("reload");
				
			}
			
			para = undefined;
			array = new Array();
			
		
		}, "json");
	}else {
		$.messager.alert('提示信息','没有可提交的志愿','info');
	}
	
	
	
	
}

function refreshData(){
	jQuery("#basicGrid_div").datagrid('reload');
	para = undefined;
			array = new Array();
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

</script>
</head>
<body class="easyui-layout">

	

	<div id="toobar"  style="padding-right: 5%;">
		&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveData();">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undoData();">撤销</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshData();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="sumbitData();">提交</a>
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'学生列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
