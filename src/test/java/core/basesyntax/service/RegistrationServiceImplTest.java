package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String PASSWORD_TEST = "123123";
    private static final String LOGIN_TEST = "MyTestLogin";
    private static final int AGE_TEST = 18;
    private static RegistrationService service;

    @BeforeAll
    static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUserSuccessfulRegistration_Ok() {
        User validUser = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);
        assertEquals(validUser, service.register(validUser));
    }

    @Test
    void register_LoginNull_NotOk() {
        User user = new User(null, PASSWORD_TEST, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_PasswordNull_NotOk() {
        User user = new User(LOGIN_TEST, null, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        User user = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);

        Storage.PEOPLE.add(user);
        User newUser = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(newUser));
    }

    @Test
    void register_LoginLengthLessThanMinLimit_notOk() {
        User userLoginCharZero = new User("", PASSWORD_TEST, AGE_TEST);
        User userLoginCharThree = new User("Ooo", PASSWORD_TEST, AGE_TEST);
        User userLogonCharFive = new User("Thhhh", PASSWORD_TEST, AGE_TEST);

        assertThrows(RegistrationException.class, () -> service.register(userLoginCharZero));
        assertThrows(RegistrationException.class, () -> service.register(userLoginCharThree));
        assertThrows(RegistrationException.class, () -> service.register(userLogonCharFive));
    }

    @Test
    void register_PasswordLengthLessThanMinLimit_notOk() {
        User userPasswordCharZero = new User(LOGIN_TEST, "", AGE_TEST);
        User userPasswordCharThree = new User(LOGIN_TEST, "ooo", AGE_TEST);
        User userPasswordCharFive = new User(LOGIN_TEST, "hhhhh", AGE_TEST);

        assertThrows(RegistrationException.class, () -> service.register(userPasswordCharZero));
        assertThrows(RegistrationException.class, () -> service.register(userPasswordCharThree));
        assertThrows(RegistrationException.class, () -> service.register(userPasswordCharFive));
    }

    @Test
    void register_AgeLessThanMinAge_notOk() {
        User userAgeLess18 = new User(LOGIN_TEST,PASSWORD_TEST, 16);
        assertThrows(RegistrationException.class, () -> service.register(userAgeLess18));
    }

    @Test
    void register_AgeNull_notOk() {
        User userAgeNull = new User(LOGIN_TEST,PASSWORD_TEST, null);
        assertThrows(RegistrationException.class, () -> service.register(userAgeNull));
    }

    @AfterEach
    void cleanStorage() {
        Storage.PEOPLE.clear();
    }
}
