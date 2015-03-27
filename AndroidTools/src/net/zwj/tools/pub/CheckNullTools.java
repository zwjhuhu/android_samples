package net.zwj.tools.pub;

/**
 * 检查对象是否为空
 */
public class CheckNullTools {

	final private static boolean SUCCESS = true;
	final private static boolean FAIL = false;
	
	public static boolean checkNull(String str) {
		if(str == null || "".equals(str))
			return SUCCESS;
		else
			return FAIL;
	}
}
