package com.mLastovsky.service;

import com.mLastovsky.dao.UserDao;
import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.dto.UserDto;
import com.mLastovsky.exception.UserAlreadyExistsException;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.mapper.CreateUserMapper;
import com.mLastovsky.mapper.UserMapper;
import com.mLastovsky.validator.CreateUserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@Slf4j
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
        log.debug("Attempting login for user: {}", username);
        return userDao.findByLogin(username)
                .filter(user -> checkPassword(password, user.getPassword()))
                .map(userMapper::mapFrom);
    }

    private boolean checkPassword(String rawPassword, String hashedPassword) {
        boolean isPasswordValid = BCrypt.checkpw(rawPassword, hashedPassword);
        if (isPasswordValid) {
            log.info("Password matched successfully");
        } else {
            log.warn("Invalid password attempt");
        }
        return isPasswordValid;
    }

    public void create(CreateUserDto createUserDto) {
        log.debug("Validating user data for: {}", createUserDto.getUsername());
        var validationResult = userValidator.validate(createUserDto);

        if (!validationResult.isValid()) {
            log.error("Validation failed for user: {}", createUserDto.getUsername());
            throw new ValidationException(validationResult.getErrors());
        }

        if(!userExists(createUserDto.getUsername(), createUserDto.getEmail())){
            var userEntity = createUserMapper.mapFrom(createUserDto);
            log.debug("Saving new user to database: {}", createUserDto.getUsername());
            userDao.save(userEntity);
            log.info("User {} created successfully", createUserDto.getUsername());
        }
        else{
            log.warn("User with username '{}' or email '{}' already exists", createUserDto.getUsername(), createUserDto.getEmail());
            throw new UserAlreadyExistsException("User with this username or email already exists");
        }
    }

    public boolean userExists(String username, String email) {
        return userDao.findByLogin(username).isPresent() || userDao.findByEmail(email).isPresent();
    }
}
