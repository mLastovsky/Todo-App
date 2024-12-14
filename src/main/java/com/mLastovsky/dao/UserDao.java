package com.mLastovsky.dao;

import com.mLastovsky.entity.UserEntity;
import com.mLastovsky.exception.DaoException;
import com.mLastovsky.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = """
            SELECT id,
               username,
               password,
               email
            FROM users
            WHERE username = ? AND password = ?
            """;

    public static UserDao getInstance() {
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
    public UserEntity save(UserEntity user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            while (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL)) {

            var resultSet = preparedStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<UserEntity> findByLoginAndPassword(String username, String password) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            while (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserEntity buildUser(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}
