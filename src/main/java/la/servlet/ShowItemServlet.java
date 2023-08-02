package la.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import la.bean.CategoryBean;
import la.bean.ItemBean;
import la.dao.DAOException;
import la.dao.ItemDAO;

/**
 * Servlet implementation class ShowItemServlet
 */
@WebServlet("/ShowItemServlet")
public class ShowItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init() throws ServletException {
		try {
			// カテゴリー一覧を取得
			ItemDAO dao = new ItemDAO();
			List<CategoryBean> list = dao.findAllCategory();
			// 取得したカテゴリー一覧をアプリケーションスコープに登録
			getServletContext().setAttribute("categories", list);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// 処理分岐用actionキーを習得
		String action = request.getParameter("action");
		if (action == null || action.isEmpty() || action.equals("top")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/top.jsp");
			dispatcher.forward(request, response);
		} else if (action.equals("list")) {
			try {
				// リクエストパラメータを取得
				int categoryCode = Integer.parseInt(request.getParameter("code"));
				// 取得したカテゴリーコードに属する商品を取得
				ItemDAO dao = new ItemDAO();
				List<ItemBean> list = dao.findByCategory(categoryCode);
				// 取得した商品リストをリクエストスコープに登録
				request.setAttribute("items", list);
				// 画面遷移
				RequestDispatcher dispatcher = request.getRequestDispatcher("/list.jsp");
				dispatcher.forward(request, response);
			} catch (DAOException e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
		} else if (action.equals("detail")) {
			try {
				// リクエストパラメータの取得
				int code = Integer.parseInt(request.getParameter("code"));
				// 商品番号の商品を取得
				ItemDAO dao = new ItemDAO();
				ItemBean bean = dao.findByPrimaryKey(code);
				// 取得した商品をリクエストスコープに登録
				request.setAttribute("item", bean);
				// 画面遷移
				this.gotoPage(request, response, "/item.jsp");
			} catch (DAOException e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
		} else if (action.equals("search")) {
			try {
				// リクエストパラメータを取得
				String keyword = request.getParameter("keyword");
				// 商品名にキーワードを含む商品を取得
				ItemDAO dao = new  ItemDAO();
				List<ItemBean> list  = dao.findByName(keyword);
				// 取得した商品リストをリクエストスコープに登録して画面遷移
				request.setAttribute("items", list);
				this.gotoPage(request, response, "/list.jsp");
			} catch (DAOException e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
