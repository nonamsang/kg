package user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import user.InfoTable.StoreInfo;

class WhiteLabel extends JLabel {
    public WhiteLabel(String text) {
        super(text);
        setOpaque(true);
        setBackground(Color.white);
    }
}

public class StoreMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel main2;
	private JPanel menu;
	int row2 = 1;
	private Map<MenuItemInfo, JPanel> addedPanels = new HashMap<>();
	int result_price;
	JLabel result_price_label = new WhiteLabel("총 " + result_price + "원");
	
	JPanel jumun = new JPanel();
	
	
	sqlDAO2 dao = new sqlDAO2();
	List<OrderItem> items = new ArrayList<>();
	public static List<OrderItem> orderItems = new ArrayList<>();

	private JPanel createMenuItem(MenuItemInfo item) {

		int itemcount = item.count;
		JLabel count_right = new WhiteLabel(String.valueOf(itemcount));
		JLabel price_right = new WhiteLabel("가격: " + item.price);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		GridBagConstraints gbc;

		JLabel imgLabel = new JLabel();
		ImageIcon icon = new ImageIcon(StoreMenu.class.getResource("/resources" + item.img));
		Image img = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setPreferredSize(new Dimension(160, 160));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 3;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 0.0;
		panel.add(imgLabel, gbc);

		JLabel nameLabel = new WhiteLabel(item.name);
		nameLabel.setFont(new Font("굴림", Font.BOLD, 18));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; // 남는 공간 차지
		panel.add(nameLabel, gbc);

		if (item.des == null)
			item.des = "";
		JLabel descLabel = new WhiteLabel("<html><div style='width:150px;'>" + item.des + "</div></html>");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(descLabel, gbc);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		bottomPanel.setBackground(Color.white);
		bottomPanel.add(new JLabel("가격: " + item.price + "원"));

		JButton reserveBtn = new JButton("예약");
		reserveBtn.setBackground(Color.white);
		reserveBtn.addActionListener(e -> {

			if (addedPanels.containsKey(item)) {
				item.count += 1;
				count_right.setText(String.valueOf(item.count)); // 숫자만 남기고 모두 제거
				try {
					int totalPrice = item.price * item.count;
					price_right.setText("가격: " + totalPrice + "원"); // 새로운 가격 표시
					result_price = item.price + result_price;
					result_price_label.setText("총: " + result_price + "원");
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}

			} else {

				item.count = 1;
				result_price = item.price + result_price;
				result_price_label.setText("총: " + result_price + "원");

				GridBagLayout layout = new GridBagLayout();
				layout.columnWidths = new int[] { 100, 100, 50, 50, 50 };
				layout.rowHeights = new int[] { 40, 40, 40 };
				layout.columnWeights = new double[] { 0, 0, 0, 0, 0 };
				layout.rowWeights = new double[] { 0, 0, 0 };

				JPanel panel_right = new JPanel(layout);
				panel_right.setBackground(Color.white);
				panel_right.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				panel_right.setPreferredSize(new Dimension(350, 120));
				panel_right.setMaximumSize(new Dimension(350, 120));
				panel_right.setMinimumSize(new Dimension(350, 120));
				GridBagConstraints gbc2;

				JLabel imgLabel_right = new JLabel();
				ImageIcon icon2 = new ImageIcon(StoreMenu.class.getResource("/resources" + item.img));
				Image img2 = icon2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				imgLabel_right.setIcon(new ImageIcon(img2));
				imgLabel_right.setPreferredSize(new Dimension(100, 100));

				JLabel nameLabel_right = new WhiteLabel(item.name);
				nameLabel_right.setFont(new Font("굴림", Font.BOLD, 22));

				JLabel counttext_right = new WhiteLabel("수량: ");
				counttext_right.setFont(new Font("굴림", Font.BOLD, 18));

				count_right.setText(String.valueOf(item.count)); // 숫자만 남기고 모두 제거
				try {
					int totalPrice = item.price * item.count;
					price_right.setText("가격: " + totalPrice + "원"); //새로운 가격 표시
				} catch (NumberFormatException ex) {
					// 예외 처리: 숫자로 변환할 수 없을 경우 처리
					ex.printStackTrace();
				}
				counttext_right.setFont(new Font("굴림", Font.BOLD, 18));

				counttext_right.setFont(new Font("굴림", Font.BOLD, 18));

				JButton countUpBtn = new JButton("+");
				countUpBtn.setBackground(Color.white);
				countUpBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						item.count += 1;
						count_right.setText(String.valueOf(item.count));
						try {
							int totalPrice = item.price * item.count;
							price_right.setText("가격: " + totalPrice + "원"); // 새로운 가격 표시
							result_price = item.price + result_price;
							result_price_label.setText("총: " + result_price + "원");
						} catch (NumberFormatException ex) {
							// 예외 처리: 숫자로 변환할 수 없을 경우 처리
							ex.printStackTrace();
						}
					}
				});

				JButton countDownBtn = new JButton("-");
				countDownBtn.setBackground(Color.white);
				countDownBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (item.count <= 1) {
							result_price = result_price - item.price;
							result_price_label.setText("총: " + result_price + "원");
							main2.remove(panel_right);
							addedPanels.remove(item);
							main2.revalidate();
							main2.repaint();
						} else {
							item.count -= 1;
							count_right.setText(String.valueOf(item.count));
							try {
								int totalPrice = item.price * item.count;
								price_right.setText("가격: " + totalPrice + "원"); // 새로운 가격 표시
								result_price = result_price - item.price;
								result_price_label.setText("총: " + result_price + "원");
							} catch (NumberFormatException ex) {
								// 예외 처리: 숫자로 변환할 수 없을 경우 처리
								ex.printStackTrace();
							}
						}
					}
				});

				JButton delBtn = new JButton("취소");
				delBtn.setBackground(Color.white);
				delBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						main2.remove(panel_right);
						addedPanels.remove(item);
						result_price = result_price - item.price * item.count;
						result_price_label.setText("총: " + result_price + "원");
						item.count = 0;
						main2.revalidate();
						main2.repaint();
					}
				});

				gbc2 = new GridBagConstraints();
				gbc2.gridx = 0;
				gbc2.gridy = 0;
				gbc2.gridheight = 3;
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.insets = new Insets(0, 0, 0, 0);
				gbc2.weightx = 0.0;
				gbc2.insets = new Insets(0, 5, 0, 0); // 위 0, 왼쪽 5, 아래 0, 오른쪽
				panel_right.add(imgLabel_right, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 1;
				gbc2.gridy = 0;
				gbc2.gridwidth = 3;
				panel_right.add(nameLabel_right, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 1;
				gbc2.gridy = 1;
				panel_right.add(counttext_right, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 2;
				gbc2.gridy = 1;
				panel_right.add(countUpBtn, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 3;
				gbc2.gridy = 1;
				gbc2.anchor = GridBagConstraints.CENTER; // 가운데 정렬
				panel_right.add(count_right, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 4;
				gbc2.gridy = 1;
				panel_right.add(countDownBtn, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 1;
				gbc2.gridy = 2;
				gbc2.gridwidth = 2;
				panel_right.add(price_right, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.fill = GridBagConstraints.NONE;
				gbc2.gridx = 3;
				gbc2.gridy = 2;
				gbc2.gridwidth = 2;
				panel_right.add(delBtn, gbc2);

				gbc2 = new GridBagConstraints();
				gbc2.anchor = GridBagConstraints.WEST;
				gbc2.weightx = 0.0;
				gbc2.gridx = 0;
				gbc2.gridy = row2;
				gbc2.anchor = GridBagConstraints.NORTHWEST; // ← 왼쪽 위로 정렬
				gbc2.fill = GridBagConstraints.NONE;

				main2.add(panel_right, gbc2);
				row2 = row2 + 1;

				addedPanels.put(item, panel);

				main2.revalidate();
				main2.repaint();
			}
		});
		bottomPanel.add(reserveBtn);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(bottomPanel, gbc);

		return panel;

	}

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

		setAlwaysOnTop(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1100, 1850));
		pack();

		// 원하는 가게 ID를 전달해서 메뉴 리스트를 가져옴

		ArrayList<MenuItemInfo> items = dao.getMenuByStoreId(Menu2.storeId);
		StoreInfo store = dao.getStoreBystoreId(Menu2.storeId);

		setSize(1170, 1050);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setBackground(Color.white);

		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 700, 350 };
		gbl_contentPane.rowHeights = new int[] { 120, 360, 450, 120 };
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);
		
		//여기에 로고 367
		JLabel logo = new JLabel("             What's up!");
		logo.setFont(new Font("굴림", Font.BOLD, 36));
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 40, 0, 5);
		gbc_logo.anchor = GridBagConstraints.WEST;
		gbc_logo.gridx = 0;
		gbc_logo.gridy = 0;
		
		contentPane.add(logo, gbc_logo);

		ImageIcon originalIcon3 = new ImageIcon(StoreMenu.class.getResource("/resources/menu2/big_logo_icon.png"));

		// 2. 이미지 추출 후 리사이즈
		Image originalImg3 = originalIcon3.getImage();
		Image resizedImg3 = originalImg3.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 원하는 크기로 설정

		// 3. 리사이즈된 이미지로 아이콘 생성
		ImageIcon resizedIcon3 = new ImageIcon(resizedImg3);
		
		

		// 4. JLabel에 적용

		logo.setIcon(resizedIcon3);

		JPanel main = new JPanel();
		main.setBackground(Color.white);
		GridBagLayout gbl_main = new GridBagLayout();
		gbl_main.columnWidths = new int[] { 300, 400 };
		gbl_main.rowHeights = new int[] { 360 };

		JPanel menu = new JPanel();
		GridBagLayout gbl_menu = new GridBagLayout();
		gbl_menu.columnWidths = new int[] { 350, 350 };
		gbl_menu.rowHeights = new int[] { 169, 169, 169, 169, 169, 169 };
		gbl_menu.columnWeights = new double[] { 1.0, 0.0 };
		gbl_menu.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		menu.setLayout(gbl_menu);

		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new GridBagLayout());

		GridBagConstraints gbc_main = new GridBagConstraints();
		gbc_main.gridx = 0;
		gbc_main.gridy = 0;
		gbc_main.fill = GridBagConstraints.HORIZONTAL;
		gbc_main.anchor = GridBagConstraints.NORTH;
		gbc_main.weightx = 1.0;
		mainContainer.add(main, gbc_main);

		// menu를 mainContainer에 추가
		GridBagConstraints gbc_menu = new GridBagConstraints();
		gbc_menu.gridx = 0;
		gbc_menu.gridy = 1;
		gbc_menu.gridheight = 2;
		gbc_menu.fill = GridBagConstraints.BOTH;
		gbc_menu.weightx = 1.0;
		gbc_menu.weighty = 1.0;
		mainContainer.add(menu, gbc_menu);

		JScrollPane scrollPane = new JScrollPane(mainContainer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 향상

		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 1;
		gbc_scroll.gridheight = 3;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.weightx = 1.0;
		gbc_scroll.weighty = 1.0;
		contentPane.add(scrollPane, gbc_scroll);

		main2 = new JPanel();
		main2.setBackground(Color.white);
		main2.setLayout(new GridBagLayout());
		GridBagConstraints gbc_main2 = new GridBagConstraints();
		gbc_main2.anchor = GridBagConstraints.NORTH;
		gbc_main2.fill = GridBagConstraints.HORIZONTAL;
		gbc_main2.insets = new Insets(0, 0, 5, 0);
		gbc_main2.gridx = 1;
		gbc_main2.gridy = 0;
		gbc_main2.gridheight = 4;
		gbc_main2.weightx = 1.0;
		gbc_main2.weighty = 1.0;
		
		contentPane.add(main2, gbc_main2);
		
		JLabel myorderLabel = new WhiteLabel("<html>My<br>Order</html>");
		myorderLabel.setFont(new Font("굴림", Font.BOLD, 36));
		
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.weightx = 0.0;
		gbc2.anchor = GridBagConstraints.CENTER; // 가운데 정렬
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.insets = new Insets(120, 0, 0, 0);
		gbc2.anchor = GridBagConstraints.NORTHWEST; // ← 왼쪽 위로 정렬
		gbc2.fill = GridBagConstraints.NONE;

		main2.add(myorderLabel, gbc2);

		jumun.setLayout(new GridBagLayout());
		jumun.setBackground(Color.white);
		GridBagConstraints gbc_jumun = new GridBagConstraints();
		gbc_jumun.gridx = 1;
		gbc_jumun.gridx = 1;
		contentPane.add(jumun, gbc_jumun);

		result_price_label = new WhiteLabel("총 " + result_price + "원");
		result_price_label.setFont(new Font("굴림", Font.BOLD, 22));
		GridBagConstraints gbc_jumun_result_price = new GridBagConstraints();
		gbc_jumun_result_price.gridx = 0;
		gbc_jumun_result_price.gridx = 0;
		jumun.add(result_price_label, gbc_jumun_result_price);

		JButton jumunBtn = new JButton("예약하기");
		jumunBtn.setBackground(Color.white);
		jumunBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (MenuItemInfo item : items) {
					if (item.count >= 1) {
						System.out.println("id: " + item.id);
						System.out.println("수량: " + item.count);

						OrderItem orderItem = new OrderItem(item.id, item.count);
						orderItems.add(orderItem);
					}
				}
				new YeyakDetail().setVisible(true);
				setVisible(false);

			}
		});
		GridBagConstraints gbc_jumun_btn = new GridBagConstraints();
		gbc_jumun_btn.gridx = 0;
		gbc_jumun_btn.gridx = 0;
		jumun.add(jumunBtn, gbc_jumun_btn);

		gbl_main.columnWeights = new double[] { 0.0, 0.0 };
		gbl_main.rowWeights = new double[] { 1.0 };
		main.setLayout(gbl_main);

		JLabel main_pic = new JLabel("");
		GridBagConstraints gbc_main_pic = new GridBagConstraints();
		gbc_main_pic.insets = new Insets(0, 0, 0, 5);
		gbc_main_pic.gridx = 0;
		gbc_main_pic.gridy = 0;
		main.add(main_pic, gbc_main_pic);

		ImageIcon originalIcon31 = new ImageIcon(StoreMenu.class.getResource("/resources" + store.img));

		// 2. 이미지 추출 후 리사이즈
		Image originalImg31 = originalIcon31.getImage();
		Image resizedImg31 = originalImg31.getScaledInstance(300, 360, Image.SCALE_SMOOTH); // 원하는 크기로 설정

		// 3. 리사이즈된 이미지로 아이콘 생성
		ImageIcon resizedIcon31 = new ImageIcon(resizedImg31);

		// 4. JLabel에 적용

		main_pic.setIcon(resizedIcon31);

		JPanel main_index = new JPanel();
		main_index.setBackground(Color.white);
		GridBagConstraints gbc_main_index = new GridBagConstraints();
		gbc_main_index.fill = GridBagConstraints.BOTH;
		gbc_main_index.gridx = 1;
		gbc_main_index.gridy = 0;
		main.add(main_index, gbc_main_index);
		GridBagLayout gbl_main_index = new GridBagLayout();
		gbl_main_index.columnWidths = new int[] { 400 };
		gbl_main_index.rowHeights = new int[] { 50, 50, 100, 160 };
		gbl_main_index.columnWeights = new double[] { 0.0 };
		gbl_main_index.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		main_index.setLayout(gbl_main_index);

		JLabel location_category = new WhiteLabel(store.location + "|" + store.category);
		GridBagConstraints gbc_location_category = new GridBagConstraints();
		gbc_location_category.anchor = GridBagConstraints.WEST;
		gbc_location_category.insets = new Insets(0, 0, 5, 0);
		gbc_location_category.gridx = 0;
		gbc_location_category.gridy = 0;
		main_index.add(location_category, gbc_location_category);

		JLabel shop_name = new WhiteLabel(store.name);
		shop_name.setFont(new Font("굴림", Font.BOLD, 28));
		GridBagConstraints gbc_shop_name = new GridBagConstraints();
		gbc_shop_name.fill = GridBagConstraints.BOTH;
		gbc_shop_name.insets = new Insets(0, 0, 5, 0);
		gbc_shop_name.gridx = 0;
		gbc_shop_name.gridy = 1;
		main_index.add(shop_name, gbc_shop_name);

		JLabel shop_index = new WhiteLabel(store.des);

		GridBagConstraints gbc_shop_index = new GridBagConstraints();
		gbc_shop_index.anchor = GridBagConstraints.NORTH;
		gbc_shop_index.fill = GridBagConstraints.HORIZONTAL;
		gbc_shop_index.insets = new Insets(0, 0, 5, 0);
		gbc_shop_index.gridx = 0;
		gbc_shop_index.gridy = 2;
		main_index.add(shop_index, gbc_shop_index);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		main_index.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new GridLayout(1, 2, 0, 0));

		JLabel lblNewLabel_5 = new WhiteLabel("");
		panel_4.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new WhiteLabel("");
		panel_4.add(lblNewLabel_6);

		int row = 0, col = 0;
		for (MenuItemInfo item : items) {

			JPanel menuItem = createMenuItem(item);

			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridx = col;
			gbc.gridy = row;
			gbc.insets = new Insets(0, 0, 0, 0);
			gbc.fill = GridBagConstraints.BOTH;
			menu.add(menuItem, gbc);

			if (++col > 1) {
				col = 0;
				row++;
			}
		}

	}

}
