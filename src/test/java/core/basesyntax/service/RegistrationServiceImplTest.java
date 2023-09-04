package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "123456";
    private static final String SOME_LOGIN = "some login";
    private static final String USER1_LOGIN = "user1Login";
    private static final String USER2_LOGIN = "user2Login";
    private static final String SHORT_LOGIN = "short";
    private static final Integer DEFAULT_AGE = 25;
    private static final int UNCORRECT_AGE = 4;
    private static RegistrationServiceImpl registrationService;
    private final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_twoValidUser_Ok() {
        User user1 = new User(USER1_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertEquals(user1, registrationService.register(user1));
        User user2 = new User(USER2_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_existingLogin_NotOk() {
        user.setLogin(SOME_LOGIN);
        assertEquals(user, registrationService.register(user));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortLogin_NotOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_notAdultAge_NotOk() {
        user.setAge(UNCORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-UNCORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }
}
