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
<title>Register</title>
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
	<div style="width: 70%;">
		<div class="panel panel-default">
			<div class="panel-body">


				<form:form class="form-horizontal"
					action="/RichirichBank/addInternalUser" method="post"
					commandName="intUser" name="f">
					<fieldset>
						<c:if test="${not empty exception}">
							<div style="width: 40%;">
								<div class="alert alert-dismissable alert-danger">
									<strong> ${exception}</strong>
								</div>
							</div>
						</c:if>
					
						<legend>New Employee</legend>
						<div class="form-group">
							<label class="col-lg-2 control-label">Firstname</label>
							<div class="col-lg-10">
								<form:input path="firstName" cssClass="form-control" />
								<form:errors path="firstName"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Lastname</label>
							<div class="col-lg-10">
								<form:input path="lastName" cssClass="form-control" />
								<form:errors path="lastName"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Username</label>
							<div class="col-lg-10">
								<form:input path="userName" cssClass="form-control" />
								<form:errors path="userName"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Email (Please give correct email id, temporary password will be sent)</label>
							<div class="col-lg-10">
								<form:input path="emaiID" cssClass="form-control" />
								<form:errors path="emaiID"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Address Line 1:</label>
							<div class="col-lg-10">
								<form:textarea path="address1" cssClass="form-control" />
								<form:errors path="address1"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Address Line 2:</label>
							<div class="col-lg-10">
								<form:textarea path="address2" cssClass="form-control" />
								<form:errors path="address2"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Phone Number</label>
							<div class="col-lg-10">
								<form:input path="phoneNumber" cssClass="form-control" />
								<form:errors path="phoneNumber"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>


						<div class="form-group">
							<label for="select" class="col-lg-2 control-label">Role</label>
							<div class="col-lg-10">
								<select name="role" class="form-control" id="select">
									<option value="ROLE_RE">Regular Employee</option>
									<option value="ROLE_SM">System Manager</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Security Question 1 : What is your first car name ?</label>
							<div class="col-lg-10">
								<form:input path="sq1" cssClass="form-control" />
								<form:errors path="sq1"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Security Question 2 : What is your pet name ?</label>
							<div class="col-lg-10">
								<form:input path="sq2" cssClass="form-control" />
								<form:errors path="sq2"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Security Question 3 : What is your mother's name ?</label>
							<div class="col-lg-10">
								<form:input path="sq3" cssClass="form-control" />
								<form:errors path="sq3"
									cssClass="alert alert-dismissable alert-danger"></form:errors>
							</div>
						</div>
						

						

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="submit" class="btn btn-primary">Submit</button>
								<button type="reset" class="btn btn-primary">Reset</button>
							</div>
						</div>
					</fieldset>
				</form:form>



			</div>
		</div>
	</div>

</body>
</html>