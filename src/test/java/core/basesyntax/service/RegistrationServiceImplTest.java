package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EXCEPTION_MESSAGE =
            RegistrationValidationException.class.toString();
    private static final String FIRST_LOGIN = "abc";
    private static final String SECOND_LOGIN = "def";
    private static final String PASSWORD = "abcdef";
    private static final String INVALID_PASSWORD = "xyz";
    private static final Integer NEGATIVE_AGE = -3;
    private static final Integer INVALID_AGE = 9;
    private static final Integer AGE = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(FIRST_LOGIN);
        user.setPassword(PASSWORD);
        user.setAge(AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(null),
                String.format("%s should be thrown for user is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for login is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_existingLogin_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for already existing login: "
                        + FIRST_LOGIN, EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for password is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for invalid password: "
                        + INVALID_PASSWORD, EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for age is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for a negative age: "
                        + NEGATIVE_AGE, EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for a invalid age: "
                        + INVALID_AGE, EXCEPTION_MESSAGE));
    }

    @Test
    void register_validUser_Ok() {
        user.setLogin(SECOND_LOGIN);
        assertEquals(user, registrationService.register(user),
                "User must be registered");
    }
}
