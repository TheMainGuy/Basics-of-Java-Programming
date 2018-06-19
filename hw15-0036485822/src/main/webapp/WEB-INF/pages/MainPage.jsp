<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>
<body>
	<c:if test="${sessionScope['current.user.id'] == null }">
		<p>not logged in</p>
	</c:if>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<p>
			<c:out value="${sessionScope['current.user.fn']}"></c:out>
			${sessionScope['current.user.ln']} <a
				href="${pageContext.request.contextPath}/servleti/logout">logout</a>
		</p>
	</c:if>
	<br>
	<c:if test="${sessionScope['current.user.fn'] != null}">
	   <h1>Welcome to simple blog site!</h1>
	</c:if>
	<c:if test="${sessionScope['current.user.fn'] == null}">
		<form action="checkLogin" method="post">
			<div>
				<div>
					<span class="formLabel">nick</span><input type="text" name="nick"
						value='<c:out value="${form.nick}"/>' size="20">
					<c:if test="${form.hasError('nick')}">
						<div class="greska">
							<c:out value="${form.getError('nick')}" />
						</div>
					</c:if>
				</div>
			</div>

			<div>
				<div>
					<span class="formLabel">password</span><input type="password"
						name="password" size="20">
				</div>
				<c:if test="${form.hasError('password')}">
					<div class="greska">
						<c:out value="${form.getError('password')}" />
					</div>
				</c:if>
			</div>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="method" value="Log in"> <input type="submit"
					name="method" value="Cancel">
			</div>
		</form>
		<br>
		<br>
		<a href="register">Sign up!</a>
	</c:if>

	<br>
	<p>List of Authors:</p>
	<c:forEach var="author" items="${authors}">
		<br>
		<a href="author/${author}"><c:out value="${author}"></c:out></a>
	</c:forEach>
</body>
</html>