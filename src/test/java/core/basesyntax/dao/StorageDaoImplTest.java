package core.basesyntax.dao;

import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void shouldAddUserToStorage() {
    User user = new User();
    user.setLogin("correct_Login");
    user.setPassword("correct_Password");
    user.setAge(25);

        User addedUser = storageDao.add(user);

        assertEquals(user, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void shouldGetUserByLogin() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(25);

        storageDao.add(user);

        User getUser = storageDao.get("correct_Login");
        assertEquals(user, getUser);
    }

    @Test
    void shouldThrowExceptionForEmptyLogin() {
        User user = new User();
        user.setLogin("");
        user.setPassword("correct_Password");
        user.setAge(25);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> storageDao.add(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullLogin() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("correct_Password");
        user.setAge(25);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> storageDao.add(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(null);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> storageDao.add(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativeAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(-5);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> storageDao.add(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }
}