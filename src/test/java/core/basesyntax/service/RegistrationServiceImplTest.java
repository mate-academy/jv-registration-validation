package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user123";
    private static final String INVALID_LOGIN = "mkwvs";
    private static final String VALID_PASSWORD = "password123";
    private static final String INVALID_PASSWORD = "nvdf";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final int MIN_AGE = 18;
    private static final String NULL_LOGIN = null;
    private static final String NULL_PASSWORD = null;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser;

    @BeforeEach
    void setUp() {
        people.clear();
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void registerNullUserThrowRegistrationException_notOk() {
        validUser = null;
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("User can't be null", exception.getMessage());
    }

    @Test
    public void registerValidUser_Ok() {
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void registerAlreadyAddedUserThrowRegistrationException_notOk() {
        User registeredUser = registrationService.register(validUser);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(registeredUser));
        assertEquals("User - " + registeredUser.getLogin()
                + " is already registered", exception.getMessage());
    }

    @Test
    public void registerUserWithNullLoginThrowRegistrationException_notOk() {
        validUser.setLogin(NULL_LOGIN);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Login can't be null", exception.getMessage());
    }

    @Test
    public void registerUserWithNullPasswordThrowRegistrationException_notOk() {
        validUser.setPassword(NULL_PASSWORD);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Password can't be null", exception.getMessage());
    }

    @Test
    public void registerUserWithInvalidAgeThrowRegistrationException_notOk() {
        validUser.setAge(INVALID_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Not valid age: " + validUser.getAge()
                + ". Min allowed age is " + MIN_AGE, exception.getMessage());
    }

    @Test
    public void registerUserWithShortPasswordThrowRegistrationException_notOk() {
        validUser.setPassword(INVALID_PASSWORD);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Password length ned to be 6 or more symbols", exception.getMessage());
    }

    @Test
    public void registerUserWithShortLoginThrowRegistrationException_notOk() {
        validUser.setLogin(INVALID_LOGIN);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
        assertEquals("Login length ned to be 6 or more symbols", exception.getMessage());
    }
}
