$(document).ready(() => {
	$('#comment-form').submit((event) => {
		const content = $('#comment-input').val();
		if(content === null || content === '') {
			event.preventDefault();
			alert('공백으로 이루어진 데이터는 사용할 수 없습니다.');
		}
	})
});

//수정 모드로 변환
const viewReplyModify = (replyNo, replyWriter, boardNo) => {
	console.log('실행')
	const modifyDOM = '<div class="d-flex flex-column"><div class="d-flex"><h5>' + replyWriter + '</h5></div><div class="row"><div class="col-lg-10"><textarea id="content-' + replyNo + '" class="form-control form-control-lg" rows="3"></textarea><input id="file-' + replyNo + '" type="file" class="form-control" accept="images/*"/></div><div class="col-lg-2 d-flex justify-content-end align-items-center"><button class="btn btn-md btn-primary"	onclick="updateReply('+ replyNo +', \'' +replyWriter+ '\', ' + boardNo + ')">수정</button></div></div></div>';
	$('#reply-'+replyNo).html(modifyDOM);
}
//수정하기 서버 통신
const updateReply = (replyNo, replyWriter, boardNo) => {
	let replyContent = $('#content-'+replyNo).val();
	console.log('request')
	const rqstData = {}
	rqstData.replyNo = replyNo;
	rqstData.replyWriter = replyWriter;
	rqstData.replyContent = replyContent;
	rqstData.replyBoard = boardNo;
	
	if(replyContent === null || replyContent.trim() === "") {
		alert('공백을 입력할 수 없습니다!');
	} else {
		$.ajax({
				type:'post',
				contentType:'application/json',
				url:'/board/update_comment',
				data: JSON.stringify(rqstData),
				success: function(data) {
					if(data === 'blank data') {
						alert(data);
					} else {
						location.href=data;
					}
				},
				error: function(e) {
					alert('문제가 발생하였습니다.');
				}
		})
	}
}
//게시글 삭제 버튼
const deleteBoard = (boardNo, anonId) => {
	if(anonId !== null && anonId !== '') {
		$('#pw-modal').modal('show');
	} else {
		location.href = '/board/delete?boardNo='+boardNo;
	}
}
//익명 게시글 삭제
const deleteAnonBoard = (boardNo) => {
	let password = $('#pw-input').val();
	location.href = '/board/delete?boardNo='+boardNo+'&anonPw='+password;
}