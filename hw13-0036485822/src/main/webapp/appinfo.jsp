<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body
	bgcolor=<%if(session.getAttribute("pickedBgColor") == null) {
        out.print("white");
      } else {
        out.print(session.getAttribute("pickedBgColor"));
      }%>>
	<%
	  ServletContext context = pageContext.getServletContext();
	  long time = Calendar.getInstance().getTimeInMillis() - Long.parseLong(context.getAttribute("time").toString());
	  int seconds = (int) time / 1000;
	  int minutes = seconds / 60;
	  int hours = minutes / 60;
	  int days = hours / 24;
	  out.print("This application has been running for: " + days + " days " + hours % 24 + " hours " + minutes % 60
	      + " minutes " + seconds % 60 + " seconds " + time % 1000 + " miliseconds.");
	%>
	<br>
	<br>
	<a href="index.jsp">Index</a>
</body>
</html>