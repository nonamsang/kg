package user;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
///////////////////
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.StaticInfo;
import user.InfoTable.StoreInfo;

public class sqlDAO2 extends Frame implements ActionListener {/////////////
	private Label la1 = new Label("search name :");
	private TextField tf1 = new TextField();
	private Button b1 = new Button("go search");
	private TextArea ta1 = new TextArea();///////////////
	private TextField tf2 = new TextField();

//    private ArrayList<StoreInfo> storeList = new ArrayList<>();
//	public ArrayList<StoreInfo> getStoreList() {
//	    return storeList;
//	}
//	
	private ArrayList<MenuItemInfo> menuList = new ArrayList<>();

	public ArrayList<MenuItemInfo> getMenuList() {
		return menuList;
	}

	public int idjungbok(String id) throws ClassNotFoundException {
		Connection con = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "SELECT USERS_ID FROM users";
			PreparedStatement st2 = con.prepareStatement(sql);

			ResultSet rs2 = st2.executeQuery();

			while (rs2.next()) {
				System.out.println(rs2.getString("USERS_ID"));
				System.out.println(id);
				if (rs2.getString("USERS_ID").equals(id)) {
					System.out.println("중복");
					return 1;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "예약 중 오류 발생: " + e.getMessage());
		}
		return 0;
	}

	public void setMenuOrdered(List<OrderedItemInfo> orderedItems) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement st = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			// 시퀀스 증가 및 현재 rv_no 값 얻기
			String getNextVal = "SELECT rv_no_seq.NEXTVAL FROM dual";
			PreparedStatement stGetRvNo = con.prepareStatement(getNextVal);
			ResultSet rs = stGetRvNo.executeQuery();

			int rvNo = -1;
			if (rs.next()) {
				rvNo = rs.getInt(1);
			}
			System.out.println(rvNo);

			String sqlInsert = "INSERT INTO reservation (rv_no, menu_id, suryang, users_signum, rv_date, rv_time, inwonsu, yochung)"
					+ " VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";
			PreparedStatement st2 = con.prepareStatement(sqlInsert);

			for (OrderedItemInfo item : orderedItems) {
				st2.setInt(1, rvNo);
				st2.setInt(2, item.getMenuId());
				st2.setInt(3, item.getSuryang());
				st2.setInt(4, StaticInfo.statUserNo);
				st2.setString(5, item.getRvDate());
				st2.setString(6, item.getRvTime());
				st2.setInt(7, item.getInwonsu());
				st2.setString(8, item.getYochung());

				st2.addBatch();
			}
			st2.executeBatch();


		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "예약 중 오류 발생: " + e.getMessage());
		}
	}

	public static ArrayList<StoreInfo> getStore() {
		Connection con = null;
		ArrayList<StoreInfo> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "SELECT STORE_ID, STORE_NAME, STORE_IMG, seolmyung FROM STORE";

			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("STORE_ID");
				String name = rs.getString("STORE_NAME");
				String imgPath = rs.getString("STORE_IMG");
				String des = rs.getString("seolmyung");

				list.add(new StoreInfo(id, name, imgPath, "", "", "", des));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	public static ArrayList<StoreInfo> getStore(String store_name) {
		Connection con = null;
		ArrayList<StoreInfo> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
			String sql = "SELECT STORE_ID, STORE_NAME, STORE_IMG, seolmyung " + "FROM STORE "
					+ "WHERE REPLACE(STORE_NAME, ' ', '') LIKE ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%" + store_name.replaceAll(" ", "") + "%");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("STORE_ID");
				String name = rs.getString("STORE_NAME");
				String imgPath = rs.getString("STORE_IMG");
				String des = rs.getString("seolmyung");

				list.add(new StoreInfo(id, name, imgPath, "", "", "", des));
			}

			for (StoreInfo storeInfo : list) {
				System.out.println(storeInfo.name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	public StoreInfo getStoreBystoreId(int storeId) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "SELECT STORE_ID, STORE_NAME, STORE_IMG, CATEGORY, LOCATION, STORE_PH, seolmyung FROM STORE WHERE STORE_ID = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, storeId);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				// 결과가 있을 경우 데이터 처리
				int id = rs.getInt("STORE_ID");
				String name = rs.getString("STORE_NAME");
				String imgPath = rs.getString("STORE_IMG");
				String category = rs.getString("CATEGORY");
				String location = rs.getString("LOCATION");
				String phone = rs.getString("STORE_PH");
				String des = rs.getString("seolmyung");

				return new StoreInfo(id, name, imgPath, category, location, phone, des);
			} else {
				// 데이터가 없을 경우 메시지 출력
				System.out.println("No data found for storeId: " + storeId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public ArrayList<MenuItemInfo> getMenuByStoreId(int storeId) {
		ArrayList<MenuItemInfo> list = new ArrayList<>();
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "SELECT MENU_ID, MENU_NAME, MENU_IMG, PRICE, SEOLMYUNG FROM MENU WHERE STORE_ID = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, storeId);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("MENU_ID");
				String name = rs.getString("MENU_NAME");
				String img = rs.getString("MENU_IMG");
				int price = rs.getInt("PRICE");
				String des = rs.getString("SEOLMYUNG");

				list.add(new MenuItemInfo(id, name, img, price, des));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	public sqlDAO2() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
