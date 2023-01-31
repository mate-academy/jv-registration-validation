package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String PASSWORD_OK = "123456";
    private static final String PASSWORD_NOT_OK = "12345";
    private static final String LOGIN = "login";
    private static final int AGE_OK = 18;
    private static final int AGE_NOT_OK = 11;
    private static final int NEGATIVE_AGE = - 1;
    private User user;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN);
        user.setAge(AGE_OK);
        user.setPassword(PASSWORD_OK);
    }

    @Test
    void register_tooSmallPassword_NotOk() {
        user.setPassword(PASSWORD_NOT_OK);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));

    }

    @Test
    void register_ageTooSmall_NotOK() {
        user.setAge(AGE_NOT_OK);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_negativeAge_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_rightUser_Ok() {
        User actual = registrationService.register(user);
        assertSame(actual, user);
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_addSameUser_NotOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }


}