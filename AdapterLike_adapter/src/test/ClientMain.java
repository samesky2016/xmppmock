package test;

import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jht.jsif.comm.JSConnection;
import com.jht.jsif.comm.impl.JSEndPoint;

import impl.DataRecieveProcessorImpl;
import impl.DataReponseProcessorImpl;
import impl.FileReceiveProcessorImpl;
import impl.UserStateMonitorImpl;
import jetty.HttpMock;

public class ClientMain {

	protected static Logger logger  = Logger.getLogger(ClientMain.class);
	public static Properties props = new Properties();
	
	public static void main(String[] args) {
			// TODO Auto-generated method stub
			//启动http服务器
			new Thread(new HttpMock()).start();
			
			//初始化缓存 
			new  DispatcherHandler();
			
			InputStream  is = null;
			
		    try{
		    	is = ClientMain.class.getResourceAsStream("/app.properties");
		    	props.load(is);
		    	
		    	JSConnection jsConnection = new JSEndPoint(props);
		    	jsConnection.setDataCallBack(new DataRecieveProcessorImpl(),new DataReponseProcessorImpl());
		    	jsConnection.setFileCallBack(new FileReceiveProcessorImpl());
		    	jsConnection.setUserStateMonitor(new UserStateMonitorImpl());
		    	
		    	
		    	jsConnection.connect();
		    	
		    	logger.info("-------------->>登陆成功:"+jsConnection.getUser());
		    	//System.out.println(jsConnection.getUser() + "登陆成功！");
		    }catch(Exception e){
		    	logger.error("-------------->>登录失败.");
		    	e.printStackTrace();
		    	
		    	try{
		    		if(is != null ){
		    			is.close();
		    		}
		    	}catch(Exception e1){
		    		e1.printStackTrace();
		    	}
		    }
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}


}
