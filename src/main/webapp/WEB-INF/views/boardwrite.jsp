<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>게시물 작성</title>
      <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
      <link	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"></link>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   </head>
   <body>
        <%@ include file="/WEB-INF/views/common/header.jsp"%>
        <section class="container-fluid mt-5 pt-5">
        	<form class="w-100 d-flex justify-content-center">
        		<div class="w-50">
        			<h1>게시물 작성</h1>
        			<hr/>
	        		<label>제목</label>
	        		<input id="input_title" class="form-control form-control-lg" placeholder="제목을 작성해주세요." name="boardTitle">
        			
        			<label>내용</label>
        			<textarea id="input_content" class="form-control form-control-lg" placeholder="내용을 작성해주세요." name="boardContent" rows=15></textarea>
        			<input type="file" class="form-control mt-5" accept="image/*">
        			<div class="d-flex justify-content-end pt-5">
        				<button type="submit" class="btn btn-lg btn-primary">저장</button>
        			</div>
        		</div>
        	</form>
        </section>
    </body>
</html>