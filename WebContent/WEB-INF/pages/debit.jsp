<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<link href="<c:url value="/resources/css/keyboard.css"/>"
	rel="stylesheet">

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.js"/>"></script>
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<title>Home</title>
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
</head>
<body>
	<jsp:include page="headExt.jsp"></jsp:include>
	<form:form action="/RichirichBank/initiateDebit"
		class="form-horizontal" method="post" commandName="debit" name="debit"
		ModelAttribute="debit">
		<fieldset>
			<legend>${errorMessage}</legend>

			<div class="form-group">
				<label class="col-lg-2 control-label">Account Type:</label>
				<div class="col-lg-10">
					<form:select path="accType">
						<c:if test="${not empty account_savings }">
							<form:option value="Savings">${account_savings}</form:option>
						</c:if>
						<c:if test="${not empty account_checking }">
							<form:option value="Checking">${account_checking}</form:option>
						</c:if>
					</form:select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-lg-2 control-label">Amount to Debit</label>
				<div class="col-lg-10">
					<input name="amount" id="amount" class="keyboardInput" required />
				</div>
			</div>
			<script type="text/javascript"
				src="<c:url value="/resources/js/keyboard.js"/>"></script>
			<script
				src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

			<!-- Bootstrap Core JavaScript -->
			<script type="text/javascript"
				src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

			<!-- Plugin JavaScript -->
			<script type="text/javascript"
				src="<c:url value="/resources/js/jquery.easing.min.js"/>"></script>


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

