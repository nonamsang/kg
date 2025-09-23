package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import main.StaticInfo;

public class YeyakDetail extends JFrame {

	private JPanel contentPane;
	private JTextField yearField;// 년도 필드
	private JTextField monthField;// 월 필드
	private JButton[] dayButtons = new JButton[42];// 날짜 버튼 42개 한 이유가 1일이 토요일이면 세로 6줄이 필요함
	private JButton[] timeButtons = new JButton[30];// 반복문을 해서 firstorder랑 lastorder사이의 30분간격의 버튼
	private JButton[] humanButtons = new JButton[11];// 11개의 인원 선택버튼
	private String firstorder = "10:00"; // 이건 db에서 할거임
	private String lastorder = "19:00"; // 이건 db에서 할거임
	private String breakstart = "15:00"; // 이건 db에서 할거임
	private String breakend = "16:00"; // 이건 db에서 할거임
	private String[] human = { "1명", "2명", "3명", "4명", "5명", "6명", "7명", "8명", "9명", "10명", "11명이상(누르고 직접입력)" };// 이건
																												// db에서
																												// 할거임
	private String selectedTime = null;// 시간 선택 버튼에서 선택된 시간

	private String y;
	private String m;
	private String dd;

	JComponent timePicker; // 시간 컴포넌트
	JComponent dataPicker; // 데이터 컴포넌트
	private String manypeople; // 인원 직접입력시 사용
	private int inwonsu = 0;// 인원수
	private String login_id = "id"; // 이것도 임시로 한 것 뿐
	private String login_pw = "pw"; // me too
	private String yeyakja_name = "종로"; // 임시
	private String yeyakja_phone = "010-1234-5678"; // 임시
	private String yochung; // 요구사항

	// 이제부터는 파일을 받은것
	int store_id;
	int menu_id;
	int suryang;
	int price;
	String rv_date;
	String rv_time;

	sqlDAO2 dao = new sqlDAO2();
	private Map<MenuItemInfo, JPanel> addedPanels = new HashMap<>();

	// 가게마다 운영시간이 다달라서 그냥 20으로 설정을 하였고 관리자 서버에서 운영시간을 따로 입력 받아서 그걸로 참고하게 하려고 생각중

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getLogin_pw() {
		return login_pw;
	}

	public void setLogin_pw(String login_pw) {
		this.login_pw = login_pw;
	}

	public String getYeyakja_name() {
		return yeyakja_name;
	}

	public void setYeyakjaName(String yeyakja_name) {
		this.yeyakja_name = yeyakja_name;
	}

	public String getYeyakja_phone() {
		return yeyakja_phone;
	}

	public void setYeyakjaPhone(String yeyakja_phone) {
		this.yeyakja_phone = yeyakja_phone;
	}

	public void setYochung(String yochung) {
		this.yochung = yochung;
	}

	public void setInwonsu(int inwonsu) {
		this.inwonsu = inwonsu;
	}

	public void setYeyakja_name(String yeyakja_name) {
		this.yeyakja_name = yeyakja_name;
	}

