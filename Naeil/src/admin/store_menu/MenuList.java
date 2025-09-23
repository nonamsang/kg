package admin.store_menu;

import java.awt.BorderLayout;
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
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import admin.LoadInfo;
import admin.store_rv.RvListModel;
import main.StaticInfo;

public class MenuList extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel containerPanel;
	private JScrollPane scrollPane;
	protected int store_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MenuList frame = new MenuList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuList() {
	}

	public MenuList(int store_id) {
//		int store_id = 1;
		setSize(710, 686);
		setLocationRelativeTo(null);
		this.store_id = store_id;

		// 레이아웃 설정
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// 상단 메뉴추가 버튼 패널
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setPreferredSize(new Dimension(700, 60)); // 최소 높이 확보

		JPanel panelStoreName = new JPanel();
		panelStoreName.setPreferredSize(new Dimension(300, 50)); // 크기 지정
		panelStoreName.setBackground(Color.white);
		topPanel.add(panelStoreName);

		JLabel lblStoreName = new JLabel(StaticInfo.statStoreName);
		lblStoreName.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		panelStoreName.add(lblStoreName);

		contentPane.add(topPanel, BorderLayout.NORTH);


		// 스크롤 패널 및 컨테이너 패널
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(containerPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도
		contentPane.add(scrollPane, BorderLayout.CENTER);

		updatePanel(store_id);
	}

	protected void updatePanel() {
		updatePanel(store_id);
	}

	private void updatePanel(int store_id) {
		SwingUtilities.invokeLater(() -> {
			try {
				containerPanel.removeAll();

				LoadInfo info = new LoadInfo();
				ArrayList<RvListModel> rvlm = info.loadMenu(store_id);

				containerPanel.setLayout(new GridBagLayout()); // ✅ 반드시 레이아웃 설정

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(10, 10, 10, 10); // 간격 설정
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;

				int col = 0;
				int row = 0;

				for (RvListModel rv : rvlm) {
					gbc.gridx = col;
					gbc.gridy = row;
					containerPanel.add(createMenuListPanel(rv.getMenuImg(), rv.getMenuName(), rv.getPrice(),
							rv.getSeolmyung(), rv.getMenuId()), gbc);

					col++;
					if (col >= 3) {
						col = 0;
						row++;
					}
				}

				// 마지막에 메뉴 추가 버튼
				gbc.gridx = col;
				gbc.gridy = row;
				containerPanel.add(createMenuListPanel("/admin/add2.png", "", 0, "", -1), gbc);

				containerPanel.revalidate();
				containerPanel.repaint();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}



	private JPanel createMenuListPanel(String menu_img, String menu_name, int price, String seolmyung, int menu_id) {
		int width = 200;
		int height = (int) (width * 1.5); // 1:1.5 비율

		JPanel menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(width, height));
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		// 이미지 패널
		try {
			String imagePath = "src/resources" + menu_img;
			ImageIcon icon = new ImageIcon(new File(imagePath).getAbsolutePath());
			Image img = icon.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setHorizontalAlignment(JLabel.CENTER);
			menuPanel.add(imgLabel, BorderLayout.CENTER);
		} catch (Exception e) {
			JLabel noImg = new JLabel("이미지 없음");
			noImg.setHorizontalAlignment(JLabel.CENTER);
			menuPanel.add(noImg, BorderLayout.CENTER);
		}

		// 텍스트 패널
		if (seolmyung == null)
			seolmyung = "";

		if(menu_id != -1) {
			JPanel textPanel = new JPanel(new GridLayout(3, 1));
			textPanel.setPreferredSize(new Dimension(width, height - width));
			textPanel.setBackground(Color.white);
			Font font = new Font("", Font.PLAIN, 12);
			textPanel.add(createLabel("메뉴 이름: " + menu_name, font));
			textPanel.add(createLabel("가격: " + price + "원", font));
			textPanel.add(createLabel("메뉴 설명: " + seolmyung, font));

			menuPanel.add(textPanel, BorderLayout.SOUTH);
		}


		// 클릭 이벤트
		menuPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (menu_id == -1) {
						new MenuInsert(store_id, MenuList.this).setVisible(true);
					} else {
					new MenuUpdate(menu_id, MenuList.this).setVisible(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});


		return menuPanel;
	}

	// 라벨 기본 설정 메소드
	private JLabel createLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		return label;
	}

}
