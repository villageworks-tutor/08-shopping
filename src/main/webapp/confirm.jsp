<%@ page import="la.bean.CustomerBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Welcome shopping!</title>
	<link rel="stylesheet" type="text/css" href="assets/css/style.css">
</head>
<body>
	<header>
		<jsp:include page="/menu.jsp" />
	</header>
	<main>
		<article>
			<section class="items">
				<h3>ご注文商品</h3>
				<c:if test="${!empty sessionScope.cart.items}">
				<table border="1">
					<tr>
						<th>NO</th>
						<th>商品名</th>
						<th>単価（税込）</th>
						<th>個数</th>
						<th>小計</th>
					</tr>
					<c:forEach items="${sessionScope.cart.items}" var="item">
					<tr>
						<td>${item.code}</td>
						<td>${item.name}</td>
						<td>${item.price}円</td>
						<td>${item.quantity}</td>
						<td>${item.price * item.quantity}円</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="5">総計：${sessionScope.cart.total}円</td>
					</tr>
				</table>
				</c:if>
			</section>
			<section class="customer">
				<h3>お客様情報を入力してください。</h3>
				<form name="customerForm" action="OrderServlet" method="post">
					<table border="1">
						<tr>
							<th>お名前</th>
							<td>${sessionScope.customer.name}</td>
						</tr>
						<tr>
							<th>ご住所</th>
							<td>${sessionScope.customer.address}</td>
						</tr>
						<tr>
							<th>お電話番号</th>
							<td>${sessionScope.customer.tel}</td>
						</tr>
						<tr>
							<th>電子メールアドレス</th>
							<td>${sessionScope.customer.email}</td>
						</tr>
					</table>
					<input type="hidden" name="action" value="order" />
					<input type="submit" value="この内容で注文" />
				</form>			
			</section>
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>