package admin.store_rv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import user.YeyakHwakin;

public class Yaeyak extends JFrame {

	private static final long serialVersionUID = 1L;
	private Connection conn;
	private JPanel contentPane;
	JTextArea[] fields;
	JPanel Yaeyak_panel;
	JPanel naeyuk;

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
		String[] formats = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyy년 M월 d일", "yyyy M d", "yyyyMMdd",
				"yy-MM-dd", "yy/MM/dd", "yy.MM.dd", "yyMMdd" };

		for (String fmt : formats) {
			try {
				if (fmt.startsWith("yy")) {
					int year, month, day;
					if (fmt.equals("yyMMdd")) {
						year = Integer.parseInt(inputDate.substring(0, 2));
						month = Integer.parseInt(inputDate.substring(2, 4));
						day = Integer.parseInt(inputDate.substring(4, 6));
					} else {
						String[] parts = inputDate.replace(".", "-").replace("/", "-").split("-");
						year = Integer.parseInt(parts[0]);
						month = Integer.parseInt(parts[1]);
						day = Integer.parseInt(parts[2]);
					}
					int currentYear = java.time.Year.now().getValue() % 100;
					int fullYear = (year > currentYear + 50) ? 1900 + year : 2000 + year;
					return java.sql.Date.valueOf(java.time.LocalDate.of(fullYear, month, day));
				}
				SimpleDateFormat sdf = new SimpleDateFormat(fmt);
				sdf.setLenient(false);
				Date parsed = sdf.parse(inputDate);
				return new java.sql.Date(parsed.getTime());
			} catch (Exception ignored) {
			}
		}
		throw new ParseException("지원하지 않는 날짜 형식입니다: " + inputDate, 0);
	}

	public Yaeyak() {
	}

	public Yaeyak(int rv_no, String store_name) {
		setTitle("예약 페이지");
		setBounds(100, 100, 889, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

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

		String storeImg = loadStoreImage(rv_no);
		setupImagePanel(storeImg);
		setupReservationDetails(rv_no);
		createDetailsArea(rv_no);
		setupMenuDetails(rv_no);
		setupActionButtons(rv_no);
	}

	private String loadStoreImage(int rv_no) {
		String fileName = "";
		try {
			conn = new RvListConn().getConnection();
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

	private void setupImagePanel(String fileName) {
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

	private void setupReservationDetails(int rv_no) {
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

		JTextArea textArea_info = new JTextArea();
		textArea_info.setBounds(35, 498, 430, 79);
		textArea_info.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		Yaeyak_panel.add(textArea_info);
		textArea_info.setEditable(false);
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

//	private void addPhoneFormatter(JTextArea textArea) {
//		textArea.getDocument().addDocumentListener(new DocumentListener() {
//			private boolean isUpdating = false;
//
//			private String formatPhoneNumber(String input) {
//				String digits = input.replaceAll("[^\\d]", "");
//				if (digits.length() >= 11)
//					return digits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
//				else if (digits.length() >= 10)
//					return digits.replaceFirst("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
//				return digits;
//			}
//
//			private void updatePhoneFormat() {
//				if (isUpdating)
//					return;
//				isUpdating = true;
//				String input = textArea.getText();
//				String formatted = formatPhoneNumber(input);
//				if (!input.equals(formatted)) {
//					textArea.setText(formatted);
//				}
//				isUpdating = false;
//			}
//
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				SwingUtilities.invokeLater(this::updatePhoneFormat);
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				SwingUtilities.invokeLater(this::updatePhoneFormat);
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//			}
//		});
//	}

	private void setupActionButtons(int rv_no) {
		JButton btnOK = new JButton("확인");
		btnOK.setBounds(380, 596, 85, 31);
		btnOK.setBackground(Color.white);
		Yaeyak_panel.add(btnOK);
		btnOK.addActionListener(e -> {
			JOptionPane.showMessageDialog(btnOK, "확인되었습니다.");
			dispose();
		});

		JButton btnNO = new JButton("거절");
		btnNO.setBounds(35, 596, 85, 31);
		btnNO.setBackground(Color.white);
		btnNO.addActionListener(e -> {
			try {
				conn.setAutoCommit(false);
				int choice = JOptionPane.showConfirmDialog(btnNO, "거절하시겠습니까?", "거절", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					String sql = "DELETE FROM reservation WHERE rv_no = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rv_no);
					if (pstmt.executeUpdate() > 0) {
						conn.commit();
						JOptionPane.showMessageDialog(btnNO, "예약이 거절되었습니다.");
						dispose();
					} else {
						JOptionPane.showMessageDialog(btnNO, "거절 실패: 해당 예약 정보가 없습니다.");
					}
					pstmt.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "오류: " + ex.getMessage());
			}
		});
		Yaeyak_panel.add(btnNO);
	}

	private void setupMenuDetails(int rv_no) {
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