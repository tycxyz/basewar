package xyz.tyc.ds.po;

/**
 * 音乐类型，一个音乐可能对应多个类型，数据保存在tree类型的字典表中
 * @author taoyuchu Jul 28, 2014
 */
public class MusicType {
	
	private String typeval; //类型值
	private String typename;//名称，与语言对应，语言不同名称也不同
	/**
	 * @return the typename
	 */
	public String getTypename() {
		return typename;
	}
	/**
	 * @param typename the typename to set
	 */
	public void setTypename(String typename) {
		this.typename = typename;
	}
	/**
	 * @return the typeval
	 */
	public String getTypeval() {
		return typeval;
	}
	/**
	 * @param typeval the typeval to set
	 */
	public void setTypeval(String typeval) {
		this.typeval = typeval;
	}
	
	
}
