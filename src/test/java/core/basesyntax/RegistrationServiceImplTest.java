package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User("validLogin", "password123", 25);
        Storage.people.addAll(Arrays.asList(new User("Bob123", "password123", 20),
                new User("Alice231", "password123", 25),
                new User("Vladimir295", "password123", 25),
                new User("Semen294", "short", 25),
                new User("Pavel194", "password123", 17),
                new User("Joe0192", "validPass456", 30)));
    }

    @Test
    void register_nullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyLogin_NotOk() {
        testUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyPassword_NotOk() {
        testUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_minLoginLength_NotOk() {
        testUser.setLogin("login");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_minPasswordLength_NotOk() {
        testUser.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_belowAge_NotOk() {
        testUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_belowZeroAge_NotOk() {
        testUser.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginAlreadyExist_NotOk() {
        Storage.people.add(testUser);
        User sameUser = new User("Bob123", "654321", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ValidUser_Ok() {
        User actualUser = registrationService.register(testUser);
        User registeredUser = Storage.people.stream()
                .filter(user -> user.getLogin().equals(testUser.getLogin()))
                .findFirst()
                .orElse(null);
        assertEquals(testUser, actualUser);
        assertNotNull(registeredUser);
        assertTrue(Storage.people.contains(testUser));
        assertEquals(testUser, registeredUser);

        assertEquals(testUser.getId(), registeredUser.getId());
        assertEquals(testUser.getLogin(), registeredUser.getLogin());
        assertEquals(testUser.getPassword(), registeredUser.getPassword());
        assertEquals(testUser.getAge(), registeredUser.getAge());
    }
}

