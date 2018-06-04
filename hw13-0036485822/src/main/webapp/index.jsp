<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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


	<a href="color.jsp">Background color chooser</a>
	<br>
	<br>

	<a href="trigonometric?a=0&b=90">Small table</a>
	<br>
	<br>

	<form action="trigonometric" method="GET">
		Početni kut:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> Završni kut:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>
	<br>
	<br>
	<a href="stories/funny.jsp">Funny story</a>
	<br>
	<br>
	<a href="report.jsp">Report</a>
	<br>
	<br>
	<a href="powers?a=1&b=100&n=3">Powers</a>
	<br>
    <br>
    <a href="appinfo.jsp">App info</a>
    <br>
    <br>
    <a href="glasanje">Glasanje</a>

</body>
</html>