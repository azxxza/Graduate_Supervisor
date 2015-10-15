<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'list.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui_1.4.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui_1.4.3/easyui_extension.js"></script>
	</head>

<body style="width: 100%; overflow: hidden;">

	<div style="height: 2%;"></div>

	<div  data-options="closable:false">
			<table class="formtable">
				<tr>
					<td class="tdLabel"><label>姓名:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_name}</label>
					</td>
						
					<td class="tdLabel"><label>性别:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_sex}</label>
					</td>
				</tr>

				<tr>
					<td class="tdLabel"><label>职称\职务:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_occupation}</label>
					</td>
					<td class="tdLabel"><label>最高学历:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_hightest_background}</label>
					</td>
				</tr>

				<tr>
					<td class="tdLabel"><label>最高学位:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_hightest_degree}</label>
					</td>

					<td class="tdLabel"><label>毕业院校:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_gradute_school}</label>
					</td>
					
				</tr>
				
				<tr>
					<td class="tdLabel"><label>联系电话:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_tel}</label>
					</td>
					
					<td class="tdLabel"><label>E-mail:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_email}</label>
					</td>
					
				
				</tr>
				
					<tr>
					<td class="tdLabel"><label>出生年月日:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_birthday}</label>
					</td>
					
					<td class="tdLabel"><label>本科毕业院校:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_bachelor_school}</label>
					</td>
					
				
				</tr>
				
					<tr>
					<td class="tdLabel"><label>本科所学专业:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_bachelor_major}</label>
					</td>
					
					<td class="tdLabel"><label>本科学位:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_bachelor_degree}</label>
					</td>
					
				
				</tr>
				
				<tr>
					<td class="tdLabel"><label>硕士毕业院校:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_master_school}</label>
					</td>
					
					<td class="tdLabel"><label>硕士所学专业:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_master_major}</label>
					</td>
					
				
				</tr>
				
				<tr>
					<td class="tdLabel"><label>硕士学位:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_master_degree}</label>
					</td>
					
					<td class="tdLabel"><label>博士毕业院校:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_doctor_school}</label>
					</td>
					
				
				</tr>
				
				<tr>
					<td class="tdLabel"><label>博士所学专业:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_doctor_major}</label>
					</td>
					
					<td class="tdLabel"><label>博士学位:</label></td>
					<td class="tdInput">
						<label>${tmsTeacher.t_doctor_degree}</label>
					</td>
					
				</tr>
				
			</table>
			
		
	</div>
	
</body>
</html>
