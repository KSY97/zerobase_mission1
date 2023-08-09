<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<jsp:include page="menu.jspf" />
<html>
<head>
<meta charset="UTF-8">
<link href="style.css" rel="stylesheet" type="text/css">
<title>와이파이 정보 구하기</title>
</head>
<body>

<form action="bookmark-group-add-submit.jsp" method="post">
	<table>
		<tr height="45">
			<th width="400" scope="row">북마크 이름</th>
			<td><input type='text' name="name"></td>
		</tr>
		<tr height="45">
			<th width="400" scope="row">순서</th>
			<td><input type='text' name="order"></td>
		</tr>
		<tr height="45">
			<td align=center colspan="2"><input type="submit" value="추가" /></td>
		</tr>
	</table>
</form>
</body>
</html>