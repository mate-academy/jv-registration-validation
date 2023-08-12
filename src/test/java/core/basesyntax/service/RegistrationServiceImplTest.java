package core.basesyntax.service;

import core.basesyntax.exception.NotValidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Arsen04";
    private static final String VALID_PASSWORD = "Arsen2004";
    private static final int VALID_AGE = 18;
    private static final String INVALID_LOGIN = "Arsen";
    private static final String INVALID_LOGIN_NULL = null;
    private static final String INVALID_PASSWORD = "Arsen";
    private static final String INVALID_PASSWORD_NULL = null;
    private static final int INVALID_AGE = 17;
    private static final Integer INVALID_AGE_NULL = null;
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(NotValidUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(INVALID_LOGIN_NULL);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(INVALID_PASSWORD_NULL);
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_AGE_NULL);
        user.setLogin(VALID_LOGIN);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginLength_notOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(INVALID_LOGIN);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_suchLoginAlreadyExists_notOk() {
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(NotValidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void userAddToStorage_Ok() {
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
