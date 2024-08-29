<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alert</title>
<script>
	var msg = "<c:out value='${msg}'/>";
	var url = "<c:out value='${url}'/>";
	var searchCtg = "<c:out value='${searchCtg}'/>";
	var keyword = "<c:out value='${keyword}'/>";
	var pageNo = "<c:out value='${pageNo}'/>";
	alert(msg);
	location.href=url+'?searchCtg='+searchCtg+'&keyword='+keyword+'&pageNo='+pageNo;
</script>
</head>
<body>
	
</body>
</html>