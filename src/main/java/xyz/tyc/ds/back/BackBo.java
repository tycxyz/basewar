package xyz.tyc.ds.back;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xyz.tyc.baseapi.comm.DateUtil;
import xyz.tyc.baseapi.comm.StringUtil;
import xyz.tyc.ds.comm.CommConst;
import xyz.tyc.ds.core.data.LocalFactory;
import xyz.tyc.ds.po.Music;


/**
 * @author taoyuchu Jul 21, 2014
 */
public class BackBo {

	/**
	 * 用户登录
	 * @param params 
	 * @param req
	 */
	public static Object[][] login(Map<String, String> params, HttpServletRequest req) {
		String name = params.get("name");
		String pwd = params.get("password");
		
		if (StringUtil.isEmpty(name) || StringUtil.isEmpty(pwd)) {
			return new Object[][]{{"result", -1}, {"msg", "用户名或密码为空"}};
		}
		
		Long exists = LocalFactory.getDefaultQHelper().queryFirst(Long.class,
				"select 1 from manager where name = ?", new Object[]{name});
		if (exists == null) {
			return new Object[][]{{"result", -1}, {"msg", "没有这个用户"}};
		}
		exists = LocalFactory.getDefaultQHelper().queryFirst(Long.class, "select 1 from manager where name = ? and password = ?", new Object[]{name, pwd});
		
		if (exists != null && exists.longValue() > 0) {
			req.getSession(true).setAttribute("backuser", name);
			return new Object[][]{{"result", 1}, {"msg", "登录成功"}};
		} else {
			return new Object[][]{{"result", -1}, {"msg", "用户名或密码错误"}};
		}
	}
	
