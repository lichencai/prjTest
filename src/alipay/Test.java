package alipay;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

public class Test {
	
	private static volatile Properties prop = null;
	
	public static Properties getPropInstance() throws Exception{
		if(prop == null) {
			synchronized (Test.class) {
				if(prop == null) {
					prop = new Properties();
					InputStream in = Test.class.getResourceAsStream("alipayInfo.properties");
					prop.load(in);     ///���������б�
				}
			}
		}
		return prop;
	}
	
	public static void main(String[] args) throws Exception{
		
		getPropInstance();
		
		AlipayClient alipayClient = new DefaultAlipayClient(prop.getProperty("alipay.gateway"), prop.getProperty("alipay.apply.id"), prop.getProperty("alipay.private.key"), "json",
                "GBK", prop.getProperty("alipay.public.key"), "RSA");
		
		//alipayTradeFastpayRefundQuery(alipayClient, null, null);
		alipayTradeQuery(alipayClient, "" , "CZ15100578476151159");
		//alipayTradeQuery(alipayClient, "2017110721001004880296859010");
	}
	
	/**
	 * ͳһ�յ������˿�ӿ�
	 * @param requestNo �˿����
	 */
	public static void alipayTradeRefund(AlipayClient alipayClient, String tradeNo, String orderSn, double refundAmount, String requestNo) throws Exception{
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" + "    \"out_trade_no\":\"" + orderSn + "\"," + "    \"trade_no\":\""
                + tradeNo + "\"," + "    \"refund_amount\":" + refundAmount + ","
                + "    \"refund_reason\":\"�����˿�\"," + "    \"out_request_no\":\""
                + requestNo + "\"" + "  }");
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		System.out.println(response.getBody());
	}
	/**
	 * ͳһ�յ����½��ײ�ѯ
	 */
	public static void alipayTradeQuery(AlipayClient alipayClient, String trade_no, String orderSn) throws Exception{
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{" +
				"    \"out_trade_no\":\"" + orderSn + "\"," +
				"    \"trade_no\":\"" + trade_no + "\"" +
				"  }");
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		System.out.println(response.getBody());
	}
	/**
	 *  ͳһ�յ������˿��ѯ
	 *  @param trade_no ���׶�����
	 *  @param out_request_no �˿����
	 */
	public static void alipayTradeFastpayRefundQuery(AlipayClient alipayClient, String trade_no, String out_request_no) throws Exception{
		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		request.setBizContent("{" +
				"\"trade_no\":\"" + trade_no + "\"," +
				"\"out_request_no\":\"" + out_request_no + "\"" +
				"}");
		AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
		System.out.println(response.getBody());
	}
	
}
