package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistratonServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.PEOPLE.add(new User("user01", "password123", 25));
    }

    @Test
    void invalidLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(null, "password", 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("", "password", 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("short", "password", 20)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user01", "password", 20)));
    }

    @Test
    void invalidPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user06", null, 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "", 20)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "1234", 20)));
    }

    @Test
    void invalidAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user07", "password", null)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", 15)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", -5)));
    }

    @Test
    void register_ok() {
        User user1 = new User("user02", "password123", 30);
        User user2 = new User("user03", "password123", 30);
        User user3 = new User("user04", "password123", 30);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertTrue(Storage.PEOPLE.contains(user1));
        assertTrue(Storage.PEOPLE.contains(user2));
        assertTrue(Storage.PEOPLE.contains(user3));
    }

    @Test
    void validLogin_ok() {
        User userLoginLength6 = new User("user00", "password123", 30);
        User userLoginLength10 = new User("user012345", "password123", 30);
        registrationService.register(userLoginLength6);
        registrationService.register(userLoginLength10);
        assertTrue(Storage.PEOPLE.contains(userLoginLength6));
        assertTrue(Storage.PEOPLE.contains(userLoginLength10));
    }

    @Test
    void validPassword_ok() {
        User userPasswordLength6 = new User("user00", "123456", 30);
        User userpasswordLength10 = new User("user012345", "1234567890", 30);
        registrationService.register(userPasswordLength6);
        registrationService.register(userpasswordLength10);
        assertTrue(Storage.PEOPLE.contains(userPasswordLength6));
        assertTrue(Storage.PEOPLE.contains(userpasswordLength10));
    }

    @Test
    void validAge_ok() {
        User userAge18 = new User("user00", "password", 18);
        User userAgeGreater18 = new User("user012345", "password", 80);
        registrationService.register(userAge18);
        registrationService.register(userAgeGreater18);
        assertTrue(Storage.PEOPLE.contains(userAge18));
        assertTrue(Storage.PEOPLE.contains(userAgeGreater18));
    }

    @AfterEach
    void tearDown() {
        // Clear the PEOPLE list after each test
        Storage.PEOPLE.clear();
    }
}
