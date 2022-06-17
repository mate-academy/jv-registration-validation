package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private static User john;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        john = new User();
    }

    @BeforeEach
    void setUp() {
        john.setAge(19);
        john.setLogin("John12.03.1993");
        john.setPassword("12345678");
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(john);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
        User actuallyGot = storageDao.get(john.getLogin());
        assertEquals(john, actuallyGot);
    }

    @Test
    void register_userWithLessAge_NotOk() {
        john.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void register_UserWithSameLogin_NotOk() {
        storageDao.add(john);
        User vanya = new User();
        vanya.setAge(24);
        vanya.setLogin("John12.03.1993");
        vanya.setPassword("dfsndlfwe19383");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(vanya);
        });
    }

    @Test
    void register_lessThanSixSymbolsPasswordUser_NotOk() {
        john.setPassword("123");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void register_nullLoginUser_NotOk() {
        john.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        john.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void register_nullAgeUser_NotOk() {
        john.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        john = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
        john = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
