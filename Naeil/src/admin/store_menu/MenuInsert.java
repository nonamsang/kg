package admin.store_menu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import admin.UpdateInfo;

public class MenuInsert extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMenuName;
	private JTextField tfMenuPrice;
	private JTextField tfMenuDes;

	private JLabel lblMenuImg;
	private JButton btnImgSelect;
	private File selectedImageFile = null; // 사용자가 선택한 새 이미지 파일
	private JLabel lblMenuName;
	private JLabel lblMenuPrice;
	private JLabel lblMenuDes;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MenuInsert frame = new MenuInsert();
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
	public MenuInsert() {

	}

	public MenuInsert(int store_id, MenuList parent) {

		setSize(635, 886);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 88, 447, 30 };
		gbl_contentPane.rowHeights = new int[] { 200, 70, 70, 70, 150, 70 };
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		btnImgSelect = new JButton("사진 설정");
		btnImgSelect.setBackground(Color.white);
		btnImgSelect.setOpaque(true); // 투명하지 않게!
		btnImgSelect.setContentAreaFilled(true); // 내용 영역 배경 그리기 허용
		GridBagConstraints gbc_btnImgSelect = new GridBagConstraints();
		gbc_btnImgSelect.gridwidth = 2;
		gbc_btnImgSelect.gridheight = 2;
		gbc_btnImgSelect.insets = new Insets(50, 0, 55, 0);
		gbc_btnImgSelect.gridx = 1;
		gbc_btnImgSelect.gridy = 0;

		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/resources/admin/photo_chuga.PNG"));
			Image img = icon.getImage().getScaledInstance(500, 384, Image.SCALE_SMOOTH);
			btnImgSelect = new JButton(new ImageIcon(img));
			btnImgSelect.setBackground(Color.WHITE); // ✅ 배경 흰색
			btnImgSelect.setOpaque(true); // ✅ 투명 비활성
			btnImgSelect.setContentAreaFilled(true); // ✅ 내부 배경 채우기
			btnImgSelect.setFocusPainted(false); // ✅ 클릭시 파란선 제거
			btnImgSelect.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가

		} catch (Exception e) {
			btnImgSelect = new JButton("이미지 없음");
		}

		contentPane.add(btnImgSelect, gbc_btnImgSelect);

		lblMenuName = new JLabel("메뉴 이름");
		lblMenuName.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblMenuName, gbc_lblNewLabel);

		tfMenuName = new JTextField();
		GridBagConstraints gbc_tfMenuName = new GridBagConstraints();
		gbc_tfMenuName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfMenuName.insets = new Insets(5, 10, 5, 10);
		gbc_tfMenuName.gridx = 2;
		gbc_tfMenuName.gridy = 2;
		contentPane.add(tfMenuName, gbc_tfMenuName);
		tfMenuName.setColumns(10);

		lblMenuPrice = new JLabel("메뉴 가격");
		lblMenuPrice.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		contentPane.add(lblMenuPrice, gbc_lblNewLabel_1);

		tfMenuPrice = new JTextField();
		GridBagConstraints gbc_tfMenuPrice = new GridBagConstraints();
		gbc_tfMenuPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfMenuPrice.insets = new Insets(5, 10, 5, 10);
		gbc_tfMenuPrice.gridx = 2;
		gbc_tfMenuPrice.gridy = 3;
		contentPane.add(tfMenuPrice, gbc_tfMenuPrice);
		tfMenuPrice.setColumns(10);

		lblMenuDes = new JLabel("메뉴 설명");
		lblMenuDes.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 4;
		contentPane.add(lblMenuDes, gbc_lblNewLabel_2);

		tfMenuDes = new JTextField();
		GridBagConstraints gbc_taMenuSeolmyung = new GridBagConstraints();
		gbc_taMenuSeolmyung.fill = GridBagConstraints.BOTH;
		gbc_taMenuSeolmyung.insets = new Insets(5, 10, 5, 10);
		gbc_taMenuSeolmyung.gridx = 2;
		gbc_taMenuSeolmyung.gridy = 4;
		contentPane.add(tfMenuDes, gbc_taMenuSeolmyung);


		JButton btnInsertMenu = new JButton("추가");
		GridBagConstraints gbc_btnInsertMenu = new GridBagConstraints();
		gbc_btnInsertMenu.gridwidth = 2;
		gbc_btnInsertMenu.gridx = 1;
		gbc_btnInsertMenu.gridy = 5;
		gbc_btnInsertMenu.insets = new Insets(5, 10, 5, 10);
		btnInsertMenu.setBackground(Color.white);
		gbc_btnInsertMenu.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnInsertMenu.fill = GridBagConstraints.NONE;
		contentPane.add(btnInsertMenu, gbc_btnInsertMenu);

		// 이벤트 핸들러
		// 이미지 변경
		btnImgSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser();
				int returnVal = jfc.showOpenDialog(getParent());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selectedImageFile = jfc.getSelectedFile(); // 임시 저장

					try {
						ImageIcon newIcon = new ImageIcon(selectedImageFile.getAbsolutePath());
						Image img = newIcon.getImage().getScaledInstance(384, 384, Image.SCALE_SMOOTH);
						btnImgSelect.setIcon(new ImageIcon(img)); // 미리보기만 변경
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		btnInsertMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newName = tfMenuName.getText().trim();
				String newPriceStr = tfMenuPrice.getText().replaceAll("[^0-9]", "").trim();
				String newSeolmyung = tfMenuDes.getText().trim();
				String newImgPath = "";
				int newPrice = 0;

				try {
					newPrice = Integer.parseInt(newPriceStr);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "가격 형식이 올바르지 않습니다.");
					return;
				}

				// 이미지 변경 여부 확인
				if (selectedImageFile != null) {
					String fileName = "/" + selectedImageFile.getName();
					String destPath = "src/resources" + fileName;
					File destFile = new File(destPath);

					// 파일 복사
					try (FileInputStream fis = new FileInputStream(selectedImageFile);
							FileOutputStream fos = new FileOutputStream(destFile)) {

						byte[] buffer = new byte[1024];
						int length;
						while ((length = fis.read(buffer)) > 0) {
							fos.write(buffer, 0, length);
						}
						newImgPath = fileName;

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "이미지 저장 실패");
						ex.printStackTrace();
						return;
					}
				}

				// 실제 업데이트

				try {
					if (!newImgPath.isEmpty() && !newName.isEmpty()) {
						boolean success = new UpdateInfo().insertMenu(store_id, newImgPath, newName, newPrice,
								newSeolmyung);
						if (success) {
							if (parent != null) {
								JOptionPane.showMessageDialog(null, "메뉴 추가 성공");
								parent.updatePanel(); // store_id를 외부 접근 가능하게
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "설명을 제외한 모든 항목을 입력해주세요.");
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "메뉴 추가 실패");
					ex.printStackTrace();
				}

				dispose();
			}

		});
	}

}
