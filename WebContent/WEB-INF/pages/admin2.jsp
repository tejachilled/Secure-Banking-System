<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>	
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>Home</title>
</head>
<body>
<jsp:include page="navBar.jsp"></jsp:include>
<%-- <jsp:include page="managementBar.jsp"></jsp:include> --%>
<div class="btn-group btn-group-justified">
    <a href="/Sparkys-Bank/UserAccountManagement" class="btn btn-default">View</a>
  	<a href="/Sparkys-Bank/EditExtProfile" class="btn btn-default">Edit</a>
  	<!-- <a href="/Sparkys-Bank/UserAccountManagementEdit" class="btn btn-default">Edit</a> -->
	<!-- <a href="/Sparkys-Bank/UserAccountManagementDelete" class="btn btn-default">Delete</a> -->
	<a href="/Sparkys-Bank/UserAccountManagementActivity" class="btn btn-default">Review Transactions</a>
	<a href="/Sparkys-Bank/ExtUserRequests" class="btn btn-default">External User Requests</a>
	<a href="/Sparkys-Bank/AuthorizeTransactions" class="btn btn-default">Authorize Transactions</a>
	<a href="/Sparkys-Bank/ExtUserProfileViewReq" class="btn btn-default">Profile View Request</a>
 </div>
<%-- <a href="<c:url value='/viewTransactions' />" >ViewTransaction</a> --%>
</body>
</html>
