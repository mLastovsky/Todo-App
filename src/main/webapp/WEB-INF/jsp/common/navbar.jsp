<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .navbar {
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: var(--primary-color);
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.5);
    z-index: 1000;
  }

  .navbar .logo {
    font-size: 1.5rem;
    font-weight: bold;
    color: var(--accent-color);
    text-decoration: none;
  }

  .navbar .nav-links {
    display: flex;
    gap: 15px;
  }

  .navbar .nav-links a {
    color: var(--text-color);
    text-decoration: none;
    font-size: 1rem;
    padding: 8px 15px;
    border-radius: 5px;
    transition: background-color 0.3s ease;
  }

  .navbar .nav-links a:hover {
    background-color: var(--secondary-color);
  }

  .btn-logout {
    background-color: var(--accent-color);
    color: var(--button-text);
    padding: 8px 15px;
    border: none;
    border-radius: 5px;
    font-size: 1rem;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s ease;
    text-transform: uppercase;
  }

  .btn-logout:hover {
    background-color: #00e6b0;
  }
</style>

<div class="navbar">
  <a href="<%=request.getContextPath()%>/" class="logo">Todo App</a>
  <div class="nav-links">
    <c:if test="${not empty user}">
      <form action="<%=request.getContextPath()%>/logout" method="post">
        <button type="submit" class="btn-logout">Logout</button>
      </form>
    </c:if>
    <c:if test="${empty user}">
      <a href="<%=request.getContextPath()%>/login">Login</a>
      <a href="<%=request.getContextPath()%>/registration">Sign Up</a>
    </c:if>
  </div>
</div>
