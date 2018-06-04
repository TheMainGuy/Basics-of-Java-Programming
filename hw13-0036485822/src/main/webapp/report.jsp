<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body
	bgcolor=<%if(session.getAttribute("pickedBgColor") == null) {
        out.print("white");
      } else {
        out.print(session.getAttribute("pickedBgColor"));
      }%>>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img src="reportImage" />
	<br>
	<a href="index.jsp">Index</a>
</body>
</html>