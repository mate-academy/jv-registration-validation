package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storage;
    private static final String VALID_LOGIN_1 = "validLogin1";
    private static final String VALID_LOGIN_2 = "validLogin2";
    private static final String EMPTY_STRING_TEST = "";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_LENGTH = "short";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @Test
    void register_validData_ok() {
        User validUser = new User(VALID_LOGIN_1, VALID_PASSWORD, 20);
        assertDoesNotThrow(() -> Storage.people.add(validUser));
    }

    @Test
    void register_sameLogin_notOk() {
        User user1 = new User(VALID_LOGIN_2, VALID_PASSWORD, 25);
        User user2 = new User(VALID_LOGIN_2, VALID_PASSWORD, 30);

        assertDoesNotThrow(() -> Storage.people.add(user1));
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(user2));
    }

    @Test
    void register_shortLoginLength_notOk() {
        User invalidUser = new User(INVALID_LENGTH, VALID_PASSWORD, 25);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_shortPasswordLength_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, INVALID_LENGTH, 25);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_littleAge_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User invalidUser = new User(null, VALID_PASSWORD, 18);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, null, 18);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_nullAge_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, -1000);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        User invalidUser = new User(EMPTY_STRING_TEST, VALID_PASSWORD, 20);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User invalidUser = new User(VALID_LOGIN_2, EMPTY_STRING_TEST, 20);
        assertThrows(RegistrationException.class,
                () -> Storage.people.add(invalidUser));
    }
}
