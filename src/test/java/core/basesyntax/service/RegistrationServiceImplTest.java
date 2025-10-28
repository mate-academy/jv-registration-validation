package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User userNullLogin = new User(52152L, null, "password", 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        User userNullPassword = new User(52152L, "Alexander", null, 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_nullAge_notOk() {
        User userNullPassword = new User(52152L, "Alexander", "password", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_validRegistration_Ok() {
        User validUser = new User(52152L, "Alexander", "password", 19);
        registrationService.register(validUser);
        User expectedUserFromDB = storageDao.get(validUser.getLogin());

        assertEquals(validUser, expectedUserFromDB);
    }

    @Test
    void register_validRegistrationEdgeCases_Ok() {
        int minAge = 18;
        String edgeLogin = "loginn";
        String edgePassword = "passwo";

        User edgeCaseUser = new User(125120L, edgeLogin, edgePassword, minAge);
        User registeredUser = registrationService.register(edgeCaseUser);
        User userExpectedFromDB = storageDao.get(edgeLogin);

        assertEquals(edgeCaseUser, registeredUser,
                "User returned from register() must equal to input user for edge case values.");
        assertEquals(edgeCaseUser, userExpectedFromDB,
                "User with edge case data must be stored correctly in DB.");
    }

    @Test
    void register_validReturnedUser_Ok() {
        User expectedUser = new User(52152L, "Alexander", "password", 19);
        User actualUser = registrationService.register(expectedUser);

        assertEquals(expectedUser, actualUser,
                "expected: " + expectedUser + ", but was: " + actualUser);
    }

    @Test
    void register_alreadyExistsUser_notOk() {
        User validUser = new User(52152L, "Alexander", "password", 19);
        storageDao.add(validUser);

        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(52152L, "Alexander", "password", 19)),
                "User: " + validUser + ", is already exists.");
    }

    @Test
    void register_invalidLoginSize_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(321884L, "John", "password", 19)));

        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(151884L, "Maria", "password", 19)));
    }

    @Test
    void register_invalidPasswordSize_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(321884L, "Maksym", "ps", 19)));

        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(842814L, "Karina", "passw", 19)));
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(321884L, "Maksym", "password", 12)));

        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(321884L, "Maksym", "password", 17)));
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(321884L, "Maksym", "password", -4)));
    }
}
