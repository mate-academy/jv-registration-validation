package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final User[] UNIQUE_CORRECT_USERS = new User[]{new User("testUser1",
            "testPassword1", 20),
            new User("testUser2", "testPassword2", 27),
            new User("testUser3", "testPassword3", 40),};

    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_successfulRegistration() {
        User user = new User("testUser", "testPassword", 20);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_existingUser_throwException() {
        User user = new User("existingUser", "password123", 25);
        storageDao.add(user);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_throwException() {
        User user = new User("short", "password123", 25);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_throwException() {
        User user = new User("validUser", "short", 25);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_throwException() {
        User user = new User("validUser", "password123", 17);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_throwException() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_UniqueUsers() {
        for (User user : UNIQUE_CORRECT_USERS) {
            User registeredUser = registrationService.register(user);

            assertNotNull(registeredUser);
            assertEquals(user, registeredUser);
        }
    }

    @Test
    void register_multipleUsersIncludingInvalid_oneUserNotAdded() {
        final User validUser1 = new User("correctUser1", "password123", 20);
        final User validUser2 = new User("correctUser2", "password456", 25);

        User registeredUser1 = registrationService.register(validUser1);
        assertNotNull(registeredUser1);
        assertEquals(validUser1, registeredUser1);

        User invalidUser = new User("short", "pass", 18);
        assertThrows(InvalidUserException.class, () -> registrationService.register(invalidUser));

        User registeredUser2 = registrationService.register(validUser2);
        assertNotNull(registeredUser2);
        assertEquals(validUser2, registeredUser2);

        assertEquals(validUser1, storageDao.get(validUser1.getLogin()));
        assertNull(storageDao.get(invalidUser.getLogin()));
        assertEquals(validUser2, storageDao.get(validUser2.getLogin()));
    }

    @Test
    void register_twoUsersWithEqualPassword_successfulRegistration() {
        String commonPassword = "password123";

        User user1 = new User("correctUser11", commonPassword, 20);
        User user2 = new User("correctUser22", commonPassword, 25);

        User registeredUser1 = registrationService.register(user1);
        assertNotNull(registeredUser1);
        assertEquals(user1, registeredUser1);

        User registeredUser2 = registrationService.register(user2);
        assertNotNull(registeredUser2);
        assertEquals(user2, registeredUser2);

        assertEquals(registeredUser1, storageDao.get(registeredUser1.getLogin()));
        assertEquals(registeredUser2, storageDao.get(registeredUser2.getLogin()));
    }
}
