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

var para = undefined;

var array = new Array();

var before = undefined;
 
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
	var rest_number = row.rest_number;
	var s_t_volunteer = row.s_t_volunteer;
	if(rest_number == 0 && (s_t_volunteer == undefined || s_t_volunteer == '')){
		return;
	}else {
		if(s_t_volunteer == undefined || s_t_volunteer == ''){
			if (endEditing()){
				$('#basicGrid_div').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
				return;
			}
		}
	
	
	}
	
	if(field == 's_t_volunteer'){
		if (endEditing()){
			$('#basicGrid_div').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			return;
		}
	}
	
}
	
/*
 * 表格初始化
 */
function initBasicGrid(isOpen) {
	
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
		url : '${ctx}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.05),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.05),align : 'center'},
		 {field : 't_occupation',title : '职称/职务',width : getWidth(0.08),align : 'center'},
		 {field : 't_hightest_background',title : '最高学历',width : getWidth(0.08),align : 'center'},
		 {field : 't_gradute_school',title : '毕业院校',width : getWidth(0.08),align : 'center'},
		 {field : 't_tel',title : '联系电话',width : getWidth(0.08),align : 'center'},
		 {field : 't_email',title : 'Email',width : getWidth(0.08),align : 'center'},
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
				 var detail = "<a href='#' class='detailcls' style='color:blue;text-decoration:none'  onclick='detail("+index+")'></a>";  
				 return detail; 
       		 } 
		 },
		 
		
		 {field:  's_t_volunteer',title:'第几志愿',width:getWidth(0.09),align:'center',
			  editor : {  type : 'combobox',  options : { 
			  	 id : 'combo', 
			  	 editable : false,
			  	 url : '${ctx}/studentVolunteer/getVolunteerJson',
          		 valueField : "value",/* value是unitJSON对象的属性名 */  
           		 textField : "text",/* name是unitJSON对象的属性名 */  
           		 }
         	  },
         	  
         	   formatter: function(value,row,index){
         	   		var del = "<a href='#' class='addcls' style='color:blue'></a>";	
         	   		
         	   		if(isOpen == false){
         	   			return '';
         	   		}
         	   		
					if(value != undefined && value != '' && row.rest_number > 0){
			 			return '第 ' + value +  ' 志愿';
			 		}else if(row.rest_number <= 0){
			 			return ''; 
			 		}else {
			 			return del;
			 		}
			 		
       		 } ,
       		 
       		 styler: function(value,row,index){
		 		
		 			return 'color:red;';
		 		
					
			},
          },
          
          
		 
		 {field:  'option',title:'操作',width:getWidth(0.08),align:'center',
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
		
		onBeforeEdit:function(index, row){
		
			before = row.s_t_volunteer;
			
		},
		
		 onAfterEdit:function(index, row, changes){
		 
		 	var s_t_volunteer = row.s_t_volunteer;
		 	
		 	if(s_t_volunteer != before){
		 		var t_work_id = row.t_work_id;
		 	
		 	if(s_t_volunteer != undefined && s_t_volunteer != ""){
		 	
		 		var par = row.t_work_id + "," + row.s_t_volunteer;
		 		for(var i = 0; i < array.length; i++){
		 		
		 			if(array[i].charAt(0) == t_work_id){
		 				break;
		 			}
		 			
		 		}
		 		
		 		array.push(par);
		 
		 		para = array.join(";");
		 	}
		 	}
		 	
		 	
	    }
		
	});
	

}


/*
 * 模态对话框初始化
 */
function initWin(){
	
	$('#win').window({
		width : 670,
		height : 450,
		modal : true,
		title : "用户操作",
		loadMsg : '正在加载数据',
		closed : true,

		onBeforeClose : function() {
			jQuery('#basicGrid_div').datagrid("reload");
		}

	});

}



/**
 * 页面加载初始化
 */
jQuery(function() {

	var isOpen = ${isOpen};

	initBasicGrid(isOpen);
	
	initWin();
	
});


/**
 * 详细信息
 */
function detail(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;
	var menu_href = "${ctx}/teacherBase/detail?t_work_id="
		+ t_work_id;
	parent.addTabs("详细信息",menu_href);
}

/**
 * 提交志愿
 */
function save(){

	var count = 0;
	
	jQuery("#basicGrid_div").datagrid('acceptChanges');
	
	var data = jQuery("#basicGrid_div").datagrid("getData");
	
	var rows = data.rows;
	
	for(var i = 0;i<rows.length;i++){
		
		var s_t_volunteer = rows[i].s_t_volunteer;
		if (s_t_volunteer != undefined && s_t_volunteer != ""){
			count = count + 1;
		}
	
	}
	
	if(count < 1){
		$.messager.alert('提示信息','没有选择志愿','warning');
		jQuery("#basicGrid_div").datagrid("reload");
		return;
	}
	
	if(count > 5){
		$.messager.alert('提示信息','一人只能选择5个志愿','warning');
		jQuery("#basicGrid_div").datagrid("reload");
		return;
	}
	
	
	var jsonPara = {"para" : para};
	
	if(para != undefined){
		var  saveURL = "${pageContext.request.contextPath}/studentVolunteer/saveStudentVolunteer?date="
				+ new Date() + "";
	
		jQuery.post(saveURL,jsonPara,function(jsonData) {
			var flag = jsonData.flag;
			
			if(flag == true){
				$.messager.alert('提示信息','恭喜您，提交成功','info',function(){
					jQuery("#basicGrid_div").datagrid("reload");
				});
				
			
			}else {
				var message = jsonData.message;
				$.messager.alert('提示信息','提交失败,原因为：'+message,'error',function(){
					jQuery("#basicGrid_div").datagrid("reload");
				});
				
			}
			
			para = undefined;
			array = new Array();
			
		
		}, "json");
	}else {
		alert("没有可提交的志愿");
	}
	
	
	
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
	para = undefined;
	array = new Array();
}


</script>
</head>
<body class="easyui-layout">
<c:if test="${isOpen == true}">
	<div id="toobar"  style="padding-right: 5%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="save();">提交志愿</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo();">撤销</a>
	</div>
</c:if>	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
