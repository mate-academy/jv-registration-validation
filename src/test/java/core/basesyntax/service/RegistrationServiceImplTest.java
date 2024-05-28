package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final String THREE_CHAR_STRING = "abs";
    private static final String VALID_LOGIN = "klimovych";
    private static final String VALID_PASSWORD = "password";
    private static final int AGE = 32;
    private static final String INVALID_LOGIN = "wrong";
    private static final String INVALID_PASSWORD = "pass";
    private static final int INVALID_AGE = 17;
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int NEGATIVE_AGE = -5;
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE);
    }

    @Test
    void register_nullUser_Not_Ok() {
        String expected = "User can't be null";
        assertEquals(expected, assertThrowsException(null).getMessage());
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validUserExistsInData_Not_Ok() {
        Storage.people.add(user);
        String expected = "User already exist in database!";
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_invalidAge_Not_Ok() {
        user.setAge(INVALID_AGE);
        String expected = "Minimum allowed age is " + MIN_AGE;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_negativeAge_Not_Ok() {
        user.setAge(NEGATIVE_AGE);
        String expected = "Minimum allowed age is " + MIN_AGE;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_minimumAge_Ok() {
        user.setAge(MIN_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_invalidLogin_Not_Ok() {
        user.setLogin(INVALID_LOGIN);
        String expected = "Minimum allowed login length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_threeCharsLogin_Not_Ok() {
        user.setLogin(THREE_CHAR_STRING);
        String expected = "Minimum allowed login length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_emptyLogin_Not_Ok() {
        user.setLogin(EMPTY_STRING);
        String expected = "Minimum allowed login length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_invalidPassword_Not_Ok() {
        user.setPassword(INVALID_PASSWORD);
        String expected = "Minimum allowed password length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_threeCharsPassword_Not_Ok() {
        user.setPassword(THREE_CHAR_STRING);
        String expected = "Minimum allowed password length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_emptyPassword_Not_Ok() {
        user.setPassword(EMPTY_STRING);
        String expected = "Minimum allowed password length is " + MIN_LENGTH;
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_nullAge_Not_Ok() {
        user.setAge(null);
        String expected = "Age can't be null";
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_nullLogin_Not_Ok() {
        user.setLogin(null);
        String expected = "Login can't be null";
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    @Test
    void register_nullPassword_Not_Ok() {
        user.setPassword(null);
        String expected = "Password can't be null";
        assertEquals(expected, assertThrowsException(user).getMessage());
    }

    private UserValidationException assertThrowsException(User invalidUser) {
        return assertThrows(UserValidationException.class, () ->
                registrationService.register(invalidUser));
    }
}
