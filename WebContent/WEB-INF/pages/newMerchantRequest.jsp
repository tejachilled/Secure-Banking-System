<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<title>Insert title here</title>
<jsp:include page="headExt.jsp"></jsp:include>
</head>
<body>

	<form:form class="form-horizontal"	action="/RichirichBank/initiateMerchTrans" method="post"  name="f">

		<div class="body"></div>
		<div class="grad"></div>
		<div class="header"></div>
			
			<label>Account Number</label><input type="text" name="accountnum"><br> 
			<label>Amount</label><input type="text" name="amount"><br>
			<label>Remark</label><input type="text" name="remark"><br>
			<input type="radio" name="radios" value="radio1" CHECKED>
             Credit
            <br>
            <input type="radio" name="radios" value="radio2">
             Debit
            <br>
			<input type="submit" name="submit" value="submit" />
		
		</div>

		
		<script
			src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	</form:form>
</body>
</html>