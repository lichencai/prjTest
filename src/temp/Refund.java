package temp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import httpConn.HttpConn;
import jdbc.mysql.MysqlUtils;
import net.sf.json.JSONObject;
import util.FileUtil;

public class Refund {
	public static void main(String[] args) throws Exception{
		try{
			List<String> tels = FileUtil.readFileLine(new File("E:\\temp\\temp.txt"));
			tels.forEach(tel -> {
				try {
					System.out.println("tel:" + tel);
					TreeMap<String, Object> sqlParams = new TreeMap<String, Object>();
					sqlParams.put("tel", tel);
					List<Map<String, Object>> users = MysqlUtils.queryTable("dd_users", sqlParams);
					if(users.isEmpty())
						throw new Exception(tel + " 用户不存在");
					if(users.size() != 1)
						throw new Exception(tel + " 用户存在多个");
					String uid = users.get(0).get("uid").toString();
					System.out.println("uid:" + uid);
					sqlParams.clear();
					sqlParams.put("uid", uid);
					sqlParams.put("type", 3);
					List<Map<String, Object>> orders = MysqlUtils.queryTable("dd_order", sqlParams);
					orders.forEach(e -> {
						try {
							String orderSn = e.get("order_sn").toString();
							System.out.println("order_sn:" + orderSn);
							String url = "https://api.mingbikes.com/api/third/queryAliOrder";	//  这里调用的是客服系统ming/xm-advert里面的接口
							url = url + "?orderSn=" + orderSn;
							String res = HttpConn.getReq(url);
							String tradeNo = getTradeNo(res);
							if(StringUtils.isNotEmpty(tradeNo)) {
								System.out.println("tradeNo:" + tradeNo);
								Map<String, String> params = new HashMap<String, String>();
								String changeUrl = "https://api.mingbikes.com/notify/thirdTakenAccounts";		//  这里调用的是客服系统mingserver/xming-notify里面的接口
								params.put("orderSn", orderSn);
								params.put("paidAmount", "199");
								params.put("tradeNo", tradeNo);
								String res1 = HttpConn.postReq(changeUrl, params);
								System.out.println(res1);
							}
						}catch(Exception e1) {
							e1.printStackTrace();
						}
					});
				}catch(Exception e2){
					e2.printStackTrace();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getTradeNo(String queryResult) {
		JSONObject jsonObj = JSONObject.fromObject(queryResult);
		String tradeStatus = jsonObj.getString("tradeStatus");
		System.out.println("alipay order : " + queryResult);
		if("TRADE_SUCCESS".equals(tradeStatus)) {
			return jsonObj.getString("tradeNo");
		}
		return null;
	}
}
