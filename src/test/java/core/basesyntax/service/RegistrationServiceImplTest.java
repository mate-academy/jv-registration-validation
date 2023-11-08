package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationServiceException;
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
    void registration_loginIsNull_notOk() {
        userSecond.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_loginExists_notOk() {
        userSecond.setLogin("Andrew");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_loginZeroChars_notOk() {
        userSecond.setLogin("");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_loginFiveChars_notOk() {
        userSecond.setLogin("Ab/@1");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_loginSixChars_ok() {
        userSecond.setLogin("Den$12");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_loginSevenChars_ok() {
        userSecond.setLogin("Alice1!");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_loginTenChars_ok() {
        userSecond.setLogin("$1Bohdan@2");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_passwordIsNull_notOk() {
        userSecond.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_passwordOneChar_notOk() {
        userSecond.setPassword("b");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_passwordFiveChars_notOk() {
        userSecond.setPassword("Bob32");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_passwordSixChars_ok() {
        userSecond.setPassword("123456");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_passwordEightChars_ok() {
        userSecond.setPassword("12//56.(");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_passwordTenChars_ok() {
        userSecond.setPassword("Bob@$.3456");
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_ageIsNull_notOk() {
        userSecond.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_ageIsNegative_notOk() {
        userSecond.setAge(-18);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_ageIs7_notOk() {
        userSecond.setAge(7);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_ageIs17_notOk() {
        userSecond.setAge(17);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void registration_ageIs18_ok() {
        userSecond.setAge(18);
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_ageIs35_ok() {
        userSecond.setAge(35);
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }

    @Test
    void registration_ageIs101_ok() {
        userSecond.setAge(101);
        registrationService.register(userSecond);
        assertSame(userSecond, storageDao.get(userSecond.getLogin()));
    }
}
