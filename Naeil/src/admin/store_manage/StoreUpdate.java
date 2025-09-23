package admin.store_manage;

import java.awt.Color;
import java.awt.Dimension;
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
import main.StaticInfo;
import user.InfoTable.StoreInfo;
import user.sqlDAO2;

public class StoreUpdate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfStoreName;
	private JTextField tfStoreCategory;
	private JTextField tfStoreJuso;
	private JTextField tfStorePh;
	private JTextField tfStoreDes;

	private JButton btnImgSelect;

	private JLabel lblStoreName;
	private JLabel lblStoreCategory;
	private JLabel lblJuso;
	private JLabel lblPh;
	private JLabel lblStoreDes;

	private File selectedImageFile = null; // 사용자가 선택한 새 이미지 파일
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StoreInsert frame = new StoreInsert();
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
	public StoreUpdate() {

	}

	public StoreUpdate(int store_id) {

		sqlDAO2 dao = new sqlDAO2();
		StoreInfo store = dao.getStoreBystoreId(StaticInfo.store_id);

		setSize(635, 886);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 88, 447, 30 };// 507 + 88 = 595
		gbl_contentPane.rowHeights = new int[] { 200, 70, 40, 40, 40, 40, 150, 70 }; // 0사진, 1사진버튼, 2이름, 3카테고리, 4주소,
																						// 5폰번호, 6설명, 7추가버튼
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
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
			ImageIcon icon = new ImageIcon(getClass().getResource("/resources" + store.img));
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

		lblStoreName = new JLabel("가게 이름");
		lblStoreName.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblStoreName, gbc_lblNewLabel);

		tfStoreName = new JTextField();
		tfStoreName.setText(store.name);
		tfStoreName.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가
		GridBagConstraints gbc_tfMenuName = new GridBagConstraints();
		gbc_tfMenuName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfMenuName.insets = new Insets(5, 10, 5, 10);
		gbc_tfMenuName.gridx = 2;
		gbc_tfMenuName.gridy = 2;
		gbc_tfMenuName.fill = GridBagConstraints.BOTH; // 가로 + 세로 모두 채우기
		gbc_tfMenuName.weightx = 0.0; // 가로 공간 분배
		gbc_tfMenuName.weighty = 0.0; // 세로 공간 분배
		contentPane.add(tfStoreName, gbc_tfMenuName);
		tfStoreName.setColumns(10);

		lblStoreCategory = new JLabel("가게 카테고리");
		lblStoreCategory.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		contentPane.add(lblStoreCategory, gbc_lblNewLabel_1);

		tfStoreCategory = new JTextField();
		tfStoreCategory.setText(store.category);
		tfStoreCategory.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가
		GridBagConstraints gbc_tfMenuPrice = new GridBagConstraints();
		gbc_tfMenuPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfMenuPrice.insets = new Insets(5, 10, 5, 10);
		gbc_tfMenuPrice.gridx = 2;
		gbc_tfMenuPrice.gridy = 3;
		gbc_tfMenuPrice.fill = GridBagConstraints.BOTH; // 가로 + 세로 모두 채우기
		gbc_tfMenuPrice.weightx = 0.0; // 가로 공간 분배
		gbc_tfMenuPrice.weighty = 0.0; // 세로 공간 분배
		contentPane.add(tfStoreCategory, gbc_tfMenuPrice);
		tfStoreCategory.setColumns(10);
		
		lblJuso = new JLabel("가게 주소");
		lblJuso.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblJuso = new GridBagConstraints();
		gbc_lblJuso.insets = new Insets(0, 0, 5, 5);
		gbc_lblJuso.gridx = 1;
		gbc_lblJuso.gridy = 4;
		contentPane.add(lblJuso, gbc_lblJuso);

		tfStoreJuso = new JTextField();
		tfStoreJuso.setText(store.location);
		tfStoreJuso.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가
		GridBagConstraints gbc_tfStoreJuso = new GridBagConstraints();
		gbc_tfStoreJuso.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStoreJuso.insets = new Insets(5, 10, 5, 10);
		gbc_tfStoreJuso.gridx = 2;
		gbc_tfStoreJuso.gridy = 4;
		gbc_tfStoreJuso.fill = GridBagConstraints.BOTH; // 가로 + 세로 모두 채우기
		gbc_tfStoreJuso.weightx = 0.0; // 가로 공간 분배
		gbc_tfStoreJuso.weighty = 0.0; // 세로 공간 분배
		contentPane.add(tfStoreJuso, gbc_tfStoreJuso);
		tfStoreJuso.setColumns(10);
		
		lblPh = new JLabel("가게 전화번호");
		lblPh.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblPh = new GridBagConstraints();
		gbc_lblPh.insets = new Insets(0, 0, 5, 5);
		gbc_lblPh.gridx = 1;
		gbc_lblPh.gridy = 5;
		contentPane.add(lblPh, gbc_lblPh);

		tfStorePh = new JTextField();
		tfStorePh.setText(store.phone);
		tfStorePh.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가
		GridBagConstraints gbc_tfStorePh = new GridBagConstraints();
		gbc_tfStorePh.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStorePh.insets = new Insets(5, 10, 5, 10);
		gbc_tfStorePh.gridx = 2;
		gbc_tfStorePh.gridy = 5;
		gbc_tfStorePh.fill = GridBagConstraints.BOTH; // 가로 + 세로 모두 채우기
		gbc_tfStorePh.weightx = 0.0; // 가로 공간 분배
		gbc_tfStorePh.weighty = 0.0; // 세로 공간 분배
		contentPane.add(tfStorePh, gbc_tfStorePh);
		tfStorePh.setColumns(10);

		lblStoreDes = new JLabel("가게 설명");
		lblStoreDes.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 6;

		contentPane.add(lblStoreDes, gbc_lblNewLabel_2);

		tfStoreDes = new JTextField();
		tfStoreDes.setText(store.des);
		GridBagConstraints gbc_taMenuSeolmyung = new GridBagConstraints();
		gbc_taMenuSeolmyung.fill = GridBagConstraints.BOTH;
		gbc_taMenuSeolmyung.insets = new Insets(5, 10, 5, 10);
		gbc_taMenuSeolmyung.gridx = 2;
		gbc_taMenuSeolmyung.gridy = 6;
		tfStoreDes.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가
		contentPane.add(tfStoreDes, gbc_taMenuSeolmyung);

		JButton btnInsertMenu = new JButton("수정");
		btnInsertMenu.setFont(new Font("굴림", Font.BOLD, 15));
		btnInsertMenu.setBackground(Color.WHITE); // ✅ 배경 흰색
		btnInsertMenu.setPreferredSize(new Dimension(200, 40)); // 200x50 픽셀로 버튼 크기 고wjd
		btnInsertMenu.setMinimumSize(new Dimension(80, 30));
		btnInsertMenu.setBorder(BorderFactory.createLineBorder(Color.darkGray)); // 테두리 추가

		GridBagConstraints gbc_btnInsertMenu = new GridBagConstraints();
		gbc_btnInsertMenu.gridwidth = 1;
		gbc_btnInsertMenu.gridx = 2;
		gbc_btnInsertMenu.gridy = 7;
		gbc_btnInsertMenu.anchor = GridBagConstraints.NORTHEAST; // 오른쪽 위에 고정
		gbc_btnInsertMenu.fill = GridBagConstraints.NONE;
		gbc_btnInsertMenu.insets = new Insets(10, 10, 10, 10); // 여백 조정으로 살짝 오른쪽 밀기

		contentPane.add(btnInsertMenu, gbc_btnInsertMenu); // 오른쪽 정렬
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
						Image img = newIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
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
				String newName = tfStoreName.getText().trim();
				String newCategory = tfStoreCategory.getText().trim();
				String newJuso = tfStoreJuso.getText().trim();
				String newPh = tfStorePh.getText().trim();
				String newSeolmyung = tfStoreDes.getText().trim();
				String newImgPath = store.img;
				

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
				
					System.out.println(StaticInfo.store_id);
					boolean success = new UpdateInfo().UpdateStore(newImgPath, newName, newCategory, newJuso, newPh,
							newSeolmyung);
					if (success) {
						JOptionPane.showMessageDialog(null, "가게 수정 성공");
					}
					System.out.println(StaticInfo.store_id + "     2");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "가게 수정 실패");
					ex.printStackTrace();
				}

				dispose();
			}

		});
	}

}