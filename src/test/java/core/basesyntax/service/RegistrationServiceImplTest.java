package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASS_LENGTH = 6;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "Method should throw RunTimeException for null user!");
    }

    @Test
    void register_nullUserLogin_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser(null,
                        "password", MINIMAL_AGE)),
                "Method should throw RunTimeException for user with null login!");
    }

    @Test
    void register_emptyUserLogin_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("",
                        "password", MINIMAL_AGE)),
                "Method should throw RunTimeException for user with empty login!");
    }

    @Test
    void register_nullUserAge_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("UserNullAge",
                        "password", null)),
                "Method should throw RunTimeException for user with null age!");
    }

    @Test
    void register_negativeUserAge_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("UserNegativeAge",
                        "password", -20)),
                "Method should throw RunTimeException for user with negative age!");
    }

    @Test
    void register_userAgeLessMinAge_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("UserAgeLessMin",
                        "password", MINIMAL_AGE - 1)),
                "Method should throw RunTimeException for user with age less " + MINIMAL_AGE + "!");
    }

    @Test
    void register_nullUserPass_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("UserNullPass",
                        null, MINIMAL_AGE)),
                "Method should throw RunTimeException for user with null password");
    }

    @Test
    void register_UserPassLengthLessMinPassLength_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(initUser("UserPassLessMin",
                        "12345", MINIMAL_AGE)),
                "Method should throw RunTimeException for user with password's length less "
                        + MINIMAL_PASS_LENGTH + "!");
    }

    @Test
    void register_userLoginAlreadyExist_notOk() {
        int size = Storage.people.size();
        User expected = registrationService.register(initUser("User",
                "password", MINIMAL_AGE));
        assertEquals(Storage.people.size() - size, 1,
                "Method should add valid user to Storage!");
        assertThrows(RuntimeException.class, () -> registrationService.register(expected),
                "Method should throw RunTimeException "
                        + "for user with already existing login in Storage!");
    }

    @Test
    void register_userValidData_Ok() {
        User expected = initUser("UserValid", "password", MINIMAL_AGE);
        assertEquals(expected, registrationService.register(expected),
                "Method should add valid user to Storage!");
    }

    private User initUser(String login, String pass, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);
        user.setAge(age);
        return user;
    }
}
