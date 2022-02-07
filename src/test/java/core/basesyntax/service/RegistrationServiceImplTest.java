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
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User correctUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = new User();
        correctUser.setAge(23);
        correctUser.setLogin("david23");
        correctUser.setPassword("1d2f3h");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_validData_Ok() {
        registrationService.register(correctUser);
        assertEquals(storageDao.get(correctUser.getLogin()), correctUser);
    }

    @Test
    void register_loginDuplicate_notOk() {
        storageDao.add(correctUser);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_age_notOk() {
        correctUser.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        correctUser.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_shortPassword_notOk() {
        correctUser.setPassword("1212");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    public void register_loginNull_notOk() {
        correctUser.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    public void register_passwordNull_notOk() {
        correctUser.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        correctUser.setPassword("        ");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        correctUser.setLogin("       ");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_passwordHasSpaces_notOk() {
        correctUser.setPassword("12  566   77");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_loginHasSpaces_notOk() {
        correctUser.setLogin("12 4555 aw");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser));
    }
}
