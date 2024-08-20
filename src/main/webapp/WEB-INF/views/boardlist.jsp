<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>게시물 목록</title>
      <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
      <link	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"></link>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   </head>
   <body>
        <%@ include file="/WEB-INF/views/common/header.jsp"%>
        <section class="container-fluid my-5">
        	<form class="w-100 d-flex justify-content-center" action="/board/list" method="GET">
        		<div id="search_container" class="w-50 d-flex justify-content-end">
        			<select class="form-select form-select-sm" name="searchCtg">
        				<option value="title" ${searchCtg == "title" ? "selected" : ""}>제목</option>
        				<option value="content" ${searchCtg == "content" ? "selected" : ""}>내용</option>
        				<option value="writer" ${searchCtg == "writer" ? "selected" : ""}>작성자</option>
        			</select>
        			<input class="form-control form-control-sm" type="text" name="keyword" value="${keyword}">
        			<button type="submit" class="btn btn-sm btn-primary">Search</button>
        		</div>
        	</form>
        </section>
        <section>
        	<div id="list_container" class="container-fluid d-flex justify-content-center">
        		<table class="table w-50">
        			<thead>
        				<tr>
        					<th>글 번호</th>
        					<th>제목</th>
        					<th>작성자</th>
        					<th>작성일</th>
        				</tr>
        			</thead>
        			<tbody>
        				<c:forEach var="board" items="${boardList}">
        					<tr>
	        					<th>${board.boardNo}</th>
	        					<td><a href="/board/detail?boardNo=${board.boardNo}">${board.boardTitle}</a></td>
	        					<td>${board.boardWriter}</td>
	        					<td><fmt:formatDate value="${board.boardDate}" type="date"/></td>
        					</tr>
        				</c:forEach>
        			</tbody>
        		</table>
        	</div>
        </section>
        <section>
        	<div id="write_button_container" class="container-fluid d-flex justify-content-center">
        		<div class="w-50 d-flex justify-content-end" style="min-height: 30px;">
        			<sec:authorize access="isAuthenticated()">
        				<a class="btn btn-sm btn-primary" href="/board/write">글작성하기</a>
        			</sec:authorize>
        		</div>
        	</div>
        </section>
        <section>
        	<div id="pagination_container" class="container-fluid d-flex justify-content-center">
        		<nav>
				  <ul class="pagination">
				    <li class="page-item ${pager.groupNo == 1 ? 'disabled' : ''}"><a class="page-link" href="#">Previous</a></li>
				    <c:forEach var="pageNum" items="${pager.pageArray}">
				    	<li class="page-item ${pager.pageNo == pageNum ? 'active' : ''}"><a class="page-link" href="/board/list?pageNo=${pageNum}">${pageNum}</a></li>
				    </c:forEach>
				    <li class="page-item ${pager.groupNo == pager.totalGroupNo ? 'disabled' : ''}"><a class="page-link" href="#">Next</a></li>
				  </ul>
				</nav>
        	</div>
        </section>
    </body>
</html>