package com.mLastovsky.dao;

import com.mLastovsky.entity.TodoEntity;
import com.mLastovsky.exception.DaoException;
import com.mLastovsky.util.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TodoDao implements Dao<Long, TodoEntity> {

    private static final TodoDao INSTANCE = new TodoDao();

    private static final String SQL_DELETE = """
            DELETE FROM todos
            WHERE id = ?
            """;

    private static final String SQL_SAVE = """
            INSERT INTO todos (user_id, task)
            VALUES (?,?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE todos
            SET user_id = ?,
                task = ?,
                completed = ?
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id,
                user_id,
                task,
                completed
            FROM todos
            """;

    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + """
            WHERE id = ?""";

    private static final String SQL_FIND_ALL_USER_TODOS = SQL_FIND_ALL + """
            WHERE user_id = ?
            """;

    public static TodoDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Attempting to delete todo with id: {}", id);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);

            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted) {
                log.info("Todo with id: {} successfully deleted", id);
            } else {
                log.warn("Todo with id: {} not found for deletion", id);
            }

            return isDeleted;
        } catch (SQLException e) {
            log.error("Error while deleting todo with id: {}", id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public TodoEntity save(TodoEntity todo) {
        log.debug("Attempting to save new todo for userId: {}", todo.getUserId());
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, todo.getUserId());
            preparedStatement.setString(2, todo.getTask());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                todo.setId(generatedKeys.getLong("id"));
                log.info("Todo saved successfully with generated id: {}", todo.getId());
            } else {
                log.warn("Failed to retrieve generated id for todo");
            }

            return todo;
        } catch (SQLException e) {
            log.error("Error while saving todo for userId: {}", todo.getUserId(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(TodoEntity todo) {
        log.debug("Attempting to update todo with id: {}", todo.getId());
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setLong(1, todo.getUserId());
            preparedStatement.setString(2, todo.getTask());
            preparedStatement.setBoolean(3, todo.getCompleted());
            preparedStatement.setLong(4, todo.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("Todo with id: {} successfully updated", todo.getId());
            } else {
                log.warn("Todo with id: {} not found for update", todo.getId());
            }
        } catch (SQLException e) {
            log.error("Error while updating todo with id: {}", todo.getId(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<TodoEntity> findById(Long id) {
        log.debug("Attempting to find todo by id: {}", id);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            TodoEntity todo = null;
            if (resultSet.next()) {
                todo = buildTodo(resultSet);
                log.info("Todo with id: {} found", id);
            } else {
                log.warn("Todo with id: {} not found", id);
            }

            return Optional.ofNullable(todo);
        } catch (SQLException e) {
            log.error("Error while finding todo by id: {}", id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<TodoEntity> findAll() {
        log.debug("Attempting to find all todos");
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL)) {

            var resultSet = preparedStatement.executeQuery();
            List<TodoEntity> todos = new ArrayList<>();
            while (resultSet.next()) {
                todos.add(buildTodo(resultSet));
            }

            log.info("Total todos found: {}", todos.size());
            return todos;
        } catch (SQLException e) {
            log.error("Error while finding all todos", e);
            throw new DaoException(e);
        }
    }

    public List<TodoEntity> findAllUserTodos(Long userId) {
        log.debug("Attempting to find all todos for userId: {}", userId);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USER_TODOS)) {
            preparedStatement.setLong(1, userId);

            var resultSet = preparedStatement.executeQuery();
            List<TodoEntity> todos = new ArrayList<>();
            while (resultSet.next()) {
                todos.add(buildTodo(resultSet));
            }

            log.info("Total todos found for userId {}: {}", userId, todos.size());
            return todos;
        } catch (SQLException e) {
            log.error("Error while finding todos for userId: {}", userId, e);
            throw new DaoException(e);
        }
    }

    private static TodoEntity buildTodo(ResultSet resultSet) throws SQLException {
        log.trace("Building TodoEntity from ResultSet");
        return TodoEntity.builder()
                .id(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .task(resultSet.getString("task"))
                .completed(resultSet.getBoolean("completed"))
                .build();
    }
}
