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
	String[] columns = {"ID", "북마크 이름", "순서", "등록일자", "수정일자", "비고"};
%>
<div>
    <button onclick="goAddPage()">북마크 그룹 이름 추가</button>
</div>

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
	List<Map<String, String>> dataList = sql.selectBookmark();
	sql.close();
	
	if(dataList != null){
		if(dataList.size() != 0){		
			for(Map<String, String> dataMap : dataList){
				out.println("<tr>");
				for(String column : columns){
					if(!"비고".equals(column)){
						out.println("<td>" + dataMap.get(column) + "</td>");
					} else {
						out.print("<td align=center><a href=\"bookmark-group-edit.jsp?id="+dataMap.get("ID")+"\">수정</a>");
						out.println(" <a href=\"bookmark-group-delete.jsp?id="+dataMap.get("ID")+"\">삭제</a></td>");
					}
					
				}
				out.println("</tr>");
			}
		} else {
			out.println("<tr height=\"100\">");
			out.println("	<td align=center colspan=\"6\"><b>정보가 존재하지 않습니다.</b></td>");
			out.println("</tr>");
		}
	} else {
		out.println("<tr height=\"100\">");
		out.println("	<td align=center colspan=\"6\"><b>정보가 존재하지 않습니다.</b></td>");
		out.println("</tr>");
	}
%>
</table>
<script type="text/javascript">
	function goAddPage(){
		console.log("!!");	
		location.href="/bookmark-group-add.jsp";
	}
</script>
</body>
</html>