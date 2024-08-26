package com.comverse.firstsubject.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardDto {
	private int boardNo;
	
	@NotBlank
	private String boardTitle;
	
	private int boardCtg;
	private int preBoard;
	private String anonId;
	private String anonPw;
	private String boardWriter;
	private Date boardDate;
	
	@NotBlank
	private String boardContent;
	
	private String imgName;
	private String imgType;
	private byte[] imgData;
	private boolean boardEnabled;
	private String hitCnt;
	
	private String boardLevel;
	private String boardPath;
	
	private MultipartFile battach;
}
