package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.CategoryBean;
import la.bean.ItemBean;
import la.servlet.ShowItemServlet;

/**
 * 商品に関するデータ操作を実行するDAO
 */
public class ItemDAO {
	
	/**
	 * クラス定数
	 */
	// データベース接続情報文字列定数群
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql://localhost:5432/sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
	private Connection con;

	/**
	 * コンストラクタ
	 * @throws DAOException 
	 */
	public ItemDAO() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
			this.con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベースへの接続に失敗しました。");
		}
	}

	/**
	 * conフィールドを取得する
	 * @return conフィールド
	 */
	public Connection getCon() {
		return this.con;
	}
	
	/**
	 * カテゴリー一覧を取得する
	 * @return List<CategoryBean> カテゴリー一覧リスト
	 * @throws DAOException
	 */
	public List<CategoryBean> findAllCategory() throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT * FROM category ORDER BY code";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();) {
			// 結果セットからカテゴリーリストへの詰め替え
			List<CategoryBean> list = new ArrayList<CategoryBean>();
			while (rs.next()) {
				CategoryBean bean = new CategoryBean();
				bean.setCode(rs.getInt("code"));
				bean.setName(rs.getString("name"));
				list.add(bean);
			}
			// カテゴリーリストを返却
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	/**
	 * 商品番号で商品を取得する
	 * @param code 取得する商品の商品番号
	 * @return 商品のインスタンス
	 * @throws DAOException
	 */
	public ItemBean findByPrimaryKey(int code) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT * FROM item WHERE code = ?";
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, code);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットからインスタンスを生成
				ItemBean bean = null;
				if (rs.next()) {
					bean = new ItemBean();
					bean.setCode(rs.getInt("code"));
					bean.setCategoryCode(rs.getInt("category_code"));
					bean.setName(rs.getString("name"));
					bean.setPrice(rs.getInt("price"));
				}
				// 商品のインスタンスを返却
				return bean;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	/**
	 * カテゴリー別の商品を取得する。
	 * @param categoryCode 取得対象のカテゴリーコード
	 * @return List<ItemBean> 商品リスト
	 * @throws DAOException 
	 */
	public List<ItemBean> findByCategory(int categoryCode) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT * FROM item WHERE category_code = ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, categoryCode);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs= pstmt.executeQuery();) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					ItemBean bean = new ItemBean();
					bean.setCode(rs.getInt("code"));
					bean.setCategoryCode(rs.getInt("category_code"));
					bean.setName(rs.getString("name"));
					bean.setPrice(rs.getInt("price"));
					list.add(bean);
				}
				// 商品リストを返却
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	/**
	 * カテゴリー別の商品一覧の指定されたページの分を取得する。
	 * @param categoryCode
	 * @param page
	 * @return List<ItemBean> 商品リスト
	 * @throws DAOException 
	 */
	public List<ItemBean> findByCategory(int categoryCode, int page) throws DAOException {
		// 1ページに表示するレコードの件数：ShowItemServletのクラス定数を利用
		final int countPerPage = ShowItemServlet.COUNT_PER_PAGE;
		// 実行するSQLの設定
		String sql = "SELECT * FROM item WHERE category_code = ? ORDER BY code LIMIT " + Integer.toString(countPerPage) + " OFFSET ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにパラメータをバインド
			pstmt.setInt(1, categoryCode);
			pstmt.setInt(2, countPerPage * (page - 1));
			try (// sQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					ItemBean bean = new ItemBean();
					bean.setCode(rs.getInt("code"));
					bean.setCategoryCode(categoryCode);
					bean.setName(rs.getString("name"));
					bean.setPrice(rs.getInt("price"));
					list.add(bean);
				}
				// 商品リストを返却
				return list;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");
		}
	}

	/**
	 * 商品名にキーワードが含まれている商品を取得する
	 * @param keyword 検索キーワード
	 * @return List<ItemBean> 商品リスト
	 * @throws DAOException
	 */
	public List<ItemBean> findByName(String keyword) throws DAOException {
		// 実行するSQLを設定
		String sql = "SELECT * FROM item WHERE name LIKE ? ORDER BY code";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setString(1, "%" + keyword + "%");
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					ItemBean bean = new ItemBean();
					bean.setCode(rs.getInt("code"));
					bean.setName(rs.getString("name"));
					bean.setPrice(rs.getInt("price"));
					list.add(bean);
				}
				// 商品リストを返却
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	/**
	 * カテゴリ検索の件数を取得する
	 * @param categoryCode 検索対象のカテゴリーコード
	 * @return 検索結果の商品件数
	 * @throws DAOException
	 */
	public int countByCategory(int categoryCode) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT count(*) FROM item WHERE category_code = ?";
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, categoryCode);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから件数を取得
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				// 件数の返却
				return count;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public List<ItemBean> findByName(String keyword, int page) throws DAOException {
		// 実行するSQLの設定
		// 1ページに表示するレコードの件数：ShowItemServletのクラス定数を利用
		final int countPerPage = ShowItemServlet.COUNT_PER_PAGE;
		String sql = "SELECT * FROM item WHERE name LIKE ? ORDER BY code LIMIT " + Integer.toString(countPerPage) + " OFFSET ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにパラメータをバインド
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, countPerPage * (page - 1));
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					ItemBean bean = new ItemBean();
					bean.setCode(rs.getInt("code"));
					bean.setCategoryCode(rs.getInt("category_code"));
					bean.setName(rs.getString("name"));
					bean.setPrice(rs.getInt("price"));
					list.add(bean);
				}
				// 商品リストを返却
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	/**
	 * キーワード検索の件数を取得する
	 * @param keyword 検索キーワード
	 * @return 検索結果の商品件数
	 * @throws DAOException
	 */
	public int countByName(String keyword) throws DAOException {
		// 実行するSQLを設定
		String sql = "SELECT count(*) FROM item WHERE name LIKE ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setString(1, "%" + keyword + "%");
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから件数を取得
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				// 件数の返却
				return count;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

}
