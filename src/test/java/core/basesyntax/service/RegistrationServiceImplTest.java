package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String TEST_LOGIN_ONE = "testLoginOne";
    private static final String TEST_LOGIN_TWO = "testLoginTwo";
    private static final String TEST_PASSWORD = "testPassword";
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
        existingUser.setLogin(TEST_LOGIN_ONE);
        storage.add(existingUser);
        User newUser = new User();
        newUser.setLogin(TEST_LOGIN_ONE);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(newUser);
        });
        assertEquals("User with the same login already exists", exception.getMessage());
    }

    @Test
    void registerWithShortLogin_notOk() {
        User testUser = new User();
        testUser.setLogin("TEST");
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerWithShortPassword_notOk() {
        User testuser = new User();
        testuser.setLogin(TEST_LOGIN_ONE);
        testuser.setPassword("abc");
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testuser);
        });
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerValidUser_Ok() {
        User testUser = new User();
        testUser.setLogin(TEST_LOGIN_ONE);
        testUser.setPassword(TEST_PASSWORD);
        testUser.setAge(25);
        User actualUser = service.register(testUser);
        assertEquals(testUser, actualUser);
    }

    @Test
    void registerLowAge_notOk() {
        User testUser = new User();
        testUser.setLogin(TEST_LOGIN_TWO);
        testUser.setPassword(TEST_PASSWORD);
        testUser.setAge(15);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Age must be at least 18 years old", exception.getMessage());
    }

    @Test
    void registerNullLogin_notOk() {
        User testUser = new User();
        testUser.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerNullPassword_notOk() {
        User testUser = new User();
        testUser.setLogin(TEST_LOGIN_TWO);
        testUser.setPassword(null);
        testUser.setAge(25);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void registerWithNegativeAge_notOk() {
        User testUser = new User();
        testUser.setLogin(TEST_LOGIN_TWO);
        testUser.setPassword(TEST_PASSWORD);
        testUser.setAge(-1);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            service.register(testUser);
        });
        assertEquals("Age must be at least 18 years old", exception.getMessage());
    }
}
