<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
</head>

<body>
	<div style="width: 100%; text-align: center">
		<h1>${bank_name}</h1>
		<h2>New PII Access Request</h2>
		<br> <br> <br>
		<c:if test="${error}">
			<p>Please try again!</p>
		</c:if>
		<form:form class="form-horizontal"
			action="/RichirichBank/sendPiiAccessRequest" method="post"
			commandName="piiRequestModel">
			Enter the Username of the External User whose PII you
				want to access:
			<br>
			<br>
			<form:input path="userNameExternal" />
			<br>
			<br>
			<input type="submit" name="sendRequest" value="Send Request" />
		</form:form>
	</div>
	</div>
</body>
</html>