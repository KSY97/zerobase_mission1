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
	String[] columns = {"거리(Km)", "관리번호", "자치구", "와이파이명", "도로명주소", "상세주소", 
			"설치위치(층)", "설치유형", "설치기관", "서비스구분", "망종류", "설치년도",
			 "실내외구분", "WIFI접속환경", "X좌표", "Y좌표", "작업일자"};
%>
<%
	sql.open();
	sql.createTable();
	sql.close();
	String lat = request.getParameter("lat");
	String lnt = request.getParameter("lnt");
%>
<div>
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
	
	<button onclick="geoFindMe()">내 위치 가져오기</button>
	<button type="submit" onclick="test(lat.value, lnt.value)">근처 WIFI 정보 보기</button>
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
  	if(lat==null && lnt==null){
  		out.println("<tr height=\"100\">");
  		out.println("	<td align=center colspan=\"17\"><b>위치 정보를 입력한 후에 조회해 주세요.</b></td>");
  		out.println("</tr>");
  	} else {
  		sql.open();
  		List<Map<String, String>> dataList = sql.selectWifi(lat, lnt, 20);
  		sql.close();
  		
  		if(dataList != null){
  			for(Map<String, String> dataMap : dataList){
  	  			out.println("<tr>");
	  	  		for(String column : columns){
	  	    		if("와이파이명".equals(column)){
	  	    			out.print("<td><a href=\"detail.jsp?mgrNo="+ dataMap.get("관리번호") + "\">");
	  	    			out.println(dataMap.get(column) + "</a></td>");
	  	    		} else {
	  	    			out.println("<td>" + dataMap.get(column) + "</td>");
	  	    		}
	  	    	}
  	  	  		out.println("</tr>");
  			}
  		} else {
  			out.println("<tr height=\"100\">");
  	  		out.println("	<td align=center colspan=\"17\"><b>DB정보를 불러오는데 실패했습니다.</b></td>");
  	  		out.println("</tr>");
  		}
  	}
  %>
</table>
<script type="text/javascript">
	function test(latValue, lntValue){
		location.href="/?lat="+latValue+"&lnt="+lntValue;
	}
	
	function geoFindMe() {
		function success(position) {
			const latitude = position.coords.latitude;
			const longitude = position.coords.longitude;
			
			console.log(latitude);
			console.log(longitude);
			
			document.getElementById("lat").value = latitude;
			document.getElementById("lnt").value = longitude;
		}
		
		function error() {
			alert("현재 위치를 가져올 수 없음");
			console.log("위치X");
		}
		
		if (!navigator.geolocation) {
			alert("브라우저가 위치 정보를 지원하지 않음");
			console.log("위치정보지원X");
		} else {
			status.textContent = "위치 파악 중…";
			navigator.geolocation.getCurrentPosition(success, error);
		}
	}	
</script>
</body>
</html>