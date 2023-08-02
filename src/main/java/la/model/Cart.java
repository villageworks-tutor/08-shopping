package la.model;

import java.util.ArrayList;
import java.util.List;

import la.bean.ItemBean;

/**
 * カートを管理するモデル
 */
public class Cart {
	
	/**
	 * フィールド：カート内商品リスト
	 */
	private List<ItemBean> items;

	/**
	 * コンストラクタ
	 */
	public Cart() {
		this.items = new ArrayList<ItemBean>();
	}

	/**
	 * カート内商品リスト（itemsフィールド）を取得する
	 * @return カート内商品リスト
	 */
	public List<ItemBean> getItems() {
		return items;
	}

	/**
	 * 商品をカートに追加する
	 * @param newItem 追加する商品
	 */
	public void add(ItemBean newItem) {
		// 追加する商品がカート内にある場合のカート内商品を初期化
		ItemBean target = null;
		for (ItemBean item : this.items ) {
			if (item.getCode() == newItem.getCode()) {
				// 商品番号が一致した場合：一致した商品を確保してループを抜ける
				target = item;
				break;
			}
		}
		
		if (target == null) {
			// 一致した商品がカート内になかった場合：引数の商品をカートに追加
			this.items.add(newItem);
		} else {
			// 一致した商品がある場合：一致した商品の現在の数量に追加する商品の数量を加算して再設定（カートに戻す必要はない理由を考えてみよう）
			int currentQuantity = target.getQuantity();
			int newQuantity = currentQuantity + newItem.getQuantity();
			target.setQuantity(newQuantity);
		}
		
	}
	
	/***
	 * カート内商品の総額を計算する
	 * @return カート内商品の総額
	 */
	public int getTotal() {
		int total = 0;
		for (ItemBean item : this.items) {
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}

	/**
	 * 指定された商品番号の商品をカートから削除する
	 * @param code 削除対象商品の商品番号
	 */
	public void delete(int code) {
		for (ItemBean item : this.items) {
			if (item.getCode() == code) {
				this.items.remove(item);
				return;
			}
		}
	}
	
}
