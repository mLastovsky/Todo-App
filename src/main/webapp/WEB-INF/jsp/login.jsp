<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
    <style>
      :root {
        --background: #101114;
        --primary-color: #1c1d20;
        --secondary-color: #4a4d57;
        --accent-color: #00ffc4;
        --text-color: #f9f9f9;
        --input-border: #4a4d57;
        --button-text: #101114;
      }

      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        font-size: 16px;
        color: var(--text-color);
        background-color: var(--background);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        padding: 10px;
      }

      .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        max-width: 400px;
        padding: 15px;
      }

      .auth-box {
        background-color: var(--primary-color);
        border-radius: 15px;
        padding: 20px 30px;
        width: 100%;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
      }

      h2 {
        color: var(--accent-color);
        text-align: center;
        margin-bottom: 20px;
        font-size: 1.8rem;
        font-weight: 700;
      }

      .input-group {
        margin-bottom: 20px;
      }

      input[type="text"],
      input[type="password"] {
        width: 100%;
        padding: 12px 15px;
        font-size: 1rem;
        border: 2px solid var(--input-border);
        border-radius: 10px;
        background-color: var(--primary-color);
        color: var(--text-color);
      }

      input[type="text"]:focus,
      input[type="password"]:focus {
        outline: none;
        border-color: var(--accent-color);
      }

      .btn-primary {
        width: 100%;
        padding: 12px 15px;
        font-size: 1rem;
        font-weight: bold;
        text-transform: uppercase;
        background-color: var(--accent-color);
        border: none;
        border-radius: 10px;
        color: var(--button-text);
        cursor: pointer;
      }

      .btn-primary:hover {
        background-color: #00e6b0;
      }

      .signup-link {
        margin-top: 15px;
        text-align: center;
        display: block;
        color: var(--accent-color);
        text-decoration: none;
        font-size: 0.9rem;
      }

      .signup-link:hover {
        text-decoration: underline;
      }

      /* Responsive Design */
      @media (max-width: 768px) {
        h2 {
          font-size: 1.5rem;
        }

        input[type="text"],
        input[type="password"] {
          font-size: 0.9rem;
          padding: 10px 12px;
        }

        .btn-primary {
          font-size: 0.9rem;
        }

        .auth-box {
          padding: 15px 20px;
        }
      }

      @media (max-width: 480px) {
        h2 {
          font-size: 1.2rem;
        }

        input[type="text"],
        input[type="password"] {
          font-size: 0.8rem;
          padding: 8px 10px;
        }

        .btn-primary {
          font-size: 0.8rem;
        }

        .auth-box {
          padding: 10px 15px;
        }
      }
    </style>
  </head>
  <body>
    <jsp:include page="common/navbar.jsp" />
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
        <a href="<%=request.getContextPath()%>/registration" class="signup-link">
          Don't have an account? Sign up here
        </a>
      </div>
    </div>
  </body>
</html>
