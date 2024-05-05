package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User validUser;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        validUser = new User();
        validUser.setLogin("validLogin");
        validUser.setPassword("strongPass123");
        validUser.setAge(25);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_returnsUser() {
        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser);
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_ageUnder18_notOk() {
        validUser.setAge(17);
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("short");
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("short");
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_existingLogin_notOk() {
        User newUser = new User();
        newUser.setLogin(validUser.getLogin());
        newUser.setPassword("differentPassword");
        newUser.setAge(30);

        registrationService.register(validUser);
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                "Should throw exception when trying to"
                        + " register a user with existing login.");
    }
}
