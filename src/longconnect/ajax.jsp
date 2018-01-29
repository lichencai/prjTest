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
            
                (function longPolling() {
                
                    $.ajax({
                        url: "http://192.168.75.149:8082/opensdk/connect/ajax",
                        data: {"timed": new Date().getTime()},
                        dataType: "text",
                        timeout: 5000,
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $("#state").append("[state: " + textStatus + ", error: " + errorThrown + " ]<br/>");
                            if (textStatus == "timeout") { // 请求超时
                              	longPolling(); // 递归调用
                           		 // 其他错误，如网络错误等
                            } else { 
                                longPolling();
                            }
                        },
                        success: function (data, textStatus) {
                            $("#state").append("[state: " + textStatus + ", data: { " + data + "} ]<br/>");
                            if (textStatus == "success") { // 请求成功
                                longPolling();
                            }
                        }
                    });
                })();
            });
        </script>
    </head>
    
	<body>
		<div id="state"></div>
	</body>
</html>