	/**
	 * 添加数据字典
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_tree_add(Map<String, String> params) {
		String pddid = params.get("pddid");
		String name =  params.get("name");
		String itemkey = params.get("itemkey");
				
		String itemvalue = params.get("itemvalue");
		String ordinal = params.get("ordinal");
		String dept = params.get("dept");
		
		if (StringUtil.isEmpty(pddid) || StringUtil.isEmpty(name) || StringUtil.isEmpty(itemkey) 
				|| StringUtil.isEmpty(itemvalue) || StringUtil.isEmpty(ordinal) || !StringUtil.isNumeric(ordinal))
			return new Object[][]{{"result", -1}, {"msg", "参数不完整，pddid/name/itemkey/itemvalue/ordinal，或参数ordinal不是数字"}};
		//判断是否存在同名的itemkey
		Long exists = LocalFactory.getDefaultQHelper().queryFirst(Long.class
				, "select 1 from data_dict where pddid=? and itemkey=?"
				, new Object[]{pddid, itemkey});
		if (exists != null && exists.intValue() == 1 ){
			return new Object[][]{{"result", -1}, {"msg", "存在同名的itemkey，放弃新增！"}};
		}
		int result = LocalFactory.getDefaultQHelper().cud("insert into data_dict (pddid, name, itemkey, itemvalue, dept, ordinal) values (?,?,?,?,?,?)"
				, new Object[]{pddid, name, itemkey, itemvalue, dept, ordinal}); 
		
		if (result > 0)
			return new Object[][]{{"result", result}, {"msg", "成功添加数据项！"}};
		else
			return new Object[][]{{"result", -1}, {"msg", "添加数据项失败！"}};
		
	}

	/**
	 * 添加kv字典
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_kv_add(Map<String, String> params) {
		String itemKey = params.get("itemkey");
		String itemName = params.get("itemname");
		String itemValue = params.get("itemvalue");
		String dept = params.get("dept");
		
		Long exist = LocalFactory.getDefaultQHelper().queryFirst(Long.class, "select 1 from data_dict_kv where itemkey=?", new Object[]{itemKey});
		
		if (exist != null && exist.longValue() > 0) {
			return new Object[][]{{"result", -1}, {"msg", "已经存在这个itemkey的项！"}};
		}
		int result = LocalFactory.getDefaultQHelper().cud("insert into data_dict_kv (name, itemkey, itemvalue, dept) values (?, ?, ?, ?)", new Object[]{itemName, itemKey, itemValue, dept});
		return new Object[][]{{"result", result}, {"msg", "成功添加" + result + "条记录！"}};
	}

	/**
	 * 编辑kv数据字典
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_kv_edit(Map<String, String> params) {
		String ddid = params.get("ddid");
		
		if (StringUtil.isEmpty(ddid) || !StringUtil.isNumeric(ddid)) {
			return new Object[][]{{"result", -1}, {"msg", "无法数据字典项编号参数！"}};
		}
		
		int result = LocalFactory.getDefaultQHelper().cud("update data_dict_kv set name=?, itemkey=?, itemvalue=?, used=? where ddid=?", new Object[]{
				params.get("name"), params.get("itemkey"), params.get("itemvalue"), params.get("used"), ddid
		});
		
		return new Object[][]{{"result", result}, {"msg", "数据字典更新成功！"}};
	}

	/**
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_kv_delete(Map<String, String> params) {
		int result = LocalFactory.getDefaultQHelper().cud("delete from data_dict_kv where ddid=?", new Object[]{params.get("ddid")});
		
		if (result > 0) {
			return new Object[][]{{"result", result}, {"msg", "删除数据字典成功！"}};
		} else {
			return new Object[][]{{"result", -1}, {"msg", "删除数据字典失败！"}};
		}
	}

	/**
	 * 保存数据字典
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_tree_edit(Map<String, String> params) {
		String ddid = params.get("ddid");
		String name = params.get("name"); 
		String itemkey = params.get("itemkey");
		String itemvalue = params.get("itemvalue");
		String dept = params.get("dept");
		String ordinal = params.get("ordinal");
		
		if (StringUtil.isEmpty(ddid) || StringUtil.isEmpty(name) || StringUtil.isEmpty(itemkey) 
				|| StringUtil.isEmpty(itemvalue) || StringUtil.isEmpty(ordinal))
			return new Object[][]{{"result", -1}, {"msg", "参数不完整，pddid/name/itemkey/itemvalue/ordinal"}};
		//判断是否存在同名的itemkey
		Long exists = LocalFactory.getDefaultQHelper().queryFirst(Long.class
				, "select 1 from data_dict a where a.ddid=? and exists (select 1 from data_dict b where b.pddid=a.pddid and b.ddid!=a.ddid and b.itemkey=? )"
				, new Object[]{ddid, itemkey});
		if (exists != null && exists.intValue() == 1 ){
			return new Object[][]{{"result", -1}, {"msg", "存在同名的itemkey，放弃更新！"}};
		}
		int result = LocalFactory.getDefaultQHelper().cud("update data_dict set name=?, itemkey=?, itemvalue=?, dept=?, ordinal=?, deleted=? where ddid=?"
						, new Object[]{name, itemkey, itemvalue, dept, ordinal, params.get("deleted"), ddid});
		return new Object[][]{{"result", result}, {"msg", "成功更新数据项！"}};
	}

	/**
	 * 删除数据字典
	 * @param params
	 * @return
	 */
	public static Object[][] datadict_tree_delete(Map<String, String> params) {
		String ddid = params.get("ddid");
		
		if (StringUtil.isEmpty(ddid) || !StringUtil.isNumeric(ddid))
			return new Object[][]{{"result", -1}, {"msg", "数据项编号格式错误"}};
		//待遍历
		List<Integer> nodes2Scan = new ArrayList<Integer>();
		nodes2Scan.add(Integer.valueOf(ddid));
		 
		//待删除
		List<Integer> nodes2Delete = new ArrayList<Integer>();
		scanSubNodes(nodes2Scan, null, nodes2Delete);
		
		int size = nodes2Delete.size();

		if (size > 0) {
			Object[][] os = new Object[size][1];
			
			for (int i = 0; i < size; i++) {
				os[i][0] = nodes2Delete.get(i);
			}
			int[] result = LocalFactory.getDefaultQHelper().batchCUD("delete from data_dict where ddid = ?"
					, os);
			int num = 0;
			
			for (Integer r : result) {
				num += r.intValue();
			}
			return new Object[][]{{"result", num}, {"msg", "删除成功" + num + "条！"}};
		}
		return new Object[][]{{"result", -1}, {"msg", "删除失败！"}};
	}

	/**
	 * @param nodes2Scan 当前这次要遍历的node
	 * @param nextNodes2Scan 下轮要遍历的node
	 * @param nodes2Delete 待删除的node
	 */
	private static void scanSubNodes(List<Integer> nodes2Scan, List<Integer> nextNodes2Scan, List<Integer> nodes2Delete) {
		//初始化下轮待遍历的列表
		if (nextNodes2Scan == null)
			nextNodes2Scan = new ArrayList<Integer>();
		else 
			nextNodes2Scan.clear();
		Iterator<Integer> iter = nodes2Scan.iterator();	
		
		while (iter.hasNext()) {
			Integer ddid =  iter.next();
			//查询子结点，并加入到下轮待遍历列表
			List<Integer> subnodes = LocalFactory.getDefaultQHelper().queryMulti(Integer.class, "select ddid from data_dict where pddid = ?", new Object[]{ddid});
			
			if (subnodes != null && subnodes.size() > 0) {
				nextNodes2Scan.addAll(subnodes);
			}
			//当前结点加到待删除队列
			nodes2Delete.add(ddid);
		}
		
		//递归调用
		if (nextNodes2Scan != null && nextNodes2Scan.size() > 0)
			scanSubNodes(nextNodes2Scan, nodes2Scan, nodes2Delete);
	}
	
