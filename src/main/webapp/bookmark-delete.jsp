<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<%@ page import="Test.SQLTest" %>
<jsp:include page="menu.jspf" />
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
%>	

<% 
sql.open();
Map<String, String> dataMap = sql.selectBookmarkWifi(id);
sql.close();
%>

<form action="bookmark-delete-submit.jsp" method="post">
	<input type="hidden" name="id" value=<%=id%>>
	<table>
		<tr height="45">
			<th width="400" scope="row">북마크 이름</th>
			<td><%
			if(dataMap == null){
				out.print("");
			} else {
				out.print(dataMap.get("북마크 이름"));
			}
			%></td>
		</tr>
		<tr height="45">
			<th width="400" scope="row">와이파이명</th>
			<td><%
			if(dataMap == null){
				out.print("");
			} else {
				out.print(dataMap.get("와이파이명"));
			}
			%></td>
		</tr>
		<tr height="45">
			<th width="400" scope="row">등록일자</th>
			<td><%
			if(dataMap == null){
				out.print("");
			} else {
				out.print(dataMap.get("등록일자"));
			}
			%></td>
		</tr>
		<tr height="45">
			<td align=center colspan="2"><a href="bookmark-list.jsp">돌아가기</a> | <input type="submit" value="삭제" /></td>
		</tr>
	</table>
</form>
</body>
</html>