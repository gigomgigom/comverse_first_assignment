package com.comverse.firstsubject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.ReplyDto;
import com.comverse.firstsubject.dto.SearchIndex;

@Mapper
public interface BoardDao {

	//게시글 전체 갯수 조회
	public int selectTotalRows(SearchIndex searchIndex);
	
	//게시글 목록 조회
	public List<BoardDto> selectBoardList(SearchIndex searchIndex);
	//게시글 상세 조회(게시글 정보 조회)
	public BoardDto selectBoardByBoardNo(int boardNo);
	//게시글 상세 조회(답글 목록 조회)
	public List<ReplyDto> selectReplyListByBoardNo(int boardNo);
	
	//게시글 이미지 파일 조회
	public BoardDto selectBoardImgByBoardNo(int boardNo);
	
	//게시글 작성
	public void insertNewBoard(BoardDto boardDto);
	//게시글 수정
	public void updateBoard(BoardDto board);
	//게시글 삭제(상태 컬럼 변경)
	public void updateBoardToDisable(int boardNo);
	
	//댓글 작성
	public void insertReply(ReplyDto reply);
	//댓글 삭제
	public void updateReplyEnabled(int replyNo);

}
