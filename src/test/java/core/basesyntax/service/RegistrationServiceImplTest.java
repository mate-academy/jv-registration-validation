package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.ValidationExeption;
import core.basesyntax.model.User;
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

    @Test
    void register_userWithUniqueLogic_Ok() throws ValidationExeption {
        user = new User("Frank","82ghnrw3",35);
        registrationService.register(new User("Frank","82ghnrw3",35));
        assertEquals(user, storageDao.get("Frank"));
    }

    @Test
    void register_isLoginInStorage_NotOk() {
        user = new User("Jane","82ghnd43",31);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user = new User(null,"82ghnd43", 31);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmptyLine_NotOk() {
        user = new User("","82ghnd43", 31);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsEighteen_Ok() throws ValidationExeption {
        user = new User("Forty","87lhnd43", 18);
        registrationService.register(user);
        assertEquals(user, storageDao.get("Forty"));
    }

    @Test
    void register_userAgeIsGreaterThanEighteen_Ok() throws ValidationExeption {
        user = new User("Yesmin","82ghfd43", 91);
        registrationService.register(user);
        assertEquals(user, storageDao.get("Yesmin"));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user = new User("Marta","fortycash", null);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeLessThanEighteen_NotOk() {
        user = new User("Marta","fortycash1", 17);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsSixCharacters_Ok() throws ValidationExeption {
        user = new User("Kobe","ghfd43", 20);
        registrationService.register(user);
        assertEquals(user, storageDao.get("Kobe"));
    }

    @Test
    void register_userPasswordIsGreaterThanSixCharacters_Ok() throws ValidationExeption {
        user = new User("George","ghfd4382daa", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get("George"));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user = new User("Monika", null, 30);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsEmptyLine_NotOk() {
        user = new User("Stephen", "", 34);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsLessThanSixCharacters_NotOK() {
        user = new User("Drew", "18ui", 31);
        assertThrows(ValidationExeption.class, () -> registrationService.register(user));
    }
}
