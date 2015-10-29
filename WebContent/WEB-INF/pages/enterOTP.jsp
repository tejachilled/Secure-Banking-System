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
			<a class="navbar-brand" href="#">Welcome <sec:authentication
					property="name" /></a>
		</div>
		<div class="navbar-collapse collapse navbar-inverse-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="extHome">Home</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Fund Management <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/RichirichBank/Debit">Debit</a></li>
						<li><a href="/RichirichBank/Credit">Credit</a></li>
						<li><a href="/RichirichBank/Transfer">Transfer</a></li>
					</ul></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/RichirichBank/logout">Logout</a></li>
			</ul>
		</div>
	</div>
	<form:form action="/RichirichBank/validateOTP"
		class="form-horizontal" method="post" commandName="otp"	name="otp" ModelAttribute="otp">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		<fieldset>
		 <div class="form-group">
				<label for="amtInvolved" class="col-lg-2 control-label">Amount
					to Credit</label>
				<div class="col-lg-10">
					<form:input path="otpValue" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">

					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>		</fieldset>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
	<c:if test="${not empty msg}">
		<c:out value="${msg}" />
	</c:if>
</body>
</html>

