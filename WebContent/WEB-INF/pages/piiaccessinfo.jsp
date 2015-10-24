<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<style>
table {
	width: 100%;
}

td {
	border: 1px black solid;
	padding: 10px;
}
</style>
</head>
<body>
	<div style="width: 100%; text-align: center">
		<h1>${bank_name}</h1>
		<h2>PII Access Information</h2>
		<br> <br> <br>
		<table>
			<tr>
				<td><b>USERNAME</b></td>
				<td><b>SSN</b></td>
			</tr>
			<c:forEach var="piiAccessInfo" items="${piiAccessInfoList}">
				<tr>
					<td>${piiAccessInfo.userName}</td>
					<td>${piiAccessInfo.pii}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>