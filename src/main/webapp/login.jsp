<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>ログイン</title>
</head>
<body>
	<h3>ログイン</h3>
	<c:if test="${!empty requestScope.message}">
		<p class="message">${requestScope.message}</p>
	</c:if>
	<form action="/shopping/LoginServlet" method="post">
		メールアドレス：<input type="text" name="email" /><br />
		パスワード：<input type="text" name="password" /><br />
		<input type="hidden" name="action" value="login" />
		<input type="submit" value="ログイン" />
	</form>
</body>
</html>