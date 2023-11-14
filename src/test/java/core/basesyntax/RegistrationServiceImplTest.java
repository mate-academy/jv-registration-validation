package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String ACCEPTABLE_LOGIN = "nowakjan";
    private static final String INVALID_LOGIN1 = "sth";
    private static final String INVALID_LOGIN2 = "pawel";
    private static final String ACCEPTABLE_PASSWORD = "javaisfun";
    private static final String INVALID_PASSWORD1 = "cos";
    private static final String INVALID_PASSWORD2 = "books";
    private static final int ACCEPTABLE_AGE = 22;
    private static final int INVALID_AGE1 = 3;
    private static final int INVALID_AGE2 = 17;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_InvalidAge_notOk() {
        User user1 = new User(ACCEPTABLE_LOGIN, ACCEPTABLE_PASSWORD, INVALID_AGE1);
        User user2 = new User(ACCEPTABLE_LOGIN, ACCEPTABLE_PASSWORD, INVALID_AGE2);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_InvalidLogin_notOk() {
        User user1 = new User(INVALID_LOGIN1, ACCEPTABLE_PASSWORD, ACCEPTABLE_AGE);
        User user2 = new User(INVALID_LOGIN2, ACCEPTABLE_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_InvalidPassword_notOk() {
        User user1 = new User(ACCEPTABLE_LOGIN, INVALID_PASSWORD1, ACCEPTABLE_AGE);
        User user2 = new User(ACCEPTABLE_LOGIN, INVALID_PASSWORD2, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullLogin_notOK() {
        User user = new User(null, ACCEPTABLE_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(ACCEPTABLE_LOGIN, null, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(ACCEPTABLE_LOGIN, ACCEPTABLE_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserIsAlreadyRegistered_notOk() {
        User user = new User(ACCEPTABLE_LOGIN, ACCEPTABLE_PASSWORD, ACCEPTABLE_AGE);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
