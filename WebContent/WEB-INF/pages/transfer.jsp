<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/resources/css/theme.css"/>"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>	
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<title>Home</title>
</head>
<body>

	<jsp:include page="headExt.jsp"></jsp:include>
	<form:form action="/RichirichBank/initiateTransfer" class="form-horizontal"	method="post" commandName="transferAmt" name="transferAmt" ModelAttribute="transferAmt">
				<fieldset>
					<legend>${errorMessage}</legend>
					<div class="form-group">
						<form:select path="type"> 
						<form:option value="Savings">${account_savings}</form:option>
						<form:option value="Checkings">${account_checking}</form:option>
						</form:select>
					</div>

					<div class="form-group">
						<label for="toAccountno" class="col-lg-2 control-label">To Account</label>
						<div class="col-lg-10">
						<form:input path="toAccountNo" />	
						</div>
					</div>
					<div class="form-group">
						<label for="amtInvolved" class="col-lg-2 control-label">Amount</label>
						<div class="col-lg-10">
							<form:input path="amountInvolved" />	
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
</body>
</html>
