<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.middleDiv {
	position: absolute;
	width: 200px;
	height: 200px;
	left: 50%;
	top: 50%;
	margin-left: -100px; /* half of the width  */
	margin-top: -100px; /* half of the height */
	font-size: 40px;
}
</style>
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<title>Admin Home</title>
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
	<div class="btn-group btn-group-justified">
		<sec:authorize access="hasAnyRole('ROLE_SA')">
			<a href="/RichirichBank/newPiiRequest" class="btn btn-default">New
				PII Request</a>
			<a href="/RichirichBank/piiaccessinfo" class="btn btn-default">PII
				Access Info</a>
		</sec:authorize>
	</div>
	<div class="middleDiv">Welcome Admin :)</div>
</body>
</html>
