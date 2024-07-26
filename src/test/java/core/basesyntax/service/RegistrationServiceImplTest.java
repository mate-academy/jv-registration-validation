package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        User user = new User("login", "password", 18);
        user.setLogin("svitlanakozak12332@gmail.com");
        user.setPassword("987654");
        user.setAge(38);

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_validUser_ok() {
        User user = new User("svitlanakozak12332@gmail.com", "987654", 38);
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User(null, "password", 18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortUserLogin_notOk() {
        User user = new User("lana", "password", 18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullUserPassword_notOk() {
        User user = new User("login", null, 18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortUserPassword_notOk() {
        User user = new User("login", "456", 18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void negativeAge_notOk() {
        User user = new User("login", "password", -1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsLessThanAllowed_notOk() {
        User user = new User("login", "password", 16);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userExist_notOk() {
        User newUser = new User("login", "password", 18);
        Storage.people.add(newUser);
        User userExist = new User("login", "password", 18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userExist));
    }
}
