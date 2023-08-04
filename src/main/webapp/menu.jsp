<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
	<a href="/shopping">ようこそ</a> | 
	<c:forEach items="${applicationScope.categories}" var="category">
		<a href="/shopping/ShowItemServlet?action=list&code=${category.code}&page=1">${category.name}</a> | 
	</c:forEach>
	こんにちは、${sessionScope.customer.name}さん | 
	<a href="/shopping/CartServlet?action=show">カートを見る</a>
	<form action="ShowItemServlet" method="get">
		<input type="text" name="keyword" />
		<input type="hidden" name="page" value="1" />
		<input type="hidden" name="action" value="search" />
		<input type="submit" value="検索" />
	</form>
</nav>
