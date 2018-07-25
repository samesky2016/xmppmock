package impl;

//import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.jht.jsif.comm.UserStateMonitor;

public class UserStateMonitorImpl implements UserStateMonitor {

	protected static Logger logger  = LoggerFactory.getLogger(UserStateMonitorImpl.class);
	@Override
	public void notify(String userJID, State state) {
		// TODO Auto-generated method stub
		logger.info("=======:{},is:{}" , userJID,state.name());
		//logger.info("=======:" + userJID + " is " + state.name());
	}

}
