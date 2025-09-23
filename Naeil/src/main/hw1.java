package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import user.sqlDAO2;

public class hw1 extends JFrame {

	private boolean isIdChecked = false;
	// 클래스 필드
	private boolean isPwChecked = false;

	private LineBorder panel_bb = new LineBorder(Color.BLACK, 1, true);

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_id;
	private JTextField textField_pw;
	private JTextField textField_pwc;
	private JTextField textField;
	private JTextField textField_010;
	private JTextField textField_email;

	private String name;
	private String id;
	private String pw;
	private String pwc;
	private String email;
	private String ph;

	private int type = 0;

	JRadioButton rdbtnuser = null;
	JRadioButton rdbtnbusiness = null;
	private JTextField textField_1234;
	private JTextField textField_5678;
	sqlDAO2 sqlDAO2 = new sqlDAO2();

	public void setDocumentFilter(JTextField textField, int maxLength, JTextField nextField) {
		Document doc = textField.getDocument();
		if (doc instanceof AbstractDocument) {
			((AbstractDocument) doc).setDocumentFilter(new DocumentFilter() {
				@Override
				public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
						throws BadLocationException {
					if (string != null && string.matches("[0-9]+")) {
						int currentLength = fb.getDocument().getLength();
						int newLength = currentLength + string.length();
						if (newLength <= maxLength) {
							super.insertString(fb, offset, string, attr);
							if (newLength == maxLength && nextField != null) {
								SwingUtilities.invokeLater(() -> nextField.requestFocusInWindow());
							}
						}
					}
				}

				@Override
				public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
						throws BadLocationException {
					if (text != null && text.matches("[0-9]+")) {
						int currentLength = fb.getDocument().getLength();
						int newLength = currentLength - length + text.length();
						if (newLength <= maxLength) {
							super.replace(fb, offset, length, text, attrs);
							if (newLength == maxLength && nextField != null) {
								SwingUtilities.invokeLater(() -> nextField.requestFocusInWindow());
							}
						}
					}
				}
			});
		}
	}

	// 전화번호 입력 필터 적용 (3자리, 4자리, 4자리)
	public class NumericFilter extends DocumentFilter {
		private int maxLength;

		public NumericFilter(int maxLength) {
			this.maxLength = maxLength;
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			if (string.matches("[0-9]*") && fb.getDocument().getLength() + string.length() <= maxLength) {
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			if (text.matches("[0-9]*") && fb.getDocument().getLength() + text.length() - length <= maxLength) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

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
					hw1 frame = new hw1();
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
	public hw1() throws ClassNotFoundException {

		String img_path = "/resources/log";

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(846, 1011);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 바인더 클립 사진
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(356, 0, 108, 163);
		contentPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(Log.class.getResource(img_path + "/ugip2.png")));

		JPanel panel = new JPanel();
		panel.setBounds(113, 134, 615, 796);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lbllogo = new JLabel("내일 뭐 먹지?");
		lbllogo.setIcon(new ImageIcon(hw1.class.getResource(img_path + "/logo_icon.png")));
		lbllogo.setForeground(Color.BLACK);
		lbllogo.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbllogo.setBackground(Color.WHITE);
		lbllogo.setBounds(12, 10, 161, 44);
		panel.add(lbllogo);

// 회원가입 라벨
		JLabel lblsignin = new JLabel("회원가입");
		lblsignin.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblsignin.setHorizontalAlignment(SwingConstants.CENTER);
		lblsignin.setBounds(185, 43, 225, 55);
		panel.add(lblsignin);

// 개인, 사업자 라디오 버튼 패널
		JPanel radiopanel = new JPanel();
		radiopanel.setBackground(Color.WHITE);
		radiopanel.setLayout(null);
		radiopanel.setBounds(113, 102, 397, 55);
		panel.add(radiopanel);

// 라디오 버튼 생성
		rdbtnuser = new JRadioButton("개인");
		rdbtnuser.setBackground(Color.WHITE);
		rdbtnuser.setBounds(8, 26, 121, 23);
		radiopanel.add(rdbtnuser);

		rdbtnbusiness = new JRadioButton("사업자");
		rdbtnbusiness.setBackground(Color.WHITE);
		rdbtnbusiness.setBounds(133, 26, 121, 23);
		radiopanel.add(rdbtnbusiness);

// ButtonGroup 생성 및 라디오 버튼 그룹에 추가
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnuser);
		group.add(rdbtnbusiness);

		rdbtnuser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = 2;
//				System.out.println(type);
			}
		});
		rdbtnbusiness.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = 1;
