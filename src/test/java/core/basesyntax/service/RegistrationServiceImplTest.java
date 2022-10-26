package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private static final String DEFAULT_LOGIN = "YuliaTsariv";
    private static final String DEFAULT_PASSWORD = "Yulia123";
    private static final int DEFAULT_AGE = 20;
    private static final int MIN_USER_AGE = 18;
    private static final int UNDER_MIN_AGE = 12;

    private User user;

    @BeforeAll
    static void createService() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_withMinAge_ok() {
        user.setAge(MIN_USER_AGE);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_withMinPassword_length_ok() {
        user.setPassword("mypass");
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void valid_allData_ok() {
        User validUser = registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
        assertEquals(user,validUser);
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("1234");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underMinAge_notOk() {
        user.setAge(UNDER_MIN_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_alreadyExist_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullPass_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void deleteUsersInStorage() {
        Storage.people.clear();
    }
}
