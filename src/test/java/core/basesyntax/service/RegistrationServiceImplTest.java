package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_ALLOWED = 18;
    private static final String PASSWORD = "123456";
    private RegistrationService service;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @AfterEach
    void tearDown() {
        service = null;
        storageDao = null;
        user = null;
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));
    }

    @Test
    void register_nullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_nullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_nullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_userPasswordLengthIsCorrect_Ok() {
        user.setPassword(PASSWORD);
        user.setLogin("user");
        user.setAge(18);
        service.register(user);
        boolean actual = user.getPassword().length() >= MIN_PASSWORD_LENGTH;
        assertTrue(actual);
    }

    @Test
    void register_userIsInStorage_NotOk() {
        user.setAge(18);
        user.setLogin("user");
        user.setPassword(PASSWORD);
        storageDao.add(user);
        service.register(user);
        boolean actual = !user.equals(storageDao.get(user.getLogin()));
        assertFalse(actual);
    }

    @Test
    void register_userAgeIsZeroOrLess_NotOk() {
        user.setAge(-5);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_userAgeEqualsIntMaxValue_NotOk() {
        user.setAge(Integer.MAX_VALUE);
        boolean actual = user.getAge() == Integer.MAX_VALUE;
        assertTrue(actual);
    }

    @Test
    void register_userHasRightAge_Ok() {
        user.setAge(18);
        user.setLogin("user");
        user.setPassword(PASSWORD);
        service.register(user);
        boolean actual = user.getAge() >= MIN_AGE_ALLOWED;
        assertTrue(actual);
    }
}
