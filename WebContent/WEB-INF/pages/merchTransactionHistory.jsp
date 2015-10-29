<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<html>

<head>
<link href="<c:url value="/resources/css/theme.css"/>" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<title>Transaction History</title>
<jsp:include page="extHome.jsp"></jsp:include>
<style>
table {
	width: 100%;
}

td {
	border: 1px black solid;
	padding: 10px;
}
</style>
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
	<sec:authorize access="hasRole('ROLE_M')">
		<table>
			<tr>
				<td><b>CUSTOMER ACCOUNT NUMBER</b></td>
				<td><b>AMOUNT</b></td>
				<td><b>CREDIT/DEBIT</b></td>
				<td><b>REMARK</b></td>
			</tr>



			<c:forEach var="merchTransactionHistory" items="${TransactionList}">
				<tr>
					<td>${merchTransactionHistory.accountId}</td>
					<td>${merchTransactionHistory.amount}</td>

					<c:if test="${merchTransactionHistory.type eq 'C'}">
						<td>Credit</td>
					</c:if>
					<c:if test="${merchTransactionHistory.type eq 'D'}">
						<td>Debit</td>
					</c:if>
					<td>${merchTransactionHistory.remark}</td>
				</tr>
			</c:forEach>


		</table>
	</sec:authorize>
</body>
</html>