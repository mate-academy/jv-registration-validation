package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.throwable.InvalidDataEntry;
import org.junit.jupiter.api.AfterEach;
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
        user = new User((long)12345, "loginValid", "passwordValid", 18);
    }

    @AfterEach
    void tearDown() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void addUser_UserIsNull_NotOk() {
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(null));
    }

    @Test
    void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserIsValid_Ok() {
        User actual = registrationService.register(user);
        assertEquals(1, Storage.people.size());
        assertEquals(user, actual);
    }

    @Test
    void registerExistedUser_NotOk() {
        storageDao.add(user);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsValid_Ok() {
        user.setLogin("loginValid");
        assertEquals(user, registrationService.register(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_LoginIs6_Ok() {
        user.setLogin("123456");
        assertEquals(user, registrationService.register(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_LoginIs0_NotOk() {
        user.setLogin("");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLessThenMin_NotOk() {
        user.setLogin("12345");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsValid_Ok() {
        user.setPassword("passwordValid");
        registrationService.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_PasswordIs6_Ok() {
        user.setPassword("123456");
        registrationService.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_PasswordIs0_NotOk() {
        user.setPassword("");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLessThenMin_NotOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsValid_Ok() {
        user.setAge(25);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_AgeIs18_Ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_AgeZero_NotOk() {
        user.setAge(0);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeLessThanMin_NotOk() {
        user.setAge(16);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }


    @Test
    void register_AgeNegative_NotOk() {
        user.setAge(-1);
        assertThrows(InvalidDataEntry.class, () -> registrationService.register(user));
    }
}
