package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static final String EXAMPLE_OF_LOGIN = "Thompson";
    private static final int EXAMPLE_OF_AGE = 23;
    private static final int MIN_ALLOWED_AGE = 18;
    private static final String EXAMPLE_OF_PASSWORD = "123456artem";
    private static final String EXAMPLE_OF_INVALID_PASS = "adqd";
    private static final int MAX_ALLOWED_AGE = 125;
    private static final String EMPTY_LINE = "";
    private static final int NEGATIVE_AGE = -33;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setAge(EXAMPLE_OF_AGE);
        user.setLogin(EXAMPLE_OF_LOGIN);
        user.setPassword(EXAMPLE_OF_PASSWORD);
    }

    @Test
    public void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_AgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_AgeIsLessThanAllowed_NotOk() {
        user.setAge(MIN_ALLOWED_AGE - 1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_negativeAgeUser_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_AgeIsBiggerThanAllowed_NotOk() {
        user.setAge(MAX_ALLOWED_AGE + 1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_PassNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_EmptyPassword_NotOk() {
        user.setPassword(EMPTY_LINE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_PassIsLessThanAllowed_NotOk() {
        user.setPassword(EXAMPLE_OF_INVALID_PASS);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ValidUser_Ok() {
        registrationService.register(user);
        assertEquals(EXAMPLE_OF_LOGIN, user.getLogin());
        assertEquals(EXAMPLE_OF_AGE, user.getAge());
        assertEquals(EXAMPLE_OF_PASSWORD, user.getPassword());
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
