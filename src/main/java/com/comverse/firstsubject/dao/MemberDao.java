package com.comverse.firstsubject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.comverse.firstsubject.dto.MemberDto;

@Mapper
public interface MemberDao {

	public MemberDto selectByMemberId(String username);

	public int insertMember(MemberDto member);

	public MemberDto selectByMemberEmail(String email);

}
