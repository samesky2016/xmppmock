package test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

import com.jht.jsif.comm.JSConnection;
import com.jht.jsif.comm.impl.JSEndPoint;

import impl.DataRecieveProcessorImpl;
import impl.DataReponseProcessorImpl;
import impl.FileReceiveProcessorImpl;
import impl.UserStateMonitorImpl;

public class LoginMain {
	private static List<JSConnection> jsList = new ArrayList<JSConnection>();
	private static BlockingQueue<JSConnection> queue = new LinkedBlockingDeque<JSConnection>(10485760);
	public static Logger logger = Logger.getLogger(LoginMain.class);
	public long loginCount = 50L;
	public long startAccount = 10009L;
	public long threadCount = 20L;
	public InputStream is = null;
	public Properties properties = new Properties();

	private void process() {
		try {
			is = ClientMain.class.getResourceAsStream("/app.properties");

			properties.load(is);

			loginCount = Integer.valueOf(properties.getProperty("login.count", "50")).intValue();
			startAccount = Integer.valueOf(properties.getProperty("start.account", "10009")).intValue();
			threadCount = Integer.valueOf(properties.getProperty("thread.count", "5")).intValue();
			for (int i = 0; i < loginCount; i++) {
				Properties props = new Properties();
				props.setProperty("server.domain", properties.getProperty("server.domain"));
				props.setProperty("server.host", properties.getProperty("server.host"));
				props.setProperty("server.port", properties.getProperty("server.port"));

				props.setProperty("login.resource", properties.getProperty("login.resource"));
				//props.setProperty("login.password", properties.getProperty("login.password"));
				props.setProperty("login.password", String.valueOf(startAccount));
				props.setProperty("login.username", String.valueOf(startAccount));

				JSConnection jsConnection = new JSEndPoint(props);
				jsConnection.setDataCallBack(new DataRecieveProcessorImpl(), new DataReponseProcessorImpl());
				jsConnection.setFileCallBack(new FileReceiveProcessorImpl());
				jsConnection.setUserStateMonitor(new UserStateMonitorImpl());
				jsList.add(jsConnection);
				queue.offer(jsConnection);
				startAccount ++;
			}
			logger.info("-------------->>全部登陆账号创建成功：" + jsList.size());
			for (int k = 0; k < threadCount; k++) {
				LongThread lt = new LongThread();
				Thread t = new Thread(lt);
				t.start();
			}
			logger.info("-------------->>全部线程登陆启动成功！threadCount:" + threadCount);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-------------->>登陆失败：", e);
			try {
				if (is == null) {
					return;
				}
				is.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private class LongThread implements Runnable {
		private LongThread() {
		}

		public void run() {
			JSConnection jsConnection = null;
			try {
				while(true){
		          jsConnection = (JSConnection)LoginMain.queue.take();
		          jsConnection.connect();
		          LoginMain.logger.info("----[" + jsConnection.getUser() + "] 登陆成功！queue:" + LoginMain.queue.size());
		          Thread.sleep(100L);
				}
			} catch (Exception e) {
				LoginMain.logger.error("-------------->>登陆失败<<--------------", e);
			}
		}
	}

	public class CheckONLineThread extends Thread {
		public CheckONLineThread() {
		}

		public void run() {
			try {
				while (true) {
					LoginMain.logger.info("-------------->>检测线程启动：" + Thread.currentThread().getName());
					sleep(90000);
					
					for (JSConnection jsConnection : jsList) {
						
						// LoginMain.logger.info("当前账号连接信息为jsConnection.isConnected():"+jsConnection.isConnected()+",jsConnection.isAuthenticated():"+jsConnection.isAuthenticated());
						if (!(jsConnection.isConnected()) || !(jsConnection.isAuthenticated())) {
							jsConnection.disconnect();
							jsConnection.connect();
							LoginMain.logger.info(jsConnection.getUser() + "-------------->>重新登陆成功！");

						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		LoginMain m = new LoginMain();
		m.process();
		CheckONLineThread checkOnline = m.new CheckONLineThread();

		checkOnline.start();
	}
}
