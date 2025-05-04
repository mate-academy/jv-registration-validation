package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private StorageDaoImpl storage;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storage);
    }

    @Test
    void register_validUser_userIsRegistered() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(25);
        registrationService.register(user);
        assertNotNull(storage.get("validLogin"));
    }

    @Test
    void register_shortLogin_throwsException() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPass");
        user.setAge(25);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_shortPassword_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(25);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_underageUser_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    void register_existingLogin_throwsException() {
        User user1 = new User();
        user1.setLogin("validLogin");
        user1.setPassword("validPass");
        user1.setAge(25);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("validLogin");
        user2.setPassword("newPassword");
        user2.setAge(30);

        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user2));
        assertEquals("User with this login already exists", exception.getMessage());
    }

    // Test Case 6: User is null
    @Test
    void register_nullUser_throwsException() {
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(null));
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void register_nullLogin_throwsException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPass");
        user.setAge(25);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_nullPassword_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(25);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_negativeAge_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(-1);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }
}
