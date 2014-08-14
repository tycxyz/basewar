package xyz.tyc.ds.core.web;

import xyz.tyc.ds.comm.CommConst;


/**
 * 汇总绑定业务与更新语句（符合同一个模式）
 * 
 * @author taoyuchu Jul 25, 2014
 */
public enum EnumStoredType {
	MUSIC_COVERPIC("music_coverpic", ""
	, "select coverpic from ds_music where mid=?"
	, "update ds_music set coverpic=?, lasttime=? where mid=?"
	, CommConst.SUB_DIR_FILE),
	
	MUSIC_FILE("music_file", ""
	, "select mfname from ds_music where mid=?"
	, "update ds_music set mfname=?, lasttime=? where mid=?"
	, CommConst.SUB_DIR_FILE),
	
	MUSIC_LRC("music_lrc", ""
	, "select lyric from ds_music where mid=?"
	, "update ds_music set lyric=?, lasttime=? where mid=?"
	, CommConst.SUB_DIR_FILE),
					
	;
	private String key;
	private String method;
	private String querySql;
	private String updateSql;
	private String path;
	
	/**
	 * 构造上传到服务器的资源元数据
	 * @param key 标识
	 * @param method 调用处理数据库的方法 
	 * @param path 保存文件的相对路径
	 */
	EnumStoredType(String key, String method, String querySql, String updateSql, String path) {
		this.key = key;
		this.method = method;
		this.querySql = querySql;
		this.updateSql = updateSql;
		this.path = path;
	}
	
	public static EnumStoredType getStoredType(String key){
		for (EnumStoredType item : values()) {
			if (item.getKey().equals(key))
				return item;
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getPath(){
		return path;
	}
	
	/**
	 * @return the querySql
	 */
	public String getQuerySql() {
		return querySql;
	}

	/**
	 * @return the updateSql
	 */
	public String getUpdateSql() {
		return updateSql;
	}

	public String getMethod(){
		return method;
	}
}
