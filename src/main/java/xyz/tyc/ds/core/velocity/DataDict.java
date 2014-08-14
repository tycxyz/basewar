package xyz.tyc.ds.core.velocity;

import java.io.Serializable;
import java.util.Date;

/**
 * 数字词典
 * 
 * @author taoyuchu 2014-3-18
 */
public class DataDict implements Serializable {

	private Integer ddid;
	private Integer pddid;
	private String name;
	private String itemkey;
	private String itemvalue;
	private String dept;
	private Integer ordinal;
	private Date lasttime;
	private Integer hasChild; //标示当前结点含有子结点
	private Integer used;
	private Integer deleted;
	
	
	/**
	 * @return the deleted
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the used
	 */
	public Integer getUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(Integer used) {
		this.used = used;
	}

	/**
	 * @return the hasChild
	 */
	public Integer getHasChild() {
		return hasChild;
	}

	/**
	 * @param hasChild the hasChild to set
	 */
	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemkey() {
		return itemkey;
	}

	public void setItemkey(String itemkey) {
		this.itemkey = itemkey;
	}

	public String getItemvalue() {
		return itemvalue;
	}

	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the ddid
	 */
	public Integer getDdid() {
		return ddid;
	}

	/**
	 * @param ddid the ddid to set
	 */
	public void setDdid(Integer ddid) {
		this.ddid = ddid;
	}

	/**
	 * @return the pddid
	 */
	public Integer getPddid() {
		return pddid;
	}

	/**
	 * @param pddid the pddid to set
	 */
	public void setPddid(Integer pddid) {
		this.pddid = pddid;
	}


	/**
	 * @return the ordinal
	 */
	public Integer getOrdinal() {
		return ordinal;
	}

	/**
	 * @param ordinal the ordinal to set
	 */
	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	/**
	 * @return the lasttime
	 */
	public Date getLasttime() {
		return lasttime;
	}

	/**
	 * @param lasttime the lasttime to set
	 */
	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

}
