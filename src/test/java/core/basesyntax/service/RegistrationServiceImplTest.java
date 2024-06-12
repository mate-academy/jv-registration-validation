package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final int AGE = 30;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setAge(AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_NullForLogin_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullForAge_Not_Ok() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NulForPassword_Not_Ok() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_EmptyLineForLogin_NotOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLengthLimit_Not_Ok() {
        user.setPassword("not");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeLimit_Not_Ok() {
        user.setAge(3);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SameUserLogin_NotOK() {
        registrationService.register(user);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_User_ValidData_Ok() {
        assertSame(user, registrationService.register(user));
    }
}
