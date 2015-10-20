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

<title>User Home</title>
</head>
<body>
	<jsp:include page="headExt.jsp"></jsp:include>
	<div class="btn-group btn-group-justified">
	<sec:authorize access="hasAnyRole('ROLE_SM','ROLE_RE', 'ROLE_SA')">
		<a href="/RichirichBank/viewMyProfile" class="btn btn-default">View</a>
		<a href="/RichirichBank/requestEdit" class="btn btn-default">Edit
			Request</a> <a href="/RichirichBank/transactionReviewRequest"
			class="btn btn-default">Review Transactions Request</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_M')">
		<a href="/RichirichBank/viewMyProfile" class="btn btn-default">View</a>
		<a href="/RichirichBank/requestEdit" class="btn btn-default">Edit
			Request</a> <a href="/RichirichBank/transactionHistoryM"
			class="btn btn-default">Review Transactions Request</a>
	</sec:authorize>
	</div>
</body>
</html>
