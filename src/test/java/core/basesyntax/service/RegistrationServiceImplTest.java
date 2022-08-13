package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService regServ;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        regServ = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("someLogin");
        user.setPassword("SomePassword_1234!");
        user.setAge(21);
    }

    @Test
    void register_emptyUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_existingUser_NotOk() {
        storageDao.get(user.getLogin());
        storageDao.add(user);
        User user2 = new User();
        user2.setLogin(user.getLogin());
        user2.setPassword("UHBU27h!_");
        user2.setAge(19);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user2));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-6);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_AgeInAcceptableRange_Ok() {
        user.setAge(18);
        assertEquals(user, regServ.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("abcd!");
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> regServ.register(user));
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, regServ.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
