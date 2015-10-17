<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="navbar navbar-inverse" style="margin:0px">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-inverse-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Welcome <sec:authentication property="name"/></a>
		</div>
		<div class="navbar-collapse collapse navbar-inverse-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/home">Home</a></li>
				<sec:authorize access="hasRole('ROLE_SA')"><li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Admin Specific Account Management<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<sec:authorize access="hasRole('ROLE_SA')"><li><a href="/register">Add a new account</a></li></sec:authorize>
						<sec:authorize access="hasRole('ROLE_SA')"><li><a href="/ViewEmpProfile">View Emp</a></li></sec:authorize>
                        <sec:authorize access="hasRole('ROLE_SA')"><li><a href="/EditEmpProfile">Edit Emp</a></li></sec:authorize>
						
					</ul></li></sec:authorize>
								<sec:authorize access="hasRole('ROLE_SA')"><li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Admin Specific Transaction Management<b class="caret"></b></a>
					<ul class="dropdown-menu">
						    <sec:authorize access="hasRole('ROLE_SA')"><li><a href="/ReviewPIIChange">PII Change Req</a></li></sec:authorize>
					</ul></li></sec:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="j_spring_security_logout">Logout</a></li>
			</ul>
		</div>
	</div>
</body>
</html>