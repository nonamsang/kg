package admin.store_menu;

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
import java.util.ArrayList;

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

import admin.LoadInfo;
import admin.UpdateInfo;
import admin.store_rv.RvListModel;

public class MenuUpdate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMenuName;
	private JTextField tfMenuPrice;
	private JTextField tfMenuDes;
	private JLabel lblMenuImg;

	private JLabel lblMenuName;
	private JLabel lblMenuPrice;
	private JLabel lblMenuDes;

	private JButton btnImgSelect;
	private File selectedImageFile = null; // 사용자가 선택한 새 이미지 파일

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MenuUpdate frame = new MenuUpdate();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuUpdate() {

	}

	public MenuUpdate(int menu_id, MenuList parent) throws Exception {

		LoadInfo info = new LoadInfo();
		ArrayList<RvListModel> rvlm = info.loadMenuDetails(menu_id);

		String menu_img = rvlm.get(0).getMenuImg();
		String menu_name = rvlm.get(0).getMenuName();
		int price = rvlm.get(0).getPrice();
		String seolmyung = rvlm.get(0).getSeolmyung();

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			System.out.println(menu_img);
			ImageIcon icon = new ImageIcon(getClass().getResource("/resources" + menu_img));
			Image img = icon.getImage().getScaledInstance(384, 384, Image.SCALE_SMOOTH);
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
		tfMenuName.setText(menu_name);
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
		tfMenuPrice.setText(price + " 원");
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
		tfMenuDes.setText(seolmyung);
		GridBagConstraints gbc_taMenuSeolmyung = new GridBagConstraints();
		gbc_taMenuSeolmyung.fill = GridBagConstraints.BOTH;
		gbc_taMenuSeolmyung.insets = new Insets(5, 10, 5, 10);
		gbc_taMenuSeolmyung.gridx = 2;
		gbc_taMenuSeolmyung.gridy = 4;
		contentPane.add(tfMenuDes, gbc_taMenuSeolmyung);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(5, 10, 10, 10);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		gbc_panel.gridwidth = 2;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 268, 268 };
		gbl_panel.rowHeights = new int[] { 71, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnOK = new JButton("변경");
		btnOK.setFont(new Font("굴림", Font.BOLD, 15));
		btnOK.setBackground(Color.WHITE); // ✅ 배경 흰색
		btnOK.setPreferredSize(new Dimension(200, 40)); // 200x50 픽셀로 버튼 크기 고wjd
		btnOK.setMinimumSize(new Dimension(80, 40));
		GridBagConstraints gbc_btnOK = new GridBagConstraints();
		gbc_btnOK.insets = new Insets(0, 0, 0, 5);
		gbc_btnOK.gridx = 0;
		gbc_btnOK.gridy = 0;
		gbc_btnOK.anchor = GridBagConstraints.NORTHWEST; // 오른쪽 위에 고정
		gbc_btnOK.fill = GridBagConstraints.NONE;
		panel.add(btnOK, gbc_btnOK);

		JButton btnDel = new JButton("삭제");
		btnDel.setFont(new Font("굴림", Font.BOLD, 15));
		btnDel.setBackground(Color.WHITE); // ✅ 배경 흰색
		btnDel.setPreferredSize(new Dimension(200, 40)); // 200x50 픽셀로 버튼 크기 고wjd
		btnDel.setMinimumSize(new Dimension(80, 40));
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.gridx = 1;
		gbc_btnDel.gridy = 0;
		gbc_btnDel.anchor = GridBagConstraints.NORTHEAST; // 오른쪽 위에 고정
		gbc_btnDel.fill = GridBagConstraints.NONE;
		panel.add(btnDel, gbc_btnDel);

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

		// 메뉴 수정한거 저장하는 버튼
		btnOK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newName = tfMenuName.getText().trim();
				String newPriceStr = tfMenuPrice.getText().replaceAll("[^0-9]", "").trim();
				String newSeolmyung = tfMenuDes.getText().trim();
				int newPrice;

				try {
					newPrice = Integer.parseInt(newPriceStr);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "가격 형식이 올바르지 않습니다.");
					return;
				}

				boolean isChanged = false;
				String newImgPath = menu_img; // 초기값: 기존 이미지 경로

				// 이미지 변경 여부 확인
				if (selectedImageFile != null) {
					isChanged = true;
					// 저장할 경로
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

						newImgPath = fileName; // DB에 저장될 경로 기준

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "이미지 저장 실패");
						ex.printStackTrace();
						return;
					}
				}

				// 텍스트 변경 여부 확인
				if (!menu_name.equals(newName) || price != newPrice || !seolmyung.equals(newSeolmyung)) {
					isChanged = true;
				}

				// 실제 업데이트
				if (isChanged) {
					try {
						boolean success = new UpdateInfo().updateMenu(menu_id, newImgPath, newName, newPrice,
								newSeolmyung);
						if (success) {
							if (parent != null) {
								JOptionPane.showMessageDialog(null, "변경되었습니다");
								parent.updatePanel(); // store_id를 외부 접근 가능하게
							}
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "메뉴 변경 실패");
						ex.printStackTrace();
					}

					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "변경된 값이 없습니다");
				}

			}
		});

		// 메뉴 삭제 버튼
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean success;
				try {
					success = new UpdateInfo().deleteMenu(menu_id);
					if (success) {
						if (parent != null) {
							JOptionPane.showMessageDialog(null, "삭제되었습니다");
							parent.updatePanel(); // store_id를 외부 접근 가능하게
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "메뉴 삭제 실패");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}

}
