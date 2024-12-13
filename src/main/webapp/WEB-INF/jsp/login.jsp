<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
  </head>
  <body>
    <div class="login-container">
      <div class="auth-box">
        <h2>Log In</h2>
        <form action="<%=request.getContextPath()%>/login" method="post">
          <div class="input-group">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Username"
              required
            />
          </div>
          <div class="input-group">
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Password"
              required
            />
          </div>
          <button type="submit" class="btn-primary">Log In</button>
        </form>
      </div>
    </div>
  </body>
</html>
