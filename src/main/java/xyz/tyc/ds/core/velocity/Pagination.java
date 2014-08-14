package xyz.tyc.ds.core.velocity;

import xyz.tyc.ds.comm.CommConst;

/**
 * 分页，用于保存对应的页面信息，比如总记录数，总页数，当前页，显示的页码等<br>
 * 
 * 在前端页面的规范表单命名规范是：count, current，这里的current是随时变化的<br>
 * 
 * 每次请求时，刷新current和previous和next的页码<br>
 * 
 * 每次显示12个页码，除了第一和最后一个页面，还有10个与current（包括）相邻的页码，即前3后6
 *  
 * @author taoych
 * 2014-2-8
 */
public class Pagination {
	private int count; //记录总数
	private int current; //当前页码
	private int previous; //上一页码
	private int next; //下一页码
	private int FIRST = 1;  //第1页码
	private int last;	 //最后页码
	private int perPage = CommConst.SIZE_PER_PAGE; //每页显示数
	private int totalPage; //总页数
	
	private double totalSize; //查询出来的所有书籍的总的大小
	
	
	private int limitStart; //查询limit起始值
	private int pageStart; //动态的起始页码
	private int pageEnd; //动态的结束页码
	
	public Pagination(int tot, int per){
		setPerPage(per);
		setCount(tot);
		setLast((tot % per == 0) ? tot / per : tot / per + 1);
	}
	//----------------查询过滤用------------------
	/**
	 * @return the startIndex
	 */
	public int getLimitStart() {
		limitStart = (current - 1) * perPage;
		return limitStart;
	}
	//----------------前端显示用------------------
	/**
	 * 设置当前页码，一系列的值需要跟随变化，分页必须是大于1页
	 * @param curr the current to set
	 */
	public void setCurrent(int curr) {
		// 如果超出最大页码，置为最大页
		if (curr > last) {
			curr = last;
		}
		this.current = curr;
		
		//当前页即为第1页
		if (curr == FIRST) {
			setPrevious(0);//置空			
		} else {
			setPrevious(curr - 1);
		}
		
		//当前页即为最后一页
		if (curr == last) {
			setNext(0);//置空			
		} else {
			setNext(curr + 1);
		}
		setPageScope(curr);
	}
	
	/**
	 * 找出中间页码的范围，前3后6，当前面不够3个时，在后面补。
	 * @param curr
	 */
	private void setPageScope(int curr) {
		//----前3
		int prefix = 0;
		for (; prefix < 3 && curr - prefix - 1 > FIRST; prefix++) {}
		int start = curr - prefix;
		
		//---除了前面的就是后面的，后面最多为9
		int need = 9 - prefix;
		int suffix = 0;
		for (; suffix < need && curr + suffix + 1 < last; suffix ++) {}
		int end = curr + suffix;
		
		//可能两者都为1
		if (start < end || (start == end && start != FIRST && end != last)) {
			setPageStart(start);
			setPageEnd(end);
		}
		//System.out.println("curr:" + curr + "|prefix:" + prefix + "|suffix:" + suffix + "|start:" + start + "|end:" + end);
	}
	
	/**
	 * @return the pageStart
	 */
	public int getPageStart() {
		return pageStart;
	}
	/**
	 * @param pageStart the pageStart to set
	 */
	public void setPageStart(int pageStart) {
		if (pageStart == FIRST)
			pageStart++; //如果是第1个，往后推一个，防重复显示
		this.pageStart = pageStart;
	}
	/**
	 * @return the pageEnd
	 */
	public int getPageEnd() {
		return pageEnd;
	}
	/**
	 * @param pageEnd the pageEnd to set
	 */
	public void setPageEnd(int pageEnd) {
		if (pageEnd == last) 
			pageEnd--; 
		this.pageEnd = pageEnd;
	}
	
	/**
	 * @return the perPage
	 */
	public int getPerPage() {
		return perPage;
	}
	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the current
	 */
	public int getCurrent() {
		return current;
	}
	
	
	/**
	 * @return the previous
	 */
	public int getPrevious() {
		return previous;
	}
	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	/**
	 * @return the next
	 */
	public int getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(int next) {
		this.next = next;
	}
	/**
	 * @return the first
	 */
	public int getFirst() {
		return FIRST;
	}	
	/**
	 * @return the last
	 */
	public int getLast() {
		return last;
	}
	/**
	 * @param last the last to set
	 */
	public void setLast(int last) {
		this.last = last;
		setTotalPage(last);
	}
	/**
	 * @return the totalSize
	 */
	public double getTotalSize() {
		return totalSize;
	}
	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(double totalSize) {
		this.totalSize = totalSize;
	}
}
