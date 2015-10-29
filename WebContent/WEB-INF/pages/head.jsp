<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

	<div class="navbar navbar-inverse" style="margin: 0px">
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
				<sec:authorize access="hasAnyRole('ROLE_SM','ROLE_RE')">
					<li><a href="/RichirichBank/intHome">Home</a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_SA')">
					<li><a href="/RichirichBank/adminHome">Home</a></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_SM')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Account Management<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<sec:authorize access="hasRole('ROLE_SM')">
								<li><a href="/RichirichBank/register">Add a new account</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_SM')">
								<li><a href="/RichirichBank/ViewEmpProfile">View/Delete
										Customer</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_SM')">
								<li><a href="/RichirichBank/EditEmpProfile">Edit
										Customer</a></li>
							</sec:authorize>
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_SA')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Internal Employee Management<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<sec:authorize access="hasRole('ROLE_SA')">
								<li><a href="/RichirichBank/addInternalUser">Add a new
										internal employee</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_SA')">
								<li><a href="/RichirichBank/ViewInternalEmpProfile">View/Delete
										internal employee</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_SA')">
								<li><a href="/RichirichBank/EditInternalEmpProfile">Edit
										internal employee</a></li>
							</sec:authorize>
						</ul></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_SA')">
					<li><a href="/RichirichBank/viewSystemLogs">System Logs</a></li>
				</sec:authorize>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<li><a href="/RichirichBank/logout">Logout</a></li>
			</ul>
		</div>
	</div>
</body>
</html>