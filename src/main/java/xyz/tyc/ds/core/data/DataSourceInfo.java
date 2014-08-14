package xyz.tyc.ds.core.data;

import xyz.tyc.baseapi.comm.PropHelper;
import xyz.tyc.baseapi.db.IDataSourceInfo;
import xyz.tyc.ds.comm.CommConst;


/**
 * 
 * @author Tao 2013-7-13 上午10:58:25
 */
public class DataSourceInfo implements IDataSourceInfo {
	PropHelper prop;
	
	public DataSourceInfo(PropHelper prop){
		this.prop = prop;
	}

	/* (non-Javadoc)
	 * @see com.words8.comm.db.IDataSourceInfo#getDBUrl()
	 */
	@Override
	public String getDBUrl() {
		return prop.get(CommConst.DBURL);
	}

	/* (non-Javadoc)
	 * @see com.words8.comm.db.IDataSourceInfo#getDBDriver()
	 */
	@Override
	public String getDBDriver() {
		return prop.get(CommConst.DBDRIVER);
	}

	/* (non-Javadoc)
	 * @see com.words8.comm.db.IDataSourceInfo#getDBUser()
	 */
	@Override
	public String getDBUser() {
		return prop.get(CommConst.DBUSER);
	}

	/* (non-Javadoc)
	 * @see com.words8.comm.db.IDataSourceInfo#getDBPassword()
	 */
	@Override
	public String getDBPassword() {
		return prop.get(CommConst.DBPWD);
	}

	/* (non-Javadoc)
	 * @see com.words8.comm.db.IDataSourceInfo#getDBMaxNum()
	 */
	@Override
	public String getDBMaxNum() {
		return prop.get(CommConst.DBMAX);
	}

}
