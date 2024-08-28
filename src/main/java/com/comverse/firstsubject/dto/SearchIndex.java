package com.comverse.firstsubject.dto;

import lombok.Data;

@Data
public class SearchIndex {
	public String searchCtg;
	public String keyword;
	public String pageNo;
	public Pager pager;
	
	public SearchIndex() {
		
	}
	
	public SearchIndex(String searchCtg, String keyword, String pageNo) {
		this.searchCtg = searchCtg;
		this.keyword = keyword;
		this.pageNo = pageNo;
	}
}
