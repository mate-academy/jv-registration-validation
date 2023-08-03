package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationService;
import core.basesyntax.RegistrationServiceException;
import core.basesyntax.RegistrationServiceImpl;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private static final int MINIMUM_AGE = 18;
    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "password";
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_LOGIN,DEFAULT_PASSWORD, MINIMUM_AGE);
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ShortPassword_NotOk() {
        user.setPassword("admin");
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginExists_NotOK() {
        Storage.people.add(user);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_WrongAge_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Ok() {
        User actual = registrationService.register(user);
        assertEquals(storageDao.get(user.getLogin()), actual);
    }

    @Test
    void register_ValidUserCase_Ok() {
        User actual = registrationService.register(user);
        assertEquals(DEFAULT_PASSWORD, actual.getPassword());
        assertEquals(MINIMUM_AGE, actual.getAge());
        assertEquals(DEFAULT_LOGIN, actual.getLogin());
    }

    @Test
    void register_LongPassword_Ok() {
        user.setPassword("O$i%#$112032m@)@J2mks#$al$#dmalsd56d5ad23");
        User actual = registrationService.register(user);
        assertEquals("O$i%#$112032m@)@J2mks#$al$#dmalsd56d5ad23", actual.getPassword());
    }

    @Test
    void register_LargeAge_Ok() {
        user.setAge(95);
        User actual = registrationService.register(user);
        assertEquals(95, actual.getAge());
    }

    @Test
    void register_LongLogin_Ok() {
        user.setLogin("asdsmksdamkdskadksadmsakdmsakdmaskdmaskdjgfjgfkgk");
        User actual = registrationService.register(user);
        assertEquals("asdsmksdamkdskadksadmsakdmsakdmaskdmaskdjgfjgfkgk", actual.getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
