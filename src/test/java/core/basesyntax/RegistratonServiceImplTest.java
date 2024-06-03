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
    void register_invalidLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user01", "password", 20)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(null, "password", 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("", "password", 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("short", "password", 20)));
    }

    @Test
    void register_invalidPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user06", null, 19)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "", 20)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "123", 20)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "12345", 20)));
    }

    @Test
    void register_invalidAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user07", "password", null)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", 15)));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", -5)));
    }

    @Test
    void register_validLogin_Ok() {
        User userLoginLength6 = new User("user00", "password123", 30);
        User userLoginLength8 = new User("user0123", "password123", 30);
        registrationService.register(userLoginLength6);
        registrationService.register(userLoginLength8);
        assertTrue(Storage.PEOPLE.contains(userLoginLength6));
        assertTrue(Storage.PEOPLE.contains(userLoginLength8));
    }

    @Test
    void register_validPassword_Ok() {
        User userPasswordLength6 = new User("user12", "123456", 30);
        User userPasswordLength8 = new User("user012345", "12345678", 30);
        registrationService.register(userPasswordLength6);
        registrationService.register(userPasswordLength8);
        assertTrue(Storage.PEOPLE.contains(userPasswordLength6));
        assertTrue(Storage.PEOPLE.contains(userPasswordLength8));
    }

    @Test
    void register_validAge_Ok() {
        User userAge18 = new User("user33", "password", 18);
        User userAgeGreater18 = new User("user22", "password", 80);
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
