package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_nullUser_notOk() {
        User nullUser = new User(null, null, null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUser));
    }

    @Test
    void register_nullUserLogin_notOk() {
        User nullUserLogin = new User(null, "password", 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUserLogin));
    }

    @Test
    void register_shortUserLogin_notOk() {
        User shortUserLogin = new User(null, "password", 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(shortUserLogin));
    }

    @Test
    void register_nullUserPassword_notOk() {
        User nullUserPassword = new User("login", null, 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUserPassword));
    }

    @Test
    void register_shortUserPassword_notOk() {
        User shortUserPassword = new User("login", null, 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(shortUserPassword));
    }

    @Test
    void register_userAgeIsLessThanAllowed_notOk() {
        User userAgeIsLessThanAllowed = new User("login", "password", 16);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userAgeIsLessThanAllowed));
    }

    @Test
    void register_userExist_notOk() {
        User newUser = new User("login", "password", 18);
        Storage.people.add(newUser);
        User userExist = new User("login", "password", 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userExist));
    }
}
