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
	String[] columns = {"ID", "북마크 이름", "와이파이명", "등록일자", "비고"};
%>

<table>
  	<tr height="50">
	<%
		for(String column : columns){
	  		out.println("<th scope=\"col\">" + column + "</th>");
	  	}
	%>
  	</tr>
  	<%
  		sql.open();
  		List<Map<String, String>> dataList = sql.selectBookmarkWifi();
  		sql.close();
  		
  		if(dataList != null){
  			if(dataList.size() != 0){
  				for(Map<String, String> dataMap : dataList){
  	  				out.println("<tr>");
  	  				for(String column : columns){
  	  					if("비고".equals(column)){
  	  						out.println("<td align=center><a href=\"bookmark-delete.jsp?id="+dataMap.get("ID")+"\">삭제</a></td>");
  	  					} else {
  	  						out.println("<td>"+dataMap.get(column)+"</td>");
  	  					}
  	  				}
  	  				out.println("</tr>");
  	  			}
  			} else {
  		  		out.println("<tr height=\"100\">");
  		  		out.println("	<td align=center colspan=\"5\"><b>정보가 존재하지 않습니다.</b></td>");
  		  		out.println("</tr>");
  			}  
  		} else {
  			out.println("<tr height=\"100\">");
  			out.println("	<td align=center colspan=\"5\"><b>정보가 존재하지 않습니다.</b></td>");
  			out.println("</tr>");
  		}
  	%>

</table>
</body>
</html>