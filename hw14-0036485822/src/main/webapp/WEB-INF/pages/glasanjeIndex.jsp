<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<h1><c:out value="${poll.title}"></c:out></h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
	<c:forEach var="pollOption" items="${poll.options}">
	   <li><a href="glasanje-glasaj?pollID=${poll.id}&optionID=${pollOption.optionId}">${pollOption.optionTitle}</a></li>
	</c:forEach>
	</ol>
</body>
</html>