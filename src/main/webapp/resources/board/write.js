$(document).ready(() => {
	$('#write-form').submit((event) => {
		console.log('exec')
		let title = $('#input_title').val();
		let content = $('#input_content').val();
		
		let isSubmitPosb = true;
		let msgList = [];
		if(title === null || title.trim() === '') {
			msgList.push('제목');
			isSubmitPosb = false;
		}
		if(content === null || content.trim() === '') {
			msgList.push('내용');
			isSubmitPosb = false;
		}
		if(!isSubmitPosb) {
			alert(msgList.join(", ")  + '은 비거나 공백이 있어서는 안됩니다.');	
			event.preventDefault();
		} 
	});
	
})