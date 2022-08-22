package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_userIsNull_notOk() {
        checkException(null);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        checkException(user);
    }

    @Test
    void register_minAge_Ok() {
        user.setAge(MIN_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        checkException(user);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        checkException(user);
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("passw");
        checkException(user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private void checkException(User currentUser) {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(currentUser));
    }
}
