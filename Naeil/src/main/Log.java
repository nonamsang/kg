package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import admin.store_manage.StoreMenu;
import admin.store_rv.RvListConn;
import user.Menu2;

public class Log extends JFrame {

	private Connection conn;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTextField idField;
	private JPasswordField pwField;

	/**
	 * Launch the application.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Log frame = new Log();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws ClassNotFoundException
	 */
	public Log() throws ClassNotFoundException {
		String img_path = "/resources/log";

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 150, 624, 707);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 바인더 클립 사진
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(247, 0, 108, 173);
		lblNewLabel.setIcon(new ImageIcon(Log.class.getResource(img_path + "/ugip2.png")));
		contentPane.add(lblNewLabel);


		JPanel panel = new JPanel();
		panel.setBackground(new Color(229, 229, 229));
		panel.setBounds(12, 119, 584, 539);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(56, 34, 470, 470);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblLog = new JLabel("Login");
		lblLog.setBounds(122, 48, 225, 55);
		panel_1.add(lblLog);
		lblLog.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblLog.setHorizontalAlignment(SwingConstants.CENTER);

		// ID 입력 패널
		JPanel idPanel = new JPanel();
		idPanel.setBackground(Color.WHITE);
		idPanel.setBounds(37, 120, 409, 81);
		panel_1.add(idPanel);
		idPanel.setLayout(null);

		idField = new JTextField("User ID");
		idField.setBackground(new Color(229, 229, 229));

