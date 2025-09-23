package admin.store_manage;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import admin.store_rv.RvListModel;

/*
 * 리스트뷰에 가게 목록 그려주는 클래스
 */

public class StoreSelectListModel extends AbstractListModel<RvListModel> {
	private final ArrayList<RvListModel> storeList;

	public StoreSelectListModel(ArrayList<RvListModel> storeList) {
		this.storeList = storeList;
	}

	@Override
	public int getSize() {
		return storeList.size();
	}

	@Override
	public RvListModel getElementAt(int index) {
		return storeList.get(index);
	}
}
