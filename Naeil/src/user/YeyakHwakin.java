package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JCalendar;

import admin.store_rv.RvListConn;

public class YeyakHwakin extends JFrame {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	private JPanel contentPane;
	JTextArea textArea_info;
	JTextArea[] fields;
	JPanel Yaeyak_panel;
	JPanel naeyuk;

	private UserRvList parent;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				YeyakHwakin frame = new YeyakHwakin();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public java.sql.Date parseDate(String inputDate) throws ParseException {
		String[] formats = { "yyyy-MM-dd" };

		for (String fmt : formats) {
			try {
				// 길이 기반 필터
				if ((fmt.equals("yyyy-MM-dd") || fmt.equals("yyyy/MM/dd")) && inputDate.length() != 10)
					continue;

				// yyyy 기반 포맷 처리
				SimpleDateFormat sdf = new SimpleDateFormat(fmt);
				sdf.setLenient(false);
				Date parsed = sdf.parse(inputDate);

				// 연도 유효성 체크
				Calendar cal = Calendar.getInstance();
				cal.setTime(parsed);
				int year = cal.get(Calendar.YEAR);

				// 원래 형식과 일치 확인
				if (sdf.format(parsed).equals(inputDate)) {
					return new java.sql.Date(parsed.getTime());
				}

			} catch (Exception ignored) {
			}
		}

		throw new ParseException("지원하지 않는 날짜 형식입니다: " + inputDate, 0);
	}


	public YeyakHwakin() {
	}

	public YeyakHwakin(int rv_no, String store_name, UserRvList parent) throws Exception {
		this.parent = parent;
		conn = new RvListConn().getConnection();

		setBounds(100, 100, 847, 709);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("예약 페이지");
		setContentPane(contentPane);
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);

