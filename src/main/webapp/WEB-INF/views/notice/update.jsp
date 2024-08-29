<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
   	  <script src="/resources/board/write.js"></script>
   </head>
   <body>
        <%@ include file="/WEB-INF/views/common/header.jsp"%>
        <section class="container-fluid mt-5 pt-5">
        	<form id="write-form" class="w-100 d-flex justify-content-center" action="/notice/admin/update" method="POST" enctype="multipart/form-data">
        		<div class="w-50">
        			<h1>게시물 수정</h1>
        			<hr/>
	        		<label>제목</label>
	        		<input id="input_title" class="form-control form-control-lg" placeholder="제목을 작성해주세요." name="boardTitle" value="${board.boardTitle}">
        			<label>내용</label>
        			<textarea id="input_content" class="form-control form-control-lg" placeholder="내용을 작성해주세요." name="boardContent" rows=15>${board.boardContent}</textarea>
        			<input type="file" class="form-control mt-5" accept="image/*" name="battach">
        			<input type="hidden" value="${board.boardNo}" name="boardNo">
        			<div class="d-flex justify-content-end pt-5">
        				<button type="submit" class="btn btn-lg btn-primary">저장</button>
        			</div>
        		</div>
        	</form>
        </section>
        <hr/>
    </body>
</html>