package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User userFirst;
    private static User userSecond;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        userFirst = new User();
        userFirst.setLogin("Andrew");
        userFirst.setPassword("Andrew123");
        userFirst.setAge(19);
        registrationService.register(userFirst);

        userSecond = new User();
        userSecond.setLogin("Bohdan");
        userSecond.setPassword("Bohdan123");
        userSecond.setAge(23);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_loginIsNull_notOk() {
        userSecond.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_loginExists_notOk() {
        userSecond.setLogin("Andrew");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_loginIsLessThanSixChars_notOk() {
        userSecond.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
        userSecond.setLogin("Ab/@1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_loginIsSixCharsAndMore_ok() {
        userSecond.setLogin("Den$12");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setLogin("Alice1!");
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setLogin("$1Bohdan@2");
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void register_passwordIsNull_notOk() {
        userSecond.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_passwordIsLessThanSixChars_notOk() {
        userSecond.setPassword("b");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
        userSecond.setPassword("Bob32");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_passwordIsSixCharsAndMore_ok() {
        userSecond.setPassword("123456");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setPassword("12//56.(");
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setPassword("Bob@$.3456");
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void register_ageIsNull_notOk() {
        userSecond.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_ageIsNegative_notOk() {
        userSecond.setAge(-18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        userSecond.setAge(7);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
        userSecond.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_ageIs18AndMore_ok() {
        userSecond.setAge(18);
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setAge(35);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
        userSecond.setAge(101);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }
}
