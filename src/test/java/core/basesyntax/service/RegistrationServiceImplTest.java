package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationFailException;
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
        user = new User();
        user.setLogin("ponomvrenko");
        user.setAge(21);
        user.setPassword("qwerty1234");
    }

    @Test
    void register_normalValidation_Ok() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_loginAlreadyExistsInDB_notOk() {
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("ponomvrenko");
        Storage.people.add(userWithSameLogin);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_belowZeroAge_notOk() {
        user.setAge(-100);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_normalAge_ok() {
        registrationService.register(user);
        assertEquals(user.getAge(), storageDao.get(user.getLogin()).getAge());
    }

    @Test
    void register_minAge_ok() {
        user.setAge(18);
        registrationService.register(user);
        assertEquals(user.getAge(), storageDao.get(user.getLogin()).getAge());
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthThree_notOk() {
        user.setPassword("123");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthFive_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthSix_Ok() {
        user.setPassword("123456");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthEight_Ok() {
        user.setPassword("12345678");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthThree_notOk() {
        user.setLogin("qwe");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthFive_notOk() {
        user.setLogin("qwert");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthSix_Ok() {
        user.setLogin("qwerty");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthEight_Ok() {
        user.setLogin("qwerty88");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_returnCorrectUserAfterSuccessfulRegistration_Ok() {
        User registered = registrationService.register(user);
        assertEquals(user, registered);
        Storage.people.remove(registered);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
