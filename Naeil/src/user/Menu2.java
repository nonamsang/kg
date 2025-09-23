package user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import user.InfoTable.StoreInfo;

public class Menu2 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String food = "";
	int sum = 0;
	static int storeId = 1;

	private JPanel contentPane;
	private JScrollPane storeScrollPane; // 클래스 필드로 선언

	private JPanel createStore(StoreInfo store) {

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(360, 180)); // 고정 너비 설정
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180)); // 고정 높이, 너비는 부모 따라가게
		GridBagConstraints gbc;

		JLabel imgLabel = new JLabel();
//		ImageIcon icon = new ImageIcon(StoreMenu.class.getResource("/resources" + store.img));
		ImageIcon icon = new ImageIcon(new File("src/resources" + store.img).getAbsolutePath());
		Image img = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setPreferredSize(new Dimension(160, 160));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 0.0;
		panel.add(imgLabel, gbc);

		JLabel lblNewLabel_1 = new JLabel(store.name);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(lblNewLabel_1, gbc);

		JLabel lblNewLabel_2 = new JLabel("<html><div style='width:170px;'>" + store.des + "</div></html>");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(lblNewLabel_2, gbc);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				storeId = store.id;
				new StoreMenu().setVisible(true);

			}
		});

		return panel;

	}

	private void updateStore() {
		ArrayList<StoreInfo> items = sqlDAO2.getStore();
		JPanel storePanel = createStorePanel(items, false);
		updateScrollPane(storePanel);
	}

	private void updateStore(String store_name) {
		ArrayList<StoreInfo> items = sqlDAO2.getStore(store_name);
		boolean showNoResult = items.isEmpty();
		JPanel storePanel = createStorePanel(items, showNoResult);
		updateScrollPane(storePanel);
	}

	private JPanel createStorePanel(ArrayList<StoreInfo> items, boolean showNoResult) {
		JPanel panel = new JPanel(new GridBagLayout());

		if (showNoResult) {
			JLabel noResult = new JLabel("검색된 가게가 없습니다.");
			noResult.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			noResult.setHorizontalAlignment(SwingConstants.CENTER);
			noResult.setPreferredSize(new Dimension(700, 100));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(20, 0, 20, 0);
			gbc.anchor = GridBagConstraints.CENTER;

			panel.add(noResult, gbc);
		} else {
			int row = 0, col = 0;
			for (StoreInfo item : items) {
				JPanel storeItem = createStore(item);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = col;
				gbc.gridy = row;
				gbc.insets = new Insets(0, 0, 0, 0);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;

				panel.add(storeItem, gbc);

				if (++col > 1) {
					col = 0;
					row++;
				}
			}
		}

		return panel;
	}

	private void updateScrollPane(JPanel panel) {
		storeScrollPane.setViewportView(panel);
		storeScrollPane.revalidate();
		storeScrollPane.repaint();
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Menu2 frame = new Menu2();
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

	public Menu2() {
		String img_path = "/resources/menu2";

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 898);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(null));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 기본 빈 패널로 초기 세팅
		JPanel initialStorePanel = new JPanel(new GridBagLayout());
		storeScrollPane = new JScrollPane(initialStorePanel);
		storeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		storeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ⬅ 추가
		storeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		storeScrollPane.setBackground(Color.LIGHT_GRAY);
		storeScrollPane.setBounds(74, 183, 763, 515);
		contentPane.add(storeScrollPane);

		updateStore();

