package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("tazbog123");
        user.setPassword("1234512345");
        user.setAge(25);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_Ok() {
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginDuplicate_notOk() {
        storageDao.add(user);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNegative_notOk() {
        user.setAge(-59);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userMinAge_Ok() {
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidAge_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLengthFour_notOk() {
        user.setLogin("1234");
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userEmptyLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLengthSix_Ok() {
        user.setLogin("123456");
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidLogin_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userEmptyPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLengthFour_notOk() {
        user.setPassword("1234");
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLengthSix_Ok() {
        user.setPassword("123456");
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidPassword_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
