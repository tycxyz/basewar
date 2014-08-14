package xyz.tyc.ds.back;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import xyz.tyc.baseapi.comm.JsonUtil;
import xyz.tyc.ds.BaseServletEntry;
import xyz.tyc.ds.core.data.ServletHelper;
import xyz.tyc.ds.core.web.Uploader;


/**
 * 后台CURD接口
 * @author taoyuchu Jul 21, 2014
 */
public class Actions extends BaseServletEntry {
	private static final long serialVersionUID = 378083299315054695L;
	
	//-------------------初始化方法集合
	private Map<String, Method> methods = null;
	
	//将公有方法加到列表中，开放给外部调用
	private void _retrieveMethods() throws ServletException {
		Method[] ms = this.getClass().getDeclaredMethods();
		methods = new HashMap<String, Method>(ms.length);
		
		for (Method m : ms) {
			if (Modifier.isPublic(m.getModifiers()) && (!"init".equals(m.getName()))) {
				methods.put(m.getName(), m);
			}
		}
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
		
		//需要验证session才能进行的操作
		if ((!"login".equals(mName)) && (!"upload".equals(mName))){
			if (req.getSession().getAttribute("backuser") == null) {
				return null;
			}
		}
		return methods.get(mName);
	}
	
	//-------------------上传文件
	private String _rootDir;
	private ServletFileUpload _upload;
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		_retrieveMethods();
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		_rootDir = servletContext.getRealPath("");
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();				
		factory.setRepository(repository);

		// Create a new file upload handler
		_upload = new ServletFileUpload(factory);
	} 
	public void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Uploader loader = new Uploader(req, _upload, _rootDir);
		String[] result = loader.execute();	
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().print(JsonUtil.genJsonObject(new Object[][]{{"msg", result[0]},{"fileName", result[1]}}));
		loader = null;
	}
	
	//-------------------一般接口示例
	//后台管理员登录
	public void login(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.login(getParams(req), req), resp);
	}
	//后台管理员登出
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.getSession().removeAttribute("backuser");
		ServletHelper.prt("{\"result\":1, \"msg\":\"success\"}", resp);
	}
	//树结构数据字典项的添加
	public void datadict_tree_add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_tree_add(getParams(req)), resp);
	}
	//树结构数据字典项的编辑
	public void datadict_tree_edit(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_tree_edit(getParams(req)), resp);
	}

	//树结构数据字典项的删除
	public void datadict_tree_delete(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_tree_delete(getParams(req)), resp);
	}

	//kv数据字典项的添加
	public void datadict_kv_add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_kv_add(getParams(req)), resp);
	}

	//kv数据字典项的编辑
	public void datadict_kv_edit(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_kv_edit(getParams(req)), resp);
	}
	//kv数据字典项的删除
	public void datadict_kv_delete(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		ServletHelper.prt(BackBo.datadict_kv_delete(getParams(req)), resp);
	}

	//-------------------业务接口示例
	public void musicadd(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		ServletHelper.prt(BackBo.musicAdd(getParams(req)), resp);
	}
	public void musicedit(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		ServletHelper.prt(BackBo.musicEdit(getParams(req)), resp);
	}
	public void update_music_file_size(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		ServletHelper.prt(BackBo.updateMfSize(this.getServletConfig().getServletContext().getRealPath("")), resp);
	}
}
