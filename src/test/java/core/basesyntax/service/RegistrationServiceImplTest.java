package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    public static final String DEFAULT_USER_LOGIN = "userNameLogin";
    public static final String DEFAULT_USER_PASSWORD = "nS0$ek0)D";
    private static RegistrationService registerService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registerService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD, 18);
    }

    @AfterEach
    void afterEach() {
        Storage.getListOfPeople().clear();
    }

    @Test
    void register_userAgeLess18_notOk() {
        user.setAge(10);
        throwRegisterServiceException(user);
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        throwRegisterServiceException(user);
    }

    @Test
    void register_userNegativeAge_notOk() {
        user.setAge(-12);
        throwRegisterServiceException(user);
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        throwRegisterServiceException(user);
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        throwRegisterServiceException(user);
    }

    @Test
    void register_userLoginEmpty_notOk() {
        user.setLogin("");
        throwRegisterServiceException(user);
    }

    @Test
    void register_userPasswordNull_notOk() {
        user.setPassword(null);
        throwRegisterServiceException(user);
    }

    @Test
    void register_userPasswordSize6_notOk() {
        user.setPassword("12345");
        throwRegisterServiceException(user);
    }

    @Test
    void register_checkSameLogins_notOk() {
        User actual = new User(DEFAULT_USER_LOGIN, "userPassword", 18);
        registerService.register(actual);
    }

    @Test
    void register_checkingForTheSameLogins_notOk() {
        User sameUser = user;
        User actual = new User(DEFAULT_USER_LOGIN, "anotherPassword", 18);
        registerService.register(sameUser);
        throwRegisterServiceException(actual);
    }

    @Test
    void register_ExistingUser_notOk() {
        try {
            User sameUser = registerService.register(user);
            registerService.register(sameUser);
            throwRegisterServiceException(sameUser);
        } catch (RegistrationServiceException e) {
            return;
        }
        fail("User with login " + user.getLogin() + " exists");
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

    private void throwRegisterServiceException(User user) {
        assertThrows(RegistrationServiceException.class, () -> registerService.register(user));
    }
}
