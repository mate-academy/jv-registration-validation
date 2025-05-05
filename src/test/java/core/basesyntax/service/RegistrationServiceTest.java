package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.model.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String VALID_LOGIN = "Valid login";
    private static final String VALID_PASSWORD = "Valid password";
    private static final String INVALID_BORDERLINE_LOGIN = "user1";
    private static final String INVALID_BORDERLINE_PASSWORD = "12345";
    private static final int VALID_AGE = 18;
    private static final int INVALID_BORDERLINE_AGE = 17;
    private static final int INVALID_NEGATIVE_AGE = -2;
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_checkUserExist_notOk() {
        User registeredUser = new User();
        registeredUser.setLogin(VALID_LOGIN);
        registeredUser.setPassword(VALID_PASSWORD);
        registeredUser.setAge(VALID_AGE);
        storageDao.add(registeredUser);
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("User with that login has already exists.Try new login!",
                exception.getMessage());
    }

    @Test
    void register_successRegistration_Ok() {
        User registeredUser = new User();
        registeredUser.setLogin(VALID_LOGIN);
        registeredUser.setPassword(VALID_PASSWORD);
        registeredUser.setAge(VALID_AGE);
        User actualUser = registrationService.register(registeredUser);
        assertEquals(registeredUser, actualUser);
    }

    @Test
    void register_nullLogin_notOk() {
        User userNullLogin = new User();
        userNullLogin.setLogin(null);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(userNullLogin);
        });
        assertEquals("Login cannot be null!", exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User userShortLogin = new User();
        userShortLogin.setLogin(INVALID_BORDERLINE_LOGIN);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(userShortLogin);
        });
        assertEquals("Login cannot be less than 6 characters!", exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User userShortPassword = new User();
        userShortPassword.setLogin(VALID_LOGIN);
        userShortPassword.setPassword(INVALID_BORDERLINE_PASSWORD);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(userShortPassword);
        });
        assertEquals("Password cannot be less than 6 characters!", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User userNullPassword = new User();
        userNullPassword.setLogin(VALID_LOGIN);
        userNullPassword.setPassword(null);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(userNullPassword);
        });
        assertEquals("Password cannot be null!", exception.getMessage());
    }

    @Test
    void register_lowAge_notOk() {
        User userLowBorderlineAge = new User();
        userLowBorderlineAge.setLogin(VALID_LOGIN);
        userLowBorderlineAge.setPassword(VALID_PASSWORD);
        userLowBorderlineAge.setAge(INVALID_BORDERLINE_AGE);
        ValidationException borderlineAgeException = assertThrows(ValidationException.class, () -> {
            registrationService.register(userLowBorderlineAge);
        });
        assertEquals("Age cannot be less than 18!", borderlineAgeException.getMessage());
        User userLowNegativeAge = new User();
        userLowNegativeAge.setLogin(VALID_LOGIN);
        userLowNegativeAge.setPassword(VALID_PASSWORD);
        userLowNegativeAge.setAge(INVALID_NEGATIVE_AGE);
        ValidationException negativeAgeException = assertThrows(ValidationException.class, () -> {
            registrationService.register(userLowNegativeAge);
        });
        assertEquals("Age cannot be less than 18!", negativeAgeException.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        User userNullAge = new User();
        userNullAge.setLogin(VALID_LOGIN);
        userNullAge.setPassword(VALID_PASSWORD);
        userNullAge.setAge(null);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.register(userNullAge);
        });
        assertEquals("Age cannot be null!", exception.getMessage());
    }
}
