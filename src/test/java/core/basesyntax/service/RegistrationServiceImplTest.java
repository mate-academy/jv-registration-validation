package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void userAdd_correctUser_Ok() {
        User user = new User(123L, "correctLogin", "correctPassword", 20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void nullUser_null_notOk() {
        User user = null;
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_NULL_USER);
    }

    @Test
    void sameUser_repeatLogin_notOK() {
        User user = new User(123L, "repeatLogin", "correctPassword", 18);
        Storage.people.add(user);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_USER_EXISTS);
    }

    @Test
    void loginLength_3Length_notOk() {
        User user = new User(123L, "sho", "correctPassword", 20);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_SHORT_LOGIN);
    }

    @Test
    void loginLength_5Length_notOk() {
        User user = new User(123L, "short", "correctPassword", 20);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_SHORT_LOGIN);
    }

    @Test
    void loginLength_6Length_Ok() {
        User user = new User(123L, "123456", "correctPassword", 20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void passwordLength_3Length_notOK() {
        User user = new User(123L, "correctLogin", "sho", 20);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_SHORT_PASSWORD);
    }

    @Test
    void passwordLength_5Length_notOK() {
        User user = new User(123L, "correctLogin", "short", 20);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_SHORT_PASSWORD);
    }

    @Test
    void passwordLength_6Length_Ok() {
        User user = new User(123L, "correctLogin", "123456", 20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void noId_nullId_notOk() {
        User user = new User(null, "correctLogin", "correctPassword", 18);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_ID_REQUIRED);
    }

    @Test
    void noLogin_nullLogin_notOk() {
        User user = new User(123L, null, "correctPassword", 18);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_LOGIN_REQUIRED);
    }

    @Test
    void noPassword_nullPassword_notOk() {
        User user = new User(123L, "correctLogin", null, 18);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_PASSWORD_REQUIRED);
    }

    @Test
    void noAge_nullAge_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", null);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_AGE_REQUIRED);
    }

    @Test
    void userAge_under18_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", 17);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_UNDERAGE_USER);
    }

    @Test
    void userAge_18_Ok() {
        User user = new User(123L, "correctLogin", "correctPassword", 18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userAge_22_Ok() {
        User user = new User(123L, "correctLogin", "correctPassword", 22);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void negativeAge_minus10_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", -10);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_UNDERAGE_USER);
    }

    @Test
    void spaceInLogin_spaceLogin_notOk() {
        User user = new User(123L, "space Login", "correctPassword", 20);
        assertUserRegistrationThrowsValidationExceptionWithMessage(
                user, ErrorMessage.ERROR_SPACE_IN_LOGIN);
    }

    private void assertUserRegistrationThrowsValidationExceptionWithMessage(
            User user, String errorMessage) {
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(errorMessage, actual.getMessage());
    }
}
