package httpConn;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpConn {
	
	
	public static String getReq(String address) throws Exception{
		URL url = new URL(address);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		int resCode = connection.getResponseCode();
		InputStream is = connection.getInputStream();
		byte[] response = new byte[is.available()];
		is.read(response);
		is.close();
		String content = null;
		if (response == null || response.length == 0) {   
			throw new Exception("没有数据返回");   
		}
		if(resCode == 200) {
			content = new String(response, "utf-8");
		}else {
			throw new Exception("请求错误：" + resCode);
		}
		connection.disconnect();
		return content;
	}
	
	public static String postReq(String address, Map<String, String> params) throws Exception{
		URL url = new URL(address);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//   设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
		connection.setDoOutput(true);
		//   Post 请求不能使用缓存
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.connect();
		if(params != null && !params.isEmpty()) {
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			StringBuilder sb = new StringBuilder();
			Set<String> keys = params.keySet();
			Iterator<String> iterator = keys.iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String value = params.get(key);
				
				if(sb.length() == 0)
					sb.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
				else
					sb.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
			}
			System.out.println("params:" + sb.toString());
			out.writeBytes(sb.toString());
	        out.flush();
	        out.close(); 
		}
		int resCode = connection.getResponseCode();
		InputStream is = connection.getInputStream();
		byte[] response = new byte[is.available()];
		is.read(response);
		is.close();
		if (response == null || response.length == 0) {   
			throw new Exception("没有数据返回");   
		}
		String content = null;
		if(resCode == 200) {
			content = new String(response, "utf-8");
		}else {
			throw new Exception("请求错误：" + resCode);
		}
		connection.disconnect();
		return content;
	}
}
