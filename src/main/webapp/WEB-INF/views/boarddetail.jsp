<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>게시물 상세</title>
      <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
      <link	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"></link>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   	  <script src="/resources/board/detail.js"></script>
	</head>
   <body>
        <%@ include file="/WEB-INF/views/common/header.jsp"%>
        <section class="container-fluid mt-5 pt-5">
        	<div id="content_container" class="w-100 d-flex justify-content-center">
        		<div class="w-50">
        			<h1>제목: ${board.boardTitle}</h1>
        			<hr/>
        			<p>작성일 : <fmt:formatDate value="${board.boardDate}" type="date"/></p>
        			<p>작성자 : ${board.boardWriter}</p>
        			<div class="row mt-5">
        				<div class="col-md-12 col-lg-6">
        					<label for="input_content">내용</label>
        					<textarea id="input_content" class="form-control form-control-lg" name="boardContent" rows=15 readonly>
        						${board.boardContent}
        					</textarea>
        				</div>
        				<div class="col-md-12 col-lg-6 d-flex justify-content-center align-items-center">
        					<c:if test="${board.imgName != null }">
        						<img src="/board/download_img?boardNo=${board.boardNo}" width="100%">
        					</c:if>
        					<c:if test="${board.imgName == null}">
        						<h4>첨부된 이미지파일이 없습니다.</h4>
        					</c:if>
        				</div>
        				
        			</div>
        			<c:if test='${board.boardWriter == id}'>
        				<div class="d-flex justify-content-end pt-5">
	        				<a class="btn btn-lg btn-primary me-3" href="/board/modify?boardNo=${board.boardNo}">수정</a>
	        				<a class="btn btn-lg btn-danger" href="/board/delete?boardNo=${board.boardNo}&boardWriter=${board.boardWriter}">삭제</a>
	        			</div>
        			</c:if>
        		</div>
        	</div>
        </section>
        <section class="container-fluid bg-secondary mt-5 py-5">
        	<div id="comment_container" class="w-100 d-flex justify-content-center">
        		<div class="w-50 row d-flex justify-content-center">
        			<c:if test='${id != "anonymous"}'>
        				<div class="text-white">
	        				<h1>댓글 작성</h1>
	        			</div>
	        			<form id="comment-form" action="/board/write_comment" method="POST">
	        				<textarea id="comment-input" class="form-control form-control-lg" rows=5 name="replyContent"></textarea>
	        				<input type="hidden" value="${board.boardNo}" name="replyBoard">
	        				<div class="d-flex justify-content-end">
	        					<button type="submit" class="btn btn-lg btn-warning mt-3">저장</button>
	        				</div>
	        			</form>
        			</c:if>
        			<div class="w-100 bg-white rounded py-5 mt-5">
        				<div class="ps-5">
        					<h1>댓글 목록</h1>
        				</div>
        				<c:if test='${replyList==null or fn:length(replyList)==0}'>
        					<div class="text-black ps-5">
        						<h4>이 게시물에 댓글이 존재하지 않습니다.</h4>
        					</div>
        				</c:if>
        				<c:forEach var="reply" items="${replyList}">
        					<div id="reply-${reply.replyNo}" class="p-5 border-bottom">
	        					<div class="d-flex justify-content-between row">
	        						<div class="col-lg-10">
	        							<h5>${reply.replyWriter}</h5>
	        							<p>${reply.replyContent}</p>
	        						</div>
	        						<div class="col-lg-2">
	        							<p><fmt:formatDate value="${reply.replyDate}" type="date"></fmt:formatDate></p>
	        							<c:if test='${reply.replyWriter == id}'>
	        								<button class="btn btn-sm btn-primary" onclick="viewReplyModify(${reply.replyNo},  '${reply.replyWriter}', ${board.boardNo})">수정</button>
	        								<a class="btn btn-sm btn-danger" href="/board/remove_comment?replyNo=${reply.replyNo}&replyWriter=${reply.replyWriter}&replyBoard=${board.boardNo}">삭제</a>
	        							</c:if>
	        						</div>
	        					</div>
        					</div>
        				</c:forEach>
        			</div>
        		</div>
        	</div>
        </section>
    </body>
</html>