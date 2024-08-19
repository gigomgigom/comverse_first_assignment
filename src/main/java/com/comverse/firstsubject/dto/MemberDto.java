package com.comverse.firstsubject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDto {
	private String memberId;
	private String memberPw;
	private String memberEmail;
	
	private boolean memberEnabled;
	private String memberRole;
}
