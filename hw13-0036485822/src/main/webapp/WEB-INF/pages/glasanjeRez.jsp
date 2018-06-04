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
<body
	bgcolor=<%if(session.getAttribute("pickedBgColor") == null) {
        out.print("white");
      } else {
        out.print(session.getAttribute("pickedBgColor"));
      }%>>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="bandVote" items="${sortedVotes}">
				<tr>
					<td>${bandVote.bandName}</td>
					<td>${bandVote.voteCount}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="winningBand" items="${winningBands}">
			<li><a href="${winningBand.song} }" target="_blank">${winningBand.bandName}</a></li>
		</c:forEach>
	</ul>
</body>
</body>

</html>