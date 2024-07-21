package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private StorageDaoImpl storage;

    @BeforeEach
    void setUp() {
        storage = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
    }

    @Test
    void registerExistingLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("TESTLOGIN");
        storage.add(existingUser);
        User newUser = new User();
        newUser.setLogin("TESTLOGIN");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(newUser);
        });
        assertEquals("User with the same login already exists", exception.getMessage());
    }

    @Test
    void registerWithShortLogin_notOk() {
        User testUser = new User();
        testUser.setLogin("TEST");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerWithShortPassword_notOk() {
        User testuser = new User();
        testuser.setLogin("validLogin");
        testuser.setPassword("abc");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(testuser);
        });
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerValidUser_Ok() {
        User testUser = new User();
        testUser.setLogin("testLogin");
        testUser.setPassword("testPassword");
        testUser.setAge(25);
        assertDoesNotThrow(() -> service.register(testUser));
        assertEquals(testUser, storage.get(testUser.getLogin()));
    }

    @Test
    void registerLowAge_notOk() {
        User testUser = new User();
        testUser.setLogin("validLogin");
        testUser.setPassword("testPassword");
        testUser.setAge(15);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Age must be at least 18 years old", exception.getMessage());
    }

    @Test
    void registerNullLogin_notOk() {
        User testUser = new User();
        testUser.setLogin(null);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerNullPassword_notOk() {
        User testUser = new User();
        testUser.setLogin("testlogin");
        testUser.setPassword(null);
        testUser.setAge(25);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerWithNumbersPasswordAndLogin_Ok() {
        User testUser = new User();
        testUser.setLogin("123456");
        testUser.setPassword("123456");
        testUser.setAge(25);
        assertDoesNotThrow(() -> service.register(testUser));
        assertEquals(testUser, storage.get(testUser.getLogin()));
    }
}
