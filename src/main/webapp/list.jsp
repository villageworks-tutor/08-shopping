<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<div class="items">
				<c:forEach items="${requestScope.items}" var="item">
					<form action="CartServlet" method="post">
						商品番号：<b>${item.code}</b><br />
						商品名：<b>${item.name}</b><br />
						価格：<b>${item.price}</b><br />
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
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>