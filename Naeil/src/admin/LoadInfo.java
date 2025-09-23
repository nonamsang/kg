package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import admin.store_rv.RvListConn;
import admin.store_rv.RvListModel;
import main.StaticInfo;

public class LoadInfo {
	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public LoadInfo() throws Exception {
		con = new RvListConn().getConnection();
	}

	// 가게 목록 불러오기
	public ArrayList<RvListModel> loadStores() throws Exception {
		ArrayList<RvListModel> rvList = new ArrayList<>();
		String sql = "SELECT store_id, store_name FROM store WHERE users_signum = ? ORDER BY 2";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, StaticInfo.statUserNo);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int store_id = rs.getInt("store_id");
			String store_name = rs.getString("store_name");
			rvList.add(new RvListModel(store_id, store_name));
		}

		return rvList;
	}

	// 해당 가게 정보 불러오기
	public String[] loadStoreInfo(int store_id) throws Exception {
		String sql = "SELECT store_name, location FROM store WHERE store_id = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, store_id);
		ResultSet rs = ps.executeQuery();

		String store_name = "";
		String location = "";
		while (rs.next()) {
			store_name = rs.getString("store_name");
			location = rs.getString("location");
		}

		return new String[] { store_name, location };
	}

	// 메뉴 정보 불러오기
	public ArrayList<RvListModel> loadMenu(int store_id) throws Exception {
		ArrayList<RvListModel> rvList = new ArrayList<>();

		String sql = "SELECT menu_img, menu_name, price, m.seolmyung, m.menu_id " + "FROM menu m, store s "
				+ "WHERE m.store_id = s.store_id " + "AND s.store_id = ? " + "ORDER BY m.menu_id";

		ps = con.prepareStatement(sql);
		ps.setInt(1, store_id);
		rs = ps.executeQuery();

		while (rs.next()) {
			String menu_img = rs.getString(1);
			String menu_name = rs.getString(2);
			int price = rs.getInt(3);
			String seolmyung = rs.getString(4);
			int menu_id = rs.getInt(5);
			rvList.add(new RvListModel(menu_id, menu_img, menu_name, price, seolmyung));
		}

		return rvList;
	}

	// 메뉴 상세 정보 불러오기
	public ArrayList<RvListModel> loadMenuDetails(int menu_id) throws Exception {
		ArrayList<RvListModel> rvList = new ArrayList<>();

		String sql = "SELECT menu_img, menu_name, price, seolmyung " + "FROM menu " + "WHERE menu_id = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, menu_id);
		rs = ps.executeQuery();

		while (rs.next()) {
			String menu_img = rs.getString(1);
			String menu_name = rs.getString(2);
			int price = rs.getInt(3);
			String seolmyung = rs.getString(4);
			rvList.add(new RvListModel(menu_id, menu_img, menu_name, price, seolmyung));
		}

		return rvList;
	}
}
