package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 140;

    @BeforeEach
    void setUp() {
        user.setLogin("Valid_Login");
        user.setPassword("Valid_Password");
        user.setAge(23);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        for (int i = -10; i < 200; i++) {
            user.setAge(i);
            if (i < MIN_AGE || i > MAX_AGE) {
                assertThrows(RuntimeException.class, () -> registrationService.register(user));
            }
        }
    }

    @Test
    void register_invalidPassLength_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("Abba");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("Jojob");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addCreatingUser_notOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneNewUser_Ok() {
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_addTwoNewUsers_Ok() {
        User user2 = new User();
        user2.setLogin("Valid_login_User2");
        user2.setPassword("Valid_password_User2");
        user2.setAge(24);

        registrationService.register(user);
        registrationService.register(user2);
        assertEquals(user, storageDao.get(user.getLogin()));
        assertEquals(user2, storageDao.get(user2.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}