	//--------------业务接口示例
	/**
	 * 编辑音乐
	 * @param params
	 * @return
	 */
	public static Object[][] musicEdit(Map<String, String> params) {
		String language = params.get("langs");
		String mid = params.get("mid");
		String types = params.get("types");
		StringBuilder sbMsg  = new StringBuilder();
		
		if (StringUtil.isEmpty(mid)) {
			sbMsg.append("√不能确定音乐编号，未处理！\n");
		}
		if(StringUtil.isEmpty(types)) {
			sbMsg.append("√没有选择类别，未处理！\n");
		}
		if(StringUtil.isEmpty(language)) {
			sbMsg.append("√没有选择语言，未处理！\n");
		}
		
		if (sbMsg.length() > 0) {
			return new Object[][]{{"result", 0}, {"msg", sbMsg.toString()}};
		}
		_updateReference("ds_music_type", "【音乐类型属性】", types, new String[]{"mid", "typeid"}, mid, sbMsg);
		_updateReference("ds_music_language", "【音乐语言属性】", language, new String[]{"mid", "lid"}, mid, sbMsg);
		
		int result = LocalFactory.getDefaultQHelper().cud("update ds_music set mname=?, deleted=?, restype = ?, author=?, performer=?, recsupport=?, lasttime=? where mid=?"
				, new Object[]{params.get("mname"), params.get("deleted"), params.get("restype"), params.get("author")
						, params.get("performer"), params.get("recsupport"), DateUtil.getGMTDateString(), mid});
		sbMsg.append("√已更新音乐基本属性\n");
		return new Object[][]{{"result", result}, {"msg", sbMsg.toString()}};
	}
	/**
	 * 更新对应关系表，表的结构通常类似于primary key auto_increment/lid/mid/deleted/lasttime
	 * @param table 表名
	 * @param boDesc 业务描述
	 * @param ids 多个lid串
	 * @param columns 列名，即mid/lid这两个名字
	 * @param key mid的值
	 * @param sbMsg 返回消息
	 */
	private static void _updateReference(String table, String boDesc, String ids, String[] columns, String key, StringBuilder sbMsg) {
		int result = 0;
		String date = DateUtil.getGMTDateString();
		String[] arrIds = ids.split("\\,");
		int size = arrIds.length;
		StringBuilder sb = new StringBuilder();//sql语句;
		
		if(size > 0){
			
			//将不在列表里的置为假删除
			result = LocalFactory.getDefaultQHelper().cud(
					sb.append("update ").append(table).append(" set deleted = 1, lasttime = ? where ").append(columns[0]).append(" = ? and deleted = 0 and ").append(columns[1]).append(" not in (").append(ids).append(")").toString()
					, new Object[]{date, key});
			sbMsg.append("√").append(boDesc).append("更新情况：\n-->已停用").append(boDesc).append(result).append("条!\n");
			
			//将在列表里的，且表里也有，假删除标记置为有效
			sb = new StringBuilder();
			result = LocalFactory.getDefaultQHelper().cud(
					sb.append("update ").append(table).append(" set deleted = 0, lasttime = ? where ").append(columns[0]).append(" = ? and deleted = 1 and ").append(columns[1]).append(" in (").append(ids).append(")").toString()
					, new Object[]{date, key});
			sbMsg.append("-->已启用").append(boDesc).append(result).append("条!\n");
			
			//将仍然生效的id查出来，与语言列表进行对比
			sb = new StringBuilder();
			List<Integer> existsIDs = LocalFactory.getDefaultQHelper().queryMulti(Integer.class, 
					sb.append("select ").append(columns[1]).append(" from ").append(table).append(" where ").append(columns[0]).append(" = ? and deleted = 0").toString(), new Object[]{key});
			int existsCount = existsIDs == null ? 0 : existsIDs.size();
			//sbMsg.append("-->已保留原有语言属性").append(existsCount).append("条!\n");
			
			if (size > existsCount) {
				Object[][] o = new Object[size - existsCount][3];
				String slid;
				
				for (int j = 0, i = 0; i < size; i++) {
					slid = arrIds[i];
					if (!existsIDs.contains(Integer.valueOf(slid))) { //表中不存在的再插入
						o[j][0] = key;
						o[j][1] = slid;
						o[j][2] = date;
						j++;
					}
				}
				sb = new StringBuilder();
				int[] results = LocalFactory.getDefaultQHelper().batchCUD(
						sb.append("insert into ").append(table).append(" (").append(columns[0]).append(", ").append(columns[1]).append(", lasttime) values (?,?, ?)").toString(), o);
				result = 0;
	
				for (Integer i : results) {
					result += i;
				}
				sbMsg.append("-->已新增").append(boDesc).append(result).append("条\n");
			}
		}
	}
	/**
	 * 添加音乐
	 * @param params
	 * @return
	 */
	public static Object[][] musicAdd(Map<String, String> params) {
		String types = params.get("types");
		String[] arrTypes = types.split("\\,");
		
		String langid[] = params.get("langs").split("\\,");
		String name = params.get("mname");
		
		if(langid == null || langid.length == 0)
			return new Object[][]{{"result", 0}, {"msg", "没有选择语言，未处理！"}};
		
		if (StringUtil.isEmpty(name))
			return new Object[][]{{"result", 0}, {"msg", "音乐别名不能为空，未处理！"}};
		
		String date = DateUtil.getGMTDateString();
		long mid = LocalFactory.getDefaultQHelper()
			.insertAndReturnId("insert into ds_music (mname, restype, lasttime) values(?,?,?)"
					, new Object[]{name, params.get("restype"), date});
		
		if (mid > 0) {
			if(langid.length > 0){
				Object[][] o = new Object[langid.length][3];
				
				for (int i = 0; i < langid.length; i++) {
					o[i][0] = mid;
					o[i][1] = langid[i];
					o[i][2] = date;
				}
				LocalFactory.getDefaultQHelper().batchCUD("insert into ds_music_language (mid, lid, lasttime) values (?,?,?)", o);
			}
			
			if(arrTypes.length > 0){
				Object[][] o = new Object[arrTypes.length][3];
				
				for (int i = 0; i < arrTypes.length; i++) {
					o[i][0] = mid;
					o[i][1] = arrTypes[i];
					o[i][2] = date;
				}
				LocalFactory.getDefaultQHelper().batchCUD("insert into ds_music_type (mid, typeid, lasttime) values (?,?,?)", o);
			}
			return new Object[][]{{"result", 1}, {"msg", "成功添加音乐！"}, {"mid", mid}};
		} else {
			return new Object[][]{{"result", -1}, {"msg", "添加音乐失败！"}};
		}
	}

