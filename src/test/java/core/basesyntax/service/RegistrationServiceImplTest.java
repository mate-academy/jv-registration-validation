package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
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
        Storage.people.add(new User("Arthur","afnfjrnb87",28));
        Storage.people.add(new User("Jane","123dsafq",18));
        Storage.people.add(new User("Natia","addarw41", 30));
        Storage.people.add(new User("Thomas","djfj3621", 20));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userWithUniqueLogic_Ok() {
        user = new User("Frank","82ghnrw3",35);
        try {
            registrationService.register(user);
            assertEquals(user, storageDao.get("Frank"));
        } catch (ValidationException e) {
            fail("User with current login exist in Storage!");

        }
    }

    @Test
    void register_isLoginInStorage_NotOk() {
        user = new User("Jane","82ghnd43",31);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user = new User(null,"82ghnd43", 31);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmptyLine_NotOk() {
        user = new User("","82ghnd43", 31);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsMinRequired_Ok() {
        user = new User("Forty","87lhnd43", 18);
        try {
            registrationService.register(user);
            assertEquals(user, storageDao.get("Forty"));
        } catch (ValidationException e) {
            fail("User age isn't correct!");
        }
    }

    @Test
    void register_userAgeIsGreaterMinRequired_Ok() {
        user = new User("Yasemin","82ghfd43", 91);
        try {
            registrationService.register(user);
            assertEquals(user, storageDao.get("Yasemin"));
        } catch (ValidationException e) {
            fail("User age isn't correct!");
        }
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user = new User("Marta","fortycash", null);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeLessThanMinRequired_NotOk() {
        user = new User("Marta","fortycash1", 17);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsMinCharacters_Ok() {
        user = new User("Kobe", "ghfd43", 20);
        try {
            registrationService.register(user);
            assertEquals(user, storageDao.get("Kobe"));
        } catch (ValidationException e) {
            fail("User password isn't correct!");

        }
    }

    @Test
    void register_userPasswordIsGreaterThanMinCharacters_Ok() {
        user = new User("George","ghfd4382daa", 19);
        try {
            registrationService.register(user);
            assertEquals(user, storageDao.get("George"));
        } catch (ValidationException e) {
            fail("User password isn't correct!");
        }
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user = new User("Monika", null, 30);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsEmptyLine_NotOk() {
        user = new User("Stephen", "", 34);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsLessThanMinCharacters_NotOK() {
        user = new User("Drew", "18ui", 31);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }
}
