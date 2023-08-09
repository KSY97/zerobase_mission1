package Wifi;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Api{
	
	int totalCnt = Integer.MAX_VALUE;
   
	public boolean getWifiInfo(Sql sql, int start, int end) throws Exception {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("676a55446367676f3336526e487656","UTF-8") ); /*인증키(sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입(xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));/*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start),"UTF-8")); /*요청시작위치(sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end),"UTF-8"));
        
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
//        System.out.println(conn.getResponseCode());       // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        JSONArray jsonRow = getJsonData(sb.toString());
        
		StringBuilder dataStr = new StringBuilder();
        
        for(Object obj : jsonRow) {
        	JSONObject jsonData = (JSONObject) obj;
//        	StringBuilder dataBuilder = getDataForQuery(jsonData);
        	dataStr.append(getDataForInsert(jsonData));
        }
        dataStr.delete(dataStr.length()-2, dataStr.length());
        
		return sql.insertWifi(dataStr);
	}
	
	public int getAllWifiInfo() {
		int start = 1;
		int end = 1000;
		boolean success = true;
		int num = 0;
		Sql sql = new Sql();
		sql.open();
		
		while(start < totalCnt) {
			try {
				if(!getWifiInfo(sql, start, end)) {
					success = false;
				}
			} catch(Exception e) {
				System.out.println(e);
			}
			
			start += 1000;
			end += 1000;
		}
		
		num = sql.selectWifiTotalNum();
		
		sql.close();
		
		if(success) {
			System.out.println("Success");
		} else {
			System.out.println("Error");
		}
		
		return num;
	};
	
	public JSONArray getJsonData(String jsonStr) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonStr);
		JSONObject jsonWifiInfo = (JSONObject) jsonObj.get("TbPublicWifiInfo");
		totalCnt = Integer.parseInt(jsonWifiInfo.get("list_total_count").toString());
		
		JSONArray jsonRow = (JSONArray) jsonWifiInfo.get("row");
		
		return jsonRow;
	}
	
	public String getDataForInsert(JSONObject jsonData) {
		
		StringBuilder sb = new StringBuilder();
		
		// openAPI에서는 위도 경도가 바뀌어 저장되어있음
		double lat = Double.parseDouble(jsonData.get("LNT").toString());
		double lnt = Double.parseDouble(jsonData.get("LAT").toString());
			
		sb.append("(\"");
		sb.append(jsonData.get("X_SWIFI_MGR_NO"));
		sb.append("\", \"");
		sb.append(jsonData.get("X_SWIFI_WRDOFC"));
		sb.append("\", \"");
		sb.append(jsonData.get("X_SWIFI_MAIN_NM"));
		sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_ADRES1"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_ADRES2"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_INSTL_FLOOR"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_INSTL_TY"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_INSTL_MBY"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_SVC_SE"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_CMCWR"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_CNSTC_YEAR"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_INOUT_DOOR"));
    	sb.append("\", \"");
    	sb.append(jsonData.get("X_SWIFI_REMARS3"));
    	sb.append("\", \"");
    	sb.append(lat);
    	sb.append("\", \"");
    	sb.append(lnt);
    	sb.append("\", \"");
    	sb.append(jsonData.get("WORK_DTTM"));
    	sb.append("\", \"");
    	sb.append(Math.sin(Math.toRadians(lat)));
    	sb.append("\", \"");
    	sb.append(Math.cos(Math.toRadians(lat)));
    	sb.append("\", \"");
    	sb.append(Math.sin(Math.toRadians(lnt)));
    	sb.append("\", \"");
    	sb.append(Math.cos(Math.toRadians(lnt)));
    	sb.append("\"), ");
    	
    	return sb.toString();
	}

}