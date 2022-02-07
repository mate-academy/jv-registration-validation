package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User correctUser;
    private User wrongUser;

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
        wrongUser = new User();
        wrongUser.setAge(15);
        wrongUser.setLogin("spartak77");
        wrongUser.setPassword("2326");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_Ok() {
        registrationService.register(correctUser);
        assertEquals(storageDao.get(correctUser.getLogin()), correctUser);
    }

    @Test
    void register_loginDuplicate_notOk() {
        Storage.people.add(correctUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    void register_age_notOk() {
        try {
            registrationService.register(wrongUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception must be thrown if age less than 18!");
    }

    @Test
    void register_ageIsNull_notOk() {
        wrongUser.setAge(null);
        try {
            registrationService.register(wrongUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception must be thrown if age is Null!");
    }

    @Test
    void register_password_notOk() {
        try {
            registrationService.register(wrongUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception must be thrown if age less than 18!");
    }

    @Test
    public void register_loginNull_notOk() {
        wrongUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(wrongUser);
        });
    }

    @Test
    public void register_passwordNull_notOk() {
        wrongUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(wrongUser);
        });
    }

    @Test
    void register_passwordEmpty_notOk() {
        correctUser.setPassword("        ");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    void register_loginEmpty_notOk() {
        correctUser.setLogin("       ");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    void register_password_has_spaces_notOk() {
        correctUser.setPassword("12  566   77");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    void register_login_has_spaces_notOk() {
        correctUser.setLogin("12 4555 aw");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
