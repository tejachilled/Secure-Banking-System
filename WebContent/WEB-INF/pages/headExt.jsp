<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>


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
				<li class="active"><a href="/RichirichBank/extHome">Home</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Fund Management <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/RichirichBank/Debit">Debit</a></li>
						<li><a href="/RichirichBank/Credit">Credit</a></li>
						<li><a href="/RichirichBank/Transfer">Transfer</a></li>
						<sec:authorize access="hasRole('ROLE_U')">
							<li><a href="paymentsFromMerchant">Payments from
									Merchant</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_M')">
							<li><a href="submitPayment">Submit Payments</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_M')">
							<li><a href="approvedTransactions">Check Approved
									Transactions</a></li>
						</sec:authorize>
					</ul></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/RichirichBank/logout">Logout</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
