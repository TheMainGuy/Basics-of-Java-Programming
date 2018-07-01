<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
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
	<a href="${pageContext.request.contextPath}/servleti/main">Home</a>
	<a
		href="${pageContext.request.contextPath}/servleti/author/${selectedUser.nick}">Back
		to posts</a>

<br>
	<c:choose>
		<c:when test="${blogEntry==null}">
      No entry with given id!
    </c:when>
		<c:otherwise>
			<h1>
				<c:out value="${blogEntry.title}" />
			</h1>
			<p>
				<c:out value="${blogEntry.text}" />
			</p>
			<br>
			<c:if
				test="${sessionScope['current.user.nick'] == selectedUser.nick}">
				<a href="edit?id=${blogEntry.id}">Edit Post</a>
			</c:if>

			<c:if test="${!blogEntry.comments.isEmpty()}">
				<ul>
					<c:forEach var="e" items="${blogEntry.comments}">
						<li><div style="font-weight: bold">
								[User=
								<c:out value="${e.usersEMail}" />
								]
								<c:out value="${e.postedOn}" />
							</div>
							<div style="padding-left: 10px;">
								<c:out value="${e.message}" />
							</div></li>
					</c:forEach>
				</ul>
			</c:if>


			<form action="postComment" method="post">

				<div>
					<div>
						<input type="hidden" name="entryId" value="${blogEntry.id}" />
					</div>
				</div>

				<div>
					<div>
						<input type="hidden" name="userNick"
							value="${sessionScope['current.user.nick']}" />
					</div>
				</div>

				<c:if test="${sessionScope['current.user.nick'] == null}">
					<div>
						<div>
							<span class="formLabel">Email</span><input type="text"
								name="email" />
						</div>
						<c:if test="${form.hasError('email')}">
							<div class="greska">
								<c:out value="${form.getError('email')}" />
							</div>
						</c:if>
					</div>
				</c:if>

				<div>
					<div>
						<span class="formLabel">Comment</span><input type="text"
							name="comment" />
					</div>
					<c:if test="${form.hasError('comment')}">
						<div class="greska">
							<c:out value="${form.getError('comment')}" />
						</div>
					</c:if>
				</div>

				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="method" value="Comment">
				</div>

			</form>
		</c:otherwise>
	</c:choose>

</body>
</html>
