<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
	<a href="/shopping">ようこそ</a> | 
	<c:forEach items="${applicationScope.categories}" var="category">
		<a href="/shopping/ShowItemServlet?action=list&code=${category.code}">${category.name}</a> | 
	</c:forEach>
	<a href="/shopping/CartServlet?action=show">カートを見る</a>
</nav>