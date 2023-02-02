package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
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
    private static final String PASSWORD_OK = "123456";
    private static final String PASSWORD_NOT_OK = "12345";
    private static final String LOGIN = "login";
    private static final int AGE_OK = 18;
    private static final int AGE_NOT_OK = 11;
    private static final int NEGATIVE_AGE = - 1;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    public void setUp() {
        user.setLogin(LOGIN);
        user.setAge(AGE_OK);
        user.setPassword(PASSWORD_OK);
    }

    @Test
    public void register_tooSmallPassword_NotOk() {
        user.setPassword(PASSWORD_NOT_OK);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));

    }

    @Test
    public void register_ageTooSmall_notOK() {
        user.setAge(AGE_NOT_OK);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginIsNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_rightUser_Ok() {
        User actual = registrationService.register(user);
        assertSame(actual, user);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_addSameUser_notOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
