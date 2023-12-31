<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util. *" %>
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
	String[] columns = {"ID", "X좌표", "Y좌표", "조회일자", "비고"};
%>
<%
	String id = request.getParameter("id");
	if(id != null){
		sql.open();
		sql.deleteHistory(id);
		sql.close();
	}
%>

<table>
  <tr height="50">
  <%
	for(String column : columns){
  		out.println("<th>" + column + "</th>");
  	}
  %>
  </tr>
  <%
  	sql.open();
  	List<Map<String, String>> dataList = sql.selectHistory();
  	sql.close();
  	
  	if(dataList != null){
  		int histroyId = dataList.size();
  		if(histroyId != 0){
			for(Map<String, String> dataMap : dataList){
				out.println("<tr>");
	 	  		for(String column : columns){
	 	  			if("ID".equals(column))
	 	  				out.println("<td>" + histroyId + "</td>");
	 	  			else if("비고".equals(column)){
	 	  				out.println("<td align=center>");
	 	  				out.print("	<button type=\"submit\" onclick=\"deleteHistory(");
	 	  				out.print(dataMap.get("ID"));
	 	  				out.print(")\">삭제</button>");
	 	  				out.println("</td>");
	 	  			}
	 	  				
	 	  			else
	 	    			out.println("<td>" + dataMap.get(column) + "</td>");
	 	    	}
	  	  		out.println("</tr>");
	  	  		histroyId--;
			}
  		} else {
  			out.println("<tr height=\"100\">");
  	  		out.println("	<td align=center colspan=\"17\"><b>히스토리 정보가 없습니다.</b></td>");
  	  		out.println("</tr>");
  		}
	} else {
		out.println("<tr height=\"100\">");
  		out.println("	<td align=center colspan=\"17\"><b>DB정보를 불러오는데 실패했습니다.</b></td>");
  		out.println("</tr>");
	}
  %>
</table>
<script type="text/javascript">
	function deleteHistory(id){
		location.href="/history.jsp?id="+id;
	}	
</script>
</body>
</html>