package xyz.tyc.ds.po;


import java.util.Date;

/**
 * @author taoyuchu 2014-6-12
 */
public class SysLog {
	private String message;
	private Date lasttime;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
