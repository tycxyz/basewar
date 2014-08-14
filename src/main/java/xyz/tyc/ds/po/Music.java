package xyz.tyc.ds.po;

import java.util.Date;

/**
 * @author taoyuchu 2014-7-19
 */
public class Music {
	private Integer mid;
	private String mname;
	private String mfname;
	private Integer size;
	private String author;
	private String performer;
	private String mtype;
	private String restype;
	private String source;
	private Integer favourites;
	private Integer f = 0; //我是否点赞了
	private String coverpic;
	private String lyric;
	private Integer recsupport;
	private Integer deleted;
	private Date lasttime;
	
	
	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
	/**
	 * @return the f
	 */
	public Integer getF() {
		return f;
	}
	/**
	 * @param f the f to set
	 */
	public void setF(Integer f) {
		this.f = f;
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
	/**
	 * @return the mid
	 */
	public Integer getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	/**
	 * @return the mname
	 */
	public String getMname() {
		return mname;
	}
	/**
	 * @param mname the mname to set
	 */
	public void setMname(String mname) {
		this.mname = mname;
	}
	/**
	 * @return the mfname
	 */
	public String getMfname() {
		return mfname;
	}
	/**
	 * @param mfname the mfname to set
	 */
	public void setMfname(String mfname) {
		this.mfname = mfname;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the performer
	 */
	public String getPerformer() {
		return performer;
	}
	/**
	 * @param performer the performer to set
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}
	/**
	 * @return the mtype
	 */
	public String getMtype() {
		return mtype;
	}
	/**
	 * @param mtype the mtype to set
	 */
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	/**
	 * @return the restype
	 */
	public String getRestype() {
		return restype;
	}
	/**
	 * @param restype the restype to set
	 */
	public void setRestype(String restype) {
		this.restype = restype;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the favourites
	 */
	public Integer getFavourites() {
		return favourites;
	}
	/**
	 * @param favourites the favourites to set
	 */
	public void setFavourites(Integer favourites) {
		this.favourites = favourites;
	}
	/**
	 * @return the coverpic
	 */
	public String getCoverpic() {
		return coverpic;
	}
	/**
	 * @param coverpic the coverpic to set
	 */
	public void setCoverpic(String coverpic) {
		this.coverpic = coverpic;
	}
	/**
	 * @return the lyric
	 */
	public String getLyric() {
		return lyric;
	}
	/**
	 * @param lyric the lyric to set
	 */
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	
	/**
	 * @return the recsupport
	 */
	public Integer getRecsupport() {
		return recsupport;
	}
	/**
	 * @param recsupport the recsupport to set
	 */
	public void setRecsupport(Integer recsupport) {
		this.recsupport = recsupport;
	}
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mname + mfname;
	}
	
}
