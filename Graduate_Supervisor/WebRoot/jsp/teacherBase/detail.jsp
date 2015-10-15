<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息列表</title>
<%@ include file="/jsp/common/meta.jsp"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/easyui_1.4.3/themes/metro-blue/easyui.css">
<%@ include file="/jsp/common/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/ShowPDF/Scripts/pdfobject.js"></script>
<script type="text/javascript">
  var filename = "";   
		   /**
 * 页面加载初始化
 */
jQuery(function() {
	var filename = '${t_file_path}';
	if(filename !=  undefined && filename != ''){
		document.getElementById( "pdf").style.display= "block" 
		 var url= "${ctx}/word/" + filename;
		var success = new PDFObject({
            url: "${ctx}/word/" + filename,
            pdfOpenParams: {
               scrollbars: 0,
               toolbar: 0,
               statusbar: 0
               }
        }).embed("pdf");
	}else {
		document.getElementById( "fff").style.display= "block"
	}
	
	
}); 
 </script>  
</head>
<body>

	<div id="pdf" style="display: none;" > <a href=""></a></div>
	
	<div id= "fff" style="display: none; text-align: center;">
	
		<div style="height: 10%;"></div>
		
		<img alt="" src="${ctx}/images/no_pdf.png">
		
	</div>
	
</body>
</html>
