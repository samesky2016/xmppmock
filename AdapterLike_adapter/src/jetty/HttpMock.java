package jetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import test.ClientMain;

public class HttpMock implements Runnable{
	protected static Logger logger  = Logger.getLogger(HttpMock.class);

	
	public static void main(String[] args) {
		try{
			Server server=new Server(10010);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);
			context.addServlet(new ServletHolder(new JsifsServlet()), "/jscsp/channel/service");
			logger.info("-------------->>httpsever启动成功！");
			server.start();
			server.join();
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void httpServerStart(){
		try{
			Server server=new Server(Integer.valueOf(ClientMain.props.getProperty("httpPort")));
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);
			context.addServlet(new ServletHolder(new JsifsServlet()), "/jscsp/channel/service");
			logger.info("-------------->>httpsever启动成功！");
			server.start();
			server.join();
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		httpServerStart();
	}
	
	
}
