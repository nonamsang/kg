package admin.store_rv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class RvList extends JFrame {
	private JPanel contentPane;
	private JPanel containerPanel;
	private JScrollPane scrollPane;
	private Timer timer;
	private RvListController rvc;
	private ArrayList<RvListModel> rvlm;

	private int width = 200;
	private int height = (int) (width * 1.5);

	private static final int REFRESH_INTERVAL = 1000;

	public RvList(int store_id) {
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

		// 주기적 패널 업데이트
		timer = new Timer(REFRESH_INTERVAL, e -> updatePanel(store_id));
		timer.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (timer != null)
					timer.stop();
			}
		});
	}

	private void updatePanel(int store_id) {
		SwingUtilities.invokeLater(() -> {
			try {
				containerPanel.removeAll();

				rvc = new RvListController();
				rvlm = rvc.getAllInfo(store_id);

				for (RvListModel rv : rvlm) {
					containerPanel.add(createRvListPanel(rv.getStoreImg(), rv.getRvNo(), rv.getStoreName(),
							rv.getRvDate(), rv.getRvTime(), rv.getYeyakjaName(), rv.getInwonsu()));
				}

				if (rvlm.size() <= 3) {
					JPanel binPanel = new JPanel();
					binPanel.setPreferredSize(new Dimension(width, height));

					for (int i = 0; i < 4 - rvlm.size(); i++) {
						containerPanel.add(binPanel);

					}
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

		// 클릭 이벤트
		rvListPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Yaeyak(rv_no, store_name).setVisible(true);
			}
		});

		return rvListPanel;
	}

	private JLabel createLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}

