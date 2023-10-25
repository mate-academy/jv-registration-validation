package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User newUser;

    @BeforeAll
    static void setup() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void crateNewUser() {
        newUser = new User("loginLogin", "password", 18);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_UserWithAllValidParameters_Ok() {
        registrationService.register(newUser);
        User expected = storageDao.get(newUser.getLogin());
        assertEquals(newUser, expected);
    }

    @Test
    public void register_UserIsNull_notOk() {
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_UserIsAlreadyInStorage_notOk() {
        storageDao.add(newUser);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWhereAgeLowerThanMinAge_notOk() {
        newUser.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWhereAgeIsZero_notOk() {
        newUser.setAge(0);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWhereAgeIsNull_notOk() {
        newUser.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWherePasswordShorterThanMinimum_notOk() {
        newUser.setPassword("passw");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWherePasswordIsEmptyString_notOk() {
        newUser.setPassword("");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWherePasswordIsNull_notOk() {
        newUser.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWithLoginShorterThanMinimum_notOk() {
        newUser.setLogin("login");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWithLoginIsEmptyString_notOk() {
        newUser.setLogin("");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_UserWhereLoginIsNull_notOk() {
        newUser.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
