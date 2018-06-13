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
<body>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Odabir glasanja</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="vote" items="${poll.options}">
				<tr>
					<td>${vote.optionTitle}</td>
					<td>${vote.votesCount}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${poll.id}" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${poll.id}">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="winner" items="${winners}">
			<li><a href="${winner.optionLink} }" target="_blank">${winner.optionTitle}</a></li>
		</c:forEach>
	</ul>
</body>
</body>

</html>