	public void setYeyakja_phone(String yeyakja_phone) {
		this.yeyakja_phone = yeyakja_phone;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public void setSuryang(int suryang) {
		this.suryang = suryang;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setRv_date(String rv_date) {
		this.rv_date = rv_date;
	}

	public void setRv_time(String rv_time) {
		this.rv_time = rv_time;
	}

	public YeyakDetail() {
		setTitle("예약");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);

		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		// 상단 패널
		JPanel topPanel = new JPanel();
		yearField = new JTextField(4);
		monthField = new JTextField(2);
		JButton drawButton = new JButton("검색");
		JButton nextmonthButton = new JButton("다음달");
		JButton lastmonthButton = new JButton("전달");
		JLabel label2 = new JLabel();
		label2.setText("(년도랑 월 입력하고 싶으면 텍스트 입력)");

		topPanel.add(yearField);
		topPanel.add(new JLabel("년도"));
		topPanel.add(monthField);
		topPanel.add(new JLabel("월"));
		topPanel.add(drawButton);
		topPanel.add(lastmonthButton);
		topPanel.add(nextmonthButton);
		topPanel.add(label2);

		contentPane.add(topPanel, BorderLayout.NORTH);

		// 달력 요일 헤더
		JPanel calendarPanel = new JPanel(new GridLayout(7, 7, 5, 5));
		String[] days = { "일", "월", "화", "수", "목", "금", "토" };
		for (String day : days) {
			JLabel label = new JLabel(day, SwingConstants.CENTER);
			label.setForeground(day.equals("일") ? Color.RED : day.equals("토") ? Color.BLUE : Color.BLACK);
			calendarPanel.add(label);
		}

		// 날짜 버튼들 초기화
		for (int i = 0; i < 42; i++) {
			JButton btn = new JButton("");
			btn.setEnabled(false);
			final int index = i;

			btn.addActionListener(e -> {
				dd = dayButtons[index].getText();
				y = yearField.getText();
				m = monthField.getText();
				int beforedate = JOptionPane.showConfirmDialog(this,
						y + "년" + m + "월" + dd + "일" + "\r\n" + "선택하시겠습니까?", "날짜 확인", JOptionPane.YES_NO_OPTION);
				if (beforedate == JOptionPane.YES_NO_OPTION) {
					JOptionPane.showMessageDialog(this, "선택한 예약일: " + y + "년" + m + "월" + dd + "일");
				} else {
					JOptionPane.showMessageDialog(this, "선택한 예약일: " + y + "년" + m + "월" + dd + "일\n취소되었습니다.");
					return;
				}

				JPanel timePanel = timeButtons(firstorder, lastorder, breakstart, breakend);
				JScrollPane scrollPane = new JScrollPane(timePanel);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPane.setPreferredSize(new Dimension(500, 100));

				// ⬇️ 시간 버튼 보여주기
				Object[] options = { "OK", "날짜 다시 선택" };
				int result = JOptionPane.showOptionDialog(this, scrollPane, "예약 가능한 시간", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (result == 1) {
					selectedTime = null;
					JOptionPane.showMessageDialog(this, "날짜를 다시 선택해주세요.");
					y = null;
					m = null;
					dd = null;
				}

			});

			dayButtons[i] = btn;
			calendarPanel.add(btn);

		}

		contentPane.add(calendarPanel, BorderLayout.CENTER);

		// 버튼 액션 리스너
		drawButton.addActionListener(e -> {
			try {
				int year = Integer.parseInt(yearField.getText());
				int month = Integer.parseInt(monthField.getText());
				drawCalendar(year, month);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "올바른 연도와 월을 입력하세요.");
			}
		});
		nextmonthButton.addActionListener(e -> {
			try {
				int year = Integer.parseInt(yearField.getText());
				int month = Integer.parseInt(monthField.getText());

				month++; // 다음 달로 증가
				if (month > 12) {// month의 값이 12초과일때
					month = 1;// month의 값은 1이 되고
					year++; // 년도가 1이 증가함
				}

				yearField.setText(String.valueOf(year));
				monthField.setText(String.valueOf(month));
				drawCalendar(year, month); // 달력 다시 그리기
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "올바른 연도와 월을 입력하세요.");
			}
		});
		lastmonthButton.addActionListener(e -> {
			try {
				int year = Integer.parseInt(yearField.getText());
				int month = Integer.parseInt(monthField.getText());

				month--; // 다음 달로 증가
				if (month < 1) {// month의 값이 12초과일때
					month = 12;// month의 값은 1이 되고
					year--; // 년도가 1이 증가함
				}

				yearField.setText(String.valueOf(year));
				monthField.setText(String.valueOf(month));
				drawCalendar(year, month); // 달력 다시 그리기
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "올바른 연도와 월을 입력하세요.");
			}
		});

