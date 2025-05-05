package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.DataValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final String VALID_LOGIN = "Oleksa";
    private static final String VALID_PASSWORD = "Ole456";
    private static final int VALID_AGE = 18;

    private static final String INVALID_LOGIN = "Alex";
    private static final String INVALID_LOGIN_NULL = null;
    private static final String INVALID_LOGIN_EMPTY = "";
    private static final String INVALID_PASSWORD = "alex5";
    private static final String INVALID_PASSWORD_NULL = null;
    private static final String INVALID_PASSWORD_EMPTY = "";

    private static final int INVALID_AGE = 17;
    private static final Integer INVALID_AGE_NULL = null;
    private static final Integer INVALID_AGE_ZERO = 0;
    private static final Integer INVALID_AGE_NEGATIVE = -1;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(DataValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(INVALID_LOGIN_NULL);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(INVALID_PASSWORD_NULL);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(INVALID_AGE_NULL);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginLength_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN_EMPTY);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD_EMPTY);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(INVALID_AGE_NEGATIVE);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalideAge_notOk() {
        user.setAge(INVALID_AGE_ZERO);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_suchLoginAlreadyExists_notOk() {
        Storage.people.add(user);
        assertThrows(DataValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAddToStorage_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
