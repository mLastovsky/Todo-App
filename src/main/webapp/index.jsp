<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Todo App</title>
    <link rel="stylesheet" href="WEB-INF/jsp/css/style.css" />
  </head>
  <body>
    <div class="container">
      <div class="todo-app">
        <h2>To-Do List</h2>
        <div class="row">
          <input type="text" placeholder="Add your text" />
          <button type="button" id="submit">Add</button>
        </div>
        <ul id="list-container">
          <div class="todo"></div>
        </ul>
      </div>
    </div>
    <script src="WEB-INF/jsp/js/script.js"></script>
  </body>
</html>
