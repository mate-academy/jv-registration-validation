package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INVALID_AGE = 16;
    private static final int VALID_AGE = 18;
    private static final long INVALID_ID = 0L;
    private static final long VALID_ID = 1234567890L;
    private static final String INVALID_PASSWORD = null;
    private static final String VALID_PASSWORD = "qwerty";

    private final User validUser = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        validUser.setAge(VALID_AGE);
        validUser.setId(VALID_ID);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setLogin("nameExample");
    }

    @Test
    public void userAddedToStorage() {
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "User should be added to storage");
    }

    @Test
    public void runValidationException() {
        validUser.setPassword(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Validation error",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void userAgeIsValid() {
        assertTrue(validUser.getAge() >= VALID_AGE,
                "User age should be greater than or equal to " + VALID_AGE);
    }

    @Test
    public void userWithSameLoginDoesNotExist() {
        assertFalse(Storage.people.contains(validUser),
                "User with the same login should not exist");
    }
}
