<<<<<<< HEAD
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Employee Profile</title>
</head>
<body>
   <jsp:include page="internalHome.jsp"></jsp:include>
    <form:form class="form-horizontal" commandName="accessInfo" method="post" action="/RichirichBank/DeleteEmpProfile">
    <fieldset>
    <legend>Enter User Credentials to Delete Account</legend>
    <div>
      <div class="col-lg-10">
           <form:input path="username" class="form-control" id="usernameid" placeholder="User Name" />
           <label style="color:red">${usernameerror}</label>
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
    </div>
  </fieldset>
   </form:form>
   <div>
   <label style="color:red">${deleteMessage}</label>
   <h4>Are you sure you want to delete this employee record?</h4>
	<form:form commandName="accessInfo" action="/RichirichBank/DeleteEmpProfile" method="post">
		<div class="panel panel-default">
			<div class="panel-heading">First Name</div>
			<form:input path="firstname" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Middle Name</div>
            <form:input path="middlename" readonly="true"/>
   		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Last Name</div>
			<form:input path="lastname" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">User Name</div>
			<form:input path="username" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Identification ID</div>
			<form:input path="identificationid" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Email ID</div>
			<form:input path="email" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Address</div>
			<form:input path="address" readonly="true"/>
	   </div>
	   <input type="submit" value="Delete Employee" />
	  </form:form>
   </div>
   
</body>
=======
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Employee Profile</title>
</head>
<body>
   <jsp:include page="internalHome.jsp"></jsp:include>
    <form:form class="form-horizontal" commandName="accessInfo" method="post" action="/RichirichBank/DeleteEmpProfile">
    <fieldset>
    <legend>Enter User Credentials to Delete Account</legend>
    <div>
      <div class="col-lg-10">
           <form:input path="username" class="form-control" id="usernameid" placeholder="User Name" />
           <label style="color:red">${usernameerror}</label>
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
    </div>
  </fieldset>
   </form:form>
   <div>
   <label style="color:red">${deleteMessage}</label>
   <h4>Are you sure you want to delete this employee record?</h4>
	<form:form commandName="accessInfo" action="/RichirichBank/DeleteEmpProfile" method="post">
		<div class="panel panel-default">
			<div class="panel-heading">First Name</div>
			<form:input path="firstname" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Middle Name</div>
            <form:input path="middlename" readonly="true"/>
   		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Last Name</div>
			<form:input path="lastname" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">User Name</div>
			<form:input path="username" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Identification ID</div>
			<form:input path="identificationid" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Email ID</div>
			<form:input path="email" readonly="true"/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Address</div>
			<form:input path="address" readonly="true"/>
	   </div>
	   <input type="submit" value="Update Details" />
	  </form:form>
   </div>
   
</body>
>>>>>>> branch 'master' of https://github.com/mounikavm/SSProject_SparkyBankingSystem.git
</html>
