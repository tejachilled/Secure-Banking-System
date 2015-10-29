<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>
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
<title>Home</title>
</head>
<body>

	<div class="navbar navbar-inverse">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-inverse-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Welcome</a>
		</div>
		<div class="navbar-collapse collapse navbar-inverse-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/RichirichBank/logout">Logout</a></li>
			</ul>
		</div>
	</div>
	<h5>*Password must be 4-10 characters, should contain one capital
		letter and a number*</h5>
	<form:form action="/RichirichBank/changePassword"
		class="form-horizontal" method="post">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		<fieldset>
			<legend>${errorMessage}</legend>
			<div id="newPassword">
				<label for="newPassword" class="col-lg-2 control-label">New
					Password:</label> <input type="password" name="newPassword"
					id="newPassword"><br> <label for="confirmPassword"
					class="col-lg-2 control-label">Confirm Password:</label> <input
					type="password" name="confirmPassword" id="confirmPassword">
			</div>

			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">

					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</fieldset>
	</form:form>

</body>
</html>
