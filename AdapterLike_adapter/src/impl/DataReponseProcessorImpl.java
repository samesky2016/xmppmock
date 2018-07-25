package impl;

//import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.alibaba.fastjson.JSON;
import com.jht.jsif.comm.ServiceResponseData;
import com.jht.jsif.comm.ServiceResponseProcessor;

public class DataReponseProcessorImpl implements ServiceResponseProcessor{

	protected static Logger logger  = LoggerFactory.getLogger(DataReponseProcessorImpl.class);
	public static int respCounter = 0;
	private static ServiceResponseData responseData;
	
	public DataReponseProcessorImpl(){
		
	}
	
	@Override
	public void execute(String fromUser, ServiceResponseData response) {
		// TODO Auto-generated method stub
		responseData = response;
		logger.info("接收异步响应：{},发送者：{}", JSON.toJSONString(response),fromUser);
		++respCounter;
	}

	public static ServiceResponseData takeResult(){
		ServiceResponseData result = responseData;
		responseData = null;
		return result;
	}
}
