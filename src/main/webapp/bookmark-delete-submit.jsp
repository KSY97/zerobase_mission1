<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="Test.SQLTest" %>
<html>
<head>
<meta charset="UTF-8">
<link href="style.css" rel="stylesheet" type="text/css">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%!
	SQLTest sql = new SQLTest();
%>
<%
	
	String id = request.getParameter("id");
	
	sql.open();
	boolean delete = sql.deleteBookmarkWifi(id);
	sql.close();
	
	if(delete){
%>
	<script type="text/javascript">
		alert("북마크 정보를 삭제하였습니다.");
		location.href="/bookmark-list.jsp";
	</script>
<%
	} else {
%>
	<script type="text/javascript">
		alert("북마크 정보를 삭제할 수 없습니다.");
		location.href="/bookmark-list.jsp";
	</script>
<%
	}
	
	
%>

</body>
</html>