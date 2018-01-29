<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script> 
        <script type="text/javascript">
            $(function () {
            
                window.setInterval(function () {
                    $("#logs").append("[data: " + $($("#frame").get(0).contentDocument).find("body").text() + " ]<br/>");
                    $("#frame").attr("src", "http://localhost:8080/web/testServlet?timed=" + new Date().getTime());
                    // 延迟1秒再重新请求
                    window.setTimeout(function () {
                    	$('#frame').attr('src', $('#frame').attr('src'));
                    }, 1000);
                }, 5000);
                
            });
        </script>
    </head>
    
    <body>
        <iframe id="frame" name="polling" style="display: none;"></iframe>
        <div id="logs"></div>
    </body>
</html>