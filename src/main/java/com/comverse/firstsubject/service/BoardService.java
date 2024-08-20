package com.comverse.firstsubject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comverse.firstsubject.dao.BoardDao;
import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.Pager;
import com.comverse.firstsubject.dto.ReplyDto;
import com.comverse.firstsubject.dto.SearchIndex;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

	@Autowired
	BoardDao boardDao;
	
	//-------------------------------------------------------------
	
	//게시글 목록 조회
	public int getTotalRows(SearchIndex searchIndex) {
		return boardDao.selectTotalRows(searchIndex);
	}
	
	public List<BoardDto> getBoardList(SearchIndex searchIndex) {
		//페이지네이션 구현
		int totalRows = getTotalRows(searchIndex);
		int changePageNoToInteger = Integer.parseInt(searchIndex.getPageNo());
		Pager pager = new Pager(10, 5, totalRows, changePageNoToInteger);
		//pager.setStartRowNo(pager.getStartRowNo() - 1);
		searchIndex.setPager(pager);
		log.info(pager.getStartRowNo()+"");
		return boardDao.selectBoardList(searchIndex);
	}
	
	//-------------------------------------------------------------
	
	//게시글 상세 조회
	public BoardDto getBoardDetail(int boardNo) {
		return boardDao.selectBoardByBoardNo(boardNo);
	}	
	
	//댓글 목록 조회
	public List<ReplyDto> getReplyList(int boardNo) {
		return boardDao.selectReplyListByBoardNo(boardNo);
	}
	
	//-------------------------------------------------------------
	
	//게시물의 이미지 파일 정보 조회
	public BoardDto getBoardImg(int boardNo) {
		return boardDao.selectBoardImgByBoardNo(boardNo);
	}
	
	//-------------------------------------------------------------
	//게시글 작성
	public void saveBoardOnDataBase(BoardDto board) {
		boardDao.insertNewBoard(board);
	}
	//게시글 수정
	public void modifyBoard(BoardDto board) {
		boardDao.updateBoard(board);
	}
	//게시글 삭제
	public void deleteBoard(int boardNo) {
		boardDao.updateBoardToDisable(boardNo);
	}
	//-------------------------------------------------------------
	//댓글 작성
	public void writeReply(ReplyDto reply) {
		boardDao.insertReply(reply);
	}
	//댓글 삭제
	public void removeComment(int replyNo) {
		boardDao.updateReplyEnabled(replyNo);
	}

}
