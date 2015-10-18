<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/easyui_1.4.3/datagrid-detailview.js" language="javascript"></script>
<style type="text/css">
	.datagrid-cell-rownumber{
		height: 26px;
	}
</style>
<script language="javascript">


/**
 * 页面加载初始化
 */
jQuery(function() {

	var s_id = document.getElementById("s_id").value;
	
	$('#dg').datagrid({
		view: detailview,
		detailFormatter:function(index,row){
			return '<div style="padding:2px"><table class="ddv"></table></div>';
		},
		onExpandRow: function(index,row){
			var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
			ddv.datagrid({
				url:'getScoreList?id='+row.id+'&s_id='+s_id,
				fitColumns:true,
				singleSelect:true,
				rownumbers:true,
				loadMsg:'',
				height:'auto',
				columns:[[
					{field:'s_c_name',title:'课程名称',width:200},
					{field:'s_c_score',title:'课程成绩',width:200}
				]],
				onResize:function(){
					$('#dg').datagrid('fixDetailRowHeight',index);
				},
				onLoadSuccess:function(){
					setTimeout(function(){
						$('#dg').datagrid('fixDetailRowHeight',index);
					},0);
				}
			});
			$('#dg').datagrid('fixDetailRowHeight',index);
		}
	});
		
	
});


</script>
</head>
<body class="easyui-layout">

	<table id="dg" 
		url="${ctx}/studentBase/getSysYearTermList" 
		title="学生成绩一览表"
		singleSelect="true" fitColumns="true" rownumbers="true" fit="true">
	<thead>
		<tr>
			<th field="id" data-options="hidden:true"></th>
			<th field="year" width="380" align="center">学年</th>
			<th field="term" width="380" align="center">学期</th>
			<th field="s_id" data-options="hidden:true"></th>
		</tr>
	</thead>
</table>
	<%@include file="../common/shade.jsp" %>
	<input type="hidden" name="s_id" value="${s_id}" id="s_id">
</body>
</html>
