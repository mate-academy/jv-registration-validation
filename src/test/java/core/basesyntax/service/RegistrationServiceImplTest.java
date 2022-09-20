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
    private static RegistrationService service;
    private static StorageDao storageDao;
    private User actual;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        actual = new User();
        actual.setLogin("user0");
        actual.setPassword("Password");
        actual.setAge(19);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class,
                () -> service.register(null), "An Exception must be thrown here");
    }

    @Test
    void register_nullLogin_notOk() {
        actual.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_newUser_ok() {
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()),
                actual,
                "User is already exists in the database");
    }

    @Test
    void register_existingUser_notOk() {
        service.register(actual);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_nullPassword_notOk() {
        actual.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_incorrectPassword_notOk() {
        actual.setPassword("Pas");
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_nullAge_notOk() {
        actual.setAge(null);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_negativeAge_notOk() {
        actual.setAge(-18);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_incorrectAge_notOk() {
        actual.setAge(17);
        assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
    }

    @Test
    void register_boundCorrectAge_ok() {
        actual.setAge(18);
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()), actual);
    }

    @Test
    void register_correctAge_ok() {
        actual.setLogin("user2");
        actual.setAge(19);
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()), actual);
    }
}
