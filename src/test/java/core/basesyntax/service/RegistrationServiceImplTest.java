package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AgeIsIncorrectException;
import core.basesyntax.exceptions.LoginIsIncorrectException;
import core.basesyntax.exceptions.PasswordIsIncorrectException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserIsNullException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userIsOk() {
        User bob = new User("bob", "qwertyu", 19);
        storageDao.add(bob);
        User testUser = new User("slava", "qwtrggggg", 23);
        User registeredUser = registrationService.register(testUser);
        assertEquals(new User("slava", "qwtrggggg", 23), registeredUser);
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User bob = new User("bob", "qwertyu", 19);
        User alice = new User("alice", "asdfghj", 20);
        User john = new User("john", "zxcvbnm", 21);
        storageDao.add(bob);
        storageDao.add(alice);
        storageDao.add(john);
        User testUser = new User("bob", "qwtrggggg", 23);
        assertThrows(LoginIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userLoginIsNull_notOk() {
        User testUser = new User(null, "qwtrggggg", 23);
        assertThrows(LoginIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User testUser = new User("Serg", "qwtrggggg", null);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User testUser = new User("Sergey", null, 19);
        assertThrows(PasswordIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeIsLess_NotOk() {
        User testUser = new User("ivan", "qwtrggggg", 17);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        User testUser = new User("Ivan", "qwtrggggg", -1);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordIncorrect_NotOk() {
        User testUser = new User("petr", "wwerr", 19);
        assertThrows(PasswordIsIncorrectException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
