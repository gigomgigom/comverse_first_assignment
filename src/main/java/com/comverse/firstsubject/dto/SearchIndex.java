package com.comverse.firstsubject.dto;

import lombok.Data;

@Data
public class SearchIndex {
	public String searchCtg;
	public String keyword;
	public String pageNo;
	public Pager pager;
}
