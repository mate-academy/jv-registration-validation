package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int ADULT_AGE = 18;
    public static final String VALID_PASSWORD = "123456";
    public static final String VALID_LOGIN = "CorrectLogin";
    private static RegistrationService registrationService;
    private User user;

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(ADULT_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
    }

    @Test
    void register_invalidUser_NotOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidDataException expected, "
                + "if user null.");
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(130);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if age more than 120.");
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if age less than 18.");
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException  expected, "
                + "if age null.");
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if password null.");
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if password length less than 6.");
    }

    @Test
    void register_invalidLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if login null");
        user.setLogin("q");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException expected, "
                + "if login length less than 2");
    }

    @Test
    void register_validAgeLoginPassword_Ok() {
        registrationService.register(user);
        assertNotNull(user.getId());
    }

    @Test
    void register_tryAddAnotherUserWithSameLogin_NotOk() {
        user.setLogin("Bob");
        registrationService.register(user);
        User userSameLogin = new User();
        userSameLogin.setLogin("Bob");
        userSameLogin.setAge(19);
        userSameLogin.setPassword(VALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userSameLogin);
        },"InvalidDataException expected, "
                + "if we try add user with same name.");
    }
}
