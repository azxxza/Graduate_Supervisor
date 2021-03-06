<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/open" prefix="open"%>
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

//渲染按钮
function renderButton(){
	
	$('.detailcls').linkbutton({text : '更多',plain : true,iconCls : 'icon-search'});
	$('.delcls').linkbutton({text : '删除志愿',plain : true,iconCls : 'icon-trash'});
	$('.addcls').linkbutton({text : '添加志愿',plain : true,iconCls : 'icon-add'});
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
		url : '${ctx}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.08),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.05),align : 'center'},
		 {field : 't_birthday',title : '出生年月',width : getWidth(0.08),align : 'center'},
		 {field : 't_degree',title : '学历',width : getWidth(0.08),align : 'center'},
		 {field : 'rest_number',title : '名额剩余(个)',width : getWidth(0.08),align : 'center',
		 	styler: function(value,row,index){
		 		if(value ==  undefined || value == ''){
		 			return 'color:red;';
		 		}else {
		 			return 'color:green;';
		 		}
					
			},
			formatter: function(value,row,index){
				if(value == 0){
					return '无';
				}else {
					return value;
				}
       		 } 
		 },
		 {field : 't_number',title : '名额(个)',width : getWidth(0.08),align : 'center'},
		 {field:  'detail',title:'详细信息',width:getWidth(0.08),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none'  onclick='teacherDetail("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		 
		 {field:  's_t_volunteer_copy',hidden:true},
		 {field:  's_t_volunteer',title:'第几志愿',width:getWidth(0.12),align:'center',
			  editor : {  type : 'combobox',  options : { 
			  	 id : 'combo', 
			  	 editable : false,
			  	 url : '${ctx}/studentVolunteer/getVolunteerJson',
          		 valueField : "value",/* value是unitJSON对象的属性名 */  
           		 textField : "text",/* name是unitJSON对象的属性名 */  
           		 icons: [{
						iconCls:'icon-clear',
						handler: function(e){
							$(e.data.target).textbox('setValue', '');
						}
					}]
           		 }
         	  },
         	  
         	   formatter: function(value,row,index){
         	   
         	   		var del = "<a href='#' class='addcls' style='color:blue' onclick='add("+index+")'></a>";	
					if(value != undefined && value != ''){
			 			return '第 ' + value +  ' 志愿';
			 		}else if(row.rest_number  <=  0){
			 			return ''; 
			 		}else {
			 			return del;
			 		}
			 		
       		 } ,
       		 
       		 styler: function(value,row,index){
		 		
		 			return 'color:red;';
		 		
					
			},
          },
          
          {field : 's_t_remark',title : '备注指导方向',width : getWidth(0.17),align : 'center',
			 
			  editor : {  type : 'textbox' },
			
		 },
		 
          
          
		 
		 {field:  'option',title:'操作',width:getWidth(0.1),align:'center',
		 	formatter : function(value, row, index) {
		 		if(row.s_t_volunteer != '' && row.s_t_volunteer != undefined){
		 			var add = "<a href='#' class='delcls'  onclick='comfirmDel("
					+ index + ")' style='color:blue'></a>";
			 		return add; 
		 		}else {
		 			return '';
		 		}
 				
			 }
		 },
		 
		 {field : 's_t_status',title : '状态',width : getWidth(0.08),align : 'center',
			 
			  styler: function(value,row,index){
		 		
		 		 return 'color:green;';
			  }
			
		 },
		 
		 
		] ],

		onLoadSuccess : function(data) {
			$('.detailcls').linkbutton({text : '更多',plain : true,iconCls : 'icon-search'});
			$('.delcls').linkbutton({text : '删除志愿',plain : true,iconCls : 'icon-trash'});
			$('.addcls').linkbutton({text : '添加志愿',plain : true,iconCls : 'icon-add'});
			$(this).datagrid('doCellTip',{});

		},

		onLoadError : function() {
			$.messager.alert('提示信息','数据加载失败','error');
		},
		
	});
	

}

function add(index){
	$('#basicGrid_div').datagrid('beginEdit', index);// 关键在这里
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

/**
 * 提交志愿
 */
 
 

function save(){

	var array = new Array();
	
	var para = undefined;
	
	jQuery("#basicGrid_div").datagrid('acceptChanges');
	
	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = data.rows;
	
	var count = 0;
	
	for(var i = 0; i < rows.length; i++){
		var s_t_volunteer = rows[i].s_t_volunteer;
		var s_t_volunteer_copy = rows[i].s_t_volunteer_copy;
		if (s_t_volunteer != undefined && s_t_volunteer != '' && s_t_volunteer != s_t_volunteer_copy){
			var t_work_id = rows[i].t_work_id;
			var s_t_remark = rows[i].s_t_remark;
			para = t_work_id + ',' + s_t_volunteer + ',' + s_t_remark;
			array.push(para);
			count = count + 1;
		}
	
	}
	
	if(count < 1){
		$.messager.alert('提示信息','没有选择志愿','warning');
		renderButton();
		return;
	}
	
	if(count > 5){
		$.messager.alert('提示信息','一人只能选择5个志愿','warning',function(){
			jQuery("#basicGrid_div").datagrid("reload");
		});
		
		return;
	}
	
	
	para = array.join(";");
	
	var jsonPara = {"para" : para};

	var  saveURL = "${ctx}/studentVolunteer/saveStudentVolunteer?date="
			+ new Date() + "";

	jQuery.post(saveURL,jsonPara,function(jsonData) {
		var flag = jsonData.flag;
		var message = jsonData.message;
		if(flag == true){
			$.messager.alert('提示信息',message,'info',function(){
				jQuery("#basicGrid_div").datagrid("reload");
			});
			
		
		}else {
			
			$.messager.alert('提示信息',message,'error',function(){
				jQuery("#basicGrid_div").datagrid("reload");
			});
			
		}
		
	
	}, "json");
	
	
	
	
}

function comfirmDel(index){
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;

	$.messager.confirm('确认对话框', '您想要删除吗？', function(yes) {
		if (yes) {

			delVolunteer(t_work_id);
		}
	});
}

function delVolunteer(t_work_id){
	var delURL = "${pageContext.request.contextPath}/studentVolunteer/deleteVolunteer?t_work_id="
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
}


</script>
</head>
<body class="easyui-layout">

	<open:show>
		<div id="toobar"  style="padding-right: 5%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="save();">提交志愿</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo();">撤销</a>
		</div>
	</open:show>

	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
