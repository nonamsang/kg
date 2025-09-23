package admin.store_manage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Log;
import main.StaticInfo;

public class StoreMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static int Upup = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StoreMenu frame = new StoreMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StoreMenu() {
		StoreMenu sm = this;
		String img_path = "/resources/admin";

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(608, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHello = new JLabel();
		lblHello.setBounds(267, 20, 80, 40);
		lblHello.setText("Hello~");
		lblHello.setHorizontalAlignment(SwingConstants.CENTER);
		lblHello.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		contentPane.add(lblHello);

		JLabel lblProfile = new JLabel();
		URL imageUrl = getClass().getResource(img_path + "/profile.png");
		if (imageUrl != null) {
			ImageIcon icon = new ImageIcon(imageUrl);
			Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			lblProfile = new JLabel(new ImageIcon(scaled));
		}
		lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfile.setBounds(225, 51, 150, 150);
		contentPane.add(lblProfile);

		// 사업자 이름 띄워주는 라벨
		JLabel lblUserName = new JLabel();
		lblUserName.setBounds(185, 200, 245, 40);
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblUserName.setText(StaticInfo.statUserName + " 님 어서오세요");
		contentPane.add(lblUserName);

		JButton btnSelectStore = new JButton("  가게 선택");
		btnSelectStore.setBounds(119, 277, 376, 55);
		btnSelectStore.setBackground(new Color(255, 255, 255));
		btnSelectStore.setIcon(new ImageIcon(StoreMenu.class.getResource(img_path + "/search.png")));
		btnSelectStore.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(btnSelectStore);

		JButton btnAddStore = new JButton("  가게 추가");
		btnAddStore.setBounds(119, 377, 376, 55);
		btnAddStore.setIcon(new ImageIcon(StoreMenu.class.getResource(img_path + "/plus.png")));
		btnAddStore.setBackground(new Color(255, 255, 255));
		btnAddStore.setFont(new Font("굴림", Font.BOLD, 15));
		contentPane.add(btnAddStore);

		JButton btnUpdateStore = new JButton("  가게 수정");
		btnUpdateStore.setBounds(119, 477, 376, 55);
		btnUpdateStore.setBackground(new Color(255, 255, 255));
		btnUpdateStore.setIcon(new ImageIcon(StoreMenu.class.getResource(img_path + "/edit.png")));
		btnUpdateStore.setFont(new Font("굴림", Font.BOLD, 15));
		contentPane.add(btnUpdateStore);

		JButton btnLogout = new JButton("로그아웃");
		btnLogout.setBounds(268, 550, 75, 23);
		btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		btnLogout.setBackground(new Color(255, 255, 255));
		contentPane.add(btnLogout);

		// 이벤트 핸들러
		// 로그아웃
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StaticInfo.statUserId = null;
				StaticInfo.statUserName = null;
				try {
					new Log().setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		// 가게 선택
		btnSelectStore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new StoreSelect(sm).setVisible(true);
			}
		});

		btnAddStore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new StoreInsert().setVisible(true);
			}
		});

		btnUpdateStore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Upup = 1;
				new StoreSelect(sm).setVisible(true);
			}
		});
	}

}
