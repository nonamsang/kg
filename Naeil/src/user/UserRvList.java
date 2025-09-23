package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import admin.store_rv.RvListController;
import admin.store_rv.RvListModel;

public class UserRvList extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int PANEL_HEIGHT = 221;
	private static final int SCROLL_WIDTH = 831;
	private static final int REFRESH_INTERVAL = 1000; // ms

	private JPanel contentPane;
	private JPanel containerPanel;
	private JScrollPane scrollPane;

	private RvListController rvc;
	private ArrayList<RvListModel> rvlm;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UserRvList frame = new UserRvList();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public UserRvList() {
		setTitle("예약 리스트");
		setSize(847, 686);
		setLocationRelativeTo(null);

		// 레이아웃 설정
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// 컨테이너 패널 (GridLayout: 3열)
		containerPanel = new JPanel(new GridLayout(0, 3, 10, 10));

		scrollPane = new JScrollPane(containerPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도
		contentPane.add(scrollPane, BorderLayout.CENTER);

		updatePanel();
	}

	protected void updatePanel() {
		SwingUtilities.invokeLater(() -> {
			try {
				containerPanel.removeAll();

				rvc = new RvListController();
				rvlm = rvc.getUserRvList();

				for (RvListModel rv : rvlm) {
					containerPanel.add(createRvListPanel(rv.getStoreImg(), rv.getRvNo(), rv.getStoreName(),
							rv.getRvDate(), rv.getRvTime(), rv.getYeyakjaName(), rv.getInwonsu()));
				}

				containerPanel.revalidate();
				containerPanel.repaint();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	private JPanel createRvListPanel(String market_img, int rv_no, String store_name, String rv_date, String rv_time,
			String yeyakja_name, int inwonsu) {

		int width = 200;
		int height = (int) (width * 1.5);

		JPanel rvListPanel = new JPanel(new BorderLayout());
		rvListPanel.setPreferredSize(new Dimension(width, height));
		rvListPanel.setBackground(Color.white);
		rvListPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		// 이미지 패널
		JPanel imgPanel = new JPanel(new BorderLayout());
		imgPanel.setPreferredSize(new Dimension(width, width));
		imgPanel.setBackground(Color.white);

		try {
			String imagePath = "src/resources" + market_img;
			ImageIcon icon = new ImageIcon(new File(imagePath).getAbsolutePath());
			Image img = icon.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setHorizontalAlignment(JLabel.CENTER);
			imgPanel.add(imgLabel, BorderLayout.CENTER);
		} catch (Exception e) {
			JLabel noImg = new JLabel("이미지 없음");
			noImg.setHorizontalAlignment(JLabel.CENTER);
			imgPanel.add(noImg, BorderLayout.CENTER);
		}

		rvListPanel.add(imgPanel, BorderLayout.CENTER);

		// 텍스트 정보 패널
		JPanel textPanel = new JPanel(new GridLayout(3, 1));
		textPanel.setPreferredSize(new Dimension(width, height - width));
		textPanel.setBackground(Color.white);

		Font font = new Font("", Font.PLAIN, 12);
		textPanel.add(createLabel("NO. " + rv_no, new Font("", Font.BOLD, 11)));
		textPanel.add(createLabel("가게이름: " + store_name, font));
		textPanel.add(
				createLabel("예약자: " + rv_date + " " + rv_time + " / " + yeyakja_name + " / " + inwonsu + "명", font));

		rvListPanel.add(textPanel, BorderLayout.SOUTH);

		// 이벤트 핸들러
		rvListPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println("패널 클릭됨: " + rv_no);
				try {
					new YeyakHwakin(rv_no, store_name, UserRvList.this).setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		return rvListPanel;
	}

	// 라벨 기본 설정 메소드
	private JLabel createLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		return label;
	}
}
