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

	String[] select = request.getParameter("select").split(", ");
	String bookmarkID = select[0];
	String bookmarkName = select[1];
	
	String wifiID = request.getParameter("wifi-id");
	String wifiName = request.getParameter("wifi-name");
	
	String dataStr = "(\""+bookmarkID+"\", \""+bookmarkName+"\", \""+wifiID+"\", \""+wifiName+"\", \""+formattedDateTime+"\")";

	sql.open();
	boolean insert = sql.insertBookmarkWifi(dataStr);
	sql.close();
	
	if(insert){
%>
	<script type="text/javascript">
		alert("북마크 정보를 추가하였습니다.");
		location.href="/bookmark-list.jsp";
	</script>
<%
	} else {
%>
	<script type="text/javascript">
		alert("북마크 정보를 추가할 수 없습니다.");
		location.href="/bookmark-list.jsp";
	</script>
<%
	}
	
	
%>

</body>
</html>