<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
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
	String[] columns = {"관리번호", "자치구", "와이파이명", "도로명주소", "상세주소", 
			"설치위치(층)", "설치유형", "설치기관", "서비스구분", "망종류", "설치년도",
			 "실내외구분", "WIFI접속환경", "X좌표", "Y좌표", "작업일자"};
%>
<%
	String mgrNo = request.getParameter("mgrNo");
%>
<div>
	<h1> 와이파이 정보 구하기 </h1>
	
	<a href="/">홈</a> | 
	<a href="history.jsp">위치 히스토리 목록</a> | 
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | 
	<a href="">즐겨 찾기 보기</a> | 
	<a href="bookmark-group.jsp">즐겨 찾기 그룹 관리</a>
	<br>
</div>
<div>
	<form action="bookmark-add-submit.jsp" method="post">
		<select name="select">
			<option>북마크 그룹 이름 선택</option>
		<%
			sql.open();
			List<Map<String, String>> bookmarkList = sql.selectBookmark();
			sql.close();
			
			if(bookmarkList != null){
				for(Map<String, String> bookmarkMap : bookmarkList){
					out.print("<option value=\""+bookmarkMap.get("ID")+", "+bookmarkMap.get("북마크 이름")+"\">");
					out.print(bookmarkMap.get("북마크 이름"));
					out.println("</option>");
				}
			}
		%>
	    </select>
	    <input type="submit" value="즐겨찾기 추가하기"/>

		<table>
			<%
			if(mgrNo != null){
				sql.open();
				Map<String, String> dataMap = sql.selectWifi(mgrNo);
				sql.close();
				
				out.println("<input type=\"hidden\" name=\"wifi-id\" value=" + dataMap.get("관리번호")+">");
				out.println("<input type=\"hidden\" name=\"wifi-name\" value=" + dataMap.get("와이파이명")+">");

				if(dataMap != null){
					for(String column : columns){
						out.println("<tr height=\"45\">");
							out.println("	<th width=\"400\" scope=\"row\">" + column + "</th>");
							out.println("	<td>" + dataMap.get(column) + "</td>");
						out.println("</tr>");
					}
				}
			}
			%>
		
		</table>

	</form>
</div>
</body>
</html>