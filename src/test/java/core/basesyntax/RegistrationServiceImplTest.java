package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User("valid login", "valid password", 20));
    }

    @Test
    void register_validLogin_Ok() {
        User user1 = registrationService.register(new User("validl", "valid password", 25));
        assertEquals(new User("validl", "valid password", 25), user1);
        User user2 = registrationService.register(new User("valid ", "valid password", 25));
        assertEquals(new User("valid ", "valid password", 25), user2);
        User user3 = registrationService.register(new User("validlogin123", "valid password", 25));
        assertEquals(new User("validlogin123", "valid password", 25), user3);
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
    }

    @Test
    void register_validPassword_Ok() {
        User user1 = registrationService.register(new User("valid login1", "validp", 20));
        assertEquals(new User("valid login1", "validp", 20), user1);
        User user2 = registrationService.register(new User("valid login2", "valid ", 20));
        assertEquals(new User("valid login2", "valid ", 20), user2);
        User user3 = registrationService.register(new User("valid login3", "validpassword123", 20));
        assertEquals(new User("valid login3", "validpassword123", 20), user3);
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
    }

    @Test
    void register_validAge_Ok() {
        User user1 = registrationService.register(new User("valid login1", "valid password", 18));
        assertEquals(new User("valid login1", "valid password", 18), user1);
        User user2 = registrationService.register(new User("valid login2", "valid password", 1096));
        assertEquals(new User("valid login2", "valid password", 1096), user2);
        User user3 = registrationService.register(new User("valid login3", "valid password", 87));
        assertEquals(new User("valid login3", "valid password", 87), user3);
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
    }

    @Test
    void register_sameUsers_notOk() {
        User user1 = registrationService.register(new User("validLogin", "valid password", 20));
        assertEquals(new User("validLogin", "valid password", 20), user1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validLogin", "valid password", 20));
        }, "We already add this user");
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "validpassword", 43));
        }, "User login can not ba null");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("", "validpassword", 43));
        }, "User login must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("v", "validpassword", 43));
        }, "User login must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("val", "validpassword", 43));
        }, "User login must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("valid", "validpassword", 43));
        }, "User login must have at least 6 characters");
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", null, 43));
        }, "User password can not be null");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "", 43));
        }, "User password must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "p", 43));
        }, "User password must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "pas", 43));
        }, "User password must have at least 6 characters");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "passw", 43));
        }, "User password must have at least 6 characters");
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "password", null));
        }, "User age can not be null");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "password", 0));
        }, "User age must be 18 at least");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "password", 1));
        }, "User age must be 18 at least");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "password", 10));
        }, "User age must be 18 at least");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("validl", "password", 17));
        }, "User age must be 18 at least");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
