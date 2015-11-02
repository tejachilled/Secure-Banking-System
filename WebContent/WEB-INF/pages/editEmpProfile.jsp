<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Employee Profile</title>
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
	<jsp:include page="internalHome.jsp"></jsp:include>
	<form:form class="form-horizontal" commandName="accessInfo"
		method="post" action="/RichirichBank/EditEmpProfile">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		<fieldset>
			<legend>Enter User name to Edit Account Details</legend>
			<c:if test="${not empty success }">
				<div style="width: 40%;">
					<div class="alert alert-dismissable alert-success">
						<strong>${success}</strong>
					</div>

				</div>
			</c:if>
			<div>
				<div class="col-lg-10">
					<form:input path="userName" class="form-control" id="usernameid"
						placeholder="User Name" />
					<label style="color: red">${usernameerror}</label>
				</div>

			</div>
			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</fieldset>
	</form:form>
	<div>
		<h4>Please note that only the Address information, Phone number
			and email id is editable</h4>
		<form:form commandName="accessInfo"
			action="/RichirichBank/EditEmpProfile" method="post">

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
				<div class="panel-heading">Role</div>
				<form:input path="role" readonly="true" />
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Modified Email ID</div>
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
				<label style="color: red">${addresserror2}</label>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Phone Number</div>
				<input type="number" id="phoneNumber" name="phoneNumber" class="form-control" min ="1" max ="9999999999" required />
								
				<label style="color: red">${phoneNumber}</label>
			</div>
			<input type="submit" class="btn btn-primary" value="Update Details" />
		</form:form>
	</div>

</body>
</html>