package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private User userForTest;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void tearDown() {
        Storage.getPeople().clear();
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        userForTest = new User();
        userForTest.setPassword("ValidPassword");
        userForTest.setLogin("ValidLogin");
        userForTest.setAge(14);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_loginShortenThanSixSymbols_notOk() {
        userForTest = new User();
        userForTest.setAge(20);
        userForTest.setPassword("ValidPassword");
        userForTest.setLogin("short");
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_passwordShortenThanSixSymbols_NotOk() {
        userForTest = new User();
        userForTest.setAge(20);
        userForTest.setLogin("validLogin");
        userForTest.setPassword("short");
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_null_notOk() {
        assertThrows(NotValidData.class, () -> registrationService.register(null));
    }

    @Test
    void register_duplicateUserForRegister_NotOk() {
        User userWillFirstRegister = new User("ValidLogin", "ValidPassword", 20);
        userForTest = new User("ValidLogin", "ValidPassword", 20);
        storageDao.add(userWillFirstRegister);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_Valid_Ok() {
        userForTest = new User("ValidLogin", "ValidPassword", 20);
        User registeredUser = registrationService.register(userForTest);
        assertNotNull(registeredUser);
        assertEquals(userForTest, storageDao.get(userForTest.getLogin()));

    }

    @Test
    void register_registerAgeNull_notOk() {
        userForTest = new User("ValidLogin", "ValidPassword", null);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_registerLoginNull_notOk() {
        userForTest = new User(null, "ValidPassword", 20);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_spaceInsteadOfSymbols_NotOk() {
        userForTest = new User("       ", "       ", 20);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_registerPasswordNull_notOk() {
        userForTest = new User("ValidLogin", null, 20);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }

    @Test
    void register_passwordLengthItsSix_Ok() {
        userForTest = new User("ValidLogin", "itssix", 20);
        User registeredUser = registrationService.register(userForTest);
        assertNotNull(registeredUser);
        assertEquals(userForTest, storageDao.get(userForTest.getLogin()));
    }

    @Test
    void register_loginLengthItsSix_Ok() {
        userForTest = new User("itsSix", "ValidPassword", 20);
        User registeredUser = registrationService.register(userForTest);
        assertNotNull(registeredUser);
        assertEquals(userForTest, storageDao.get(userForTest.getLogin()));
    }

    @Test
    void register_ageItsEighteen_Ok() {
        userForTest = new User("ValidLogin", "itsSix", 18);
        User registeredUser = registrationService.register(userForTest);
        assertNotNull(registeredUser);
        assertEquals(userForTest, storageDao.get(userForTest.getLogin()));
    }

    @Test
    void register_badPasswordExperience_NotOk() {
        userForTest = new User("ValidLogin", "123456789", 20);
        assertThrows(NotValidData.class, () -> registrationService.register(userForTest));
    }
}
