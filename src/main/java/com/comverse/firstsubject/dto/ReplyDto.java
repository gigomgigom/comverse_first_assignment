package com.comverse.firstsubject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDto {
	private int replyNo;
	private String replyWriter;
	private int replyBoard;
	private Date replyDate;
	private String replyContent;
	private boolean replyEnabled;
}
