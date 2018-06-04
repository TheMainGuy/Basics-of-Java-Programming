<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body
	bgcolor=<%if(session.getAttribute("pickedBgColor") == null) {
        out.print("white");
      } else {
        out.print(session.getAttribute("pickedBgColor"));
      }%>>
	<a href="index.jsp">Index</a>

	<table>
		<thead>
			<tr>
				<th>Number</th>
				<th>Sin</th>
				<th>Cos</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="trigonometric" items="${trigonometricInfo}">
				<tr>
					<td>${trigonometric.number}</td>
					<td>${trigonometric.sin}</td>
					<td>${trigonometric.cos }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>