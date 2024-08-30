package com.comverse.firstsubject.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.SearchIndex;

import jakarta.validation.Valid;

@Mapper
public interface NoticeDao {
	
	//공지사항 목록 조회에 사용
	public int selectTotalRows(SearchIndex searchIndex);
	//공지사항 목록 조회
	public List<BoardDto> selectNoticeList(SearchIndex searchIndex);
	//공지사항 작성
	public void insertNotice(BoardDto board);
	//공지사항 상세조회
	public BoardDto selectNoticeByBno(int boardNo);
	public void updateNoticeHitCnt(int boardNo);
	public HashMap<String, Object> selectNoticePreNext(int boardNo, SearchIndex searchIndex);
	//공지사항 수정
	public void updateNotice(BoardDto board);
	//공지사항 삭제
	public void updateNoticeToDelete(int boardNo);

}
