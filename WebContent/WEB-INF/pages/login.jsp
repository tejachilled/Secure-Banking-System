<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	width: 200px;
	height: 15px;
	background-color: #fff;
	color: #ff0000;
	padding: 6px;
}

/*.msg {
	width: 200px;
	height: 15px;
	background: transparent;
	background-color: #b3b4a9;
	border: 2px solid rgba(255, 255, 255, 0.6);
	border-radius: 1px;
	color: #008000;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 200;
	padding: 6px;
}

@import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);

@import
	url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300)
	;

body {
	margin: 0;
	padding: 0;
	background: #fff;
	color: #fff;
	font-family: Arial;
	font-size: 12px;
}*/
.body {
	position: absolute;
	top: -20px;
	left: -20px;
	right: -40px;
	bottom: -40px;
	width: auto;
	height: auto;
	background-image:
		url(http://www.enchantedbrides.com/wp-content/uploads/2015/07/new-york-night-skyline-wallpaper-39006-in-city-telusers-new-york-skyline.jpg);
	background-size: cover;
	-webkit-filter: blur(10px);
	z-index: 0;
}

.grad {
	position: absolute;
	top: -20px;
	left: -20px;
	right: -40px;
	bottom: -40px;
	width: auto;
	height: auto;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(0, 0
		, 0, 0)), color-stop(100%, rgba(0, 0, 0, 0.65)));
	/* Chrome,Safari4+ */
	z-index: 1;
	opacity: 0.7;
}

.header {
	position: absolute;
	top: calc(50% - 35px);
	left: calc(50% - 255px);
	z-index: 2;
}

.header div {
	float: left;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 35px;
	font-weight: 200;
}

/*.header div span {
	color: #5379fa !important;
}*/

.login {
	position: absolute;
	top: calc(50% - 75px);
	left: calc(50% - 50px);
	height: 150px;
	width: 350px;
	padding: 30px;
	z-index: 2;
}

.login input[type=text] {
	width: 250px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255, 255, 255, 0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
}

.login input[type=password] {
	width: 250px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255, 255, 255, 0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
	margin-top: 10px;
}

.login input[type=submit] {
	width: 260px;
	height: 35px;
	background: #fff;
	border: 1px solid #fff;
	cursor: pointer;
	border-radius: 2px;
	color: #a18d6c;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 6px;
	margin-top: 10px;
}

.login input[type=submit]:hover {
	opacity: 0.8;
}

.login input[type=submit]:active {
	opacity: 0.6;
}

.login input[type=text]:focus {
	outline: none;
	border: 1px solid rgba(255, 255, 255, 0.9);
}

.login input[type=password]:focus {
	outline: none;
	border: 1px solid rgba(255, 255, 255, 0.9);
}

.login input[type=submit]:focus {
	outline: none;
}

::-webkit-input-placeholder {
	color: rgba(255, 255, 255, 0.6);
}

::-moz-input-placeholder {
	color: rgba(255, 255, 255, 0.6);
}
}
</style>
<!-- Bootstrap Core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/keyboard.css"/>"
	rel="stylesheet">

</head>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/keyboard.js"/>"></script>
<script
	src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<!-- Plugin JavaScript -->
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.easing.min.js"/>"></script>


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

	<form name='loginForm' action="<c:url value='/login' />" method='POST'
		autocomplete="off">

		<div class="body"></div>
		<div class="grad"></div>
		<div class="header">
			<div>
				SunDevilBank 
			</div>
		</div>

		<div class="login">

			<c:if test="${not empty error}">
				<input type="search" name="error" class="error" value="${error }" readonly>
				<br>
			</c:if>
			<c:if test="${not empty success}">
				<input type="search" name="error" class="msg" value="${success }" readonly>
				<br>
			</c:if>
			<input type="text" name="username" placeholder="Username" required
				autofocus /><br> <input type="password" name="password"
				id="password" class="form-control keyboardInput"
				placeholder="Password" required /><br>
			<div class="g-recaptcha"
				data-sitekey="6Lev8g8TAAAAAJ3GeikaTHN4o59Ij2rANfyBPA2U"></div>
			<br> <input name="submit" type="submit" value="Login" /> <br>
			<br> <a style="color: white"
				href="/RichirichBank/forgotpassword">Forgot Password? </a>
		</div>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

	</form>


</body>
</html>