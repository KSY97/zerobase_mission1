<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
	request.setCharacterEncoding("utf-8");
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	String formattedDateTime = now.format(formatter);

	String name = request.getParameter("name");
	String order = request.getParameter("order");
	
	String dataStr = "(\""+name+"\", \""+order+"\", \""+formattedDateTime+"\", \"\")";
	
	sql.open();
	boolean insert = sql.insertBookmark(dataStr);
	sql.close();
	
	if(insert){
		System.out.println(dataStr);
%>
	<script type="text/javascript">
		alert("북마크 그룹 정보를 추가하였습니다.");
		location.href="/bookmark-group.jsp";
	</script>
<%
	} else {
%>
	<script type="text/javascript">
		alert("북마크 그룹 정보를 추가할 수 없습니다.");
		location.href="/bookmark-group-add.jsp";
	</script>
<%
	}
	
	
%>

</body>
</html>