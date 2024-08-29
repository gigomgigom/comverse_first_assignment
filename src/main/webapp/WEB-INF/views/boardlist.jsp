<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>자유게시판</title>
      <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
      <link	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"></link>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   </head>
   <body>
        <%@ include file="/WEB-INF/views/common/header.jsp"%>
        <section class="container-fluid mb-5">
        	<div class="w-100 d-flex flex-column align-items-center">
        		<div class="w-100 d-flex justify-content-center my-5">
        			<a class="btn btn-lg btn-secondary w-25 me-2 disabled">
        				자유게시판
        			</a>
        			<a class="btn btn-lg btn-primary w-25" href="/notice/list">
        				공지사항
        			</a>        			
        		</div>
        		<h1>자유게시판</h1>
        	</div>
        </section>
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
        <c:if test="${pager.totalRows < 1}">
			<div class="container-fluid d-flex justify-content-center" style="height: 50vh;">
				<h3>검색결과가 없습니다.</h3>
			</div>        
        </c:if>
        <c:if test="${pager.totalRows > 0}">
        <section style="min-height: 40vh">
        	<div id="list_container" class="container-fluid d-flex justify-content-center">
        		<table class="table table-bordered w-50">
        			<thead class="table-primary">
        				<tr class="row">
        					<th class="col-sm-1 text-center">글 번호</th>
        					<th class="col-sm-5 text-center">제목</th>
        					<th class="col-sm-2 text-center">작성자</th>
        					<th class="col-sm-2 text-center">작성일</th>
        					<th class="col-sm-2 text-center">조회수</th>
        				</tr>
        			</thead>
        			<tbody>
        				<c:forEach var="board" items="${boardList}">
        					<tr class="row ${board.boardDepth == 0 ? 'table-info' : ''}">
	        					<th class="col-sm-1 text-center border-right">${board.boardNo}</th>
	        					<td class="col-sm-5 border-right" style="padding-left: ${board.boardDepth == 0 ? 5 : board.boardDepth * 20}px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap"><a href="/board/detail?boardNo=${board.boardNo}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}&pageNo=${searchIndex.pageNo}">${board.boardDepth==0?'':'ㄴ'}${board.boardEnabled ? board.boardTitle : '삭제 된 게시글입니다.'}</a></td>
	        					<td class="col-sm-2 text-center border-right">${board.boardWriter != null ? board.boardWriter : board.anonId}</td>
	        					<td class="col-sm-2 text-center border-right"><fmt:formatDate value="${board.boardDate}" type="date"/></td>
	        					<td class="col-sm-2 text-center">${board.hitCnt}</td>
        					</tr>
        				</c:forEach>
        			</tbody>
        		</table>
        	</div>
        </section>
        </c:if>
        <section>
        	<div id="write_button_container" class="container-fluid d-flex justify-content-center">
        		<div class="w-50 d-flex justify-content-end" style="min-height: 30px;">
        			<sec:authorize access="isAuthenticated()">
        				<a class="btn btn-sm btn-primary" href="/board/write">글작성하기</a>
        			</sec:authorize>
        		</div>
        	</div>
        </section>
        <c:if test="${pager.totalRows > 0}">
        <section>
        	<div id="pagination_container" class="container-fluid d-flex justify-content-center">
        		<nav>
				  <ul class="pagination">
				    <li class="page-item ${pager.groupNo == 1 ? 'disabled' : ''}"><a class="page-link" href="/board/list?pageNo=${pager.startPageNo - 1}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}">Previous</a></li>
				    <c:forEach var="pageNum" items="${pager.pageArray}">
				    	<li class="page-item ${pager.pageNo == pageNum ? 'active' : ''}"><a class="page-link" href="/board/list?pageNo=${pageNum}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}">${pageNum}</a></li>
				    </c:forEach>
				    <li class="page-item ${pager.groupNo == pager.totalGroupNo ? 'disabled' : ''}"><a class="page-link" href="/board/list?pageNo=${pager.endPageNo + 1}&searchCtg=${searchIndex.searchCtg}&keyword=${searchIndex.keyword}">Next</a></li>
				  </ul>
				</nav>
        	</div>
        </section>
        </c:if>
    </body>
</html>