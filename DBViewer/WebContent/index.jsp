<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sensei's Database Viewer | version 1.0.0 </title>
</head>
<body>

<!-- Header Image and tag line. -->
<h1><img alt="" src="http://tutorialsdojo.com/java/wp-content/uploads/sites/9/2014/08/tutorialsdojo-logo.png"> Sensei's DBViewer</h1>
<hr>

<!-- FORM -->

Enter the table name that you want to view: <br />
<form method="get">
<input type="text" name="tableName">
<input type="submit" value="SUBMIT">
<input type="submit" value="Show All Tables">
</form>

<hr>

<jsp:useBean id="DBViewer" class="com.tutorialsdojo.DBViewer"></jsp:useBean>

<% String tableName = request.getParameter("tableName");%>
<%=DBViewer.showTable(tableName) %> 

</body>
</html>