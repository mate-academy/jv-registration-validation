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
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User newUser = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

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
        User expected = storageDao.get(newUser.getLogin());
        assertEquals(newUser, expected);
    }

    @Test
    void registerUserIsAlreadyInStorage_notOk() {
        registrationService.register(newUser);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
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

}
