<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Employee Profile</title>
</head>
<body>
	<jsp:include page="internalHome.jsp"></jsp:include>
	<form:form class="form-horizontal" commandName="accessInfo"
		method="post" action="/RichirichBank/EditInternalEmpProfile">
		<fieldset>
			<legend>Enter User name to Edit employee Details</legend>
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
			action="/RichirichBank/EditInternalEmpProfile" method="post">
			<c:if test="${not empty success }">
				<div style="width: 40%;">
					<div class="alert alert-dismissable alert-success">
						<strong>${success} </strong>
					</div>

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
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Phone Number</div>
				<form:input path="phoneNumber" />
				<label style="color: red">${phoneNumber}</label>
			</div>
			<input type="submit" value="Update Details" />
		</form:form>
	</div>

</body>
</html>