		// 회색 글씨 표시
		idField.setForeground(Color.GRAY);
		// 포커스 이벤트 추가
		idField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (idField.getText().equals("User ID")) {
					idField.setText("");
				}
				idField.setForeground(Color.BLACK);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (idField.getText().isEmpty()) {
					idField.setText("User ID");
					idField.setForeground(Color.GRAY);
				}
			}

		});

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(309, 39, 32, 32);
		panel_2.setBackground(Color.LIGHT_GRAY);
		idPanel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblPw_1 = new JLabel("");
		lblPw_1.setIcon(new ImageIcon(Log.class.getResource(img_path + "/id_icon.png")));
		lblPw_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblPw_1.setBounds(0, 0, 32, 32);
		panel_2.add(lblPw_1);

		idField.setBounds(54, 39, 256, 32);
		idPanel.add(idField);
		idField.setColumns(10);

		// 비밀번호 입력 패널
		JPanel pwPanel = new JPanel();
		pwPanel.setBackground(Color.WHITE);
		pwPanel.setBounds(37, 171, 409, 81);
		panel_1.add(pwPanel);
		pwPanel.setLayout(null);

		pwField = new JPasswordField("Password");
		pwField.setBackground(new Color(229, 229, 229));

		// 🔹 placeholder 상태일 땐 마스킹 제거 + 회색
		pwField.setEchoChar((char) 0);
		pwField.setForeground(Color.GRAY);

		pwField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String pwd = new String(pwField.getPassword());
				if (pwd.equals("Password")) {
					pwField.setText("");
					pwField.setEchoChar('●'); // 입력 시 마스킹
					pwField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				String pwd = new String(pwField.getPassword());
				if (pwd.isEmpty()) {
					pwField.setText("Password");
					pwField.setEchoChar((char) 0); // placeholder는 마스킹 제거
					pwField.setForeground(Color.GRAY);
				}
			}
		});
		pwField.setColumns(10);
		pwField.setBounds(55, 39, 256, 32);
		pwPanel.add(pwField);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(310, 39, 32, 32);
		panel_3.setBackground(Color.LIGHT_GRAY);
		pwPanel.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblPw = new JLabel("");
		lblPw.setBackground(Color.LIGHT_GRAY);
		lblPw.setBounds(0, 0, 32, 32);
		panel_3.add(lblPw);
		lblPw.setIcon(new ImageIcon(Log.class.getResource(img_path + "/pw_icon.png")));
		lblPw.setFont(new Font("맑은 고딕", Font.BOLD, 14));

		// 버튼 패널 설정
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBounds(37, 304, 409, 55);
		panel_1.add(btnPanel);
		btnPanel.setLayout(null);

		JButton btnFindId = new JButton("ID 찾기");
		btnFindId.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		btnFindId.setBounds(12, 10, 97, 23);
		btnFindId.setFocusPainted(false); // 포커스 안보이게
		btnFindId.setBorderPainted(false); // 윤곽선 안보이게
		btnFindId.setContentAreaFilled(false); // 배경 안보이게
		btnPanel.add(btnFindId);

		JButton btnResetPw = new JButton("비밀번호 변경");
		btnResetPw.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		btnResetPw.setBounds(140, 10, 124, 23);
		btnResetPw.setFocusPainted(false); // 포커스 안보이게
		btnResetPw.setBorderPainted(false); // 윤곽선 안보이게
		btnResetPw.setContentAreaFilled(false); // 배경 안보이게
		btnPanel.add(btnResetPw);

		JButton btnGaip = new JButton("회원가입");
		btnGaip.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		btnGaip.setBounds(294, 10, 86, 23);
		btnGaip.setFocusPainted(false); // 포커스 안보이게
		btnGaip.setBorderPainted(false); // 윤곽선 안보이게
		btnGaip.setContentAreaFilled(false); // 배경 안보이게
		btnPanel.add(btnGaip);

		JLabel lblNewLabel_1 = new JLabel("|");
		lblNewLabel_1.setBounds(118, 11, 22, 23);
		btnPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("|");
		lblNewLabel_1_1.setBounds(269, 11, 32, 23);
		btnPanel.add(lblNewLabel_1_1);

		// 로그인 버튼 패널
		JPanel logPanel = new JPanel();
		logPanel.setBounds(92, 262, 292, 32);
		panel_1.add(logPanel);
		logPanel.setLayout(null);

		JButton btnLog = new JButton("LOGIN");
		btnLog.setFont(new Font("돋움", Font.PLAIN, 15));
		btnLog.setBounds(0, 0, 292, 32);
		btnLog.setBackground(Color.white);
		logPanel.add(btnLog);

		// 이벤트 핸들러
		// 로그인 버튼
		btnLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		// 아이디 찾기 버튼
		btnFindId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				try {
					conn = new RvListConn().getConnection();

					String inputName = null;
					String inputEmail = null;
					boolean nameValid = false;
					boolean emailValid = false;

					// 1️⃣ 이름 확인 루프
					while (!nameValid) {
						inputName = JOptionPane.showInputDialog("성함을 입력해 주세요:");
						if (inputName == null)
							return; // 취소 시 종료

						String nameCheckSql = "SELECT * FROM users WHERE users_name = ?";
						PreparedStatement nameStmt = conn.prepareStatement(nameCheckSql);
						nameStmt.setString(1, inputName.trim());
						ResultSet nameRs = nameStmt.executeQuery();

						if (nameRs.next()) {
							nameValid = true;
						} else {
							JOptionPane.showMessageDialog(null, "존재하지 않는 이름입니다. 다시 입력해 주세요.");
						}
					}

					// 2️⃣ 이메일 확인 루프
					while (!emailValid) {
						inputEmail = JOptionPane.showInputDialog("이메일을 입력해 주세요:");
						if (inputEmail == null)
							return; // 취소 시 종료

						String emailCheckSql = "SELECT * FROM users WHERE users_name = ? AND users_email = ?";
						PreparedStatement emailStmt = conn.prepareStatement(emailCheckSql);
						emailStmt.setString(1, inputName.trim());
						emailStmt.setString(2, inputEmail.trim());
						ResultSet emailRs = emailStmt.executeQuery();

						if (emailRs.next()) {
							String userID = emailRs.getString("users_id");
							JOptionPane.showMessageDialog(btnFindId, inputName + "님 ID는 " + userID + " 입니다.");
							emailValid = true;
						} else {
							JOptionPane.showMessageDialog(null, "이메일이 일치하지 않습니다. 다시 입력해 주세요.");
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
				}
			}
		});

		// 패스워드 변경버튼
		btnResetPw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;

				try {
					conn = new RvListConn().getConnection();

					String userId = null;
					String userEmail = null;
					String oldHashedPW = null;

					// 1️⃣ 아이디 확인 루프
					while (true) {
						userId = JOptionPane.showInputDialog("아이디를 입력해 주세요.");
						if (userId == null)
							return;

						String idCheckSql = "SELECT * FROM users WHERE users_id = ?";
						try (PreparedStatement idCheckStmt = conn.prepareStatement(idCheckSql)) {
							idCheckStmt.setString(1, userId.trim());
							ResultSet idRs = idCheckStmt.executeQuery();

							if (idRs.next()) {
								break;
							} else {
								JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다. 다시 입력해 주세요.");
							}
						}
					}

					// 2️⃣ 이메일 확인 및 기존 해시 비밀번호 가져오기
					while (true) {
						userEmail = JOptionPane.showInputDialog("이메일을 입력해 주세요.");
						if (userEmail == null)
							return;

						String emailCheckSql = "SELECT * FROM users WHERE users_id = ? AND users_email = ?";
						try (PreparedStatement emailCheckStmt = conn.prepareStatement(emailCheckSql)) {
							emailCheckStmt.setString(1, userId.trim());
							emailCheckStmt.setString(2, userEmail.trim());
							ResultSet emailRs = emailCheckStmt.executeQuery();

							if (emailRs.next()) {
								oldHashedPW = emailRs.getString("users_password");
								break;
							} else {
								JOptionPane.showMessageDialog(null, "이메일이 일치하지 않습니다. 다시 입력해 주세요.");
							}
						}
					}

					// 3️⃣ 비밀번호 입력 및 확인 루프
					String newPW = "", confirmPW = "", hashedPW = "";

					while (true) {
						// 새 비밀번호 입력
						JPasswordField passwordField1 = new JPasswordField();
						int option1 = JOptionPane.showConfirmDialog(null, passwordField1, "새 비밀번호를 입력해 주세요.",
								JOptionPane.OK_CANCEL_OPTION);
						if (option1 != JOptionPane.OK_OPTION)
							return;

						newPW = new String(passwordField1.getPassword()).trim();
						if (newPW.isEmpty()) {
							JOptionPane.showMessageDialog(null, "새 비밀번호를 입력해 주세요.");
							continue;
						}

						// 암호화
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						byte[] hashBytes = md.digest(newPW.getBytes("UTF-8"));
						StringBuilder sb = new StringBuilder();
						for (byte b : hashBytes) {
							sb.append(String.format("%02x", b));
						}
						String newHashedPW = sb.toString();

						// 기존 비밀번호와 같은지 확인
						if (newHashedPW.equals(oldHashedPW)) {
							JOptionPane.showMessageDialog(null, "기존 비밀번호와 동일합니다. 다른 비밀번호를 입력해 주세요.");
							continue;
						}

						// 비밀번호 확인 입력
						JPasswordField passwordField2 = new JPasswordField();
						int option2 = JOptionPane.showConfirmDialog(null, passwordField2, "새 비밀번호를 한 번 더 입력해 주세요.",
								JOptionPane.OK_CANCEL_OPTION);
						if (option2 != JOptionPane.OK_OPTION)
							return;

						confirmPW = new String(passwordField2.getPassword()).trim();
						if (confirmPW.isEmpty()) {
							JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해 주세요.");
							continue;
						}

						if (!newPW.equals(confirmPW)) {
							JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
							continue;
						}

						hashedPW = newHashedPW;
						break;
					}

					// 4️⃣ 비밀번호 업데이트
					String updateSql = "UPDATE users SET users_password = ? WHERE users_id = ?";
					try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
						updateStmt.setString(1, hashedPW);
						updateStmt.setString(2, userId);

						int result = updateStmt.executeUpdate();
						if (result > 0) {
							JOptionPane.showMessageDialog(null, "비밀번호가 성공적으로 변경되었습니다.");
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호 변경에 실패했습니다.");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
				}
			}
		});

		// 회원가입 버튼
		btnGaip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new hw1().setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// ID 입력 필드에서 엔터
		idField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		// 비밀번호 입력 필드에서 엔터
		pwField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

	}

	private void login() {
		try {
			conn = new RvListConn().getConnection();

			String inputId = idField.getText().trim();
			String inputPw = new String(pwField.getPassword()).trim();

			// SHA-256 해시
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(inputPw.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			String hashedInputPw = sb.toString();

			// 로그인 쿼리 (해시된 비밀번호 사용)
			String sql = "SELECT * FROM users WHERE users_id = ? AND users_password = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, inputId);
				pstmt.setString(2, hashedInputPw);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					// 사용자 이름 가져오기
					String userName = rs.getString("users_name");

					// 로그인 성공 메시지에 이름 포함
					JOptionPane.showMessageDialog(null, userName + "님, 환영합니다!");

					StaticInfo.statUserName = userName;
					StaticInfo.statUserId = inputId;
					StaticInfo.statUserNo = rs.getInt("users_signum");
					StaticInfo.statUserPhone = rs.getString("users_phone");

					int userType = rs.getInt("users_type");

					if (userType == 1) {
						// 사업자
						// 사업자 메인화면으로 가는 코드

						new StoreMenu().setVisible(true);
						dispose();
					} else if (userType == 2) {
						// 사용자
						// 사용자 메인화면으로 가는 코드
						new Menu2().setVisible(true);
						dispose();
					}

				} else {
					// 로그인 실패
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "DB 오류: " + ex.getMessage());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