		Yaeyak_panel = new JPanel();
		Yaeyak_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		Yaeyak_panel.setBounds(12, 10, 498, 637);
		Yaeyak_panel.setBackground(Color.white);
		contentPane.add(Yaeyak_panel);
		Yaeyak_panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 0, 500, 66);
		panel_1.setBackground(Color.white);
		Yaeyak_panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblYaeyak_List = new JLabel("나의 예약");
		lblYaeyak_List.setBounds(194, 10, 112, 46);
		panel_1.add(lblYaeyak_List);
		lblYaeyak_List.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblYaeyak_List.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_storename = new JLabel("가게 이름  : ");
		lbl_storename.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lbl_storename.setBounds(10, 83, 72, 20);
		Yaeyak_panel.add(lbl_storename);

		JLabel storename = new JLabel(store_name);
		storename.setBounds(84, 76, 196, 36);
		storename.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		Yaeyak_panel.add(storename);

		String storeImgPath = getStoreImage(rv_no);
		createImagePanel(storeImgPath);
		createReservationInfoSection(rv_no);
		createDetailsArea(rv_no);
		createActionButtons(rv_no);
		createMenuSection(rv_no);
	}

	private String getStoreImage(int rv_no) {
		String fileName = "";
		try {
			String sql = "SELECT s.store_img FROM reservation r JOIN menu m ON r.menu_id = m.menu_id JOIN store s ON m.store_id = s.store_id WHERE r.rv_no = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rv_no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				fileName = rs.getString("store_img");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private void createImagePanel(String fileName) {
		JPanel imgPanel = new JPanel(new BorderLayout());
		imgPanel.setBounds(26, 121, 450, 187);
		imgPanel.setLayout(new BorderLayout());
		imgPanel.setBackground(Color.white);
		ImageIcon icon = null;
		try {
			URL imageUrl = getClass().getResource("/resources" + fileName);
			if (imageUrl != null) {
				icon = new ImageIcon(imageUrl);
				Image image = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
				imgPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
			} else {
				JLabel label = new JLabel("❌ 이미지 없음", JLabel.CENTER);
				label.setPreferredSize(new Dimension(250, 200));
				imgPanel.add(label, BorderLayout.CENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Yaeyak_panel.add(imgPanel);
		imgPanel.setVisible(true);
	}

	private void createReservationInfoSection(int rv_no) {
		naeyuk = new JPanel(null);
		naeyuk.setBounds(26, 343, 450, 120);
		naeyuk.setBackground(Color.white);
		Yaeyak_panel.add(naeyuk);
		naeyuk.setLayout(null);

//		contentPane.add(naeyuk);

		String[] labels = { "예약자 이름:", "전화번호:", "예약일:", "인원수:" };
		fields = new JTextArea[4];
		for (int i = 0; i < fields.length; i++) {
			JLabel label = new JLabel(labels[i]);
			label.setBounds(10, 14 + i * 25, 70, 18);
			label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			naeyuk.add(label);

			fields[i] = new JTextArea();
			fields[i].setBounds(82, 14 + i * 25, 100, 18);
			fields[i].setEditable(false);
			fields[i].setBorder(BorderFactory.createLineBorder(Color.darkGray));
			naeyuk.add(fields[i]);
		}

		try {
			String sql = "SELECT u.users_name, u.users_phone, TO_CHAR(r.rv_date, 'yyyy-MM-dd') || ' ' || r.rv_time AS rv_datetime_str, r.inwonsu FROM reservation r JOIN users u ON r.users_signum = u.users_signum WHERE rv_no = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rv_no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				fields[0].setText(rs.getString("users_name"));
				fields[1].setText(rs.getString("users_phone"));
				fields[2].setText(rs.getString("rv_datetime_str"));
				fields[3].setText(String.valueOf(rs.getInt("inwonsu")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		naeyuk.setVisible(true);
	}

	private void createDetailsArea(int rv_no) {
		JLabel lblinfo = new JLabel("요청사항 : ");
		lblinfo.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblinfo.setBounds(35, 473, 60, 15);
		Yaeyak_panel.add(lblinfo);

		textArea_info = new JTextArea();
		textArea_info.setBounds(35, 498, 430, 79);
		textArea_info.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		Yaeyak_panel.add(textArea_info);
		textArea_info.setEditable(true);
		try {
			String sql = "SELECT yochung FROM reservation WHERE rv_no = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rv_no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				textArea_info.setText(rs.getString("yochung"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createActionButtons(int rv_no) {
		JButton btnMinus = new JButton("-");
		btnMinus.setBounds(233, 89, 41, 20);
		btnMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// 현재 숫자 읽기
					String text = fields[3].getText().trim();
					int current = text.isEmpty() ? 0 : Integer.parseInt(text);
					current--;
					fields[3].setText(String.valueOf(current));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "숫자 형식이 아닙니다.");
				}
			}
		});
		btnMinus.setBackground(Color.white);
		naeyuk.add(btnMinus);

		JButton btnPlus = new JButton("+");
		btnPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// 현재 숫자 읽기
					String text = fields[3].getText().trim();
					int current = text.isEmpty() ? 0 : Integer.parseInt(text);
					current++;
					fields[3].setText(String.valueOf(current));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "숫자 형식이 아닙니다.");
				}
			}
		});
		btnPlus.setBackground(Color.white);
		btnPlus.setBounds(187, 89, 41, 20);
		naeyuk.add(btnPlus);

		JButton btnCalendar;
		btnCalendar = new JButton("");
		btnCalendar.setIcon(new ImageIcon(YeyakHwakin.class.getResource("/resources/calendar.png")));
		btnCalendar.setBounds(187, 64, 18, 18);
		btnCalendar.setBackground(Color.white);
		naeyuk.add(btnCalendar);
		btnCalendar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String originalDateStr = fields[2].getText();

				JDialog dialog = new JDialog();
				dialog.setSize(320, 360);
				dialog.getContentPane().setLayout(null);

				JCalendar calendar = new JCalendar();
				calendar.setBounds(10, 10, 280, 200);
				dialog.getContentPane().add(calendar);

				// 오전/오후 선택
				JLabel lblAmPm = new JLabel("오전/오후:");
				lblAmPm.setBounds(10, 220, 60, 25);
				dialog.getContentPane().add(lblAmPm);

				JComboBox<String> amPmBox = new JComboBox<>(new String[] { "오전", "오후" });
				amPmBox.setBounds(70, 220, 60, 25);
				dialog.getContentPane().add(amPmBox);

				// 시 선택 (1~12)
				JLabel lblHour = new JLabel("시:");
				lblHour.setBounds(140, 220, 20, 25);
				dialog.getContentPane().add(lblHour);

				String[] hours = new String[12];
				for (int i = 1; i <= 12; i++)
					hours[i - 1] = String.format("%02d", i);
				JComboBox<String> hourBox = new JComboBox<>(hours);
				hourBox.setBounds(160, 220, 50, 25);
				dialog.getContentPane().add(hourBox);

				// 분 선택 (0~59)
				JLabel lblMinute = new JLabel("분:");
				lblMinute.setBounds(220, 220, 20, 25);
				dialog.getContentPane().add(lblMinute);

				String[] minutes = new String[60];
				for (int i = 0; i < 60; i++)
					minutes[i] = String.format("%02d", i);
				JComboBox<String> minuteBox = new JComboBox<>(minutes);
				minuteBox.setBounds(240, 220, 50, 25);
				dialog.getContentPane().add(minuteBox);

				JButton btnConfirm = new JButton("확인");
				btnConfirm.setBounds(200, 260, 80, 25);
				dialog.getContentPane().add(btnConfirm);

				JButton btnCancel = new JButton("취소");
				btnCancel.setBounds(110, 260, 80, 25);
				dialog.getContentPane().add(btnCancel);

				btnConfirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Date selectedDate = calendar.getDate();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String datePart = sdf.format(selectedDate);

						String ampm = (String) amPmBox.getSelectedItem();
						int hour = Integer.parseInt((String) hourBox.getSelectedItem());
						String minute = (String) minuteBox.getSelectedItem();

						// 12시간 → 24시간 변환
						if ("오전".equals(ampm) && hour == 12)
							hour = 0;
						if ("오후".equals(ampm) && hour != 12)
							hour += 12;

						String timePart = String.format("%02d:%s", hour, minute);
						fields[2].setText(datePart + " " + timePart);
						dialog.dispose();
					}
				});

				btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fields[2].setText(originalDateStr);
						dialog.dispose();
					}
				});

				dialog.setLocationRelativeTo(naeyuk);
				dialog.setVisible(true);
			}
		});

		JButton btnOK = new JButton("확인");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conn.setAutoCommit(false);

					String dateTimeStr = fields[2].getText().trim();
					int inwonsu = Integer.parseInt(fields[3].getText().trim());
					String yochung = textArea_info.getText().trim();

					String[] parts = dateTimeStr.split(" ");
					if (parts.length != 2) {
						throw new IllegalArgumentException("날짜와 시간 형식이 잘못되었습니다. 예: 2025-04-23 14:30");
					}

					String dateStr = parts[0];
					String timeStr = parts[1];

					String selectSql = "SELECT rv_date, rv_time, inwonsu, yochung FROM reservation WHERE rv_no = ?";
					PreparedStatement selectStmt = conn.prepareStatement(selectSql);
					selectStmt.setInt(1, rv_no);
					ResultSet rs = selectStmt.executeQuery();

					String origDate = "", origTime = "", origYochung = "";
					int origInwon = 0;
					if (rs.next()) {
						origDate = rs.getDate("rv_date").toString();
						origTime = rs.getString("rv_time");
						origInwon = rs.getInt("inwonsu");
						origYochung = rs.getString("yochung");
					}

					boolean isSame = dateStr.equals(origDate) && timeStr.equals(origTime) && inwonsu == origInwon
							&& yochung.equals(origYochung);

					if (isSame) {
						JOptionPane.showMessageDialog(btnOK, "확인되었습니다.");
						dispose();
					} else {
						String updateSql = "UPDATE reservation SET rv_date=?, rv_time=?, inwonsu=?, yochung=? WHERE rv_no=?";
						PreparedStatement pstmt = conn.prepareStatement(updateSql);
						pstmt.setDate(1, parseDate(dateStr));
						pstmt.setString(2, timeStr);
						pstmt.setInt(3, inwonsu);
						pstmt.setString(4, yochung);
						pstmt.setInt(5, rv_no);

						int result = pstmt.executeUpdate();

						if (result > 0) {
							int confirm = JOptionPane.showConfirmDialog(btnOK, "변경사항이 있습니다. 수정하시겠습니까?", "확인",
									JOptionPane.YES_NO_OPTION);
							if (confirm == JOptionPane.YES_OPTION) {
								conn.commit();
								JOptionPane.showMessageDialog(btnOK, "수정이 완료되었습니다.");
								parent.updatePanel();
								dispose();
							} else {
								conn.rollback();
								fields[2].setText(origDate + " " + origTime);
								fields[3].setText(String.valueOf(origInwon));
								textArea_info.setText(origYochung);
								JOptionPane.showMessageDialog(btnOK, "수정이 취소되었습니다.");
							}
						} else {
							JOptionPane.showMessageDialog(btnOK, "수정 실패: 예약 정보 없음.");
							dispose();
						}
					}

				} catch (Exception ex) {
					try {
						conn.rollback();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					ex.printStackTrace();
					JOptionPane.showMessageDialog(btnOK, "오류: " + ex.getMessage());
				}
			}
		});
		btnOK.setBounds(380, 596, 85, 31);
		btnOK.setBackground(Color.white);
		Yaeyak_panel.add(btnOK);

		JButton btnCancle = new JButton("예약 취소");
		btnCancle.setBounds(35, 596, 85, 31);
		btnCancle.setBackground(Color.white);
		btnCancle.addActionListener(e -> {
			try {
				String sql = "DELETE FROM reservation WHERE rv_no = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rv_no);
				if (pstmt.executeUpdate() > 0) {
					JOptionPane.showMessageDialog(btnCancle, "예약이 취소되었습니다.");
					parent.updatePanel();
					dispose();
				} else {
					JOptionPane.showMessageDialog(btnCancle, "해당 예약이 존재하지 않습니다.");
					dispose();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(btnCancle, "다시 입력해주세요.");
			}
		});
		Yaeyak_panel.add(btnCancle);
	}

	private void createMenuSection(int rv_no) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(510, 10, 309, 637);
		contentPane.add(scrollPane);

		JPanel menu = new JPanel();
		menu.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(menu);
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setAlignmentX(Component.LEFT_ALIGNMENT);
		menu.setBackground(Color.white);

		try {
			String sql = "SELECT m.menu_img, m.menu_name, r.suryang FROM menu m JOIN reservation r ON m.menu_id = r.menu_id WHERE rv_no = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rv_no);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String fileName = rs.getString("menu_img");
				String menuName = rs.getString("menu_name");
				int suryang = rs.getInt("suryang");

				JPanel menuPanel = new JPanel(new BorderLayout(5, 5));
				menuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
				menuPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				menuPanel.setBackground(Color.white);

				URL imageUrl = getClass().getResource("/resources/" + fileName);
				if (imageUrl != null) {
					Image image = new ImageIcon(imageUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
					menuPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
				} else {
					menuPanel.add(new JLabel("❌", JLabel.CENTER), BorderLayout.WEST);
				}

				JLabel infoLabel = new JLabel("<html>메뉴명: " + menuName + "<br>수량: " + suryang + "</html>");
				menuPanel.add(infoLabel, BorderLayout.CENTER);
				menu.add(menuPanel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
