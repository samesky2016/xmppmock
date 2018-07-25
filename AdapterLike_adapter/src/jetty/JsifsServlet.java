package jetty;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import test.ClientMain;
import test.DispatcherHandler;
import test.RandomCarNo;

public class JsifsServlet extends HttpServlet {
	protected static Logger logger  = Logger.getLogger(JsifsServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setStatus(200);
		resp.setContentType("text/plain;charset=utf-8");
		resp.setDateHeader("Expires", 0);
		
		try{
		PrintWriter out =resp.getWriter();
		// 生成订单协议动态处理
		String protocol=DispatcherHandler.selectProtocol(ClientMain.props.getProperty("serviceId"));
		if ("NISSP_JSPAY_ORDER".equals(ClientMain.props.getProperty("serviceId"))) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			protocol = protocol.replace("BK_orderNo", ClientMain.getUUID())
					.replace("carNO", RandomCarNo.getStringRandom(6))
					.replace("serviceStime", sdf.format(new Date()));
		}
		out.print(protocol);
		out.flush();
		out.close();
		long end=System.currentTimeMillis();
		
		logger.info("-------------->>处理耗时："+(end-start)+"ms");
		
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
			doGet(req, resp);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1609796961366172848L;
	
	
	
	
}
