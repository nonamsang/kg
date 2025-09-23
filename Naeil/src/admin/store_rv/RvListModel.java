package admin.store_rv;

/*
 * 예약자 리스트를 저장하는 모델
 */

public class RvListModel {
	private int rv_no, inwonsu, store_id, price, menu_id;
	private String store_img, menu_img, store_name, yeyakja_name, yeyakja_phone, rv_date, rv_time, menu_name, location,
			seolmyung;

	public RvListModel() {
	}

//	public RvListModel(String menu_img, int rv_no, String menu_name, String yeyakja_name) {
//		this.menu_img = menu_img;
//		this.rv_no = rv_no;
//		this.menu_name = menu_name;
//		this.yeyakja_name = yeyakja_name;
//	}

//	public RvListModel(String store_img, int rv_no, String store_name, String menu_name, String rv_date,
//			String yeyakja_name, int inwonsu) {
//		
//	}

	/**
	 * @param rv_no
	 * @param store_img
	 * @param store_name
	 * @param yeyakja_name
	 * @param inwonsu
	 * @param rv_date
	 * @param menu_name
	 */

	// 예약목록에서 사용하는 정보
	public RvListModel(String store_img, int rv_no, String store_name, String rv_date, String rv_time,
			String yeyakja_name, int inwonsu) {
		this.store_img = store_img;
		this.rv_no = rv_no;
		this.store_name = store_name;
		this.rv_date = rv_date;
		this.rv_time = rv_time;
		this.yeyakja_name = yeyakja_name;
		this.inwonsu = inwonsu;
	}

	// 가게 선택에서 사용하는 정보
	public RvListModel(int store_id, String store_name) {
		this.store_id = store_id;
		this.store_name = store_name;
	}

	// 가게 관리에서 사용하는 정보
	public RvListModel(String store_name, String location) {
		this.store_name = store_name;
		this.location = location;
	}

	// 가게 관리 -> 메뉴관리에서 사뇽하는 정보
	public RvListModel(int menu_id, String menu_img, String menu_name, int price, String seolmyung) {
		this.menu_id = menu_id;
		this.menu_img = menu_img;
		this.menu_name = menu_name;
		this.price = price;
		this.seolmyung = seolmyung;
	}

	/**
	 * @return the rvId
	 */
	public int getRvNo() {
		return rv_no;
	}

	/**
	 * @param rvId the rvId to set
	 */
	public void setRvNo(int rvNo) {
		this.rv_no = rvNo;
	}

	/**
	 * @return the menuId
	 */
	public int getMenuId() {
		return menu_id;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(int menuId) {
		this.menu_id = menu_id;
	}

	/**
	 * @return the rvImg
	 */
	public String getMenuImg() {
		return menu_img;
	}

	/**
	 * @param rvImg the rvImg to set
	 */
	public void setMenuImg(String rvImg) {
		this.menu_img = rvImg;
	}

	/**
	 * @return the yeyakjaName
	 */
	public String getYeyakjaName() {
		return yeyakja_name;
	}

	/**
	 * @param yeyakjaName the yeyakjaName to set
	 */
	public void setYeyakjaName(String yeyakjaName) {
		this.yeyakja_name = yeyakjaName;
	}

	/**
	 * @return the yeyakjaPhone
	 */
	public String getYeyakjaPhone() {
		return yeyakja_phone;
	}

	/**
	 * @param yeyakjaPhone the yeyakjaPhone to set
	 */
	public void setYeyakjaPhone(String yeyakjaPhone) {
		this.yeyakja_phone = yeyakjaPhone;
	}

	/**
	 * @return the inwonsu
	 */
	public int getInwonsu() {
		return inwonsu;
	}

	/**
	 * @param inwonsu the inwonsu to set
	 */
	public void setInwonsu(int inwonsu) {
		this.inwonsu = inwonsu;
	}

	/**
	 * @return the rvDate
	 */
	public String getRvDate() {
		return rv_date;
	}

	/**
	 * @param rvDate the rvDate to set
	 */
	public void setRvDate(String rvDate) {
		this.rv_date = rvDate;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menu_name;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menu_name) {
		this.menu_name = menu_name;
	}

	/**
	 * @return the store_name
	 */
	public String getStoreName() {
		return store_name;
	}

	/**
	 * @param store_name the store_name to set
	 */
	public void setStoreName(String store_name) {
		this.store_name = store_name;
	}

	/**
	 * @return the store_img
	 */
	public String getStoreImg() {
		return store_img;
	}

	/**
	 * @param store_img the store_img to set
	 */
	public void setStoreImg(String store_img) {
		this.store_img = store_img;
	}

	/**
	 * @return the store_id
	 */
	public int getStoreId() {
		return store_id;
	}

	/**
	 * @param store_id the store_id to set
	 */
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}

	@Override
	public String toString() {
		return store_name; // 리스트에서 보이게 할 텍스트
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the seolmyung
	 */
	public String getSeolmyung() {
		return seolmyung;
	}

	/**
	 * @param seolmyung the seolmyung to set
	 */
	public void setSeolmyung(String seolmyung) {
		this.seolmyung = seolmyung;
	}

	/**
	 * @return the rv_time
	 */
	public String getRvTime() {
		return rv_time;
	}

	/**
	 * @param rv_time the rv_time to set
	 */
	public void setRvTime(String rv_time) {
		this.rv_time = rv_time;
	}

}
