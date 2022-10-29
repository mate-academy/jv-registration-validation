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
    static final int EIGHTEEN_AGE = 18;
    static final int SEVENTEEN_AGE = 17;
    static final int ZERO_AGE = 0;
    static final int NEGATIVE_AGE = -5869;
    static final String DEFAULT_LOGIN = "first";
    static final String DEFAULT_PASSWORD = "registration";
    static final String EMPTY_STRING = "";
    static final String POOR_PASSWORD = "liluu";
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
    void register_uniqueLogin_ok() {
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
    void register_validAge_ok() {
        registrationService.register(firstUser);
        assertEquals(DEFAULT_LOGIN, storageDao.get(DEFAULT_LOGIN).getLogin());
        assertEquals(DEFAULT_AGE, storageDao.get(DEFAULT_LOGIN).getAge());
        assertEquals(DEFAULT_PASSWORD, storageDao.get(DEFAULT_LOGIN).getPassword());
    }

    @Test
    void register_18Age_ok() {
        firstUser.setAge(EIGHTEEN_AGE);
        registrationService.register(firstUser);
        assertEquals(DEFAULT_LOGIN, storageDao.get(DEFAULT_LOGIN).getLogin());
        assertEquals(EIGHTEEN_AGE, storageDao.get(DEFAULT_LOGIN).getAge());
        assertEquals(DEFAULT_PASSWORD, storageDao.get(DEFAULT_LOGIN).getPassword());
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_lowAge_notOk() {
        firstUser.setAge(SEVENTEEN_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_0Age_notOk() {
        firstUser.setAge(ZERO_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_negativeAge_notOk() {
        firstUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_validPassword_ok() {
        registrationService.register(firstUser);
        assertEquals(DEFAULT_LOGIN, storageDao.get(DEFAULT_LOGIN).getLogin());
        assertEquals(DEFAULT_AGE, storageDao.get(DEFAULT_LOGIN).getAge());
        assertEquals(DEFAULT_PASSWORD, storageDao.get(DEFAULT_LOGIN).getPassword());
    }

    @Test
    void register_poorPassword_notOk() {
        firstUser.setPassword(POOR_PASSWORD);
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
