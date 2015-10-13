
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>Registration</title>
</head>
<body>
	<div style="width: 70%;">
		<div class="panel panel-default">
			<div class="panel-body">
				<form:form class="form-horizontal" action="/Bank/addExtUser"
					method="post" commandName="extUser" name="f">
					<fieldset>
						<legend>Add a new User</legend>
						<div class="form-group">
							<label class="col-lg-2 control-label">Firstname</label>
							<div class="col-lg-10">
								<form:input path="firstname" cssClass="form-control" />
								<form:errors path="firstname"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Middlename</label>
							<div class="col-lg-10">
								<form:input path="middlename" cssClass="form-control" />
								<form:errors path="middlename"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Lastname</label>
							<div class="col-lg-10">
								<form:input path="lastname" cssClass="form-control" />
								<form:errors path="lastname"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Username</label>
							<div class="col-lg-10">
								<form:input path="username" cssClass="form-control" />
								<form:errors path="username"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Password</label>
							<div class="col-lg-10">
								<form:password path="password" cssClass="form-control" />
								<form:errors path="password"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Email</label>
							<div class="col-lg-10">
								<form:input path="email" cssClass="form-control" />
								<form:errors path="email"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Address</label>
							<div class="col-lg-10">
								<form:textarea path="address" cssClass="form-control" />
								<form:errors path="address"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label for="select" class="col-lg-2 control-label">Role/Type</label>
							<div class="col-lg-10">
								<select name="role" class="form-control" id="select">
									<option value="ROLE_CUSTOMER">Customer</option>
									<option value="ROLE_MERCHANT">Merchant</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="submit" class="btn btn-primary">Submit</button>
								<button type="reset" class="btn btn-primary">Clear</button>
							</div>
						</div>
					</fieldset>
				</form:form>

				<c:if test="${not empty accountnum }">
					<div style="width: 30%;">
						<div class="alert alert-dismissable alert-success">
							<bold> Your new account number is -  ${accountnum} </bold> </br>
						</div>
					</div>
				</c:if>

			</div>
		</div>
	</div>
</body>
</html>