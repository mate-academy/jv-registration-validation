package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword");
        user.setAge(33);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void newUser_alreadyExist_notOk() {
        storageDao.add(user);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_Ok() {
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_notOk() {
        user.setAge(0);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLessThanSixCharacters_notOk() {
        user.setPassword("usrpw");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeLessThan18_notOk() {
        user.setAge(12);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userNegativeAge_notOk() {
        user.setAge(-12);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
