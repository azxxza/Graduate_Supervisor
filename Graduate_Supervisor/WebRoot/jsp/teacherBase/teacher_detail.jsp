<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/easyui_1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/ShowPDF/Scripts/pdfobject.js"></script>
<script type="text/javascript">
  	var filename = "";   
	/**
	 * 页面加载初始化
	 */
	window.onload = function (){
	var filename = '${t_file_path}';
	
	if(filename != undefined && filename != ''){
		var success = new PDFObject({
	        url: "${ctx}/pdf/" + filename,
	        
	        pdfOpenParams: {
	            scrollbars: '0',
	            toolbar: '0',
	            statusbar: '0'
	        }
	   	}).embed("pdf");
	}else {
		$("#fff").show();
	}
	
	 
	
	
}
 </script>  
</head>
<body>

	<div id="pdf"> <a href=""></a></div>
	
	<div id= "fff" style="display: none; text-align: center;">
	
		<div style="height: 10%;"></div>
		
		<img alt="" src="${ctx}/images/no_pdf.png">
		
	</div>
	
</body>
</html>
