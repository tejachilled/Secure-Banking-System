<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>Home</title>
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
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<sec:authorize access="hasAnyRole('ROLE_RE','ROLE_SM')">
		<div class="btn-group btn-group-justified">
			<a href="/RichirichBank/viewMyIntProfile" class="btn btn-default">View</a>
			<sec:authorize access="hasRole('ROLE_RE')">
				<a href="/RichirichBank/pendingTransactionsRE"
					class="btn btn-default">Authorize Transactions </a>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_SM')">
				<a href="/RichirichBank/pendingTransactionsSM"
					class="btn btn-default">Authorize Transactions (Critical)</a>
			</sec:authorize>
		</div>
	</sec:authorize>
</body>
</html>