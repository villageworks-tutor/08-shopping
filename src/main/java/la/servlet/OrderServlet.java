package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.CustomerBean;
import la.dao.DAOException;
import la.dao.OrderDAO;
import la.model.Cart;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 既存のセッションを開始
		HttpSession session = request.getSession(false);
		if (session == null) {
			// セッションがない場合
			request.setAttribute("message", "セッションが切れています。もう一度トップページから操作してください。");
			this.gotoPage(request, response, "/errInternal.jsp");
			return;
		}
		// セッションからカートを取得
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			// カートがない場合：不正な操作とみなす
			request.setAttribute("message", "正しく操作してください。");
			this.gotoPage(request, response, "/errInternal.jsp");
			return;
		}
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// 処理分岐用actionキーを取得
		String action = request.getParameter("action");
		if (action == null || action.isEmpty() || action.equals("input_customer")) {
			// actionキーが送信されないかまたは「input_customer」である場合
			this.gotoPage(request, response, "/customerInfo.jsp");
		} else if (action.equals("confirm")) {
			// リクエストパラメータを取得
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			// 取得したパラメータから顧客インスタンスをセッションスコープに登録
			CustomerBean bean = new CustomerBean(name, address, tel, email);
			session.setAttribute("customer", bean);
			// 画面遷移
			this.gotoPage(request, response, "/confirm.jsp");
		} else if (action.equals("order")) {
			try {
				// セッションから顧客を取得
				CustomerBean customer = (CustomerBean) session.getAttribute("customer");
				if (customer == null) {
					request.setAttribute("message", "正しく操作してください。");
					this.gotoPage(request, response, "/errInternal.jsp");
					return;
				}
				// 注文確定処理の実行
				OrderDAO dao = new OrderDAO();
				int orderNumber = dao.order(customer, cart);
				// セッションスコープのキーを削除
				session.removeAttribute("customer");
				session.removeAttribute("cart");
				// 注文番号をリクエストスコープに登録して画面遷移
				request.setAttribute("orderNumber", orderNumber);
				this.gotoPage(request, response, "order.jsp");
			} catch (DAOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
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
