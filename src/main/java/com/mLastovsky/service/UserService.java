package com.mLastovsky.service;

import com.mLastovsky.dao.UserDao;
import com.mLastovsky.dto.UserDto;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.mapper.UserMapper;
import com.mLastovsky.validator.UserValidator;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final UserValidator userValidator = UserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public static UserService getInstance(){
        return INSTANCE;
    }

    public void create(UserDto userDto){
        var validationResult = userValidator.isValid(userDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = userMapper.mapFrom(userDto);
        userDao.save(userEntity);
    }
}
