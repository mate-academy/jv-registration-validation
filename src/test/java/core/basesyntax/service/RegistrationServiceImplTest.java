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

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void user_nullValue_notOk() {
        assertThrows(UserIsNullException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userIsOk() {
        StorageDao storageDao = new StorageDaoImpl();
        User bob = new User("bob", "qwertyu", 19);
        User alice = new User("alice", "asdfghj", 20);
        User john = new User("john", "zxcvbnm", 21);
        storageDao.add(bob);
        storageDao.add(alice);
        storageDao.add(john);
        User slava = new User("slava", "qwtrggggg", 23);
        User newUser = registrationService.register(slava);
        assertEquals(new User("slava", "qwtrggggg", 23), newUser);
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        User bob = new User("bob", "qwertyu", 19);
        User alice = new User("alice", "asdfghj", 20);
        User john = new User("john", "zxcvbnm", 21);
        storageDao.add(bob);
        storageDao.add(alice);
        storageDao.add(john);
        User slava = new User("bob", "qwtrggggg", 23);
        assertThrows(LoginIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_userLoginIsNull_notOk() {
        User slava = new User(null, "qwtrggggg", 23);
        assertThrows(LoginIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User slava = new User("Serg", "qwtrggggg", null);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User slava = new User("Sergey", null, 19);
        assertThrows(PasswordIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_userAgeIsLess_NotOk() {
        User slava = new User("ivan", "qwtrggggg", 17);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        User slava = new User("Ivan", "qwtrggggg", -1);
        assertThrows(AgeIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @Test
    void register_passwordIncorrect_NotOk() {
        User slava = new User("petr", "wwerr", 19);
        assertThrows(PasswordIsIncorrectException.class, () -> {
            registrationService.register(slava);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
