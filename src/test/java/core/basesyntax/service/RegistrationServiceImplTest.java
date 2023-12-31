package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Long ID = 1L;
    private static final String LOGIN = "login6";
    private static final String LOGIN_2 = "loginD";
    private static final String PASSWORD = "123456";
    private static final int AGE = 18;
    private static RegistrationServiceImpl registrationService;
    private static RegistrationException exception;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        exception = new RegistrationException();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = createUser(ID, LOGIN, PASSWORD, AGE);
    }

    private User createUser(Long id, String login, String password, int age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_oneUser_Ok() {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_twoDifferUsers_Ok() {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());

        User user2 = createUser(ID, LOGIN_2, PASSWORD, AGE);
        User registeredUser2 = registrationService.register(user2);
        assertNotNull(registeredUser2);
        assertEquals(user2.getLogin(), registeredUser2.getLogin());
    }

    @Test
    void register_alreadyRegistered_notOk() {
        registrationService.register(user);
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                    registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.ALREADY_RGSTR_USER_MSG));
    }

    @Test
    void register_userNull_notOk() {
        user = null;
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                    registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.USER_NULL_MSG));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_LOGIN_MSG));
    }

    @Test
    void register_loginLengthLessMinValue_notOk() {
        user.setLogin("abcde");
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_LOGIN_MSG));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_PASSWORD_MSG));
    }

    @Test
    void register_passwordLengthLessMinValue_notOk() {
        user.setPassword("12345");
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_PASSWORD_MSG));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_AGE_MSG));
    }

    @Test
    void register_ageLessMinValue_notOk() {
        user.setAge(17);
        RegistrationException thrown = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertTrue(thrown.getMessage().equals(exception.INCORRECT_AGE_MSG));
    }
}
