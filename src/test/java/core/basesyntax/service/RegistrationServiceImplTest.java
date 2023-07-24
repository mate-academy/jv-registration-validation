package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
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
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_UserWithNullLogin_MotOk() {
        user.setPassword("password");
        user.setAge(42);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithFiveLengthLogin_NotOk() {
        user.setLogin("Seven");
        user.setPassword("fivefsda");
        user.setAge(20);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithSixLengthLogin_Ok() {
        user.setLogin("Eleven");
        user.setPassword("RandomPassword");
        user.setAge(23);
        registrationService.register(user);
        User actual = storageDao.get("Eleven");
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_UserWithNullPassword_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithLoginUnderMinLength_NotOk() {
        user.setLogin("byaka");
        user.setPassword("123456");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithPasswordUnderMinLength_NotOk() {
        user.setLogin("Kalyabaka");
        user.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithAgeUnderMinAge_NotOk() {
        user.setLogin("Zaluzhniy112");
        user.setPassword("1234567");
        user.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AddValidUserData_Ok() {
        user.setLogin("DenisShvagro123");
        user.setPassword("qazwsxedc123");
        user.setAge(25);
        registrationService.register(user);
        User actual = storageDao.get("DenisShvagro123");
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_MultipleUsers_Ok() {
        user.setLogin("KumMichael");
        user.setPassword("xedc123");
        user.setAge(25);
        registrationService.register(user);
        User actual = storageDao.get("KumMichael");
        assertEquals(user, actual);
        User secondUser = new User();
        secondUser.setLogin("Abrakadabra");
        secondUser.setPassword("qwer123");
        secondUser.setAge(30);
        registrationService.register(secondUser);
        User actualSecond = storageDao.get("Abrakadabra");
        assertEquals(secondUser, actualSecond);
    }

    @Test
    void register_UserWithNotUniqueLogin_NotOk() {
        user.setLogin("AbyssWalker");
        user.setPassword("234235246");
        user.setAge(19);
        registrationService.register(user);
        User secondUser = new User();
        secondUser.setLogin("AbyssWalker");
        secondUser.setPassword("asuslenovo");
        secondUser.setAge(20);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(secondUser);
        });
    }
}
