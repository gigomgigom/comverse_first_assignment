package com.comverse.firstsubject.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplyDto {
	private int replyNo;
	private String replyWriter;
	private int replyBoard;
	private Date replyDate;
	
	@NotBlank
	private String replyContent;
	private boolean replyEnabled;
}
