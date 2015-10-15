<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'list.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/form.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui_1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui_1.4.3/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui_1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui_1.4.3/easyui_extension.js"></script>
</head>

<body style="width: 100%; overflow: hidden;">
	<div style="height: 2%;"></div>

	<div data-options="closable:false">
		<form id="basic">
			<input type="hidden" id="id" name="acceptance.id"
				value="${acceptance.id }" />
			<table class="formtable">
				<tr>
					<td class="tdLabel"><label>姓名:</label></td>
					<td class="tdInput"><input class="easyui-textbox"
						id="r_ac_title" name="acceptance.r_ac_title"
						value="${acceptance.r_ac_title }"
						data-options="required:true,missingMessage:'成果名称为空'" /></td>

					<td class="tdLabel"><label>性别:</label></td>
					<td class="tdInput"><input class="easyui-numberbox"
						id="r_ac_amount" name="acceptance.r_ac_amount"
						value="${acceptance.r_ac_amount }"
						data-options="prefix:'￥',precision:'2',required:true,missingMessage:'金额为空'" />
					</td>
				</tr>

				<tr>
					<td class="tdLabel"><label>职称/职务:</label></td>
					<td class="tdInput"><input id="r_ac_unit"
						class="easyui-textbox" name="acceptance.r_ac_unit"
						value="${acceptance.r_ac_unit }"
						data-options="required:true,missingMessage:'完成为空'" /></td>
					<td class="tdLabel"><label>最好学历:</label></td>
					<td class="tdInput"><input id="r_ac_completer"
						class="easyui-textbox" name="acceptance.r_ac_completer"
						value="${acceptance.r_ac_completer }"
						data-options="prompt:'主要完成人员已逗号隔开',required:true,missingMessage:'主要完成为空'" />
					</td>
				</tr>

				<tr>
					<td class="tdLabel"><label>最高学位:</label></td>
					<td class="tdInput"><input class="easyui-datebox"
						id="r_ac_start_date" name="acceptance.r_ac_start_date"
						value="${acceptance.r_ac_start_date }"
						data-options="required:true,missingMessage:'立项时间为空'" /></td>

					<td class="tdLabel"><label>毕业院校:</label></td>
					<td class="tdInput"><input class="easyui-datebox" type="text"
						id="r_ac_finish_date" value="${acceptance.r_ac_finish_date }"
						name="acceptance.r_ac_finish_date"
						data-options="required:true,missingMessage:'完成时间为空',editable:false" />
					</td>

				</tr>

				<tr>
					<td class="tdLabel"><label>email:</label></td>
					<td class="tdInput"><input class="easyui-combobox"
						id="r_ac_class" name="acceptance.r_ac_class"
						value="${acceptance.r_ac_class }"
						data-options="required:true,missingMessage:'类别为空'" /></td>

					<td class="tdLabel"><label>备注:</label></td>
					<td class="tdInput"><input class="easyui-textbox"
						id="r_ac_remark" name="acceptance.r_ac_remark"
						value="${acceptance.r_ac_remark}"
						data-options="multiline:true,required:true,missingMessage:'备注为空'"
						style="width: 190px; height: 35px;"></td>


				</tr>

			</table>

			<table class="submittable">
				<tr>
					<td style="width: 20%;text-align: right;"><a
						href="javascript:void(0)" onclick="submitData()"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a></td>

					<td style="width: 20%; text-align: center;"><a id="btn"
						href="javascript:void(0)" onclick="closeWin()"
						class="easyui-linkbutton" on data-options="iconCls:'icon-cancel'">关闭</a></td>

					<td style="width: 20%;text-align: left;"><a id="btn"
						href="javascript:void(0)" onclick="resetForm()"
						class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a></td>

				</tr>
			</table>
		</form>
	</div>



</body>
</html>
