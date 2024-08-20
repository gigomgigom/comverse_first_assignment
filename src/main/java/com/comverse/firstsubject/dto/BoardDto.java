package com.comverse.firstsubject.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDto {
	private int boardNo;
	private String boardTitle;
	private String boardWriter;
	private Date boardDate;
	private String boardContent;
	private String imgName;
	private String imgType;
	private byte[] imgData;
	private boolean boardEnabled;
	
	private MultipartFile battach;
}
