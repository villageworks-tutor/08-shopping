<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>ログイン</title>
</head>
<body>
	<h3>ログイン</h3>
	<form action="/shopping/LoginSevlet" method="post">
		メールアドレス：<input type="text" name="email" /><br />
		パスワード：<input type="text" name="password" /><br />
		<input type="hidden" name="action" value="login" />
		<input type="submit" value="ログイン" />
	</form>
</body>
</html>