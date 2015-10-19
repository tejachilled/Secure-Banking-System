<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction History</title>
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
<body>
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
						
						<c:if test="${merchTransactionHistory.type eq 'c'}">
							<td>Credit</td>
						</c:if>
						<c:if test="${merchTransactionHistory.type eq 'd'}">
							<td>Debit</td>
						</c:if>
						<td>${merchTransactionHistory.remark}</td>
					</tr>
				</c:forEach>
				
			
		</table>
</body>
</html>