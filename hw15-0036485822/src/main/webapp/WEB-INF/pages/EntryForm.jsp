<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Entry</title>

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
	<h1>
		<c:choose>
			<c:when test="${form.id == 0}">
                New Entry
            </c:when>
			<c:otherwise>
                Edit Entry
            </c:otherwise>
		</c:choose>
	</h1>

	<form action="save" method="post">

		<div>
			<div>
				<input type="hidden" name="id" value='<c:out value="${form.id}"/>'>
			</div>
		</div>

		<div>
			<div>
				<span class="formLabel">Entry Title</span><input type="text"
					name="title" value='<c:out value="${form.title}"/>' size="20">
			</div>
			<c:if test="${form.hasError('title')}">
				<div class="greska">
					<c:out value="${form.getError('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Entry Text</span><input type="text"
					name="text" value='<c:out value="${form.text}"/>' size="20">
			</div>
			<c:if test="${form.hasError('text')}">
				<div class="greska">
					<c:out value="${form.getError('text')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Save"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

</body>
</html>
