package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_minAge_Ok() {
        user.setAge(MIN_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_moreThenMinAge_Ok() {
        user.setAge(MIN_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(0);
        checkException(user);
    }

    @Test
    void register_lessThenMinAge_NotOk() {
        user.setAge(MIN_AGE - 1);
        checkException(user);
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        checkException(user);
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        checkException(user);
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        checkException(user);
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("p");
        checkException(user);
    }

    @Test
    void register_minPasswordLength_Ok() {
        user.setPassword("passwo");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_MoreThenMinPasswordLength_Ok() {
        user.setPassword(DEFAULT_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private void checkException(User currentUser) {
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }
}
