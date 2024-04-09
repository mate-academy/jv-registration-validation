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
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_NULL_USER, actual.getMessage());
    }

    @Test
    void sameUser_repeatLogin_notOK() {
        User user = new User(123L, "repeatLogin", "correctPassword", 18);
        Storage.people.add(user);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_USER_EXISTS, actual.getMessage());
    }

    @Test
    void loginLength_3Length_notOk() {
        User user = new User(123L, "sho", "correctPassword", 20);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_SHORT_LOGIN, actual.getMessage());
    }

    @Test
    void loginLength_5Length_notOk() {
        User user = new User(123L, "short", "correctPassword", 20);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_SHORT_LOGIN, actual.getMessage());
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
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_SHORT_PASSWORD, actual.getMessage());
    }

    @Test
    void passwordLength_5Length_notOK() {
        User user = new User(123L, "correctLogin", "short", 20);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_SHORT_PASSWORD, actual.getMessage());
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
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_ID_REQUIRED, actual.getMessage());
    }

    @Test
    void noLogin_nullLogin_notOk() {
        User user = new User(123L, null, "correctPassword", 18);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_LOGIN_REQUIRED, actual.getMessage());

    }

    @Test
    void noPassword_nullPassword_notOk() {
        User user = new User(123L, "correctLogin", null, 18);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_PASSWORD_REQUIRED, actual.getMessage());

    }

    @Test
    void noAge_nullAge_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", null);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_AGE_REQUIRED, actual.getMessage());
    }

    @Test
    void userAge_under18_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", 17);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_UNDERAGE_USER, actual.getMessage());
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
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_UNDERAGE_USER, actual.getMessage());
    }

    @Test
    void spaceInLogin_spaceLogin_notOk() {
        User user = new User(123L, "space Login", "correctPassword", 20);
        ValidationException actual = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(ErrorMessage.ERROR_SPACE_IN_LOGIN, actual.getMessage());
    }
}
