<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

function getJson(){
	var unitJSON = new Array();
	for(var i = 1; i <= 5; i++){
		var a = { "name" : "第一志愿",  
            "value" : i };
         unitJSON.push(a)
	}
	
	return unitJSON;
}

var unitJSON = [{  
            "name" : "第一志愿",  
            "value" : 1 
        }, {  
            "name" : "第二志愿",  
            "value" : 2 
        }, {  
            "name" : "第三志愿",  
            "value" : 3
        }, {  
            "name" : "第四志愿",  
            "value" : 4
        }, {  
            "name" : "第五志愿",  
            "value" : 5
        }
        
        ];

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
		url : '${pageContext.request.contextPath}/teacherBase/getTeacherBaseList?Date='
				+ new Date() + '',
		 columns : [ [
		 {field : 't_work_id',hidden:true},
	 	 {field : 't_name',title : '姓名',width : getWidth(0.045),align : 'center'},
		 {field : 't_sex',title : '性别',width : getWidth(0.045),align : 'center'},
		 {field : 't_occupation',title : '职称/职务',width : getWidth(0.075),align : 'center'},
		 {field : 't_hightest_background',title : '最高学历',width : getWidth(0.07),align : 'center'},
		 {field : 't_gradute_school',title : '毕业院校',width : getWidth(0.07),align : 'center'},
		 {field : 't_tel',title : '联系电话',width : getWidth(0.07),align : 'center'},
		 {field : 't_tel',title : '学生志愿个数',width : getWidth(0.07),align : 'center'},
		 {field : 't_email',title : 'Email',width : getWidth(0.07),align : 'center'},
		 {field : 't_number',title : '名额(个)',width : getWidth(0.05),align : 'center',
		 },
		 {field:  'detail',title:'详细信息',width:getWidth(0.05),align:'center',
       		 formatter: function(value,row,index){
				 var detail = "<a href='#' style='color:blue' onclick='detail("+index+")'>更多</a>";  
				 return detail; 
       		 } 
		 },
		 
		 {field : 's_t_status',title : '状态',width : getWidth(0.05),align : 'center',
		 	styler: function(value,row,index){
		 		if(value == "已通过"){
		 			return 'color:red;';
		 		}else {
		 			return 'color:green;';
		 		}
					
			},
			
			formatter : function(value, row, index) {
		 		if(row.s_t_volunteer != '' && row.s_t_volunteer != undefined && row.s_t_status != '已通过'){
		 			return '待定';
		 		}else if(row.s_t_status == '已通过'){
		 			return value;
		 		}else {
		 			return '';
		 		}
			 }
			
			
		 },
		 {field:  's_t_volunteer',title:'第几志愿(单元格可单击添加志愿)',width:getWidth(0.17),align:'center',
			  editor : {  type : 'combobox',  options : { 
			  	 id : 'combo', 
			  	 editable : false,
			  	 url : '${pageContext.request.contextPath}/teacherBase/getVolunteerJson',
          		 valueField : "value",/* value是unitJSON对象的属性名 */  
           		 textField : "text",/* name是unitJSON对象的属性名 */  
           		 }
         	  },
         	  
         	   formatter: function(value,row,index){
					if(value != undefined && value != ''){
						return '第 ' + value + ' 志愿';
					}
       		 } 
          },
		 
		 {field:  'option',title:'操作',width:getWidth(0.09),align:'center',
		 	formatter : function(value, row, index) {
		 		if(row.s_t_volunteer != '' && row.s_t_volunteer != undefined && row.s_t_status != '已通过'){
		 			var del = "<a href='#' class='delcls' style='color:blue' onclick='comfirmDel("
					+ index + ")'></a>";
			 		return del; 
		 		}else {
		 			return '';
		 		}
 				
			 }
		 }

		] ],

		onLoadSuccess : function(data) {
			$('.delcls').linkbutton({text : '删除志愿',plain : true,iconCls : 'icon-trash'});
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
		 	
// 		 	$('.delcls').linkbutton({text : '删除志愿',plain : true,iconCls : 'icon-trash'});
		 	
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

// alert(unitJSON);

// getJson();

// 	initBasicGrid();
	
// 	initWin();
	
});


/**
 * 详细信息
 */
function detail(index) {
	$('#basicGrid_div').datagrid('selectRow', index);// 关键在这里
	var row = $('#basicGrid_div').datagrid('getSelected');
	var t_work_id = row.t_work_id;
	var menu_href = "${pageContext.request.contextPath}/teacherBase/detail?t_work_id="
		+ t_work_id;
	parent.addTabs("详细信息",menu_href);
}

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
		alert("没有选择志愿");
		return;
	}
	
	if(count > 5){
		alert("一人只能选择5个志愿");
		jQuery("#basicGrid_div").datagrid("reload");
		return;
	}
	
		
	alert(para);
	
	var jsonPara = {"para" : para};
	
	if(para != undefined){
		var  saveURL = "${pageContext.request.contextPath}/studentVolunteer/saveStudentVolunteer?date="
				+ new Date() + "";
	
		jQuery.post(saveURL,jsonPara,function(jsonData) {
			var flag = jsonData.flag;
			var message = jsonData.message;
			if(flag == true){
				alert("恭喜您，提交成功");
				jQuery("#basicGrid_div").datagrid("reload");
			
			}else {
				jQuery("#basicGrid_div").datagrid("reload");
				alert(message);
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

	<div id="toobar"  style="padding-right: 5%;">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="save();">提交志愿</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="false" onclick="undo();">撤销</a>
		
	</div>
	
	<div id="basic_div" data-options="region:'center',title:'教师基本信息列表'">
		
		<div id="basicGrid_div"></div>
	</div>

	<%@include file="../common/shade.jsp" %>
</body>
</html>
