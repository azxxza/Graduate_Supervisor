<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/privilege" prefix="privilege"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>教师出国审批系统前台首页</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<style type="text/css">
	.textbox {
		height: 20px;
		margin: 0;
		padding: 0 2px;
		box-sizing: content-box;
	}
</style>
<script type="text/javascript">
	$(function() {
		for (var i = 1; i <= 8; i++) {
			$('#ttt_' + i).tree({
				onClick : function(node) {
					var title = node.text;
					var url = node.url;
					if (title == '修改密码') {
						$('#loginDialog').dialog('open');
					} else {
						addTabs(title, url);
					}
				}
			});
		}
	});
	
	/*
	 * 添加选项卡
	 */
	function addTabs(info, url) {
	
		if ($('#main').tabs('exists', info)) {
			$('#main').tabs('select', info);
			
		} else {
			var content1 = "<iframe name='rightFrame' id='rightFrame' frameborder='0' style='width:100%;height:100%;'  src='"
					+ url + "'></iframe>";
			$('#main').tabs('add', {
				title : info,
				content : content1,
				closable : true
			});

			//获取最后一个tabs 在新加的选项卡后面添加"关闭全部"
			var li = $(".tabs-wrap ul li:last-child");
			$("#close").remove();
			li.after("<li id='close'><a class='tabs-inner' href='javascript:void()' onClick='javascript:closeAll()'>关闭全部</a></li>");
		}

	}
	
	/*
	 * 关闭所有选项卡
	 */
	function closeAll() {
		$.messager.confirm('确认对话框', '您确认要关闭所有选项卡？', function(yes) {
			if (yes) {

				$(".tabs li").each(function(index, obj) {
					//获取所有可关闭的选项卡
					var tab = $(".tabs-closable", this).text();
					$(".easyui-tabs").tabs('close', tab);
				});
				$("#close").remove();//同时把此按钮关闭
			}
		});

	}
	
  	function logout(){
  	
		window.location.href = "${ctx}/login/logout";
		
      }
        
     function closeTab(title){
		$('#main').tabs('close',title);
	}
	
	function goHome(){
		addTabs('后台首页', '');
	}
	
	function savePassword(){
	
		var oginal = $('#oginal').val();

		var password = $('#password').val();
		
		var com_password = $('#com_password').val();
		
		if(password != com_password){
			$.messager.alert('提示信息', '原始密码和确认密码不一致','warning');
			return;
		}
		
		if($("#passwordForm").form('validate')){
		
			var formData = jQuery("#passwordForm").serializeArray();
			
			var saveURL =  "${ctx}/user/updatePassword?date="
				+ new Date() + "";
		
			jQuery.post(saveURL, formData, function(jsonData) {
				var flag = jsonData.flag;
				
				if (flag == true) {
					$.messager.alert('提示信息', '密码修改成功','info');
					closeWin();
		
				} else {
					var message = jsonData.message;
					$.messager.alert('提示信息', '修改失败,原因：' + message,'error');
		
				}
			}, "json");
		
		}
		
		
	}
	
	function closeWin(){
		$('#loginDialog').dialog('close');
	}
        
</script>
<style type="text/css">
	.tree-node{
		padding:5px;
		height: 20px;
		
	}
	
	.panel-header{
		height: 15px;
		padding: 10px;
	}
	
	.panel-title{
		font-size: 15px;
	}

</style>
</head>

<body class="easyui-layout">

<div region="north" style="height:70px; background-image: url('${ctx}/images/light.png');" class="easyui-layout" >
    <div region="west" id="logo" style="width: 600px; padding-left: 30px;background-color: #6ac5f4" border="false"><img src="${ctx}/images/loginlogo.png" style="height: 60px;"></div>
    <div region="center" class="easyui-layout" border="false" style="background-color: #6ac5f4"></div>
    <div region="east" style="width: 180px;background-color: #6ac5f4" border="false">
    	<div style="height: 20px;"></div>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" icon="icon-home" onClick="goHome();" style="margin-right: 20px;color: white;">首页</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" icon="icon-user-go" onclick="logout()" style="margin-right: 20px; color: white;">退出登录</a>
    </div>
</div>

