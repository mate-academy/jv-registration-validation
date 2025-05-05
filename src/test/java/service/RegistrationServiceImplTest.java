package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User standartUser;

    @BeforeEach
    void beforeEach() {
        standartUser = new User("PAMPERS", "12345678", 20);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_User_isOK() {
        registrationService.register(standartUser);
        User actual = storageDao.get(standartUser.getLogin());
        assertEquals(standartUser, actual);
    }

    @Test
    void register_SameUser_NotOk() {
        registrationService.register(standartUser);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_NullUser_NotOk() {
        User user = null;
        assertThrows(UserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        standartUser.setPassword(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectPassword_NotOk() {
        standartUser.setPassword("1");
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_NullLogin_NotOk() {
        standartUser.setLogin(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectLogin_NotOk() {
        standartUser.setLogin("A");
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        standartUser.setAge(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectAge_NotOk() {
        standartUser.setAge(12);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }
}
