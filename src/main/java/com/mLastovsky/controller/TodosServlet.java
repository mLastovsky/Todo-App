package com.mLastovsky.controller;

import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.dto.UserDto;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.service.TodoService;
import com.mLastovsky.util.JsonUtils;
import com.mLastovsky.util.PathExtractor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.mLastovsky.util.UrlPath.TODOS;
import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet(urlPatterns = TODOS)
public class TodosServlet extends HttpServlet {

    private final TodoService todoService = TodoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = ((UserDto) req.getSession().getAttribute("user")).getId();
        var todos = todoService.getTodosByUserId(userId);

        try(var out = resp.getWriter()) {
            resp.setContentType("application/json");
            out.print(JsonUtils.toJson(todos));
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = ((UserDto) req.getSession().getAttribute("user")).getId();
        var createTodoDto = JsonUtils.parseJsonBody(req, CreateTodoDto.class);

        try {
            todoService.create(userId, createTodoDto);
            resp.setStatus(SC_CREATED);
        } catch (ValidationException e) {
            resp.setStatus(SC_FORBIDDEN);
            req.setAttribute("errors", e.getErrors());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = ((UserDto) req.getSession().getAttribute("user")).getId();
        var todoId = PathExtractor.getIdFromPath(req);
        if(todoService.delete(userId, todoId)){
            resp.setStatus(SC_NO_CONTENT);
        }else{
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = ((UserDto) req.getSession().getAttribute("user")).getId();
        var updatedTodoDto = JsonUtils.parseJsonBody(req, TodoDto.class);
        var todoId = PathExtractor.getIdFromPath(req);
        if(todoService.update(userId, todoId, updatedTodoDto)){
            resp.setStatus(SC_CREATED);
        } else {
            resp.setStatus(SC_FORBIDDEN);
        }
    }
}
