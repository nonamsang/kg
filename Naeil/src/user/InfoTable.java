package user;


class MenuItemInfo {
	int id;
	String name;
	String img;
	int price;
	String des;
	int count = 0;

	public MenuItemInfo(int id, String name, String img, int price, String des) {
		this.id = id;
		this.name = name;
		this.img = img;
		this.price = price;
		this.des = des;

	}

	public int getMenuId() {
		return id;
	}

	public String getMenuName() {
		return name;
	}

	public String getMenuImg() {
		return img;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return des;
	}
}

class OrderItem {

	private int id;
	private int quantity;

	// 부모 클래스의 생성자를 호출하여 id, name, img, price, des를 초기화
	public OrderItem(int id, int quantity) {
		this.id = id; // 부모 생성자 호출
		this.quantity = quantity;
	}

	public int getMenuId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

class OrderedItemInfo {

	private int store_id; // 가게 ID
	int menu_id; // 메뉴 ID
	int suryang; // 수량
	int price; // 가격
	String yeyakja_name; // 예약자 이름
	String yeyakja_phone; // 예약자 전화번호
	String rv_date; // 예약 날짜
	String rv_time; // 예약 시간
	int inwonsu; // 인원수
	String yochung; // 요청사항

	// 생성자
	public OrderedItemInfo(int menu_id, int suryang, String yeyakja_name, String yeyakja_phone, String rv_date,
			String rv_time, int inwonsu, String yochung) {
		this.menu_id = menu_id;
		this.suryang = suryang;
		this.yeyakja_name = yeyakja_name;
		this.yeyakja_phone = yeyakja_phone;
		this.rv_date = rv_date;
		this.rv_time = rv_time;
		this.inwonsu = inwonsu;
		this.yochung = yochung;
	}

	// Getter
	public int getMenuId() {
		return menu_id;
	}

	public int getSuryang() {
		return suryang;
	}

	public String getYeyakjaName() {
		return yeyakja_name;
	}

	public String getYeyakjaPhone() {
		return yeyakja_phone;
	}

	public String getRvDate() {
		return rv_date;
	}

	public String getRvTime() {
		return rv_time;
	}

	public int getInwonsu() {
		return inwonsu;
	}

	public String getYochung() {
		return yochung;
	}

	// Setter
	public void setMenuId(int menu_id) {
		this.menu_id = menu_id;
	}

	public void setSuryang(int suryang) {
		this.suryang = suryang;
	}

	public void setYeyakjaName(String yeyakja_name) {
		this.yeyakja_name = yeyakja_name;
	}

	public void setYeyakjaPhone(String yeyakja_phone) {
		this.yeyakja_phone = yeyakja_phone;
	}

	public void setRvDate(String rv_date) {
		this.rv_date = rv_date;
	}

	public void setRvTime(String rv_time) {
		this.rv_time = rv_time;
	}

	public void setInwonsu(int inwonsu) {
		this.inwonsu = inwonsu;
	}

	public void setYochung(String yochung) {
		this.yochung = yochung;
	}
}

public class InfoTable {
	public static class StoreInfo {
		public int id;
		public String img;
		public String name;
		public String category;
		public String location;
		public String phone;
		public String des;

		public StoreInfo(int id, String name, String img, String category, String location, String phone, String des) {
			this.id = id;
			this.name = name;
			this.img = img;
			this.category = category;
			this.location = location;
			this.phone = phone;
			this.des = des;
		}
	}
}
