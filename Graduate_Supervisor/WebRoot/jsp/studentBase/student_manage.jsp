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
	
	.textbox {
		height: 20px;
		margin: 0;
		padding: 0 2px;
		box-sizing: content-box;
	}
</style>
<script language="javascript">

$.extend($.fn.validatebox.defaults.rules, {
           
      number: {// 验证货币
          validator: function (value) {
               return /^[0-9]*$/i.test(value);
          },
          message: '请输入正确的数字'
      },
    
     
 });
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
		rownumbers:true,
		striped:true,
		url : '${pageContext.request.contextPath}/studentBase/getAllStudentBaseList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 's_id',title : '学号',width : getWidth(0.2),align : 'center'},
	 	 {field : 's_name',title : '姓名',width : getWidth(0.2),align : 'center'},
		 {field : 's_sex',title : '性别',width : getWidth(0.15),align : 'center'},
	
		 {field : 'detail',title : '详细信息',width : getWidth(0.2),align : 'center',
	 	  	 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration: none;' onclick='studentScore("+index+")'>更多</a>";  
				 return detail; 
       		 } 
	 	  },
		 
		 
		 {field:  'option',title:'操作',width:getWidth(0.2),align:'center',
		 	formatter : function(value, row, index) {
	 			var del = "<a href='#' class='delcls' style='color:blue;text-decoration:none' onclick='comfirmDel("
				+ index + ")'></a>";
		 		return del; 
 				
			 }
		 }

		] ],

		onLoadSuccess : function(data) {
			$('.detailcls').linkbutton({text : '智育成绩',plain : true,iconCls : 'icon-search'});
			$('.delcls').linkbutton({text : '删除学生',plain :true,iconCls : 'icon-trash'});

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
	
	parent.$("#main").tabs("loaded");
	
});

function comfirmDel(index){
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var s_id = row.s_id;
	
	$.messager.confirm('确认对话框', '您想要删除吗？', function(yes) {
		if (yes) {
			deleteStudent(s_id);
		}
	});
}

function deleteStudent(s_id){
	var delURL = "${ctx}/studentBase/deleteStudent?s_id="
			+ s_id + "&date=" + new Date() + "";
	jQuery.get(delURL, function(jsonData) {

		var flag = jsonData.flag;
		var message = jsonData.message;
		if (flag == true) {
			jQuery('#basicGrid_div').datagrid("reload");

		} else {
			$.messager.alert('我的消息','操作失败,'+message,'error');

		}
	}, "json");
}


/**
 * 详细信息
 */
function studentScore(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var s_id = row.s_id;
	var s_name = row.s_name;
	var menu_href = "${ctx}/studentBase/studentScore?s_id="
		+ s_id;
	parent.addTabs(s_name + "详细信息",menu_href);
	
}

function check(filepath){

	//为了避免转义反斜杠出问题，这里将对其进行转换
	var re = /(\\+)/g;
	var filename = filepath.replace(re, "#");
	//对路径字符串进行剪切截取
	var one = filename.split("#");
	//获取数组中最后一个，即文件名
	var two = one[one.length - 1];
	//再对文件名进行截取，以取得后缀名
	var three = two.split(".");
	//获取截取的最后一个字符串，即为后缀名
	var last = three[three.length - 1];
	//添加需要判断的后缀名类型
	var tp = "xls,xlsx";
	//返回符合条件的后缀名在字符串中的位置
	var rs = tp.indexOf(last);
	
	//如果返回的结果大于或等于0，说明包含允许上传的文件类型
	if (filepath == "" || filepath.length < 3) {
		$.messager.alert('提示信息', '请选择有效文件！', 'warning');
		return false;
	}

	if (rs < 0) {
		$.messager.alert('提示信息', '您选择的上传文件不是有效的excel文件！', 'warning');
		return false;
	}

	
	return true;
}


function openBasicWin(){
	$('#basicWin').dialog('open');
}

function openScoreWin(){
	$('#scoreWin').dialog('open');
}

function importBasic(){
	//获取欲上传的文件路径
	var filepath = $('#excelBasic').filebox('getValue');
	var flag = check(filepath);
	if(flag){
	
		document.getElementById("uploadBasicForm").submit();
		
		parent.$("#main").tabs("loading","正在导入中");
		
	}
}

function importScore(){
	//获取欲上传的文件路径
	var filepath = $('#excelScore').filebox('getValue');
	var flag = check(filepath);
	if(flag){
	
		document.getElementById("uploadScoreForm").submit();
		$("#scoreWin").dialog('close');
		parent.$("#main").tabs("loading","正在导入中");
		
	}
}





</script>
</head>
<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="openBasicWin();">导入学生基本信息</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="openScoreWin();">导入学生成绩信息</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="saveStudent();">添加学生基本信息</a>
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>

	<div id="basicWin" class="easyui-dialog" title="导入基本信息" style="width:400px;height:300px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true">
        
        <div style="height: 30%;"></div>
        
       <form id="uploadBasicForm"
			action="${ctx}/studentBase/uploadBasicExcel"
			name="uploadBasicForm" method="post" enctype="multipart/form-data">
        	 <input  id="excelBasic" name="excelBasic" class="easyui-filebox" data-options="buttonText:'选择excel文件'"><br><br>
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="false" onclick="importBasic();">确定</a>&nbsp;&nbsp;
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancelUpload();">取消</a>
       		
     	</form>
     	
	</div>  
	
	<div id="scoreWin" class="easyui-dialog" title="导入成绩信息" style="width:400px;height:300px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true">
        
        <div style="height: 30%;"></div>
        
       <form id="uploadScoreForm"
			action="${ctx}/studentBase/uploadScoreExcel"
			name="uploadScoreForm" method="post" enctype="multipart/form-data">
        	 <input id="excelScore" name="excelScore" class="easyui-filebox" data-options="buttonText:'选择excel文件'"><br><br>
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="false" onclick="importScore();">确定</a>&nbsp;&nbsp;
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancelUpload();">取消</a>
       		
     	</form>
     	
	</div>  
	
	<div id="addTeacher" class="easyui-dialog" title="添加教师" style="width:400px;height:250px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true">
        
        <div style="height: 10%;"></div>
        
         <form id="addTeacherForm">
			<table style="padding-left: 50px;" cellpadding="5px;">
				<tr>
					<td>教职工号：</td>
					<td><input class="easyui-validatebox textbox" name="infoTeacherBasic.t_work_id" id="t_work_id" data-options=" required:true,validType:'number',missingMessage:'教职工号不为空'"></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-validatebox textbox" name="infoTeacherBasic.t_name" data-options=" required:true,missingMessage:'姓名不为空'"></td>
				</tr>
				<tr>
					<td>姓别：</td>
					<td><input class="easyui-validatebox textbox" name="infoTeacherBasic.t_sex" data-options=""></td>
				</tr>
			</table>
			
			<div style="padding-top: 10px;text-align: center;">
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="false" onclick="submitTeacherData();">确定</a>&nbsp;&nbsp;
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancelAddTeacher();">取消</a>
			</div>
       		
     	</form>
        
      
     	
	</div>  


	<%@include file="../common/shade.jsp" %>
	
	
	
</body>
</html>
