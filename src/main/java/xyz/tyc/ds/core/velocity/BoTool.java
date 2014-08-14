package xyz.tyc.ds.core.velocity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xyz.tyc.baseapi.comm.StringUtil;
import xyz.tyc.ds.comm.CommConst;
import xyz.tyc.ds.core.data.LocalFactory;
import xyz.tyc.ds.po.Music;
import xyz.tyc.ds.po.SysLog;


/**
 * 自定义velocity工具类，这里主要用于后台管理页面的业务数据查询<br>
 * @author taoyuchu 2014-7-19
 */
public class BoTool {
	/**
	 * 返回树状字典某级目录下所有子结点的信息，以itemvalue作为key，name作为value，主要用于页面显示name
	 * @param path 路径，比如booksort.writing
	 * @return
	 */
	public static Map<String, String> getDataDictNames(String path) { 
		String[] paths = path.split("\\.");
		
		List<DataDict> dds = LocalFactory.getDefaultQHelper().queryMulti(DataDict.class, getSQL(paths));
		Map<String, String> m_dds = null;
		
		if (dds != null && dds.size() > 0) {
			m_dds = new HashMap<String, String>();
		
			for(DataDict dd : dds) {
				m_dds.put(dd.getItemvalue(), dd.getName());
			}
		}
		return m_dds;
	}
	/**
	 * 拼接SQL语句字符串
	 * 
	 * @param value
	 */
	public static String getSQL(String[] paths) {

		StringBuffer sb = new StringBuffer();
		String temp = "son";
		sb.append("select * from data_dict " + temp + " where exists (");


		for (int i = paths.length - 1; i >= 0; i--) {
			String str = paths[i];
			if (i == (paths.length - 1)) {
				sb.append("select 1 from data_dict " + str + " where ddid = " + temp + ".pddid and itemkey = '" + str + "'");
				temp = str;
				continue;
			}
			sb.append(" and  exists (select 1 from data_dict " + str + " where ddid  = " + temp + ".pddid and itemkey = '" + str + "'");
			temp = str;
		}

		for (int i = 0; i < paths.length; i++)
			sb.append(")");

		return sb.toString();
	}
	/**
	 * 获取当前数据字典的属性
	 * @param ddid
	 * @return
	 */
	public DataDict getCurrentDataDict(String ddid) {
		if (StringUtil.isEmpty(ddid) || !StringUtil.isNumeric(ddid))
			return null;
		
		DataDict data  = LocalFactory.getDefaultQHelper().queryFirst(DataDict.class, "select * from data_dict where ddid = ?", new Object[]{ddid});
		return data;
	}
	
	/**
	 * 根据父结点查数据字典
	 * @param pddid
	 * @return
	 */
	public List<DataDict> getDataDict(String pddid){
		if (StringUtil.isEmpty(pddid) || !StringUtil.isNumeric(pddid)) {
			pddid =  "0";
		}
		String sql =  new StringBuilder()
			.append("SELECT distinct a.*, b.hasChild FROM `data_dict` a ")
			.append("LEFT JOIN (SELECT 1 AS hasChild, pddid FROM data_dict ) b ") 
			.append("ON b.pddid = a.ddid ")
			.append("WHERE a.pddid = ? ")
			.append("ORDER BY a.ordinal ASC").toString();
		
		List<DataDict> rs = LocalFactory.getDefaultQHelper().queryMulti(DataDict.class
				, sql, new Object[]{pddid});
		return rs;
	}
	
	/**
	 * 查询所有的kv类数据字典
	 * @return
	 */
	public List<DataDict> getKvDataDict(){
		return LocalFactory.getDefaultQHelper().queryMulti(DataDict.class, "select * from data_dict_kv");
	}
	public DataDict getDataDictKv(String ddid) {
		return LocalFactory.getDefaultQHelper().queryFirst(DataDict.class, "select * from data_dict_kv where ddid= ?"
				, new Object[]{ddid});
	}
	public List<SysLog> logList(String cond) {
		return LocalFactory.getDefaultQHelper().queryMulti(SysLog.class, "select message, lasttime from logs where message like '" + cond + "%' order by id desc");
	}
	
	//-------------------------具体业务相关
	public List<Music> getMusics(){
		List<Music> ms = LocalFactory.getDefaultQHelper().queryMulti(Music.class
				, "select * from ds_music");
		return ms;
	}
	
