<%@page import="la.model.Pagination"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// ページネーション生成用サンプルデータ：これでうまく行けば、Paginationをスコープに登録すれば表示可能！
List<Pagination> pages = new ArrayList<>();
pages.add(new Pagination("list", "1", "", "2"));   // カテゴリーによる商品一覧表示の場合のサンプル
pages.add(new Pagination("search", "", "B", "3")); // キーワード検索による商品一覧表示の場合のサンプル
request.setAttribute("pages", pages);
request.setAttribute("paginations", pages.get(0));
%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>商品一覧</title>
</head>
<body>
	<header>
		<jsp:include page="/menu.jsp" />
	</header>
	<main>
		<article>
			<h3>商品一覧</h3>
			<c:if test="${!empty requestScope.count}">
				<p>${requestScope.count}件の商品が存在しました。</p>
			</c:if>
			<div class="items">
				<c:forEach items="${requestScope.items}" var="item">
					<form action="CartServlet" method="post">
						商品番号：<b>${item.code}</b><br />
						商品名：<b>${item.name}</b><br />
						価格：<b>${item.price}円</b><br />
						数量：<b>
						<select name="quantity">
							<% for (int i = 0; i < 5; i++) { %>
								<option value="<%= i + 1 %>"><%= i + 1 %></option>
							<% } %>
						</select>個
						</b><br />
						<a href="ShowItemServlet?action=detail&code=${item.code}">詳細</a><br />
						<input type="hidden" name="action" value="add" />
						<input type="hidden" name="code" value="${item.code}" />
						<input type="submit" value="カートに追加" />
					</form>
					<br />
				</c:forEach>
			</div>
			<div class="pagination">
				<c:forEach var="page" begin="1" end="${requestScope.pagination.maxPage}" step="1">
					<a href="/shopping/ShowItemServlet?action=${requestScope.pagination.action}&code=${requestScope.pagination.category}&keyword=${requestScope.pagination.keyword}&page=${page}">${page}</a>
				</c:forEach>
			</div>
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>