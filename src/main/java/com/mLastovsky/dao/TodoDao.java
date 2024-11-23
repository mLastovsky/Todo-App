package com.mLastovsky.dao;

import com.mLastovsky.entity.TodoEntity;
import com.mLastovsky.exception.DaoException;
import com.mLastovsky.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoDao implements Dao<Long, TodoEntity> {

    private static final TodoDao INSTANCE = new TodoDao();

    private static final String SQL_DELETE = """
            DELETE FROM todos
            WHERE id = ?
            """;

    private static final String SQL_SAVE = """
            INSERT INTO todos (user_id, task, completed)
            VALUES (?,?,?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE todos
            SET user_id = ?,
                task = ?,
                completed = ?,
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

    public static TodoDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public TodoEntity save(TodoEntity todo) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, todo.getUserId());
            preparedStatement.setString(2, todo.getTask());
            preparedStatement.setBoolean(3, todo.getCompleted());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                todo.setId(generatedKeys.getLong("id"));
            }

            return todo;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(TodoEntity todo) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setLong(1, todo.getUserId());
            preparedStatement.setString(2, todo.getTask());
            preparedStatement.setBoolean(3, todo.getCompleted());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<TodoEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            TodoEntity todo = null;
            while (resultSet.next()) {
                todo = buildTodo(resultSet);
            }

            return Optional.ofNullable(todo);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TodoEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL)) {

            var resultSet = preparedStatement.executeQuery();
            List<TodoEntity> todos = new ArrayList<>();
            while (resultSet.next()) {
                todos.add(buildTodo(resultSet));
            }

            return todos;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static TodoEntity buildTodo(ResultSet resultSet) throws SQLException {
        return TodoEntity.builder()
                .id(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .task(resultSet.getString("task"))
                .completed(resultSet.getBoolean("completed"))
                .build();
    }
}
