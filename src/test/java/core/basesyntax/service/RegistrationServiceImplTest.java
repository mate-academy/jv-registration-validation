package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String DEFAULT_USER_LOGIN = "userNameLogin";
    private static final String DEFAULT_USER_PASSWORD = "nS0$ek0)D";
    private static final int DEFAULT_USER_MIN_AGE = 18;
    private static final Integer NEGATIVE_AGE = -12;
    private static final String WRONG_PASSWORD = "12345";
    private static RegistrationService registerService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registerService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD, DEFAULT_USER_MIN_AGE);
    }

    @AfterEach
    void afterEach() {
        Storage.getListOfPeople().clear();
    }

    @Test
    void register_userAgeLess18_notOk() {
        user.setAge(DEFAULT_USER_MIN_AGE - 1);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userNegativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userLoginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userPasswordSize6_notOk() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_userSameLogin_notOk() {
        User sameUser = user;
        User actual = new User(DEFAULT_USER_LOGIN, "uaesrPassword", DEFAULT_USER_MIN_AGE);
        registerService.register(sameUser);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(actual));
    }

    @Test
    void register_ExistingUser_notOk() {
        registerService.register(user);
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }

    @Test
    void register_Age18_ok() {
        User actual = user;
        User expected = registerService.register(actual);
        assertEquals(expected, actual);
    }

    @Test
    void register_AgeMoreThan18_ok() {
        User actual = new User(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD, 24);
        User expected = registerService.register(actual);
        assertEquals(expected, actual);
    }
}
