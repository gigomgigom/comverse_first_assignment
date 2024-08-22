$(document).ready(() => {
	
	$('#input_id').on('change', () => {
		console.log('change detected')
		let idError = $('#id_error');
		let idOk = $('#id_ok');
			
		let submitBtn = $('#submit-form');
					
		idError.html('');
		idOk.html('');
		submitBtn.addClass('disabled');
	});
	
	$('#id-check').click(() => {
			let id = $('#input_id').val();
			
			let idError = $('#id_error');
			let idOk = $('#id_ok');
			
			const idPattern = /^[a-z]{5,10}$/;
			
			if(id === '') {
				idError.html('아이디는 필수 값입니다.');
			} else if(!idPattern.test(id)) {
				idError.html('유효하지 않는 형식입니다.');
			} else {
				idError.html('');
				findIdExist(id);
			}
		}
	)
	
	
	$('#write-form').submit((event) => {
		
		event.preventDefault();
		let isSubmitPosb = true;
		
		let id = $('#input_id').val();
		let pw = $('#input_pw').val();
		let rewritePw = $('#rewrite_pw').val();
		let email = $('#input_email').val();
		
		let idError = $('#id_error');
		let pwError = $('#pw_error');
		let emailError = $('#email_error');
		
		const idPattern = /^[a-z]{5,10}$/;
		const pwPattern = /^[a-zA-Z]{8,12}$/;
		const emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
		//ID 유효성 검사
		if(id === "") {
			idError.html('아이디는 필수값입니다.');
			isSubmitPosb = false;
		} else if(!idPattern.test(id)) {
			idError.html('유효하지 않는 형식입니다.');
			isSubmitPosb = false;
		} else {
			idError.html("");
		}
		//PW 유효성 검사
		if(pw === "") {
			pwError.html('비밀번호는 필수값입니다.');
			isSubmitPosb = false;
		} else if(!pwPattern.test(pw)) {
			pwError.html('유효하지 않는 형식입니다.');
			isSubmitPosb = false;
		} else if(pw !== rewritePw) {
			pwError.html('재확인 비밀번호가 일치하지 않습니다.');
			isSubmitPosb = false;
		} else {
			pwError.html('');
		}
		//이메일 유효성 검사
		if(email === "") {
			emailError.html('이메일은 필수값입니다.');
			isSubmitPosb = false;
		} else if(!emailPattern.test(email)) {
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
//Id 존재여부 확인---------------------------------------
const findIdExist = (id) => {
	console.log('통신시작')
	$.ajax({
		type:'GET',
		url: '/auth/find_id_exist',
		data: {
			'memberId': id
		},
		success: function(data) {
			noticeResult(data);
		},
		error: function(e) {
			console.log(e);
			alert('문제가 발생하였습니다.');
		} 
	});
}

const noticeResult = (result) => {
	
	let idError = $('#id_error');
	let idOk = $('#id_ok');
	
	let submitBtn = $('#submit-form');

	if(result === 'true') {
		idError.html('이미 존재하고 있는 아이디입니다.');
		idOk.html('');
	} else {
		idError.html('');
		idOk.html('사용가능한 아이디입니다.');
		submitBtn.removeClass('disabled');
	}
}
//----------------------------------------------------