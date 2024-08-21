package com.comverse.firstsubject.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.comverse.firstsubject.dto.MemberDto;

public class MemberValidator implements Validator {
	
	private static String idPattern = "^[a-z]+$";
	private static String pwPattern = "^[a-zA-Z]+$";
	private static String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	
	@Override
	public boolean supports(Class<?> clazz) {
		boolean check = MemberDto.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberDto member = (MemberDto) target;
		
		String memberId = member.getMemberId();
		if(memberId == null || memberId.equals("")) {
			errors.rejectValue("memberId", "아이디는 필수 입력 값입니다.");
		} else if(memberId.length() < 5 || memberId.length() > 10) {
			errors.rejectValue("memberId", "아이디는 5~10자 이어야 합니다.");
		} else if(!Pattern.matches(idPattern, memberId)) {
			errors.rejectValue("memberId", "아이디는 소문자로 구성되어야합니다.");
		} //아이디 존재 여부를 파악해야하는데....
		
		String memberPw = member.getMemberPw();
		if(memberPw == null || memberId.isBlank()) {
			errors.rejectValue("memberPw", "비밀번호는 필수 값입니다.");
		} else if(memberPw.length() < 8 || memberPw.length() > 12) {
			errors.rejectValue("memberPw", "비밀번호는 8~12자이어야 합니다.");
		} else if(!Pattern.matches(pwPattern, memberPw)) {
			errors.rejectValue("memberPw", "비밀번호는 영대문자 8~12자이어야 합니다.");
		}
		
		String memberEmail = member.getMemberEmail();
		if(memberEmail == null || memberEmail.equals("")) {
			errors.rejectValue("memberEmail", "이메일은 필수 입력 값입니다.");
		} else if(!Pattern.matches(emailPattern, memberEmail)) {
			errors.rejectValue("memberEmail", "이메일이 유효하지 않습니다.");
		}
	}

}
