<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>

</head>
<body
	bgcolor=<%if(session.getAttribute("pickedBgColor") == null) {
        out.print("white");
      } else {
        out.print(session.getAttribute("pickedBgColor"));
      }%>>
	<p>
		<font
			color=<%double color = Math.random();
      if(color < 0.2) {
        out.print("red");
      } else if(color < 0.4) {
        out.print("blue");
      } else if(color < 0.6) {
        out.print("green");
      } else if(color < 0.8) {
        out.print("orange");
      } else {
        out.print("black");
      }%>>In
			my junior year of high school, this guy asked me on a date. He rented
			a Redbox movie and made a pizza. We were watching the movie and the
			oven beeped so the pizza was done. He looked me dead in the eye and
			said, “This is the worst part.” I then watched this boy open the oven
			and pull the pizza out with his bare hands, rack and all, screaming
			at the top of his lungs. We never had a second date.</font>
	</p>
	<br>
	<a href="../index.jsp">Index</a>
</body>
</html>