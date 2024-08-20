let originalHTML = '';
//수정 모드로 변환
const viewReplyModify = (replyNo, replyWriter, boardNo) => {
	console.log('실행')
	const modifyDOM = '<div class="d-flex flex-column"><div class="d-flex"><h5>tladudwh</h5></div><div class="row"><div class="col-lg-10"><textarea id="content-' + replyNo + '" class="form-control form-control-lg" rows="3"></textarea></div><div class="col-lg-2 d-flex justify-content-end align-items-center"><button class="btn btn-md btn-primary" onclick="updateReply('+ replyNo +', \'' +replyWriter+ '\', ' + boardNo + ')">수정</button></div></div></div>';
	$('#reply-'+replyNo).html(modifyDOM);
}

const updateReply = (replyNo, replyWriter, boardNo) => {
	let replyContent = $('#content-'+replyNo).val();
	console.log('request')
	const data = {}
	data.replyNo = replyNo;
	data.replyWriter = replyWriter;
	data.replyContent = replyContent;
	data.replyBoard = boardNo;
	$.ajax({
		type:'post',
		contentType:'application/json',
		url:'/board/update_comment',
		data: JSON.stringify(data),
		success: function(data) {
			location.href=data;
		},
		error: function(e) {
			alert('문제가 발생하였습니다.');
		}
	})
}