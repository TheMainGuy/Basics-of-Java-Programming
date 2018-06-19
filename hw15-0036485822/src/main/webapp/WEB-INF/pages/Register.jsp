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
	<form action="addUser" method="post">

		<div>
			<div>
				<span class="formLabel">First Name</span><input type="text"
					name="firstName" value='<c:out value="${form.firstName}"/>'
					size="5">
			</div>
			<c:if test="${form.hasError('firstName')}">
				<div class="greska">
					<c:out value="${form.getError('firstName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Last Name</span><input type="text"
					name="lastName" value='<c:out value="${form.lastName}"/>' size="20">
			</div>
			<c:if test="${form.hasError('lastName')}">
				<div class="greska">
					<c:out value="${form.getError('lastName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Email</span><input type="text" name="email"
					value='<c:out value="${form.email}"/>' size="20">
			</div>
			<c:if test="${form.hasError('email')}">
				<div class="greska">
					<c:out value="${form.getError('email')}" />
				</div>
			</c:if>
		</div>

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
				name="method" value="SignUp"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

</body>
</html>