<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Todo App</title>
        <link rel="stylesheet"
        	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        	crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="common/header.jsp"></jsp:include>
        <div class="container">
        	<h2>Create account</h2>
        	<div class="col-md-6 col-md-offset-3">
        		<form action="<%=request.getContextPath()%>/registration" method="post">
        			<div class="form-group">
        				<input type="text" class="form-control" id="username" placeholder="username" name="username" required>
        			</div>

        			<div class="form-group">
        				<input type="email" class="form-control" id="email" placeholder="email" name="email" required>
        			</div>

        			<div class="form-group">
        				<input type="password" class="form-control" id="password" placeholder="password" name="password" required>
        			</div>

        			<button type="submit" class="btn btn-primary">Sign in</button>
        		</form>
        	</div>
        </div>
    </body>
</html>