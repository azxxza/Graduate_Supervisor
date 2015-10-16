<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'list.jsp' starting page</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/form.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<script type="text/javascript">
	/*
 * 提交
 */
function submitData() {
	var formData = jQuery("#basic").serializeArray();
	
	var saveURL = "${ctx}/admin/saveOpenTime?date="
				+ new Date() + "";
	
	jQuery.post(saveURL,formData,function(jsonData) {
		var flag = jsonData.flag;
		var message = jsonData.message;
		if (flag == true) {
			$.messager.alert('提示信息','操作成功','info',function() {
				
				
			});

		} else {
			$.messager.alert('提示信息', '操作失败,原因为：' + message,'error');

		}
	}, "json");
}
	
</script>
	</head>

<body style="width: 100%; overflow: hidden;">

	<div style="height: 2%;"></div>

	<div  data-options="closable:false">
	
		<form id="basic">
		
			<input type="hidden" name="sysTime.o_t_id" value="${sysTime.o_t_id}"> 
	
			<table class="formtable">
				<tr>
					<td class="tdLabel"><label>第一轮开放时间:</label></td>
					<td class="tdInput">
						
						<input class="easyui-datetimebox" id="o_t_start_time" name="sysTime.o_t_start_time" value="${sysTime.o_t_start_time }" data-options="required:true,missingMessage:'起始时间为空'"/>
						至
						<input class="easyui-datetimebox" id="o_t_end_time" name="sysTime.o_t_end_time" value="${sysTime.o_t_end_time }" data-options="required:true,missingMessage:'结束时间为空'"/>
					&nbsp;&nbsp;&nbsp;
						<a
						href="javascript:void(0)" onclick="submitData()"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
				
					</td>
						
					
				</tr>

			</table>
			
		</form>
	</div>
	
	<%@include file="../common/shade.jsp" %>
	
</body>
</html>
