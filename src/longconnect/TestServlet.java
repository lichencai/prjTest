package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="TestServlet",urlPatterns="/testServlet")
public class TestServlet extends HttpServlet{
	private static final long serialVersionUID = -5727276180170917125L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		long timed = Long.parseLong(req.getParameter("timed"));
		PrintWriter writer = response.getWriter();
		Random rand = new Random();
		// 死循环 查询有无数据变化
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // 休眠300毫秒，模拟处理业务等
			int i = rand.nextInt(100); // 产生一个0-100之间的随机数
			if (i > 20 && i < 56) { // 如果随机数在20-56之间就视为有效数据，模拟数据发生变化
				long responseTime = System.currentTimeMillis();
				// 返回数据信息，请求时间、返回数据时间、耗时
				writer.print("result: " + i + ", response time: " + responseTime + ", request time: " + timed
						+ ", use time: " + (responseTime - timed));
				
				System.out.println("result: " + i + ", response time: " + responseTime + ", request time: " + timed
						+ ", use time: " + (responseTime - timed));
				
				break; // 跳出循环，返回数据
			} else { // 模拟没有数据变化，将休眠 hold住连接
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		System.out.println("post...");
		
		String httpsafe_alarm = req.getParameter("httpsafe_alarm");
		System.out.println("httpsafe_alarm:" + httpsafe_alarm);
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE HTML>");
        out.println("<HTML>");
        out.println("      <HEAD>");
        out.println("    　　<TITLE>A Servlet</TITLE>");
        out.println("    　　<meta http-equiv=\"content-type\" " + "content=\"text/html; charset=utf-8\">");
        out.println("　　 </HEAD>");
        out.println("       <BODY>");
        out.println("             Hello AnnotationServlet.");
        out.println("     </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
	}
	
}
