package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        Storage.people.add(new User("user123", "password123", 25));
        Storage.people.add(new User("user123", "password123", 25));
        Storage.people.add(new User("user456", "password456", 30));
    }

    @Test
    void loginIsExist_notOk() {
        String login = "user123";
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "password", 20)));
        assertNotNull(storageDao.get(login));
    }

    @Test
    void loginLengthIsShort_notOk() {
        String login = "Login";
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "password", 20)));
        assertNull(storageDao.get(login));
    }

    @Test
    void passwordLengthIsShort_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user678", "1234", 20)));
    }

    @Test
    void largeAge_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService
                .register(new User("user123", "password123", 15)));
    }

    @Test
    void addUserToDB_ok() {
        User user = new User("newUser", "newPassword", 25);
        storageDao.add(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void getUserByLogin_ok() {
        User user = new User("user123", "password123", 25);
        storageDao.add(user);
        assertEquals(storageDao.get("user123"), user);
    }

    @Test
    public void testIdSetterGetter_ok() {
       User user = new User("user123", "password123", 25);

        assertNull(user.getId());
        user.setId(1L);
        assertEquals(Long.valueOf(1L), user.getId());
    }

    @Test
    public void testGettersAndSetters_ok() {
        User user = new User("user123", "password123", 25);

        assertEquals("user123", user.getLogin());
        assertEquals("password123", user.getPassword());
        assertEquals(Integer.valueOf(25), user.getAge());

        user.setLogin("newUser");
        user.setPassword("newPassword");
        user.setAge(30);

        assertEquals("newUser", user.getLogin());
        assertEquals("newPassword", user.getPassword());
        assertEquals(Integer.valueOf(30), user.getAge());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("user123", "password123", 25);
        User user2 = new User("user123", "password123", 25);
        User user3 = new User("user456", "password456", 30);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }


}
