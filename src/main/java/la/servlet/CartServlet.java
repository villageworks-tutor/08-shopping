package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.ItemBean;
import la.dao.DAOException;
import la.dao.ItemDAO;
import la.model.Cart;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// 処理分岐用actionキーを取得
		String action = request.getParameter("action");
		if (action == null || action.isEmpty() || action.equals("show")) {
			this.gotoPage(request, response, "/cart.jsp");
		} else if (action.equals("add")) {
			// セッションを開始
			HttpSession session = request.getSession();
			// セッションからカートを取得
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				// カートがnullの場合：はじめてカートに商品を追加することになるのでカートを生成しておく
				cart = new Cart();
				//  セッションに登録
				session.setAttribute("cart", cart);
			}
			// リクエストパラメータを取得
			int code = Integer.parseInt(request.getParameter("code"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			try {
				// 取得した商品コードの商品を取得
				ItemDAO dao = new ItemDAO();
				ItemBean bean = dao.findByPrimaryKey(code);
				// 取得した商品に数量を設定
				bean.setQuantity(quantity);
				// カートに追加
				cart.add(bean);
				// 画面遷移
				this.gotoPage(request, response, "/cart.jsp");
			} catch (DAOException e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
		} else if (action.equals("delete")) {
			// 既存のセッションを取得
			HttpSession session = request.getSession(false);
			if (session == null) {
				request.setAttribute("message", "セッションが切れています。もう一度トップページより操作してください。");
				this.gotoPage(request, response, "/errInternal.jsp");
				return;
			}
			// カートを取得
			Cart cart  = (Cart) session.getAttribute("cart");
			if  (cart == null) {
				// カートがない場合：不正な操作とみなす
				request.setAttribute("message", "正しく操作してください。");
				this.gotoPage(request, response, "/errInternal.jsp");
				return;
			}
			
			// リクエストパラメータを取得
			int code  = Integer.parseInt(request.getParameter("code"));
			// 取得した商品コードの商品をカートから削除
			cart.delete(code);
			// カート画面に遷移
			this.gotoPage(request, response, "/cart.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 指定されたURLに遷移する
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param string 遷移先URL
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String nextURL) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
		dispatcher.forward(request, response);
	}

}
