<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<%
		  @SuppressWarnings("unchecked")
		  List<String> bandList = (List<String>) request.getServletContext().getAttribute("bandList");
		  for (String band : bandList) {
		    out.print(
		        "<li><a href=\"glasanje-glasaj?id=" + band.split("\t")[0] + "\">" + band.split("\t")[1] + "</a></li>");
		  }
		%>

	</ol>
</body>
</html>