package com.mLastovsky.servlet;

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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.mLastovsky.util.UrlPath.TODOS;
import static jakarta.servlet.http.HttpServletResponse.*;

@Slf4j
@WebServlet(TODOS)
public class TodosServlet extends HttpServlet {

    private final TodoService todoService = TodoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = getUserIdFromSession(req);
        log.debug("Fetching todos for userId: {}", userId);

        try (var out = resp.getWriter()) {
            var todos = todoService.getTodosByUserId(userId);
            log.info("Successfully fetched todos for userId: {}", userId);
            resp.setContentType("application/json");
            out.write(JsonUtils.toJson(todos));
        } catch (IOException e) {
            log.error("Error occurred while fetching todos for userId: {}", userId, e);
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = getUserIdFromSession(req);
        log.debug("Processing todo creation for userId: {}", userId);

        CreateTodoDto createTodoDto = JsonUtils.parseJsonBody(req, CreateTodoDto.class);

        try {
            todoService.create(userId, createTodoDto);
            resp.setStatus(SC_CREATED);
            log.info("Successfully created todo for userId: {}", userId);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            log.warn("Validation failed while creating todo for userId: {}", userId, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = getUserIdFromSession(req);
        Long todoId = PathExtractor.getIdFromPath(req);
        log.debug("Attempting to delete todo with ID: {} for userId: {}", todoId, userId);

        if (todoService.delete(userId, todoId)) {
            resp.setStatus(SC_NO_CONTENT);
            log.info("Successfully deleted todo with ID: {} for userId: {}", todoId, userId);
        } else {
            resp.setStatus(SC_NOT_FOUND);
            log.warn("Todo with ID: {} not found for userId: {}", todoId, userId);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = getUserIdFromSession(req);
        Long todoId = PathExtractor.getIdFromPath(req);
        log.debug("Attempting to update todo with ID: {} for userId: {}", todoId, userId);

        TodoDto updatedTodoDto = JsonUtils.parseJsonBody(req, TodoDto.class);

        if (todoService.update(userId, todoId, updatedTodoDto)) {
            resp.setStatus(SC_OK);
            log.info("Successfully updated todo with ID: {} for userId: {}", todoId, userId);
        } else {
            resp.setStatus(SC_FORBIDDEN);
            log.warn("Failed to update todo with ID: {} for userId: {}", todoId, userId);
        }
    }

    private Long getUserIdFromSession(HttpServletRequest req) {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        return user.getId();
    }
}
