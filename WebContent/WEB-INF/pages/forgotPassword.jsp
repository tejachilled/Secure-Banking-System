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


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
</head>
<body>
	<h1>Bank</h1>
	<div style="width: 60%;">
		<div class="panel panel-default">
			<div class="panel-body">

				<fieldset>
					<legend>Forgot Password</legend>

						<form:form action="forgotpassword" class="form-horizontal"
				method="post" commandName="forgotpassword" name="forgotpassword" ModelAttribute="forgotpassword">
				<fieldset>
					<legend>${errorMessage}</legend>

				<div id="enteredusername">
					<label for="enteredusername" class="col-lg-2 control-label">Enter Username:</label>
					<input type="text" name="enteredusername" id="enteredusername">
					</div>
					
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">

							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>
				</fieldset>
			</form:form>

				</fieldset>
			</div>
		</div>
	</div>
</body>
</html>


























