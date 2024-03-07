package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User("login1", "password1", 18));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User testUser = new User("login1", "password1", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_invalidUserLogin_notOk() {
        User testUser = new User(null, "validPassword", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));

        User testUser2 = new User("", "validPassword", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser2));

        User testUser3 = new User("asd", "validPassword", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser3));

        User testUser4 = new User("asdas", "validPassword", 18); // negative edge case for login
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser4));
    }

    @Test
    void register_validUserLogin_ok() {
        User testUser = new User("asdasd", "validPassword", 18); // positive edge case for login
        User registered = registrationService.register(testUser);
        assertNotNull(registered.getId());

        testUser = new User("validLogin", "validPassword", 18);
        User registered2 = registrationService.register(testUser);
        assertNotNull(registered2.getId());
    }

    @Test
    void register_invalidUserPassword_notOk() {
        User testUser = new User("validLogin", null, 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));

        User testUser2 = new User("validLogin", "", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser2));

        User testUser3 = new User("validLogin", "asd", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser3));

        User testUser4 = new User("validLogin", "asdas", 18); // negative edge case for password
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser4));
    }

    @Test
    void register_validUserPassword_ok() {
        User testUser = new User("validLogin", "asdasd", 18); // positive edge case for password
        User registered = registrationService.register(testUser);
        assertNotNull(registered.getId());

        testUser = new User("validLogin2", "validPassword", 18);
        User registered2 = registrationService.register(testUser);
        assertNotNull(registered2.getId());
    }

    @Test
    void register_invalidUserAge_notOk() {
        User testUser = new User("validLogin", "validPassword", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));

        User testUser2 = new User("validLogin", "validPassword", -8);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser2));

        User testUser3 = new User("validLogin", "validPassword", 17); // negative edge case for age
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void register_validUserAge_ok() {
        User testUser = new User("validLogin", "validPassword", 18); // positive edge case for age
        User registered = registrationService.register(testUser);
        assertNotNull(registered.getId());

        testUser = new User("validLogin2", "validPassword", 28);
        User registered2 = registrationService.register(testUser);
        assertNotNull(registered2.getId());
    }

    @Test
    void register_validUser_ok() {
        User testUser = new User("validLogin", "validPassword", 18);
        User registered = registrationService.register(testUser);
        assertNotNull(registered.getId());
    }
}
