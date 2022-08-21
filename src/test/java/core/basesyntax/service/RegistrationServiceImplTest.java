package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void registerUserValid() {
        user.setLogin("example");
        user.setPassword("123456");
        user.setAge(18);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertNotNull(actual);
    }

    @Test
    void registerUserNullLogin() {
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserInvalidPassword() {
        user.setLogin("example");
        user.setPassword("12345");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullPassword() {
        user.setLogin("example");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserInvalidAge() {
        user.setLogin("example");
        user.setPassword("123456");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullAge() {
        user.setLogin("example");
        user.setPassword("123456");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithSameLogins() {
        user.setLogin("example");
        user.setPassword("123456");
        user.setAge(20);
        registrationService.register(user);
        User user = new User();
        user.setLogin("example");
        user.setPassword("2368994");
        user.setAge(21);
        registrationService.register(user);
        int actual = Storage.people.size();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void registrationUserNull() {
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

}
