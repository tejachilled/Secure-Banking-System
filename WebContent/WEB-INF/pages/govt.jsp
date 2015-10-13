<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Govt User!</title>
<style>
table {
	width: 100%;
}

td, th {
	border: 1px black solid;
	padding: 10px;
}

th {
	font-weight: bold;
}
}
</style>
</head>
<body>
	<h1>${bank_name}</h1>
	<div style="width: 100%; text-align: center">
		<br> <br> <br>
		<h2>PII Request History and Status</h2>
		<br> <br> <br>
		<table>
			<tr>
				<td></td>
				<td><b>PERSON REQUESTING</b></td>
				<td><b>EXTERNAL USER'S USERNAME</b></td>
				<td><b>STATUS</b></td>
			</tr>

			<form:form method="post" action="/RichirichBank/govtAction"
				modelAttribute="govtAction">
				<c:forEach var="govtRequest" items="${govtRequestsList}">
					<tr>
						<td><form:checkbox path="checkboxList" 
						
						checkboxList must be in govtRequestsModel !!
						
								id="${govtRequest.internalUserName}_${govtRequest.externalUserName}" />
						</td>
						<td>${govtRequest.internalUserName}</td>
						<td>${govtRequest.externalUserName}</td>
						<c:if test="${govtRequest.status eq 'a'.charAt(0)}">
							<td>Accepted</td>
						</c:if>
						<c:if test="${govtRequest.status eq 'r'.charAt(0)}">
							<td>Rejected</td>
						</c:if>
						<c:if test="${govtRequest.status eq 'p'.charAt(0)}">
							<td>Pending</td>
						</c:if>
					</tr>
				</c:forEach>

				<tr>
					<td><a href="govtAction"><img src="img/a.png"></a> <input
						type="button" name="accept" value="Accept Request(s)" /></td>
				</tr>
				<tr>
					<td><input type="button" name="reject"
						value="Reject Request(s)" /></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>