<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
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
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>
</head>
<script>
	document.onmousedown = disableclick;
	status = "Right Click Disabled";
	function disableclick(event) {
		if (event.button == 2) {
			alert(status);
			return false;
		}
	}
</script>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<sec:authorize access="hasRole('ROLE_SA')">
		<div class="btn-group btn-group-justified">
			<sec:authorize access="hasAnyRole('ROLE_SA')">
				<a href="/RichirichBank/newPiiRequest" class="btn btn-default">New
					PII Request</a>
				<a href="/RichirichBank/piiaccessinfo" class="btn btn-default">PII
					Access Info</a>
			</sec:authorize>
		</div>
		<div style="width: 100%; text-align: center">
			<h1>${bank_name}</h1>
			<h2>PII Access Information</h2>
			<br> <br> <br>
			<c:if test="${fn:length(piiAccessInfoList) gt 0}">
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
			</c:if>

			<c:if test="${fn:length(piiAccessInfoList) eq 0}">
			None of your PII Access Requests have been approved yet! Please try again sometime later.
		</c:if>
		</div>
	</sec:authorize>
</body>
</html>