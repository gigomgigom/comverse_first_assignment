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
   </head>
   <body>
        <section class="container-fluid">
   			<div class="d-flex flex-column justify-content-center align-items-center" style="width: 90vw; min-height: 90vh">
   				<div class="bg-secondary p-3 mb-5 rounded">
   					<img src="https://comverse.co.kr/assets/img/logo.png" alt="comverse_logo">
   				</div>
   				<h1 class="my-5">회원가입</h1>
   				<form action="/auth/signup" method="POST">
   					<div class="d-flex flex-column">
   						<label for="input_id">아이디</label>
   						<input id="input_id" class="form-control mb-3" type="text" name="memberId">
   						<label for="input_pw">비밀번호</label>
						<input id="input_pw" class="form-control mb-3" type="password" name="memberPw">
						<label for="input_email">E-mail</label>
						<input id="input_email" class="form-control mb-5" type="email" name="memberEmail">
						<button type="submit" class="btn btn-lg btn-primary mb-2">회원가입</button>
   					</div>
				</form>
   			</div>
   		</section>
    </body>
</html>