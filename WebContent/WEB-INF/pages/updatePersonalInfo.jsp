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

<title>User Home</title>
</head>
<body>
	<jsp:include page="headExt.jsp"></jsp:include>
	<c:if test="${piiExists  eq 'n'}">
		<div style="width: 100%;">
			<form:form class="form-horizontal" commandName="personalInfo"
				method="post" action="/RichirichBank/confirmUpdate">

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				<fieldset>
					<legend>Enter Your SSN: </legend>
					<c:if test="${not empty success }">
						<div style="width: 40%;">
							<div class="alert alert-dismissable alert-success">
								<strong>${success}</strong>
							</div>

						</div>
					</c:if>
					<div>
						<div class="col-lg-10">
							<form:input path="pii" class="form-control" id="piiId"
								placeholder="Format: 'XXXXXXXXX'" />
							<label style="color: red">${piierror}</label>
						</div>

					</div>
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
	</c:if>
	<c:if test="${piiExists  eq 'y'}">
	    You have already updated your Personal Info!!! Please contact the bank for modifying it!!!
	 </c:if>
	<c:if test="${not empty error}">
		<c:out value="${error}" />
	</c:if>
</body>
</html>
