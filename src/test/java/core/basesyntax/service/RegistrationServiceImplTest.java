package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INVALID_AGE = 16;
    private static final int VALID_AGE = 18;
    private static final long INVALID_ID = 0L;
    private static final long VALID_ID = 1234567890L;
    private static final String INVALID_PASSWORD = null;
    private static final String VALID_PASSWORD = "qwerty";
    private static final String VALID_LOGIN = "nameEx";
    private static final String INVALID_LOGIN = "name";

    private final User validUser = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        validUser.setAge(VALID_AGE);
        validUser.setId(VALID_ID);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setLogin(VALID_LOGIN);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_AddToStorage_Ok() {
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "User should be added to storage");
    }

    @Test
    public void run_ValidationException_Ok() {
        validUser.setPassword(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Validation error",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_invalidAge_notOk() {
        assertTrue(validUser.getAge() >= VALID_AGE,
                "User age should be greater than or equal to " + VALID_AGE);
    }

    @Test
    public void register_invalidLogin_notOk() {
        assertTrue(validUser.getLogin().length() >= VALID_LOGIN.length(),
                "User login length should be greater than or equal to " + VALID_LOGIN.length());
    }

    @Test
    public void register_invalidPassword_notOk() {
        assertTrue(validUser.getPassword().length() >= VALID_PASSWORD.length(),
                "User password length should be greater than or equal to "
                        + VALID_PASSWORD.length());
    }

    @Test
    public void userWithSameLoginExist() {
        assertFalse(Storage.people.contains(validUser),
                "User with the same login should not exist");
    }
}
