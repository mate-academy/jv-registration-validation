package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistratonTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.PEOPLE.add(new User("user123", "password123", 25));
        Storage.PEOPLE.add(new User("user456", "password456", 30));
    }

    @Test
    void register_LoginNotNull_notOk() {
        String login = "user123";
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "password", 20)));
        assertNotNull(storageDao.get(login));
    }

    @Test
    void register_ShortLogin_notOk() {
        String login = "Login";
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "password", 20)));
        assertNull(storageDao.get(login));
    }

    @Test
    void register_ShortPassword_notOk() {
        String password = "1234";
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", password, 20)));
    }

    @Test
    void register_LargeAge_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", 15)));
    }

    @Test
    void addUserToDB_ok() {
        User user = new User("newUser", "newPassword", 25);
        storageDao.add(user);
        assertTrue(Storage.PEOPLE.contains(user));
    }

    @Test
    void getUserByLogin_ok() {
        User user = new User("user123", "password123", 25);
        storageDao.add(user);
        User retrieveUser = storageDao.get("user123");
        assertNotNull(retrieveUser);
        assertEquals(user.getLogin(),retrieveUser.getLogin());
    }

    @Test
    void registerUserToDb_ok() {
        User user = new User("registeredNewUser", "password123", 30);
        registrationService.register(user);
        assertTrue(Storage.PEOPLE.contains(user));
    }
}