	/**
	 * 更新音乐文件的大小到字段
	 * @param rootPath
	 * @return
	 */
	public static Object[][] updateMfSize(String rootDir) {
		List<Music> ms = LocalFactory.getDefaultQHelper().queryMulti(Music.class, "select mid, mname, mfname, size from ds_music where not mfname is null");
		int size = 0;
		String DIR =  rootDir + CommConst.SUB_DIR_FILE;
		int fact = 0;
		StringBuilder sb = new StringBuilder();
		
		if (ms != null && (size = ms.size()) > 0) {
			Object[][] params = new Object[ms.size()][2];
			Music m;
			String mfname;
			File f;
			long len;
			
			for (int i = 0; i < size; i ++) {
				m = ms.get(i);
				mfname = m.getMfname();
				f =  new File(DIR + mfname);
				
				if (f.exists() && f.isFile()) {
					len = f.length() / 1024;
					
					//书籍包的大小不等，再更新数据库
					if (m.getSize() != null && len != m.getSize().intValue()) {
						params[fact][0] = len;
						params[fact][1] = m.getMid();
						fact ++;
						sb.append("编号为【").append(m.getMid()).append("】的音乐【").append(m.getMname())
							.append("】大小由【").append(m.getSize()).append("】更新为【").append(len).append("】<br>");
					}
				}
			}
			
			Object[][] final_params = Arrays.copyOf(params, fact);
			
			if (fact > 0) {
				LocalFactory.getDefaultQHelper().batchCUD("update ds_music set size = ? where mid = ?", final_params);
			}
		}
		return new Object[][]{{"result", fact}, {"msg", "成功更新" + fact + "个音乐的大小！" + (fact > 0 ? "详情：<br>" + sb.toString() : "") }};
	}
	
}
