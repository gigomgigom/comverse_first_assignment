<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<header class="bg-secondary py-3 ps-3">
	<div id="header_wrapper" class="d-flex justify-content-between">
		<a href="/board/list">
			<img src="https://comverse.co.kr/assets/img/logo.png"/>
		</a>
		<div class="d-flex align-items-center me-5">
			<sec:authorize access="isAnonymous()">
			<a class="btn btn-sm btn-primary" href="/auth/login">로그인</a>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<span class="text-white fw-bold">
					<sec:authentication property="principal.username"/>
					님 반갑습니다!
					<a class="btn btn-sm btn-danger ms-5" href="/logout">로그아웃</a>
				</span>
			</sec:authorize>
		</div>
	</div>
</header>