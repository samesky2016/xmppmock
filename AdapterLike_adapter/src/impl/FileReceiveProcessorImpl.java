package impl;

import java.io.File;
import java.io.InputStream;

//import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.jht.jsif.comm.FileDescription;
import com.jht.jsif.comm.FileReceiveProcessor;

public class FileReceiveProcessorImpl implements FileReceiveProcessor {
	
	protected static Logger logger  = LoggerFactory.getLogger(FileReceiveProcessorImpl.class);
	
	public FileReceiveProcessorImpl(){
		
	}

	@Override
	public File createNewFile(String fromJID, String fileName, FileDescription fileDescription) {
		// TODO Auto-generated method stub
		logger.info("接收照片请求，发送者：{},文件名：{}",fromJID, fileName );
		StringBuilder filFullPath = new StringBuilder();
		filFullPath.append(System.getProperty("user.dir"));
		filFullPath.append(File.separator);
		filFullPath.append("image");
		filFullPath.append(File.separator);
		filFullPath.append(fileDescription.getAttributes().get("COMMAND_ID"));
		filFullPath.append(File.separator);
		filFullPath.append(fileDescription.getAttributes().get("NISSP_ID"));
		filFullPath.append(".jpg");
		
		File file = new File(filFullPath.toString());
		File dir = file.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		return file;
	}

	@Override
	public void fileReceived(String fromJID, FileDescription fileDescription,File recievedFile) {
		// TODO Auto-generated method stub
		logger.info("fromJID:{},;fileUid:{}, is Recieved!",fromJID, fileDescription.getUid());
		//logger.info("fromJID:" + fromJID + ";fileUid:"+ fileDescription.getUid() + "is Recieved!");
	}

	@Override
	public void fileReceived(String arg0, FileDescription arg1, InputStream arg2)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyProgress(int percent, String fileName, String fromJID) {
		
		// TODO Auto-generated method stub
		logger.info("fromJID:{},;fileName:{},;percent:{}",fromJID, fileName,percent);
		//logger.info("fromJID:" + fromJID + ";fileName:"+ fileName + ";percent:" + percent);
		
	}
}