		// 기본값 현재 날짜로 입력
		Calendar now = Calendar.getInstance();
		yearField.setText(String.valueOf(now.get(Calendar.YEAR)));
		monthField.setText(String.valueOf(now.get(Calendar.MONTH) + 1));
		drawCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);

	}



	// 날짜 계산 및 버튼 활성화
	private void drawCalendar(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);

		int startDay = cal.get(Calendar.DAY_OF_WEEK); // 1~7 (일~토)
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Calendar d30 = (Calendar) today.clone();
		d30.add(Calendar.DATE, 30); // 오늘부터 +30일

		// 초기화
		for (int i = 0; i < 42; i++) {
			dayButtons[i].setText("");
			dayButtons[i].setEnabled(false);
		}

		for (int i = 0; i < lastDay; i++) {
			int index = (startDay - 1) + i;
			int day = i + 1;

			Calendar cellDate = Calendar.getInstance();
			cellDate.set(year, month - 1, day);
			cellDate.set(Calendar.HOUR_OF_DAY, 0);
			cellDate.set(Calendar.MINUTE, 0);
			cellDate.set(Calendar.SECOND, 0);
			cellDate.set(Calendar.MILLISECOND, 0);

			dayButtons[index].setText(String.valueOf(day));

			if (!cellDate.before(today) && !cellDate.after(d30)) {
				dayButtons[index].setEnabled(true); // 오늘 ~ +30일 이내만 클릭 가능
			}
		}
	}

	private JPanel timeButtons(String firstorder, String lastorder, String breakstart, String breakend) {
		JPanel timebuttons = new JPanel();
		timebuttons.setLayout(new GridLayout(0, 5, 10, 10));// 가로로 스크롤 할 수 있게 설정할 거임 그래서 스크롤 바를 설정해야함
		String[] firstSplit = firstorder.split(":"); // 퍼스트오더를 입력했던 것을 배열로 설정
		String[] lastSplit = lastorder.split(":"); // 라스트오더를 입력했던 것을 배열로 설정
		String[] breakSplit = breakstart.split(":");
		String[] breakSplit2 = breakend.split(":");
		int firsthour = (Integer.parseInt(firstSplit[0])); // 오픈시간을 문자열로 저장했는데 정수로 바꾸는 과정
		int firstminute = (Integer.parseInt(firstSplit[1]));// 오픈시간을 문자열로 저장했는데 정수로 바꾸는 과정2

		int lasthour = (Integer.parseInt(lastSplit[0])); // 라스트오더를 문자열로 저장 to 정수
		int lastminute = (Integer.parseInt(lastSplit[1])); // 라스트오더를 문자열로 저장 to 정수

		int breakstarthour = (Integer.parseInt(breakSplit[0]));
		int breakstartminute = (Integer.parseInt(breakSplit[1]));

		int breakendhour = (Integer.parseInt(breakSplit2[0]));
		int breakendminute = (Integer.parseInt(breakSplit2[1]));

		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, firsthour);
		start.set(Calendar.MINUTE, firstminute);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		Calendar last = Calendar.getInstance();
		last.set(Calendar.HOUR_OF_DAY, lasthour);
		last.set(Calendar.MINUTE, lastminute);

		Calendar now = Calendar.getInstance(); // 현재시간을 알려주는 now 객체 생성
		boolean istoday = false; // 오늘이 지났다면 거짓
		int selectedyear = Integer.parseInt(yearField.getText());
		int selectedmonth = Integer.parseInt(monthField.getText());
		int selectedday = Integer.parseInt(dd);
		now.set(Calendar.SECOND, 0 + 1 * 3600);
		now.set(Calendar.MILLISECOND, 0);

		Calendar breakbegin = Calendar.getInstance();
		breakbegin.set(Calendar.HOUR_OF_DAY, breakstarthour - 1);
		breakbegin.set(Calendar.MINUTE, breakstartminute);

		Calendar breakfinish = Calendar.getInstance();
		breakfinish.set(Calendar.HOUR_OF_DAY, breakendhour);
		breakfinish.set(Calendar.MINUTE, breakendminute);

		if (selectedyear == now.get(Calendar.YEAR) && selectedmonth == now.get(Calendar.MONTH) + 1
				&& selectedday == now.get(Calendar.DAY_OF_MONTH)) {
			istoday = true;
		}

		while (!start.after(last)) {
			int hour = start.get(Calendar.HOUR_OF_DAY);// start에 캘린더에서 선택한 일의 시간을 hour에 저장
			int minute = start.get(Calendar.MINUTE); // start에 캘린더에서 시작한 분을 minute에 저장
			String timeStr = String.format("%02d:%02d", hour, minute);

			if (istoday && start.before(now)) {
				start.add(Calendar.MINUTE, 30);
				continue; // 현재 시간 전이면 건너뜀
			}
			JButton timeBtn = new JButton(timeStr);
			if (start.after(breakbegin) && start.before(breakfinish)) {
				timeBtn.setEnabled(false); // break times
			}
			timeBtn.addActionListener(e -> {
				selectedTime = timeStr; // 선택한 시간 저장
				int time = JOptionPane.showConfirmDialog(this, "선택한 시간: " + selectedTime + "\n이 시간으로 예약하시겠습니까?",
						"예약 시간 확인", JOptionPane.YES_NO_OPTION);// yes no만 뜨게 설정함
				// yes를 선택하면 정상적으로 진행
				// no가 선택되면은 예약일자만 기록이 남아 있고 다시 설정하게 하면됨
				if (time == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(this, "선택한 시간: " + selectedTime);
					// 시간 선택 시 인원 선택 창 표시
					JPanel humanPanel = humanpanel();
					JScrollPane scrollPane2 = new JScrollPane(humanPanel);
					JOptionPane.showMessageDialog(this, scrollPane2, "예약 인원수", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "선택한 시간: " + selectedTime + " 이 취소되었습니다.");
				}

				for (JButton btn : dayButtons) {
					if (btn.isEnabled() && btn.getModel().isArmed()) {// btn에 값이 들어 있고 지금 누르고 있는 버튼인지 확인 하려는 것
						dd = btn.getText();
						break;
					}
				}
			});
			timebuttons.add(timeBtn);
			start.add(Calendar.MINUTE, 30); // 30분 간격

		}

		return timebuttons;

	}

	protected void showCalendar() {
		// TODO Auto-generated method stub

	}

	private JPanel humanpanel() { // 사람 수 버튼들이 담길 패널을 생성하는 메소드
		JPanel humanpanel = new JPanel(); // JPanel 객체 생성
		humanpanel.setLayout(new GridLayout(0, 4, 10, 10)); // 4열 그리드 레이아웃으로 버튼을 정렬하고, 간격 10px 지정
		for (String personLabel : human) {// human 배열에 있는 각 인원수 옵션을 바탕으로 버튼 생성
			JButton hubtl = new JButton(personLabel); // 버튼 생성
			hubtl.addActionListener(e -> { // 각 버튼에 액션 리스너 추가
				if (personLabel.contains("직접입력")) {// 버튼 텍스트가 "직접입력"일 경우
					manypeople = JOptionPane.showInputDialog("예약하실 인원수를 입력해주세요"); // 사용자에게 인원 수 직접 입력 받기
					if (manypeople != null && !manypeople.trim().isEmpty()) {// 입력값이 null이 아니고 공백이 아닐 때만 처리
						try {
							// 문자열을 숫자로 변환
							int man = Integer.parseInt(manypeople.trim());
							if (man > 0 && man <= 99) {// 인원이 1명 이상 100명 미만일 때만 정상 처리
								JOptionPane.showMessageDialog(this,
										"선택한 시간 : " + selectedTime + "\n" + "선택한 인원: " + man + "명");
								y = yearField.getText();
								m = monthField.getText();

								inwonsu = man;

								int mm = Integer.parseInt(m);
								int d2 = Integer.parseInt(dd);
								String date = String.format("%s-%02d-%02d", y, mm, d2);

								String finalMessage = "아래 정보가 맞는지 확인해주세요\n\n" + "\n" +
								// "메뉴: " + menu + "\n" +
								// "가격: " + price + "원 x " + itch + "=" + total + "원\n" +
										"예약일: " + date + "\n" + "예약시간: " + selectedTime + "\n" + "예약인원: " + inwonsu
										+ "\n";

								String daegi = "예약신청 하시겠습니까?";
								JOptionPane.showMessageDialog(this, finalMessage, "예약 정보 확인",
										JOptionPane.INFORMATION_MESSAGE);
								int last = JOptionPane.showConfirmDialog(this, daegi, "예약 최종 확인",
										JOptionPane.YES_NO_OPTION);// yes no만 뜨게 설정함
								// yes를 선택하면 정상적으로 진행
								// no가 선택되면은 예약일자만 기록이 남아 있고 다시 설정하게 하면됨
								if (last == JOptionPane.YES_OPTION) {
									JTextField nameField = new JTextField(10);// 네임필드라는 텍스트 필드 (추가는 안하고 그냥 기본 설정만)
									JTextField phoneField = new JTextField(10);// 폰필드라는 텍스트 필드(추가는 안하고 그냥 기본 설정만)
									JTextField requestField = new JTextField(15);// 요청필드라는 텍스트필드 (추가는 안하고 그냥 기본 설정만)
									JPanel inpanel = new JPanel(new GridLayout(0, 1));
									if (!login_id.isEmpty() && login_id != null && !login_pw.isEmpty()
											&& login_pw != null) {
										// 위에 회원이 아니면 주문이 안되지만 일단 임시로 했음. 아이디와 비밀번호가 일치하면 이라고 해야지(사실 이거 안해도 yes옵션이라고 하면
										// 될 것 같은데)
										nameField.setText(StaticInfo.statUserName);// 예약자의 이름을 namefield에 입력된 것(지금은
																					// setter만
																					// 있으니 오류가 남)
										phoneField.setText(StaticInfo.statUserPhone);// 예약자의 전화번호를 phonefield에 입력된 것( ""
																						// )
										inpanel.add(new JLabel("예약자 성함"));// 예약자의 이름을 알려주는 텍스트필드 왼쪽에 있음
										inpanel.add(nameField);// namefield라는 텍스트 필드 추가

										inpanel.add(new JLabel("전화번호 (중간에 - 입력 ex)010-####-####)"));// 예약자의 전화번호를 알려주는
																									// 텍스트필드 왼쪽에 있음
										inpanel.add(phoneField); // 텍스트필드 추가
										inpanel.add(new JLabel("요구사항 (선택사항)"));// 텍스트필드 왼쪽에 있음
										inpanel.add(requestField);// 요청필드 추가
										inpanel.add(new JLabel(
												"<html>예약자와 방문자가 다를경우<br>예약자의 정보를 지우고<br>방문자의 이름과 전화번호를 입력해주세요.</html>"));
									}
									Object[] option2 = { "확인", "취소" };
									int result = JOptionPane.showOptionDialog(this, inpanel, "예약자 정보를 입력해주세요",
											JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, option2,
											option2[0]);
									if (result == 0) {
										String name = nameField.getText().trim();
										String phone = phoneField.getText().trim();
										yochung = requestField.getText();
										if (!name.matches(".*[a-zA-Z가-힣]+.*")
												&& !phone.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
											JOptionPane.showMessageDialog(this,
													"이름은 문자로 입력해주시고\n전화번호를 형식에 맞게 입력해주세요.\n예: 010-1234-5678");
										} else if (!name.matches(".*[a-zA-Z가-힣]+.*")) {
											JOptionPane.showMessageDialog(this, "문자를 입력해주세요.");
											return; // 여기서 중단
										} else if (!phone.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
											JOptionPane.showMessageDialog(this,
													"전화번호를 형식에 맞게 입력해주세요.\n예: 010-1234-5678");
											return;
										}

										if (yochung.isEmpty()) {
											yochung = "없음";

										}

										// 여기서 예약 정보 요약 출력하거나 저장하는 코드로 연결!
										String finalSummary = "예약신청이 완료되었습니다!\n\n" + "▶ 예약자 성함: " + name + "\n"
												+ "▶ 연락처: " + phone + "\n" + "\n" + "▶ 날짜: "
												+ date + "\n" + "▶ 시간: " + selectedTime + "\n" + "▶ 인원: " + inwonsu
												+ "명\n" + "▶ 요구사항: " + yochung;

										JOptionPane.showMessageDialog(this, finalSummary, "최종 예약 정보",
												JOptionPane.INFORMATION_MESSAGE);

										JOptionPane.showMessageDialog(this,
												"\"예약 신청이 완료되었으며 예약 최종확인은\n 마이페이지에서 가능합니다.");
										dispose();
										String name13 = name; // 예약자 이름 (예시)
										String phone13 = phone; // 예약자 전화번호 (임시)
										String date13 = date; // 예약 날짜 (임시)
										String time13 = selectedTime; // 예약 시간
										int inwonsu13 = inwonsu; // 인원수
										String yochung13 = yochung; // 요청사항

										System.out.printf(y, m, dd);

										// 3. 각 OrderItem을 기반으로 OrderedItemInfo 생성
										List<OrderedItemInfo> orderedList = new ArrayList<>();
										for (OrderItem item : StoreMenu.orderItems) {
											OrderedItemInfo ordered = new OrderedItemInfo(item.getMenuId(),
													item.getQuantity(), name13, phone13, date13, time13, inwonsu13,
													yochung13);
											orderedList.add(ordered);
											ordered = null;
										}

										sqlDAO2 dao = new sqlDAO2(); // DAO 객체 생성
										try {
											dao.setMenuOrdered(orderedList); // DB에 예약 정보 삽입
											JOptionPane.showMessageDialog(null, "예약이 완료되었습니다!");
											orderedList.clear();
											StoreMenu.orderItems.clear();
										} catch (Exception ex) {
											ex.printStackTrace();
											JOptionPane.showMessageDialog(null, "예약 중 오류 발생: " + ex.getMessage());
										}
										dispose();
									}
									// 수정해야함.

									if (result == 1) {
										JOptionPane.showMessageDialog(this, "취소했습니다.");
										return;
									}

									// null값 허용하고 글자 제한을 걸어둘까.. 그냥 여유롭게 200로할까.. 생각중
									// 요구사항이 입력이 되면 예약자 서버에 식당,(메뉴,가격,수량,총합),~~ 여러가지 나오게 하고 방문꼭 해라 이런 문구 표시
									// 그리고 요구사항도 나오게 할려고 하는데 만약 수정을 하고 싶으면 이것도 예약 수정을 통해서..
									// 마찬가지로 식당 사장 서버에도 나와야 함. 다 나오고 고객이름이랑 전화번호 예약번호 나오게해야함.ex)예약날짜로 8글자로 하고 만약
									// 예약시간이
									// 16시다 202504161601 (이렇게해서 1~50까지)하게 하고 51~100은 1651로 가고 ..여기서 또 문제가 있는데 만약 취소를
									// 한다면 문제가 없지만
									// 수정을 한다면 예약번호를 변경해야할지 그냥 그대로 가야할지... 일단 2가지 버전으로 내일전까지 해결을 해야할 것 같음.
									// 시간 선택 시 인원 선택 창 표시

								} else if (last == JOptionPane.NO_OPTION) {
									JOptionPane.showMessageDialog(this, "다시 주문해주시길 바랍니다.\n 창이 다 닫힐 예정입니다.");
									y = null;
									m = null;
									dd = null;
									selectedTime = null;

									dispose();
								}
							} else if (man <= 0) {
								JOptionPane.showMessageDialog(this, "0명 이하로는 예약이 불가합니다.");
							} else {
								JOptionPane.showMessageDialog(this, "최대 예약인원은 99명입니다.");
							}
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(this, "숫자로 입력해주세요.");// 숫자가 아닌 값을 입력했을 경우
						}
					}
				} else {
					inwonsu = Integer.parseInt(personLabel.replaceAll("[^0-9]", ""));

					// "직접입력"이 아닌 경우 - 확인 다이얼로그로 예약 인원 확인
					int soo = JOptionPane.showConfirmDialog(this, "예약 인원: " + personLabel + " 맞나요?", // 내용
							"예약 인원 확인", // 타이틀
							JOptionPane.YES_NO_OPTION); // 예 / 아니오 버튼

					if (soo == JOptionPane.YES_OPTION) { // 예 버튼 눌렀을 경우
						JOptionPane.showMessageDialog(this,
								"선택한 시간 : " + selectedTime + "\n" + "선택한 예약인원 : " + personLabel);
						String y = yearField.getText();
						String m = monthField.getText();
						String chooseTime = selectedTime;// 선택한시간
						String selectPeople = personLabel;

						for (JButton btn : dayButtons) { // btn버튼이 daybutton이 활성화 될 때까지
							if (btn.getModel().isArmed()) {
								dd = btn.getText();
								break;
							}
						}
						if (y.isEmpty() || m.isEmpty() || dd.isEmpty()) {
							JOptionPane.showMessageDialog(this, "똑바로 입력해주세요.");
						}

						int mm = Integer.parseInt(m);
						int d2 = Integer.parseInt(dd);
						String date = String.format("%s-%02d-%02d", y, mm, d2);

						String finalMessage = "아래 정보가 맞는지 확인해주세요\n\n" + "예약일: " + date + "\n"
								+ "예약시간: " + selectedTime + "\n" + "예약인원: " + selectPeople + "\n";

						String daegi = "예약신청 하시겠습니까?"; // 예약신청을 하겠냐고 말하게 하는 것
						JOptionPane.showMessageDialog(this, finalMessage, "예약 정보 확인", JOptionPane.INFORMATION_MESSAGE);
						int last = JOptionPane.showConfirmDialog(this, daegi, "예약 최종 확인", JOptionPane.YES_NO_OPTION);// yes
																														// no만
																														// 뜨게
																														// 설정함
						// yes를 선택하면 정상적으로 진행
						// no가 선택되면은 예약일자만 기록이 남아 있고 다시 설정하게 하면됨
						if (last == JOptionPane.YES_OPTION) {
							JTextField nameField = new JTextField(10);// 네임필드라는 텍스트 필드 (추가는 안하고 그냥 기본 설정만)
							JTextField phoneField = new JTextField(10);// 폰필드라는 텍스트 필드(추가는 안하고 그냥 기본 설정만)
							JTextField requestField = new JTextField(15);// 요청필드라는 텍스트필드 (추가는 안하고 그냥 기본 설정만)
							JPanel inpanel = new JPanel(new GridLayout(0, 1));
							if (!login_id.isEmpty() && login_id != null && !login_pw.isEmpty() && login_pw != null) {
								// 위에 회원이 아니면 주문이 안되지만 일단 임시로 했음. 아이디와 비밀번호가 일치하면 이라고 해야지(사실 이거 안해도 yes옵션이라고 하면
								// 될 것 같은데)
								nameField.setText(StaticInfo.statUserName);// 예약자의 이름을 namefield에 입력된 것(지금은 setter만 있으니
																			// 오류가 남)
								phoneField.setText(StaticInfo.statUserPhone);// 예약자의 전화번호를 phonefield에 입력된 것( "" )
								inpanel.add(new JLabel("예약자 성함"));// 예약자의 이름을 알려주는 텍스트필드 왼쪽에 있음
								inpanel.add(nameField);// namefield라는 텍스트 필드 추가

								inpanel.add(new JLabel("전화번호 (중간에 - 입력 ex)010-####-####)"));// 예약자의 전화번호를 알려주는 텍스트필드 왼쪽에
																							// 있음
								inpanel.add(phoneField); // 텍스트필드 추가
								inpanel.add(new JLabel("요구사항 (선택사항)"));// 텍스트필드 왼쪽에 있음
								inpanel.add(requestField);// 요청필드 추가
								inpanel.add(new JLabel(
										"<html>예약자와 방문자가 다를경우<br>예약자의 정보를 지우고<br>방문자의 이름과 전화번호를 입력해주세요.</html>"));
							}
							Object[] option2 = { "확인", "취소" };
							int result = JOptionPane.showOptionDialog(this, inpanel, "예약자 정보를 입력해주세요",
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, option2, option2[0]);

							if (result == 1) {
								JOptionPane.showMessageDialog(this, "취소했습니다.");
								return;
							} else if (result == 2) {
								String name = nameField.getText().trim();
								String phone = phoneField.getText().trim();
								yochung = requestField.getText().trim();
								name = "";
								phone = "";

							}
							if (result == JOptionPane.OK_OPTION) {
								String name = nameField.getText().trim();
								String phone = phoneField.getText().trim();
								yochung = requestField.getText().trim();
								if (!name.matches(".*[a-zA-Z가-힣]+.*") && !phone.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
									JOptionPane.showMessageDialog(this,
											"이름은 문자로 입력해주시고\n전화번호를 형식에 맞게 입력해주세요.\n예: 010-1234-5678");
								} else if (!name.matches(".*[a-zA-Z가-힣]+.*")) {
									JOptionPane.showMessageDialog(this, "문자를 입력해주세요.");
									return; // 여기서 중단
								} else if (!phone.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
									JOptionPane.showMessageDialog(this, "전화번호를 형식에 맞게 입력해주세요.\n예: 010-1234-5678");
									return;
								}

								if (yochung.isEmpty()) {
									yochung = "없음";

								}

								// 여기서 예약 정보 요약 출력하거나 저장하는 코드로 연결!
								String finalSummary = "예약신청이 완료되었습니다!\n\n" + "▶ 예약자 성함: " + name + "\n" + "▶ 연락처: "
										+ phone + "\n" + "▶ 날짜: " + date + "\n" + "▶ 시간: " + selectedTime + "\n"
										+ "▶ 인원: " + inwonsu + "명\n" + "▶ 요구사항: " + yochung;

								JOptionPane.showMessageDialog(this, finalSummary, "최종 예약 정보",
										JOptionPane.INFORMATION_MESSAGE);

								JOptionPane.showMessageDialog(this,
										"<html>예약 신청이 완료되었으며 <br>예약 최종확인은 마이페이지에서 가능합니다.</html>");
								dispose();
								String name13 = name; // 예약자 이름 (예시)
								String phone13 = phone; // 예약자 전화번호 (임시)
								String date13 = date; // 예약 날짜 (임시)
								String time13 = selectedTime; // 예약 시간
								int inwonsu13 = inwonsu; // 인원수
								String yochung13 = yochung; // 요청사항


								System.out.printf(y, m, dd);

								// 3. 각 OrderItem을 기반으로 OrderedItemInfo 생성
								List<OrderedItemInfo> orderedList = new ArrayList<>();
								for (OrderItem item : StoreMenu.orderItems) {
									OrderedItemInfo ordered = new OrderedItemInfo(item.getMenuId(), item.getQuantity(),
											name13, phone13, date13, time13, inwonsu13, yochung13);
									orderedList.add(ordered);
									ordered = null;
								}

								// 4. 확인용 콘솔 출력
								System.out.println("== 최종 예약 정보 출력 ==");
								for (OrderedItemInfo oi : orderedList) {
									System.out.println("메뉴 ID: " + oi.getMenuId());
									System.out.println("수량: " + oi.getSuryang());
									System.out.println("예약자: " + oi.getYeyakjaName());
									System.out.println("전화번호: " + oi.getYeyakjaPhone());
									System.out.println("날짜: " + oi.getRvDate());
									System.out.println("시간: " + oi.getRvTime());
									System.out.println("인원수: " + oi.getInwonsu());
									System.out.println("요청사항: " + oi.getYochung());
									System.out.println("------------------------");
								}

								sqlDAO2 dao = new sqlDAO2(); // DAO 객체 생성
								try {
									dao.setMenuOrdered(orderedList); // DB에 예약 정보 삽입
									JOptionPane.showMessageDialog(null, "예약이 완료되었습니다!");
									orderedList.clear();
									StoreMenu.orderItems.clear();
								} catch (Exception ex) {
									ex.printStackTrace();
									JOptionPane.showMessageDialog(null, "예약 중 오류 발생: " + ex.getMessage());
								}

								setVisible(false);
								dispose();
							}
							// 수정해야함.

						} else if (last == JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(this, "다시 주문해주시길 바랍니다.\n 창이 닫힙니다.");
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(this, "선택한 예약인원 " + personLabel + " 취소되었습니다.");// 아니오 선택 시 예약 취소
																										// 알림
					}
				}
			});

			humanpanel.add(hubtl);// 생성된 버튼을 패널에 추가
		}

		return humanpanel; // 패널 반환
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			YeyakDetail frame = new YeyakDetail();
			frame.setVisible(true);

		});

	}

}
