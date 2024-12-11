<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body>
    <div class="container">
      <div class="todo-app">
        <h2>To-Do List</h2>
        <div class="row">
          <input type="text" placeholder="Add your text" id="input"/>
          <button type="button" id="submit">Add</button>
        </div>
        <ul id="list-container">
          <c:forEach var="todo" items="${todos}">
            <li>${todo.task}</li>
          </c:forEach>
        </ul>
      </div>
    </div>
    <script src="js/script.js"></script>
  </body>
</html>
