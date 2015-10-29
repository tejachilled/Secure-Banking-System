<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<jsp:include page="headExt.jsp"></jsp:include>
	<form:form action="/RichirichBank/initiateTransfer"
		class="form-horizontal" method="post" commandName="transferAmt"
		name="transferAmt" ModelAttribute="transferAmt">
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
				<label class="col-lg-2 control-label">To Account</label>
				<div class="col-lg-10">
					<input name="toAccountNo" id="toAccountNo" required/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label">Amount</label>
				<div class="col-lg-10">
					<input name="amountInvolved" id="amountInvolved" required/>
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

