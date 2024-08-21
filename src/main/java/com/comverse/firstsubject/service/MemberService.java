package com.comverse.firstsubject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.comverse.firstsubject.dao.MemberDao;
import com.comverse.firstsubject.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	
	@Autowired
	MemberDao memberDao;
	
	public int createNewAccount(MemberDto member) {
		// TODO Auto-generated method stub
		member.setMemberEnabled(true);
		member.setMemberRole("USER_ROLE");
		
		PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		member.setMemberPw(pe.encode(member.getMemberPw()));
		
		return memberDao.insertMember(member);
	}
	
	public boolean findAccountById(String memberId) {
		MemberDto mem = memberDao.selectByMemberId(memberId);
		if(mem != null) {
			return true;
		}
		return false;
	}
	
	public boolean findAccountByEmail(String email) {
		MemberDto mem = memberDao.selectByMemberEmail(email);
		if (mem != null) {
			return true;
		}
		return false;
	}

}
