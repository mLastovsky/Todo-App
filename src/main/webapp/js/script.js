const localhostAddress = "http://localhost:8080/todos";
const newTodoInput = document.querySelector("#new-todo input");
const submitButton = document.querySelector("#submit");
let isEditingTask = false;
let editButtonTodoID = "";
let isComplete = false;

document.addEventListener("DOMContentLoaded", () => {
  displayTodos();

  submitButton.addEventListener("click", () =>
    isEditingTask ? editTask() : addTask()
  );
});

async function getTodos() {
  try {
    const response = await fetch(localhostAddress);
    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error("Error:", error);
  }
}

async function createTodo(data) {
  try {
    const response = await fetch(localhostAddress, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    const result = await response.json();
    console.log("Success:", result.message);
  } catch (error) {
    console.error("Error:", error);
  }
}

async function deleteTodo(TodoID) {
  try {
    const response = await fetch(`${localhostAddress}/${TodoID}`, {
      method: "DELETE",
    });
    const result = await response.json();
    console.log("Success:", result.message);
  } catch (error) {
    console.error("Error:", error);
  }
}

async function updateTodo(id, data) {
  try {
    const response = await fetch(`${localhostAddress}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    const result = await response.json();
    console.log("Success:", result);
  } catch (error) {
    console.error("Error:", error);
  }
}

async function addTask() {
  const data = { task: newTodoInput.value };
  await createTodo(data);
  displayTodos();
  newTodoInput.value = "";
}

async function editTask() {
  const data = { task: newTodoInput.value, completed: isComplete };

  if (isEditingTask) await updateTodo(editButtonTodoID, data);
  displayTodos();

  newTodoInput.value = "";
  isEditingTask = false;
  submitButton.innerHTML = "ADD";
}

async function displayTodos() {
  const todoList = await getTodos();
  let todoListContainer = document.querySelector("#todo-list");
  todoListContainer.innerHTML = "";

  todoList.sort((a, b) => a.id - b.id);

  if (todoList.length != 0) {
    todoList.forEach((todo) => {
      todoListContainer.innerHTML += `
        <div class="todo">
          <input type="checkbox" id="todo-${todo.id}" ${todo.completed ? "checked" : ""}/>
          <label class="custom-checkbox" for="todo-${todo.id}">
            <svg fill="transparent" xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24"><path d="M382-240 154-468l57-57 171 171 367-367 57 57-424 424Z" /></svg>
          </label>
          <label for="todo-${todo.id}" class="todo-text" id="todoname" data-iscomplete="${todo.completed}">
            ${todo.task}
          </label>
          <button class="edit" data-id="${todo.id}">
            <svg fill="var(--secondary-color)" xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24"> <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z" /> </svg>
          </button>
          <button class="delete" data-id="${todo.id}">
            <svg fill="var(--secondary-color)" xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24" > <path d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z" /> </svg>
          </button>
        </div>
      `;
    });
  }

  deleteTaskButton();
  toggleTaskCompletion();
  editTaskTitleButton();
}


function deleteTaskButton() {
  const deleteTodoButtons = document.querySelectorAll(".delete");

  for (const deleteButton of deleteTodoButtons) {
    deleteButton.onclick = async function () {
      const todoID = deleteButton.getAttribute("data-id");
      await deleteTodo(todoID);
      displayTodos();
    };
  }
}

function editTaskTitleButton() {
  const editTodoTitleButtons = document.querySelectorAll(".edit");

  for (const editButton of editTodoTitleButtons) {
    editButton.onclick = async function () {
      const todoElement = editButton.closest(".todo");
      const todoName = todoElement.querySelector(".todo-text");
      newTodoInput.value = todoName.innerText;
      submitButton.innerHTML = "EDIT";
      isEditingTask = true;

      editButtonTodoID = editButton.getAttribute("data-id");
      isComplete = JSON.parse(todoName.getAttribute("data-iscomplete"));
    };
  }
}

function toggleTaskCompletion() {
  const checkboxes = document.querySelectorAll(".todo input[type='checkbox']");

  for (const checkbox of checkboxes) {
    checkbox.onchange = async function () {
      const todoID = checkbox.id.split("-")[1];
      const isTaskDone = checkbox.checked;

      const todoElement = checkbox.closest(".todo");
      const todoText = todoElement.querySelector(".todo-text").innerText;

      const data = { task: todoText, completed: isTaskDone };

      await updateTodo(todoID, data);

      displayTodos();
    };
  }
}
