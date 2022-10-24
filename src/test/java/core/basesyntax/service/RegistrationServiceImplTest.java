package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String CORRECT_LOGIN = "login@gmail.com";
    public static final String CORRECT_PASSWORD = "password";
    public static final String INCORRECT_PASSWORD = "pass";
    public static final int CORRECT_AGE = 77;
    public static final int INCORRECT_AGE = 17;
    public static final int NEGATIVE_AGE = -1;
    public static final long CORRECT_ID = 777777L;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        user.setId(CORRECT_ID);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nonExistsUser_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_alreadyExistsUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        user.setAge(CORRECT_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_age_notOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passLength_ok() {
        user.setPassword(CORRECT_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_passLength_notOk() {
        user.setPassword(INCORRECT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void clearAfterEach() {
        Storage.people.clear();
    }
}
