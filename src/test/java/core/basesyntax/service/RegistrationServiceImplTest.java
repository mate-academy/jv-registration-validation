package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import java.util.ArrayList;
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
        Storage.people = new ArrayList<>();
    }

    @Test
    void register_addUserByInvalidLogin_notOk() {
        bob.setLogin("bob");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByInvalidPassword_notOk() {
        bob.setPassword("123");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByInvalidAge_notOk() {
        bob.setAge(15);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addUserByAlreadyCreatedLogin_notOk() {
        Storage.people.add(bob);
        bob.setLogin("bob423");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_registerUsersByCorrectData_ok() {
        Storage.people.add(bob);
        bob.setLogin("bob_terminator");
        Storage.people.add(bob);
        bob.setLogin("bob_jenkins");
        Storage.people.add(bob);
        int actual = Storage.people.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void register_getUserByExistLogin_ok() {
        Storage.people.add(bob);
        String actual = storageDao.get(bob.getLogin()).getLogin();
        String expected = bob.getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void register_addUserByNullLogin_notOk() {
        bob.setLogin(null);
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
    void register_addUserByNullAge_notOk() {
        bob.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_addNullUser_notOk() {
        bob = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        });
    }
}