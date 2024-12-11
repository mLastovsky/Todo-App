const localhostAddress = "http://localhost:8080/todos";
const newTodoInput = document.querySelector("#todo-app input");
const submitButton = document.querySelector("#submit");
let isEditingTask = false;
let editButtonTodoID = "";
let isComplete = false;

//async function getTodos() {
//  try {
//    const response = await fetch(localhostAddress);
//    const responseData = await response.json();
//    return responseData.data;
//  } catch (error) {
//    console.error("Error:", error);
//  }
//}