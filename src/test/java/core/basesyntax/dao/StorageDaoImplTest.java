package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDaoImpl storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        user = createNewUser();
        Storage.people.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void add_shouldAddUserCorrectly() {
        User addedUser = storageDao.add(user);
        assertNotNull(addedUser);
        assertEquals(1L, addedUser.getId());
        assertEquals("test_login", addedUser.getLogin());
    }

    @Test
    void add_whenNullUser_thenThrowException_notOk() {
        assertThrows(InvalidInputDataException.class, () -> storageDao.add(null));
    }

    @Test
    void get_shouldReturnUserByLogin_ok() {
        storageDao.add(user);
        User foundUser = storageDao.get(user.getLogin());
        assertNotNull(foundUser, "Should find a user by login");
        assertEquals(user.getLogin(), foundUser.getLogin(),
                "Found user login should match the requested login");
    }

    @Test
    void get_nonExistentLogin_returnsNull_notOk() {
        assertNull(storageDao.get("non_existent_login"),
                "Should return null for non-existent user login");
    }

    @Test
    void get_nullLogin_throwsException_notOk() {
        assertThrows(InvalidInputDataException.class, () -> storageDao.get(null),
                "Should throw InvalidInputDataException when trying to get user with null login");
    }

    public static User createNewUser() {
        return new User(1L, "test_login", "password_user", 18);
    }
}
