<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${sessionScope['current.user.id'] == null }">
		<p>not logged in</p>
	</c:if>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<p>
			<c:out value="${sessionScope['current.user.fn']}"></c:out>
			${sessionScope['current.user.ln']} <a href="${pageContext.request.contextPath}/servleti/logout">logout</a>
		</p>
	</c:if>
	<p>Successfully registered!</p>
	<a href="main">Login</a>
</body>
</html>