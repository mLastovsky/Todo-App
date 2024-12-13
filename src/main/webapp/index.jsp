<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <h1>Todo App</h1>
        <div class="todo-app">
          <div id="new-todo">
            <input
              id="input"
              type="text"
              placeholder="Tasks to be done"
              autocomplete="off"
            />
            <button type="submit" id="submit">ADD</button>
          </div>
          <div id="todo-list">
          </div>
        </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
  </body>
</html>
