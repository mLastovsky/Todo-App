package com.mLastovsky.service;

import com.mLastovsky.dao.TodoDao;
import com.mLastovsky.dao.UserDao;
import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.entity.TodoEntity;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.mapper.CreateTodoMapper;
import com.mLastovsky.mapper.TodoMapper;
import com.mLastovsky.validator.TodoValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TodoService {

    private static final TodoService INSTANCE = new TodoService();

    private final UserDao userDao = UserDao.getInstance();
    private final TodoDao todoDao = TodoDao.getInstance();
    private final TodoValidator todoValidator = TodoValidator.getInstance();
    private final TodoMapper todoMapper = TodoMapper.getInstance();
    private final CreateTodoMapper createTodoMapper = CreateTodoMapper.getInstance();

    public void create(Long userId, CreateTodoDto createTodoDto) {
        log.debug("Creating todo for user with ID: {}", userId);
        var updateCreateTodoDto = CreateTodoDto.builder()
                .userId(userId)
                .task(createTodoDto.getTask())
                .build();

        var validationResult = todoValidator.validate(updateCreateTodoDto);
        if (!validationResult.isValid()) {
            log.error("Validation failed for todo: {}", validationResult.getErrors());
            throw new ValidationException(validationResult.getErrors());
        }
        var todoEntity = createTodoMapper.mapFrom(updateCreateTodoDto);
        todoDao.save(todoEntity);
        log.info("Todo created successfully for user with ID: {}", userId);
    }

    public boolean delete(Long userId, Long todoId) {
        log.debug("Attempting to delete todo with ID: {} for user with ID: {}", todoId, userId);

        var todoEntityOptional = todoDao.findById(todoId);
        if (todoEntityOptional.isPresent()) {
            var todoEntity = todoEntityOptional.get();
            if (todoEntity.getUserId().equals(userId)) {
                todoDao.delete(todoId);
                log.info("Todo with ID: {} deleted successfully", todoId);
                return true;
            } else {
                log.warn("User ID: {} is not authorized to delete this todo", userId);
            }
        } else {
            log.warn("Todo with ID: {} not found", todoId);
        }

        return false;
    }

    public boolean update(Long userId, Long todoId, TodoDto todoDto) {
        log.debug("Attempting to update todo with ID: {} for user with ID: {}", todoId, userId);

        var todoEntityOptional = todoDao.findById(todoId);
        if (todoEntityOptional.isPresent()) {
            var todo = todoEntityOptional.get();
            if (todo.getUserId().equals(userId)) {
                updateTodoFields(todo, todoDto);
                todoDao.update(todo);
                log.info("Todo with ID: {} updated successfully", todoId);
                return true;
            } else {
                log.warn("User ID: {} is not authorized to update this todo", userId);
            }
        } else {
            log.warn("Todo with ID: {} not found", todoId);
        }

        return false;
    }

    private void updateTodoFields(TodoEntity todo, TodoDto todoDto) {
        todo.setTask(todoDto.getTask() != null ? todoDto.getTask() : todo.getTask());
        todo.setCompleted(todoDto.getCompleted() != null ? todoDto.getCompleted() : todo.getCompleted());
    }

    public List<TodoDto> getTodosByUserId(Long userId) {
        log.debug("Fetching todos for user with ID: {}", userId);

        var user = userDao.findById(userId);
        if (user.isEmpty()) {
            log.warn("User with ID: {} not found", userId);
            return List.of();
        }

        var todos = todoDao.findAllUserTodos(userId);

        return todos.stream()
                .map(todoMapper::mapFrom)
                .toList();
    }

    public static TodoService getInstance() {
        return INSTANCE;
    }
}
