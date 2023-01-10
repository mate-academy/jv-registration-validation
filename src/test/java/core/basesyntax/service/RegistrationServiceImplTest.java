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
    private static final String MIN_LIMIT_AGE_MESSAGE = "User age is less than min age";
    private static final String MIN_LENGTH_PASSWORD_MESSAGE
            = "Password needs 6 characters at least";
    private static final String NULL_PASSWORD_MESSAGE = "Password is null";
    private static final String NULL_LOGIN_MESSAGE = "Login is null";
    private static final String NULL_AGE_MESSAGE = "Age is null";
    private static RegistrationService registrationService;

    private static StorageDao storageDao;
    private static User userUnderTest;

    @BeforeAll
    private static void setup() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    private void beforeEach() {
        userUnderTest = new User();
        userUnderTest.setAge(18);
        userUnderTest.setLogin("Greek");
        userUnderTest.setPassword("bandit1234");
    }

    @Test
    void register_validUser_isOk() {
        userUnderTest.setAge(18);

        registrationService.register(userUnderTest);
        User actual = storageDao.get(userUnderTest.getLogin());
        assertEquals(userUnderTest, actual, "User isn`t registered");
    }

    @Test
    void register_tooYoungUser_notOk() {
        userUnderTest.setAge(17);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(MIN_LIMIT_AGE_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_tooShortPassword_notOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword("Shrek");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(MIN_LENGTH_PASSWORD_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_passwordIsNull_notOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin("Userok");
        userUnderTest.setPassword(null);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(NULL_PASSWORD_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_loginIsNull_notOk() {
        userUnderTest.setAge(19);
        userUnderTest.setLogin(null);
        userUnderTest.setPassword("PutinMurlo");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(NULL_LOGIN_MESSAGE, validationException.getMessage());
    }

    @Test
    void register_ageIsNull_notOk() {
        userUnderTest.setAge(null);
        userUnderTest.setLogin("Lexus");
        userUnderTest.setPassword("PutinMurlo");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> registrationService.register(userUnderTest));
        assertEquals(NULL_AGE_MESSAGE, validationException.getMessage());
    }
}
