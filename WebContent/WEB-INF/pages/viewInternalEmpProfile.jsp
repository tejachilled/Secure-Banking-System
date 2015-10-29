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
<title>View/Delete Internal Employee Profile</title>
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
		method="post" action="/RichirichBank/ViewInternalEmpProfile">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		<fieldset>
			<legend>Enter User Credentials to View/Delete Employee
				Details</legend>
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
					<button type="submit" class="btn btn-primary">View</button>
				</div>
			</div>

			<div>
				<div class="panel panel-default">
					<div class="panel-heading">First Name</div>
					<div class="panel-body">${accessInfo.firstName}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Last Name</div>
					<div class="panel-body">${accessInfo.lastName}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">User Name</div>
					<div class="panel-body">${accessInfo.userName}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Email ID</div>
					<div class="panel-body">${accessInfo.emaiID}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Address</div>
					<div class="panel-body">${accessInfo.address1}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Address</div>
					<div class="panel-body">${accessInfo.address2}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Role</div>
					<div class="panel-body">${accessInfo.role}</div>

				</div>
			</div>

			<c:if test="${not empty accessInfo.role }">
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-2">
						<input type="hidden" id="role" name="role"
							value="${accessInfo.role}"> <input type="submit"
							class="btn btn-primary" value="Delete" name="Delete"></input>
					</div>
				</div>
			</c:if>
		</fieldset>
	</form:form>

</body>
</html>