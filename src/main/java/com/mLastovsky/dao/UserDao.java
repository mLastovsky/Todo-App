package com.mLastovsky.dao;

import com.mLastovsky.entity.UserEntity;
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
public class UserDao implements Dao<Long, UserEntity> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SQL_DELETE = """
            DELETE FROM users
            WHERE id = ?
            """;

    private static final String SQL_SAVE = """
            INSERT INTO users (username, password, email)
            VALUES (?,?,?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE users
            SET username = ?,
                password = ?,
                email = ?
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id,
                username,
                password,
                email
            FROM users
            """;

    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + """
            WHERE id = ?""";

    private static final String SQL_FIND_BY_LOGIN = """
            SELECT id,
               username,
               password,
               email
            FROM users
            WHERE username = ?
            """;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Attempting to delete user with ID: {}", id);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);

            boolean result = preparedStatement.executeUpdate() > 0;
            if (result) {
                log.info("Successfully deleted user with ID: {}", id);
            } else {
                log.warn("No user found with ID: {} to delete", id);
            }
            return result;
        } catch (SQLException e) {
            log.error("Failed to delete user with ID: {}", id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public UserEntity save(UserEntity user) {
        log.debug("Attempting to save user: {}", user);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
                log.info("User saved successfully with ID: {}", user.getId());
            }

            return user;
        } catch (SQLException e) {
            log.error("Failed to save user: {}", user, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        log.debug("Attempting to update user: {}", user);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();
            log.info("User updated successfully: {}", user);
        } catch (SQLException e) {
            log.error("Failed to update user: {}", user, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        log.debug("Searching for user by ID: {}", id);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            while (resultSet.next()) {
                user = buildUser(resultSet);
            }

            if (user != null) {
                log.info("User found with ID: {}", id);
            } else {
                log.warn("No user found with ID: {}", id);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            log.error("Failed to find user by ID: {}", id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        log.debug("Retrieving all users");
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL)) {

            var resultSet = preparedStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            log.info("Retrieved {} users", users.size());
            return users;
        } catch (SQLException e) {
            log.error("Failed to retrieve all users", e);
            throw new DaoException(e);
        }
    }

    public Optional<UserEntity> findByLogin(String username) {
        log.debug("Attempting to find user by username: {}", username);
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_LOGIN)) {
            preparedStatement.setString(1, username);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            while (resultSet.next()) {
                user = buildUser(resultSet);
            }

            if (user != null) {
                log.info("User found for username: {}", username);
            } else {
                log.warn("No user found for username: {}", username);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            log.error("Failed to find user by username: {}", username, e);
            throw new DaoException(e);
        }
    }

    private UserEntity buildUser(ResultSet resultSet) throws SQLException {
        log.trace("Building UserEntity from ResultSet");
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}
