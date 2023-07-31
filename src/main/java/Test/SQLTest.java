package Test;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class SQLTest {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private boolean isOpened = false;
	
	private String dbName = "test";
	private String wifiTableName = "wifi";
	private String historyTableName = "history";
	private String db = "jdbc:sqlite:"+dbName+".db";
	
	public boolean open() {
		if(!this.isOpened) {
			try {
				Class.forName("org.sqlite.JDBC");
				this.con = DriverManager.getConnection(this.db);
//				this.pstmt = con.prepareStatement();
			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
			
			this.isOpened = true;
			return true;
		} else {
			System.out.println("Already opened");
			return false;
		}
	}
	
	public boolean close() {
		if(this.isOpened) {
			try {
				this.pstmt.close();
				this.rs.close();
				this.con.close();
			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
			this.isOpened = false;
			return true;
		} else {
			System.out.println("Already closed");
			return false;
		}
				
	}

	public boolean createWifi() {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("CREATE TABLE IF NOT EXISTS ");
			sql.append(this.wifiTableName);
			sql.append(" (WIFI_MGR_NO TEXT PRIMARY KEY,");
			sql.append("	WIFI_WRDOFC TEXT, ");
			sql.append("	WIFI_MAIN_NM TEXT, ");
			sql.append("	WIFI_ADRES1 TEXT, ");
			sql.append("	WIFI_ADRES2 TEXT, ");
			sql.append("	WIFI_INSTL_FLOOR TEXT, ");
			sql.append("	WIFI_INSTL_TY TEXT, ");
			sql.append("	WIFI_INSTL_MBY TEXT, ");
			sql.append("	WIFI_SVC_SE TEXT, ");
			sql.append("	WIFI_CMCWR TEXT, ");
			sql.append("	WIFI_CNSTC_YEAR TEXT, ");
			sql.append("	WIFI_INOUT_DOOR TEXT, ");
			sql.append("	WIFI_REMARS3 TEXT, ");
			sql.append("	LAT TEXT, ");
			sql.append("	LNT TEXT, ");
			sql.append("	WORK_DTTM TEXT, ");
			sql.append("	SIN_LAT TEXT, ");
			sql.append("	COS_LAT TEXT, ");
			sql.append("	SIN_LNT TEXT, ");
			sql.append("	COS_LNT TEXT)");
			
			try {
				this.pstmt = con.prepareStatement(sql.toString());
				this.pstmt.executeUpdate();
			} catch(Exception e) {
				System.out.println(e);
				return false;
			}
			return true;
		} else {
			System.out.println("didn't opened");
			return false;
		}
	}
	
	public boolean createHistory() {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("CREATE TABLE IF NOT EXISTS ");
			sql.append(this.historyTableName);
			sql.append(" (WIFI_MGR_NO TEXT PRIMARY KEY,");
			sql.append("	WIFI_WRDOFC TEXT, ");
			sql.append("	WIFI_MAIN_NM TEXT, ");
			sql.append("	WIFI_ADRES1 TEXT, ");
			sql.append("	WIFI_ADRES2 TEXT, ");
			sql.append("	WIFI_INSTL_FLOOR TEXT, ");
			sql.append("	WIFI_INSTL_TY TEXT, ");
			sql.append("	WIFI_INSTL_MBY TEXT, ");
			sql.append("	WIFI_SVC_SE TEXT, ");
			sql.append("	WIFI_CMCWR TEXT, ");
			sql.append("	WIFI_CNSTC_YEAR TEXT, ");
			sql.append("	WIFI_INOUT_DOOR TEXT, ");
			sql.append("	WIFI_REMARS3 TEXT, ");
			sql.append("	LAT TEXT, ");
			sql.append("	LNT TEXT, ");
			sql.append("	WORK_DTTM TEXT, ");
			sql.append("	SIN_LAT TEXT, ");
			sql.append("	COS_LAT TEXT, ");
			sql.append("	SIN_LNT TEXT, ");
			sql.append("	COS_LNT TEXT)");
			
			try {
				this.pstmt = con.prepareStatement(sql.toString());
				this.pstmt.executeUpdate();
			} catch(Exception e) {
				System.out.println(e);
				return false;
			}
			return true;
		} else {
			System.out.println("didn't opened");
			return false;
		}
	}
	
	public boolean insert(StringBuilder dataStr) {
		if(this.isOpened) {
			
			StringBuilder sql = new StringBuilder();
					
			sql.append("INSERT OR REPLACE INTO ");
			sql.append(this.wifiTableName);
			sql.append("(WIFI_MGR_NO, WIFI_WRDOFC, WIFI_MAIN_NM,");
			sql.append(" WIFI_ADRES1, WIFI_ADRES2, WIFI_INSTL_FLOOR,");
			sql.append(" WIFI_INSTL_TY, WIFI_INSTL_MBY, WIFI_SVC_SE,");
			sql.append(" WIFI_CMCWR, WIFI_CNSTC_YEAR, WIFI_INOUT_DOOR,");
			sql.append(" WIFI_REMARS3, LAT, LNT, WORK_DTTM,");
			sql.append(" SIN_LAT, COS_LAT, SIN_LNT, COS_LNT) VALUES ");
			sql.append(dataStr);
			
			try {
				this.pstmt = con.prepareStatement(sql.toString());
				this.pstmt.executeUpdate();
			} catch(Exception e) {
				System.out.println(e);
				return false;
			}

			return true;
		} else {
			System.out.println("didn't opened");
			return false;
		}
	}
	
	public int selectWifiTotalNum() {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT COUNT(*) FROM ");
			sql.append(this.wifiTableName);
			
			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				if(rs.next()) {
					return rs.getInt(1);
				}
			} catch(Exception e) {
				System.out.println(e);
				return 0;
			}
			
			return 1;
		} else {
			System.out.println("didn't opened");
			return 0;
		}
	}
	
	public List<Map<String, String>> selectWifi(String lat, String lnt, int num) {
		if(this.isOpened) {
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			
	        double sinLat = Math.sin(Math.toRadians(Double.parseDouble(lat)));
	        double cosLat = Math.cos(Math.toRadians(Double.parseDouble(lat)));
	        double sinLnt = Math.sin(Math.toRadians(Double.parseDouble(lnt)));
	        double cosLnt = Math.cos(Math.toRadians(Double.parseDouble(lnt)));
	        
			StringBuilder sql = new StringBuilder();
			
			String query = "(" + cosLat + " * COS_LAT * (COS_LNT * " + cosLnt
	                + " + SIN_LNT * " + sinLnt + ") + " + sinLat + " * SIN_LAT ) ";
			
			sql.append("SELECT *, ");
			sql.append(query);
			sql.append("AS partial_distance");
			sql.append(" FROM ");
			sql.append(this.wifiTableName);
			sql.append(" ORDER BY partial_distance DESC LIMIT ");
			sql.append(num);

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				while(rs.next()) {
					Map<String, String> dataMap = new HashMap<String, String>();
					
			        double sinLatDB = Double.parseDouble(rs.getString("SIN_LAT"));
			        double cosLatDB = Double.parseDouble(rs.getString("COS_LAT"));
			        double sinLntDB = Double.parseDouble(rs.getString("SIN_LNT"));
			        double cosLntDB = Double.parseDouble(rs.getString("COS_LNT"));
					
			        double partial_distance = cosLat * cosLatDB * (cosLntDB * cosLnt + 
			        		sinLntDB * sinLnt) + sinLat * sinLatDB;
			        double distance = 6371 * Math.acos(partial_distance);
			        double distanceKM = Math.round(distance*10000)/10000.0;

					dataMap.put("거리(Km)", String.valueOf(distanceKM));
					dataMap.put("관리번호", rs.getString("WIFI_MGR_NO"));
					dataMap.put("자치구", rs.getString("WIFI_WRDOFC"));
					dataMap.put("와이파이명", rs.getString("WIFI_MAIN_NM"));
					dataMap.put("도로명주소", rs.getString("WIFI_ADRES1"));
					dataMap.put("상세주소", rs.getString("WIFI_ADRES2"));
					dataMap.put("설치위치(층)", rs.getString("WIFI_INSTL_FLOOR"));
					dataMap.put("설치유형", rs.getString("WIFI_INSTL_TY"));
					dataMap.put("설치기관", rs.getString("WIFI_INSTL_MBY"));
					dataMap.put("서비스구분", rs.getString("WIFI_SVC_SE"));
					dataMap.put("망종류", rs.getString("WIFI_CMCWR"));
					dataMap.put("설치년도", rs.getString("WIFI_CNSTC_YEAR"));
					dataMap.put("실내외구분", rs.getString("WIFI_INOUT_DOOR"));
					dataMap.put("WIFI접속환경", rs.getString("WIFI_REMARS3"));
					dataMap.put("X좌표", rs.getString("LNT"));
					dataMap.put("Y좌표", rs.getString("LAT"));
					dataMap.put("작업일자", rs.getString("WORK_DTTM"));
					
					dataList.add(dataMap);
				}
				
				return dataList;
				
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
			
		} else {
			System.out.println("didn't opened");
			return null;
		}
	}
}
