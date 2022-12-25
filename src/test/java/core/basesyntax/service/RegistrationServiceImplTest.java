package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String UNIQUE_LOGIN = "arsenvovk";
    private static final String USED_LOGIN = "bobhomenko";
    private static final String VALID_PASSWORD = "1vtqn9frf";
    private static final String INVALID_PASSWORD = "qwer";
    private static final Integer ADULT_AGE = 27;
    private static final Integer YOUNG_AGE = 17;
    private static final Integer INVALID_AGE = -8;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(UNIQUE_LOGIN, ADULT_AGE, VALID_PASSWORD);
    }

    @Test
    void register_userIsNull_notOk() {
        testUser = null;
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        testUser.setLogin(USED_LOGIN);
        Storage.people.add(testUser);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(INVALID_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_youngUser_notOk() {
        testUser.setAge(YOUNG_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_notNullReturnUser_ok() {
        assertNotNull(registrationService.register(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
