package xyz.tyc.ds.client;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.tyc.ds.BaseServletEntry;
import xyz.tyc.ds.core.data.ServletHelper;


/**
 * 前台R接口集合
 * @author taoyuchu 2014-7-19
 */
public class Actions extends BaseServletEntry {
	private static final long serialVersionUID = 9053265145386459588L;
	
	//-------------------初始化方法集合
	private Map<String, Method> methods = null;
	
	//将公有方法加到列表中
	private void _retrieveMethods() throws ServletException {
		Method[] ms = this.getClass().getDeclaredMethods();
		methods = new HashMap<String, Method>(ms.length);
		
		for (Method m : ms) {
			//System.out.println("method:" + m.getName() + "/accessible:" + Modifier.isPublic(m.getModifiers()));
			
			if (Modifier.isPublic(m.getModifiers()) && (!"init".equals(m.getName()))) {
				methods.put(m.getName(), m);
			}
		}
	}
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		_retrieveMethods();
	}
	
	/**
	 * 通过uri解析获得将要调用的actions类里的方法名
	 * @param req
	 * @return
	 */	
	@Override
	protected Method _getMethod(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String mName = uri.substring(uri.lastIndexOf("/") + 1);
		return methods.get(mName);
	}

	//-------------------开放给客户端请求的方法
	public void listmusic(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		ServletHelper.prt(ClientBo.listMusic(getParams(req)), resp);
	}
	public void listmtype(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(ClientBo.listMusicTypes(getParams(req)), resp);
	}
}
