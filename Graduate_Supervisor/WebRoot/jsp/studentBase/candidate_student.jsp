<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		striped:true,
		url : '${pageContext.request.contextPath}/studentBase/getStudentBaseList?Date='
				+ new Date() + '',

		 columns : [ [
		 {field : 's_t_id',checkbox:true},
		 {field : 'count',title:'人数',hidden:true},
	 	 {field : 's_id',title : '学号',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_sex',title : '性别',width : getWidth(0.2),align : 'center'},
	 	  {field:  'detail',title:'详细信息',width:getWidth(0.15),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' style='color:blue' onclick='detail("+index+")'>智育成绩</a>";  
				 return detail; 
       		 } 
		 },
		 
	 	 {field:  's_t_volunteer',title:'第几志愿',width:getWidth(0.15),align:'center',
	 	 	formatter: function(value,row,index){
				return '第 '+ value + ' 志愿';
       		 } 
       	}
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

function select(){
	var rows = jQuery("#basicGrid_div").datagrid("getSelections");
	
	if (rows == null || rows == ""){
		window.alert('还未选择数据');
		return;
	}
	
	
	teacher_select(rows);
		
}

function teacher_select(rows){

	var idArray = new Array();
	var total_id = "";
	
	for(var i = 0;i<rows.length;i++){
	
		if(rows.length > rows[i].count ){
			alert("名额已超过");
			return;
		}
		
		idArray.push(rows[i].s_id);
		total_id = idArray.join(";");
	}
	
	alert(total_id);
	
	var URL = "${pageContext.request.contextPath}/studentVolunteer/teacherSelect?date="+new Date()+"";
	var paramObj = {
			"total_id":			total_id
	};
	
	jQuery.get(URL,paramObj,function(jsonData){
		
		var flag = jsonData.flag;
	
		if (flag == true){
			
			window.alert("操作成功");
			jQuery("#basicGrid_div").datagrid("reload");
				
		} else {
			window.alert("操作失败");
		}
	}, "json");

			
			
		
}


</script>
</head>
<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
	
		<c:if test="${loginUser.p_id == 2}">
	
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="select();">选择学生</a>
		
		</c:if>
		
	</div>
	
	<c:if test="${loginUser.p_id == 2}">

	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
		
	</div>
	
	</c:if>
	
	<c:if test="${loginUser.p_id == 3}">
	
	<div id="basic_div" data-options="region:'center'">
		
		<div id="basicGrid_div"></div>
		
	</div>
	
	</c:if>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
