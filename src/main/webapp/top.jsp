<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Welcome Sample Shopping!</title>
	<link rel="stylesheet" type="text/css" href="/shopping/assets/css/style.css">
</head>
<body>
	<headr>
		<jsp:include page="/menu.jsp" />
	</headr>
	<main>
		<article>
			<h3>ようこそ！サンプルショッピングサイトへ</h3>
			<p>このサイトは教材用として作成されています。</p>
			<p>デザインなどは各自工夫してみましょう。</p>
		</article>
	</main>
	<footer>
		<jsp:include page="/copyright.jsp" />
	</footer>
</body>
</html>