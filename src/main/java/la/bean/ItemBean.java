package la.bean;

import java.io.Serializable;

/**
 * itemテーブルの1レコードを管理するJavaBean
 */
public class ItemBean implements Serializable{

	/**
	 * フィールド
	 */
	private int code;         // 商品番号
	private int categoryCode; // カテゴリーコード
	private String name;      // 商品名
	private int price;        // 価格
	private int quantity;     // 数量
	
	/**
	 * デフォルトコンストラクタ
	 */
	public ItemBean() {}
	
	/**
	 * コンストラクタ：新規商品の登録の際に呼出される
	 * @param categoryCode カテゴリコード
	 * @param name         商品名
	 * @param price        価格
	 */
	public ItemBean(int categoryCode, String name, int price) {
		this.categoryCode = categoryCode;
		this.name = name;
		this.price = price;
	}

	/**
	 * コンストラクタ：すでに登録された商品のインスタンス化で呼び出される
	 * @param code         商品番号
	 * @param categoryCode カテゴリコード
	 * @param name         商品名
	 * @param price        価格
	 */
	public ItemBean(int code, int categoryCode, String name, int price) {
		this(categoryCode, name, price);
		this.code = code;
	}

	/**
	 * コンストラクタ：すでに登録された商品のインスタンス化で呼び出される
	 * @param code         商品番号
	 * @param categoryCode カテゴリコード
	 * @param name         商品名
	 * @param price        価格
	 * @param quantity     数量
	 */
	public ItemBean(int code, int categoryCode, String name, int price, int quantity) {
		this(code, categoryCode, name, price);
		this.quantity = quantity;
	}

	/**
	 * コンストラクタ：単体テスト用
	 * @param code         商品番号
	 * @param name         商品名
	 * @param price        価格
	 * @param quantity     数量
	 */
	public ItemBean(int code, String name, int price, int quantity) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	/** アクセサメソッド群 */
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemBean [");
		builder.append("code=" + code + ", ");
		builder.append("categoryCode=" + categoryCode + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity + "]");
		return builder.toString();
	}
	
}
