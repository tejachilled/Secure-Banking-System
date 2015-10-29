<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>Home</title>
</head>
<body>

	<jsp:include page="headExt.jsp"></jsp:include>
	<form:form action="/RichirichBank/initiateCredit"
		class="form-horizontal" method="post" commandName="credit"
		name="credit" ModelAttribute="credit">

		<fieldset>
			<legend>${errorMessage}</legend>

			<div class="form-group">
				<label class="col-lg-2 control-label">Account Type:</label>
				<div class="col-lg-10">
					<form:select path="accType">
						<form:option value="Savings">${account_savings}</form:option>
						<form:option value="Checking">${account_checking}</form:option>
					</form:select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-lg-2 control-label">Amount to credit</label>
				<div class="col-lg-10">
					<input name="amount" id="amount" required/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">

					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</fieldset>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
	<c:if test="${not empty msg}">
		<c:out value="${msg}" />
	</c:if>
</body>
</html>

