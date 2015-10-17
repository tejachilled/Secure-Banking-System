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
		<h2>PII Request History and Status</h2>
		<br> <br> <br>
		<c:if test="${error}">
			<p>Please try again!</p>
		</c:if>
		<table>
			<tr>
				<td></td>
				<td><b>PERSON REQUESTING</b></td>
				<td><b>EXTERNAL USER'S USERNAME</b></td>
				<td><b>STATUS</b></td>
			</tr>

			<form:form method="post" action="/RichirichBank/acceptRequests"
				modelAttribute="govtAction">

				<c:forEach var="govtRequest" items="${govtRequestsList}">
					<tr>
						<td><form:checkbox path="checkboxList" value="${govtRequest}" /></td>
						<td>${govtRequest.internalUserName}</td>
						<td>${govtRequest.externalUserName}</td>
						<c:if test="${govtRequest.status eq 'a'.charAt(0)}">
							<td>Accepted</td>
						</c:if>
						<c:if test="${govtRequest.status eq 'p'.charAt(0)}">
							<td>Pending</td>
						</c:if>
					</tr>
				</c:forEach>
				<tr>
					<td><input type="submit" name="accept"
						value="Accept Request(s)" /></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>