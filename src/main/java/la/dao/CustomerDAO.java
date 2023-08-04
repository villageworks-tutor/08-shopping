package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import la.bean.CustomerBean;

/**
 * 顧客に関するデータ操作を実行するDAO
 */
public class CustomerDAO {

	/**
	 * クラス定数
	 */
	// データベース接続情報文字列群
	private static final String DB_DRIVER     = "org.postgresql.Driver";
	private static final String DB_URL        = "jdbc:postgresql://localhost:5432/sample";
	private static final String DB_USER       = "student";
	private static final String DB__PASSWORD  = "himitu";
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
	private Connection con;

	/**
	 * コンストラクタ
	 * @throws DAOException
	 */
	public CustomerDAO() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
			this.con = DriverManager.getConnection(DB_URL, DB_USER, DB__PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの読込みに失敗しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベースへの接続に失敗しました。");
		}
	}

	public CustomerBean findByEmailAndPassword(String email, String password) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT code, name, address, tel, email FROM customer WHERE email = ? AND password = ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにパラメータをバインド
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから顧客インスタンスを取得
				CustomerBean bean = null;
				if (rs.next()) {
					bean = new CustomerBean();
					bean.setId(rs.getInt("code"));
					bean.setName(rs.getString("name"));
					bean.setAddress(rs.getString("address"));
					bean.setTel(rs.getString("tel"));
					bean.setEmail(rs.getString("email"));
				}
				// 顧客インスタンスを返却
				return bean;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	
	
}
