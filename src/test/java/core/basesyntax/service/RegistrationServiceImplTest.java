package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String CORRECT_DATA = "MoreThan6";
    private static final String INCORRECT_DATA = "UpTo6";
    private static final int CORRECT_AGE = 21;
    private static final int INCORRECT_AGE = 17;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(CORRECT_DATA);
        user.setPassword(CORRECT_DATA);
        user.setAge(CORRECT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(INCORRECT_DATA);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(INCORRECT_DATA);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_userWithSameLogin_notOk() {
        Storage.people.add(user);
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin(CORRECT_DATA);
        userWithSameLogin.setPassword(CORRECT_DATA);
        userWithSameLogin.setAge(CORRECT_AGE);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(userWithSameLogin);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
