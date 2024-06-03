package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegisterException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        User validuser = new User("testing@gmail.com", "123456", 18);
        assertEquals(registrationService.register(validuser), validuser);
    }

    @Test
    void register_loginAlreadyExist_NotOk() {
        User validUser = new User("testing@gmail.com", "123456", 18);
        Storage.people.add(validUser);
        User existedLoginUser = new User("testing@gmail.com", "123456", 18);
        assertThrows(RegisterException.class, () ->
                registrationService.register(existedLoginUser));
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        User negativeAgeUser = new User("testing@gmail.com", "123456", -1);
        assertThrows(RegisterException.class, () ->
                registrationService.register(negativeAgeUser));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegisterException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        User userLoginIsNull = new User(null, "123456", 18);
        assertThrows(RegisterException.class, () ->
                registrationService.register(userLoginIsNull));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        User userPasswordIsNull = new User("testing@gmail.com", null, 18);
        assertThrows(RegisterException.class, () ->
                registrationService.register(userPasswordIsNull));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        User userAgeIsNull = new User("testing@gmail.com", "123456", null);
        assertThrows(RegisterException.class, () ->
                registrationService.register(userAgeIsNull));
    }

    @Test
    void register_shortUserLogin_NotOk() {
        User shortUserLogin = new User("testi", "123456", 18);
        assertThrows(RegisterException.class, () ->
                registrationService.register(shortUserLogin));
    }

    @Test
    void register_shortUserPassword_NotOk() {
        User shortUserPassword = new User("testing@gmail.com", "12345", 18);
        assertThrows(RegisterException.class, () ->
                registrationService.register(shortUserPassword));
    }

    @Test
    void register_underageUser_NotOk() {
        User underageUser = new User(
                "testing@gmail.com",
                "123456",
                17
        );
        assertThrows(RegisterException.class, () ->
                registrationService.register(underageUser));
    }
}
