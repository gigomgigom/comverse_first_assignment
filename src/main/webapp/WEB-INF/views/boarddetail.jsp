<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>게시물 상세</title>
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"></link>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/resources/board/detail.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<c:if test="${board.boardDepth != 0}">
		<section
			class="container-fluid mt-5 pt-5 d-flex justify-content-center">
			<div id="pre_board_container"
				class="bg-secondary text-white w-50 p-2">
				<a href="/board/detail?boardNo=${parentBoard.boardNo}"
					class="btn text-white fs-1 mb-3">상위 포스트 게시글</a>
				<hr>
				<c:if test="${parentBoard != null}">
					<c:if test="${parentBoard.boardWriter != null}">
						<p>작성자 : ${parentBoard.boardWriter}</p>
					</c:if>
					<c:if test="${parentBoard.boardWriter == null}">
						<p>작성자 : ${parentBoard.anonId}</p>
					</c:if>
					<p>제 목 : ${parentBoard.boardTitle}</p>
					<p>내 용 : ${parentBoard.boardContent}</p>
				</c:if>
				<c:if test="${parentBoard == null}">
					<p>삭제된 글입니다.</p>
				</c:if>
			</div>
		</section>
	</c:if>
	<section class="container-fluid mt-5 pt-5">
		<div id="content_container"
			class="w-100 d-flex justify-content-center">
			<div class="w-50">
				<h1>제목: ${board.boardTitle}</h1>
				<hr>
				<p>
					작성일 :
					<fmt:formatDate value="${board.boardDate}" type="date" />
				</p>
				<c:if test="${board.boardWriter != null}">
					<p>작성자 : ${board.boardWriter}</p>
				</c:if>
				<c:if test="${board.boardWriter == null}">
					<p>작성자 : ${board.anonId}</p>
				</c:if>
				<p>조회수 : ${board.hitCnt}</p>
				<div class="row mt-5">
					<div class="col-md-12 col-lg-6">
						<label for="input_content">내용</label>
						<textarea id="input_content" class="form-control form-control-lg" name="boardContent" rows=15 readonly>${board.boardContent}</textarea>
					</div>
					<div
						class="col-md-12 col-lg-6 d-flex justify-content-center align-items-center">
						<c:if test="${board.imgName != null }">
							<img src="/board/download_img?boardNo=${board.boardNo}"
								width="100%">
						</c:if>
						<c:if test="${board.imgName == null}">
							<h4>첨부된 이미지파일이 없습니다.</h4>
						</c:if>
					</div>
				</div>
				<c:if test='${board.boardWriter == id || board.boardWriter == null}'>
					<div class="d-flex justify-content-end pt-5">
						<a class="btn btn-lg btn-primary me-3" href="/board/modify?boardNo=${board.boardNo}">수정</a>							
						<button class="btn btn-lg btn-danger" onClick="deleteBoard(${board.boardNo}, '${board.anonId}')">삭제</button>
					</div>
				</c:if>
			</div>
		</div>
	</section>
	<c:if test="${false}">
		<section
			class="container-fluid mt-5 pt-5 d-flex justify-content-center">
			<div id="move_post_container"
				class="w-50 d-flex justify-content-between">
				<div>
					<c:if test="${prev != null}">
						<a class="btn btn-lg btn-primary" href="/board/detail?boardNo=${prev.boardNo}"> 이전 포스트<br>${prev.boardTitle}
						</a>
					</c:if>
				</div>
				<div>
					<c:if test="${next != null}">
						<a class="btn btn-lg btn-primary" href="/board/detail?boardNo=${next.boardNo}"> 다음 포스트<br>${next.boardTitle}
						</a>
					</c:if>
				</div>
			</div>
		</section>
	</c:if>
	<section class="container-fluid bg-secondary mt-5 py-5">
		<div id="comment_container"
			class="w-100 d-flex justify-content-center">
			<div class="w-50 row d-flex justify-content-center">
				<div class="text-white">
					<h1>댓글 작성</h1>
				</div>
				<form id="comment-form" action="/board/write_comment" method="POST"
					enctype="multipart/form-data">
					<textarea id="comment-input" class="form-control form-control-lg"
						rows=5 name="boardContent"></textarea>
					<input type="file" class="form-control mt-2 mb-3" accept="image/*" name="battach" />
					<input type="hidden" value="${board.boardNo}" name="preBoard">
					<input type="hidden" value="${'RE: '}${board.boardTitle}" name="boardTitle">
					<c:if test="${id == 'anonymous'}">
						<label for="anon_id" class="text-white">이름</label>
						<input id="anon_id" name="anonId" type="text" class="w-25 form-control form-control-md" />
						<label for="anon_pw" class="text-white">비밀번호</label>
						<input id="anon_pw" name="anonPw" type="password" class="w-25 form-control form-control-md" />
					</c:if>
					<div class="d-flex justify-content-end">
						<button type="submit" class="btn btn-lg btn-warning mt-3">저장</button>
					</div>
				</form>
				<div class="w-100 bg-white rounded py-5 mt-5">
					<div class="ps-5 border-bottom">
						<h1>댓글 목록</h1>
					</div>
					<c:if test='${replyList==null or fn:length(replyList)==0}'>
						<div class="text-black ps-5">
							<h4>이 게시물에 댓글이 존재하지 않습니다.</h4>
						</div>
					</c:if>
					<c:forEach var="reply" items="${replyList}">
						<div id="reply-${reply.boardNo}" class="p-5 border-bottom">
							<div class="d-flex justify-content-between row" style="padding-left: ${reply.boardDepth==board.boardDepth+1?0:(reply.boardDepth-board.boardDepth)*20}px">
								<c:if test="${reply.boardEnabled}">
									<div class="col-lg-10">
										<a class="btn btn-lg ps-0" href="/board/detail?boardNo=${reply.boardNo}">${reply.boardDepth==board.boardDepth+1?'':'ㄴ'}제목 : ${reply.boardTitle}</a>
										<c:if test="${reply.boardWriter == null}">
											<p>작성자 : ${reply.anonId}</p>
										</c:if>
										<c:if test="${reply.boardWriter != null}">
											<p>작성자 : ${reply.boardWriter}</p>
										</c:if>
									</div>
									<div class="col-lg-2">
										<p>
											<fmt:formatDate value="${reply.boardDate}" type="date"></fmt:formatDate>
										</p>
										<c:if
											test='${false}'>
											<button class="btn btn-sm btn-primary"
												onclick="viewReplyModify(${reply.boardNo},  '${reply.boardWriter}', ${board.boardNo})">수정</button>
											<a class="btn btn-sm btn-danger"
												href="/board/remove_comment?replyNo=${reply.boardNo}&replyWriter=${reply.boardWriter}&replyBoard=${board.boardNo}">삭제</a>
										</c:if>
									</div>
								</c:if>
								<c:if test="${!reply.boardEnabled}">
									<div class="d-flex justify-content-center align-items-center">
										<h5>삭제된 댓글입니다.</h5>
									</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
	<div id="pw-modal" class="modal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">비밀번호 확인</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		      	<label for="password-input">댓글을 작성하셨을때 기입하셨던 비밀번호를 입력해주세요.</label>
		        <input type="password" id="pw-input" class="form-control">
		      </div>
		      <div class="modal-footer">
		     	<button type="button" class="btn btn-primary" onClick="deleteAnonBoard(${board.boardNo})">확인</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		      </div>
		    </div>
		</div>
	</div>
</body>
</html>