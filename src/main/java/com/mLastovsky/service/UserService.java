package com.mLastovsky.service;

import com.mLastovsky.dao.UserDao;
import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.dto.UserDto;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.mapper.CreateUserMapper;
import com.mLastovsky.mapper.UserMapper;
import com.mLastovsky.validator.CreateUserValidator;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator userValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String username, String password) {
        return userDao.findByLogin(username)
                .filter(user -> BCrypt.checkpw(password, user.getPassword()))
                .map(userMapper::mapFrom);
    }

    public void create(CreateUserDto createUserDto) {
        var validationResult = userValidator.validate(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(createUserDto);
        userDao.save(userEntity);
    }
}
