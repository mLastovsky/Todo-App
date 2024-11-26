<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Todo App</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h2>Sign in</h2>
            <input type="text" name="username" placeholder="username" required><br>
            <input type="password" name="password" placeholder="password" required><br>
            <button type="submit">Login</button>
            <a href="${pageContext.request.contextPath}/registration">
                <button type="button">Register</button>
            </a>
        </form>
    </body>
</html>