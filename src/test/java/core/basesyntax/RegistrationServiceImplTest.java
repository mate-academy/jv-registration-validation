package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("validLogin", "password123", 25);
        Storage.people.addAll(Arrays.asList(new User("Bob123", "password123", 20),
                new User("Alice231", "password123", 25),
                new User("Vladimir295", "password123", 25),
                new User("Semen294", "short", 25),
                new User("Pavel194", "password123", 17),
                new User("Joe0192", "validPass456", 30)));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLoginLength_NotOk() {
        user.setLogin("login");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minPasswordLength_NotOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_belowAge_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_belowZeroAge_NotOk() {
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginAlreadyExist_NotOk() {
        Storage.people.add(user);
        User sameUser = new User("Bob123", "654321", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

}
