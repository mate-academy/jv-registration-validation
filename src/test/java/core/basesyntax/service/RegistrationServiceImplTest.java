package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    public static void createRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(ValidationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_UsersWithInvalidCredentials_notOk() {
        User userInvalidPassword = new User();
        userInvalidPassword.setLogin("Corey");
        userInvalidPassword.setAge(42);
        userInvalidPassword.setPassword("asdf");

        User userInvalidAge = new User();
        userInvalidAge.setLogin("John Deere");
        userInvalidAge.setAge(12);
        userInvalidAge.setPassword("qwerty");

        assertThrows(ValidationException.class,
                () -> registrationService.register(userInvalidPassword));
        assertThrows(ValidationException.class,
                () -> registrationService.register(userInvalidAge));
    }

    @Test
    void register_UserWithNullPassword_notOk() {
        User userNullPassword = new User();
        userNullPassword.setLogin("Corey");
        userNullPassword.setAge(42);
        userNullPassword.setPassword(null);

        assertThrows(ValidationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_UserWithNullLogin_notOk() {
        User userNullLogin = new User();
        userNullLogin.setLogin(null);
        userNullLogin.setAge(42);
        userNullLogin.setPassword("asdfqwer");

        assertThrows(ValidationException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_UserWithNegativeAge_notOk() {
        User userNullLogin = new User();
        userNullLogin.setLogin("Abrams");
        userNullLogin.setAge(-1);
        userNullLogin.setPassword("asdfqwer");

        assertThrows(ValidationException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_existingUser_notOk() {
        User userValid = new User();
        userValid.setLogin("John");
        userValid.setAge(18);
        userValid.setPassword("qwerty");

        assertEquals(userValid, registrationService.register(userValid));
        assertThrows(ValidationException.class, () -> registrationService.register(userValid));
    }

    @Test
    void register_userWithValidCredentials_Ok() {
        User userValid = new User();
        userValid.setLogin("John");
        userValid.setAge(18);
        userValid.setPassword("qwerty");

        User userValidOther = new User();
        userValidOther.setLogin("James");
        userValidOther.setAge(58);
        userValidOther.setPassword("metallica");

        assertEquals(userValid, registrationService.register(userValid));
        assertEquals(userValidOther, registrationService.register(userValidOther));
    }
}
