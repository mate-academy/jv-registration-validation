package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService;
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        User bob = new User();
        bob.setAge(16);
        bob.setLogin("bob2009");
        bob.setPassword("bob2009");
        User alice = new User();
        alice.setAge(19);
        alice.setLogin("alice");
        alice.setPassword("alice2006");
        User john = new User();
        john.setAge(22);
        john.setLogin("john2002");
        john.setPassword("john2002");
        Storage.people.add(bob);
        Storage.people.add(alice);
        Storage.people.add(john);
    }

    @Test
    void userIsNull_NotOK() {
        User newUser = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void loginIsNull_NotOK() {
        User newUser = new User();
        newUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void passwordIsNull_NotOK() {
        User newUser = new User();
        newUser.setLogin("bob200956");
        newUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void ageIsNull_NotOK() {
        User newUser = new User();
        newUser.setLogin("bob200956");
        newUser.setPassword("ger3442453");
        newUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void loginAlreadyExist_NotOK() {
        User newUser = new User();
        newUser.setLogin("bob2009");
        newUser.setPassword("bob20091");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void passwordIsSmaller_NotOK() {
        User newUser = new User();
        newUser.setLogin("bob200955");
        newUser.setPassword("123");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void loginIsSmaller_NotOK() {
        User newUser = new User();
        newUser.setLogin("bob2");
        newUser.setPassword("123563");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void registrationService_OK() {
        User newUser = new User();
        newUser.setAge(26);
        newUser.setLogin("bob200022");
        newUser.setPassword("bob200023");
        registrationService.register(newUser);
        assertEquals(storageDao.get("bob200022"), newUser);
    }
}
