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
        			<p>조회수 : ${board.hitCnt}</p>
        			<div class="row mt-5">
        				<div class="col-md-12 col-lg-6">
        					<label for="input_content">내용</label>
        					<textarea id="input_content" class="form-control form-control-lg" name="boardContent" rows=15 readonly>${board.boardContent}</textarea>
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
	        				<a class="btn btn-lg btn-primary me-3" href="/notice/admin/edit?boardNo=${board.boardNo}">수정</a>
	        				<a class="btn btn-lg btn-danger" href="/notice/admin/delete?boardNo=${board.boardNo}">삭제</a>
	        			</div>
        			</c:if>
        		</div>
        	</div>
        </section>
		<section
				class="container-fluid mt-5 pt-5 d-flex justify-content-center">
				<div id="move_post_container"
					class="w-50 d-flex justify-content-between">
					<div>
						<c:if test="${prevBo != null}">
							<a class="btn btn-lg btn-primary" href="/notice/detail?boardNo=${prevBo.boardNo}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}&pageNo=${searchIndex.pageNo}"> 이전 포스트<br>${prevBo.boardTitle}
							</a>
						</c:if>
					</div>
					<div>
						<a class="btn btn-lg btn-primary" href="/notice/list?searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}&pageNo=${searchIndex.pageNo}">목록</a>
					</div>
					<div>
						<c:if test="${nextBo != null}">
							<a class="btn btn-lg btn-primary" href="/notice/detail?boardNo=${nextBo.boardNo}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}&pageNo=${searchIndex.pageNo}"> 다음 포스트<br>${nextBo.boardTitle}
							</a>
						</c:if>
					</div>
				</div>
		</section>
    </body>
</html>