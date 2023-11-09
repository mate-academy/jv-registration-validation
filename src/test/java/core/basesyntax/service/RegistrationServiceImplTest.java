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
    private static User registeredUser;
    private static User newUser;
    private static final String REGISTERED_USER_LOGIN = "Andrew";
    private static final String REGISTERED_USER_PASSWORD = "Andrew123";
    private static final int REGISTERED_USER_AGE = 19;
    private static final String NEW_USER_LOGIN = "Bohdan";
    private static final String NEW_USER_PASSWORD = "Bohdan123";
    private static final int NEW_USER_AGE = 23;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        registeredUser = new User();
        registeredUser.setLogin(REGISTERED_USER_LOGIN);
        registeredUser.setPassword(REGISTERED_USER_PASSWORD);
        registeredUser.setAge(REGISTERED_USER_AGE);
        registrationService.register(registeredUser);

        newUser = new User();
        newUser.setLogin(NEW_USER_LOGIN);
        newUser.setPassword(NEW_USER_PASSWORD);
        newUser.setAge(NEW_USER_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        newUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        newUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        newUser.setLogin(REGISTERED_USER_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_notValidLogin_notOk() {
        newUser.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
        newUser.setLogin("Ab/@1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_validLogin_ok() {
        newUser.setLogin("Den$12");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setLogin("Alice1!");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setLogin("$1Bohdan@2");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_passwordIsNull_notOk() {
        newUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_notValidPassword_notOk() {
        newUser.setPassword("b");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
        newUser.setPassword("Bob32");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_validPassword_ok() {
        newUser.setPassword("123456");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setPassword("12//56.(");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setPassword("Bob@$.3456");
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_ageIsNull_notOk() {
        newUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        newUser.setAge(-18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_lowAge_notOk() {
        newUser.setAge(7);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
        newUser.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_validAge_ok() {
        newUser.setAge(18);
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setAge(35);
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
        Storage.people.clear();
        newUser.setAge(101);
        registrationService.register(newUser);
        assertSame(newUser, storageDao.get(newUser.getLogin()));
    }
}