<div region="west" data-options="split:false" title="导师双向选择菜单" style="width:235px;" >
  <div class="easyui-accordion">
  
  <privilege:show powerName="menu_admin">
				<div  data-options="iconCls:'icon-edit'" title="系统参数设置">
					<ul id="ttt_1">
						<li data-options="iconCls:'icon-add',url:'${ctx}/admin/allocNumberList'" >
							<span>
								教师名额分配
							</span>
						</li>
						<li data-options="iconCls:'icon-time',url:'${ctx}/admin/openTimeList'"><span>开放时间</span></li>
					</ul>
				</div>
			
			
			<div title="用户管理" data-options="iconCls:'icon-user'">
				<ul id="ttt_2">
					
					<li data-options="iconCls:'icon-user',url:'${ctx}/admin/importStudent'"><span>学生信息管理</span></li>
					<li data-options="iconCls:'icon-user',url:'${ctx}/admin/importTeacher'"><span>教师信息管理</span></li>
				</ul>
			</div>
			
			<div title="双选结果" data-options="iconCls:'icon-search'">
				<ul id="ttt_3">
					<li data-options="iconCls:'icon-ok',url:'${ctx}/admin/teacherProcessList'">查看双选结果</span></li>
					<li data-options="iconCls:'icon-ok',url:'${ctx}/admin/studentProcessList'"><span>学生分配</span></li>
				</ul>
			</div>
			
			</privilege:show>
			
			
			<privilege:show powerName="menu_teacher">
				<div title="师生互选" data-options="iconCls:'icon-reload'">
				<ul id="ttt_4">
					<li data-options="iconCls:'icon-table-refresh',url:'${ctx}/studentBase/candidate_student'"><span>所有学生列表</span></li>
					<li data-options="iconCls:'icon-edit',url:'${ctx}/studentBase/volunteer_student'"><span>当前志愿可录取学生列表</span></li>
				</ul>
				
				</div>
				
				<div title="选择结果" data-options="iconCls:'icon-ok'">
				<ul id="ttt_5">
					<li data-options="iconCls:'icon-ok',url:'${ctx}/studentBase/selected_student'"><span>已完成选择学生列表</span></li>
				</ul>
				</div>
			</privilege:show>
			
			<privilege:show powerName="menu_student">
				
			<div title="师生互选" data-options="iconCls:'icon-reload'">
				<ul id="ttt_6">
					<li data-options="iconCls:'icon-ok',url:'${ctx}/teacherBase/candidateTeacherList'"><span>选择教师</span></li>
				</ul>
			</div>
				
			</privilege:show>
			
			<div title="个人系统设置" data-options="iconCls:'icon-setting'">
				<ul id="ttt_7">
					<li data-options="iconCls:'icon-key'"><span>修改密码</span></li>
				</ul>
			</div>
  </div>
</div>


<div id="opt_info" border="false" region="center" title="..." >

  <div id="main" class="easyui-tabs"  fit="true" border="false" plain="true">
    <div title="后台首页" style="padding:10px;">
      <iframe  frameborder="0" style="width:100%;height:100%;" src="${ctx}/welcome"></iframe>
    </div>
  </div>
</div>

<div  region="south" style="text-align:center; background-color: #6ac5f4; height: 25px; font-size: 13px;">
<div style="height: 4px;"></div>福建农林大学  计算机与信息学院 </div>
<div class="easyui-dialog" id="loginDialog" style="width:400px;height:300px;" data-options="title:'修改密码',modal:true,iconCls:'icon-key',closed:true">
	<form id="passwordForm">
	<table style="padding-left: 50px; padding-top: 50px; font-size: 13px;" cellpadding="5px;">
		<tr>
			<td>原始密码:</td>
			<td><input class="easyui-validatebox textbox" type="password" id="oginal" name="oginal" data-options=" required:true,validType:'length[3,20]',missingMessage:'原始密码不为空'"></td>
		</tr>
		
		<tr>
			<td>新密码:</td>
			<td><input class="easyui-validatebox textbox" type="password" id="password" name="password" data-options=" required:true,validType:'length[3,20]',missingMessage:'新密码不为空'"></td>
		</tr>
		
		<tr>
			<td>确认密码:</td>
			<td><input class="easyui-validatebox textbox" type="password" id="com_password" name="com_password" data-options=" required:true,validType:'length[3,20]',missingMessage:'确认密码不为空'"></td>
		</tr>
		
	</table>
	
	</form>
	
	<table style="text-align: center; padding-left: 80px;" cellpadding="15px;">
		<tr>
			<td><a href="javascript:void(0)" onclick="savePassword()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存修改</a></td>
			<td><a href="javascript:void(0)" onclick="closeWin()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭窗口</a></td>
		</tr>
	</table>
</div>
</body>
</html>
