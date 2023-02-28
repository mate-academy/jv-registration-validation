package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final RegistrationService regService = new RegistrationServiceImpl();
    private User defaultUser;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        defaultUser = new User();
        defaultUser.setId(100L);
        defaultUser.setAge(20);
        defaultUser.setLogin("newUser");
        defaultUser.setPassword("123456");
    }

    @Test
    void register_AgeUnderMin_NotOk() {
        defaultUser.setAge(MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NegativeAge_NotOk() {
        defaultUser.setAge(-1);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ZeroAge_NotOk() {
        defaultUser.setAge(0);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
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
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ZeroLengthLogin_NotOk() {
        defaultUser.setLogin("");
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_ShortPassword_NotOk() {
        defaultUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }

    @Test
    void register_UserAlreadyAdded_NotOk() {
        storageDao.add(defaultUser);
        assertThrows(RegistrationException.class, () -> regService.register(defaultUser));
    }
}
