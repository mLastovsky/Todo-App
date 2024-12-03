package com.mLastovsky.service;

import com.mLastovsky.dao.TodoDao;
import com.mLastovsky.dao.UserDao;
import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.mapper.CreateTodoMapper;
import com.mLastovsky.mapper.TodoMapper;
import com.mLastovsky.validator.TodoValidator;

import java.util.List;

public class TodoService {

    private static final TodoService INSTANCE = new TodoService();

    private final UserDao userDao = UserDao.getInstance();
    private final TodoDao todoDao = TodoDao.getInstance();
    private final TodoValidator todoValidator = TodoValidator.getInstance();
    private final TodoMapper todoMapper = TodoMapper.getInstance();
    private final CreateTodoMapper createTodoMapper = CreateTodoMapper.getInstance();

    public void create(Long userId, CreateTodoDto createTodoDto) {
        var updateCreateTodoDto = CreateTodoDto.builder()
                .userId(userId)
                .task(createTodoDto.getTask())
                .completed(createTodoDto.getCompleted())
                .build();

        var validationResult = todoValidator.validate(updateCreateTodoDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var todoEntity = createTodoMapper.mapFrom(updateCreateTodoDto);
        todoDao.save(todoEntity);
    }

    public boolean delete(Long userId, Long todoId) {
        var todoEntityOptional = todoDao.findById(todoId);
        if(todoEntityOptional.isPresent()){
            var todoEntity = todoEntityOptional.get();
            if(todoEntity.getUserId().equals(userId)){
                return todoDao.delete(todoId);
            }
        }

        return false;
    }

    public boolean update(Long userId, Long todoId, TodoDto todoDto) {
        var todoEntityOptional = todoDao.findById(todoId);
        if (todoEntityOptional.isPresent()) {
            var todo = todoEntityOptional.get();
            if(todo.getUserId().equals(userId)) {
                todo.setId(todoId);
                todo.setUserId(todoDto.getUserId() == null ? todo.getUserId() : todoDto.getUserId());
                todo.setTask(todoDto.getTask() == null ? todo.getTask() : todoDto.getTask());
                todo.setCompleted(todoDto.getCompleted() == null ? todo.getCompleted() : todoDto.getCompleted());
                todoDao.update(todo);

                return true;
            }
        }

        return false;
    }

    public List<TodoDto> getTodosByUserId(Long userId) {
        var user = userDao.findById(userId);
        if (user.isEmpty()) {
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
