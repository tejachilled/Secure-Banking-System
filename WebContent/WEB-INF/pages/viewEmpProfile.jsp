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
<title>View Employee Profile</title>
</head>
<body>
	<jsp:include page="internalHome.jsp"></jsp:include>
	<form:form class="form-horizontal" commandName="accessInfo"
		method="post" action="/RichirichBank/ViewEmpProfile">
		<fieldset>
			<legend>Enter User Credentials to Access Account Details</legend>
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
					<div class="panel-heading">Address line 1</div>
					<div class="panel-body">${accessInfo.address1}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Address line 2</div>
					<div class="panel-body">${accessInfo.address2}</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">Phone Number</div>
					<div class="panel-body">${accessInfo.phoneNumber}</div>
				</div>

				<c:forEach var="accounts" items="${accessInfo.account}">
					<div class="panel panel-default">
						<div class="panel-heading">${accounts.accountType}Account</div>
						<div class="panel-body">${accounts.accountno}</div>
					</div>
				</c:forEach>
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