//		JPanel panel_2 = new JPanel();
//		panel_2.setBackground(Color.WHITE);
//		panel_2.setBorder(panel_bb);
//		panel_2.setBounds(22, 636, 786, 74);
//		contentPane.add(panel_2);
//		panel_2.setLayout(null);

		JButton btnHome = new JButton();
		btnHome.setBorderPainted(false);
		btnHome.setContentAreaFilled(false);
		btnHome.setBackground(Color.WHITE);
		btnHome.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/imgHome.png")));
		btnHome.setBounds(101, 764, 48, 48);
		contentPane.add(btnHome);

		JButton btnSearch = new JButton();
		btnSearch.setBorderPainted(false); // 윤곽선 안보이게
		btnSearch.setContentAreaFilled(false); // 배경 안보이게
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/imgSearch.png")));
		btnSearch.setBounds(825, 10, 32, 32);
		contentPane.add(btnSearch);

		JButton btnMyReserv = new JButton();
		btnMyReserv.setBorderPainted(false); // 윤곽선 안보이게
		btnMyReserv.setContentAreaFilled(false); // 배경 안보이게
		btnMyReserv.setBackground(Color.WHITE);
		btnMyReserv.setIcon(
				new ImageIcon(Menu2.class.getResource(img_path + "/imgUserResrv.png")));
		btnMyReserv.setBounds(179, 764, 48, 48);
		contentPane.add(btnMyReserv);

		JButton btnMyPage = new JButton();
		btnMyPage.setBorderPainted(false); // 윤곽선 안보이게
		btnMyPage.setContentAreaFilled(false); // 배경 안보이게
		btnMyPage.setBackground(Color.WHITE);
		btnMyPage.setIcon(
				new ImageIcon(Menu2.class.getResource(img_path + "/imgMyPage.png")));
		btnMyPage.setBounds(776, 764, 48, 48);
		contentPane.add(btnMyPage);

		JLabel lblbutton_spoon = new JLabel();
		lblbutton_spoon.setPreferredSize(new Dimension(160, 160));
		lblbutton_spoon.setBackground(new Color(229, 229, 229));
		lblbutton_spoon.setOpaque(false);
		ImageIcon icon = new ImageIcon(Menu2.class.getResource(img_path + "/spoon.png"));
		Image spoonimg = icon.getImage().getScaledInstance(828, 200, Image.SCALE_SMOOTH);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/logo_icon.png")));
		logo.setBounds(22, 10, 32, 32);
		contentPane.add(logo);

		JLabel logoname = new JLabel("내일 뭐먹지?");
		logoname.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		logoname.setBounds(56, 10, 93, 32);
		contentPane.add(logoname);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(141, 61, 559, 121);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblchar = new JLabel("");
		lblchar.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/캐릭터.png")));
		lblchar.setBounds(0, 0, 559, 130);
		panel_2.add(lblchar);

		JLabel lblchopsticks_left = new JLabel("");
		lblchopsticks_left.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/젓가락.png")));
		lblchopsticks_left.setBounds(694, 67, 43, 164);
		contentPane.add(lblchopsticks_left);

		JLabel lblchopsticks_right = new JLabel("");
		lblchopsticks_right.setIcon(new ImageIcon(Menu2.class.getResource(img_path + "/젓가락2.png")));
		lblchopsticks_right.setBounds(722, 56, 86, 164);
		contentPane.add(lblchopsticks_right);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.setBackground(new Color(255, 255, 255));
		panel_8.setBounds(0, 0, 906, 51);
		contentPane.add(panel_8);

		ImageIcon icon2 = new ImageIcon(Menu2.class.getResource(img_path + "/접시10.png"));
		Image spoonimg2 = icon2.getImage().getScaledInstance(858, 626, Image.SCALE_SMOOTH);

		JLabel Imgdish = new JLabel();
		Imgdish.setIcon(new ImageIcon(spoonimg2));
		Imgdish.setPreferredSize(new Dimension(800, 598));
		Imgdish.setBounds(22, 117, 858, 626);
		contentPane.add(Imgdish);

		JLabel Imgspoon = new JLabel();
		Imgspoon.setIcon(new ImageIcon(spoonimg));
		Imgspoon.setPreferredSize(new Dimension(800, 200));
		Imgspoon.setBounds(37, 725, 828, 124);
		contentPane.add(Imgspoon);

		// 이벤트 핸들러
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStore();
			}
		});

		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String store_name = JOptionPane.showInputDialog("찾으시는 가게가 있으신가요?");
				if (!"".equals(store_name) && store_name != null) {
					updateStore(store_name);
				} else {
					updateStore();
				}
			}
		});

		btnMyReserv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new UserRvList().setVisible(true);
			}
		});

		btnMyPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new MyPage().setVisible(true);
				dispose();
			}
		});

	}
}
