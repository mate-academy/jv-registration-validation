package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_PASSWORD = "12345";
    private static final String LILY_PASSWORD = "lilyPassword";
    private static final Integer LOWER_THAN_POSSIBLE_AGE = 17;
    private static final Integer HIGHER_THAN_POSSIBLE_AGE = 159;
    private static final Integer LILY_AGE = 24;
    private static final String LILY_LOGIN = "lilyLogin";

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LILY_LOGIN);
        user.setPassword(LILY_PASSWORD);
        user.setAge(LILY_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(user);
        assertEquals(LILY_LOGIN, user.getLogin());
        assertEquals(LILY_PASSWORD, user.getPassword());
        assertEquals(LILY_AGE, user.getAge());
        assertEquals(1, user.getId());
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        User newUser = new User();
        newUser.setLogin(LILY_LOGIN);
        newUser.setPassword(LILY_PASSWORD);
        newUser.setAge(LILY_AGE);
        Storage.people.add(user);
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
