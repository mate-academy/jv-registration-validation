package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validLoginPasswordAge_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("validUser", registeredUser.getLogin(), "Login should match");
    }

    @Test
    void register_InvalidLogin_exceptionThrown() {
        User user = new User();
        user.setLogin("abc");

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for short login");
    }

    @Test
    void register_InvalidPassword_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for short password");
    }

    @Test
    void register_InvalidAge_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(16);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for age < 18");
    }

    @Test
    void register_nullLogin_exceptionThrown() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("securePass");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Login cannot be null");
    }

    @Test
    void register_nullPassword_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Password cannot be null");
    }

    @Test
    void register_nullAge_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Age cannot be null");
    }

    @Test
    void register_loginLessThan6_exceptionThrown() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("securePass");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Login must be at least 6 characters, but was 'abc'");
    }

    @Test
    void register_loginEqualTo6_ok() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("securePass");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("abcdef", registeredUser.getLogin(), "Login should match");
    }

    @Test
    void register_loginGreaterThan6_ok() {
        User user = new User();
        user.setLogin("validUser123");
        user.setPassword("securePass");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("validUser123", registeredUser.getLogin(), "Login should match");
    }

    @Test
    void register_passwordLessThan6_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Password must be at least 6 characters, but was '12345'");
    }

    @Test
    void register_passwordEqualTo6_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("secure");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("secure", registeredUser.getPassword(), "Password should match");
    }

    @Test
    void register_passwordGreaterThan6_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePassword123");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("securePassword123", registeredUser.getPassword(), "Password should match");
    }

    @Test
    void register_ageLessThan18_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(16);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User must be at least 18 years old, but age was '16'");
    }

    @Test
    void register_ageEqualTo18_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(18);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals(18, registeredUser.getAge(), "Age should match");
    }

    @Test
    void register_ageGreaterThan18_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals(25, registeredUser.getAge(), "Age should match");
    }

}
