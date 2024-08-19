package com.comverse.firstsubject.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.comverse.firstsubject.dto.MemberDto;

public class AppUserDetails extends User {
	
	private MemberDto member;
	
	public AppUserDetails(MemberDto member, List<GrantedAuthority> authorities) {
		super(
				member.getMemberId(), member.getMemberPw(), member.isMemberEnabled(), 
				true, true, true, authorities
		);
		this.member = member;
	}
	
	public MemberDto getMember() {
		return member;
	}
	
}
