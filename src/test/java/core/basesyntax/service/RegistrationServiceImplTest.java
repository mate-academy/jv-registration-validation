package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN = "srgch7";
    private static final String LOGIN_SECOND_OCCUR = "tnhxeR";
    private static final String PASSWORD = "468271";
    private static final int AGE = 18;
    private static RegistrationServiceImpl registrationServiceImpl;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = createNewUser(LOGIN, PASSWORD, AGE);
    }

    @Test
    void register_singleUser_ok() {
        User registeredUser = registrationServiceImpl.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void registered_twoDifferentUsers_Ok() {
        User registeredUserFirst = registrationServiceImpl.register(user);
        assertNotNull(registeredUserFirst);
        assertEquals(user.getLogin(), registeredUserFirst.getLogin());

        User userTwo = createNewUser(LOGIN_SECOND_OCCUR, PASSWORD, AGE);
        User registeredUserSecond = registrationServiceImpl.register(userTwo);
        assertNotNull(registeredUserSecond);
        assertEquals(userTwo.getLogin(), registeredUserSecond.getLogin());
    }

    @Test
    void registered_nullUser_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_twoRegisteredUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_underpassword_notOk() {
        user.setPassword("36271");
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_underage_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_ageEqualsMin_notOk() {
        user.setAge(18);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_loginTooShort_notOk() {
        user.setLogin("rymwg");
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    @Test
    void registered_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(user));
    }

    private User createNewUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}