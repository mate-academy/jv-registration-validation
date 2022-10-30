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
    static final int DEFAULT_AGE = 21;
    static final String DEFAULT_LOGIN = "first";
    static final String DEFAULT_PASSWORD = "registration";
    static final String EMPTY_STRING = "";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User firstUser;
    private User secondUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setLogin(DEFAULT_LOGIN);
        firstUser.setAge(DEFAULT_AGE);
        firstUser.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_validData_ok() {
        User registeredUser = registrationService.register(firstUser);
        assertEquals(firstUser, registeredUser);
    }

    @Test
    void register_uniqueLogin_notOk() {
        storageDao.add(firstUser);
        secondUser = new User();
        secondUser.setLogin(DEFAULT_LOGIN);
        secondUser.setAge(DEFAULT_AGE);
        secondUser.setPassword(DEFAULT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        firstUser.setLogin(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_18Age_ok() {
        firstUser.setAge(18);
        registrationService.register(firstUser);
        assertEquals(DEFAULT_LOGIN, storageDao.get(DEFAULT_LOGIN).getLogin());
        assertEquals(18, storageDao.get(DEFAULT_LOGIN).getAge());
        assertEquals(DEFAULT_PASSWORD, storageDao.get(DEFAULT_LOGIN).getPassword());
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_lowAge_notOk() {
        firstUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_0Age_notOk() {
        firstUser.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_negativeAge_notOk() {
        firstUser.setAge(-5869);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_poorPassword_notOk() {
        firstUser.setPassword("liluu");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        firstUser.setPassword(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
