<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/privilege" prefix="privilege"%>
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
		 {field : 'id',checkbox:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.18),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.15),align : 'center'},
	 	  {field:  'detail',title:'详细信息',width:getWidth(0.2),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none' onclick='detail("+index+")'>智育成绩</a>";  
				 return detail; 
       		 } 
		 },
		 
	 	 {field:  's_t_volunteer',title:'第几志愿',width:getWidth(0.2),align:'center',
	 	 	formatter: function(value,row,index){
				return '第 '+ value + ' 志愿';
       		 } 
       	}
		] ],

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		},
		
		onLoadSuccess : function(data) {
			$('.detailcls').linkbutton({text : '智育成绩',plain : true,iconCls : 'icon-search'});
			
			$(this).datagrid('doCellTip',{});

		},
		
		
	});
	

}


/**
 * 详细信息
 */
function detail(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var s_id = row.s_id;
	alert(s_id);
	var menu_href = "${pageContext.request.contextPath}/studentBase/detail?s_id="
		+ s_id;
	parent.addTabs("详细信息",menu_href);
}



/**
 * 页面加载初始化
 */
jQuery(function() {

	initBasicGrid();
	
});

function select(){
	var rows = jQuery("#basicGrid_div").datagrid("getSelections");
	
	if (rows == null || rows == ""){
		window.alert('还未选择数据');
		return;
	} else {
		var rest_count = document.getElementById("rest_count").value;
		
		var rest_count_int = parseInt(rest_count);
		
		var select_count = parseInt(rows.length);
		
		if (select_count > rest_count_int){
		
			$.messager.alert('提示信息','名额已超过','warning');
			
			return;
			
		}else {
		
			var idArray = new Array();
			
			var total_id = "";
			
			for(var i = 0;i < rows.length; i++){
			
				idArray.push(rows[i].s_id);
				
				total_id = idArray.join(";");
			}
			teacher_select(total_id);
		}
		
		
		
	}
	
	
	
	
	
		
}

function teacher_select(total_id){

	
	var URL = "${ctx}/studentVolunteer/teacherSelect?date="+new Date()+"";
	var paramObj = {
		"total_id":			total_id
	};
	
	jQuery.get(URL,paramObj,function(jsonData){
		
		var flag = jsonData.flag;
		var message = jsonData.message;
		if (flag == true){
			
			$.messager.alert('提示信息',message,'info',function(){
				jQuery("#basicGrid_div").datagrid("reload");
			});
			
				
		} else {
			$.messager.alert('提示信息',message,'error',function(){
				jQuery("#basicGrid_div").datagrid("reload");
			});
		}
	}, "json");
	
}




</script>
</head>
<body class="easyui-layout">

	

	<c:if test="${can_select == true}">

	<div id="toobar"  style="padding-right: 5%;">

		<privilege:show powerName="menu_teacher">
	
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="select();">选择学生</a>
		
		</privilege:show>
		
	</div>
	
	</c:if>
	
	<privilege:show powerName="menu_teacher">

	<div id="basic_div" data-options="region:'center',title:'学生信息列表'">
		<input type="hidden" id="rest_count" name="rest_count" value="${rest_count}">
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
