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
	<jsp:include page="extHome.jsp"></jsp:include>
	<sec:authorize access="hasAnyRole('ROLE_M','ROLE_U')">
		<div style="width: 100%; text-align: center">
			<table>
				<form:form method="post"
					action="/RichirichBank/approveTransactionsMerchant"
					modelAttribute="transactionIdList">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<tr>
						<td><b>Customer Account ID&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Amount (in $)&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Transaction Type&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Date Initiated&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
					</tr>
					<c:forEach var="trListItem" items="${trList}">
						<tr>
							<td>${trListItem.accountId}</td>
							<td>${trListItem.amount}</td>
							<c:if test="${trListItem.isCritical eq 'C'}">
								<td>Credit</td>
							</c:if>
							<c:if test="${trListItem.isCritical eq 'M'}">
								<td>Debit</td>
							</c:if>
							<td>${trListItem.dateInitiated}</td>
							<c:if test="${trListItem.isCritical eq 'M'}">
								<td><form:checkbox path="TidList"
										value="${trListItem.transactionID}" /></td>
							</c:if>
						</tr>
					</c:forEach>
					<tr></tr>
					<tr></tr>
					<tr>
						<td bordercolor="white"></td>
						<td></td>
						<td bordercolor="white"><input type="submit" name="toDo"
							value="Approve" /></td>
						<td bordercolor="white"><input type="submit" name="toDo"
							value="Reject" /></td>
					</tr>
				</form:form>
			</table>
			<br /> <br />
			<c:if test="${not empty msg}">
				<c:out value="${msg}" />
			</c:if>
		</div>
	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_SM')">
		<div style="width: 100%; text-align: center">
			<table>


				<form:form method="post"
					action="/RichirichBank/approveTransactionsSM"
					modelAttribute="transactionIdList">
					<tr>
						<td><b>Customer Account ID&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Amount (in $)&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Transaction Type&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
						<td><b>Date Initiated&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
					</tr>
					<c:forEach var="trListItem" items="${trList}">
						<tr>
							<td>${trListItem.accountId}</td>
							<td>${trListItem.amount}</td>
							<c:if test="${trListItem.type eq 'C'}">
								<td>Credit</td>
							</c:if>
							<c:if test="${trListItem.type eq 'D'}">
								<td>Debit</td>
							</c:if>
							<td>${trListItem.dateInitiated}</td>
							<c:if test="${trListItem.isCritical eq 'H'}">
								<td>Critical</td>
							</c:if>
							<c:if test="${trListItem.isCritical eq 'L'}">
								<td>Non-Critical</td>
							</c:if>
							<td><form:checkbox path="TidList"
									value="${trListItem.transactionID}" /></td>
						</tr>
					</c:forEach>
					<tr></tr>
					<tr></tr>
					<tr>
						<td bordercolor="white"></td>
						<td></td>
						<td bordercolor="white"><input type="submit" name="toDo"
							value="Approve" /></td>
						<td bordercolor="white"><input type="submit" name="toDo"
							value="Reject" /></td>
					</tr>
				</form:form>
			</table>
			<br /> <br />
			<c:if test="${not empty msg}">
				<c:out value="${msg}" />
			</c:if>
		</div>
	</sec:authorize>
</body>
</html>
