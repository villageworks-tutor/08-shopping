package la.bean;

import java.io.Serializable;

/**
 * customerテーブルの1レコードを管理するJavaBean
 */
public class CustomerBean implements Serializable {
	
	/**
	 * フィールド
	 */
	private int id;          // 顧客番号
	private String name;     // 顧客名
	private String address;  // 送付先住所
	private String tel;      // 連絡先電話番号
	private String email;    // 連絡先電子メールアドレス
	private String password; // パスワード
	
	/**
	 * デフォルトコンストラクタ
	 */
	public CustomerBean() {}

	/**
	 * コンストラクタ：新規登録の際に呼び出される
	 * @param name    顧客名
	 * @param address 送付先住所
	 * @param tel     連絡先電話番号
	 * @param email   電子メールアドレス
	 */
	public CustomerBean(String name, String address, String tel, String email) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
	}

	/**
	 * コンストラクタ：すでに登録された顧客のインスタンス化で呼び出される
	 * @param id
	 * @param name
	 * @param address
	 * @param tel
	 * @param email
	 */
	public CustomerBean(int id, String name, String address, String tel, String email) {
		this(name, address, tel, email);
		this.id = id;
	}

	/** アクセサメソッド群 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerBean [");
		builder.append("id=" + id + ", ");
		builder.append("name=" + name + ", ");
		builder.append("address=" + address + ", ");
		builder.append("tel=" + tel + ", ");
		builder.append("email="	+ email + "]");
		return builder.toString();
	}
	
}
