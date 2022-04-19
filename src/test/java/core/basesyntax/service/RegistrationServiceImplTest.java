package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(25);
        user.setPassword("77778888");
        user.setLogin("bob25@gmail.com");
    }

    @Test
    void userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginHaveWhiteSpace_NotOk() {
        user.setLogin("bla bla@gmail.com");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLogin_IsOk() {
        user.setLogin("bober2020@gmail.com");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userLoginIsExist_NotOk() {
        user.setLogin("bob25@gmail.com");
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAge_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNegative_NotOk() {
        user.setAge(-20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAge_IsOk() {
        user.setAge(50);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userPasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordLength_NotOk() {
        user.setPassword("2223");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordLength_IsOk() {
        user.setPassword("qqwweerr");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
