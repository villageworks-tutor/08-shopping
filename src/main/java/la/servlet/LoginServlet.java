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
import la.dao.CustomerDAO;
import la.dao.DAOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// 処理分岐用actionキーを取得
		String action = request.getParameter("action");
		if (action == null || action.isEmpty()) {
			// リクエストパラメータが送信されていない場合
			this.gotoPage(request, response, "/login.jsp");
		} else if (action.equals("login")) {
			try {
				// リクエストパラメータを取得
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				// リクエストパラメータを利用して顧客インスタンスを取得
				CustomerDAO dao = new CustomerDAO();
				CustomerBean customer = dao.findByEmailAndPassword(email, password);
				if (customer == null) {
					// 顧客インスタンスを取得できない場合：メッセージを表示してログイン画面に遷移
					request.setAttribute("message", "メールアドレスとパスワードが一致しませんでした。");
					this.gotoPage(request, response, "/login.jsp");
					return;
				}
				// 顧客インスタンスをセッションスコープに登録
				HttpSession session = request.getSession();
				session.setAttribute("customer", customer);
				// 画面遷移
				response.sendRedirect("ShowItemServlet");
			} catch (DAOException e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
		} else if (action.equals("logout")) {
			// セッションを取得
			HttpSession session = request.getSession(false);
			if (session == null) {
				this.gotoPage(request, response, "/login.jsp");
				return;
			}
			// ログイン情報を取得
			CustomerBean customer = (CustomerBean) session.getAttribute("customer");
			if (customer == null) {
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
				return;
			}
			// セッションスコープのcustomerキーを削除
			session.removeAttribute("customer");
			// セッションスコープを解放
			session.invalidate();
			// ログイン画面に遷移
			this.gotoPage(request, response, "/login.jsp");
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
