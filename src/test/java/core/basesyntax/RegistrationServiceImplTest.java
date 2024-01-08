package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Forzen123212";
    private static final Integer DEFAULT_AGE = 20;
    private static final String DEFAULT_PASSWORD = "13Bobo1312";
    private static final String UNACCEPTABLE_LOGIN = "forz";
    private static final String UNACCEPTABLE_LOGIN2 = "for";
    private static final String UNACCEPTABLE_LOGIN3 = "forze";
    private static final Integer UNACCEPTABLE_AGE = 17;
    private static final Integer UNACCEPTABLE_AGE2 = 1;
    private static final Integer UNACCEPTABLE_AGE3 = -13;
    private static final String UNACCEPTABLE_PASSWORD = "pass";
    private static final String UNACCEPTABLE_PASSWORD2 = "123";
    private static final String UNACCEPTABLE_PASSWORD3 = "13444";
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_registerSameUserTwice_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_unacceptableAge_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, UNACCEPTABLE_AGE);
        User user2 = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, UNACCEPTABLE_AGE2);
        User user3 = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, UNACCEPTABLE_AGE3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_unacceptablePassword_notOk() {
        User user = new User(DEFAULT_LOGIN, UNACCEPTABLE_PASSWORD, DEFAULT_AGE);
        User user2 = new User(DEFAULT_LOGIN, UNACCEPTABLE_PASSWORD2, DEFAULT_AGE);
        User user3 = new User(DEFAULT_LOGIN, UNACCEPTABLE_PASSWORD3, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullValuePassword_notOk() {
        User user = new User(DEFAULT_LOGIN, null, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullValueLogin_notOk() {
        User user = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_unacceptableLengthLogin_notOk() {
        User user1 = new User(UNACCEPTABLE_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        User user2 = new User(UNACCEPTABLE_LOGIN2, DEFAULT_PASSWORD, DEFAULT_AGE);
        User user3 = new User(UNACCEPTABLE_LOGIN3, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userValidated_ok() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }
}
