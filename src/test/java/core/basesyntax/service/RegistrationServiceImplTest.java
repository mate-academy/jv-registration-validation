package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "loginlogin";
    private static final String DEFAULT_PASSWORD = "password";
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_existedUser_NotOk() {
        User user = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        registrationService.register(user);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength_ok() {
        User userPasswordMinimumLength = setUpUser(DEFAULT_AGE, "login6", DEFAULT_PASSWORD);
        User userPasswordEnoughLength = setUpUser(DEFAULT_AGE, "longlogin", DEFAULT_PASSWORD);

        Assertions.assertEquals(userPasswordMinimumLength,
                registrationService.register(userPasswordMinimumLength));
        Assertions.assertEquals(userPasswordEnoughLength,
                registrationService.register(userPasswordEnoughLength));
    }

    @Test
    void register_loginLength_NotOk() {
        User userNullLoginValue = setUpUser(DEFAULT_AGE, null, DEFAULT_PASSWORD);
        User userSmallLengthLogin = setUpUser(DEFAULT_AGE, "login", DEFAULT_PASSWORD);
        User userBlankLoginSmallLength = setUpUser(DEFAULT_AGE, "     ", DEFAULT_PASSWORD);
        User userBlankLoginMinimumLength = setUpUser(DEFAULT_AGE, "      ", DEFAULT_PASSWORD);
        User userBlankLoginEnoughLength = setUpUser(DEFAULT_AGE, "             ", DEFAULT_PASSWORD);

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLoginValue));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userSmallLengthLogin));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankLoginSmallLength));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankLoginMinimumLength));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankLoginEnoughLength));
    }

    @Test
    void register_passwordLength_Ok() {
        User userMinimumLength = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "passwr");
        User userPasswordEnoughLength = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "password");

        Assertions.assertEquals(userMinimumLength, registrationService.register(userMinimumLength));
        Assertions.assertEquals(userPasswordEnoughLength,
                registrationService.register(userPasswordEnoughLength));
    }

    @Test
    void register_passwordLength_NotOk() {
        User userNullPasswordValue = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, null);
        User userSmallLengthPassword = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "pass");
        User userBlankPasswordSmallLength = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "    ");
        User userBlankPasswordMinimumLength = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "      ");
        User userBlankPasswordEnoughLength = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "             ");

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPasswordValue));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userSmallLengthPassword));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankPasswordSmallLength));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankPasswordMinimumLength));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userBlankPasswordEnoughLength));
    }

    @Test
    void register_userAge_Ok() {
        User userMinimumFitAge = setUpUser(18, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User userEnoughAge = setUpUser(48, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        Assertions.assertEquals(userMinimumFitAge, registrationService.register(userMinimumFitAge));
        Assertions.assertEquals(userEnoughAge, registrationService.register(userEnoughAge));
    }

    @Test
    void register_userAge_NotOk() {
        User userAlmostEnoughAge = setUpUser(17, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User userSmallAge = setUpUser(2, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User userNegativeAge = setUpUser(-20, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userAlmostEnoughAge));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userSmallAge));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(userNegativeAge));
    }

    @Test
    void register_userNull_Ok() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    private User setUpUser(int age, String login, String password) {
        User user = new User();

        user.setAge(age);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }

}
