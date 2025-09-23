package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import admin.store_rv.RvListConn;
import main.StaticInfo;

public class UpdateInfo {

	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public UpdateInfo() throws Exception {
		con = new RvListConn().getConnection();
	}

	public boolean updateMenu(int menu_id, String menu_img, String menu_name, int price, String seolmyung) {
		String sql = "UPDATE menu " + "SET menu_img = ?, menu_name = ?, price = ?, seolmyung = ? "
				+ "WHERE menu_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, menu_img);
			ps.setString(2, menu_name);
			ps.setInt(3, price);
			ps.setString(4, seolmyung);
			ps.setInt(5, menu_id);

			int i = ps.executeUpdate();
			if (i < 1) {
				JOptionPane.showMessageDialog(null, "메뉴 변경 실패");
				return false;
			}

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean insertMenu(int store_id, String newImgPath, String newName, int newPrice, String newSeolmyung) {
		String sql = "INSERT INTO menu (menu_id, store_id, menu_img, menu_name, price, seolmyung)"
				+ "VALUES (menu_id_seq.NEXTVAL, ?, ?, ?, ? , ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, store_id);
			ps.setString(2, newImgPath);
			ps.setString(3, newName);
			ps.setInt(4, newPrice);
			ps.setString(5, newSeolmyung);

			int result = ps.executeUpdate();
			return result > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean insertStore(String newImgPath, String newName, String newCategory, String newJuso, String newPh, String newSeolmyung) {
		String sql = "INSERT INTO store (store_id, store_img, store_name, category, location, store_ph, seolmyung, users_signum)"
				+ "VALUES (store_id_seq.NEXTVAL, ?, ?, ?, ? , ?, ?, ?)";
		//INSERT INTO store (store_id, store_name, store_img, category, location) VALUES (1, '육즙 좔좔 스테이크', '/steak/0_st.jpg', '양식', '서울 강남구 논현로 150길');
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, newImgPath);
			ps.setString(2, newName);
			ps.setString(3, newCategory);
			ps.setString(4, newJuso);
			ps.setString(5, newPh);
			ps.setString(6, newSeolmyung);
			ps.setInt(7, StaticInfo.statUserNo);

			int result = ps.executeUpdate();
			return result > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean UpdateStore(String newImgPath, String newName, String newCategory, String newJuso, String newPh,
			String newSeolmyung) {
		String sql = "UPDATE store SET store_img = ?, store_name = ?, category = ?, location = ?, store_ph = ?, seolmyung = ? "
				+ "WHERE store_id = ?";
		// INSERT INTO store (store_id, store_name, store_img, category, location)
		// VALUES (1, '육즙 좔좔 스테이크', '/steak/0_st.jpg', '양식', '서울 강남구 논현로 150길');
		// "UPDATE reservation SET rv_date=?, inwonsu=?, yochung=? WHERE rv_no=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, newImgPath);
			ps.setString(2, newName);
			ps.setString(3, newCategory);
			ps.setString(4, newJuso);
			ps.setString(5, newPh);
			ps.setString(6, newSeolmyung);
			ps.setInt(7, StaticInfo.store_id);

			int result = ps.executeUpdate();
			return result > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteMenu(int menu_id) {
		String sql = "DELETE FROM menu WHERE menu_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, menu_id);
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
