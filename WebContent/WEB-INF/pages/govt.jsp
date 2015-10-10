<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Govt User!</title>
</head>
<body>
<h1>${bank_name}</h1>
<h2>PII Request History and Status</h2>
<table style="width:100%">
   
   <c:forEach var="govtRequest" items="${govtRequestsList}">
    <tr>
     <td>${govtRequest.externalUserName}</td>
     <td>${govtRequest.internalUserName}</td>
     <td>${govtRequest.status}</td>
    </tr>
   </c:forEach>
   
</table>
</body>
</html>