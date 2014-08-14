package xyz.tyc.ds.comm;

import xyz.tyc.baseapi.comm.DateUtil;

/**
 * @author taoyuchu Jul 25, 2014
 */
public class RsUtil {
	/**
	 * 生成随机14位字符串，用作文件名
	 * @return
	 */
	public static String generateRandomString() {
		String day = DateUtil.getGMTDateString("yyMMdd");
		return day + java.util.UUID.randomUUID().toString().substring(0, 8);
	}
	
	private static String generateRandomString(int len) {
		return java.util.UUID.randomUUID().toString().substring(0, len);
	}
	
	public static void main(String[] args) {
		System.out.println(generateRandomString(8));//fa2ddc35
	}
}
