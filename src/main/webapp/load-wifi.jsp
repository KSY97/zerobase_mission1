<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="Test.APITest" %>
<html>
<head>
<meta charset="UTF-8">
<link href="style.css" rel="stylesheet" type="text/css">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%
	APITest api = new APITest();
	
	int totalCnt = api.getAllWifiInfo();
	
%>

<div align=center>
	<b><%=totalCnt%>개의 WIFI 정보를 정상적으로 저장하였습니다.</b><br>
	<a href="/">홈 으로 가기</a>
</div>


</body>
</html>