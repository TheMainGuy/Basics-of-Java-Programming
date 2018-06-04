<%@ page session="true"%>

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
	<h1>Choose background color :)</h1>
	<a href="setcolor?color=white">WHITE</a>
	<br>
	<a href="setcolor?color=red">RED</a>
	<br>
	<a href="setcolor?color=green">GREEN</a>
	<br>
	<a href="setcolor?color=cyan">CYAN</a>
	<br>
	<br>
	<a href="index.jsp">Index</a>

</body>
</html>