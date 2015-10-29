<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>User Home</title>
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
	<jsp:include page="headExt.jsp"></jsp:include>


	<c:if test="${not empty error}">
		<c:out value="${error}" />
	</c:if>
	<div>
		<h4>Please note that only the Address information, Phone number,
			email id and SSN (one time) is editable</h4>
		<form:form commandName="accessInfo"
			action="/RichirichBank/confirmUpdate" method="post"
			autocomplete="off">
			<c:if test="${piiExists  eq 'n'}">
				<div>
					<div class="panel panel-default">
						<div class="panel-heading">Enter Your SSN:</div>
						<input name="ssn" class="form-control" id="ssn"
							placeholder="Format: 'XXXXXXXXX'" required /> <label
							style="color: red">${error}</label>
					</div>
				</div>
			</c:if>
			<c:if test="${piiExists  eq 'y'}">
				<c:if test="${not empty success }">
					<div style="width: 40%;">
						<div class="alert alert-dismissable alert-success">
							<strong>${success}</strong>
						</div>
					</div>
				</c:if>
	    You have already updated your Personal Info!!! Please contact the bank for modifying it!!!
	    <div class="panel panel-default">
					<div class="panel-heading">SSN</div>
					<form:input path="ssn" readonly="true" />
				</div>
			</c:if>

			<div class="panel panel-default">
				<div class="panel-heading">User Name</div>
				<form:input path="userName" readonly="true" />
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">First Name</div>
				<form:input path="firstName" readonly="true" />
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Last Name</div>
				<form:input path="lastName" readonly="true" />
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Updated Email ID</div>
				<form:input path="emaiID" />
				<label style="color: red">${emailid}</label>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Address line1</div>
				<form:input path="address1" />
				<label style="color: red">${addresserror}</label>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Address line 2</div>
				<form:input path="address2" />
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Phone Number</div>
				<form:input path="phoneNumber" />
				<label style="color: red">${phoneNumber}</label>
			</div>
			<input type="submit" class="btn btn-primary" value="Update Details" />
		</form:form>
	</div>
</body>
</html>
