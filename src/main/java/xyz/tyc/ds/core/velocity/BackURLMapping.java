package xyz.tyc.ds.core.velocity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 处理url映射（暂仅后台），比如/editbook/1 -> /editbook.vm?p1=1
 * @version 0.0.1 2013-9-11 
 * @author taoych  
 */
public class BackURLMapping implements Filter {
	private ServletContext context;
	private String PATH_PREFIX;
	private List<String> ignoreURIs = new ArrayList<String>();
	private List<String> ignoreExts = new ArrayList<String>();
	private String rootDomain = "weeocean.com";
	private final static String VM_EXT = ".vm";
	private final static String VM_INDEX = "/index" + VM_EXT;
	private HashMap<String, String> other_base = new HashMap<String, String>();
	private String default_base;
	
	public void init(FilterConfig cfg) throws ServletException {
		context = cfg.getServletContext();
		
		// 模板存放路径
		PATH_PREFIX = cfg.getInitParameter("template-path-prefix");
		String ignores = cfg.getInitParameter("ignore");
		// 某些URL前缀不予处理（例如 /img/***）
		if (ignores != null)
			for (String ig : StringUtils.split(ignores, ','))
				ignoreURIs.add(ig.trim());
		// 某些URL扩展名不予处理（例如 *.jpg）
		ignores = cfg.getInitParameter("ignoreExts");
		if (ignores != null)
			for (String ig : StringUtils.split(ignores, ','))
				ignoreExts.add('.' + ig.trim());
		// 主域名，必须指定
		String tmp = cfg.getInitParameter("domain");
		if (!StringUtils.isBlank(tmp))
			rootDomain = tmp;
		// 二级域名和对应页面模板路径
		Enumeration<String> names = cfg.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String v = cfg.getInitParameter(name);
			if (v.endsWith("/"))
				v = v.substring(0, v.length() - 1);
			if ("ignore".equalsIgnoreCase(name) || "ignoreExts".equalsIgnoreCase(name))
				continue;
			
			if ("default".equalsIgnoreCase(name))
				default_base = PATH_PREFIX + v;
			else
				other_base.put(name, PATH_PREFIX + v);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest)req).getServletPath();
		
		try {
			for (String ignoreUri : ignoreURIs) {
				if (uri.startsWith(ignoreUri)) {
					chain.doFilter(req, resp);
					return;
				}
			}
			
			for (String ignoreExt : ignoreExts) {
				if (uri.endsWith(ignoreExt)) {
					chain.doFilter(req, resp);
					return;
				}
			}
			
			String[] paths = StringUtils.split(uri, '/');		
			String vm = _GetTemplate((HttpServletRequest)req, paths, paths.length);
			
			RequestDispatcher rd = context.getRequestDispatcher(vm);
			rd.forward(req, resp);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private String _GetTemplate(HttpServletRequest req, String[] paths, int idx_base) {		
		StringBuilder vm = new StringBuilder(_GetTemplateBase(req));
		
		if(idx_base == 0)
			return vm.toString() + VM_INDEX + _MakeQueryString(paths, idx_base);//最终还是没找到，返回/WEB-INF/vm/www/index.vm?p1=urlmapping&p2=ss
		
		for(int i=0;i<idx_base;i++){
			vm.append('/');
			vm.append(paths[i]);
		}
		String vms = vm.toString();
		String the_path = vms;
		
		if(_IsVmExist(the_path + VM_EXT)) //找/WEB-INF/vm/www/urlmapping/ss.vm，第二轮找 /WEB-INF/vm/www/urlmapping.vm?p1=ss
			return the_path + VM_EXT + _MakeQueryString(paths, idx_base)+"&header="+paths[idx_base-1];
	
		the_path += VM_INDEX;

		if(_IsVmExist(the_path)) //找/WEB-INF/vm/www/urlmapping/ss/index.vm，第二轮找/WEB-INF/vm/www/urlmapping/index.vm?p1=ss
			return the_path + _MakeQueryString(paths, idx_base)+"&header="+paths[idx_base-1];
		
		vms += VM_EXT;
		if(_IsVmExist(vms)) //找/WEB-INF/vm/www/urlmapping/ss.vm，第二轮找/WEB-INF/vm/www/urlmapping.vm?p1=ss
			return vms + _MakeQueryString(paths, idx_base)+"&header="+paths[idx_base-1];
		
		return _GetTemplate(req, paths, idx_base-1);
	}
	
	private String _MakeQueryString(String[] paths, int idx_base) {
		StringBuilder params = new StringBuilder();
		int idx = 1;
		for(int i=idx_base;i<paths.length;i++){
			if(params.length()==0)
				params.append('?');
			if(i>idx_base)
				params.append('&');
			params.append("p");
			params.append(idx++);
			params.append('=');
			params.append(paths[i]);
		}
		if("".equals(params.toString()))params.append("?1=1");
		return params.toString();
	}
	
	private String _GetTemplateBase(HttpServletRequest req) {
		String base = null;
		String prefix = req.getServerName().toLowerCase();
		int idx = (rootDomain!=null)?prefix.indexOf(rootDomain):0;
		if(idx > 0){
			prefix = prefix.substring(0, idx - 1);
			base = other_base.get(prefix);
		}
		return (base==null)?default_base:base;
	}

	private final static List<String> vm_cache = new Vector<String>();
	
	/**
	 * 判断某个页面是否存在，如果存在则缓存此结果
	 * @param path
	 * @return
	 */
	private boolean _IsVmExist(String path){
		if(vm_cache.contains(path))
			return true; 
		File testFile = new File(context.getRealPath(path));
		boolean isVM = testFile.exists() && testFile.isFile();
		if(isVM)
			vm_cache.add(path);
		return isVM;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
