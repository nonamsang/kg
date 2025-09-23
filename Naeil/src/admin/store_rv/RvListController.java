package admin.store_rv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import main.StaticInfo;

public class RvListController {
	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public RvListController() throws Exception {
		con = new RvListConn().getConnection();
	}

	public ArrayList<RvListModel> getAllInfo(int store_id) throws Exception {
		ArrayList<RvListModel> rvList = new ArrayList<RvListModel>();
//		String sql = "SELECT store_img 가게사진, rv_no 예약번호, menu_name 메뉴명, yeyakja_name 예약자성명 "
//				+ "FROM reservation r, menu m WHERE r.menu_id = m.menu_id";
//		String sql = "SELECT menu_img 메뉴사진, rv_no 예약번호,  store_name 가게명, menu_name 메뉴명, rv_date 예약날짜, yeyakja_name 예약자명, inwonsu 인원수 "
//				+ "FROM reservation r, menu m, store s "
//				+ "WHERE r.menu_id = m.menu_id AND m.store_id = s.store_id AND s.store_id = ? " + "ORDER BY 2";

		String sql = "SELECT store_img AS 가게사진,\r\n"
				+ "       rv_no AS 예약번호,\r\n"
				+ "       store_name AS 가게명,\r\n"
				+ "       rv_date AS 예약일,\r\n"
				+ "       rv_time AS 예약시간,\r\n"
				+ "       users_name AS 예약자이름,\r\n"
				+ "       inwonsu AS 인원수\r\n"
				+ "FROM (\r\n"
				+ "    SELECT s.store_img,\r\n"
				+ "           r.rv_no,\r\n"
				+ "           s.store_name,\r\n"
				+ "           TO_CHAR(r.rv_date, 'YYYY-MM-DD') AS rv_date,\r\n"
				+ "           r.rv_time,\r\n"
				+ "           u.users_name,\r\n"
				+ "           r.inwonsu,\r\n"
				+ "           ROW_NUMBER() OVER (PARTITION BY r.rv_no ORDER BY r.rv_no) AS rn\r\n"
				+ "    FROM reservation r\r\n"
				+ "    JOIN menu m ON r.menu_id = m.menu_id\r\n"
				+ "    JOIN store s ON m.store_id = s.store_id\r\n"
				+ "    JOIN users u ON r.users_signum = u.users_signum\r\n"
				+ "    WHERE s.store_id = ?\r\n"
				+ ")\r\n"
				+ "WHERE rn = 1\r\n"
				+ "ORDER BY rv_no";

		ps = con.prepareStatement(sql);
		ps.setInt(1, store_id);
		rs = ps.executeQuery();

		while (rs.next()) {
			String store_img = rs.getString(1);
			int rv_no = rs.getInt(2);
			String store_name = rs.getString(3);
			String rv_date = rs.getString(4);
			String rv_time = rs.getString(5);
			String yeyakja_name = rs.getString(6);
			int inwonsu = rs.getInt(7);

			RvListModel rlm = new RvListModel(store_img, rv_no, store_name, rv_date, rv_time, yeyakja_name, inwonsu);

			rvList.add(rlm);
		}

//		for (RvListModel rv : rvList) {
//			System.out.println(
//					rv.getRvImg() + " " + rv.getRvNo() + " " + rv.getMenuName() + " " + rv.getYeyakjaName() + "\n");
//		}

		rs.close();
		ps.close();
		con.close();

		return rvList;
	}

	public ArrayList<RvListModel> getUserRvList() throws Exception {
		ArrayList<RvListModel> rvList = new ArrayList<RvListModel>();
		String sql = "SELECT store_img AS 가게사진,\r\n"
				+ "       rv_no AS 예약번호,\r\n"
				+ "       store_name AS 가게명,\r\n"
				+ "       rv_date AS 예약일,\r\n"
				+ "       rv_time AS 예약시간,\r\n"
				+ "       users_name AS 예약자이름,\r\n"
				+ "       inwonsu AS 인원수\r\n"
				+ "FROM (\r\n"
				+ "    SELECT s.store_img,\r\n"
				+ "           r.rv_no,\r\n"
				+ "           s.store_name,\r\n"
				+ "           TO_CHAR(r.rv_date, 'YYYY-MM-DD') AS rv_date,\r\n"
				+ "           r.rv_time,\r\n"
				+ "           u.users_name,\r\n"
				+ "           r.inwonsu,\r\n"
				+ "           ROW_NUMBER() OVER (PARTITION BY r.rv_no ORDER BY r.rv_no) AS rn\r\n"
				+ "    FROM reservation r\r\n"
				+ "    JOIN menu m ON r.menu_id = m.menu_id\r\n"
				+ "    JOIN store s ON m.store_id = s.store_id\r\n"
				+ "    JOIN users u ON r.users_signum = u.users_signum\r\n"
				+ "    WHERE u.users_signum = ?\r\n"
				+ ")\r\n"
				+ "WHERE rn = 1\r\n"
				+ "ORDER BY rv_no";

		ps = con.prepareStatement(sql);
		ps.setInt(1, StaticInfo.statUserNo);
		rs = ps.executeQuery();
		while (rs.next()) {
			String store_img = rs.getString(1);
			int rv_no = rs.getInt(2);
			String store_name = rs.getString(3);
			String rv_date = rs.getString(4);
			String rv_time = rs.getString(5);
			String yeyakja_name = rs.getString(6);
			int inwonsu = rs.getInt(7);

			RvListModel rlm = new RvListModel(store_img, rv_no, store_name, rv_date, rv_time, yeyakja_name, inwonsu);

			rvList.add(rlm);
		}

//		for (RvListModel rv : rvList) {
//			System.out.println(
//					rv.getStoreImg() + " " + rv.getRvNo() + " " + rv.getMenuName() + " " + rv.getYeyakjaName() + "\n");
//		}

		rs.close();
		ps.close();
		con.close();

		return rvList;
	}
}
