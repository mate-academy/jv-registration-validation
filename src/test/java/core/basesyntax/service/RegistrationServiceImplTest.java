package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String MIN_LIMIT_AGE_MESSAGE = "You are so little";
    private static final String MIN_LENGTH_PASSWORD_MESSAGE
            = "Password needs 6 characters at least";
    private static final String EMPTY_PASSWORD_MESSAGE = "Password is empty";
    private static final String EMPTY_LOGIN_MESSAGE = "Login is empty";
    private static final String EMPTY_AGE_MESSAGE = "Age is empty";
    private static RegistrationService registrationService;

    private static StorageDao storageDao;
    private static User userUnderTest;

    @BeforeAll
    private static void setup() {
        registrationService = new RegistrationServiceImpl();
        userUnderTest = new User();
    }

    @BeforeEach
    private void beforeEach() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_ValidUser_Ok() {
        userUnderTest.setAge(18);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword("bandit1234");

        registrationService.register(userUnderTest);
        User actual = storageDao.get(userUnderTest.getLogin());
        assertEquals(userUnderTest, actual, "User isn`t registered");
    }

    @Test
    void register_TooYoungUser_NotOk() {
        userUnderTest.setAge(17);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword("bandit1234");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(MIN_LIMIT_AGE_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_TooShortPassword_NotOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword("Shrek");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(MIN_LENGTH_PASSWORD_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_PasswordIsEmpty_NotOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword(null);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(EMPTY_PASSWORD_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_LoginIsEmpty_NotOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin(null);
        userUnderTest.setPassword("PutinMurlo");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(EMPTY_LOGIN_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_AgeIsEmpty_NotOk() {
        userUnderTest.setAge(null);
        userUnderTest.setLogin("Lexus");
        userUnderTest.setPassword("PutinMurlo");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(EMPTY_AGE_MESSAGE, validationException.getMessage());
    }
}
