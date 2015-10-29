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

<title>Forgot Password</title>
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
	<div class="jumbotron">
		<h1>SunDevil Bank</h1>

	</div>
	<div style="width: 70%;">
		<div class="panel panel-default">
			<div class="panel-body">


				<form:form class="form-horizontal"
					action="/RichirichBank/forgotpassword" method="post"
					commandName="extUser" name="f">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<fieldset>
						<c:if test="${not empty error}">
							<div style="width: 40%;">
								<div class="alert alert-dismissable alert-danger">
									<strong> ${error}</strong>
								</div>
							</div>
						</c:if>
						<c:if test="${ empty username }">
							<div class="form-group">
								<label class="col-lg-2 control-label">Enter your
									UserName</label>
								<div class="col-lg-10">
									<form:input path="userName" cssClass="form-control" />
									<form:errors path="userName"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>
						</c:if>

						<c:if test="${not empty username }">
							<div class="form-group">
								<label class="col-lg-2 control-label"> UserName</label>
								<div class="col-lg-10">
									<input name="userName" class="form-control" id="userName"
										value="${username}" readonly />
									<form:errors path="userName"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label">Email </label>
								<div class="col-lg-10">
									<form:input path="emaiID" cssClass="form-control" />
									<form:errors path="emaiID"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label">Security Question
									1 : What is your first car name ?</label>
								<div class="col-lg-10">
									<form:input path="sq1" cssClass="form-control" />
									<form:errors path="sq1"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">Security Question
									2 : What is your pet name ?</label>
								<div class="col-lg-10">
									<form:input path="sq2" cssClass="form-control" />
									<form:errors path="sq2"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">Security Question
									3 : What is your mother's name ?</label>
								<div class="col-lg-10">
									<form:input path="sq3" cssClass="form-control" />
									<form:errors path="sq3"
										cssClass="alert alert-dismissable alert-danger"></form:errors>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</div>
					</fieldset>
				</form:form>



			</div>
		</div>
	</div>
</body>
</html>