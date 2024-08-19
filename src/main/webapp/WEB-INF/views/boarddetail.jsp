<%@ page contentType="text/html; charset=UTF-8"%>
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
        			<h1>제목:</h1>
        			<hr/>
        			<span>작성일 : 2024.05.06</span>
        			<label>내용</label>
        			<textarea id="input_content" class="form-control form-control-lg" name="boardContent" rows=15 readonly></textarea>
        			<div class="d-flex justify-content-end pt-5">
        				<a class="btn btn-lg btn-primary me-3" href="#">수정</a>
        				<a class="btn btn-lg btn-danger" href="#">삭제</a>
        			</div>
        		</div>
        	</div>
        </section>
        <section class="container-fluid bg-secondary mt-5 py-5">
        	<div id="comment_container" class="w-100 d-flex justify-content-center">
        		<div class="w-50 row d-flex justify-content-center">
        			<form>
        				<textarea class="form-control form-control-sm" rows=5></textarea>
        				<div class="d-flex justify-content-end">
        					<button type="submit" class="btn btn-lg btn-warning mt-3">답변</button>
        				</div>
        			</form>
        			<div class="w-100 bg-white rounded py-5 mt-5">
        				<!-- 여기서 부터 반복 -->
        				<div class="d-flex flex-column p-5 border-bottom">
        					<div class="d-flex justify-content-between">
        						<div>
        							<h5>tlarlrma</h5>
        							<p>아 그거 그렇게 하는거 아닌데</p>
        						</div>
        						<div>
        							<p>2024.06.08</p>
        							<button class="btn btn-sm btn-primary">수정</button>
        							<button class="btn btn-sm btn-danger">삭제</button>
        						</div>
        					</div>
        				</div>
        				
        			</div>
        		</div>
        	</div>
        </section>
    </body>
</html>