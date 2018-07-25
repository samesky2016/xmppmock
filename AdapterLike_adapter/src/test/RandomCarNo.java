package test;

import java.util.Random;

public class RandomCarNo {
	
	//随机生成车牌号码
	public static String getStringRandom(int length){
		
		String area[] = {"京","津","冀","晋","蒙","辽","吉","黑","沪","苏",
						"浙","皖","闽","赣","鲁","豫","鄂","湘","粤","桂",
						"琼","渝","川","贵","云","藏","陕","甘","青","宁","新"
						};
		StringBuffer sb = new StringBuffer("");
	    Random random = new Random();
	    //参数length表示随机生成字符串长度；
	    for(int i = 0;i < length;i++){
	    	String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
	    	if("char".equalsIgnoreCase(charOrNum)){
	    		sb.append((char) (random.nextInt(26) + 65));
	    	}else{
	    		sb.append(String.valueOf(random.nextInt(10)));
	    	}
	    }
		return area[random.nextInt(31)] + "-" +  sb.toString();
	}

}
