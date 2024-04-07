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
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void nullUser_null_notOk() {
        User user = null;
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void sameUser_repeatLogin_notOK() {
        User user = new User(123L, "repeatLogin", "correctPassword", 18);
        Storage.people.add(user);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLength_3Length_notOk() {
        User user = new User(123L, "sho", "correctPassword", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLength_5Length_notOk() {
        User user = new User(123L, "short", "correctPassword", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLength_6Length_Ok() {
        User user = new User(123L, "123456", "correctPassword", 20);
        User actual = registrationService.register(user);
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void passwordLength_3Length_notOK() {
        User user = new User(123L, "correctLogin", "sho", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_5Length_notOK() {
        User user = new User(123L, "correctLogin", "short", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_6Length_Ok() {
        User user = new User(123L, "correctLogin", "123456", 20);
        User actual = registrationService.register(user);
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void noId_nullId_notOk() {
        User user = new User(null, "correctLogin", "correctPassword", 18);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void noLogin_nullLogin_notOk() {
        User user = new User(123L, null, "correctPassword", 18);
        assertThrows(ValidationException.class, () -> registrationService.register(user));

    }

    @Test
    void noPassword_nullPassword_notOk() {
        User user = new User(123L, "correctLogin", null, 18);
        assertThrows(ValidationException.class, () -> registrationService.register(user));

    }

    @Test
    void noAge_nullAge_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", null);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAge_under18_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", 17);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAge_18_Ok() {
        User user = new User(123L, "correctLogin", "correctPassword", 18);
        User actual = registrationService.register(user);
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void userAge_22_Ok() {
        User user = new User(123L, "correctLogin", "correctPassword", 22);
        User actual = registrationService.register(user);
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void negativeAge_minus10_notOk() {
        User user = new User(123L, "correctLogin", "correctPassword", -10);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }
}
