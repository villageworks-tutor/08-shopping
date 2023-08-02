package la.bean;

import java.io.Serializable;

/**
 * categoryテーブルの1レコードを管理するJavaBean
 */
public class CategoryBean implements Serializable {
	
	/**
	 * フィールド
	 */
	private int code;    // カテゴリーコード
	private String name; // カテゴリー名
	
	/**
	 * デフォルトコンストラクタ
	 */
	public CategoryBean() {}

	/**
	 * コンストラクタ
	 * @param code カテゴリーコード
	 * @param name カテゴリー名
	 */
	public CategoryBean(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/** アクセサメソッド群 */

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoryBean [");
		builder.append("code=" + code + ", ");
		builder.append("name=" + name + "]");
		return builder.toString();
	}
	
}
