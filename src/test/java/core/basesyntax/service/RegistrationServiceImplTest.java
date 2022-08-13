package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService regServ;
    private User user;

    @BeforeAll
    static void beforeAll() {
        regServ = new RegistrationServiceImpl();
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
        try {
            regServ.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if user is empty");
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if login is null");
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if login is empty");
    }

    @Test
    void register_existingUser_NotOk() {
        StorageDao object = new StorageDaoImpl();
        object.get(user.getLogin());
        object.add(user);
        User user2 = new User();
        user2.setLogin(user.getLogin());
        user2.setPassword("UHBU27h!_");
        user2.setAge(19);
        try {
            regServ.register(user2);
        } catch (RuntimeException e) {
            Storage.people.clear();
            return;
        }
        fail("RuntimeException should be thrown if login already exists");
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(17);
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if age under 18");
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if age is null");
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-6);
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if age is null");
    }

    @Test
    void register_AgeEquals18_Ok() {
        user.setAge(18);
        assertEquals(user, regServ.register(user));
        Storage.people.clear();
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("abcd!");
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if password shorter than 6 characters");
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        try {
            regServ.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if password is null");
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, regServ.register(user));
    }
}
