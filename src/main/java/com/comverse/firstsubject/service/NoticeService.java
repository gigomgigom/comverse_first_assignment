package com.comverse.firstsubject.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.comverse.firstsubject.dao.NoticeDao;
import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.Pager;
import com.comverse.firstsubject.dto.SearchIndex;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;
	
	//공지사항 목록 조회
	public List<BoardDto> getNoticeList(SearchIndex searchIndex) {
		int totalRows = noticeDao.selectTotalRows(searchIndex);
		Pager pager = new Pager(10, 5, totalRows, Integer.parseInt(searchIndex.getPageNo()));
		searchIndex.setPager(pager);
		return noticeDao.selectNoticeList(searchIndex);
	}
	//공지사항 상세조회
	@Transactional
	public String getNoticeDetail(int boardNo, Model model) {
		BoardDto notice = noticeDao.selectNoticeByBno(boardNo);
		if(notice == null) {
			model.addAttribute("msg", "제목과 내용은 필수적인 내용입니다.");
			model.addAttribute("url", "/");
			return "alert";
		}
		noticeDao.updateNoticeHitCnt(notice.getBoardNo());
		HashMap<String, Object> result = noticeDao.selectNoticePreNext(notice.getBoardNo());
		BoardDto prevBoard = noticeDao.selectNoticeByBno(Integer.parseInt(result.get("prev_bo").toString()));
		BoardDto nextBoard = noticeDao.selectNoticeByBno(Integer.parseInt(result.get("next_bo").toString()));
		
		model.addAttribute("prevBo", prevBoard);
		model.addAttribute("nextBo", nextBoard);
		model.addAttribute("board", notice);
		return "notice/detail";
	}
	//공지사항 생성
	public void saveNoticeOnDataBase(BoardDto board, Authentication auth) {
		if(board.getBattach() != null && !board.getBattach().isEmpty()) {
			MultipartFile mf = board.getBattach();
			board.setImgName(mf.getOriginalFilename());
			board.setImgType(mf.getContentType());
			try {
				board.setImgData(mf.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		board.setBattach(null);
		board.setBoardWriter(auth.getName());
		board.setBoardCtg(2);
		board.setBoardEnabled(true);
		noticeDao.insertNotice(board);
	}
	//공지사항 페이지 이동 전 권한 확인
	public String checkBeforeEditForm(int boardNo, Authentication auth, Model model) {
		BoardDto boardOnDB = noticeDao.selectNoticeByBno(boardNo);
		if(boardOnDB.getBoardWriter().equals(auth.getName())) {
			model.addAttribute("board", boardOnDB);
			return "notice/update";
		} else {
			model.addAttribute("msg", "올바르지 않은 권한입니다.");
			model.addAttribute("url", "/notice/detail?boardNo="+boardNo);
			return "alert";
		}
	}
	//공지사항 수정
	public String modifyNotice(BoardDto board, Authentication auth, Model model) {
		BoardDto boardOnDB = noticeDao.selectNoticeByBno(board.getBoardNo());
		if(boardOnDB.getBoardWriter().equals(boardOnDB.getBoardWriter())) {
			if(board.getBattach() != null && !board.getBattach().isEmpty()) {
				MultipartFile mf = board.getBattach();
				board.setImgName(mf.getOriginalFilename());
				board.setImgType(mf.getContentType());
				try {
					board.setImgData(mf.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			board.setBattach(null);
			noticeDao.updateNotice(board);
			return "redirect:/notice/detail?boardNo="+board.getBoardNo();
		} else {
			model.addAttribute("msg", "올바르지 않은 권한입니다.");
			model.addAttribute("url", "/notice/detail?boardNo="+board.getBoardNo());
			return "alert";
		}
	}
	public String deleteNotice(int boardNo, Authentication auth, Model model) {
		BoardDto notice = noticeDao.selectNoticeByBno(boardNo);
		if(notice.getBoardWriter().equals(auth.getName())) {
			noticeDao.updateNoticeToDelete(boardNo);
			return "redirect:/notice/list";
		} else {
			model.addAttribute("msg", "올바르지 않은 권한입니다.");
			model.addAttribute("url", "/notice/detail?boardNo="+boardNo);
			return "alert";
		}
	}
}
