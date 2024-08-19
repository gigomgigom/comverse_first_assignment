package com.comverse.firstsubject.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.comverse.firstsubject.dao.MemberDao;
import com.comverse.firstsubject.dto.MemberDto;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	MemberDao memberDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDto member = memberDao.selectByMemberId(username);
		
		if(member == null) {
			throw new UsernameNotFoundException("아이디가 존재하지않습니다.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(member.getMemberRole()));
		
		AppUserDetails userDetails = new AppUserDetails(member, authorities);
		return userDetails;
	}
	
	
}
