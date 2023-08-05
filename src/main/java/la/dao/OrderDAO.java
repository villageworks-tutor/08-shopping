package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import la.bean.CustomerBean;
import la.bean.ItemBean;
import la.model.Cart;

/**
 * 注文確定処理に関するDAO
 */
public class OrderDAO {

	/**
	 * クラス定数：データベース接続情報文字列定数群
	 */
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql:sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	/**
	 * フィールド：データベース接続オブジェクト */
	private Connection con;

	/**
	 * コンストラクタ
	 * @throws DAOException
	 */
	public OrderDAO() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
			this.con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの読込みに失敗しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベースの接続に失敗しました。");
		}
	}

	public int order(CustomerBean customer, Cart cart) throws DAOException {
		// 最新の顧客番号の初期化
		int customerNumber = 0;
		// customerテーブルの最新の顧客番号を取得
		String sql = "SELECT nextval('customer_code_seq')";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();) {
			// 結果セットから最新のシーケンスを取得
			if (rs.next()) {
				customerNumber = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("シーケンスの取得に失敗しました。");
		}
		// customerテーブルに顧客を登録
		sql = "INSERT INTO customer (code, name, address, tel, email) VALUES (?, ?, ?, ?, ?)";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, customerNumber);
			pstmt.setString(2, customer.getName());
			pstmt.setString(3, customer.getAddress());
			pstmt.setString(4, customer.getTel());
			pstmt.setString(5, customer.getEmail());
			// SQLの実行
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		}
		
		// 最新の注文番号の初期化
		int orderNumber = 0;
		// 最新の注文番号を取得
		sql = "SELECT nextval('ordered_code_seq')";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();) {
			// 結果セットから最新の注文番号を取得
			if (rs.next()) {
				orderNumber = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("シーケンスの取得に失敗しました。");
		}
		// orderテーブルに注文を登録
		sql = "INSERT INTO ordered (code, customer_code, ordered_date, total_price) VALUES (?, ?, ?, ?)";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, orderNumber);
			pstmt.setInt(2, customerNumber);
			pstmt.setDate(3, new Date(System.currentTimeMillis()));
			pstmt.setInt(4, cart.getTotal());
			// SQLの実行
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		}
		
		// 注文明細の登録
		sql = "INSERT INTO ordered_detail (ordered_code, item_code, num) VALUES (?, ?, ?)";
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// カート内商品リストを取得
			List<ItemBean> items = cart.getItems();
			for (ItemBean item : items) {
				pstmt.setInt(1, orderNumber);
				pstmt.setInt(2, item.getCode());
				pstmt.setInt(3, item.getQuantity());
				// SQLの実行
				pstmt.executeUpdate();
			}
			// 注文番号を返却
			return orderNumber;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		}
		
	}

	public int order(int customerNumber, Cart cart) throws DAOException {
		// セッションから顧客番号を取得
		// 最新の注文番号の初期化
		int orderNumber = 0;
		// 最新の注文番号を取得
		String sql = "SELECT nextval('ordered_code_seq')";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();) {
			// 結果セットから最新の注文番号を取得
			if (rs.next()) {
				orderNumber = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("シーケンスの取得に失敗しました。");
		}
		// orderテーブルに注文を登録
		sql = "INSERT INTO ordered (code, customer_code, ordered_date, total_price) VALUES (?, ?, ?, ?)";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, orderNumber);
			pstmt.setInt(2, customerNumber);
			pstmt.setDate(3, new Date(System.currentTimeMillis()));
			pstmt.setInt(4, cart.getTotal());
			// SQLの実行
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		}
		
		// 注文明細の登録
		sql = "INSERT INTO ordered_detail (ordered_code, item_code, num) VALUES (?, ?, ?)";
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.con.prepareStatement(sql);) {
			// カート内商品リストを取得
			List<ItemBean> items = cart.getItems();
			for (ItemBean item : items) {
				pstmt.setInt(1, orderNumber);
				pstmt.setInt(2, item.getCode());
				pstmt.setInt(3, item.getQuantity());
				// SQLの実行
				pstmt.executeUpdate();
			}
			// 注文番号を返却
			return orderNumber;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		}
	}

}
