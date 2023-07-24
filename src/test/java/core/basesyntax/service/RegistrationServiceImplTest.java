package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = " ";
    private static final String VALID_PASSWORD = "abcdef";
    private static final String NOT_VALID_PASSWORD = "abcde";
    private static final String VALID_LOGIN = "fedcba";
    private static final String NOT_VALID_LOGIN = "fedcb";
    private static final User EXISTING_USER = new User(VALID_LOGIN, "password", 20);
    private static final Integer VALID_AGE = 18;
    private static final Integer NOT_VALID_AGE = 17;
    private static final int MIN_FIELD_LENGTH = 6;

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void registerNullUser() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User can't be null", registrationException.getMessage());
    }

    @Test
    public void registerValidUser() {
        User expectedUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
        assertNotNull(actualUser);
    }

    @Test
    public void registerNullPassword() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        null, VALID_AGE)));
        assertEquals("Login and password can't be null", registrationException.getMessage());
    }

    @Test
    public void registerNullLogin() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(null,
                        VALID_PASSWORD, VALID_AGE)));
        assertEquals("Login and password can't be null", registrationException.getMessage());
    }

    @Test
    public void registerNullAge() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        VALID_PASSWORD, null)));
        assertEquals("User age can't be null", registrationException.getMessage());
    }

    @Test
    public void registerNotValidLogin() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(NOT_VALID_LOGIN,
                        VALID_PASSWORD, VALID_AGE)));
        assertEquals("Login and password must be longer than " + MIN_FIELD_LENGTH,
                registrationException.getMessage());
    }

    @Test
    public void registerNotValidPassword() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        NOT_VALID_PASSWORD, VALID_AGE)));
        assertEquals("Login and password must be longer than " + MIN_FIELD_LENGTH,
                registrationException.getMessage());
    }

    @Test
    public void registerNotValidAge() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        VALID_PASSWORD, NOT_VALID_AGE)));
        assertEquals("User age must be greater than " + VALID_AGE,
                registrationException.getMessage());
    }

    @Test
    public void registerEmptyLogin() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(EMPTY_STRING,
                        VALID_PASSWORD, VALID_AGE)));
        assertEquals("Login and password can't be empty",
                registrationException.getMessage());
    }

    @Test
    public void registerEmptyPassword() {
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        EMPTY_STRING, VALID_AGE)));
        assertEquals("Login and password can't be empty",
                registrationException.getMessage());
    }

    @Test
    public void registerExistingLogin() {
        Storage.people.add(EXISTING_USER);
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(VALID_LOGIN,
                        VALID_PASSWORD, VALID_AGE)));
        assertEquals("User with login: " + EXISTING_USER.getLogin() + " already exists",
                registrationException.getMessage());
    }
}
