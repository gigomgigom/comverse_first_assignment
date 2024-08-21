<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>SignUp</title>
      <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
      <link	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"></link>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   	  <script src="/resources/auth/signup.js"></script>
   </head>
   <body>
        <section class="container-fluid">
   			<div class="d-flex flex-column justify-content-center align-items-center" style="width: 90vw; min-height: 90vh">
   				<div class="bg-secondary p-3 mb-5 rounded">
   					<img src="https://comverse.co.kr/assets/img/logo.png" alt="comverse_logo">
   				</div>
   				<h1 class="my-5">회원가입</h1>
   				<form id="write-form" action="/auth/signup" method="POST">
   					<div class="d-flex flex-column">
   						<label for="input_id">아이디</label>
   						<input id="input_id" class="form-control" type="text" name="memberId" placeholder="영소문자 5~10">
   						<p class="mb-3">
   							<span id="id_error" class="text-danger mb-3 fs-6"></span>
   						</p>
   						<label for="input_pw">비밀번호</label>
						<input id="input_pw" class="form-control" type="password" name="memberPw" placeholder="영 소대문자 8~12">
						<p class="mb-3">
   							<span id="pw_error" class="text-danger mb-3 fs-6"></span>
   						</p>
						<label for="input_email">E-mail</label>
						<input id="input_email" class="form-control" type="email" name="memberEmail" novalidate>
						<p class="mb-5">
   							<span id="email_error" class="text-danger fs-6"></span>
   						</p>
						<button type="submit" class="btn btn-lg btn-primary mb-2">회원가입</button>
						<a href="/auth/login" class="btn btn-lg btn-primary mb-2">로그인하러가기</a>
   					</div>
				</form>
   			</div>
   		</section>
    </body>
</html>