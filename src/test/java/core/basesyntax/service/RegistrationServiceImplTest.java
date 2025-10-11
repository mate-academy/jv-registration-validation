package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.throwable.InvalidDataEntry;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    RegistrationServiceImpl registrationService;
    StorageDaoImpl storageDao;
    User user;
    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User((long)12345, "loginValid", "passwordValid", 18);
    }

    @AfterEach
    void tearDown() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void addUser_OK() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void addUser_UserIsNull_NotOk() {
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(null));
    }

    @Test
    void registerExistedUser_NotOk() {
        storageDao.add(user);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void login_SizeLessThan6_NotOk() {
        user.setLogin("12345");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void password_SizeLessThan6_NotOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void age_LessThen18_NotOk() {
        user.setAge(16);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }
}