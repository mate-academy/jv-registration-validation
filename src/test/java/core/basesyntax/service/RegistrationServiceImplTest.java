package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_user_Ok() {
        User firstExpected = new User();
        firstExpected.setLogin("Bastard");
        firstExpected.setPassword("123456");
        firstExpected.setAge(20);
        User secondExpected = new User();
        secondExpected.setLogin("Bitch");
        secondExpected.setPassword("123456");
        secondExpected.setAge(24);
        registrationService.register(firstExpected);
        registrationService.register(secondExpected);
        assertEquals(2, Storage.people.size());
        assertEquals(firstExpected, storageDao.get(firstExpected.getLogin()));
        assertEquals(secondExpected, storageDao.get(secondExpected.getLogin()));

    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_NotOk() {
        User user = new User();
        user.setLogin("Moron");
        user.setPassword("1234567");
        user.setAge(19);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_underAge_NotOk() {
        User user = new User();
        user.setLogin("Moron");
        user.setPassword("1234567");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_NotOk() {
        User user = new User();
        user.setLogin("Dark_Vlastelin");
        user.setPassword("123456");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User();
        user.setLogin("Gnom");
        user.setPassword("543231");
        user.setAge(-3);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("Black");
        user.setPassword(null);
        user.setAge(21);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ShortPassword_NotOk() {
        User user = new User();
        user.setLogin("Dumb");
        user.setPassword("12345");
        user.setAge(25);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
