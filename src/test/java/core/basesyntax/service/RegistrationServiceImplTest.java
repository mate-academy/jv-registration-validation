package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private final User testUser = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser.setLogin("Albertino");
        testUser.setPassword("password");
        testUser.setAge(25);
    }

    @Test
    void registerUser_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "RegistrationException should be thrown if User is null!");
    }

    @Test
    void registerUser_youngUser_NotOk() {
        testUser.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
        testUser.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
        testUser.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
    }

    @Test
    void registerUser_shortLogin_NotOk() {
        testUser.setLogin("Bob");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                "RegistrationException should be thrown if User's login is no longer "
                + "than 6 symbols!");
    }

    @Test
    void registerUser_shortPassword_NotOk() {
        String errorMessage = "User's password is less than 6 symbols";
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                errorMessage);
        testUser.setPassword("Bob");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser),
                errorMessage);
    }

    @Test
    void registerUser_validPassword_Ok() throws RegistrationException {
        testUser.setPassword("Bobina");
        User registeredUser = registrationService.register(testUser);
        assertEquals(registeredUser, testUser);
    }

    @Test
    void registerUser_userExists_Ok() throws RegistrationException {
        registrationService.register(testUser);
        User userFromStorage = storageDao.get(testUser.getLogin());
        assertNotNull(userFromStorage);
        assertEquals(testUser.getLogin(), userFromStorage.getLogin());
    }
}
