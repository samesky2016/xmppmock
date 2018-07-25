package impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.jht.jsif.comm.ServiceRequestParam;
import com.jht.jsif.comm.ServiceRequestProcessor;
import com.jht.jsif.comm.ServiceResponseData;

import test.ClientMain;
import test.DispatcherHandler;
import test.RandomCarNo;

public class DataRecieveProcessorImpl implements ServiceRequestProcessor {
	protected static Logger logger = Logger.getLogger(DataRecieveProcessorImpl.class);

	public DataRecieveProcessorImpl() {

	}

	@Override
	public ServiceResponseData execute(String fromUser, ServiceRequestParam param) {
		// TODO Auto-generated method stub
		 logger.info("本次接收到的数据来自-------------->>："+fromUser+"请求内容为：-------------->>:"+JSON.toJSON(param));
//		if ("SY.equip.voicedisplay".equals(param.getServiceId())) {
//			//logger.info("本次接收到的数据来自-------------->>："+fromUser+"请求内容为：-------------->>:"+JSON.toJSON(param));
//			logger.info("请求内容为：-------------->>:" +System.currentTimeMillis()+','+ param.getDataItems().get(0).getAttribute("ledContent"));
//		}
		String protocol = DispatcherHandler.selectProtocol(param.getServiceId());
		ServiceResponseData data = param.createServiceResponse();
		data.setSeqId(param.getSeqId());

		if ("".equals(protocol)) {
			data.setMessage("未找到对应的协议，请增加相应的返回！");
			data.setResultCode(1);

		} else {
			// // 生成订单协议动态处理
			// if ("NISSP_JSPAY_ORDER".equals(param.getServiceId())) {
			// SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd
			// HH:mm:ss");
			// protocol = protocol.replace("BK_orderNo", ClientMain.getUUID())
			// .replace("carNO", RandomCarNo.getStringRandom(6))
			// .replace("serviceStime", sdf.format(new Date()));
			// }

			ServiceResponseData srp = JSON.parseObject(protocol, ServiceResponseData.class);
			srp.setSource(fromUser);

			data.addDataItem(srp.getDataItems());
			data.setResultCode(srp.getResultCode());
			data.setMessage(srp.getMessage());
			logger.debug("本次返回数据为-------------->>：" + JSON.toJSON(data));
		}
		return data;
	}

}
