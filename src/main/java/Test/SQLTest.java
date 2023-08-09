package Test;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLTest {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private boolean isOpened = false;
	
	private String dbName = "wifi_info";
	private String wifiTableName = "wifi";
	private String historyTableName = "history";
	private String bookmarkTableName = "bookmark";
	private String bookmarkWifiTableName = "bookmark_wifi";
	private String db = "jdbc:sqlite:"+dbName+".db";
	
	public boolean open() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.con = DriverManager.getConnection(this.db);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
		this.isOpened = true;
		return true;
	}
	
	public boolean close() {
		if(this.isOpened) {
			try {
				this.con.close();
				this.pstmt.close();
				this.rs.close();
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
	
	public boolean createTable() {
		return (createWifi() && createHistory() && createBookmark() && createBookmarkWifi()) ? true : false;
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
			sql.append("   (ID INTEGER PRIMARY KEY,");
			sql.append("	LAT TEXT, ");
			sql.append("	LNT TEXT, ");
			sql.append("	DATE_TIME TEXT) ");
			
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
	
	public boolean createBookmark() {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("CREATE TABLE IF NOT EXISTS ");
			sql.append(this.bookmarkTableName);
			sql.append("   (ID INTEGER PRIMARY KEY,");
			sql.append("	NAME TEXT, ");
			sql.append("	\"ORDER\" INTEGER, ");
			sql.append("	DATE_TIME TEXT, ");
			sql.append("	LAST_REVISION_DATE_TIME TEXT) ");
			
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
	
	public boolean createBookmarkWifi() {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("CREATE TABLE IF NOT EXISTS ");
			sql.append(this.bookmarkWifiTableName);
			sql.append("   (ID INTEGER PRIMARY KEY,");
			sql.append("	BOOKMARK_ID TEXT, ");
			sql.append("	BOOKMARK_NAME TEXT, ");
			sql.append("	WIFI_ID TEXT, ");
			sql.append("	WIFI_NAME TEXT, ");
			sql.append("	DATE_TIME TEXT, ");
			sql.append("	FOREIGN KEY(BOOKMARK_ID) REFERENCES bookmark(ID) ");
			sql.append("	FOREIGN KEY(WIFI_ID) REFERENCES wifi(WIFO_MGR_NO)) ");
			
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
	
	public boolean insertWifi(StringBuilder dataStr) {
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
	
	public boolean insertHistory(StringBuilder dataStr) {
		if(this.isOpened) {
			
			StringBuilder sql = new StringBuilder();
					
			sql.append("INSERT INTO ");
			sql.append(this.historyTableName);
			sql.append("(LAT, LNT, DATE_TIME) VALUES ");
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
	
	public boolean insertBookmark(String dataStr) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
					
			sql.append("INSERT INTO ");
			sql.append(this.bookmarkTableName);
			sql.append("(NAME, \"ORDER\", DATE_TIME, LAST_REVISION_DATE_TIME) VALUES ");
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
	
	public boolean insertBookmarkWifi(String dataStr) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
					
			sql.append("INSERT INTO ");
			sql.append(this.bookmarkWifiTableName);
			sql.append("(BOOKMARK_ID, BOOKMARK_NAME, WIFI_ID, WIFI_NAME, DATE_TIME) VALUES ");
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
			StringBuilder dataStr = new StringBuilder();
			
			 LocalDateTime now = LocalDateTime.now();
		     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		     String formattedDateTime = now.format(formatter);
			
			dataStr.append("(\"");
			dataStr.append(lat);
			dataStr.append("\", \"");
			dataStr.append(lnt);
			dataStr.append("\", \"");
			dataStr.append(formattedDateTime);
			dataStr.append("\")");
			insertHistory(dataStr);
			
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
	
	public Map<String, String> selectWifi(String mgrNo) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM ");
			sql.append(this.wifiTableName);
			sql.append(" WHERE WIFI_MGR_NO = \"");
			sql.append(mgrNo);
			sql.append("\"");

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				Map<String, String> dataMap = new HashMap<String, String>();
				
				if(rs.next()) {
					dataMap.put("거리(Km)", String.valueOf(0.0));
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
				}
//				System.out.println(dataMap);
				return dataMap;
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
		} else {
			System.out.println("didn't opened");
			return null;
		}
	}
	
	public List<Map<String, String>> selectHistory() {
		if(this.isOpened) {
			
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append(this.historyTableName);
			sql.append(" ORDER BY DATE_TIME DESC");

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				while(rs.next()) {
					Map<String, String> dataMap = new HashMap<String, String>();
					
					dataMap.put("ID", rs.getString("ID"));
					dataMap.put("X좌표", rs.getString("LNT"));
					dataMap.put("Y좌표", rs.getString("LAT"));
					dataMap.put("조회일자", rs.getString("DATE_TIME"));
					
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
	
	public List<Map<String, String>> selectBookmark() {
		if(this.isOpened) {
			
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append(this.bookmarkTableName);
			sql.append(" ORDER BY \"ORDER\"");

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				while(rs.next()) {
					Map<String, String> dataMap = new HashMap<String, String>();
					
					dataMap.put("ID", rs.getString("ID"));
					dataMap.put("북마크 이름", rs.getString("NAME"));
					dataMap.put("순서", rs.getString("ORDER"));
					dataMap.put("등록일자", rs.getString("DATE_TIME"));
					dataMap.put("수정일자", rs.getString("LAST_REVISION_DATE_TIME"));
					
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
	
	public Map<String, String> selectBookmark(String id) {
		if(id == null) {
			return null;
		}
		
		if(this.isOpened) {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append(this.bookmarkTableName);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");


			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				Map<String, String> dataMap = new HashMap<String, String>();
				if(rs.next()) {
					
					
					dataMap.put("ID", rs.getString("ID"));
					dataMap.put("북마크 이름", rs.getString("NAME"));
					dataMap.put("순서", rs.getString("ORDER"));
					dataMap.put("등록일자", rs.getString("DATE_TIME"));
					dataMap.put("수정일자", rs.getString("LAST_REVISION_DATE_TIME"));
					
				}
				
				return dataMap;
				
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
			
		} else {
			System.out.println("didn't opened");
			return null;
		}
	}
	
	public List<Map<String, String>> selectBookmarkWifi() {
		if(this.isOpened) {
			
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append(this.bookmarkWifiTableName);

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				
				while(rs.next()) {
					Map<String, String> dataMap = new HashMap<String, String>();
					
					dataMap.put("ID", rs.getString("ID"));
					dataMap.put("북마크 이름", rs.getString("BOOKMARK_NAME"));
					dataMap.put("와이파이명", rs.getString("WIFI_NAME"));
					dataMap.put("등록일자", rs.getString("DATE_TIME"));
					
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
	
	public Map<String, String> selectBookmarkWifi(String id) {
		if(this.isOpened) {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append(this.bookmarkWifiTableName);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");

			try {
				this.pstmt = con.prepareStatement(sql.toString());
				rs = this.pstmt.executeQuery();
				Map<String, String> dataMap = new HashMap<String, String>();
				
				if(rs.next()) {
					dataMap.put("ID", rs.getString("ID"));
					dataMap.put("북마크 이름", rs.getString("BOOKMARK_NAME"));
					dataMap.put("와이파이명", rs.getString("WIFI_NAME"));
					dataMap.put("등록일자", rs.getString("DATE_TIME"));
				}
				
				return dataMap;
				
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
			
		} else {
			System.out.println("didn't opened");
			return null;
		}
	}
	
	public boolean updateBookmark(String id, String update) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(this.bookmarkTableName);
			sql.append(" SET ");
			sql.append(update);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");

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
	
	public boolean deleteHistory(String id) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("DELETE FROM ");
			sql.append(this.historyTableName);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");

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
	
	public boolean deleteBookmark(String id) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("DELETE FROM ");
			sql.append(this.bookmarkTableName);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");

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
	
	public boolean deleteBookmarkWifi(String id) {
		if(this.isOpened) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("DELETE FROM ");
			sql.append(this.bookmarkWifiTableName);
			sql.append(" WHERE ID=\"");
			sql.append(id);
			sql.append("\"");

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
}
