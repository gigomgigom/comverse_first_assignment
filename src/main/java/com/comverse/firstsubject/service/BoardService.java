package com.comverse.firstsubject.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return boardDao.selectBoardList(searchIndex);
	}
	
	//-------------------------------------------------------------
	
	//게시글 상세 조회
	@Transactional
	public BoardDto getBoardDetail(int boardNo) {
		BoardDto board = boardDao.selectBoardByBoardNo(boardNo);
		if(board != null) {
			boardDao.updateBoardHitCnt(boardNo);
		}
		return board;
	}
	//댓글의 부모 글 조회
	public BoardDto getParentBoard(int boardNo) {
		return boardDao.selectParentBoardByBoardNo(boardNo);
	}
	//댓글 목록 조회
	public List<BoardDto> getReplyList(int boardNo) {
		List<BoardDto> replyList = boardDao.selectReplyListByBoardNo(boardNo);
		return replyList;
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
	@Transactional
	public void writeReply(BoardDto reply) {
		BoardDto parentDto = boardDao.selectParentBoard(reply.getPreBoard());
		boardDao.updateBoardStep(parentDto);
		boardDao.insertReply(reply);
	}
	//댓글 삭제
	public void removeComment(int replyNo) {
		boardDao.updateReplyEnabled(replyNo);
	}
	//댓글 수
	public void modifyReply(ReplyDto reply) {		
		boardDao.updateReply(reply);
	}
	
	
	//게시글 작성자 찾
	public BoardDto getBoardWriterByBoNo(int boardNo) {
		return boardDao.selectBoardWriterByBoNo(boardNo);
	}
	
	//전,후 게시판 찾기
	public HashMap<String, Object> getPrevNextBoard(int boardRef, SearchIndex searchIndex) {
		HashMap<String, Object> prevNext = boardDao.selectBoardPrevNext(boardRef, searchIndex);
		BoardDto prevBo = boardDao.selectBoardByBoardNo(Integer.parseInt(prevNext.get("prev_bo").toString()));
		BoardDto nextBo = boardDao.selectBoardByBoardNo(Integer.parseInt(prevNext.get("next_bo").toString()));
		prevNext.put("prevBo", prevBo);
		prevNext.put("nextBo", nextBo);
		return prevNext;
	}

}
