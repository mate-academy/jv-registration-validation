package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_PASSWORD = "12345";
    private static final String LILY_PASSWORD = "lilyPassword";
    private static final Integer LOWER_THAN_POSSIBLE_AGE = 17;
    private static final Integer HIGHER_THAN_POSSIBLE_AGE = 159;
    private static final Integer LILY_AGE = 24;
    private static final String INVALID_LOGIN = "DefaultLogin";
    private static final String VALID_LOGIN = "DefaultLogin";
    private static final String LILY_LOGIN = "LilyLogin";

    private static RegistrationService registrationService;
    private User user;
    private User lily;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(LILY_PASSWORD);
        user.setAge(LILY_AGE);
        lily = new User();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_validUser_Ok() {
        lily.setLogin(LILY_LOGIN);
        lily.setPassword(LILY_PASSWORD);
        lily.setAge(LILY_AGE);
        registrationService.register(lily);
        assertEquals(2, lily.getId());
        assertEquals(LILY_LOGIN, lily.getLogin());
        assertEquals(LILY_PASSWORD, lily.getPassword());
        assertEquals(LILY_AGE, lily.getAge());
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(InvalidInputDataException.class, () -> {
            user.setPassword(INVALID_PASSWORD);
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(InvalidInputDataException.class, () -> {
            user.setPassword(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(INVALID_LOGIN);
        newUser.setPassword(LILY_PASSWORD);
        newUser.setAge(LILY_AGE);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_lowerThanPossible_notOk() {
        user.setAge(LOWER_THAN_POSSIBLE_AGE);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_higherThanPossible_notOk() {
        user.setAge(HIGHER_THAN_POSSIBLE_AGE);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
