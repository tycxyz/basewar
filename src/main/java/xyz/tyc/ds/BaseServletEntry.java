package xyz.tyc.ds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.tyc.ds.core.data.ServletHelper;


/**
 * @author taoyuchu Jul 21, 2014
 */
public abstract class BaseServletEntry extends HttpServlet {
	private static final long serialVersionUID = 8591636172426283565L;
	protected abstract Method _getMethod(HttpServletRequest req);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Method m = _getMethod(req);
			
			if (m == null) {
				ServletHelper.prt("{\"msg\":\"用户没有登录或没有这个业务\", \"result\":-1}", resp);
			} else {
				m.invoke(this, new Object[]{req, resp});
			}
		} catch (Exception e) {
			ServletHelper.prt("{\"msg\":"+ e.getMessage() +",\"result\":-1}", resp);
			e.printStackTrace();
		}
	}

	/**
	 * 将request中的参数提取出来放到一个map中
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected Map<String, String> getParams(HttpServletRequest req) throws UnsupportedEncodingException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		Enumeration<String> en = req.getParameterNames();

		while (en.hasMoreElements()) {
			String key = en.nextElement();
			String value = req.getParameter(key);//new String((req.getParameter(key)).getBytes("ISO-8859-1"), "GBK");
			paramsMap.put(key, value);
		}
		return paramsMap;
	}
}
