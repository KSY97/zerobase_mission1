<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util. *" %>
<!DOCTYPE html>
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
	String[] columns = {"거리(Km)", "관리번호", "자치구", "와이파이명", "도로명주소", "상세주소", 
			"설치위치(층)", "설치유형", "설치기관", "서비스구분", "망종류", "설치년도",
			 "실내외구분", "WIFI접속환경", "X좌표", "Y좌표", "작업일자"};
%>
<%
	String lat = request.getParameter("lat");
	String lnt = request.getParameter("lnt");
%>
<div>
	<h1> 와이파이 정보 구하기 </h1>
	
	<a href="/">홈</a> | <a>위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a><br>
	
	<%
		out.print("LAT: <input type='text' id='lat' value=");
		if(lat != null)
			out.print(lat+">");
		else
			out.print("'0.0'>");
		
		out.print(" , LNT: <input type='text' id='lnt' value=");
		if(lat != null)
			out.print(lnt+">");
		else
			out.print("'0.0'>");
	%>
	
	<input type="button" value="내 위치 가져오기" >
	<button type="submit" onclick="test(lat.value, lnt.value)">근처 WIFI 정보 보기</button>
</div>



	
<table>
  <tr height="50">
<%
	for(String column : columns){
  		out.println("<th>" + column + "</th>");
  	}
%>
  </tr>
  <%
  	if(lat==null && lnt==null){
  		out.println("<tr height=\"100\">");
  		out.println("	<td align=center colspan=\"17\"><b>위치 정보를 입력한 후에 조회해 주세요.</b></td>");
  		out.println("</tr>");
  	} else {
  		sql.open();
  		List<Map<String, String>> dataList = sql.selectWifi(lat, lnt, 20);
  		
  		if(dataList != null){
  			for(Map<String, String> dataMap : dataList){
  	  			out.println("<tr>");
	  	  		for(String column : columns){
	  	    		out.println("<td>" + dataMap.get(column) + "</td>");
	  	    	}
  	  	  		out.println("</tr>");
  			}
  		} else {
  			out.println("<tr height=\"100\">");
  	  		out.println("	<td align=center colspan=\"17\"><b>DB정보를 불러오는데 실패했습니다.</b></td>");
  	  		out.println("</tr>");
  		}
  		
  		sql.close();
  	}
  %>
</table>
<script type="text/javascript">
	function test(lat, lnt){
		location.href="/?lat="+lat+"&lnt="+lnt;
	}
</script>
</body>
</html>