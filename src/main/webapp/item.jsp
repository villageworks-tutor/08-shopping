<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Welcom shopping!</title>
</head>
<body>
	<header>
		<jsp:include page="/menu.jsp" />
	</header>
	<main>
		<article>
			<h3>商品詳細</h3>
			商品番号：<b>${item.code}</b><br />
			商品名：<b>${item.name}</b><br />
			価格（税込）：<b>${item.price}円</b><br />
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>