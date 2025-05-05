package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    private RegistrationService registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_ok() {
        User validUser = new User("validLogin", "securePassword", 25);
        assertDoesNotThrow(() -> registrationService.register(validUser));
        assertNotNull(storageDao.get("validLogin"));
    }

    @Test
    void register_existingLogin_notOk() {
        User existingUser = new User("existingUser", "password123", 20);
        storageDao.add(existingUser);
        User duplicateUser = new User("existingUser", "password456", 22);

        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(duplicateUser));
        assertEquals("User with this login already exists.", exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User shortLoginUser = new User("short", "password123", 20);
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(shortLoginUser));
        assertEquals("Login must be at least 6 characters long.", exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User shortPasswordUser = new User("validLogin", "pass", 20);
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(shortPasswordUser));
        assertEquals("Password must be at least 6 characters long.", exception.getMessage());
    }

    @Test
    void register_underageUser_notOk() {
        User underageUser = new User("validLogin", "password123", 17);
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(underageUser));
        assertEquals("User must be at least 18 years old.", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = new User(null, "password123", 20);
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(nullLoginUser));
        assertEquals("Login cannot be null or empty.", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = new User("validLogin", null, 20);
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(nullPasswordUser));
        assertEquals("Password cannot be null or empty.", exception.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        Exception exception = assertThrows(InvalidUserException.class, () -> registrationService.register(null));
        assertEquals("User cannot be null.", exception.getMessage());
    }
}


