<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/error/css/error.css" />
  </head>
  <body>
    <jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
    <div class="error-container">
      <h1>400 - Bad Request</h1>
      <p>Oops! The request was invalid. Please check the URL and try again.</p>
      <a href="<%=request.getContextPath()%>/" class="error-btn">Go to Home</a>
    </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
  </body>
</html>