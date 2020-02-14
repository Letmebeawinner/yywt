package com.a_268.base.core;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 分页对象类
 * @author jingxue.chen
 */
public class Pagination implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int DEFAULT_PAGE_SIZE = 30;
	private int DEFAULT_CURRENTPAGE = 1;

	private int pageSize; // 每页默认30条数据
	private int currentPage; // 当前页
	private int totalPages; // 总页数
	private int totalCount; // 总数据数

	/**HttpServletRequest接口，用于获取URL*/
	private HttpServletRequest request;
	private String currentUrl;//当前URL
    private int begin=1;
    private int end=this.getTotalPages();

	public Pagination(int totalCount, int pageSize) {
		this.init(totalCount, pageSize);
	}

	public Pagination() {
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.currentPage = DEFAULT_CURRENTPAGE;
	}

	/**
	 * 初始化分页参数:需要先设置totalRows
	 */

	public void init(int totalCount, int pageSize) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		if ((totalCount % pageSize) == 0) {
			totalPages = totalCount / pageSize;
		} else {
			totalPages = totalCount / pageSize + 1;
		}

	}

	public void init(int totalCount, int pageSize, int currentPage) {
		this.currentPage = currentPage;
		this.init(totalCount, pageSize);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	@SuppressWarnings("unchecked")
	public void setRequest(HttpServletRequest request) {
		Enumeration<String> enumparam = request.getParameterNames();
		StringBuffer buf = request.getRequestURL().append("?");
		while(enumparam.hasMoreElements()){
			String name = enumparam.nextElement();
			String value = request.getParameter(name);
			try {
				buf.append(name).append("=").append(URLEncoder.encode(value,"utf-8")).append("&");
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		this.setCurrentUrl(buf.toString());
		this.request = request;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

    public void setPageList(){
        int maxShowPage =5;
        int _currentPage = this.getCurrentPage();
        int _totalPageSzie = this.getTotalPages();

        if(_totalPageSzie>maxShowPage){
            if(_currentPage>=maxShowPage){
                if(_totalPageSzie-_currentPage<maxShowPage){
                    this.setBegin(_totalPageSzie-maxShowPage+1);
                    this.setEnd(_totalPageSzie);
                }else{
                    this.setBegin(_currentPage-maxShowPage+2);
                    this.setEnd(_currentPage+1);
                }
            }else{
                this.setBegin(1);
                this.setEnd(maxShowPage);
            }
        }else{
            this.setBegin(1);
            this.setEnd(_totalPageSzie);
        }
    }

    public int getBegin() {
		setPageList();
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
		return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		return "Pagination [DEFAULT_PAGE_SIZE=" + DEFAULT_PAGE_SIZE
				+ ", DEFAULT_CURRENTPAGE=" + DEFAULT_CURRENTPAGE
				+ ", pageSize=" + pageSize + ", currentPage=" + currentPage
				+ ", totalPages=" + totalPages + ", totalCount=" + totalCount
				+ "]";
	}

}
