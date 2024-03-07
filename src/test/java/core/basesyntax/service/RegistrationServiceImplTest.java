package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User bob;

    @BeforeEach
    void setUp() {
        bob = new User("bob423", "123456", 19);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_registerUsersByCorrectData_ok() {
        registrationService.register(bob);
        User bob2 = new User("bob_terminator", "123456", 19);
        registrationService.register(bob2);
        User bob3 = new User("bob_jenkins", "123456", 19);
        registrationService.register(bob3);
        int actual = Storage.people.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void register_addLoginWith6CharsLength_ok() {
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_addLoginWith8CharsLength_ok() {
        bob.setLogin("bob45678");
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_addPasswordWith6CharsLength_ok() {
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_addPasswordWith8CharsLength_ok() {
        bob.setPassword("12345678");
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_addMinAllowedAge_ok() {
        bob.setAge(18);
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_add30YearsOldUser_ok() {
        bob.setAge(30);
        User actual = registrationService.register(bob);
        User expected = storageDao.get(bob.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_addNullUser_notOk() {
        bob = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByNullLogin_notOk() {
        bob.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addEmptyLogin_notOk() {
        bob.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addLoginWith3CharsLength_notOk() {
        bob.setLogin("123");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addLoginWith5CharsLength_notOk() {
        bob.setLogin("12345");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByAlreadyCreatedLogin_notOk() {
        storageDao.add(bob);
        bob.setLogin("bob423");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByNullPassword_notOk() {
        bob.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addEmptyPassword_notOk() {
        bob.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addPasswordWith3CharsLength_notOk() {
        bob.setPassword("123");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addPasswordWith5CharsLength_notOk() {
        bob.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByNullAge_notOk() {
        bob.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addNegativeAge_notOk() {
        bob.setAge(-5);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addZeroAge_notOk() {
        bob.setAge(0);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addNotAllowedAge_notOk() {
        bob.setAge(13);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }
}
