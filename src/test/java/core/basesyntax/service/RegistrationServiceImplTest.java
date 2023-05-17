package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Nikita";
    private static final int DEFAULT_AGE = 25;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final String DEFAULT_PASS = "123456";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASS);
    }

    @Test
    void register_ok() {
        User registeredUser = registrationService.register(user);
        assertSame(storageDao.get(DEFAULT_LOGIN), registeredUser);
        assertNotNull(registeredUser.getId());
    }

    @Test
    void register_minAge_ok() {
        user.setAge(MIN_AGE);
        User registeredUser = registrationService.register(user);
        assertSame(storageDao.get(DEFAULT_LOGIN), registeredUser);
    }

    @Test
    void register_maxAge_ok() {
        user.setAge(MAX_AGE);
        User registeredUser = registrationService.register(user);
        assertSame(storageDao.get(DEFAULT_LOGIN), registeredUser);
    }

    @Test
    void register_existedUser_notOk() {
        storageDao.add(user);
        User nameExistsUser = new User();
        nameExistsUser.setLogin(DEFAULT_LOGIN);
        nameExistsUser.setAge(24);
        nameExistsUser.setPassword("newPass");
        assertThrows((InvalidDataException.class), ()
                -> registrationService.register(nameExistsUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_ageLessMinAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_ageMoreMaxAge_notOk() {
        user.setAge(120);
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, ()
                -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
