package admin.store_manage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import admin.LoadInfo;
import admin.store_menu.MenuList;
import admin.store_rv.RvList;
import admin.store_rv.RvListConn;
import main.StaticInfo;

public class StoreManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection conn;
	private File selectedImageFile = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StoreManagement frame = new StoreManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StoreManagement() {
	}

	public StoreManagement(int store_id) throws Exception {

		LoadInfo info = new LoadInfo();
		String store_name = info.loadStoreInfo(store_id)[0];
		String location = info.loadStoreInfo(store_id)[1];

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(608, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel btnBack = new JLabel();
		URL imageUrl = getClass().getResource("/resources/admin/back.png");
		if (imageUrl != null) {
			ImageIcon icon = new ImageIcon(imageUrl);
			Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			btnBack = new JLabel(new ImageIcon(scaled));
		}
		btnBack.setBounds(30, 30, 30, 30);
		contentPane.add(btnBack);

		JLabel lblStoreName = new JLabel(store_name);
		lblStoreName.setBounds(109, 10, 376, 65);
		lblStoreName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreName.setFont(new Font("굴림", Font.BOLD, 25));
		contentPane.add(lblStoreName);


		JLabel lblStoreLoc = new JLabel(location);
		lblStoreLoc.setBounds(109, 347, 376, 65);
		lblStoreLoc.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStoreLoc);

		JButton btnMenuUpdate = new JButton("메뉴 관리");
		btnMenuUpdate.setBounds(109, 422, 376, 65);
		btnMenuUpdate.setBackground(new Color(255, 255, 255));
		btnMenuUpdate.setFont(new Font("굴림", Font.BOLD, 15));
		contentPane.add(btnMenuUpdate);

		JButton btnRvList = new JButton("예약 확인");
		btnRvList.setBounds(109, 522, 376, 70);
		btnRvList.setBackground(new Color(255, 255, 255));
		btnRvList.setFont(new Font("굴림", Font.BOLD, 15));
		contentPane.add(btnRvList);

		JLabel lblProfile = new JLabel("");
		lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfile.setBounds(146, 58, 300, 300);
		contentPane.add(lblProfile);

		String newFileName = "";
		try {
			conn = new RvListConn().getConnection();
			String sql = "select store_img from store where store_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, StaticInfo.store_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				newFileName = rs.getString("store_img");
				if (newFileName == null || newFileName.isEmpty()) {
					newFileName = "/image/profile.png";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ImageIcon icon = null;
		try {
			// DB에서 가져온 경로
			File imageFile = new File("src/resources" + newFileName);
			if (imageFile.exists()) {
				icon = new ImageIcon(imageFile.getAbsolutePath());
			} else {
				// 기본 이미지 Fallback
				File defaultImg = new File("src/resources/admin/profile.png");
				if (defaultImg.exists()) {
					icon = new ImageIcon(defaultImg.getAbsolutePath());
				} else {
					System.err.println("기본 이미지도 없습니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (icon != null) {
			Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
			lblProfile.setIcon(new ImageIcon(img));
		}

		// 이벤트 핸들러
		// 뒤로가기 버튼
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new StoreMenu().setVisible(true);
			}
		});

		// 메뉴관리
		btnMenuUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuList(store_id).setVisible(true);
			}
		});

		// 예약목록 확인
		btnRvList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RvList(store_id).setVisible(true);
			}
		});
	}

}
