package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String PASSWORD_WITH_LESS_THAN_SIX_CHARACTERS = "abc";
    private static final String LOGIN_FOR_USER_IN_STORAGE = "uniqueLogin";
    private static final int AGE_UNDER_EIGHTEEN = 15;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("1234567");
        user.setAge(22);
    }

    @Test
    void register_nullUser_notOk() {
        user = new User();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_uniqueLogin_ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getLogin(), user.getLogin());
    }

    @Test
    void register_notUniqueLogin_notOk() {
        User userInStorage = new User();
        userInStorage.setLogin(LOGIN_FOR_USER_IN_STORAGE);
        storageDao.add(userInStorage);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctPassword_ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getPassword(), user.getPassword());
    }

    @Test
    void register_passwordLessThanSixCharacters_notOk() {
        user.setPassword(PASSWORD_WITH_LESS_THAN_SIX_CHARACTERS);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctAge_ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getAge(), user.getAge());
    }

    @Test
    void register_ageUnderEighteen_notOk() {
        user.setAge(AGE_UNDER_EIGHTEEN);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