	private String _getContentFromReq(HttpServletRequest req, String name) {
		String result;
		try {
			String v = req.getParameter(name);
			if (StringUtil.isEmpty(v)) {
				return null;
			}
			result = new String(v.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return req.getParameter(name);
		}
		return result;
	}
	/**
	 * @param intValue
	 * @param sizePerPage
	 * @return
	 */
	private Pagination _getPagination(int count, int per) {
		Pagination pag = new Pagination(count, per);
		pag.setCurrent(1);
		return pag;
	}

	/**
	 * @param req
	 * @return
	 */
	private Pagination _getPagination(HttpServletRequest req) {
		Pagination pag = null;
		
		try {
			Map<String, String[]> params = req.getParameterMap();
			
			// 两个必要参数
			if (!params.containsKey("count") && !params.containsKey("perPage"))
				return null;
			
			pag = new Pagination( Integer.parseInt( ((String[])params.get("count"))[0] )
					, Integer.parseInt( ((String[])params.get("perPage"))[0] ));
			
			//如果没有Current参数即取1
			pag.setCurrent(params.get("current") == null ? 1 : Integer.parseInt( ((String[])params.get("current")) [0]) );
		} catch (NumberFormatException e) {
			pag.setCurrent(1);
		}
		return pag;
	}
	/**
	 * 查询音乐，并支持分页
	 * @param req
	 * @return
	 */
	public Map<String, Object> getMusics(HttpServletRequest req){
		//过滤条件包括：语言，音乐类型，收费类型，名称
		String lid = req.getParameter("lid");
		String type = req.getParameter("type");
		String restype = req.getParameter("restype");
		String mname = _getContentFromReq(req, "mname");
		StringBuilder sbsql = new StringBuilder();
		sbsql.append("select * from ds_music mu where 1 = 1");
		
		if (!StringUtil.isEmpty(restype)) {
			sbsql.append(" and restype =  '").append(restype).append("'");
		}
		if (!StringUtil.isEmpty(lid)) {
			sbsql.append(" and exists (select 1 from ds_music_language where mid = mu.mid and deleted = 0 and lid = ").append(lid).append(")");
		}
		if (!StringUtil.isEmpty(type)) {
			sbsql.append(" and exists (select 1 from ds_music_type where mid = mu.mid and deleted = 0 and typeid = ").append(type).append(")");
		}
		if (!StringUtil.isEmpty(mname)) {
			sbsql.append(" and mname like '%").append(mname).append("%'");
		}
		sbsql.append(" order by lasttime desc");
		Map<String, Object> result = new HashMap<String, Object>();
		
		//----分页支持
		Pagination pag = _getPagination(req); //根据request的参数生成pag对象
		//还没有初始化过
		if (pag == null) {
			Long count = LocalFactory.getDefaultQHelper().queryFirst(Long.class
					, sbsql.toString().replace("*", "count(1)"));
			if (count != null && count.longValue() > 0) {
				pag = _getPagination(count.intValue(), CommConst.SIZE_PER_PAGE); //根据查询数量重新生成
			}
		}
		
		if (pag != null) {
			sbsql.append(" limit ").append(pag.getLimitStart()).append(",").append(pag.getPerPage());
			result.put("pag", pag);
		}
		//----end 分页支持
		String sql = sbsql.toString();//.replace("*", fieldsQuery);
		//System.out.println(sql);
		List<Music> books = LocalFactory.getDefaultQHelper().queryMulti(Music.class, sql);
		result.put("musics", books);
		result.put("mname", mname);
		
		return result;
	}
	public Music getMusic(String mid) {
		return LocalFactory.getDefaultQHelper().queryFirst(Music.class, "select * from ds_music where mid =?", new Object[]{mid});
	}
	public String getMusicTypes(String mid){
		String str = "";
		List<Integer> list = LocalFactory.getDefaultQHelper().queryMulti(Integer.class, "select typeid from ds_music_type where mid=? and deleted = 0", new Object[]{mid});
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder();
			
			for (Integer lid : list) {
				sb.append(",").append(lid);
			}
			sb.deleteCharAt(0);
			str = sb.toString();
		}
		return str;
	}
	public String getMusicLanguages(String mid){
		String str = "";
		List<Integer> list = LocalFactory.getDefaultQHelper().queryMulti(Integer.class, "select lid from ds_music_language where mid=? and deleted = 0", new Object[]{mid});
		
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder();
			
			for (Integer lid : list) {
				sb.append(",").append(lid);
			}
			sb.deleteCharAt(0);
			str = sb.toString();
		}
		return str;
	}
}
