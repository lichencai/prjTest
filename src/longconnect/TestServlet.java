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
		// ��ѭ�� ��ѯ�������ݱ仯
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // ����300���룬ģ�⴦��ҵ���
			int i = rand.nextInt(100); // ����һ��0-100֮��������
			if (i > 20 && i < 56) { // ����������20-56֮�����Ϊ��Ч���ݣ�ģ�����ݷ����仯
				long responseTime = System.currentTimeMillis();
				// ����������Ϣ������ʱ�䡢��������ʱ�䡢��ʱ
				writer.print("result: " + i + ", response time: " + responseTime + ", request time: " + timed
						+ ", use time: " + (responseTime - timed));
				
				System.out.println("result: " + i + ", response time: " + responseTime + ", request time: " + timed
						+ ", use time: " + (responseTime - timed));
				
				break; // ����ѭ������������
			} else { // ģ��û�����ݱ仯�������� holdס����
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
        out.println("    ����<TITLE>A Servlet</TITLE>");
        out.println("    ����<meta http-equiv=\"content-type\" " + "content=\"text/html; charset=utf-8\">");
        out.println("���� </HEAD>");
        out.println("       <BODY>");
        out.println("             Hello AnnotationServlet.");
        out.println("     </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
	}
	
}