//				System.out.println(type);
			}
		});

		// 이름 패널
		JPanel namepanel = new JPanel();
		namepanel.setBackground(Color.WHITE);
		namepanel.setBounds(113, 167, 397, 81);
		panel.add(namepanel);
		namepanel.setLayout(null);

		JLabel lblname = new JLabel("*이름");
		lblname.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblname.setBounds(12, 10, 89, 29);
		namepanel.add(lblname);

		textField_name = new JTextField();
		textField_name.setBackground(Color.WHITE);
		textField_name.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textField_name.setBounds(12, 39, 252, 32);
		namepanel.add(textField_name);
		textField_name.setColumns(10);

		JPanel idpanel = new JPanel();
		idpanel.setBackground(Color.WHITE);
		idpanel.setLayout(null);
		idpanel.setBounds(113, 258, 397, 81);
		panel.add(idpanel);

		JLabel lblid = new JLabel("*아이디");
		lblid.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblid.setBounds(12, 10, 89, 29);
		idpanel.add(lblid);

		textField_id = new JTextField();
		textField_id.setBackground(Color.WHITE);
		textField_id.setFont(new Font("Gulim", Font.PLAIN, 12));
		textField_id.setColumns(10);
		textField_id.setBounds(12, 40, 252, 32);
		idpanel.add(textField_id);

		JButton btnjungbok = new JButton("중복 확인");
		btnjungbok.setBackground(Color.WHITE);
		btnjungbok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String enteredID = textField_id.getText().trim();
				// 아이디 필드가 비어있는지 확인
				if (enteredID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// 임의의 중복된 ID 예시

				try {
					if (sqlDAO2.idjungbok(enteredID) == 1) {
						JOptionPane.showMessageDialog(null, "이미 사용 중인 아이디입니다. 😢", "중복 확인",
								JOptionPane.WARNING_MESSAGE);
						isIdChecked = false; // 중복이면 false
					} else {
						JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다! 🎉", "중복 확인",
								JOptionPane.INFORMATION_MESSAGE);
						isIdChecked = true; // 사용 가능할 때 true
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnjungbok.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnjungbok.setBounds(276, 40, 89, 31);
		idpanel.add(btnjungbok);

		JPanel pwpanel = new JPanel();
		pwpanel.setBackground(Color.WHITE);
		pwpanel.setLayout(null);
		pwpanel.setBounds(113, 349, 397, 81);
		panel.add(pwpanel);

		JLabel lblNewLabel_2_1_1 = new JLabel("*비밀번호");
		lblNewLabel_2_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel_2_1_1.setBounds(12, 10, 89, 29);
		pwpanel.add(lblNewLabel_2_1_1);

		textField_pw = new JPasswordField();
		textField_pw.setBackground(Color.WHITE);
		textField_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textField_pw.setColumns(10);
		textField_pw.setBounds(12, 39, 252, 32);
		pwpanel.add(textField_pw);

		JPanel pwcpanel = new JPanel();
		pwcpanel.setBackground(Color.WHITE);
		pwcpanel.setLayout(null);
		pwcpanel.setBounds(113, 440, 397, 81);
		panel.add(pwcpanel);

		JLabel lblpwc = new JLabel("*비밀번호 확인");
		lblpwc.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblpwc.setBounds(12, 10, 100, 29);
		pwcpanel.add(lblpwc);

		textField_pwc = new JPasswordField();
		textField_pwc.setBackground(Color.WHITE);
		textField_pwc.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textField_pwc.setColumns(10);
		textField_pwc.setBounds(12, 39, 252, 32);
		pwcpanel.add(textField_pwc);

		// 휴대폰 번호
		JPanel phonenumberpanel = new JPanel();
		phonenumberpanel.setBackground(Color.WHITE);
		phonenumberpanel.setLayout(null);
		phonenumberpanel.setBounds(113, 531, 397, 81);
		panel.add(phonenumberpanel);

		JLabel lblph = new JLabel("*휴대폰 번호");
		lblph.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblph.setBounds(12, 10, 100, 29);
		phonenumberpanel.add(lblph);

		JLabel lblhi = new JLabel("-");
		lblhi.setHorizontalAlignment(SwingConstants.CENTER);
		lblhi.setBounds(88, 49, 57, 15);
		phonenumberpanel.add(lblhi);

		JLabel lblhi1 = new JLabel("-");
		lblhi1.setHorizontalAlignment(SwingConstants.CENTER);
		lblhi1.setBounds(246, 49, 57, 15);
		phonenumberpanel.add(lblhi1);

		textField_010 = new JTextField();
		textField_010.setBackground(Color.WHITE);
		textField_010.setBounds(12, 41, 70, 32);
		phonenumberpanel.add(textField_010);
		textField_010.setColumns(10);
//        textField_010.setDocument(new PlainDocument() {
//            {
//                setDocumentFilter(new NumericFilter(3));  // 3자리만 허용
//            }
//           
//        });
//        textField_1234.setDocument(new PlainDocument() {
//            {
//                setDocumentFilter(new NumericFilter(4));  // 3자리만 허용
//            }
//           
//        });
//        textField_5678.setDocument(new PlainDocument() {
//            {
//                setDocumentFilter(new NumericFilter(4));  // 3자리만 허용
//            }
//           
//        });

		textField_1234 = new JTextField();
		textField_1234.setBackground(Color.WHITE);
		textField_1234.setColumns(10);
		textField_1234.setBounds(157, 41, 70, 32);
		phonenumberpanel.add(textField_1234);

		textField_5678 = new JTextField();
		textField_5678.setBackground(Color.WHITE);
		textField_5678.setColumns(10);
		textField_5678.setBounds(315, 41, 70, 32);
		phonenumberpanel.add(textField_5678);

		// 이메일 입력
		JPanel emailpanel = new JPanel();
		emailpanel.setBackground(Color.WHITE);
		emailpanel.setLayout(null);
		emailpanel.setBounds(113, 622, 397, 81);
		panel.add(emailpanel);

		JLabel lblemail = new JLabel("*이메일");
		lblemail.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblemail.setBounds(12, 10, 100, 29);
		emailpanel.add(lblemail);

		textField_email = new JTextField();
		textField_email.setBackground(Color.WHITE);
		textField_email.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textField_email.setColumns(10);
		textField_email.setBounds(12, 39, 252, 32);
		emailpanel.add(textField_email);

		JButton btngaip = new JButton("가입하기");
		btngaip.setBackground(Color.WHITE);
		btngaip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// 가입하기 버튼 클릭 이벤트 처리
		btngaip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				name = textField_name.getText();
				id = textField_id.getText();
				pw = textField_pw.getText();
				pwc = textField_pwc.getText();
				ph = textField_010.getText() + '-' + textField_1234.getText() + '-' + textField_5678.getText();
				email = textField_email.getText();

				// 이름 입력받았는지 확인
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 이름: 한글 최소 2자 이상
				if (!name.matches("^[가-힣]{2,}$")) {
					JOptionPane.showMessageDialog(null, "이름은 한글 2자 이상 입력해주세요. 😢", "입력 오류",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 아이디 입력해주세요
				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 아이디: 영문 또는 숫자 4~12자
				if (!id.matches("^[a-zA-Z0-9]{4,12}$")) {
					JOptionPane.showMessageDialog(null, "아이디는 영문 또는 숫자로 4~12자 입력해주세요. 😢", "입력 오류",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 비밀번호 입력해주세요
				if (pw.isEmpty()) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 비밀번호: 영문 + 숫자 포함, 6자 이상
				if (!pw.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문과 숫자를 포함하여 6자 이상이어야 합니다. 😢", "입력 오류",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 전화번호 입력해주세요
				if (ph.isEmpty()) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;

				}
				// 이메일 입력해주세요
				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "이메일을 입력해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 이메일: 기본 이메일 형식 체크
				if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
					JOptionPane.showMessageDialog(null, "올바른 이메일 형식으로 입력해주세요. 😢", "입력 오류",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 회원 유형 선택 확인
				if (type == 0) {
					JOptionPane.showMessageDialog(null, "회원 유형을 선택해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				} // 아이디 중복 확인 했는지 검사
				if (!isIdChecked) {
					JOptionPane.showMessageDialog(null, "아이디 중복 확인을 해주세요. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				} // 비밀번호 확인
				if (!textField_pw.getText().equals(textField_pwc.getText())) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 😢", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}



				Connection con1 = null;
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버로딩
					// 객체.메소드(), 클래스명.스태틱 메소드()
					// DB 연결
					con1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");// 접속

					// String userType = rdbtnNewRadioButton.isSelected() ? "User" : "Admin";
					// SQL 쿼리 작성

					String sql = "INSERT INTO users (users_signum, users_name, users_id, users_password, users_phone, users_email, users_type) VALUES (users_signum_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
					PreparedStatement pstmt = con1.prepareStatement(sql);

					// 암호화
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					byte[] hashBytes = md.digest(pw.getBytes("UTF-8"));
					StringBuilder sb = new StringBuilder();
					for (byte b : hashBytes) {
						sb.append(String.format("%02x", b));
					}
					String newHashedPW = sb.toString();

					pstmt.setString(1, name);
					pstmt.setString(2, id);
					pstmt.setString(3, newHashedPW);
					pstmt.setString(4, ph);
					pstmt.setString(5, email);
					pstmt.setInt(6, type);

					// 실행
					pstmt.executeUpdate();

					// 자원 정리
					JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다! 🎉", "가입 완료", JOptionPane.INFORMATION_MESSAGE);

					pstmt.close();
					con1.close();

					dispose();

				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "DB 저장 중 오류 발생!" + ex.getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btngaip.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btngaip.setBounds(203, 713, 97, 36);
		panel.add(btngaip);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(43, 118, 755, 833);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

	}
}
