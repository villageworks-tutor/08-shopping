<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome shopping!</title>
	<link rel="stylesheet" type="text/css" href="assets/css/style.css" />
</head>
<body>
	<header>
		<jsp:include page="/menu.jsp" />
	</header>
	<main>
		<article>
			<h3>ご注文ありがとうございました。</h3>
			<p>お客様の注文番号は<span class="orderNumber">${requestScope.orderNumber}</span>になります。</p>
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>