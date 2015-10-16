<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		url : '${pageContext.request.contextPath}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.1),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.05),align : 'center'},
		 {field : 't_occupation',title : '职称/职务',width : getWidth(0.1),align : 'center'},
		 {field : 't_hightest_background',title : '最高学历',width : getWidth(0.1),align : 'center'},
		 {field : 't_gradute_school',title : '毕业院校',width : getWidth(0.12),align : 'center'},
		 {field : 't_tel',title : '联系电话',width : getWidth(0.1),align : 'center'},
		 {field : 't_email',title : 'Email',width : getWidth(0.1),align : 'center'},
		 {field:  'upload',title:'文档上传',width:getWidth(0.1),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='uploadcls' style='color:blue;text-decoration:none' onclick='upload("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		 
		 {field:  'detail',title:'查看文档',width:getWidth(0.1),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='searchcls' style='color:blue;text-decoration:none' onclick='search("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		 
		 
		 {field:  'option',title:'操作',width:getWidth(0.1),align:'center',
		 	formatter : function(value, row, index) {
	 			var del = "<a href='#' class='delcls' style='color:blue;text-decoration:none' onclick='comfirmDel("
				+ index + ")'></a>";
		 		return del; 
 				
			 }
		 }

		] ],

		onLoadSuccess : function(data) {
			$('.uploadcls').linkbutton({text : '上传pdf文档',plain :true,iconCls : 'icon-upload'});
			$('.searchcls').linkbutton({text : '查看pdf文档',plain :true,iconCls : 'icon-search'});
			$('.delcls').linkbutton({text : '删除教师',plain :true,iconCls : 'icon-trash'});

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

function comfirmDel(index){
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;

	$.messager.confirm('确认对话框', '您想要删除吗？', function(yes) {
		if (yes) {
			deleteTeacher(t_work_id);
		}
	});
}

function deleteTeacher(t_work_id){
	var delURL = "${ctx}/teacherBase/deleteTeacher?t_work_id="
			+ t_work_id + "&date=" + new Date() + "";
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

function undo(){
	jQuery("#basicGrid_div").datagrid('rejectChanges');
	para = undefined;
	array = new Array();
}

/**
 * 详细信息
 */
function upload(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;
	$('#win').window('open');
	document.getElementById("f_t_work_id").value = t_work_id;
}

function check(){
	
	//获取欲上传的文件路径
	var filepath = $('#pdf').filebox('getValue');
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
	var tp = "pdf";
	//返回符合条件的后缀名在字符串中的位置
	var rs = tp.indexOf(last);
	
	//如果返回的结果大于或等于0，说明包含允许上传的文件类型
	if (filepath == "" || filepath.length < 3) {
		$.messager.alert('提示信息', '请选择有效文件！', 'warning');
		return false;
	}

	if (rs < 0) {
		$.messager.alert('提示信息', '您选择的上传文件不是有效的pdf文件！', 'warning');
		return false;
	}

	
	return true;
}

function uploadPDF(){

	var flag = check();
	
	if(flag){
		document.getElementById("uploadForm").submit();
	}
}


function cancelUpload(){
	$('#win').window('close');
}


</script>
</head>
<body class="easyui-layout">

	<div id="toobar"  style="padding-right: 5%;">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="save();">导入教师信息</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="undo();">添加教师信息</a>
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>

	<div id="win" class="easyui-dialog" title="文档上传" style="width:400px;height:300px; text-align: center;"   
        data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true">
        
        <div style="height: 30%;"></div>
        
       <form id="uploadForm"
			action="${ctx}/teacherBase/uploadPDF"
			name="uploadForm" method="post" enctype="multipart/form-data">
			 <input type="hidden" name="t_work_id" id="f_t_work_id">
        	 <input  id="pdf" name="pdf" class="easyui-filebox" data-options="buttonText:'选择pdf文件'"><br><br>
        	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="false" onclick="uploadPDF();">确定</a>&nbsp;&nbsp;
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
					<td><input class="easyui-textbox" name="infoTeacherBasic.t_work_id" id="t_work_id"></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-textbox" name="infoTeacherBasic.t_name"></td>
				</tr>
				<tr>
					<td>姓别：</td>
					<td><input class="easyui-textbox" name="infoTeacherBasic.t_sex"></td>
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
