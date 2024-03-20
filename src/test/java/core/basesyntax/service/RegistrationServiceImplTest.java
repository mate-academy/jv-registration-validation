package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    static void initService() {
        service = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        String newLogin = "newLogin";
        User newUser = new User();
        newUser.setLogin(newLogin);
        newUser.setAge(23);
        newUser.setPassword("1234567");
        String existingLogin = "newLogin";
        User newUserWithSameLogin = new User();
        newUserWithSameLogin.setLogin(existingLogin);
        newUserWithSameLogin.setAge(34);
        newUserWithSameLogin.setPassword("4574527");
        service.register(newUser);
        Assertions.assertThrows(ValidationException.class,
                () -> service.register(newUserWithSameLogin));
    }

    @Test
    void register_loginLengthLessThan6Chars_notOk() {
        String wrongLogin = "Login";
        User newUser = new User();
        newUser.setLogin(wrongLogin);
        newUser.setAge(23);
        newUser.setPassword("1234567");
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_passwordLengthLessThan6Chars_notOk() {
        User newUser = new User();
        String wrongPassword = "12345";
        newUser.setLogin("newLogin");
        newUser.setAge(23);
        newUser.setPassword(wrongPassword);
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_ageLessThan18_notOk() {
        int wrongAge = 17;
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(wrongAge);
        newUser.setPassword("1234567");
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_Ok() {
        int wrongAge = 19;
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(wrongAge);
        newUser.setPassword("1234567");
        service.register(newUser);
        long userIdAfterRegistration = newUser.getId();
        Assertions.assertEquals(1, userIdAfterRegistration);
    }

    @Test
    void register_UserNull_notOk() {
        User newUser = null;
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_UserLoginIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setAge(55);
        newUser.setPassword("1234567");
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_UserAgeIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(null);
        newUser.setPassword("1234567");
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }

    @Test
    void register_UserPasswordIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(44);
        newUser.setPassword(null);
        Assertions.assertThrows(ValidationException.class, () -> service.register(newUser));
    }
}
