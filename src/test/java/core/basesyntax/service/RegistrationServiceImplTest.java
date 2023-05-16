package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final int DEFAULT_AGE = 25;
    private static RegistrationService registration;
    private static StorageDao storage;
    private static User user;

    @BeforeAll
    static void setUp() {
        registration = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setDefaultUser() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userWithValidData_ok() {
        assertEquals(user, registration.register(user),
                "Test failed! User " + user + " has correct data");
    }

    @Test
    void register_loginSurroundedSpaces_ok() {
        user.setLogin(" " + DEFAULT_LOGIN + "  ");
        registration.register(user);
        assertEquals(user, storage.get(DEFAULT_LOGIN),
                "Test failed! Login '" + " " + DEFAULT_LOGIN + "  ' is valid");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registration.register(null);
        }, "Test failed! User with Null");
    }

    @Test
    void register_existingUser_notOk() {
        registration.register(user);
        assertNull(registration.register(user),
                "Test failed! User " + user + " is already exist");
    }
}
