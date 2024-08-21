$(document).ready(() => {
	$('#write-form').submit((event) => {
		
		event.preventDefault();
		let isSubmitPosb = true;
		
		let id = $('#input_id').val();
		let pw = $('#input_pw').val();
		let email = $('#input_email').val();
		
		let idError = $('#id_error');
		let pwError = $('#pw_error');
		let emailError = $('#email_error');
		
		const idPattern = /^[a-z]{5,10}$/;
		const pwPattern = /^[a-zA-Z]{8,12}$/;
		const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/;
		
		if(id === "") {
			idError.html('아이디는 필수값입니다.');
			isSubmitPosb = false;
		} else if(!idPattern.test(id)) {
			idError.html('유효하지 않는 형식입니다.');
			isSubmitPosb = false;
		} else {
			idError.html("");
		}
		
		if(pw === "") {
			pwError.html('비밀번호는 필수값입니다.');
			isSubmitPosb = false;
		} else if(!pwPattern.test(pw)) {
			pwError.html('유효하지 않는 형식입니다.');
			isSubmitPosb = false;
		} else {
			pwError.html('');
		}
		
		if(email === "") {
			emailError.html('이메일은 필수값입니다.');
			isSubmitPosb = false;
		} else if(emailPattern.test(email)) {
			emailError.html('유효하지 않는 형식입니다.');
			isSubmitPosb = false;
		} else {
			emailError.html('');
		}
		
		if(isSubmitPosb) {
			event.target.submit();
		}
		
	});
	
})