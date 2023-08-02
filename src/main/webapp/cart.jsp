<%@page import="la.model.Cart"%>
<%@page import="la.bean.ItemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Welcome shopping!e</title>
	<link rel="stylesheet" type="text/css" href="assets/css/style.css" />
</head>
<body>
	<header>
		<jsp:include page="/menu.jsp" />
	</header>
	<main>
		<article>
			<h3>現在のカートの中身</h3>
			<c:choose>
			<c:when test="${empty sessionScope.cart.items}">
				<p>現在、カートは空です。</p>
			</c:when>
			<c:otherwise>
				<table border="1">
					<tr>
						<th>NO</th>
						<th>商品名</th>
						<th>単価（税込）</th>
						<th>数量</th>
						<th>小計</th>
						<th>削除</th>
					</tr>
					<c:forEach items="${sessionScope.cart.items}" var="item">
					<tr>
						<td>${item.code}</td>
						<td>${item.name}</td>
						<td>${item.price}円</td>
						<td>${item.quantity}</td>
						<td>${item.price * item.quantity}円</td>
						<td>
							<form action="/shopping/CartServlet" method="post">
								<input type="hidden" name="action" value="delete" />
								<input type="hidden" name="code" value="${item.code}" />
								<input type="submit" value="削除" />
							</form>
						</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="6">総計：${cart.total}円</td>
					</tr>
				</table>
				<form action="/shopping/OrderServlet" method="post">
					<input type="hidden" name="action" value="input_customer" />
					<input type="submit" value="注文する" />
				</form>
			</c:otherwise>
			</c:choose>
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>