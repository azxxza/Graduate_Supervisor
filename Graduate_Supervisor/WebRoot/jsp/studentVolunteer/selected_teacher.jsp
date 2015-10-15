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
function unitFormatter(value, rowData, rowIndex) { 
	 if(value == "1"){
	 	return "第一志愿";
	 } else 
	 
	  if(value == "2"){
	 	return "第二志愿";
	 } else 
	 
	  if(value == "3"){
	 	return "第三志愿";
	 } else 
	 
	  if(value == "4"){
	 	return "第四志愿";
	 } else 
	 
	  if(value == "5"){
	 	return "第五志愿";
	 } else {
	 	return "";
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
		singleSelect : false,
		loadMsg : '正在加载数据',
		pagination : true,
		emptyMsg : '没有相关记录',
		striped:true,
		rownumbers:true,
		url : '${pageContext.request.contextPath}/volunteer/getStudentVolunteerList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 's_t_id',checkbox:true},
		 {field : 't_work_id',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.3),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.3),align : 'center'},
		 {field : 's_t_volunteer',title : '第几志愿',width : getWidth(0.35),align : 'center',formatter : unitFormatter}
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

function save(){
	var selectArray = jQuery("#basicGrid_div").datagrid("getSelections");
	
	if (selectArray == null || selectArray == ""){
		window.alert('还未选择数据');
		return;
	}
	
	var idArray = new Array();
	for (var i = 0; i < selectArray.length; i++){
		var  selectRow = selectArray[i];
		var s_id = selectRow.s_id;
		idArray.push(s_id);
	}

	var total_id = idArray.join(",");
	
	var t_number = document.getElementById("t_number").value;
	
	if(idArray.length > t_number){
		alert("超过名额限制");
		return;
	}
	
	var URL = "${pageContext.request.contextPath}/volunteer/doTeacherVolunteer?date="+new Date()+"";
	
	var paramObj = {
					"total_id":			total_id
			};
	jQuery.get(URL,paramObj,function(jsonData){
			
		var flag = jsonData.flag;
		alert(jsonData.flag);
		if (flag == true){
			jQuery("#basicGrid_div").datagrid("reload");
			window.alert("选择成功");
		}
	}, "json");
	

		
}

</script>
</head>
<body class="easyui-layout">

	

	<div id="toobar"  style="padding-right: 5%;">
	
		<input type="hidden" id="t_number" value="${t_number}"> 
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="save();">确认选择</a>
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
