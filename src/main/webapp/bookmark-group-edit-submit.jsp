<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Wifi.Sql" %>
<html>
<head>
<meta charset="UTF-8">
<link href="style.css" rel="stylesheet" type="text/css">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%!
	Sql sql = new Sql();
%>
<%
	request.setCharacterEncoding("utf-8");
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	String formattedDateTime = now.format(formatter);

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String order = request.getParameter("order");
	
	String dataStr = "NAME = \""+name+"\", \"ORDER\" = \""+order+"\", LAST_REVISION_DATE_TIME = \""+formattedDateTime+"\" ";
	
	sql.open();
	boolean update = sql.updateBookmark(id, dataStr);
	sql.close();
	
	if(update){
%>
	<script type="text/javascript">
		alert("북마크 그룹 정보를 수정하였습니다.");
		location.href="/bookmark-group.jsp";
	</script>
<%
	} else {
%>
  	<script type="text/javascript">
		alert("북마크 그룹 정보를 수정할 수 없습니다.");
		location.href="/bookmark-group.jsp";
	</script>
<%
	}
	
	
%>

</body>
</html>