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
			throw new Exception("û�����ݷ���");   
		}
		if(resCode == 200) {
			content = new String(response, "utf-8");
		}else {
			throw new Exception("�������" + resCode);
		}
		connection.disconnect();
		return content;
	}
	
	public static String postReq(String address, Map<String, String> params) throws Exception{
		URL url = new URL(address);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//   �����Ƿ���httpUrlConnection�������Ϊ�����post���󣬲���Ҫ����http�����ڣ������Ҫ��Ϊtrue, Ĭ���������false;
		connection.setDoOutput(true);
		//   Post ������ʹ�û���
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
        // ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode
        // ���б���
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
			throw new Exception("û�����ݷ���");   
		}
		String content = null;
		if(resCode == 200) {
			content = new String(response, "utf-8");
		}else {
			throw new Exception("�������" + resCode);
		}
		connection.disconnect();
		return content;
	}
}
