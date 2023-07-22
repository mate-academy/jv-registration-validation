package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void registrationOfUserWithInvalidLogin_NotOk() {
        user.setLogin("byaka");
        user.setPassword("123456");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationOfUserWithInvalidPassword_NotOk() {
        user.setLogin("Kalyabaka");
        user.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationOfUserWithIvalidAge_NotOk() {
        user.setLogin("Zaluzhniy112");
        user.setPassword("1234567");
        user.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationOfValidUser_Ok() {
        user.setLogin("DenisShvagro123");
        user.setPassword("qazwsxedc123");
        user.setAge(25);
        registrationService.register(user);
        User actual = storageDao.get("DenisShvagro123");
        User expected = user;
        assertEquals(expected, actual, "Incorrect user returned from Storage");
    }

    @Test
    void registrationOfMultipleUsers_Ok() {
        user.setLogin("KumMichael");
        user.setPassword("xedc123");
        user.setAge(25);
        registrationService.register(user);
        User actual = storageDao.get("KumMichael");
        assertEquals(user, actual, "Incorrect user returned from Storage");
        User secondUser = new User();
        secondUser.setLogin("Abrakadabra");
        secondUser.setPassword("qwer123");
        secondUser.setAge(30);
        registrationService.register(secondUser);
        User actualSecond = storageDao.get("Abrakadabra");
        assertEquals(secondUser, actualSecond);
    }

    @Test
    void registrationOfUserWithNotUniqueLogin_NotOk() {
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
