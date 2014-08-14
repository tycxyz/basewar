package xyz.tyc.ds.core.data;

import xyz.tyc.baseapi.comm.PropHelper;
import xyz.tyc.baseapi.db.IDataSourceInfo;
import xyz.tyc.baseapi.db.QHelperProvider;
import xyz.tyc.baseapi.db.QueryHelper;
import xyz.tyc.ds.comm.CommConst;


/**
 * 本地工厂，关联公共库，提供公共帮助对象
 * @author Tao 2013-7-13 上午10:51:32
 */
public class LocalFactory {
	private static QueryHelper qh; //避免每次去new DataSourceInfo 
	
	/**
	 * 取得配置文件的全路径，注意配置文件需要放到classes根目录下
	 * @param fileName 文件名
	 * @return
	 */
	private static String getConfigureFile(String fileName){
		return LocalFactory.class.getResource("/").getPath() +  fileName;
	}
	
	public static PropHelper getPropHelper(){
		return getPropHelper(CommConst.FILE_DEFAULT_CONFIG);
	}
	
	/**
	 * 取得读配置助手
	 * @param fileName 配置文件名，默认找resource文件下
	 * @return
	 */
	public static PropHelper getPropHelper(String fileName){
		return PropHelper.getInstance(getConfigureFile(fileName));
	}
	
	public static QueryHelper getDefaultQHelper(){
		if (qh != null)
			return qh;
		qh = getQHelper(new DataSourceInfo(getPropHelper()));
		return qh;
	}
	public static QueryHelper getQHelper(IDataSourceInfo dInfo){
		return QHelperProvider.getInstance().getQueryHelper(dInfo); 
	}
}
