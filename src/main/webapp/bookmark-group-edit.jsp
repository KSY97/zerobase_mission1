<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<%@ page import="Wifi.Sql" %>
<jsp:include page="menu.jspf" />
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
	String id = request.getParameter("id");
%>	

<% 
sql.open();
Map<String, String> dataMap = sql.selectBookmark(id);
sql.close();
%>

<form action="bookmark-group-edit-submit.jsp" method="post">
	<input type="hidden" name="id" value=<%=id%>>
	<table>
		<tr height="45">
			<th width="400" scope="row">북마크 이름</th>
			<td><input type='text' name="name" value="<%
			if(dataMap == null){
				out.print("");
			} else {
				out.print(dataMap.get("북마크 이름"));
			}
			%>"></td>
		</tr>
		<tr height="45">
			<th width="400" scope="row">순서</th>
			<td><input type='text' name="order" value="<%
			if(dataMap == null){
				out.print("");
			} else {
				out.print(dataMap.get("순서"));
			}
			%>"></td>
		</tr>
		<tr height="45">
			<td align=center colspan="2"><a href="bookmark-group.jsp">돌아가기</a> | <input type="submit" value="수정" /></td>
		</tr>
	</table>
</form>
</body>
</html>