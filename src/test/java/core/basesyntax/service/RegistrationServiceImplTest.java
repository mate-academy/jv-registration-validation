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
    RegistrationService registrationService = new RegistrationServiceImpl();
    private User newUser = new User();
    StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    public void crateNewUser() {
       newUser = new User("loginLogin", "password", 18);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void registerAllValid_Ok() {
        registrationService.register(newUser);
        User actual = newUser;
        User expected = Storage.people.get(0);
        assertEquals(actual, expected);
    }

    @Test
    void registerAgeLowerThanMinAge_notOk() {
        newUser.setAge(12);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerAgeNull_notOk() {
        newUser.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerPasswordShorterThanSix_notOk() {
        newUser.setPassword("passw");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerPasswordNull_notOk() {
        newUser.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerLoginShorterThanSix_notOk() {
        newUser.setLogin("login");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerLoginNull_notOk() {
        newUser.setLogin("login");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registerMultipleValidUsersAndGetId_Ok() {
        User lastUser = new User("Login4", "Password4", 18);
        registrationService.register(new User("Login1", "Password1", 18));
        registrationService.register(new User("Login2", "Password2", 18));
        registrationService.register(new User("Login3", "Password3", 18));
        registrationService.register(lastUser);
        long actual = storageDao.get("Login4").getId();
        assertEquals(actual, 4);
    }
}