package xyz.tyc.ds.core.data;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import xyz.tyc.baseapi.comm.JsonUtil;
import xyz.tyc.baseapi.exception.TycBaseException;


/**
 * @author taoyuchu 2014-7-19
 */
public class ServletHelper {

	/**
	 * 打印结果到客户端
	 * 
	 * @param objects
	 * @param resp 
	 */
	public static void prt(Object[][] results, HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		try {
			resp.getWriter().write(genArrayString(results));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TycBaseException comme) {
			comme.printStackTrace();
		}
	}
	
	/**
	 * 打印结果到客户端
	 * 
	 * @param results
	 * @param resp 
	 */
	public static void prt(String results, HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		try {
			resp.getWriter().write(results);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String genArrayString(Object[][] results) throws TycBaseException{
		return JsonUtil.genJsonString(results);
	}
}
