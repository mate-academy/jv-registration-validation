package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl REGISTRATION_SERVICE
            = new RegistrationServiceImpl();
    private static final StorageDaoImpl STORAGE_DAO = new StorageDaoImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void addUser_NullLogin_ThrowsException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void exists_Login() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        storageDao.add(user);

        User foundUser = storageDao.get("Alice123");
        assertNotNull(foundUser);
        assertEquals("Alice123", foundUser.getLogin());
    }

    @Test
    void addUser_EmptyLogin_ThrowsException() {
        User user = new User();
        user.setLogin("");
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_NullPassword_ThrowsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_EmptyPassword_ThrowsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_LoginLessThanMinLength_ThrowsException() {
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("qwerty1234");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_PasswordLessThanMinLength_ThrowsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("qwert");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_Underage_ThrowsException() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void addUser_WithValidDetails_Success() {
        User user = new User();
        user.setPassword("qwerty123");
        user.setLogin("Alex1976");
        user.setAge(27);
        User registeredUser = REGISTRATION_SERVICE.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void addUser_InvalidAge_ThrowsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("password");
        user.setAge((int) (Integer.MAX_VALUE + 1L));
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void testGetUserByLogin_Success() {
        User user = new User();
        user.setLogin("abcdr22");
        user.setPassword("jjjjjjj");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User retrievedUser = STORAGE_DAO.get(user.getLogin());
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetUserByNonexistentLogin_ReturnsNull() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User retrievedUser = STORAGE_DAO.get("NonexistentLogin");
        assertNull(retrievedUser);
    }

    @Test
    void testGetUserWithoutGetterMethod_Success() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User retrievedUser = STORAGE_DAO.get("Alice123");
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetUserAfterDirectStorageAdd_Success() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        Storage.people.add(user);
        User retrievedUser = STORAGE_DAO.get(user.getLogin());
        assertEquals(user, retrievedUser);
    }
}
