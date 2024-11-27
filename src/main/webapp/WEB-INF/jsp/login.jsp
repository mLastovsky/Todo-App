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
        <form action="/login" method="post">

            <h2>Sign in</h2>
            <input type="text" name="username" placeholder="username" required><br>
            <input type="password" name="password" placeholder="password" required><br>
            <button type="submit">Login</button>
            <a href="/registration">
                <button type="button">Register</button>
            </a>
        </form>
    </body>
</html>