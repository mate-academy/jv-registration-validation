package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final RegistrationService regService = new RegistrationServiceImpl();
    private User defaultUser;

    @BeforeEach
    void createDefaultUser() {
        defaultUser = new User();
        defaultUser.setId(100L);
        defaultUser.setAge(20);
        defaultUser.setLogin("newUser");
        defaultUser.setPassword("123456");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_AgeUnderMin_NotOk() {
        defaultUser.setAge(MIN_AGE - 1);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NegativeAge_NotOk() {
        defaultUser.setAge(-1);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ZeroAge_NotOk() {
        defaultUser.setAge(0);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_AgeMin_Ok() {
        defaultUser.setAge(MIN_AGE);
        assertEquals(defaultUser, regService.register(defaultUser));
        assertEquals(defaultUser, storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void register_AgeOverMin_Ok() {
        defaultUser.setAge(MIN_AGE + 1);
        assertEquals(defaultUser, regService.register(defaultUser));
        assertEquals(defaultUser, storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void register_NullId_NotOk() {
        defaultUser.setId(null);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ZeroLengthLogin_NotOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ShortPassword_NotOk() {
        defaultUser.setPassword("12345");
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_UserAlreadyAdded_NotOk() {
        storageDao.add(defaultUser);
        assertThrows(InvalidUserDataException.class, () -> regService.register(defaultUser));
    }
}
