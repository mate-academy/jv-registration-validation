package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private final String validLogin = "retkinf";
    private final String validPassword = "qwerty";
    private final int validAge = 18;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void clearList() {
        Storage.people.clear();
    }

    @Test
    void checkEmptyList_Ok() {
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword(validPassword);
        user.setAge(validAge);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin(validLogin);
        user.setAge(validAge);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin(validLogin);
        user.setPassword("abcdf");
        user.setAge(validAge);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("abc");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("Dmitr");
        user.setPassword(validPassword);
        user.setAge(validAge);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("Dmi");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_youngAge_notOk() {
        User user = new User();
        user.setLogin(validLogin);
        user.setPassword(validPassword);
        user.setAge(8);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(-8);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin(validLogin);
        user1.setPassword(validPassword);
        user1.setAge(validAge);
        Storage.people.add(user1);
        User user2 = new User();
        user2.setLogin(validLogin);
        user2.setPassword(validPassword);
        user2.setAge(validAge);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_user_Ok() throws RegistationException {
        User user = new User();
        user.setLogin(validLogin);
        user.setPassword(validPassword);
        user.setAge(validAge);
        registrationService.register(user);
        User user1 = new User();
        user1.setLogin("Artem234");
        user1.setPassword("qwerty12fgh");
        user1.setAge(32);
        registrationService.register(user1);
        assertEquals(user, storageDao.get(validLogin));
        assertEquals(user1, storageDao.get("Artem234"));
    }
}
