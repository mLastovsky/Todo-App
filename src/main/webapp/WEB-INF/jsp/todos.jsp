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
          <form>
            <input
              id="input"
              type="text"
              placeholder="Task to be done"
              autocomplete="off"
            />
            <button id="add">ADD</button>
          </form>
          <ul id="todo-list">
            <c:forEach items="${todos}" var="todo">
              <li class="todo">
                <input type="checkbox" id="todo-${todo.id}" ${todo.completed ? 'checked' : ''}/>
                  <label class="custom-checkbox" for="todo-${todo.id}">
                  <svg
                    fill="transparent"
                    xmlns="http://www.w3.org/2000/svg"
                    height="24"
                    viewBox="0 -960 960 960"
                    width="24"
                  >
                    <path d="M382-240 154-468l57-57 171 171 367-367 57 57-424 424Z" />
                  </svg>
                  </label>
                  <label for="todo-${todo.id}" class="todo-text">
                    ${todo.task}
                  </label>
                  <button class="edit">
                    <svg
                      fill="var(--secondary-color)"
                      xmlns="http://www.w3.org/2000/svg"
                      height="24"
                      viewBox="0 -960 960 960"
                      width="24"
                    >
                      <path
                        d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"
                      />
                    </svg>
                  </button>
                  <button class="delete">
                    <svg
                      fill="var(--secondary-color)"
                      xmlns="http://www.w3.org/2000/svg"
                      height="24"
                      viewBox="0 -960 960 960"
                      width="24"
                    >
                      <path
                        d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z"
                      />
                    </svg>
                  </button>
                </li>
            </c:forEach>
          </ul>
        </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
  </body>
</html>
