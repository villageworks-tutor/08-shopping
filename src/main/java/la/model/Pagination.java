package la.model;

/**
 * ページネーションを管理するモデル
 */
public class Pagination {

	/**
	 * フィールド
	 */
	private String action;   // アクションキー
	private String category; // カテゴリーコード
	private String keyword;  // キーワード
	private String maxPage;  // 最大ページ数

	/**
	 * コンストラクタ
	 * @param action   アクションキー
	 * @param category カテゴリーコード
	 * @param keyword  キーワード
	 * @param maxPage  最大ページ数
	 */
	public Pagination(String action, String category, String keyword, String maxPage) {
		this.action = action;
		this.category = category;
		this.keyword = keyword;
		this.maxPage = maxPage;
	}
	
	/** アクセッサメソッド群 */

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}
	
}
