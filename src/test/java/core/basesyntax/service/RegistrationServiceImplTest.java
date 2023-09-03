package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "123456";
    private static final Integer DEFAULT_AGE = 25;
    private static final List<User> USERS = new ArrayList<>();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void register_validUser_Ok() {
        USERS.add(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE));
        registrationService.register(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_twoValidUser_Ok() {
        User user1 = new User("user1Login", DEFAULT_PASSWORD, DEFAULT_AGE);
        USERS.add(user1);
        registrationService.register(user1);
        assertEquals(USERS.size(), Storage.PEOPLE.size());
        User user2 = new User("user2Login", DEFAULT_PASSWORD, DEFAULT_AGE);
        USERS.add(user2);
        registrationService.register(user2);
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_edgeLogin_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User(null, DEFAULT_PASSWORD, DEFAULT_AGE)));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_edgePassword_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User(DEFAULT_LOGIN, null, 18)));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_edgeAge_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null)));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_existingLogin_NotOk() {
        User user = new User("some login", DEFAULT_PASSWORD, DEFAULT_AGE);
        USERS.add(user);
        registrationService.register(user);
        assertEquals(USERS.size(), Storage.PEOPLE.size());
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_shortLogin_NotOk() {
        User user = new User("short", DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, "short", DEFAULT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_notAdultAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, 4);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, -4);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals(USERS.size(), Storage.PEOPLE.size());
    }
}
