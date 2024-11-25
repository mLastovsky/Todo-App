<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Todo App</title>
    </head>
    <body>
        <form action="/registration" method="post">
            <h2>Create account</h2>
            <input type="text" name="username" placeholder="username"><br>
            <input type="email" name="email" placeholder="email"><br>
            <input type="password" name="password" placeholder="password"><br>
            <button type="submit">Send</button>
        </form>
    </body>
</html>